package com.khrd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.khrd.dto.Article;
import com.khrd.jdbc.JDBCUtil;

public class ArticleDAO {
	private static final ArticleDAO dao = new ArticleDAO();
	
	public static ArticleDAO getInstance() {
		return dao;
	}
	
	private ArticleDAO() {
		
	}
	
	public int insertArticle(Connection conn, Article ar) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "insert into article values(null, ?, ?, ?, now(), now(), 0)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, ar.getWriter_id());
			pstmt.setString(2, ar.getWriter_name());
			pstmt.setString(3, ar.getTitle());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(pstmt);
		}
		
		return -1;
	}
	
	public int insertContent(Connection conn, String content) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "insert into article_content values(last_insert_id(), ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, content);
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(pstmt);
		}
		return -1;
	}
	
	public List<Article> selectArticleList(Connection conn){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from article order by article_no desc"; // 최근 글이 위로 오도록 정렬
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<Article> list = new ArrayList<>();
			while(rs.next()) {
				Article a = new Article(rs.getInt("article_no"), 
										rs.getString("writer_id"), 
										rs.getString("writer_name"), 
										rs.getString("title"), 
										rs.getTimestamp("regdate"), // getDate는 자바 SQL // 우리는 자바 유틸것을 쓰니까 getTimestamp 사용.
										rs.getTimestamp("moddate"), 
										rs.getInt("read_cnt"), 
										null);
				list.add(a);
			}
			return list;
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(conn);
		}
		return null;
	}
	
	public Article selectByNo(Connection conn, int article_no) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "select * from article as a join article_content as ac on a.article_no = ac.article_no where a.article_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, article_no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Article article = new Article(rs.getInt("article_no"),
											  rs.getString("writer_id"),
											  rs.getString("writer_name"),
											  rs.getString("title"),
											  rs.getTimestamp("regdate"),
											  rs.getTimestamp("moddate"),
											  rs.getInt("read_cnt"),
											  rs.getString("content"));
				return article;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(rs);
			JDBCUtil.close(pstmt);
		}
		
		return null; 
	}
	
	
	public int deleteArticle(Connection conn, int article_no) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "delete from article where article_no =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, article_no);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(pstmt);
		}
		return -1;
	}
	
	public int deleteContent(Connection conn, int article_no) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "delete from article_content where article_no =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, article_no);
			return pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(pstmt);
		}
		return -1;
	}
	
	public int updateArticle(Connection conn, Article article) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "update article set title=? where article_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getTitle());
			pstmt.setInt(2, article.getArticle_no());

			return pstmt.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(pstmt);
		}
		return -1;
	}
	
	public int updateContent(Connection conn, Article article) {
		PreparedStatement pstmt = null;
		
		try {
			String sql = "update article_content set content=? where article_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, article.getContent());
			pstmt.setInt(2, article.getArticle_no());

			return pstmt.executeUpdate();

		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtil.close(pstmt);
		}
		return -1;
	}
}
