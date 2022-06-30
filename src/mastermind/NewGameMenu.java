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
 * A Menu oszt�ly lesz�rmazottja, a j�t�k neh�zs�g�t lehet itt kiv�lasztani,
 * (ekkor bizonyos el�re megadott param�terekkel indul a j�t�k) vagy
 * a "Custom" JButton-t v�lasztva megjelen�ti a "CustomGameMenu" oszt�ly �ltal
 * reprezent�lt men�t.
 */
public class NewGameMenu extends Menu {
	
	/**
	 * Az oszt�ly konstruktora, l�trehozza a men�h�z sz�ks�ges JButton-�ket,
	 * (amikhez hozz�adja az oszt�lyon bel�l defini�lt ActionListenert) �s
	 * ezeket elhelyezi a saj�t JPanel-j�n.
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
	 * A men�ben l�v� JButton-�k�rt felel�s ActionListener
	 */
	class NewGameMenuActionListener implements ActionListener {
		
		/**
		 * A "Back" JButton megnyom�s�nak hat�s�ra visszal�p a f�men�be.
		 * K�nny� fokozatot v�lasztva 6 sz�nnel, k�zepes neh�zs�gn�l 8 sz�nnel,
		 * neh�z fokozatn�l pedig 10 sz�nnel ind�tja a j�t�kot, mindegyik esetben
		 * 10 pr�b�lkoz�si lehet�s�ggel, valamint az utols� k�t esetben megengedi, 
		 * hogy egy sz�nb�l t�bb is lehessen az elrejtett sorban.
		 * A "Custom" JButton v�laszt�sa eset�n megjelen�ti a megfelel� men�t.
		 * 
		 * @param ae	Egy figyelt JButton megnyom�s�nak hat�s�ra l�trej�v� esem�ny
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
