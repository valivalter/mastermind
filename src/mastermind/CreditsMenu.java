package mastermind;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * A Menu osztály leszármazottja, a játékban felhasznált jogvédett
 * tartalmak elérhetõségét és készítõjét jeleníti meg.
 */
public class CreditsMenu extends Menu {
	
	/**
	 * Az osztály konstruktora, létrehozza a menühöz szükséges Swing objektumokat,
	 * (A "Back" JButton-höz hozzá is adja az osztályon belül definiált ActionListenert) és
	 * ezeket elhelyezi a saját JPanel-jén.
	 */
	public CreditsMenu() {
		
		JLabel defaultBackground = createLabel("Default theme background by LisadiKaprio");
		JTextField defaultBackgroundLink = createSelectableText("https://opengameart.org/content/sunny-sky");
		JLabel fruits = createLabel("Fruits by Ravenmore");
		JTextField fruitsLink = createSelectableText("http://dycha.net");
		JLabel spaceBackground = createLabel("Planets theme background by fraang");
		JTextField spaceBackgroundLink = createSelectableText("https://opengameart.org/content/space-backgrounds-0");
		JLabel planets = createLabel("Planets by Viktor Hahn");
		JTextField planetsLink1 = createSelectableText("https://opengameart.org/content/15-planet-sprites");
		JTextField planetsLink2 = createSelectableText("https://opengameart.org/content/17-planet-sprites");
		JButton back = createButton("Back", "back");
		
		ownPanel = new JPanel();
		ownPanel.setBackground(Color.WHITE);
		ownPanel.setLayout(new BoxLayout(ownPanel, BoxLayout.Y_AXIS));
		ownPanel.add(Box.createRigidArea(new Dimension(0, 150)));
		ownPanel.add(defaultBackground);
		ownPanel.add(defaultBackgroundLink);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		ownPanel.add(fruits);
		ownPanel.add(fruitsLink);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		ownPanel.add(spaceBackground);
		ownPanel.add(spaceBackgroundLink);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 50)));
		ownPanel.add(planets);
		ownPanel.add(planetsLink1);
		ownPanel.add(planetsLink2);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 70)));
		ownPanel.add(back);
		
		ActionListener al = new CreditsMenuActionListener();
		back.addActionListener(al);
	}
	
	/**
	 * Létrehoz egy JLabel-t, aminek beállítja a pozícióját, valamint 
	 * a megjelenítendõ szövegnek a paramétereit is.
	 * 
	 * @param name		A JLabel által kiírt szöveg, a felhasználó ezt látja a képernyõn
	 * @return			A létrehozott JLabel
	 */
	public JLabel createLabel(String name) {
		JLabel label = new JLabel(name, JLabel.CENTER);
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		return label;
	}
	
	/**
	 * Létrehoz egy JTextField-et és beállítja rajta, hogy csak a benne lévõ
	 * látszódjon módosíthatatlanul, valamint beállítja 
	 * a megjelenítendõ szöveg a paramétereit is.
	 * Ez a függény azért létezik, hogy ki lehessen jelölni a képernyõn
	 * lévõ linkeket.
	 * 
	 * @param name		A JTextField által kiírt szöveg, a felhasználó ezt látja a képernyõn
	 * @return			A létrehozott JTextField
	 */
	public JTextField createSelectableText(String name) {
		JTextField textField = new JTextField(name, JTextField.CENTER);
		textField.setEditable(false);
		textField.setBackground(null);
		textField.setBorder(null);
		textField.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setMaximumSize(new Dimension(800, 35));
		return textField;
	}
	
	/**
	 * A menüben lévõ JButton-ért felelõs ActionListener
	 */
	class CreditsMenuActionListener implements ActionListener {
		
		/**
		 * A "Back" JButton megnyomásának hatására visszalép a fõmenübe.
		 * 
		 * @param ae	A "Back" JButton megnyomásának hatására létrejövõ esemény
		 */
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("back")) {
				MenuSystem.showPanel("mainmenu");
			}
		}
	}
}
