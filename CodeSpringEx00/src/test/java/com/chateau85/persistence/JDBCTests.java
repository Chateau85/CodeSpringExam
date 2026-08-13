package com.chateau85.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JDBCTests {
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnection() {
		try (Connection con = DriverManager.getConnection(requiredEnvironment("DB_URL"),
				requiredEnvironment("DB_USERNAME"), requiredEnvironment("DB_PASSWORD"))) {
			log.info("{}", con);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	private static String requiredEnvironment(String name) {
		String value = System.getenv(name);
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalStateException(name + " environment variable is required");
		}
		return value;
	}
}
