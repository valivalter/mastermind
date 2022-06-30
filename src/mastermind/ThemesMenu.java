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
 * A Menu osztály leszármazottja, a témaválasztásért felelõs menü,
 * ahol a felhasználó négy meglévõ téma közül választhatja ki, hogy milyen
 * kinézetet szeretne a játéknak.
 */
public class ThemesMenu extends Menu {
	
	/**
	 * Az éppen kiválasztott témát reprezentáló JButton.
	 */
	private JButton selectedTheme;
	
	/**
	 * Az osztály konstruktora, létrehozza a menühöz szükséges JButton-öket,
	 * (amikhez hozzáadja az osztályon belül definiált ActionListenert) és
	 * ezeket elhelyezi a saját JPanel-jén.
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
	 * A menüben lévõ JButton-ökért felelõs ActionListener
	 */
	class ThemesMenuActionListener implements ActionListener {
		
		/**
		 * A "Back" JButton megnyomásának hatására visszalép a fõmenübe,
		 * minden más JButton esetén pedig átállítja a választott témát
		 * a megfelelõre.
		 * 
		 * @param ae	Egy figyelt JButton megnyomásának hatására létrejövõ esemény
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
