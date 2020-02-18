package quiz;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class EnteringMain extends JFrame implements ActionListener{
	
	JPanel dataPanel = new JPanel(new BorderLayout());
	
//	레이블
	JLabel quizLabel = new JLabel("문제");
	JLabel answerLabel = new JLabel("정답");
	JLabel wrong1Label = new JLabel("오답 1");
	JLabel wrong2Label = new JLabel("오답 2");
	JLabel wrong3Label = new JLabel("오답 3");
	JLabel quizPasswordLabel = new JLabel("비밀번호");
	JLabel password2Label = new JLabel("비밀번호 확인");
	
//	텍스트 필드
	JTextArea quizField = new JTextArea();;
	JTextField answerField = new JTextField();
	JTextField wrong1Field = new JTextField();
	JTextField wrong2Field = new JTextField();
	JTextField wrong3Field = new JTextField();
	JTextField quizPasswordField = new JPasswordField();
	JTextField password2Field = new JPasswordField();
	
	
//	버튼 필드	
	JButton saveButton = new JButton("저장하기");
	JButton cancelButton = new JButton("나가기");
	
	public EnteringMain() {
		setTitle("문제 입력");
		setBounds(1100, 100, 700, 600);
		getContentPane().setBackground(Color.yellow);
		setLayout(null);
		
		
//		문제 라벨, 필드
		quizField.setLineWrap(true);				// 텍스트 에리아에서 줄바꿈해주는 코드
		quizLabel.setBounds(40, 35, 100, 30);
		quizField.setBounds(120, 38, 480, 100);
		add(quizLabel);
		add(quizField);
//		정답 라벨, 필드
		answerLabel.setBounds(40, 185, 100, 30);
		answerField.setBounds(120, 185, 450, 40);
		add(answerLabel);
		add(answerField);
//		오답1 라벨, 필드
		wrong1Label.setBounds(40, 260, 100, 30);
		wrong1Field.setBounds(120, 260, 450, 40);
		add(wrong1Label);
		add(wrong1Field);
//		오답2 라벨, 필드
		wrong2Label.setBounds(40, 305, 100, 30);
		wrong2Field.setBounds(120, 305, 450, 40);
		add(wrong2Label);
		add(wrong2Field);
//		오답3 라벨, 필드
		wrong3Label.setBounds(40, 350, 100, 30);
		wrong3Field.setBounds(120, 350, 450, 40);
		add(wrong3Label);
		add(wrong3Field);
//		비밀번호 라벨, 필드		
		quizPasswordLabel.setBounds(40, 420, 100, 30);
		quizPasswordField.setBounds(120, 420, 100, 40);
		quizPasswordField.setFont(new Font("돋움", Font.BOLD, 50));
		add(quizPasswordLabel);
		add(quizPasswordField);
//		비밀번호 확인 라벨, 필드
		password2Label.setBounds(290, 420, 100, 30);
		password2Field.setBounds(400, 420, 100, 40);
		password2Field.setFont(new Font("돋움", Font.BOLD, 50));
		
		add(password2Label);
		add(password2Field);
//		버튼 
		saveButton.setBounds(235, 480, 100, 50);
		cancelButton.setBounds(355, 480, 100, 50);
		saveButton.setBackground(Color.black);
		saveButton.setForeground(Color.white);
		cancelButton.setBackground(Color.black);
		cancelButton.setForeground(Color.white);
		saveButton.setFont(new Font("돋움", Font.BOLD, 15 ));
		cancelButton.setFont(new Font("돋움", Font.BOLD, 15 ));
		add(saveButton);
		add(cancelButton);
		
//		버튼에 엑션리스너를 걸어줌
		saveButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String quiz = "", answer = "", wrong1 = "", wrong2 = "", wrong3 = "", quizPassword = "", password2 = "";
		
		switch(e.getActionCommand()) {
		case "저장하기" :
			
//			텍스트 필드들에서 입력받은 데이터를 각 저장공간에 저장시켜준다.
			quiz = quizField.getText().trim();
			StringBuffer quiz1 = new StringBuffer(quiz);
			for (int i = 1; i <= (int)quiz.length() / 20; i++) {
				if(i == 1) {
					quiz1.insert(20 * i, "<br>");
				}else {
					quiz1.insert(20 * i + 4 * (i-1), "<br>");
					
				}
			}
			quiz = quiz1+"";
			answer = answerField.getText().trim();
			wrong1 = wrong1Field.getText().trim();
			wrong2 = wrong2Field.getText().trim();
			wrong3 = wrong3Field.getText().trim();
			quizPassword = quizPasswordField.getText().trim();
			password2 = password2Field.getText().trim();
			
//			비밀번호 확인
			if(quizPassword.equals(password2)) {
				
			
			QuizVO vo = new QuizVO();
			vo.setQuiz(quiz);
			vo.setAnswer(answer);
			vo.setwrong1(wrong1);
			vo.setwrong2(wrong2);
			vo.setwrong3(wrong3);
			vo.setquizPassword(quizPassword);
			
//			데이터베이스에 저장해준다.
			boolean flag = EnteringDAO.insert(vo);
			
//			저장된 후에 텍스트 필드들에 있는 글들을 다 지워준다.
//			flag값은 EnteringDAO에서 얻어온다.
//			빈칸을 1개라도 입력하지 않으면 저장이 안되는데, 만약 저장해달라고 메세지를 띄우고
//			이제까지 쓴 글들이 다 지워지면 빡치니까, flag로 다 저장됬을때만 true를 반환해서
//			쓴 글들을 지워준다.
			if(flag) {
				quizField.setText("");
				answerField.setText("");
				wrong1Field.setText("");
				wrong2Field.setText("");
				wrong3Field.setText("");
				quizPasswordField.setText("");
				password2Field.setText("");
			}
			
//			퀴즈필드에 포커스를 옮겨준다.
			quizField.requestFocus();
			}
			else {
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 확인해주세요");
			}
			
			break;
		case "나가기" :
			this.setVisible(false);
			break;
		}
	}
	
	
}

