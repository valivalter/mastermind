package mastermind;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import mastermind.CustomGameMenu.CustomGameMenuActionListener;

/**
 * A CustomGameMenu osztály tesztelésére szolgáló osztály.
 */
public class CustomGameMenuTest {
	
	/**
	 * CustomGameMenu objektum
	 */
	CustomGameMenu menu;
	/**
	 * A menü osztályán belül definiált ActionListener objektum
	 */
	CustomGameMenuActionListener listener;
	/**
	 * GamePanel objektum
	 */
	GamePanel game;
	
	/**
	 * Minden teszt elvégzése elõtt inicializálja a tesztosztály attribútumait.
	 */
	@Before
	public void setup() {
		menu = new CustomGameMenu();
		listener = menu.new CustomGameMenuActionListener();
	}
	
	/**
	 * Teszteli a CustomGameMenu JLabel-ök létrehozására készült függvényét.
	 */
	@Test
	public void testCreateLabel() {
		JLabel label = new JLabel("nothing");
		label.setMaximumSize(new Dimension(50, 50));
		label.setFont(new Font("Arial", Font.BOLD, 50));
		
		label = menu.createLabel("test");
		
		Assert.assertEquals("test", label.getText());
		Assert.assertEquals(new Dimension(250, 100), label.getMaximumSize());
		Assert.assertEquals(new Font("Trebuchet MS", Font.PLAIN, 30), label.getFont());
		
	}
	
	/**
	 * Teszteli az CustomGameMenuActionListener színek számának növeléséért felelõs függvényét.
	 */
	@Test
	public void testPlusColor() {
		ActionEvent plusColor = new ActionEvent(0, 0, "plusColor");
		ActionEvent nothing = new ActionEvent(0, 0, "nothing");
		
		Assert.assertEquals(8, menu.getColors());
		
		listener.actionPerformed(nothing);
		Assert.assertEquals(8, menu.getColors());
		
		listener.actionPerformed(plusColor);
		Assert.assertEquals(9, menu.getColors());
		
		MenuSystem.setSelectedTheme("animals");
		listener.actionPerformed(plusColor);
		Assert.assertEquals(9, menu.getColors());
		
		MenuSystem.setSelectedTheme("spheres");
		listener.actionPerformed(plusColor);
		Assert.assertEquals(10, menu.getColors());
		
		listener.actionPerformed(plusColor);
		Assert.assertEquals(10, menu.getColors());
		
	}
	
	/**
	 * Teszteli az CustomGameMenuActionListener színek számának csökkentéséért felelõs függvényét.
	 */
	@Test
	public void testMinusColor() {
		ActionEvent minusColor = new ActionEvent(0, 0, "minusColor");
		ActionEvent nothing = new ActionEvent(0, 0, "nothing");
		
		Assert.assertEquals(8, menu.getColors());
		
		listener.actionPerformed(nothing);
		Assert.assertEquals(8, menu.getColors());
		
		listener.actionPerformed(minusColor);
		Assert.assertEquals(7, menu.getColors());
		
		for (int i = 0; i < 3; i++) {
			listener.actionPerformed(minusColor);
		}
		Assert.assertEquals(4, menu.getColors());
		
		listener.actionPerformed(minusColor);
		Assert.assertEquals(4, menu.getColors());
	}
	
	/**
	 * Teszteli az CustomGameMenuActionListener próbálkozások számának növeléséért felelõs függvényét.
	 */
	@Test
	public void testPlusAttempt() {
		ActionEvent plusAttempt = new ActionEvent(0, 0, "plusAttempt");
		ActionEvent nothing = new ActionEvent(0, 0, "nothing");
		
		Assert.assertEquals(8, menu.getAttempts());
		
		listener.actionPerformed(nothing);
		Assert.assertEquals(8, menu.getAttempts());
		
		listener.actionPerformed(plusAttempt);
		Assert.assertEquals(9, menu.getAttempts());
		
		listener.actionPerformed(plusAttempt);
		Assert.assertEquals(10, menu.getAttempts());
		
		listener.actionPerformed(plusAttempt);
		Assert.assertEquals(10, menu.getAttempts());
	}

	/**
	 * Teszteli az CustomGameMenuActionListener próbálkozások számának csökkentéséért felelõs függvényét.
	 */
	@Test
	public void testMinusAttempt() {
		ActionEvent minusAttempt = new ActionEvent(0, 0, "minusAttempt");
		ActionEvent nothing = new ActionEvent(0, 0, "nothing");
		
		Assert.assertEquals(8, menu.getAttempts());
		
		listener.actionPerformed(nothing);
		Assert.assertEquals(8, menu.getAttempts());
		
		listener.actionPerformed(minusAttempt);
		Assert.assertEquals(7, menu.getAttempts());
		
		for (int i = 0; i < 6; i++) {
			listener.actionPerformed(minusAttempt);
		}
		Assert.assertEquals(1, menu.getAttempts());
		
		listener.actionPerformed(minusAttempt);
		Assert.assertEquals(1, menu.getAttempts());
	}
	
	/**
	 * Teszteli az CustomGameMenuActionListenernek azt a függvényét, amely azért
	 * a logikai értékért felel, ami megadja, hogy az elrejtett sorban lehet-e egy színbõl
	 * több is, vagy sem.
	 */
	@Test
	public void testSetDifferentOnly() {
		ActionEvent differentColors = new ActionEvent(0, 0, "differentcolors");
		ActionEvent nothing = new ActionEvent(0, 0, "nothing");
		
		Assert.assertEquals(true, menu.getDifferentOnly());
		
		listener.actionPerformed(nothing);
		Assert.assertEquals(true, menu.getDifferentOnly());
		
		listener.actionPerformed(differentColors);
		Assert.assertEquals(false, menu.getDifferentOnly());
		
		listener.actionPerformed(differentColors);
		Assert.assertEquals(true, menu.getDifferentOnly());
	}
}
