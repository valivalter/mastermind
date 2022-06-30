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
 * Minden men�t reprezent�l� oszt�ly ebb�l az absztrakt oszt�lyb�l sz�rmazik.
 */
public abstract class Menu {
	
	/**
	 * Maga a JPanel, ami minden sz�ks�ges dolgot t�rol az adott men�vel kapcsolatban.
	 */
	protected JPanel ownPanel;

	/**
	 * L�trehoz egy JButton-t, aminek be�ll�tja a m�ret�t, kin�zet�t, valamint 
	 * a megjelen�tend� sz�vegnek a param�tereit is.
	 * 
	 * @param name				A JButton �ltal ki�rt sz�veg, a felhaszn�l� ezt l�tja a k�perny�n
	 * @param actionCommand		Erre v�ltoztatja a f�ggv�ny a JButton action command-j�t
	 * @return					A l�trehozott JButton
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
	 * L�trehoz egy JButton-t, amin elv�gzi ugyanazokat a m�dos�t�sokat, mint amiket az el�z� f�ggv�ny,
	 * viszont hozz�ad a JButton-h�z egy ikont is. 
	 * 
	 * @param name				A JButton �ltal mutatott sz�veg, a felhaszn�l� ezt l�tja a k�perny�n
	 * @param actionCommand		Erre v�ltoztatja a f�ggv�ny a JButton action command-j�t
	 * @param iconPath			A megjelen�tend� ikon helye a f�jlrendszerben
	 * @return					A l�trehozott JButton
	 */
	public JButton createButtonWithIcon(String name, String actionCommand, String iconPath) {
		JButton button = createButton(name, actionCommand);
		button.setIcon(new ImageIcon(iconPath));
		button.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		return button;
	}
	
	/**
	 * Visszaadja az oszt�ly egyetlen attrib�tum�t, a men�t alkot� JPanel-t.
	 * 
	 * @return		A JPanel attrib�tum
	 */
	public JPanel getPanel() {
		return ownPanel;
	}
}
