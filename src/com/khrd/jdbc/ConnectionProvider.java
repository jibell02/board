package com.khrd.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionProvider {
	public static Connection getConnection() throws SQLException{
		String jdbcDriver = "jdbc:apache:commons:dbcp:board"; // 커넥션 풀의 이름.
		return DriverManager.getConnection(jdbcDriver); // 커넥션 풀에서 커넥션을 끄집어냄.
	}
}
