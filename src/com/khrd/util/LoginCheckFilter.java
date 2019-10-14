package com.khrd.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCheckFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		// req.getSess // insertHandler에서는 HttpServletRequest를 사용하고 여기서는 ServletRequest야. ServletRequest이 HttpServletRequest의 부모야
		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute("Auth") == null) { // 로그인 안된 상태 -> 로그인 화면으로 이동
			// session에서 강제로 이동되게 하는 것은 Redirect와 forward가 있는데, 포워드 쓰면 상단 주소에 add.do의 add가 남게 되어서 여기선 포워드 쓰면 안됨. 
			HttpServletResponse response = (HttpServletResponse) res;
			response.sendRedirect(request.getContextPath() + "/login.do");
		}else { // 로그인된 상태 -> 진행되도록
			chain.doFilter(req, res);
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
