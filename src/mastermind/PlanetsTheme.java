package mastermind;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * A Theme lesz�rmazottja, a bolyg�s t�m�hoz tartoz�
 * egyedi rajzol�sok�rt felel�s.
 */
public class PlanetsTheme extends Theme {
	
	/**
	 * Konstruktor, megh�vja az �soszt�ly konstruktor�t konkr�t param�terekkel
	 * �s v�letlenszer�en be�ll�tja a h�tt�rk�pet n�gy lehets�ges k�pb�l v�lasztva.
	 * 
	 * @param game		A j�t�kmenet, amelyhez l�trej�n a t�ma
	 */
	public PlanetsTheme(GamePanel game) {
		super("planet", "themes" + System.getProperty("file.separator") + "planets", game);
		String fileSeparator = System.getProperty("file.separator");
		Random r = new Random();
		int random = r.nextInt(4);
		try {
			background = ImageIO.read(new File("themes" + fileSeparator + "planets" + fileSeparator + "background-" + random + ".png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �jrarajzolja az eg�sz j�t�kot a game attrib�tum �tmeneti v�szn�n.
	 * Fel�l�rja az �s f�ggv�ny�t, mert a t�ma s�t�ts�ge miatt m�s sz�nekre van sz�ks�g.
	 */
	@Override
	public void redraw() {
		Graphics g = game.getCanvas().getGraphics();
		g.drawImage(background, 0, 000, null);
		
		g.setFont(new Font("Papyrus", Font.PLAIN, 50));
		g.setColor(Color.WHITE);
		g.drawLine(500, 100, 500, 790);
		g.drawString("Selected:", 15, 865);
		
		for (int x = 100; x <= 400; x += 100) {
			for (int y = 730 - (game.getNumberOfAttempts()-1)*70; y <= 730; y += 70) {
				g.drawImage(ballPlace, x, y, null);
			}
		}
		
		drawSelectableBalls(g);
		drawSelected(g);
		drawRows(g);
	}
	
	/**
	 * Kirajzolja a v�lasztott b�but.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawSelected(Graphics g) {
		g.drawImage(balls.get(game.getSelectedBall()), 240, 810, null);
	}
	
	/**
	 * Kirajzolja a v�laszthat� b�bukat.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawSelectableBalls(Graphics g) {
		for (int i = 0; i < game.getNumberOfColors(); i++) {
			g.drawImage(balls.get(i), 10 + 77*i, 885, null);
		}
	}

	/**
	 * Kirajzolja az �sszes sor aktu�lis �llapot�t a visszajelz� b�bukkal egy�tt.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawRows(Graphics g) {
		for (int x = 96; x <= 96 + 3*100; x += 100) {
			for (int y = 725; y >= 725-70*game.getCurrentRow(); y -= 70) {
				int ball = game.getBallPlaces().get((725-y)/70).get(x/100);
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
		for (int x = 96; x <= 96 + 3*100; x += 100) {
			g.drawImage(balls.get(game.getBallPlaces().get(game.getNumberOfAttempts()).get(x/100)), x, 25, null);
		}
	}
	
	/**
	 * Kirajzolja, hogy a j�t�kos nyert-e vagy vesztett, �s
	 * megjelen�t k�t v�laszthat� men�pontot: "Play again" �s
	 * "Quit to menu", majd lecser�li a j�t�khoz tartoz� listenert,
	 * hogy ezeket ki lehessen v�lasztani.
	 * Fel�l�rja az �s f�ggv�ny�t, mert a t�ma s�t�ts�ge miatt m�s sz�nekre van sz�ks�g.
	 * 
	 * @param g				A Graphics objektum, amelyre rajzol a f�ggv�ny
	 * @param playerWon		Logikai �rt�k, hogy a j�t�kos nyert-e, vagy sem
	 */
	@Override
	public void drawEndOfGame(Graphics g, boolean playerWon) {
		g.setColor(Color.BLACK);
		g.fillRect(230, 455, 320, 80);
		g.fillRect(230, 555, 320, 80);
		g.fillRect(110, 230, 560, 150);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Papyrus", Font.BOLD, 50));
		g.drawString("Play again", 280, 510);
		g.drawString("Quit to menu", 240, 610);
		g.setFont(new Font("Papyrus", Font.BOLD, 130));
		if (playerWon) {
			g.drawString("You won!", 120, 350);
		}
		else {
			g.drawString("You lost!", 120, 350);
		}
		
		game.makeEndGameListener();
	}
	
	/**
	 * Kirajzolja a j�t�k meg�ll�t�sa eset�n a "Resume",
	 * "Save game" �s "Quit to menu" opci�kat, majd lecser�li a j�t�khoz tartoz� listenert,
	 * hogy ezeket ki lehessen v�lasztani.
	 * Fel�l�rja az �s f�ggv�ny�t, mert a t�ma s�t�ts�ge miatt m�s sz�nekre van sz�ks�g.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	@Override
	public void drawPauseMenu(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(230, 305, 320, 80);
		g.fillRect(230, 405, 320, 80);
		g.fillRect(230, 505, 320, 80);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Papyrus", Font.BOLD, 50));
		g.drawString("Resume", 295, 360);
		g.drawString("Save game", 265, 460);
		g.drawString("Quit to menu", 240, 560);
		game.makePauseMenuListener();
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
		return inCircle(x, y, 10 + 77*number, 885, this.balls.get(0).getHeight(null));
	}
}
