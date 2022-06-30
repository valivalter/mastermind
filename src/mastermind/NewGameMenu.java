package mastermind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
/**
 * A Menu osztály leszármazottja, a játék nehézségét lehet itt kiválasztani,
 * (ekkor bizonyos elõre megadott paraméterekkel indul a játék) vagy
 * a "Custom" JButton-t választva megjeleníti a "CustomGameMenu" osztály által
 * reprezentált menüt.
 */
public class NewGameMenu extends Menu {
	
	/**
	 * Az osztály konstruktora, létrehozza a menühöz szükséges JButton-öket,
	 * (amikhez hozzáadja az osztályon belül definiált ActionListenert) és
	 * ezeket elhelyezi a saját JPanel-jén.
	 */
	public NewGameMenu() {
		
		JButton easy = createButton("Easy", "easy");
		JButton medium = createButton("Medium", "medium");
		JButton hard = createButton("Hard", "hard");
		JButton custom = createButton("Custom", "custom");
		JButton back = createButton("Back", "back");
		
		ownPanel = new JPanel();
		ownPanel.setBackground(Color.WHITE);
		ownPanel.setLayout(new BoxLayout(ownPanel, BoxLayout.Y_AXIS));
		ownPanel.add(Box.createRigidArea(new Dimension(0, 100)));
		ownPanel.add(easy);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 60)));
		ownPanel.add(medium);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 60)));
		ownPanel.add(hard);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 60)));
		ownPanel.add(custom);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 60)));
		ownPanel.add(back);
		
		ActionListener al = new NewGameMenuActionListener();
		easy.addActionListener(al);
		medium.addActionListener(al);
		hard.addActionListener(al);
		custom.addActionListener(al);
		back.addActionListener(al);
	}

	/**
	 * A menüben lévõ JButton-ökért felelõs ActionListener
	 */
	class NewGameMenuActionListener implements ActionListener {
		
		/**
		 * A "Back" JButton megnyomásának hatására visszalép a fõmenübe.
		 * Könnyû fokozatot választva 6 színnel, közepes nehézségnél 8 színnel,
		 * nehéz fokozatnál pedig 10 színnel indítja a játékot, mindegyik esetben
		 * 10 próbálkozási lehetõséggel, valamint az utolsó két esetben megengedi, 
		 * hogy egy színbõl több is lehessen az elrejtett sorban.
		 * A "Custom" JButton választása esetén megjeleníti a megfelelõ menüt.
		 * 
		 * @param ae	Egy figyelt JButton megnyomásának hatására létrejövõ esemény
		 */
		public void actionPerformed(ActionEvent ae) {
			String command = ae.getActionCommand();
			if (command.equals("easy")) {
				MenuSystem.getMenus().add(new GamePanel(6, 10, true), "game");
				MenuSystem.showPanel("game");
			}
			else if (command.equals("medium")) {
				MenuSystem.getMenus().add(new GamePanel(8, 10, false), "game");
				MenuSystem.showPanel("game");
			}
			else if (command.equals("hard")) {
				if (MenuSystem.getSelectedTheme().equals("animals"))
					MenuSystem.getMenus().add(new GamePanel(9, 10, false), "game");
				else
					MenuSystem.getMenus().add(new GamePanel(10, 10, false), "game");
				MenuSystem.showPanel("game");
			}
			else if (command.equals("custom")) {
				MenuSystem.showPanel("customgamemenu");
			}
			else if (command.equals("back")) {
				MenuSystem.showPanel("mainmenu");
			}
		}
	}
}
