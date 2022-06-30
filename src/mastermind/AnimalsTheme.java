package mastermind;

import java.awt.Graphics;

/**
 * A Theme lesz�rmazottja, az �llatos t�m�hoz tartoz�
 * egyedi rajzol�sok�rt felel�s.
 */
public class AnimalsTheme extends Theme {

	/**
	 * Konstruktor, megh�vja az �soszt�ly konstruktor�t konkr�t param�terekkel.
	 * 
	 * @param game		A j�t�kmenet, amelyhez l�trej�n a t�ma
	 */
	public AnimalsTheme(GamePanel game) {
		super("animal", "themes" + System.getProperty("file.separator") + "animals", game);
	}

	/**
	 * Kirajzolja a v�lasztott b�but.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawSelected(Graphics g) {
		g.drawImage(balls.get(game.getSelectedBall()), 230, 800, null);
	}

	/**
	 * Kirajzolja a v�laszthat� b�bukat.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawSelectableBalls(Graphics g) {
		for (int i = 0; i < game.getNumberOfColors(); i++) {
			g.drawImage(balls.get(i), 87*i, 883, null);
		}
	}

	/**
	 * Kirajzolja az �sszes sor aktu�lis �llapot�t a visszajelz� b�bukkal egy�tt.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
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
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawHiddenRow(Graphics g) {
		for (int x = 87; x <= 87 + 3*100; x += 100) {
			g.drawImage(balls.get(game.getBallPlaces().get(game.getNumberOfAttempts()).get(x/100)), x, 12, null);
		}
	}
	
	/**
	 * Visszaadja logikai �rt�kk�nt, hogy a megadott pont
	 * a v�laszthat� b�buk sor�ban van-e a megadott helyen bel�l.
	 * 
	 * @param x			A megadott pont x koordin�t�ja
	 * @param y			A megadott pont y koordin�t�ja
	 * @param number	A v�laszthat� b�buk sor�ban l�v� ilyen sorsz�m� b�bu
	 * @return			Logikai �rt�k, hogy a megadott pont a megadott v�laszthat� b�bu hely�n bel�l van-e
	 */
	public boolean inSelectionArea(int x, int y, int number) {
		return inArea(x, y, 87*number, 883, this.balls.get(0).getWidth(null), this.balls.get(0).getHeight(null));
	}
}
