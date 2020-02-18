package teamProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BoardMain extends JFrame implements ActionListener{
	
	String[] columnNames = { "번호", "문제", "작성자", "작성일" , "정답률" ,"조회수"};
	DefaultTableModel model = new DefaultTableModel(columnNames,0);
	JTable table = new JTable(model);
	Socket socket;
	PrintWriter printWriter;
	Scanner sc;
	String msg="";
	ArrayList<QuizVO> list = new ArrayList<QuizVO>();
	
//	JPanel titlePanel = new JPanel(new BorderLayout());
	JPanel tablePanel = new JPanel(new BorderLayout());
	JPanel restPanel1 = new JPanel();
	JPanel restPanel2 = new JPanel();
	
	
	JPanel buttonPanel = new JPanel(new GridLayout(2,1));
	JPanel buttonPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
	JPanel buttonPanel2 = new JPanel();
	
	JLabel mainlabel = new JLabel("QUIZ Board");
	
	static JButton inputButton = new JButton("입력");
	JButton solveButton = new JButton("문제풀기");
	static JButton updateButton = new JButton("수정");
	static JButton deleteButton = new JButton("삭제");
	static JButton loginButton = new JButton("로그인");
	JButton joinButton = new JButton("회원가입");
	
	public BoardMain() {
		
		setTitle("QuizMainBoard");
		setBounds(100, 100, 1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		JScrollPane jsp = new JScrollPane(table);
		
//		제목라벨 셋팅
		mainlabel.setOpaque(true);
		mainlabel.setBackground(new Color(466259));
		mainlabel.setHorizontalAlignment(JLabel.CENTER);
		mainlabel.setFont(new Font("맑은 고딕" , Font.BOLD, 40));
		mainlabel.setForeground(Color.white);
		mainlabel.setPreferredSize(new Dimension(1000,80));
		
//		테이블 셋팅
		
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
//		테이블 판넬
		
		tablePanel.add(restPanel1, BorderLayout.WEST);
		tablePanel.add(restPanel2, BorderLayout.EAST);
		
		tablePanel.add(jsp, BorderLayout.CENTER);
		
//		공백판넬
		restPanel1.setBackground(new Color(466259));
		restPanel2.setBackground(new Color(466259));
		restPanel1.setPreferredSize(new Dimension(30,300));
		restPanel2.setPreferredSize(new Dimension(30,300));
		
//		프레임에 판넬추가
		
		add(mainlabel , BorderLayout.NORTH);
		add(tablePanel , BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		
//		버튼 비활성화
		inputButton.setEnabled(false);
		updateButton.setEnabled(false);
		deleteButton.setEnabled(false);
		
		
//		버튼판넬 
		buttonPanel1.setBackground(new Color(466259));
		buttonPanel2.setBackground(new Color(466259));
		buttonPanel.setPreferredSize(new Dimension(1000,300));
		
		
		
//		버튼 크기
		inputButton.setPreferredSize(new Dimension(150,40));
		solveButton.setPreferredSize(new Dimension(150,40));
		updateButton.setPreferredSize(new Dimension(150,40));
		deleteButton.setPreferredSize(new Dimension(150,40));
		loginButton.setPreferredSize(new Dimension(120,50));
		joinButton.setPreferredSize(new Dimension(120,50));
		
//		버튼 폰트지정
		inputButton.setFont(new Font("나눔고딕코딩", Font.BOLD , 20));
		solveButton.setFont(new Font("나눔고딕코딩", Font.BOLD , 20));
		updateButton.setFont(new Font("나눔고딕코딩", Font.BOLD , 20));
		deleteButton.setFont(new Font("나눔고딕코딩", Font.BOLD , 20));
		loginButton.setFont(new Font("나눔고딕코딩", Font.BOLD , 20));
		joinButton.setFont(new Font("나눔고딕코딩", Font.BOLD , 20));
		
//		버튼 색깔지정
		inputButton.setBackground(new Color(14609663));
		solveButton.setBackground(new Color(14609663));
		updateButton.setBackground(new Color(14609663));
		deleteButton.setBackground(new Color(14609663));
		loginButton.setBackground(new Color(14609663));
		joinButton.setBackground(new Color(14609663));
		
//		버튼 판넬 셋팅
		
		buttonPanel1.add(inputButton);
		buttonPanel1.add(solveButton);
		buttonPanel1.add(updateButton);
		buttonPanel1.add(deleteButton);
		
		buttonPanel2.add(loginButton);
		buttonPanel2.add(joinButton);
		buttonPanel2.setLayout(new FlowLayout(FlowLayout.RIGHT,20,0));
		
		buttonPanel.setPreferredSize(new Dimension(1000, 150));
		buttonPanel1.setPreferredSize(new Dimension(1000, 60));
		buttonPanel2.setPreferredSize(new Dimension(1000, 90));
		buttonPanel.add(buttonPanel1);
		buttonPanel.add(buttonPanel2);
		

		
//		버튼 액션리스너
		inputButton.addActionListener(this);
		solveButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		loginButton.addActionListener(this);
		joinButton.addActionListener(this);

		setVisible(true);
		
		
//		서버 연동 및 서버에 게시판 데이터 요청		
		try {
			//서버와 연결
			socket = new Socket("192.168.7.34",10004);
			System.out.println("서버 접속중");
			
			//서버에 객체 요청(1번) 전송
			printWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			printWriter.write(1+"\n");
			printWriter.flush();
			
			//서버에서 넘어노는 객체를 반복하여 null일 때 까지 받아 list에 넣는다.
			QuizVO vo; 
			while(true) {
				ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
				vo = (QuizVO) objectInputStream.readObject();
				if(vo == null) {
					break;
				}
				list.add(vo);
				System.out.println(vo);
			}
			printWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//END try~catch
		
	}
	

	public static void main(String[] args) {
		BoardMain window = new BoardMain();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case "입력":
			Entering entering = new Entering();
			entering.setVisible(true);
			break;
		case "문제풀기":
			QuizMain quizmain = new QuizMain();
			quizmain.setVisible(true);
			break;
		case "수정":
			JOptionPane.showMessageDialog(null, "수정");
			break;
		case "삭제":
			JOptionPane.showMessageDialog(null, "삭제");
			break;
		case "로그인":
			LoginFrame loginFrame = new LoginFrame();
			loginFrame.setVisible(true);
			
			
			break;
			
		case "회원가입":
			JOptionPane.showMessageDialog(null, "회원가입");
			SignUp signUp = new SignUp();
			signUp.setVisible(true);
			break;
			
		case "로그아웃":
			int logout = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION);
			if(logout == 0) {
				inputButton.setEnabled(false);
				updateButton.setEnabled(false);
				deleteButton.setEnabled(false);
				loginButton.setText("로그인");	
				JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
			}
			break;
		}
	}


}