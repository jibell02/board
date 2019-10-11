package com.khrd.handler;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.khrd.controller.CommandHandler;
import com.khrd.dao.MemberDAO;
import com.khrd.dto.Member;
import com.khrd.jdbc.ConnectionProvider;
import com.khrd.jdbc.JDBCUtil;

public class ChangePwdHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("get")) {
			return "/WEB-INF/view/changePwdForm.jsp"; 
		}else if(request.getMethod().equalsIgnoreCase("post")) {
			
			String id = request.getParameter("memberid");
			String password = request.getParameter("password");
			String newPassword = request.getParameter("newPassword");
			
			Connection conn = null;
			try {
				conn = ConnectionProvider.getConnection();
				MemberDAO dao = MemberDAO.getInstance();
				Member m = dao.selectIdAndPw(conn, id, password);
				
				if(m == null) {
					request.setAttribute("notMatch", true);
					return "/WEB-INF/view/changePwdForm.jsp";
				}
				
				
				Member member = new Member(id, m.getName(), newPassword, null); 
				dao.update(conn, member);
				
				return "/WEB-INF/view/changePwdSuccess.jsp"; 
			}catch (Exception e) {
				e.printStackTrace();
			}finally {
				JDBCUtil.close(conn);
			}
		}	
		return null;
	}
	
	

}
