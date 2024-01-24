package com.poseidon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.poseidon.dto.CommentDTO;

public class CommentDAO extends AbstractDAO {
	public int writeComment(CommentDTO dto) {
		int result = 0;
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "INSERT INTO comment (board_no, ccomment, mno, cip) "
				+ "VALUES (?, ?, (SELECT mno FROM member WHERE mid=?), ?)";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getBoard_no());
			pstmt.setString(2, dto.getComment());
			pstmt.setString(3, dto.getMid());
			pstmt.setString(4, dto.getIp());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		return result;
	}

	public int commentDelete(CommentDTO dto) {
		int result = 0;
		Connection con = db.getConnection();
		PreparedStatement pstmt = null;
		String sql = "UPDATE comment SET cdel='0' "
				+ "WHERE cno=? AND mno=(SELECT mno FROM member WHERE mid=?)";
		
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getCno());
			pstmt.setString(2, dto.getMid());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(null, pstmt, con);
		}
		return result;
	}
}
