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
 * A témák absztrakt õsosztálya, a témákra közösen jellemzõ
 * rajzolásokért felelõs.
 */
public abstract class Theme {

	/**
	 * Az aktuális játékmenet
	 */
	protected GamePanel game;
	/**
	 * A téma háttérképe, valamint azoknak a helyeknek a képe,
	 * ahová elhelyezhetõek az adott témához tartozó bábuk.
	 */
	protected Image background, ballPlace;
	/**
	 * A témához tartozó bábuk képeinek listája
	 */
	protected ArrayList<Image> balls = new ArrayList<>();
	
	/**
	 * Konstruktor, értéket ad az osztály attribútumainak.
	 * 
	 * @param themeName			A téma neve
	 * @param directoryPath		A témához tartozó fájlokat tartalmazó mappa helye a fájlrendszerben
	 * @param game				A játékmenet, amelyhez létrejön a téma
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
	 * Újrarajzolja az egész játékot a game attribútum átmeneti vásznán.
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
	 * Kirajzolja a választott bábut.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public abstract void drawSelected(Graphics g);
	/**
	 * Kirajzolja a választható bábukat.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public abstract void drawSelectableBalls(Graphics g);
	/**
	 * Kirajzolja az összes sor aktuális állapotát a visszajelzõ bábukkal együtt.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public abstract void drawRows(Graphics g);
	/**
	 * Kirajzolja az elrejtett sort.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public abstract void drawHiddenRow(Graphics g);
	
	/**
	 * Kirajzolja a megadott sorhoz tartozó eredményt
	 * fekete és fehér jelzésekkel.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 * @param row	A sor száma
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
	 * Kirajzolja, hogy a játékos nyert-e vagy vesztett, és
	 * megjelenít két választható menüpontot: "Play again" és
	 * "Quit to menu", majd lecseréli a játékhoz tartozó listenert,
	 * hogy ezeket ki lehessen választani.
	 * 
	 * @param g				A Graphics objektum, amelyre rajzol a függvény
	 * @param playerWon		Logikai érték, hogy a játékos nyert-e, vagy sem
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
	 * Kirajzolja a játék megállítása esetén a "Resume",
	 * "Save game" és "Quit to menu" opciókat, majd lecseréli a játékhoz tartozó listenert,
	 * hogy ezeket ki lehessen választani.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
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
	 * Visszaadja logikai értékként, hogy a megadott pont
	 * a paraméterként megadott téglalapon belül van-e.
	 * 
	 * @param clickX		A megadott pont x koordinátája
	 * @param clickY		A megadott pont y koordinátája
	 * @param upperLeftX	A téglalap bal felsõ sarkának x koordinátája
	 * @param upperLeftY	A téglalap bal felsõ sarkának y koordinátája
	 * @param width			A téglalap szélessége
	 * @param height		A téglalap magassága
	 * @return				Logikai érték, hogy a megadott pont a paraméterként megadott téglalapon belül van-e
	 */
	public boolean inArea(int clickX, int clickY, int upperLeftX, int upperLeftY, int width, int height) {
		boolean properX = clickX >= upperLeftX && clickX <= upperLeftX + width;
		boolean properY = clickY >= upperLeftY && clickY <= upperLeftY + height;
		return properX && properY;
	}
	
	/**
	 * Visszaadja logikai értékként, hogy a megadott pont
	 * a paraméterként megadott körön belül van-e.
	 * 
	 * @param clickX		A megadott pont x koordinátája
	 * @param clickY		A megadott pont y koordinátája
	 * @param upperLeftX	A kör köré írt négyzet bal felsõ sarkának x koordinátája
	 * @param upperLeftY	A kör köré írt négyzet bal felsõ sarkának y koordinátája
	 * @param d				A kör átmérõje
	 * @return				Logikai érték, hogy a megadott pont a paraméterként megadott körön belül van-e
	 */
	public boolean inCircle(int clickX, int clickY, int upperLeftX, int upperLeftY, int d) {
		int centerX = upperLeftX + d/2;
		int centerY = upperLeftY + d/2;
		if (Math.sqrt(Math.pow(centerX - clickX, 2) + Math.pow(centerY - clickY, 2)) <= d/2)
			return true;
		return false;
	}
	
	/**
	 * Visszaadja logikai értékként, hogy a megadott pont
	 * az aktuális sorban van-e a megadott helyen belül, ahova lehet bábut helyezni.
	 * 
	 * @param x			A megadott pont x koordinátája
	 * @param y			A megadott pont y koordinátája
	 * @param number	Az aktuális sorban lévõ ilyen sorszámú bábuhely
	 * @return			Logikai érték, hogy a megadott pont a megadott bábuhelyen belül van-e
	 */
	public boolean inCurrentRowArea(int x, int y, int number) {
		return inCircle(x, y, 100 + number*100, 730 - game.getCurrentRow()*70, ballPlace.getHeight(null));
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
	public abstract boolean inSelectionArea(int x, int y, int number);
}
