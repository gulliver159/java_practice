package net.thumbtack.school.database;

import net.thumbtack.school.GettingCityData;
import net.thumbtack.school.JdbcUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JdbcUtils.class)
public class GettingCityDataTestDatabaseMock {

    @Test
    public void downloadDataOfCities() throws SQLException, IOException {
        ResultSet resultSet = mock(ResultSet.class);

        when(resultSet.getString("name")).thenReturn("london")
                .thenReturn("canberra")
                .thenReturn("ottawa")
                .thenReturn("paris")
                .thenReturn("rome");

        PreparedStatement statement = mock(PreparedStatement.class);
        when(statement.executeQuery()).thenReturn(resultSet);

        Connection jdbcConnection = mock(Connection.class);
        when(jdbcConnection.prepareStatement(anyString())).thenReturn(statement);

        mockStatic(JdbcUtils.class);
        when(JdbcUtils.getConnection()).thenReturn(jdbcConnection);

        when(resultSet.next()).thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);

        GettingCityData gettingCityData = new GettingCityData();
        gettingCityData.downloadDataOfCities();

        verify(jdbcConnection, times(0)).createStatement();
        verify(jdbcConnection, times(1)).prepareStatement(anyString());
    }
}