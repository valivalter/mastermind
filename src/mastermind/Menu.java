package mastermind;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Minden menüt reprezentáló osztály ebbõl az absztrakt osztályból származik.
 */
public abstract class Menu {
	
	/**
	 * Maga a JPanel, ami minden szükséges dolgot tárol az adott menüvel kapcsolatban.
	 */
	protected JPanel ownPanel;

	/**
	 * Létrehoz egy JButton-t, aminek beállítja a méretét, kinézetét, valamint 
	 * a megjelenítendõ szövegnek a paramétereit is.
	 * 
	 * @param name				A JButton által kiírt szöveg, a felhasználó ezt látja a képernyõn
	 * @param actionCommand		Erre változtatja a függvény a JButton action command-ját
	 * @return					A létrehozott JButton
	 */
	public JButton createButton(String name, String actionCommand) {
		JButton button = new JButton(name);
		button.setActionCommand(actionCommand);
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.setMaximumSize(new Dimension(300, 100));
		button.setFont(new Font("Trebuchet MS", Font.PLAIN, 30));
		button.setFocusPainted(false);
		button.setBackground(new Color(240,255,240));
		return button;
	}
	
	/**
	 * Létrehoz egy JButton-t, amin elvégzi ugyanazokat a módosításokat, mint amiket az elõzõ függvény,
	 * viszont hozzáad a JButton-höz egy ikont is. 
	 * 
	 * @param name				A JButton által mutatott szöveg, a felhasználó ezt látja a képernyõn
	 * @param actionCommand		Erre változtatja a függvény a JButton action command-ját
	 * @param iconPath			A megjelenítendõ ikon helye a fájlrendszerben
	 * @return					A létrehozott JButton
	 */
	public JButton createButtonWithIcon(String name, String actionCommand, String iconPath) {
		JButton button = createButton(name, actionCommand);
		button.setIcon(new ImageIcon(iconPath));
		button.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		return button;
	}
	
	/**
	 * Visszaadja az osztály egyetlen attribútumát, a menüt alkotó JPanel-t.
	 * 
	 * @return		A JPanel attribútum
	 */
	public JPanel getPanel() {
		return ownPanel;
	}
}
