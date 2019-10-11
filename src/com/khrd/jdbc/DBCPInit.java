package com.khrd.jdbc;

import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

// 이렇게 만든 것을 web.xml에 등록하면 실행될 때 알아서 이 파일 수행함.
public class DBCPInit extends HttpServlet{
	
	// init중에 매개변수 없는애로 
	@Override
	public void init() throws ServletException {
		loadJDBCDriver();
		initConnectionPool();
	}
	
	private void loadJDBCDriver() {
		try { // MySQLDriverLoader.java init부분 내용 가져옴.
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void initConnectionPool() {
		try { // MySQLDriverLoader.java getConnection()부분. 이제 MySQLDriverLoader.java파일 안 씀
			String url = "jdbc:mysql://localhost:3306/board";
	        String userId = "root";
	        String userPw = "rootroot";
	         
	        ConnectionFactory connFactory = new DriverManagerConnectionFactory(url, userId, userPw);
	        PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory, null);
	         
	        // 커넥션을 검사할 때 사용할 쿼리를 설정한다.
	        // bad 커넥션을 dbcp의 connection pool에선 여전히 가지고 있는 상태일 때,
	        // DB관련 프로그램이 호출되면 커넥션 관련 에러가 발생하게 된다.
	        // java에서 DB를 사용하기 전에 해당 connection이 정상인지 검사를 하도록 하는 것
	        // 이것이 아래의 옵션이다.
	        // 우리가 따로 커리를 연결할 수 있지만, select 1은 Microsoft SQL Server에서 권장하는 방법이다.
	        poolableConnFactory.setValidationQuery("select 1"); // 스페이스 해야함.
	        // pool에서 conn을 사용하면 반드시 반납해야해.
	        // 반납을 안하게 되면 bad커넥션이 됨.
	        // bad커넥션이 많아지면 사용할 수 있는 conn이 줄어들어. 그래서 위에처럼 설정을 하면 쓸 수 있는 커넥션이 있는지, 쓸 수 없는 것은 있는지, 몇개인지를 체크해서 빼줌.
	        // 이 아래부분이 그것을 검사하는 방법
	        
	        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
	        poolConfig.setTimeBetweenEvictionRunsMillis(1000L*60L*5); // 1분 * 5 = 5분 주기로 검사를 시킨다.
	        poolConfig.setTestWhileIdle(true); // true로 되어있어야 검사함. 
	        poolConfig.setMinIdle(4); // 사용가능한 커넥션이 최소 4개를 보장시켜라
	        poolConfig.setMaxTotal(50); // 최대 커넥션은 50개로 해라. bad커넥션이든 사용가능한 커넥션이든
	        // 이 조건들을 갖추기 위해 노력함.
	        
	        GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnFactory, poolConfig);
	        poolableConnFactory.setPool(connectionPool);
	        
	        Class.forName("org.apache.commons.dbcp2.PoolingDriver");
	        
	        PoolingDriver driver = (PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
	        driver.registerPool("board", connectionPool);
			// jsp_study : 내가 만든 커넥션 pool의 이름. 보통 DB이름이랑 같은것을 씀. 덜 헷갈리게 하려고. 그래서 DB이름(jdbc:mysql://localhost:3306/jsp_study에서 jsp_study)을 바꾸면 위에 pool이름도 바꿔주는 것이 좋음.
	         
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
