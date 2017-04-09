package dinnerTime;

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
	private Client client = new Client("127.0.0.1", 3250);

	public DinnerGUI() {
		LogInDisplay lid = new LogInDisplay();
		client.start();
		lid.run();
	}

	private class LogInDisplay implements ActionListener {
		private JLabel titleLbl = new JLabel("DinnerTime");
		private JFrame jf = new JFrame("Log In");
		private JPanel jp = new JPanel();
		private JLabel userNameLbl = new JLabel("Username");
		private JLabel pwdLbl = new JLabel("Password");
		private JButton newAccountButton = new JButton("New Account");
		private JTextField jfUserName = new JTextField("Username");
		private JPasswordField jfPwd = new JPasswordField();
		private JButton logIn = new JButton("Log In");
		private User user;

		// MainDisplay md = new MainDisplay();

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
			// jf.pack();
			jf.setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == logIn) {
				
//				jf.setVisible(false);
//				new MainDisplay();
			} else if (e.getSource() == newAccountButton) {
				System.out.println("Create new account");
				user = new User(jfUserName.getText(), jfPwd.getText());
				client.sendToServer(user);
			}
		}
	}

	private class MainDisplay implements MouseListener, MouseMotionListener, ActionListener {
		private JFrame jf = new JFrame("DinnerTime");
		private JLabel worldMap = new JLabel(new ImageIcon("images/WorldMap.png"));
		private JButton logOutBtn = new JButton("Log Out"), recipeBtn = new JButton("Nytt Recept"),
				dtl = new JButton("Dinner Time");
		private JTextField search = new JTextField("Sök");
		LogInDisplay lid = new LogInDisplay();

		public MainDisplay() {
			jf.setLayout(new BorderLayout());
			jp1.setLayout(new GridLayout(0, 3));
			jp1.add(search);
			dtl.setFont(new Font("Serif", Font.ITALIC, 18));
			dtl.setForeground(Color.red);
			dtl.setBackground(Color.GREEN);
			logOutBtn.addActionListener(this);
			recipeBtn.addActionListener(this);
			dtl.addActionListener(this);
			jp1.add(dtl);
			jp1.add(logOutBtn);
			jp1.add(recipeBtn);
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

		public void displayNA() {
			na.setVisible(true);
		}

		public void checkCountry(int x, int y) {
			if (y <= 235 && x <= 305) {
				System.out.println("NORDAMERIKA");
			} else if (y >= 236 && x <= 330) {
				System.out.println("SYDAMERIKA");
			} else if (x >= 350 && x <= 510 && y <= 155) {
				System.out.println("EUROPA");
			}
		}

		public void mouseMoved(MouseEvent e) {
			// checkCountry(e.getX(), e.getY());
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
			if (e.getSource() == logOutBtn) {
				jf.setVisible(false);
				jp1.remove(logOutBtn); // annars läggs det till fler för varje
										// gång man loggar ut sen loggar in igen
				jp.remove(worldMap);
				jp1.remove(search);
				jp1.remove(dtl);
				jp1.remove(recipeBtn);
				lid.run();
			}
			if (e.getSource() == recipeBtn) {
				new NewRecipeDisplay();
			}
			if (e.getSource() == dtl) { // om man söker efter något
				client.sendToServer("search" + search.getText());
			}
		}
	}

	private class NewRecipeDisplay implements ActionListener {
		private JLabel lblTitle = new JLabel("Rätt:"), lblCountry = new JLabel("Land:"),
				lblTime = new JLabel("Tillagningstid (minuter):");
		private JTextField tfTitle = new JTextField(), tfCountry = new JTextField(), tfTime = new JTextField();
		private JTextArea taIngredients = new JTextArea();
		private JButton btnSend = new JButton("Skicka");
		private JPanel pnlUp = new JPanel(), pnlMid = new JPanel(), pnlDown = new JPanel();
		private JFrame frame = new JFrame();

		public NewRecipeDisplay() {
			start();
			btnSend.addActionListener(this);
		}

		public void start() {
			frame.setLayout(new BorderLayout());
			frame.setPreferredSize(new Dimension(350, 400));
			pnlUp.setLayout(new GridLayout(4, 2));
			pnlUp.add(lblTitle);
			pnlUp.add(tfTitle);
			pnlUp.add(lblCountry);
			pnlUp.add(tfCountry);
			pnlUp.add(lblTime);
			pnlUp.add(tfTime);
			frame.add(pnlUp, BorderLayout.NORTH);
			pnlMid.setLayout(new BorderLayout());
			taIngredients.setText("Lista ingredienserna här!");
			pnlMid.add(taIngredients);
			frame.add(pnlMid, BorderLayout.CENTER);
			pnlDown.setLayout(new BorderLayout());
			pnlDown.add(btnSend);
			frame.add(pnlDown, BorderLayout.SOUTH);
			frame.setVisible(true);
			frame.pack();
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnSend) {
				Recipe recipe = new Recipe();
				if (!tfTitle.getText().isEmpty()) {
					recipe.setTitle(tfTitle.getText());
				}
				if (!tfCountry.getText().isEmpty()) {
					recipe.setCountry(tfCountry.getText());
				}
				if (!tfTime.getText().isEmpty()) {
					recipe.setTime(Integer.parseInt(tfTime.getText()));
				}
				if (!taIngredients.getText().isEmpty()) {
					String[] ingredients = taIngredients.getText().split("\\n");
					for (int i = 0; i < ingredients.length; i++) {
						recipe.setIngredient(ingredients[i]);
					}
				}
				client.sendToServer(recipe);
				frame.setVisible(false);
			}
		}
	}

	public static void main(String[] args) {
		DinnerGUI d = new DinnerGUI();
	}
}