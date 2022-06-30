package mastermind;

import java.awt.Graphics;

/**
 * A Theme lesz�rmazottja, a gy�m�lcs�s t�m�hoz tartoz�
 * egyedi rajzol�sok�rt felel�s.
 */
public class FruitsTheme extends Theme {
	
	/**
	 * Konstruktor, megh�vja az �soszt�ly konstruktor�t konkr�t param�terekkel.
	 * 
	 * @param game		A j�t�kmenet, amelyhez l�trej�n a t�ma
	 */
	public FruitsTheme(GamePanel game) {
		super("fruit", "themes" + System.getProperty("file.separator") + "fruits", game);
	}
	
	/**
	 * Kirajzolja a v�lasztott b�but.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawSelected(Graphics g) {
		g.drawImage(balls.get(game.getSelectedBall()), 230, 805, null);
	}

	/**
	 * Kirajzolja a v�laszthat� b�bukat.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawSelectableBalls(Graphics g) {
		for (int i = 0; i < game.getNumberOfColors(); i++) {
			g.drawImage(balls.get(i), 20 + 75*i, 885, null);
		}
	}

	/**
	 * Kirajzolja az �sszes sor aktu�lis �llapot�t a visszajelz� b�bukkal egy�tt.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawRows(Graphics g) {
		for (int x = 90; x <= 90 + 3*100; x += 100) {
			for (int y = 720; y >= 720-70*game.getCurrentRow(); y -= 70) {
				int ball = game.getBallPlaces().get((720-y)/70).get(x/100);
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
		for (int x = 90; x <= 90 + 3*100; x += 100) {
			g.drawImage(balls.get(game.getBallPlaces().get(game.getNumberOfAttempts()).get(x/100)), x, 20, null);
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
		return inCircle(x, y, 20 + 75*number, 885, this.balls.get(0).getHeight(null));
	}
}
