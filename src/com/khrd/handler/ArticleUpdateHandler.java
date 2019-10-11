package com.khrd.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khrd.controller.CommandHandler;
import com.khrd.dao.ArticleDAO;
import com.khrd.dto.Article;
import com.khrd.jdbc.ConnectionProvider;
import com.khrd.jdbc.JDBCUtil;

public class ArticleUpdateHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("get")) { // articleRead에서 수정을 눌렀을 때 get으로 넘어옴.
			String sNo = request.getParameter("no"); // 넘어오는 곳(articleRead.jsp에서 [게시글 수정]부분에서 ?no=로 넘어오기 때문에 no를 받아와야함.
			int no = Integer.parseInt(sNo);
			request.setAttribute("no", no);
			
			Connection conn = null;
			
			try {
				conn = ConnectionProvider.getConnection();
				ArticleDAO dao = ArticleDAO.getInstance();
				Article article = dao.selectByNo(conn, no);
				
				request.setAttribute("article", article);
				
				return "/WEB-INF/view/articleUpdateForm.jsp"; // form으로 가서 value값을 줌 -> 수정버튼 눌렀을 때 게시글에 값이 뜨도록 함.
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				JDBCUtil.close(conn);
			}
			
		}else if(request.getMethod().equalsIgnoreCase("post")) { // updateForm에서 수정 버튼 눌렀을 때 이루어지는 부분.
			request.setCharacterEncoding("utf-8");
			
			String sNo = request.getParameter("article_no");
			int no = Integer.parseInt(sNo);
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			Connection conn = null;
			
			try {
				conn = ConnectionProvider.getConnection();
				conn.setAutoCommit(false);
				ArticleDAO dao = ArticleDAO.getInstance();
				
				Article article = new Article(no, null, null, title, null, null, 0, content);
				
				dao.updateArticle(conn, article);
				dao.updateContent(conn, article);
				
				conn.commit();
				
				response.sendRedirect(request.getContextPath() + "/article/list.do");
				return null;
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();
			}finally {
				JDBCUtil.close(conn);
			}
		
		}
		return null;
	}

}
