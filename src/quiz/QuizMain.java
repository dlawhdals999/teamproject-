package quiz;


import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class QuizMain extends JFrame implements ActionListener, ItemListener{

//윈도우 화면을 띄울 객체 선언
	JPanel panelTitle;						// 가장 상단에 로고, 또는 디자인적 텍스트가 들어갈 라벨을 올릴 패널
	JPanel panelQuiz;						// 문제가 들어간 라벨을 올릴 패널
	JPanel panelanswer;						// 4개의 보기 버튼과 문제의 보기들이 올라갈 패널
	JPanel btn;
	JPanel field;
	JPanel test;
	
	JLabel titleLabel;						// 상단의 텍스트가 들어갈 라벨
	JLabel quizeLabel;						// 문제가 올라갈 라벨
	
	JLabel field1,field2,field3,field4;		// 문제의 보기 
	JRadioButton btn1,btn2,btn3,btn4;		// 라디오 버튼 
	ButtonGroup radio = new ButtonGroup();
	JButton okBtn;							// 제출버튼
	
	int randomIndex=0;
	QuizVO vo;
	String item;
	int aCount;
	int wCount;
	
	ArrayList<Integer> list;
	ArrayList<String> list2;
	int arr[] ={0,1,2,3};
	
	public QuizMain() {
		setTitle("정보처리기사 문제풀기");
		setBounds(300,100,500,700);
		
		//상단에 라벨 추가
		panelTitle = new JPanel();
		titleLabel = new JLabel("정보처리기사");
		titleLabel.setPreferredSize(new Dimension(300,100));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Dialog",Font.BOLD,25));
		panelTitle.add(titleLabel);
		add(panelTitle, BorderLayout.NORTH);

		//중단에 문제가 표시되는 라벨을 추가한다.
		panelQuiz = new JPanel();
		quizeLabel = new JLabel("문제 : ");
		quizeLabel.setPreferredSize(new Dimension(400,150));;
		quizeLabel.setHorizontalAlignment(JLabel.CENTER);
		quizeLabel.setFont(new Font("Dialog",Font.BOLD,18));
		quizeLabel.setOpaque(true);
		quizeLabel.setBackground(new Color(0xffb5b5));
		panelQuiz.add(quizeLabel);
		add(panelQuiz);
	
		//하단에 버튼과 텍스트 필드 추가
		panelanswer = new JPanel();	// 보기와 라디오 버튼, 제출 버튼이 들어갈 패널
		
		btn1 = new JRadioButton("",new ImageIcon("./src/img/Duke01.gif"));  //라디오 버튼 1
		btn2 = new JRadioButton("",new ImageIcon("./src/img/Duke01.gif"));	//라디오 버튼 2
		btn3 = new JRadioButton("",new ImageIcon("./src/img/Duke01.gif")); 	//라디오 버튼 3
		btn4 = new JRadioButton("",new ImageIcon("./src/img/Duke01.gif"));	//라디오 버튼 4
		
		field1 = new JLabel(); 		//문제의 보기가 들어갈 라벨 1
		field2 = new JLabel();		//문제의 보기가 들어갈 라벨 2
		field3 = new JLabel();		//문제의 보기가 들어갈 라벨 3	
		field4 = new JLabel();		//문제의 보기가 들어갈 라벨 4	
		
		field1.setPreferredSize(new Dimension(200,30));
		field2.setPreferredSize(new Dimension(200,20));
		field3.setPreferredSize(new Dimension(200,20));
		field4.setPreferredSize(new Dimension(200,20));
		
		btn1.addItemListener(this);
		btn2.addItemListener(this);
		btn3.addItemListener(this);
		btn4.addItemListener(this);
		
		//라디오 그룹(radio)에 라디오 버튼을 추가한다.
		radio.add(btn1);
		radio.add(btn2);
		radio.add(btn3);
		radio.add(btn4);
		
		//btn 패널에 라디오버튼을 추가한다.
		btn = new JPanel(new GridLayout(4,1,1,20));
		btn.add(btn1);
		btn.add(btn2);
		btn.add(btn3);
		btn.add(btn4);
		
		//field 패널에 텍스트 라벨을 추가한다.
		field = new JPanel(new GridLayout(4,1,1,20));
		field.add(field1);
		field.add(field2);
		field.add(field3);
		field.add(field4);
		
		//제출버튼
		okBtn = new JButton("제출");
		okBtn.setPreferredSize(new Dimension(70,30));
		okBtn.setBackground(new Color(0xffb5b5));
		okBtn.addActionListener(this);
		
		//제출 버튼을 위한 패널(test 패널)
		test = new JPanel();
		test.add(okBtn);	
		
		// panelanswer 패널에 btn(왼), field(중),test(하) 패널을 추가하고 panelanswer을 JFrame의 하단에 추가한다.
		panelanswer.setLayout(new BorderLayout());
		panelanswer.add(btn, BorderLayout.WEST);
		panelanswer.add(field, BorderLayout.CENTER);
		panelanswer.add(test, BorderLayout.SOUTH);
		add(panelanswer, BorderLayout.SOUTH);
		
		setVisible(true);
		
//=====================================================================================================================		

		//데이터베이스의 전체문제의 idx를 얻어와서 list에 idx를 저장한다.
		list = QuizDAO.readIdx();
		
		//idx의 순서를 섞어준다.
		for(int i=0; i<1000; i++) {
			int ran = new Random().nextInt(list.size()-1)+1;
			int temp = list.get(0);
			list.set(0,list.get(ran));
			list.set(ran,temp);
		}
		
		//문제를 읽어오는 메소드를 랜덤한 idx를 던져줘서 quizeLabel에 SetText한다.
		rePrintText();
	}


	private void rePrintText() {
		try {
			vo = QuizDAO.readQuiz(list.get(randomIndex));
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "모든 문제를 푸셨습니다.");
			CheckPoint cp = new CheckPoint(aCount, wCount);
			cp.setVisible(true);
		}
		
		list2 = new ArrayList<String>();
	    
		list2.add(vo.getAnswer());
		list2.add(vo.getwrong1());
		list2.add(vo.getwrong2());
		list2.add(vo.getwrong3());
		
		for(int i=0; i<1000; i++) {
			int ran = new Random().nextInt(4);
			int temp = arr[0];
			arr[0] = arr[ran];
			arr[ran] = temp;
		}
		
		list2.add(vo.getAnswer());
		list2.add(vo.getwrong1());
		list2.add(vo.getwrong2());
		list2.add(vo.getwrong3());

		field1.setText(list2.get(arr[0]));
		field2.setText(list2.get(arr[1]));
		field3.setText(list2.get(arr[2]));
		field4.setText(list2.get(arr[3]));
		
		quizeLabel.setText("<html>"+vo.getQuiz()+"</html>");
	}	

//	제출 버튼 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent e) {
		if(vo.getAnswer().equals(item)) {
			aCount++;
			int result = answerMSG();
		}else {
			wCount++;
			int result = noAnswerMSG();
		}
	}

	private int noAnswerMSG() {
		String[]  btnString = {"채점하기","다음문제"};
		int result = JOptionPane.showOptionDialog(null, "오답입니다 ㅠㅠ", "오답", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, btnString, btnString[0]);
		if(result == 0) {
			CheckPoint cp = new CheckPoint(aCount, wCount);
			cp.setVisible(true);
		}else if(result==1) {
			randomIndex++;
			if(randomIndex < list2.size()) {
				rePrintText();
			}
		}                               
		return result;
	}


	private int answerMSG() {
		String[]  btnString = {"채점하기","다음문제"};
		int result = JOptionPane.showOptionDialog(null, "정답입니다!!!", "정답", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, btnString, btnString[0]);
		if(result == 0) {
			CheckPoint cp = new CheckPoint(aCount, wCount);
			cp.setVisible(true);
		}else if(result==1) {
			randomIndex++;
			if(randomIndex < list2.size()) {
				rePrintText();
			}
		}
		return result;
	}

// 라디오 버튼 이벤트 처리	
	@Override
	public void itemStateChanged(ItemEvent e) {
		Object object = e.getSource();
		JRadioButton radio = (JRadioButton) object;
		
		if(radio == btn1) {
			// 라디오 버튼 선택 시 item 변수에 field1을 저장한다.
			item = field1.getText();
			// 라디오 버튼 클릭 시 이미지를 변경한다.
			if(radio.isSelected()) {
				btn1.setIcon(new ImageIcon("./src/img/Duke06.gif"));
			}else {
				btn1.setIcon(new ImageIcon("./src/img/Duke01.gif"));
			}
		}else if(radio == btn2) {
			item = field2.getText();
			if(radio.isSelected()) {
				btn2.setIcon(new ImageIcon("./src/img/Duke06.gif"));
			}else {
				btn2.setIcon(new ImageIcon("./src/img/Duke01.gif"));
			}
		}else if(radio == btn3) {
			item = field3.getText();
			if(radio.isSelected()) {
				btn3.setIcon(new ImageIcon("./src/img/Duke06.gif"));
			}else {
				btn3.setIcon(new ImageIcon("./src/img/Duke01.gif"));
			}
		}else if(radio == btn4) {
			item = field4.getText();
			if(radio.isSelected()) {
				btn4.setIcon(new ImageIcon("./src/img/Duke06.gif"));
			}else {
				btn4.setIcon(new ImageIcon("./src/img/Duke01.gif"));
			}
		}else if(!radio.isSelected()) {
			JOptionPane.showMessageDialog(null, "정답을 선택해주세요.");
		}
	}//END itemStateChanged()

}