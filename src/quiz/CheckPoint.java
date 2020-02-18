package quiz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CheckPoint extends JFrame {

// 윈도우 멤버
	JPanel pointPanel;
	JLabel pointLabel;	// 점수 표시 라벨
	
	JPanel ButtonPanel;
	JButton button;
	ImgPanel img;
// 점수 계산 멤버
	double point = 0.0;
	
	public CheckPoint(int aCount, int wCount) {
		setTitle("점수 확인하기");
		setBounds(300,100,500,400);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		//점수 표시 패널, 라벨 생성
		pointPanel = new JPanel();
		pointLabel = new JLabel();
		pointLabel.setFont(new Font("Dialog", Font.BOLD , 20));
		pointLabel.setOpaque(true);
		
		//점수 계산해서 pointLabel에 setText
		point = (aCount/(aCount + wCount + 0.0)) * 100;
		
		//점수에 따른 titleLabel 텍스트 및 컬러 변경
		if(point > 60) {
			pointLabel.setText("<html>"+"축하합니다 합격입니다!<br>"+ "수고하셨습니다. <br>" + point + "점 입니다." +"</html>");
			img = new ImgPanel("./src/img/yes.jpg");
			pointPanel.setBackground(Color.red);
			pointLabel.setBackground(Color.red);
		}else {
			pointLabel.setText("<html>"+ "이런식이면 불합격입니다.<br>"+ "수고하셨습니다. <br>" + point + "점 입니다." + "</html>");
			img = new ImgPanel("./src/img/no.jpg");
			pointPanel.setBackground(Color.black);
			pointLabel.setBackground(Color.black);
			pointLabel.setForeground(Color.white);
		}
		
		//이미지
		ButtonPanel = new JPanel();
		
		//점수 라벨, 패널 추가하기
		pointPanel.setBounds(95,50,300,100);
		pointPanel.add(pointLabel);
		add(pointPanel);
		
		img.setBounds(200,150,100,100);
		add(img);
		
////		확인 버튼 추가
//		button.setBounds(60, 60, 250, 350);
//		button.setBackground(Color.yellow);
//		add(button);
//		setVisible(true);
	}
}