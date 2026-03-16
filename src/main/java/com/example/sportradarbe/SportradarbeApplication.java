package com.example.sportradarbe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SportradarbeApplication {

	public static void main(String[] args) {
		ensureDatabaseExists();
		SpringApplication.run(SportradarbeApplication.class, args);
	}

	private static void ensureDatabaseExists() {
		String host = envOrDefault("DB_HOST", "localhost");
		String port = envOrDefault("DB_PORT", "5432");
		String dbName = envOrDefault("DB_NAME", "sportradar");
		String user = envOrDefault("DB_USER", "postgres");
		String password = envOrDefault("DB_PASSWORD", "postgres");

		if (!dbName.matches("[a-zA-Z0-9_]+")) {
			throw new IllegalArgumentException("DB_NAME must contain only letters, numbers, or underscore.");
		}

		String adminJdbcUrl = "jdbc:postgresql://" + host + ":" + port + "/postgres";

		try (Connection connection = DriverManager.getConnection(adminJdbcUrl, user, password)) {
			if (databaseMissing(connection, dbName)) {
				try (Statement statement = connection.createStatement()) {
					statement.execute("CREATE DATABASE \"" + dbName + "\"");
				}
			}
		} catch (SQLException ex) {
			throw new IllegalStateException("Unable to ensure database exists: " + dbName, ex);
		}
	}

	private static boolean databaseMissing(Connection connection, String dbName) throws SQLException {
		String sql = "SELECT 1 FROM pg_database WHERE datname = ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, dbName);
			try (ResultSet resultSet = statement.executeQuery()) {
				return !resultSet.next();
			}
		}
	}

	private static String envOrDefault(String key, String defaultValue) {
		String value = System.getenv(key);
		return (value == null || value.isBlank()) ? defaultValue : value;
	}

}
