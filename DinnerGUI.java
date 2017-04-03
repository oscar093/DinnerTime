package DinnerTime;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SplashScreen;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.*;

//extends MouseAdapter implements MouseListener, MouseMotionListener, ActionListener
public class DinnerGUI {
	private JLabel na = new JLabel(new ImageIcon("images/nordamerika.png"));
	private JPanel jp = new JPanel();
	private JPanel jp1 = new JPanel();
	private JTextField search = new JTextField("Sök");
	private JButton dtl = new JButton("Dinner Time");

	public DinnerGUI() {
		LogInDisplay lid = new LogInDisplay();
		lid.start();
	}

	private class LogInDisplay extends Thread implements ActionListener {
		private JLabel titleLbl = new JLabel("DinnerTime");
		private JFrame jf = new JFrame("Log In");
		private JPanel jp = new JPanel();
		private JLabel userNameLbl = new JLabel("Username");
		private JLabel pwdLbl = new JLabel("Password");
		private JButton newAccountButton = new JButton("New Account");
		private JTextField jfUserName = new JTextField("Username");
		private JTextField jfPwd = new JTextField("Password");
		private JButton logIn = new JButton("Log In");

//		 		MainDisplay md = new MainDisplay();

		public void run() {
			jp.setBackground(Color.decode("#28530D"));
			jp.setLayout(new GridLayout(7, 0));
			jf.setSize(400, 500);
			
			titleLbl.setFont(new Font("Serif", Font.ITALIC, 18));
			titleLbl.setForeground(Color.red);
			userNameLbl.setForeground(Color.WHITE);
			pwdLbl.setForeground(Color.WHITE);
			logIn.addActionListener(this);
			newAccountButton.addActionListener(this);
			jp.add(titleLbl);
			jp.add(userNameLbl);
			jp.add(jfUserName);
			jp.add(pwdLbl);
			jp.add(jfPwd);
			jp.add(logIn);
			jp.add(newAccountButton);
			
			jf.add(jp);
//			 jf.pack();
			jf.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == logIn) {
				System.out.println(jfUserName.getText());
				System.out.println(jfPwd.getText());
				jf.setVisible(false);
				this.interrupt();
//				md.start();
				Thread thread = new MainDisplay();
				thread.start();
			} else if(e.getSource()==newAccountButton){
				System.out.println("Create new account");
			}
		}
	}

	private class MainDisplay extends Thread implements MouseListener, MouseMotionListener,ActionListener {
		private JFrame jf = new JFrame("DinnerTime");
		private JLabel worldMap = new JLabel(new ImageIcon("images/WorldMap.png"));
		private Thread thread = new Thread(this);
		private JButton logOutBtn = new JButton("Log Out");
		LogInDisplay lid = new LogInDisplay();
		public MainDisplay(){
			jf.setLayout(new BorderLayout());
			jp1.setLayout(new GridLayout(0, 3));
			jp1.add(search);
			dtl.setFont(new Font("Serif", Font.ITALIC, 18));
			dtl.setForeground(Color.red);
			dtl.setBackground(Color.GREEN);
			logOutBtn.addActionListener(this);
			jp1.add(dtl);
			jp1.add(logOutBtn);
			worldMap.addMouseListener(this);
			worldMap.addMouseMotionListener(this);
			na.setVisible(false);
			
			jp.add(worldMap);
			jf.add(jp1, BorderLayout.NORTH);
			jf.add(jp, BorderLayout.CENTER);
			jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setVisible(true);
			jf.pack();
		}

		public void run() {
//				jf.setLayout(new BorderLayout());
//				jp1.setLayout(new GridLayout(0, 3));
//				jp1.add(search);
//				dtl.setFont(new Font("Serif", Font.ITALIC, 18));
//				dtl.setForeground(Color.red);
//				dtl.setBackground(Color.GREEN);
//				logOutBtn.addActionListener(this);
//				jp1.add(dtl);
//				jp1.add(logOutBtn);
//				worldMap.addMouseListener(this);
//				worldMap.addMouseMotionListener(this);
//				na.setVisible(false);
//				
//				jp.add(worldMap);
//				jf.add(jp1, BorderLayout.NORTH);
//				jf.add(jp, BorderLayout.CENTER);
//				jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
//				jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				jf.setVisible(true);
//				jf.pack();
		}
		

		public void displayNA() {

			

			na.setVisible(true);

		}

		public void checkCountry(int x, int y) {
			if (y <= 235 && x <= 305) {
				System.out.println("NORDAMERIKA");
				thread.interrupt();
			} else if (y >= 236 && x <= 330) {
				System.out.println("SYDAMERIKA");
			} else if (x >= 350 && x <= 510 && y <= 155) {
				System.out.println("EUROPA");
			}

		}

		public void mouseMoved(MouseEvent e) {
//			checkCountry(e.getX(), e.getY());
		}

		public void mouseDragged(MouseEvent e) {

		}

		public void mouseClicked(MouseEvent e) {

		}

		public void mousePressed(MouseEvent e) {

		}

		public void mouseReleased(MouseEvent e) {
			checkCountry(e.getX(), e.getY());
		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}


		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==logOutBtn){
				jf.setVisible(false);
				lid.start();
			}
		}
	}

	public static void main(String[] args) {
		DinnerGUI d = new DinnerGUI();

	}
}
