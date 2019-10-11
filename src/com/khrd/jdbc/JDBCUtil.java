package com.khrd.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// memberDAO finally부분에서 pstmt.close();을 try-catch문 하면 되는데, 이 try-catch문이 conn이나 pstmt나 내용이 같아. 그래서 그걸 한번에 묶는 클래스를 만들어서 memberDAO에서 호출할 것임.
public class JDBCUtil {
	public static void close(Connection conn) { // memberDAO에서 클래스 생성하지 않고 호출하려고 static붙인 것. 생성 안해도 된다고 아무데나 붙이면 안되고 정말 자주 쓸 것 같은 애들한테만 붙여주기
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void close(PreparedStatement pstmt) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void close(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Statement stmt) {
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
