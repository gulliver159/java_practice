package net.thumbtack.school;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GettingCityData {

    public Map<String, Set<String>> downloadDataOfCities() throws IOException, SQLException {
        Map<String, Set<String>> dataCities = new HashMap<>();
        Set<String> cities = new HashSet<>();

        String query = "select * from cities";
        JdbcUtils.createConnection();
        Connection conDatabase = JdbcUtils.getConnection();

        try (PreparedStatement stmt = conDatabase.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cities.add(rs.getString("name"));
            }
        }

        for (String cityName : cities) {
            URL url = new URL("http://restcountries.eu/rest/v2/capital/" + cityName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            try( InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
                 BufferedReader buff = new BufferedReader(in)) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = buff.readLine()) != null) {
                    sb.append(line).append("\n");
                    Pattern pattern = Pattern.compile("\"name\":\"(\\D+)\",\"top.+\"code\":\"(\\D+)\",\"name");
                    Matcher m = pattern.matcher(sb);
                    if (m.find()) {
                        Set<String> dataCity = new HashSet<>();
                        dataCity.add(m.group(1));
                        dataCity.add(m.group(2));
                        dataCities.put(cityName, dataCity);
                    }
                }
            }
        }
        return dataCities;
    }

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println(new GettingCityData().downloadDataOfCities());
    }

}
