package mastermind;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A játékért felelõs komponens
 */
public class GamePanel extends JComponent {
	
	/**
	 * A deszerializáció megsegítésére egy serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A játék témája
	 */
	private transient Theme theme;
	/**
	 * A játék témájának neve
	 */
	private String themeName;
	/**
	 * Kétdimenziós lista a játék mezõinek tárolására.
	 * A belsõ ArrayList-nek mindig 4 eleme van, a külsõ elemszáma
	 * pedig egyenló a megengedett próbálkozások számának eggyel növelt
	 * értékével. (A +1-edik sor az elrejtett sor tárolására van)
	 * Egy elem értéke a rajta lévõ bábu sorszáma, vagy ha nincs
	 * rajta bábu, akkor -1.
	 */
	private ArrayList<ArrayList<Integer>> ballPlaces = new ArrayList<>();
	/**
	 * BufferedImage az átmeneti rajzolásokhoz
	 */
	private transient Image canvas = new BufferedImage(800, 1000, BufferedImage.TYPE_INT_RGB);
	/**
	 * A választott bábu sorszáma, a különbözõ színek és próbálkozások száma, valamint az aktuális sor sorszáma, ahova le lehet tenni bábukat.
	 */
	private int selectedBall, numberOfColors, numberOfAttempts, currentRow = 0;
	/**
	 * Logikai érték, hogy lehet-e az elrejtett sorban egy színbõl több is.
	 */
	private boolean differentOnly;
	/**
	 * A komponensen történõ kattintásokra figyelõ MouseListener
	 */
	private transient MouseListener mouseListener;
	
	/**
	 * Konstruktor, beállítja az attribútumok értékét és
	 * hozzáad a komponenshez egy példányt az osztályon belül 
	 * definiált KeyListener-bõl.
	 * 
	 * @param numberOfColors		A játékban lévõ különbözõ színek száma
	 * @param numberOfAttempts		A megengedett próbálkozások száma
	 * @param differentOnly			Logikai érték, hogy lehet-e az elrejtett sorban egy színbõl több is, vagy sem
	 */
	public GamePanel(int numberOfColors, int numberOfAttempts, boolean differentOnly) {
		
		this.numberOfColors = numberOfColors;
		this.numberOfAttempts = numberOfAttempts;
		this.differentOnly = differentOnly;
		this.themeName = MenuSystem.getSelectedTheme();
		switch (themeName) {
			case "spheres": theme = new DefaultTheme(this); 	break;
			case "animals": theme = new AnimalsTheme(this); 	break;
			case "fruits": 	theme = new FruitsTheme(this);		break;
			case "planets": theme = new PlanetsTheme(this); 	break;
			default:	break;
		}
		
		selectedBall = 0;
		
		mouseListener = new GamePanelMouseListener();
		this.addMouseListener(mouseListener);
		
		this.setFocusable(true);
		this.addKeyListener(new PauseKeyListener());
		
		Integer[] ballPlaceArray = new Integer[]{-1, -1, -1, -1};
		for (int i = 0; i < numberOfAttempts+1; i++) {
			ballPlaces.add(new ArrayList<Integer>(Arrays.asList(ballPlaceArray)));
		}
		
		hideColors(differentOnly);
		
		theme.redraw();
	}
	
	/**
	 * Létrehozza az elrejtett sort véletlenszerûen választva
	 * a bábuk közül a paraméternek megfelelõen.
	 * 
	 * @param differentOnly		Logikai érték, hogy lehet-e az elrejtett sorban egy színbõl több is, vagy sem
	 */
	public void hideColors(boolean differentOnly) {
		if (differentOnly) {
			ArrayList<Integer> list = new ArrayList<>();
			for (int i = 0; i < numberOfColors; i++) {
				list.add(i);
			}
			Collections.shuffle(list);
			for (int i = 0; i < 4; i++) {
				int index = list.get(i);
				ballPlaces.get(numberOfAttempts).set(i, index);
			}
		}
		else {
			Random r = new Random();
			for (int i = 0; i < 4; i++) {
				int index = r.nextInt(numberOfColors);
				ballPlaces.get(numberOfAttempts).set(i, index);
			}
		}
	}
	
	/**
	 * Visszaadja a játék témáját.
	 * 
	 * @return		A játék témája
	 */
	public Theme getTheme() {
		return theme;
	}
	
	/**
	 * Visszaadja a játékban lévõ különbözõ színek számát.
	 * 
	 * @return		A játékban lévõ különbözõ színek száma
	 */
	public int getNumberOfColors() {
		return numberOfColors;
	}
	
	/**
	 * Visszaadja a megengedett próbálkozások számát
	 * 
	 * @return		A próbálkozások száma
	 */
	public int getNumberOfAttempts() {
		return numberOfAttempts;
	}
	
	/**
	 * Visszaadja az átmeneti rajzolásokhoz használt BufferedImage-et.
	 * 
	 * @return		Az átmeneti rajzolásokhoz használt BufferedImage
	 */
	public Image getCanvas() {
		return canvas;
	}
	
	/**
	 * Visszaadja a kiválasztott bábut.
	 * 
	 * @return 		A kiválasztott bábu
	 */
	public int getSelectedBall() {
		return selectedBall;
	}
	
	/**
	 * Visszaadja a mezõket tároló kétdimenziós tömböt.
	 * 
	 * @return		A mezõket tároló kétdimenziós tömb
	 */
	public ArrayList<ArrayList<Integer>> getBallPlaces() {
		return ballPlaces;
	}
	
	/**
	 * Visszaadja az aktuális sor sorszámát, ahova le lehet tenni bábukat.
	 * 
	 * @return		az aktuális sor sorszáma, ahova le lehet tenni bábukat
	 */
	public int getCurrentRow() {
		return currentRow;
	}
	
	/**
	 * Lecseréli az objektum MouseListener-ét az
	 * osztályon belül definiált EndOfGameMouseListener
	 * egy példányára.
	 */
	public void makeEndGameListener() {
		this.removeMouseListener(mouseListener);
		mouseListener = new EndOfGameMouseListener();
		this.addMouseListener(mouseListener);
	}
	
	/**
	 * Lecseréli az objektum MouseListener-ét az
	 * osztályon belül definiált PauseMenuMouseListener
	 * egy példányára.
	 */
	public void makePauseMenuListener() {
		this.removeMouseListener(mouseListener);
		mouseListener = new PauseMenuMouseListener();
		this.addMouseListener(mouseListener);
	}
	
	/**
	 * Lecseréli az objektum MouseListener-ét az
	 * osztályon belül definiált GamePanelMouseListener
	 * egy példányára.
	 */
	public void makeGameMouseListener() {
		this.removeMouseListener(mouseListener);
		mouseListener = new GamePanelMouseListener();
		this.addMouseListener(mouseListener);
	}
	
	/**
	 * Szerializálja az objektumot.
	 * 
	 * @param out				Az OutputStream, ahova kiíródnak az adatok.
	 * @throws IOException		
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	/**
	 * Deszerializálja az objektumot.
	 * 
	 * @param in				Az InputStream, ahonnan az adatok beolvasásra kerülnek.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		switch (themeName) {
			case "spheres": theme = new DefaultTheme(this); 	break;
			case "animals": theme = new AnimalsTheme(this); 	break;
			case "fruits": 	theme = new FruitsTheme(this);		break;
			case "planets": theme = new PlanetsTheme(this); 	break;
			default:	break;
		}
		canvas = new BufferedImage(800, 1000, BufferedImage.TYPE_INT_RGB);
		theme.redraw();
		
		mouseListener = new GamePanelMouseListener();
		this.addMouseListener(mouseListener);
		this.setFocusable(true);
		this.addKeyListener(new PauseKeyListener());
	}
	
	/**
	 * Kirajzolja a komponenst, vagyis
	 * megjeleníti az átmeneti tárolásra használt BufferedImage tartalmát.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a függvény
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(canvas, 0, 0, null);
		this.grabFocus();
	}
	
	/**
	 * Kiértékeli a megadott sort a játékszabályok szerint.
	 * 
	 * @param row	A kiértékelendõ sor sorszáma
	 * @return		Egy kételemû tömb, amiben az elsõ elem a fekete, a második pedig a fehér visszajelzõ bábuk száma
	 */
	public int[] checkRow(int row) {
		int black = 0, white = 0;
		ArrayList<Integer> hiddenList = new ArrayList<>();
		ArrayList<Integer> currentList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			if (ballPlaces.get(row).get(i).equals(ballPlaces.get(numberOfAttempts).get(i))) {
				black++;
				hiddenList.add(i);
				currentList.add(i);
			}
		}
		for (int i = 0; i < 4; i++) {
			if (!currentList.contains(i)) {
				for (int j = 0; j < 4; j++) {
					if (!hiddenList.contains(j)) {
						if (ballPlaces.get(row).get(i).equals(ballPlaces.get(numberOfAttempts).get(j))) {
							white++;
							currentList.add(i);
							hiddenList.add(j);
							break;
						}
					}
				}
			}
		}
		int[] result = new int[]{black, white};
		return result;
	}
	
	/**
	 * MouseListener, amely a bábuk választásáért és
	 * egy mezõn való elhelyezésükért felelõs, valamint
	 * felismeri, ha a játékos nyert vagy vesztett.
	 */
	public class GamePanelMouseListener extends MouseAdapter {
		
		/**
		 * Ha megfelelõ helyen történt egérfelengedés, akkor a helytõl
		 * függõen vagy lecseréli a választott bábut, vagy elhelyez egy bábut
		 * a helyhez tartozó mezõn, majd újrarajzolja a komponenst. Ha ezzel
		 * valamilyen okból kifolyólag vége lesz a játéknak, akkor meghívja a
		 * téma megfelelõ függvényét.
		 *
		 * @param me	A figyelt komponensen történõ egérfelengedés hatására létrejövõ esemény
		 */
		@Override
		public void mouseReleased(MouseEvent me) {
			int x = me.getX();
			int y = me.getY();
			for (int i = 0; i < numberOfColors; i++) {
				if (theme.inSelectionArea(x, y, i)) {
					selectedBall = i;
					theme.redraw();
					((JComponent)me.getSource()).repaint();
				}
			}
			for (int i = 0; i < 4; i++) {
				if (theme.inCurrentRowArea(x, y, i)) {
					ballPlaces.get(currentRow).set(i, selectedBall);
					theme.redraw();
					if (!(ballPlaces.get(currentRow).contains(-1))) {
						theme.drawRowResult(canvas.getGraphics(), currentRow);
						if (checkRow(currentRow)[0] == 4) {
							theme.drawHiddenRow(canvas.getGraphics());
							theme.drawEndOfGame(canvas.getGraphics(), true);
						}
						else if (currentRow == numberOfAttempts-1) {
							theme.drawHiddenRow(canvas.getGraphics());
							theme.drawEndOfGame(canvas.getGraphics(), false);
						}
						else
							currentRow++;
					}
					((JComponent)me.getSource()).repaint();
				}
			}
		}
		
	}
	
	/**
	 * MouseListener, amely a játék végén megjelenõ
	 * menüben való választásért felelõs.
	 */
	public class EndOfGameMouseListener extends MouseAdapter {
		
		/**
		 * Az egérfelengedés helyétõl függõen újraindítja
		 * a játékot, vagy kilép a fõmenübe.
		 *
		 * @param me	A figyelt komponensen történõ egérfelengedés hatására létrejövõ esemény
		 */
		@Override
		public void mouseReleased(MouseEvent me) {
			int x = me.getX();
			int y = me.getY();
			if (theme.inArea(x, y, 230, 455, 320, 80)) {
				MenuSystem.getMenus().add(new GamePanel(numberOfColors, numberOfAttempts, differentOnly), "game");
				MenuSystem.showPanel("game");
			}
			else if (theme.inArea(x, y, 230, 555, 320, 80)) {
				MenuSystem.showPanel("mainmenu");
			}
		}		
	}
	
	/**
	 * KeyListener, amely a játék megállításárt felelõs,
	 * ha a felhasználó ezt megfelelõen jelzi.
	 */
	public class PauseKeyListener extends KeyAdapter {
		
		/**
		 * Megjeleníti a játék közbeni menüt, ha a
		 * felhasználó megnyomja az "Escape" vagy a "P"
		 * billentyût.
		 * 
		 * @param ke	A figyelt komponensen történõ billentyûlenyomás hatására létrejövõ esemény
		 */
		@Override
		public void keyPressed(KeyEvent ke) {
			String keyText = KeyEvent.getKeyText(ke.getKeyCode());
			if (keyText.equals("Escape") || keyText.equals("P")) {
				theme.drawPauseMenu(canvas.getGraphics());
				((JComponent)ke.getSource()).repaint();
			}
			
		}
	}
	
	/**
	 * MouseListener, amely a játék leállításakor megjelenõ
	 * menüben való választásért felelõs.
	 */
	public class PauseMenuMouseListener extends MouseAdapter {
		/**
		 * Az egérfelengedés helyétõl függõen visszatér a játékhoz,
		 * kilép a fõmenübe, vagy egy JFileChooser segítségével elmenti
		 * a játék aktuális állását a felhasználó által megadott fájlba.
		 * 
		 * @param me	A figyelt komponensen történõ egérfelengedés hatására létrejövõ esemény
		 */
		@Override
		public void mouseReleased(MouseEvent me) {
			int x = me.getX();
			int y = me.getY();
			if (theme.inArea(x, y, 230, 305, 320, 80)) {
				theme.redraw();
				((JComponent)me.getSource()).repaint();
				((GamePanel)me.getSource()).makeGameMouseListener();
			}
			else if (theme.inArea(x, y, 230, 405, 320, 80)) {
				JFileChooser chooser = new JFileChooser("savedgames");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("DAT files", "dat");
			    chooser.setFileFilter(filter);
			    int returnVal = chooser.showSaveDialog((JComponent)me.getSource());
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	String fileAbsolutePath = chooser.getSelectedFile().getAbsolutePath();
			    	try {
			    		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(fileAbsolutePath + ".dat")));
			    		out.writeObject((GamePanel)me.getSource());
			    		out.close();
			    	}
			    	catch (Exception e) {
			    		e.printStackTrace();
			    	}
			    }
			}
			else if (theme.inArea(x, y, 230, 505, 320, 80)) {
				MenuSystem.showPanel("mainmenu");
			}
		}	
	}
}
