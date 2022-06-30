package mastermind;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
/**
 * A Menu oszt�ly lesz�rmazottja, a f�men�t reprezent�lja.
 * A men�ben l�v� JButton-�k k�z�l v�lasztva lehet kor�bban elmentett j�t�kot
 * bet�lteni, a programb�l kil�pni, �s �tl�pni az �j j�t�k ind�t�s��rt felel�s,
 * a t�mav�laszt�, vagy a "Credits" men�be.
 */
public class MainMenu extends Menu {
	
	/**
	 * Az oszt�ly konstruktora, l�trehozza a men�h�z sz�ks�ges JButton-�ket,
	 * (amikhez hozz�adja az oszt�lyon bel�l defini�lt ActionListenert) �s
	 * ezeket elhelyezi a saj�t JPanel-j�n. Ezenk�v�l megjelen�ti a j�t�k
	 * c�m�t is egy k�p seg�ts�g�vel.
	 */
	public MainMenu() {
		
		JLabel logo = new JLabel(new ImageIcon("misc" + System.getProperty("file.separator") + "logo.png"), JLabel.CENTER);
		logo.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton start = createButton("Start new game", "start");
		JButton load = createButton("Load game", "load");
		JButton themes = createButton("Themes", "themes");
		JButton credits = createButton("Credits", "credits");
		JButton exit = createButton("Exit", "exit");
		
		ownPanel = new JPanel();
		ownPanel.setBackground(Color.WHITE);
		ownPanel.setLayout(new BoxLayout(ownPanel, BoxLayout.Y_AXIS));
		ownPanel.add(Box.createRigidArea(new Dimension(0, 40)));
		ownPanel.add(logo);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		ownPanel.add(start);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 45)));
		ownPanel.add(load);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 45)));
		ownPanel.add(themes);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 45)));
		ownPanel.add(credits);
		ownPanel.add(Box.createRigidArea(new Dimension(0, 45)));
		ownPanel.add(exit);
		
		ActionListener al = new MainMenuActionListener();
		start.addActionListener(al);
		load.addActionListener(al);
		themes.addActionListener(al);
		credits.addActionListener(al);
		exit.addActionListener(al);
	}
	
	/**
	 * A men�ben l�v� JButton-�k�rt felel�s ActionListener
	 */
	class MainMenuActionListener implements ActionListener {
		
		/**
		 * Az "Exit" JButton megnyom�s�nak hat�s�ra v�get�r a program fut�sa.
		 * A "Credits", "Themes", �s "Start new game" gombokat megnyomva megjelenik
		 * a n�vnek megfelel� men�.
		 * A "Load game" megnyom�s�nak hat�s�ra egy JFileChooser seg�ts�g�vel lehet
		 * bet�lteni kor�bban elmnetett j�t�k�ll�sokat, �s ott folytatni, ahol
		 * abbahagytuk.
		 * 
		 * @param ae	Egy figyelt JButton megnyom�s�nak hat�s�ra l�trej�v� esem�ny
		 */
		public void actionPerformed(ActionEvent ae) {
			String command = ae.getActionCommand();
			if (command.equals("start")) {
				MenuSystem.showPanel("newgamemenu");
			}
			else if (command.equals("load")) {
				JFileChooser chooser = new JFileChooser("savedgames");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("DAT files", "dat");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showOpenDialog((Component)ae.getSource());
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	String fileAbsolutePath = chooser.getSelectedFile().getAbsolutePath();
			    	try {
			    		ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(fileAbsolutePath)));
			    		GamePanel gamePanel = (GamePanel)in.readObject();
			    		in.close();
			    		MenuSystem.getMenus().add(gamePanel, "game");
			    		MenuSystem.showPanel("game");
			    	}
			    	catch (Exception e) {
			    		e.printStackTrace();
			    	}
			    }
			}
			else if (command.equals("themes")) {
				MenuSystem.showPanel("themesmenu");
			}
			else if (command.equals("credits")) {
				MenuSystem.showPanel("creditsmenu");
			}
			else if (command.equals("exit")) {
				System.exit(0);
			}
		}
	}
}
