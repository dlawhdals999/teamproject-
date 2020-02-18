package login;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import board.BoardMain;

public class LoginMain extends JFrame implements ActionListener{
	

	JTextField IDField = new JTextField(20);
	JPasswordField PWField = new JPasswordField(10);

	
	public LoginMain() {

	
		setBounds(100, 100, 450, 300);
		
		LoginPanel login = new LoginPanel(new ImageIcon(".\\src\\images\\login.png").getImage());
		setSize(new Dimension(922, 530));
		setPreferredSize(new Dimension(login.getDim()));
		
		getContentPane().add(login, BorderLayout.SOUTH);
		

		IDField = new JTextField();
		IDField.setBounds(575, 171, 246, 44);
		IDField.setBorder(null);
		login.add(IDField);
		IDField.setColumns(10);
		
		PWField = new JPasswordField();
		PWField.setBounds(575, 247, 246, 49);
		PWField.setBorder(null);
		login.add(PWField);
		
		JButton loginButton = new JButton("로그인");
		loginButton.setBounds(451, 329, 149, 72);
		loginButton.setFont(new Font("D2Coding", Font.BOLD, 30));
		loginButton.setBackground(new Color(15844367));
		login.add(loginButton);
		loginButton.addActionListener(this);
		
		JButton cancelButton = new JButton("취소");
		cancelButton.setFont(new Font("D2Coding", Font.BOLD, 30));
		cancelButton.setBackground(new Color(241, 196, 15));
		cancelButton.setBounds(656, 329, 149, 72);
		login.add(cancelButton);
		cancelButton.addActionListener(this);
	
		setSize(LoginPanel.getDim());
		setPreferredSize(LoginPanel.getDim()); 
		pack();
		setVisible(true);

	}
	public static void main(String[] args) {
		LoginMain window = new LoginMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String userID = null;
		String userPW= null;
		
		switch (e.getActionCommand()) {
		case "로그인":
			IDField.requestFocus();
			userID = IDField.getText().trim();
			userPW = PWField.getText().trim();
			if(userID.equals("")) {
				JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
			}else if(userPW.equals("")){
				JOptionPane.showMessageDialog(null, "비밀번호를 입력해주세요");
			}else{
				MemberInfoVO vo = LoginDAO.login(userID,userPW);
				if(vo == null) {
					IDField.setText("");
					PWField.setText("");
					IDField.requestFocus();
				}else {
					this.setVisible(false);
					JOptionPane.showMessageDialog(null, "어서오세요, 반갑습니다!");
					BoardMain.getInputButton().setEnabled(true);
					BoardMain.getUpdateButton().setEnabled(true);
					BoardMain.getDeleteButton().setEnabled(true);
					BoardMain.getJoinButton().setEnabled(false);
					BoardMain.getLoginButton().setEnabled(true);
					BoardMain.getLoginButton().setText("로그아웃");
				}
			}
			break;
		case "취소":
			this.setVisible(false);
			BoardMain.getJoinButton().setEnabled(true);
			BoardMain.getLoginButton().setEnabled(true);
			break;
			
		}
		
	}
	

}