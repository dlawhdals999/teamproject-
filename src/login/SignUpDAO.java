package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import board.DBUtil;

public class SignUpDAO {

	public static void insert(MemberInfoVO vo) {   
		
		Boolean flag = true;
		
		if(vo.getUserID().length() == 0) {
			JOptionPane.showMessageDialog(null, "아이디가 입력되지 않았습니다.");
			flag = false;
		}else if(vo.getUserPW().length() == 0) {
			JOptionPane.showMessageDialog(null, "비밀번호가 입력되지 않았습니다.");
			flag = false;
		}else if(vo.getUserPWCheck().length() == 0) {
			JOptionPane.showMessageDialog(null, "비밀번호 확인이 입력되지 않았습니다.");
			flag = false;
		}else if(!vo.getUserPW().equals(vo.getUserPWCheck())){
			JOptionPane.showMessageDialog(null, "비밀번호와 비밀번호 확인이 일치하지 않습니다.");
			flag = false;
		}else if(vo.getUserName().length() == 0) {
			JOptionPane.showMessageDialog(null, "이름을 입력하지 않았습니다.");
			flag = false;
		}else if(vo.getNickName().length() == 0) {
			JOptionPane.showMessageDialog(null, "닉네임을 입력하지 않았습니다.");
			flag = false;
		}else if(!vo.getGflag()){
			JOptionPane.showMessageDialog(null, "성별을 선택해주세요");
			flag = false;
		}else {
			try {
				Connection conn = DBUtil.getMySQLConnection();
				String sql = "SELECT * FROM memberINfo where userID = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getUserID());
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					JOptionPane.showMessageDialog(null, "아이디가 중복되었습니다. 다른 아이디를 사용해주세요");
					flag = false;
				}
				sql = "SELECT * FROM memberINfo where nickName = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getNickName());
				rs = pstmt.executeQuery();
				if (rs.next()) {
					JOptionPane.showMessageDialog(null, "닉네임이 중복되었습니다. 다른 닉네임을 사용해주세요");
					flag = false;
				}
				
				DBUtil.close(conn);
				DBUtil.close(pstmt);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		if(flag) {
			try {
				Connection conn = DBUtil.getMySQLConnection();
				String sql = "insert into memberINfo (userID, userPW, userName, nickName, gender) values (?, ?, ?, ?, ?)";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, vo.getUserID());
				pstmt.setString(2, vo.getUserPW());
				pstmt.setString(3, vo.getUserName());
				pstmt.setString(4, vo.getNickName());
				pstmt.setBoolean(5, vo.getGender());
				pstmt.executeUpdate();
				DBUtil.close(conn);
				DBUtil.close(pstmt);
				JOptionPane.showMessageDialog(null, "축하합니다. 회원가입이 완료되었습니다.");
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
	}
	
}