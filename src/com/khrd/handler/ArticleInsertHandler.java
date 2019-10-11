package com.khrd.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

import com.khrd.controller.CommandHandler;
import com.khrd.dao.ArticleDAO;
import com.khrd.dao.MemberDAO;
import com.khrd.dto.Article;
import com.khrd.dto.Member;
import com.khrd.jdbc.ConnectionProvider;
import com.khrd.jdbc.JDBCUtil;

public class ArticleInsertHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if(request.getMethod().equalsIgnoreCase("get")) {
			return "/WEB-INF/view/articleInsertForm.jsp";
		}else if(request.getMethod().equalsIgnoreCase("post")) {
			request.setCharacterEncoding("utf-8");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false); // DB에 바로 반영하지 말아라.
				ArticleDAO dao = ArticleDAO.getInstance();
				MemberDAO mDao = MemberDAO.getInstance();
				
				HttpSession session = request.getSession();
				String writer_id = (String) session.getAttribute("Auth");
				
				Member member = mDao.selectById(conn, writer_id); // writer_name을 얻기 위해 id값을 가져옴.
				
				
				Article ar = new Article(0, writer_id, member.getName(), title, null, null, 0, content); // insertArticle 부분에 어차피 아이디, 이름, 제목만 필요해. 그래서 굳이 regdate등 넣을 필요 없어. 안쓰는 것은 string은 null, int는 0으로 써주면 됨. 
				dao.insertArticle(conn, ar);
				dao.insertContent(conn, content);
				
				conn.commit(); // 값을 변경하라.
				
				//서버단안에서(자바에서) /가 프로젝트 이름을 대신함.
//				return "/WEB-INF/view/articleList.jsp";
//				return "list.do";
				
				response.sendRedirect(request.getContextPath() + "/article/list.do"); // 이주소로 바꿔주세요 라고 브라우저에 요청
				
				return null; // redirect할 때는 return을 null로 해야함. // 리턴이 널로 되면 ControllerUsingURI에서 포워드가 실행 안됨.
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback(); // DB 원상복귀해라.
			}finally {
				JDBCUtil.close(conn);
			}
		}
		return null;
	}
}
