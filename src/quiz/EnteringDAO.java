package quiz;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import board.DBUtil;


public class EnteringDAO {

	
	public static boolean insert(QuizVO vo) {
		
//		문제, 정답, 오답1,2,3, 비밀번호가 입력되었나 검사
		boolean flag = true;
		if(vo.getQuiz().length() == 0) {
			flag = false;
			JOptionPane.showMessageDialog(null, "문제를 입력해주세요.");
		}else if(vo.getAnswer().length() == 0) {
			flag = false;
			JOptionPane.showMessageDialog(null, "정답을 입력해주세요.");
		}else if (vo.getAnswer().length() == 0) {
			flag = false;
			JOptionPane.showMessageDialog(null, "오답1을 입력해주세요.");
		}else if (vo.getwrong1().length() == 0) {
			flag = false;
			JOptionPane.showMessageDialog(null, "오답2을 입력해주세요.");
		}else if (vo.getwrong2().length() == 0) {
			flag = false;
			JOptionPane.showMessageDialog(null, "오답3을 입력해주세요.");
		}else if (vo.getwrong3().length() == 0) {
			flag = false;
			JOptionPane.showMessageDialog(null, "비밀번호를 설정해주세요.");
		}
		
//		모두 다 입력되었으면 저장한다.
		if(flag) {

			try {
			Connection conn = DBUtil.getMySQLConnection();
			String sql = "insert into quiz (quiz, answer, wrong1, wrong2, wrong3, "
					+ " quizPassword, userID, userName, nickName) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getQuiz());
			pstmt.setString(2, vo.getAnswer());
			pstmt.setString(3, vo.getwrong1());
			pstmt.setString(4, vo.getwrong2());
			pstmt.setString(5, vo.getwrong3());
			pstmt.setString(6, vo.getquizPassword());
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "문제가 저장되었습니다.");
			DBUtil.close(conn);
			DBUtil.close(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		}
		return flag;
		
	}

}