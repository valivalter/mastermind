package mastermind;

import java.awt.Graphics;

/**
 * A Theme leszármazottja, az alapértelmezett témához tartozó
 * egyedi rajzolásokért felelõs.
 */
public class DefaultTheme extends Theme {
	
	/**
	 * Konstruktor, meghívja az õsosztály konstruktorát konkrét paraméterekkel.
	 * 
	 * @param game		A játékmenet, amelyhez létrejön a téma
	 */
	public DefaultTheme(GamePanel game) {
		super("sphere", "themes" + System.getProperty("file.separator") + "spheres", game);
	}
	
	/**
	 * Kirajzolja a választott bábut.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawSelected(Graphics g) {
		g.drawImage(balls.get(game.getSelectedBall()), 250, 820, null);
	}
	
	/**
	 * Kirajzolja a választható bábukat.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawSelectableBalls(Graphics g) {
		for (int i = 0; i < game.getNumberOfColors(); i++) {
			g.drawImage(balls.get(i), 25 + 75*i, 900, null);
		}
	}

	/**
	 * Kirajzolja az összes sor aktuális állapotát a visszajelzõ bábukkal együtt.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawRows(Graphics g) {
		for (int x = 100; x <= 400; x += 100) {
			for (int y = 730; y >= 730 - 70*game.getCurrentRow(); y -= 70) {
				int ball = game.getBallPlaces().get((730-y)/70).get(x/100 - 1);
				if (ball != -1)
					g.drawImage(balls.get(ball), x, y, null);
			}
		}
		for (int i = 0; i < game.getCurrentRow(); i++) {
			drawRowResult(g, i);
		}
	}

	/**
	 * Kirajzolja az elrejtett sort.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawHiddenRow(Graphics g) {
		for (int x = 100; x <= 400; x += 100) {
			g.drawImage(balls.get(game.getBallPlaces().get(game.getNumberOfAttempts()).get(x/100 - 1)), x, 30, null);
		}
	}

	/**
	 * Visszaadja logikai értékként, hogy a megadott pont
	 * a választható bábuk sorában van-e a megadott helyen belül.
	 * 
	 * @param x			A megadott pont x koordinátája
	 * @param y			A megadott pont y koordinátája
	 * @param number	A választható bábuk sorában lévõ ilyen sorszámú bábu
	 * @return			Logikai érték, hogy a megadott pont a megadott választható bábu helyén belül van-e
	 */
	public boolean inSelectionArea(int x, int y, int number) {
		return inCircle(x, y, 25 + 75*number, 900, this.balls.get(0).getHeight(null));
	}
}
