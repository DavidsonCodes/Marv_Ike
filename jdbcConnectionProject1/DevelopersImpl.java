package org.example.jdbcConnectionProject1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DevelopersImpl implements Developers {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/developer";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "password";

    private static final String DEVELOPERS_TABLE_CREATE_SQL = "CREATE TABLE IF NOT EXISTS developers (" +
            "id INT AUTO_INCREMENT PRIMARY KEY," +
            "name VARCHAR(255)," +
            "age INT," +
            "location VARCHAR(255)," +
            "skill VARCHAR(255))";

    public DevelopersImpl() {
        try {
            initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Error initializing database: " + e.getMessage());
        }
    }

    public void updateTable() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(String.format("INSERT INTO developers (name, age, location, skill) VALUES (?, ?, ?, ?)"))) {
            if (!tableExists(connection)) {
                createTable(connection);
            }
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Please enter developer's name (type 'exit' to stop): ");
                String name = scanner.nextLine().trim();
                if ("exit".equalsIgnoreCase(name)) {
                    break;
                }
                String age = getDeveloperProperty("age", scanner);
                String location = getDeveloperProperty("location", scanner);
                String skill = getDeveloperProperty("skill", scanner);
                if (!isValidInput(name, age, location, skill)) {
                    System.out.println("Invalid input provided. Please try again.");
                    continue;
                }
                statement.setString(1, name);
                statement.setInt(2, Integer.parseInt(age));
                statement.setString(3, location);
                statement.setString(4, skill);
                statement.executeUpdate();
                System.out.println("The Developer details was created successfully!");
            }
        } catch (SQLException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
    }


    private boolean tableExists(Connection connection) {
        String sql = "SHOW TABLES LIKE 'developers'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error checking table existence: " + e.getMessage());
            return false;
        }
    }

    private void createTable(Connection connection) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(DEVELOPERS_TABLE_CREATE_SQL);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    private void initializeDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD)) {
            createTable(connection);
        }
    }

    private void loadFromTextToDB(Connection connection, String filePath) throws IOException, SQLException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 4 && isValidInput(data[0], data[1], data[2], data[3])) {
                        try (PreparedStatement statement = connection.prepareStatement(String.format("INSERT INTO developers (name, age, location, skill) VALUES (?, ?, ?, ?)"))) {
                            statement.setString(1, data[0].trim());
                            statement.setInt(2, Integer.parseInt(data[1].trim()));
                            statement.setString(3, data[2].trim());
                            statement.setString(4, data[3].trim());
                            statement.executeUpdate();
                        } catch (SQLException e) {
                            System.out.println("Error inserting data into the database: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid data in the file: " + line);
                    }
                }
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }

    private boolean isValidInput(String name, String age, String location, String skill) {
        return name != null && age != null && location != null && skill
                != null && !name.isEmpty() && !age.isEmpty() && !location.isEmpty() && !skill.isEmpty();
    }

    private String getDeveloperProperty(String property, Scanner scanner) {
        System.out.println("Please enter developer's " + property + ": ");
        return scanner.nextLine().trim();
    }

    @Override
    public ResultSet loadDevelopers() {
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
             Statement statement = connection.createStatement()) {
            createTable(connection);
            loadFromTextToDB(connection, "C:\\Users\\user\\IdeaProjects\\IngrydTrainingProgram\\src\\main\\java\\org\\example\\jdbcConnectionProject1\\project.txt");
            String selectStatement = String.format("SELECT * FROM developers");
            resultSet = statement.executeQuery(selectStatement);
        } catch (SQLException | IOException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return resultSet;
    }


    public static void main(String[] args) throws SQLException, IOException {
        DevelopersImpl  devs = new DevelopersImpl();
        ResultSet resultSet = devs.loadDevelopers();

        if (resultSet != null) {
            try (resultSet) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getInt("id") + "\t" +
                            resultSet.getString("name") + "\t" +
                            resultSet.getString("age") + "\t" +
                            resultSet.getString("location") + "\t" +
                            resultSet.getString("skill"));
                }
            } catch (SQLException e) {
                System.out.println("Error reading data from ResultSet: " + e.getMessage());
            }
        }

        devs.updateTable();
    }
}




