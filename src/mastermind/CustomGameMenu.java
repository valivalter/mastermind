package mastermind;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * A Menu oszt�ly lesz�rmazottja, azt a men�t reprezent�lja,
 * ahol egy�nileg lehet megadni a j�t�k param�tereit a k�v�nt m�don.
 */
public class CustomGameMenu extends Menu {
	
	/**
	 * A JButton, amellyel azt lehet �ll�tani, hogy lehet-e az elrejtett
	 * sorban egy sz�nb�l t�bb is.
	 */
	private JButton differentColors;
	/**
	 * A sz�nek �s a pr�b�lkoz�sok jelenlegi sz�m�t ki�r� JLabel-�k.
	 */
	private JLabel colors, attempts;
	/**
	 * A sz�nek �s a pr�b�lkoz�sok jelenlegi sz�ma.
	 */
	private int numberOfColors = 8, numberOfAttempts = 8;
	/**
	 * A logikai �rt�k, hogy lehet-e az elrejtett sorban egy sz�nb�l t�bb is.
	 */
	private boolean differentOnly = true;
	
	/**
	 * Visszaadja a sz�nek jelenlegi sz�m�t.
	 * 
	 * @return		A sz�nek jelenlegi sz�ma.
	 */
	public int getColors() {
		return numberOfColors;
	}
	
	/**
	 * Visszaadja a pr�b�lkoz�sok jelenlegi sz�m�t.
	 * 
	 * @return		A pr�b�lkoz�sok jelenlegi sz�ma
	 */
	public int getAttempts() {
		return numberOfAttempts;
	}
	
	/**
	 * Visszaadja a logikai �rt�ket, hogy lehet-e az elrejtett sorban egy sz�nb�l t�bb is.
	 * 
	 * @return		A logikai �rt�k
	 */
	public boolean getDifferentOnly() {
		return differentOnly;
	}
	
	/**
	 * Az oszt�ly konstruktora, �rt�ket ad az attrib�tumoknak,
	 * l�trehozza a men�h�z sz�ks�ges JButton-�ket �s JLabel-�ket,
	 * (a JButton-�kh�z hozz�adja az oszt�lyon bel�l defini�lt ActionListenert)
	 * �s ezeket elhelyezi a saj�t JPanel-j�n.
	 */
	public CustomGameMenu() {
		
		JButton plusColor = createButton("+", "plusColor");
		JButton minusColor = createButton("-", "minusColor");
		colors = createLabel("Number of colors: " + numberOfColors);
		JButton plusAttempt = createButton("+", "plusAttempt");
		JButton minusAttempt = createButton("-", "minusAttempt");
		attempts = createLabel("Number of attempts: " + numberOfAttempts);
		differentColors = createButtonWithIcon("Different colors only", "differentcolors", "misc" + System.getProperty("file.separator") + "tickedcheckbox.png");
		JButton start = createButton("Start", "start");
		JButton back = createButton("Back", "back");
		
		start.setMinimumSize(differentColors.getMinimumSize());
		colors.setMinimumSize(differentColors.getMinimumSize());
		attempts.setMinimumSize(differentColors.getMinimumSize());
		back.setMinimumSize(differentColors.getMinimumSize());
		
		Component horizontalPadding = Box.createRigidArea(new Dimension(100, 0));
		Component verticalPadding = Box.createRigidArea(new Dimension(0, 200));
		
		ownPanel = new JPanel();
		ownPanel.setBackground(Color.WHITE);
		GroupLayout groupLayout = new GroupLayout(ownPanel);
		groupLayout.setAutoCreateGaps(true);
		ownPanel.setLayout(groupLayout);
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(horizontalPadding)
				.addComponent(horizontalPadding))
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(plusColor)
				.addComponent(plusAttempt))
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(verticalPadding)
				.addComponent(start)
				.addComponent(attempts)
				.addComponent(differentColors)
				.addComponent(colors)
				.addComponent(back))
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(minusColor)
				.addComponent(minusAttempt))
			.addGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
				.addComponent(horizontalPadding)
				.addComponent(horizontalPadding)));
		
		groupLayout.setVerticalGroup(groupLayout.createSequentialGroup()
			.addComponent(verticalPadding)
			.addComponent(start)
			.addGroup(groupLayout.createParallelGroup()
				.addComponent(horizontalPadding)
				.addComponent(plusColor)
				.addComponent(colors)
				.addComponent(minusColor)
				.addComponent(horizontalPadding))
			.addGroup(groupLayout.createParallelGroup()
				.addComponent(horizontalPadding)
				.addComponent(plusAttempt)
				.addComponent(attempts)
				.addComponent(minusAttempt)
				.addComponent(horizontalPadding))
			.addComponent(differentColors)
			.addComponent(back));
		
		ActionListener al = new CustomGameMenuActionListener();
		start.addActionListener(al);
		plusColor.addActionListener(al);
		minusColor.addActionListener(al);
		plusAttempt.addActionListener(al);
		minusAttempt.addActionListener(al);
		differentColors.addActionListener(al);
		back.addActionListener(al);
	}
	
	/**
	 * L�trehoz egy JLabel-t, aminek be�ll�tja a m�ret�t, valamint 
	 * a megjelen�tend� sz�vegnek a param�tereit is.
	 * 
	 * @param name		A JLabel �ltal ki�rt sz�veg, a felhaszn�l� ezt l�tja a k�perny�n
	 * @return			A l�trehozott JLabel
	 */
	public JLabel createLabel(String name) {
		JLabel label = new JLabel(name, JLabel.CENTER);
		label.setMaximumSize(new Dimension(250, 100));
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
		return label;
	}
	
	/**
	 * A men�ben l�v� JButton-�k�rt felel�s ActionListener
	 */
	class CustomGameMenuActionListener implements ActionListener {
		
		/**
		 * A "Start" megnyom�s�ra megh�vja a j�t�kot az aktu�lis
		 * param�terekkel elind�t� f�ggv�nyt, a "Back" hat�s�ra pedig visszal�p a
		 * "NewGameMenu" oszt�ly �ltal reprezent�lt men�be.
		 * Minden egy�b JButton megnyom�s�nak hat�s�ra a j�t�k param�tereit
		 * megfelel� m�don �ll�t� f�ggv�nyt h�vja meg.
		 * 
		 * @param ae	Egy figyelt JButton megnyom�s�nak hat�s�ra l�trej�v� esem�ny
		 */
		public void actionPerformed(ActionEvent ae) {
			String command = ae.getActionCommand();
			if (command.equals("start")) {
				startGame();
			}
			else if (command.equals("plusColor")) {
				plusColor();
			}
			else if (command.equals("minusColor")) {
				minusColor();
			}
			else if (command.equals("plusAttempt")) {
				plusAttempt();
			}
			else if (command.equals("minusAttempt")) {
				minusAttempt();
			}
			else if (command.equals("differentcolors")) {
				setDifferentOnly();
			}
			else if (command.equals("back")) {
				MenuSystem.showPanel("newgamemenu");
			}
		}
		
		/**
		 * Elind�tja a j�t�kot az aktu�lis param�terekkel.
		 */
		public void startGame() {
			if (numberOfColors == 10 && MenuSystem.getSelectedTheme().equals("animals")) {
				numberOfColors--;
			}
			MenuSystem.getMenus().add(new GamePanel(numberOfColors, numberOfAttempts, differentOnly), "game");
			MenuSystem.showPanel("game");
		}
		
		/**
		 * N�veli a j�t�kban l�v� k�l�nb�z� sz�nek sz�m�t.
		 */
		public void plusColor() {
			if (numberOfColors < 9 || (numberOfColors < 10 && !MenuSystem.getSelectedTheme().equals("animals"))) {
				numberOfColors++;
				colors.setText("Number of colors: " + numberOfColors);
			}
		}
		
		/**
		 * Cs�kkenti a j�t�kban l�v� k�l�nb�z� sz�nek sz�m�t.
		 */
		public void minusColor() {
			if (numberOfColors > 4) {
				numberOfColors--;
				colors.setText("Number of colors: " + numberOfColors);
			}
		}
		
		/**
		 * N�veli a pr�b�lkoz�sok maxim�lis sz�m�t.
		 */
		public void plusAttempt() {
			if (numberOfAttempts < 10) {
				numberOfAttempts++;
				attempts.setText("Number of attempts: " + numberOfAttempts);
			}
		}
		
		/**
		 * Cs�kkenti a pr�b�lkoz�sok maxim�lis sz�m�t.
		 */
		public void minusAttempt() {
			if (numberOfAttempts > 1) {
				numberOfAttempts--;
				attempts.setText("Number of attempts: " + numberOfAttempts);
			}
		}
		
		/**
		 * Be�ll�tja, hogy a rejtett sorban lehetnek-e azonos sz�n� b�buk, vagy sem,
		 * �s az erre vonatkoz� JButton ikonj�t is megv�ltoztatja.  
		 */
		public void setDifferentOnly() {
			differentOnly = !differentOnly;
			if (differentColors.getIcon().toString().equals(new ImageIcon("misc" + System.getProperty("file.separator") + "xcheckbox.png").toString()))
				differentColors.setIcon(new ImageIcon("misc" + System.getProperty("file.separator") + "tickedcheckbox.png"));
			else
				differentColors.setIcon(new ImageIcon("misc" + System.getProperty("file.separator") + "xcheckbox.png"));
		}
	}

}
