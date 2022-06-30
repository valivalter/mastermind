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
 * A Menu osztály leszármazottja, azt a menüt reprezentálja,
 * ahol egyénileg lehet megadni a játék paramétereit a kívánt módon.
 */
public class CustomGameMenu extends Menu {
	
	/**
	 * A JButton, amellyel azt lehet állítani, hogy lehet-e az elrejtett
	 * sorban egy színbõl több is.
	 */
	private JButton differentColors;
	/**
	 * A színek és a próbálkozások jelenlegi számát kiíró JLabel-ök.
	 */
	private JLabel colors, attempts;
	/**
	 * A színek és a próbálkozások jelenlegi száma.
	 */
	private int numberOfColors = 8, numberOfAttempts = 8;
	/**
	 * A logikai érték, hogy lehet-e az elrejtett sorban egy színbõl több is.
	 */
	private boolean differentOnly = true;
	
	/**
	 * Visszaadja a színek jelenlegi számát.
	 * 
	 * @return		A színek jelenlegi száma.
	 */
	public int getColors() {
		return numberOfColors;
	}
	
	/**
	 * Visszaadja a próbálkozások jelenlegi számát.
	 * 
	 * @return		A próbálkozások jelenlegi száma
	 */
	public int getAttempts() {
		return numberOfAttempts;
	}
	
	/**
	 * Visszaadja a logikai értéket, hogy lehet-e az elrejtett sorban egy színbõl több is.
	 * 
	 * @return		A logikai érték
	 */
	public boolean getDifferentOnly() {
		return differentOnly;
	}
	
	/**
	 * Az osztály konstruktora, értéket ad az attribútumoknak,
	 * létrehozza a menühöz szükséges JButton-öket és JLabel-öket,
	 * (a JButton-ökhöz hozzáadja az osztályon belül definiált ActionListenert)
	 * és ezeket elhelyezi a saját JPanel-jén.
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
	 * Létrehoz egy JLabel-t, aminek beállítja a méretét, valamint 
	 * a megjelenítendõ szövegnek a paramétereit is.
	 * 
	 * @param name		A JLabel által kiírt szöveg, a felhasználó ezt látja a képernyõn
	 * @return			A létrehozott JLabel
	 */
	public JLabel createLabel(String name) {
		JLabel label = new JLabel(name, JLabel.CENTER);
		label.setMaximumSize(new Dimension(250, 100));
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
		return label;
	}
	
	/**
	 * A menüben lévõ JButton-ökért felelõs ActionListener
	 */
	class CustomGameMenuActionListener implements ActionListener {
		
		/**
		 * A "Start" megnyomására meghívja a játékot az aktuális
		 * paraméterekkel elindító függvényt, a "Back" hatására pedig visszalép a
		 * "NewGameMenu" osztály által reprezentált menübe.
		 * Minden egyéb JButton megnyomásának hatására a játék paramétereit
		 * megfelelõ módon állító függvényt hívja meg.
		 * 
		 * @param ae	Egy figyelt JButton megnyomásának hatására létrejövõ esemény
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
		 * Elindítja a játékot az aktuális paraméterekkel.
		 */
		public void startGame() {
			if (numberOfColors == 10 && MenuSystem.getSelectedTheme().equals("animals")) {
				numberOfColors--;
			}
			MenuSystem.getMenus().add(new GamePanel(numberOfColors, numberOfAttempts, differentOnly), "game");
			MenuSystem.showPanel("game");
		}
		
		/**
		 * Növeli a játékban lévõ különbözõ színek számát.
		 */
		public void plusColor() {
			if (numberOfColors < 9 || (numberOfColors < 10 && !MenuSystem.getSelectedTheme().equals("animals"))) {
				numberOfColors++;
				colors.setText("Number of colors: " + numberOfColors);
			}
		}
		
		/**
		 * Csökkenti a játékban lévõ különbözõ színek számát.
		 */
		public void minusColor() {
			if (numberOfColors > 4) {
				numberOfColors--;
				colors.setText("Number of colors: " + numberOfColors);
			}
		}
		
		/**
		 * Növeli a próbálkozások maximális számát.
		 */
		public void plusAttempt() {
			if (numberOfAttempts < 10) {
				numberOfAttempts++;
				attempts.setText("Number of attempts: " + numberOfAttempts);
			}
		}
		
		/**
		 * Csökkenti a próbálkozások maximális számát.
		 */
		public void minusAttempt() {
			if (numberOfAttempts > 1) {
				numberOfAttempts--;
				attempts.setText("Number of attempts: " + numberOfAttempts);
			}
		}
		
		/**
		 * Beállítja, hogy a rejtett sorban lehetnek-e azonos színû bábuk, vagy sem,
		 * és az erre vonatkozó JButton ikonját is megváltoztatja.  
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
