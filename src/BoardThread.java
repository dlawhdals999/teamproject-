package teamProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//	BoardMain클래스 접근 시(프로그램 실행 시) QuizVO 객체를 DB에서 가지고와 모두 클라이언트로 보내준다.
public class BoardThread extends Thread{
	Socket socket;
	
	public BoardThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// DB의 데이터를 객체로 가지고온다.
			Connection conn = DBUtil.getMySQLConnection();
			String sql = "select * from quiz";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs =  pstmt.executeQuery();
			while(rs.next()) {
				
				ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				QuizVO vo = new QuizVO();
				vo.setQuiz(rs.getString("quiz"));
				vo.setAnswer(rs.getString("answer"));
				vo.setwrong1(rs.getString("wrong1"));
				vo.setwrong2(rs.getString("wrong2"));
				vo.setwrong3(rs.getString("wrong3"));
				vo.setquizPassword(rs.getString("quizPassword"));
				vo.setIdx(rs.getInt("idx"));
				vo.setWriteData(rs.getTimestamp("writedate"));
				outputStream.writeObject(vo);
				outputStream.flush();
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}//END RUN	

}
