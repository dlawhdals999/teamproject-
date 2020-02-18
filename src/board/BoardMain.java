package board;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Connection;

import login.LoginMain;
import login.SignUpMain;
import quiz.EnteringMain;
import quiz.QuizDAO;
import quiz.QuizMain;
import quiz.QuizVO;

public class BoardMain extends JFrame implements ActionListener{
	
	
	static JButton solveButton = new JButton("문제풀기");
	static JButton inputButton = new JButton("문제입력");
	static JButton updateButton = new JButton("문제수정");
	static JButton deleteButton = new JButton("문제삭제");
	static JButton chatButton = new JButton("채팅창");
	static JButton loginButton = new JButton("로그인");
	static JButton joinButton = new JButton("회원가입");
	public BoardMain() {
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainPanel MainPanel = new MainPanel(new ImageIcon(".\\src\\images\\main.png").getImage());
		getContentPane().add(MainPanel);
		setLayout(null);
		
		String[] columnNames = { "번호", "문제", "작성자", "작성일" , "정답률" ,"조회수"};
		DefaultTableModel model = new DefaultTableModel(columnNames,0);
		JTable table = new JTable(model);
		JScrollPane sc = new JScrollPane(table);
		sc.setBounds(150,132,684,283);
		MainPanel.add(sc);
		
		
		chatButton.setBorder(null);
		chatButton.setBounds(457, 537, 146, 88);
		chatButton.setBackground(new Color(15248986));
		chatButton.setForeground(new Color(9803));
		chatButton.setFont(new Font("굴림", Font.BOLD, 30));
		MainPanel.add(chatButton);
		chatButton.addActionListener(this);
		
		solveButton.setForeground(new Color(15248986));
		solveButton.setFont(new Font("굴림", Font.BOLD, 15));
		solveButton.setBorder(null);
		solveButton.setBackground(new Color(9803));
		solveButton.setBounds(499, 436, 72, 44);
		MainPanel.add(solveButton);
		solveButton.addActionListener(this);
		
		inputButton.setForeground(new Color(15248986));
		inputButton.setFont(new Font("굴림", Font.BOLD, 15));
		inputButton.setBorder(null);
		inputButton.setBackground(new Color(9803));
		inputButton.setBounds(583, 436, 72, 44);
		MainPanel.add(inputButton);
		inputButton.addActionListener(this);
		
		updateButton.setForeground(new Color(15248986));
		updateButton.setFont(new Font("굴림", Font.BOLD, 15));
		updateButton.setBorder(null);
		updateButton.setBackground(new Color(9803));
		updateButton.setBounds(667, 436, 72, 44);
		MainPanel.add(updateButton);
		updateButton.addActionListener(this);
		
		deleteButton.setForeground(new Color(232, 174, 90));
		deleteButton.setFont(new Font("굴림", Font.BOLD, 15));
		deleteButton.setBorder(null);
		deleteButton.setBackground(new Color(0, 38, 75));
		deleteButton.setBounds(752, 436, 72, 44);
		MainPanel.add(deleteButton);
		deleteButton.addActionListener(this);
		
		loginButton.setBorder(null);
		loginButton.setBounds(458, 540, 146, 88);
		loginButton.setBackground(new Color(15248986));
		loginButton.setForeground(new Color(9803));
		loginButton.setBounds(627, 537, 146, 88);
		loginButton.setFont(new Font("굴림", Font.BOLD, 30));
		MainPanel.add(loginButton);
		loginButton.addActionListener(this);
		
		joinButton.setBorder(null);
		joinButton.setBounds(795, 537, 146, 88);
		joinButton.setBackground(new Color(15248986));
		joinButton.setForeground(new Color(9803));
		joinButton.setFont(new Font("굴림", Font.BOLD, 30));
		MainPanel.add(joinButton);
		joinButton.addActionListener(this);
		
		setSize(MainPanel.getDim());
		setPreferredSize(MainPanel.getDim()); 
		setVisible(true);
		
		pack();
	}
		
	public static void main(String[] args) {
		
		BoardMain window = new BoardMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case "문제풀기":
			QuizMain quizmain = new QuizMain();
			quizmain.setVisible(true);
			break;
		case "문제입력":
			EnteringMain entering = new EnteringMain();
			entering.setVisible(true);
			break;
		case "문제수정":
			JOptionPane.showMessageDialog(null, "수정(아직안함)");
			break;
		case "문제삭제":
			JOptionPane.showMessageDialog(null, "삭제(아직안함)");
			break;
		case "채팅창":
			JOptionPane.showMessageDialog(null, "채팅창(아직안함)");
			break;
		case "로그인":
			LoginMain loginMain = new LoginMain();
			loginMain.setVisible(true);
			getJoinButton().setEnabled(false);
			getLoginButton().setEnabled(false);
			break;
		case "회원가입":
			SignUpMain signUpMain = new SignUpMain();
			signUpMain.setVisible(true);
			break;
		case "로그아웃":
			int logout = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
			if(logout == 0) {
				getInputButton().setEnabled(false);
				getUpdateButton().setEnabled(false);
				getDeleteButton().setEnabled(false);
				getJoinButton().setEnabled(true);
				getLoginButton().setText("로그인");	
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
			}
			break;
		}
	}

public JButton getChatButton() {
	return chatButton;
}

public void setChatButton(JButton chatButton) {
	this.chatButton = chatButton;
}

public JButton getSolveButton() {
	return solveButton;
}

public void setSolveButton(JButton solveButton) {
	this.solveButton = solveButton;
}

public static JButton getInputButton() {
	return inputButton;
}

public void setInputButton(JButton inputButton) {
	this.inputButton = inputButton;
}

public static JButton getUpdateButton() {
	return updateButton;
}

public void setUpdateButton(JButton updateButton) {
	this.updateButton = updateButton;
}

public static JButton getDeleteButton() {
	return deleteButton;
}

public void setDeleteButton(JButton deleteButton) {
	this.deleteButton = deleteButton;
}

public static JButton getLoginButton() {
	return loginButton;
}

public void setLoginButton(JButton loginButton) {
	this.loginButton = loginButton;
}

public static JButton getJoinButton() {
	return joinButton;
}

public void setJoinButton(JButton joinButton) {
	this.joinButton = joinButton;
}
}


