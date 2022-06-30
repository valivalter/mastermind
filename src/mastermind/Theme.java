package mastermind;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * A t�m�k absztrakt �soszt�lya, a t�m�kra k�z�sen jellemz�
 * rajzol�sok�rt felel�s.
 */
public abstract class Theme {

	/**
	 * Az aktu�lis j�t�kmenet
	 */
	protected GamePanel game;
	/**
	 * A t�ma h�tt�rk�pe, valamint azoknak a helyeknek a k�pe,
	 * ahov� elhelyezhet�ek az adott t�m�hoz tartoz� b�buk.
	 */
	protected Image background, ballPlace;
	/**
	 * A t�m�hoz tartoz� b�buk k�peinek list�ja
	 */
	protected ArrayList<Image> balls = new ArrayList<>();
	
	/**
	 * Konstruktor, �rt�ket ad az oszt�ly attrib�tumainak.
	 * 
	 * @param themeName			A t�ma neve
	 * @param directoryPath		A t�m�hoz tartoz� f�jlokat tartalmaz� mappa helye a f�jlrendszerben
	 * @param game				A j�t�kmenet, amelyhez l�trej�n a t�ma
	 */
	public Theme(String themeName, String directoryPath, GamePanel game) {
		this.game = game;
		try {
			String fileSeparator = System.getProperty("file.separator");
			background = ImageIO.read(new File(directoryPath + fileSeparator + "background.png"));
			ballPlace = ImageIO.read(new File(directoryPath + fileSeparator + "circle.png"));
			for (int i = 0; i < game.getNumberOfColors(); i++) {
				balls.add(ImageIO.read(new File(directoryPath + fileSeparator + themeName + "-" + i + ".png")));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �jrarajzolja az eg�sz j�t�kot a game attrib�tum �tmeneti v�szn�n.
	 */
	public void redraw() {
		Graphics g = game.getCanvas().getGraphics();
		g.drawImage(background, 0, 000, null);
		
		g.setColor(Color.BLACK);
		g.drawLine(500, 100, 500, 790);
		
		g.setFont(new Font("Papyrus", Font.PLAIN, 50));
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
	public abstract void drawSelected(Graphics g);
	/**
	 * Kirajzolja a v�laszthat� b�bukat.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public abstract void drawSelectableBalls(Graphics g);
	/**
	 * Kirajzolja az �sszes sor aktu�lis �llapot�t a visszajelz� b�bukkal egy�tt.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public abstract void drawRows(Graphics g);
	/**
	 * Kirajzolja az elrejtett sort.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public abstract void drawHiddenRow(Graphics g);
	
	/**
	 * Kirajzolja a megadott sorhoz tartoz� eredm�nyt
	 * fekete �s feh�r jelz�sekkel.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 * @param row	A sor sz�ma
	 */
	public void drawRowResult(Graphics g, int row) {
		int[] result = game.checkRow(row);
		try {
			Image white = ImageIO.read(new File("misc" + System.getProperty("file.separator") + "whitestick.png"));
			Image black = ImageIO.read(new File("misc" + System.getProperty("file.separator") + "blackstick.png"));
			int x = 535;
			int y = 730-70*row;
			for (int i = result[0]; i > 0; i--) {
				g.drawImage(black, x, y, null);
				x += 60;
			}
			for (int i = result[1]; i > 0; i--) {
				g.drawImage(white, x, y, null);
				x += 60;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Kirajzolja, hogy a j�t�kos nyert-e vagy vesztett, �s
	 * megjelen�t k�t v�laszthat� men�pontot: "Play again" �s
	 * "Quit to menu", majd lecser�li a j�t�khoz tartoz� listenert,
	 * hogy ezeket ki lehessen v�lasztani.
	 * 
	 * @param g				A Graphics objektum, amelyre rajzol a f�ggv�ny
	 * @param playerWon		Logikai �rt�k, hogy a j�t�kos nyert-e, vagy sem
	 */
	public void drawEndOfGame(Graphics g, boolean playerWon) {
		g.setColor(Color.WHITE);
		g.fillRect(230, 455, 320, 80);
		g.fillRect(230, 555, 320, 80);
		g.fillRect(110, 230, 560, 150);
		g.setColor(Color.BLACK);
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
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void drawPauseMenu(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(230, 305, 320, 80);
		g.fillRect(230, 405, 320, 80);
		g.fillRect(230, 505, 320, 80);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Papyrus", Font.BOLD, 50));
		g.drawString("Resume", 295, 360);
		g.drawString("Save game", 265, 460);
		g.drawString("Quit to menu", 240, 560);
		game.makePauseMenuListener();
	}
	
	/**
	 * Visszaadja logikai �rt�kk�nt, hogy a megadott pont
	 * a param�terk�nt megadott t�glalapon bel�l van-e.
	 * 
	 * @param clickX		A megadott pont x koordin�t�ja
	 * @param clickY		A megadott pont y koordin�t�ja
	 * @param upperLeftX	A t�glalap bal fels� sark�nak x koordin�t�ja
	 * @param upperLeftY	A t�glalap bal fels� sark�nak y koordin�t�ja
	 * @param width			A t�glalap sz�less�ge
	 * @param height		A t�glalap magass�ga
	 * @return				Logikai �rt�k, hogy a megadott pont a param�terk�nt megadott t�glalapon bel�l van-e
	 */
	public boolean inArea(int clickX, int clickY, int upperLeftX, int upperLeftY, int width, int height) {
		boolean properX = clickX >= upperLeftX && clickX <= upperLeftX + width;
		boolean properY = clickY >= upperLeftY && clickY <= upperLeftY + height;
		return properX && properY;
	}
	
	/**
	 * Visszaadja logikai �rt�kk�nt, hogy a megadott pont
	 * a param�terk�nt megadott k�r�n bel�l van-e.
	 * 
	 * @param clickX		A megadott pont x koordin�t�ja
	 * @param clickY		A megadott pont y koordin�t�ja
	 * @param upperLeftX	A k�r k�r� �rt n�gyzet bal fels� sark�nak x koordin�t�ja
	 * @param upperLeftY	A k�r k�r� �rt n�gyzet bal fels� sark�nak y koordin�t�ja
	 * @param d				A k�r �tm�r�je
	 * @return				Logikai �rt�k, hogy a megadott pont a param�terk�nt megadott k�r�n bel�l van-e
	 */
	public boolean inCircle(int clickX, int clickY, int upperLeftX, int upperLeftY, int d) {
		int centerX = upperLeftX + d/2;
		int centerY = upperLeftY + d/2;
		if (Math.sqrt(Math.pow(centerX - clickX, 2) + Math.pow(centerY - clickY, 2)) <= d/2)
			return true;
		return false;
	}
	
	/**
	 * Visszaadja logikai �rt�kk�nt, hogy a megadott pont
	 * az aktu�lis sorban van-e a megadott helyen bel�l, ahova lehet b�but helyezni.
	 * 
	 * @param x			A megadott pont x koordin�t�ja
	 * @param y			A megadott pont y koordin�t�ja
	 * @param number	Az aktu�lis sorban l�v� ilyen sorsz�m� b�buhely
	 * @return			Logikai �rt�k, hogy a megadott pont a megadott b�buhelyen bel�l van-e
	 */
	public boolean inCurrentRowArea(int x, int y, int number) {
		return inCircle(x, y, 100 + number*100, 730 - game.getCurrentRow()*70, ballPlace.getHeight(null));
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
	public abstract boolean inSelectionArea(int x, int y, int number);
}
