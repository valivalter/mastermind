package mastermind;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * A Theme leszármazottja, a bolygós témához tartozó
 * egyedi rajzolásokért felelõs.
 */
public class PlanetsTheme extends Theme {
	
	/**
	 * Konstruktor, meghívja az õsosztály konstruktorát konkrét paraméterekkel
	 * és véletlenszerûen beállítja a háttérképet négy lehetséges képbõl választva.
	 * 
	 * @param game		A játékmenet, amelyhez létrejön a téma
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
	 * Újrarajzolja az egész játékot a game attribútum átmeneti vásznán.
	 * Felülírja az õs függvényét, mert a téma sötétsége miatt más színekre van szükség.
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
	 * Kirajzolja a választott bábut.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawSelected(Graphics g) {
		g.drawImage(balls.get(game.getSelectedBall()), 240, 810, null);
	}
	
	/**
	 * Kirajzolja a választható bábukat.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawSelectableBalls(Graphics g) {
		for (int i = 0; i < game.getNumberOfColors(); i++) {
			g.drawImage(balls.get(i), 10 + 77*i, 885, null);
		}
	}

	/**
	 * Kirajzolja az összes sor aktuális állapotát a visszajelzõ bábukkal együtt.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
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
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void drawHiddenRow(Graphics g) {
		for (int x = 96; x <= 96 + 3*100; x += 100) {
			g.drawImage(balls.get(game.getBallPlaces().get(game.getNumberOfAttempts()).get(x/100)), x, 25, null);
		}
	}
	
	/**
	 * Kirajzolja, hogy a játékos nyert-e vagy vesztett, és
	 * megjelenít két választható menüpontot: "Play again" és
	 * "Quit to menu", majd lecseréli a játékhoz tartozó listenert,
	 * hogy ezeket ki lehessen választani.
	 * Felülírja az õs függvényét, mert a téma sötétsége miatt más színekre van szükség.
	 * 
	 * @param g				A Graphics objektum, amelyre rajzol a függvény
	 * @param playerWon		Logikai érték, hogy a játékos nyert-e, vagy sem
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
	 * Kirajzolja a játék megállítása esetén a "Resume",
	 * "Save game" és "Quit to menu" opciókat, majd lecseréli a játékhoz tartozó listenert,
	 * hogy ezeket ki lehessen választani.
	 * Felülírja az õs függvényét, mert a téma sötétsége miatt más színekre van szükség.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
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
	 * Visszaadja logikai értékként, hogy a megadott pont
	 * a választható bábuk sorában van-e a megadott helyen belül.
	 * 
	 * @param x			A megadott pont x koordinátája
	 * @param y			A megadott pont y koordinátája
	 * @param number	A választható bábuk sorában lévõ ilyen sorszámú bábu
	 * @return			Logikai érték, hogy a megadott pont a megadott választható bábu helyén belül van-e
	 */
	public boolean inSelectionArea(int x, int y, int number) {
		return inCircle(x, y, 10 + 77*number, 885, this.balls.get(0).getHeight(null));
	}
}
