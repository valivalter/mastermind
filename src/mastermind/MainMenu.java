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
 * A Menu osztály leszármazottja, a fõmenüt reprezentálja.
 * A menüben lévõ JButton-ök közül választva lehet korábban elmentett játékot
 * betölteni, a programból kilépni, és átlépni az új játék indításáért felelõs,
 * a témaválasztó, vagy a "Credits" menübe.
 */
public class MainMenu extends Menu {
	
	/**
	 * Az osztály konstruktora, létrehozza a menühöz szükséges JButton-öket,
	 * (amikhez hozzáadja az osztályon belül definiált ActionListenert) és
	 * ezeket elhelyezi a saját JPanel-jén. Ezenkívûl megjeleníti a játék
	 * címét is egy kép segítségével.
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
	 * A menüben lévõ JButton-ökért felelõs ActionListener
	 */
	class MainMenuActionListener implements ActionListener {
		
		/**
		 * Az "Exit" JButton megnyomásának hatására végetér a program futása.
		 * A "Credits", "Themes", és "Start new game" gombokat megnyomva megjelenik
		 * a névnek megfelelõ menü.
		 * A "Load game" megnyomásának hatására egy JFileChooser segítségével lehet
		 * betölteni korábban elmnetett játékállásokat, és ott folytatni, ahol
		 * abbahagytuk.
		 * 
		 * @param ae	Egy figyelt JButton megnyomásának hatására létrejövõ esemény
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
