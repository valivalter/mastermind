package mastermind;

import java.awt.Graphics;

/**
 * A Theme leszármazottja, az állatos témához tartozó
 * egyedi rajzolásokért felelõs.
 */
public class AnimalsTheme extends Theme {

	/**
	 * Konstruktor, meghívja az õsosztály konstruktorát konkrét paraméterekkel.
	 * 
	 * @param game		A játékmenet, amelyhez létrejön a téma
	 */
	public AnimalsTheme(GamePanel game) {
		super("animal", "themes" + System.getProperty("file.separator") + "animals", game);
	}

	/**
	 * Kirajzolja a választott bábut.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawSelected(Graphics g) {
		g.drawImage(balls.get(game.getSelectedBall()), 230, 800, null);
	}

	/**
	 * Kirajzolja a választható bábukat.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawSelectableBalls(Graphics g) {
		for (int i = 0; i < game.getNumberOfColors(); i++) {
			g.drawImage(balls.get(i), 87*i, 883, null);
		}
	}

	/**
	 * Kirajzolja az összes sor aktuális állapotát a visszajelzõ bábukkal együtt.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawRows(Graphics g) {
		for (int x = 87; x <= 87 + 3*100; x += 100) {
			for (int y = 712; y >= 712-70*game.getCurrentRow(); y -= 70) {
				int ball = game.getBallPlaces().get((712-y)/70).get(x/100);
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
		for (int x = 87; x <= 87 + 3*100; x += 100) {
			g.drawImage(balls.get(game.getBallPlaces().get(game.getNumberOfAttempts()).get(x/100)), x, 12, null);
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
		return inArea(x, y, 87*number, 883, this.balls.get(0).getWidth(null), this.balls.get(0).getHeight(null));
	}
}
