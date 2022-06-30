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
 * A Menu oszt�ly lesz�rmazottja, a j�t�kban felhaszn�lt jogv�dett
 * tartalmak el�rhet�s�g�t �s k�sz�t�j�t jelen�ti meg.
 */
public class CreditsMenu extends Menu {
	
	/**
	 * Az oszt�ly konstruktora, l�trehozza a men�h�z sz�ks�ges Swing objektumokat,
	 * (A "Back" JButton-h�z hozz� is adja az oszt�lyon bel�l defini�lt ActionListenert) �s
	 * ezeket elhelyezi a saj�t JPanel-j�n.
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
	 * L�trehoz egy JLabel-t, aminek be�ll�tja a poz�ci�j�t, valamint 
	 * a megjelen�tend� sz�vegnek a param�tereit is.
	 * 
	 * @param name		A JLabel �ltal ki�rt sz�veg, a felhaszn�l� ezt l�tja a k�perny�n
	 * @return			A l�trehozott JLabel
	 */
	public JLabel createLabel(String name) {
		JLabel label = new JLabel(name, JLabel.CENTER);
		label.setFont(new Font("Trebuchet MS", Font.PLAIN, 25));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		return label;
	}
	
	/**
	 * L�trehoz egy JTextField-et �s be�ll�tja rajta, hogy csak a benne l�v�
	 * l�tsz�djon m�dos�thatatlanul, valamint be�ll�tja 
	 * a megjelen�tend� sz�veg a param�tereit is.
	 * Ez a f�gg�ny az�rt l�tezik, hogy ki lehessen jel�lni a k�perny�n
	 * l�v� linkeket.
	 * 
	 * @param name		A JTextField �ltal ki�rt sz�veg, a felhaszn�l� ezt l�tja a k�perny�n
	 * @return			A l�trehozott JTextField
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
	 * A men�ben l�v� JButton-�rt felel�s ActionListener
	 */
	class CreditsMenuActionListener implements ActionListener {
		
		/**
		 * A "Back" JButton megnyom�s�nak hat�s�ra visszal�p a f�men�be.
		 * 
		 * @param ae	A "Back" JButton megnyom�s�nak hat�s�ra l�trej�v� esem�ny
		 */
		public void actionPerformed(ActionEvent ae) {
			if (ae.getActionCommand().equals("back")) {
				MenuSystem.showPanel("mainmenu");
			}
		}
	}
}
