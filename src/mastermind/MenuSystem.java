package mastermind;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A menürendszert reprezentáló osztály és a JFrame
 * leszármazottja, az összes menüt
 * tartalmazza és ezek megjelenítését végzi el.
 * Az attribútumai és a tagfüggvényei statikusak, mivel a
 * legtöbb másik osztálynak szüksége van rá.
 *
 */
public class MenuSystem extends JFrame {
	
	/**
	 * A deszerializáció megsegítésére egy serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Az összes menüt tartalmazó JPanel.
	 */
	private static JPanel menus;
	/**
	 * A témaválasztó menüben aktuálisan kiválasztott téma neve.
	 */
	private static String selectedTheme = "spheres";
	
	/**
	 * A kontruktor létrehozza a programhoz szükséges ablakot
	 * és létrehozza a menüket.
	 */
	public MenuSystem() {
		super();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Mastermind");
		setResizable(false);
		
		menus = new JPanel(new CardLayout());
		menus.add(new MainMenu().getPanel(), "mainmenu");
		menus.add(new ThemesMenu().getPanel(), "themesmenu");
		menus.add(new NewGameMenu().getPanel(), "newgamemenu");
		menus.add(new CustomGameMenu().getPanel(), "customgamemenu");
		menus.add(new CreditsMenu().getPanel(), "creditsmenu");
		
		this.add(menus);
		this.pack();
		this.setSize(800, 1000);
	}
	
	/**
	 * Visszaadja a menüket tartalmazó JPanel-t.
	 * 
	 * @return		A menüket tartalmazó JPanel
	 */
	public static JPanel getMenus() {
		return menus;
	}
	
	/**
	 * Visszaadja az aktuális téma nevét
	 * 
	 * @return		Az aktuális téma neve
	 */
	public static String getSelectedTheme() {
		return selectedTheme;
	}
	
	/**
	 * Beállítja az aktuális témát.
	 * 
	 * @param s		A beállítandó téma neve
	 */
	public static void setSelectedTheme(String s) {
		selectedTheme = s;
	}
	
	/**
	 * A megadott nevû JPanel-t jeleníti meg.
	 * 
	 * @param name		A megjelenítendõ JPanel neve
	 */
	public static void showPanel(String name) {
		CardLayout cl = (CardLayout)menus.getLayout();
		cl.show(menus, name);
	}
	
	/**
	 * Létrehoz egy MenuSystem objektumot és láthatóvá teszi.
	 * 
	 * @param args		A parancssori argumentumok
	 */
	public static void main(String[] args) {
		MenuSystem menuSystem = new MenuSystem();
		menuSystem.setVisible(true);
	}
}
