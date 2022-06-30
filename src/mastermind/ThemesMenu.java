package mastermind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * A Menu oszt�ly lesz�rmazottja, a t�mav�laszt�s�rt felel�s men�,
 * ahol a felhaszn�l� n�gy megl�v� t�ma k�z�l v�laszthatja ki, hogy milyen
 * kin�zetet szeretne a j�t�knak.
 */
public class ThemesMenu extends Menu {
	
	/**
	 * Az �ppen kiv�lasztott t�m�t reprezent�l� JButton.
	 */
	private JButton selectedTheme;
	
	/**
	 * Az oszt�ly konstruktora, l�trehozza a men�h�z sz�ks�ges JButton-�ket,
	 * (amikhez hozz�adja az oszt�lyon bel�l defini�lt ActionListenert) �s
	 * ezeket elhelyezi a saj�t JPanel-j�n.
	 */
	public ThemesMenu() {
		
		JButton spheres = createButtonWithIcon("Default", "spheres", "misc" + System.getProperty("file.separator") + "tickedcheckbox.png");
		selectedTheme = spheres;
		JButton fruits = createButtonWithIcon("Fruits", "fruits", null);
		JButton animals = createButtonWithIcon("Animals", "animals", null);
		JButton planets = createButtonWithIcon("Planets", "planets", null);
		JButton back = createButton("Back", "back");
		
		ownPanel = new JPanel();
		ownPanel.setBackground(Color.WHITE);
		ownPanel.setLayout(new BoxLayout(ownPanel, BoxLayout.Y_AXIS));
		ownPanel.add(Box.createRigidArea(new Dimension(0, 100)));
		ownPanel.add(spheres);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 60)));
		ownPanel.add(fruits);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 60)));
		ownPanel.add(animals);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 60)));
		ownPanel.add(planets);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 60)));
		ownPanel.add(back);
		
		ActionListener al = new ThemesMenuActionListener();
		spheres.addActionListener(al);
		fruits.addActionListener(al);
		planets.addActionListener(al);
		animals.addActionListener(al);
		back.addActionListener(al);
	}
	
	/**
	 * A men�ben l�v� JButton-�k�rt felel�s ActionListener
	 */
	class ThemesMenuActionListener implements ActionListener {
		
		/**
		 * A "Back" JButton megnyom�s�nak hat�s�ra visszal�p a f�men�be,
		 * minden m�s JButton eset�n pedig �t�ll�tja a v�lasztott t�m�t
		 * a megfelel�re.
		 * 
		 * @param ae	Egy figyelt JButton megnyom�s�nak hat�s�ra l�trej�v� esem�ny
		 */
		public void actionPerformed(ActionEvent ae) {
			String command = ae.getActionCommand();
			if (command.equals("spheres") || command.equals("animals") || command.equals("fruits") || command.equals("planets")) {
				selectedTheme.setIcon(null);
				selectedTheme = (JButton)ae.getSource();
				selectedTheme.setIcon(new ImageIcon("misc" + System.getProperty("file.separator") + "tickedcheckbox.png"));
				MenuSystem.setSelectedTheme(command);
			}
			else if (command.equals("back")) {
				MenuSystem.showPanel("mainmenu");
			}
		}
	}
}
