package net.thumbtack.school.database;

import net.thumbtack.school.GettingCityData;
import net.thumbtack.school.JdbcUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JdbcUtils.class)
public class GettingCityDataTestDatabaseSpy {

    private static Connection spyConnection;
    private static final String USER = "test";
    private static final String PASSWORD = "test";
    private static final String URL = "jdbc:mysql://localhost:3306/ttschool?useUnicode=yes&characterEncoding=UTF8&useSSL=false&serverTimezone=Asia/Omsk";

    @Before
    public void setUp() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        spyConnection = spy(connection);
        mockStatic(JdbcUtils.class);
        when(JdbcUtils.getConnection()).thenReturn(spyConnection);
    }
    @Test
    public void testDownloadDataOfCities() throws SQLException, IOException {
        GettingCityData gettingCityData = new GettingCityData();
        gettingCityData.downloadDataOfCities();
        
        verify(spyConnection, never()).createStatement();
        verify(spyConnection).prepareStatement(anyString());
    }

    @Test
    public void testDownloadDataOfCitiesContainsSelect() throws SQLException, IOException {
        GettingCityData gettingCityData = new GettingCityData();

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        reset(spyConnection);
        gettingCityData.downloadDataOfCities();

        verify(spyConnection).prepareStatement(argument.capture());
        assertTrue(argument.getValue().toLowerCase().contains("select"));
    }
}