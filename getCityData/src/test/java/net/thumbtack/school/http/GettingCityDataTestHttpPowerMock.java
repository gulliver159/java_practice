package net.thumbtack.school.http;

import net.thumbtack.school.GettingCityData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.api.mockito.PowerMockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GettingCityData.class)
public class GettingCityDataTestHttpPowerMock {

    @Test
    public void testUrl() throws Exception {
        HttpURLConnection http = mock(HttpURLConnection.class);
        URL url = mock(URL.class);
        when(url.openConnection()).thenReturn(http);
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(url);

        try {
            new GettingCityData().downloadDataOfCities();
        } catch (NullPointerException e) {
            //ignore
        }

        PowerMockito.verifyNew(URL.class).withArguments("http://restcountries.eu/rest/v2/capital/london");
    }

    @Test
    public void testDownloadPage() throws Exception {
        InputStream stream = new ByteArrayInputStream(
                ("[{\"name\":\"United Kingdom of Great Britain and Northern Ireland\",\"topLevelDomain\":" +
                        "[\".uk\"],\"gini\":34.0,\"timezones\":[\"UTC-08:00\",\"UTC-05:00\",\"numericCode\":" +
                        "\"826\",\"currencies\":[{\"code\":\"GBP\",\"name\":\"British pound\",\"symbol\":\"£\"}]")
                        .getBytes()
        );
        HttpURLConnection http = mock(HttpURLConnection.class);
        when(http.getContent()).thenReturn(stream);
        URL url = mock(URL.class);
        when(url.openConnection()).thenReturn(http);
        PowerMockito.whenNew(URL.class).withAnyArguments().thenReturn(url);

        Map<String, Set<String>> result = new GettingCityData().downloadDataOfCities();

        Map<String, Set<String>> expectedResult = new HashMap<>();
        Set<String> dataCity = new HashSet<>();
        dataCity.add("United Kingdom of Great Britain and Northern Ireland");
        dataCity.add("GBP");
        expectedResult.put("london", dataCity);

        assertEquals(expectedResult, result);
    }
}