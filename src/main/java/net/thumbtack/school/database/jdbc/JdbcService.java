package net.thumbtack.school.database.jdbc;

import net.thumbtack.school.database.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcService {
    private static Connection con = JdbcUtils.getConnection();
    public static void insertTrainee(Trainee trainee) throws SQLException {
        String query = "insert into trainee values (?,?,?,?,null)";
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setString(2, trainee.getFirstName());
            stmt.setString(3, trainee.getLastName());
            stmt.setInt(4, trainee.getRating());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                trainee.setId(generatedKeys.getInt((1)));
            } else {
                throw new SQLException("Creating Trainee failed, no ID obtained.");
            }
        }
    }

    public  static void updateTrainee(Trainee trainee) throws SQLException {
        String query = "update trainee set firstName = ?, lastName = ?, rating = ? where id = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, trainee.getFirstName());
            stmt.setString(2, trainee.getLastName());
            stmt.setInt(3, trainee.getRating());
            stmt.setInt(4, trainee.getId());
            stmt.executeUpdate();
        }
    }

    public static Trainee getTraineeByIdUsingColNames(int traineeId) throws SQLException {
        String query = "select * from trainee where id = " + traineeId;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Trainee(traineeId, rs.getString("firstName"),
                        rs.getString("lastName"), rs.getInt("rating"));
            }
            return null;
        }
    }

    public static Trainee getTraineeByIdUsingColNumbers(int traineeId) throws SQLException {
        String query = "select * from trainee where id = " + traineeId;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Trainee(traineeId, rs.getString(2),
                        rs.getString(3), rs.getInt(4));
            }
            return null;
        }
    }

    public static List<Trainee> getTraineesUsingColNames() throws SQLException {
        String query = "select * from trainee";
        List<Trainee> trainees = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                trainees.add(new Trainee(rs.getInt("id"), rs.getString("firstName"),
                        rs.getString("lastName"), rs.getInt("rating")));
            }
        }
        return trainees;
    }

    public static List<Trainee> getTraineesUsingColNumbers() throws SQLException {
        String query = "select * from trainee";
        List<Trainee> trainees = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            while(rs.next()){
                trainees.add(new Trainee(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4)));
            }
        }
        return trainees;
    }

    public static void deleteTrainee(Trainee trainee) throws SQLException {
        String query = "delete from trainee where id = " + trainee.getId();
        try (PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.executeUpdate();
        }
    }

    public static void deleteTrainees() throws SQLException {
        String query = "delete from trainee";
        try (PreparedStatement stmt = con.prepareStatement(query);) {
            stmt.executeUpdate();
        }
    }

    public static void insertSubject(Subject subject) throws SQLException {
        String query = "insert into subject values (?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setString(2, subject.getName());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                subject.setId(generatedKeys.getInt((1)));
            } else {
                throw new SQLException("Creating Subject failed, no ID obtained.");
            }
        }
    }

    public static Subject getSubjectByIdUsingColNames(int subjectId) throws SQLException {
        String query = "select name from subject where id = " + subjectId;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Subject(subjectId, rs.getString("name"));
            }
            return null;
        }
    }

    public static Subject getSubjectByIdUsingColNumbers(int subjectId) throws SQLException {
        String query = "select name from subject where id = " + subjectId;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Subject(subjectId, rs.getString(1));
            }
            return null;
        }
    }

    public static void deleteSubjects() throws SQLException {
        String query = "delete from subject";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }

    public static void insertSchool(School school) throws SQLException {
        String query = "insert into school values (?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setString(2, school.getName());
            stmt.setInt(3, school.getYear());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                school.setId(generatedKeys.getInt((1)));
            } else {
                throw new SQLException("Creating School failed, no ID obtained.");
            }
        }
    }

    public static School getSchoolByIdUsingColNames(int schoolId) throws SQLException {
        String query = "select name, year from school where id = " + schoolId;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new School(schoolId, rs.getString("name"), rs.getInt("year"));
            }
            return null;
        }
    }

    public static School getSchoolByIdUsingColNumbers(int schoolId) throws SQLException {
        String query = "select name, year from school where id = " + schoolId;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new School(schoolId, rs.getString(1), rs.getInt(2));
            }
            return null;
        }
    }

    public static void deleteSchools() throws SQLException {
        String query = "delete from school";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }

    public static void insertGroup(School school, Group group) throws SQLException {
        String query = "insert into `group` values (?,?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setNull(1, Types.INTEGER);
            stmt.setString(2, group.getName());
            stmt.setString(3, group.getRoom());
            stmt.setInt(4, school.getId());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                group.setId(generatedKeys.getInt((1)));
            } else {
                throw new SQLException("Creating Group failed, no ID obtained.");
            }
        }
    }

    public static School getSchoolByIdWithGroups(int id) throws SQLException {
        School school;
        String query = "select * from school join `group` on schoolid = school.id where school.id = " + id;
        try (PreparedStatement stmt = con.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                school = new School(id, rs.getString("school.name"), rs.getInt("year"));
                school.addGroup(new Group(rs.getInt("group.id"), rs.getString("group.name"),
                        rs.getString("room")));
            }
            else {
                return null;
            }
            while (rs.next()) {
                school.addGroup(new Group(rs.getInt("group.id"), rs.getString("group.name"),
                        rs.getString("room")));
            }
            return school;
        }
    }

    public static List<School> getSchoolsWithGroups() throws SQLException {
        String schoolsQuery = "select * from school join `group` on schoolid = school.id";
        List<School> schools = new ArrayList<>();
        School school;
        try (PreparedStatement stmtSchools = con.prepareStatement(schoolsQuery); ResultSet rs = stmtSchools.executeQuery()) {
            while (rs.next()) {
                school = new School(rs.getInt("school.id"), rs.getString("school.name"), rs.getInt("year"));
                school.addGroup(new Group(rs.getInt("group.id"), rs.getString("group.name"),
                        rs.getString("room")));
                while (rs.next() && rs.getInt("school.id") == school.getId()) {
                    school.addGroup(new Group(rs.getInt("group.id"), rs.getString("group.name"),
                            rs.getString("room")));
                }
                schools.add(school);
                rs.previous();
            }
            return schools;
        }
    }
}
