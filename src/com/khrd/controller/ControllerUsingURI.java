package com.khrd.controller;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerUsingURI extends HttpServlet {
	private HashMap<String, CommandHandler> commandHandlerMap = new HashMap<>();
	
	// commandHandler : 형태를 정하는 인터페이스. 클래스의 부모임. 이 부모를 맵에 담도록 처리한 것. 
	// 맵에 담길때 simple.do에 해당하는 simpleHandler클래스, calhandler 등을 new해서 생성해서 맵에 들어가게 함.
	// init은 맵에 push하는 역할이 다임. 즉 맵에 담는 것이 다임.
	// 키는 command 즉 /simple.do고 값은 handler 즉  simpleHandler클래스를 말함
	
	// 
	
	@Override
	public void init() throws ServletException {
		// configFile = /WEB-INF/commandHandler.properties
		String configFile = getInitParameter("configFile"); 
		
		// properties파일의 절대 주소를 가져옴.
		String configFilePath = getServletContext().getRealPath(configFile);

		Properties prop = new Properties();
		
		// commandHandler.properties 파일안의 내용을 읽어들임
		try(FileReader fis = new FileReader(configFilePath)) {
			prop.load(fis); // 담음
		} catch (Exception e) {
			throw new ServletException(e);
		}
		Iterator keyIter = prop.keySet().iterator();
		while(keyIter.hasNext()) { // commandHandler.properties에서 한줄씩 키를 가져옴. = 앞에 있는 값을 가져옴. /simple.do = com.khrd.handler.SimpleHandler에서 = 앞에 있는 것
			String command = (String) keyIter.next(); // simple.do
			String handlerClassName = prop.getProperty(command); // handlerClassName = com.khrd.handler.SimpleHandler // = 뒤에 있는 것 -> /simple.do = com.khrd.handler.SimpleHandler 여기서 = 뒤에 있는 것
			try {
				// handlerClassName는 단순한 String이야. String인채로 맵에 담는 것이 아니라 클래스화 즉 인스턴스화 해서 넣어야해. new해서 살아있는 것을 넣는다고 보면 됨.
				Class<?> handlerClass = Class.forName(handlerClassName);
				CommandHandler handler = (CommandHandler) handlerClass.newInstance(); // new인스턴스화 함.
				// 위에 두줄. 
				// 즉 handler = new SimpleHandler(); 한 것임.
				
				commandHandlerMap.put(command, handler); // = 앞에 있는 애 즉 /simple.do을 맵에 담음.
				// 이렇게 되면 commandHandler.properties에 /simple.do = com.khrd.handler.SimpleHandler 이런식으로 키와 클래스를 넣어주면 맵에 자동으로 넣어지는 것.
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String command = request.getRequestURI(); // /chapter18/simple.do 즉 도메인 뒤에 부분이 옴.
		if(command.indexOf(request.getContextPath()) == 0) { // command를 뽑아내는 부분.
			command = command.substring(request.getContextPath().length()); // ContextPath가 주는 값 즉 request.getContextPath()가 프로젝트 이름임. /chapter18
			// 그래서 command 에는 /simple.do 가 들어가게됨.
		}
		
		// command에 해당하는 class를 가져옴.
		CommandHandler handler = commandHandlerMap.get(command); // commandHandlerMap은 init에서 만들어 둔 맵. /simple.do를 클래스에 넣어두는 것인데, 넣어둔 값 즉 command가 있으면 그 값을, 없으면 null을 리턴함.
		if(handler == null) {
			handler = new NullHandler();
		}
		
		String viewPage = null; // 화면에 보일 jsp파일
		try {
			viewPage = handler.process(request, response); // handler가 들고 있는 process호출. 이동할 jsp파일을 받음
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		if(viewPage != null) { // forward처리 // 이동할 파일이 아니면?? 
			RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
			dispatcher.forward(request, response); //forward되도록
		}
	}
}
