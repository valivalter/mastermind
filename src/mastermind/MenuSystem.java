package mastermind;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A men�rendszert reprezent�l� oszt�ly �s a JFrame
 * lesz�rmazottja, az �sszes men�t
 * tartalmazza �s ezek megjelen�t�s�t v�gzi el.
 * Az attrib�tumai �s a tagf�ggv�nyei statikusak, mivel a
 * legt�bb m�sik oszt�lynak sz�ks�ge van r�.
 *
 */
public class MenuSystem extends JFrame {
	
	/**
	 * A deszerializ�ci� megseg�t�s�re egy serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Az �sszes men�t tartalmaz� JPanel.
	 */
	private static JPanel menus;
	/**
	 * A t�mav�laszt� men�ben aktu�lisan kiv�lasztott t�ma neve.
	 */
	private static String selectedTheme = "spheres";
	
	/**
	 * A kontruktor l�trehozza a programhoz sz�ks�ges ablakot
	 * �s l�trehozza a men�ket.
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
	 * Visszaadja a men�ket tartalmaz� JPanel-t.
	 * 
	 * @return		A men�ket tartalmaz� JPanel
	 */
	public static JPanel getMenus() {
		return menus;
	}
	
	/**
	 * Visszaadja az aktu�lis t�ma nev�t
	 * 
	 * @return		Az aktu�lis t�ma neve
	 */
	public static String getSelectedTheme() {
		return selectedTheme;
	}
	
	/**
	 * Be�ll�tja az aktu�lis t�m�t.
	 * 
	 * @param s		A be�ll�tand� t�ma neve
	 */
	public static void setSelectedTheme(String s) {
		selectedTheme = s;
	}
	
	/**
	 * A megadott nev� JPanel-t jelen�ti meg.
	 * 
	 * @param name		A megjelen�tend� JPanel neve
	 */
	public static void showPanel(String name) {
		CardLayout cl = (CardLayout)menus.getLayout();
		cl.show(menus, name);
	}
	
	/**
	 * L�trehoz egy MenuSystem objektumot �s l�that�v� teszi.
	 * 
	 * @param args		A parancssori argumentumok
	 */
	public static void main(String[] args) {
		MenuSystem menuSystem = new MenuSystem();
		menuSystem.setVisible(true);
	}
}
