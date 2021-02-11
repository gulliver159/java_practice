package net.thumbtack.school.http;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import net.thumbtack.school.GettingCityData;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class GettingCityDataTestHttpJMockit {

    @Mocked HttpURLConnection conn;
    @Mocked URL url;

    @Test
    public void testUrl() throws Exception {
        try {
            new GettingCityData().downloadDataOfCities();
        } catch (NullPointerException e) {}

        new Verifications() {{
            withCapture(new URL("http://restcountries.eu/rest/v2/capital/london"));
        }};
    }

    @Test
    public void testDownloadPage() throws Exception {

        InputStream stream = new ByteArrayInputStream(
                ("[{\"name\":\"United Kingdom of Great Britain and Northern Ireland\",\"topLevelDomain\":" +
                        "[\".uk\"],\"gini\":34.0,\"timezones\":[\"UTC-08:00\",\"UTC-05:00\",\"numericCode\":" +
                        "\"826\",\"currencies\":[{\"code\":\"GBP\",\"name\":\"British pound\",\"symbol\":\"£\"}]")
                        .getBytes()
        );

        new Expectations() {{
            url.openConnection(); result = conn;
            conn.getContent();    result = stream;
        }};

        Map<String, Set<String>> result = new GettingCityData().downloadDataOfCities();

        Map<String, Set<String>> expectedResult = new HashMap<>();
        Set<String> dataCity = new HashSet<>();
        dataCity.add("United Kingdom of Great Britain and Northern Ireland");
        dataCity.add("GBP");
        expectedResult.put("london", dataCity);

        assertEquals(expectedResult, result);
    }
}