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
 * A j�t�k�rt felel�s komponens
 */
public class GamePanel extends JComponent {
	
	/**
	 * A deszerializ�ci� megseg�t�s�re egy serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A j�t�k t�m�ja
	 */
	private transient Theme theme;
	/**
	 * A j�t�k t�m�j�nak neve
	 */
	private String themeName;
	/**
	 * K�tdimenzi�s lista a j�t�k mez�inek t�rol�s�ra.
	 * A bels� ArrayList-nek mindig 4 eleme van, a k�ls� elemsz�ma
	 * pedig egyenl� a megengedett pr�b�lkoz�sok sz�m�nak eggyel n�velt
	 * �rt�k�vel. (A +1-edik sor az elrejtett sor t�rol�s�ra van)
	 * Egy elem �rt�ke a rajta l�v� b�bu sorsz�ma, vagy ha nincs
	 * rajta b�bu, akkor -1.
	 */
	private ArrayList<ArrayList<Integer>> ballPlaces = new ArrayList<>();
	/**
	 * BufferedImage az �tmeneti rajzol�sokhoz
	 */
	private transient Image canvas = new BufferedImage(800, 1000, BufferedImage.TYPE_INT_RGB);
	/**
	 * A v�lasztott b�bu sorsz�ma, a k�l�nb�z� sz�nek �s pr�b�lkoz�sok sz�ma, valamint az aktu�lis sor sorsz�ma, ahova le lehet tenni b�bukat.
	 */
	private int selectedBall, numberOfColors, numberOfAttempts, currentRow = 0;
	/**
	 * Logikai �rt�k, hogy lehet-e az elrejtett sorban egy sz�nb�l t�bb is.
	 */
	private boolean differentOnly;
	/**
	 * A komponensen t�rt�n� kattint�sokra figyel� MouseListener
	 */
	private transient MouseListener mouseListener;
	
	/**
	 * Konstruktor, be�ll�tja az attrib�tumok �rt�k�t �s
	 * hozz�ad a komponenshez egy p�ld�nyt az oszt�lyon bel�l 
	 * defini�lt KeyListener-b�l.
	 * 
	 * @param numberOfColors		A j�t�kban l�v� k�l�nb�z� sz�nek sz�ma
	 * @param numberOfAttempts		A megengedett pr�b�lkoz�sok sz�ma
	 * @param differentOnly			Logikai �rt�k, hogy lehet-e az elrejtett sorban egy sz�nb�l t�bb is, vagy sem
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
	 * L�trehozza az elrejtett sort v�letlenszer�en v�lasztva
	 * a b�buk k�z�l a param�ternek megfelel�en.
	 * 
	 * @param differentOnly		Logikai �rt�k, hogy lehet-e az elrejtett sorban egy sz�nb�l t�bb is, vagy sem
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
	 * Visszaadja a j�t�k t�m�j�t.
	 * 
	 * @return		A j�t�k t�m�ja
	 */
	public Theme getTheme() {
		return theme;
	}
	
	/**
	 * Visszaadja a j�t�kban l�v� k�l�nb�z� sz�nek sz�m�t.
	 * 
	 * @return		A j�t�kban l�v� k�l�nb�z� sz�nek sz�ma
	 */
	public int getNumberOfColors() {
		return numberOfColors;
	}
	
	/**
	 * Visszaadja a megengedett pr�b�lkoz�sok sz�m�t
	 * 
	 * @return		A pr�b�lkoz�sok sz�ma
	 */
	public int getNumberOfAttempts() {
		return numberOfAttempts;
	}
	
	/**
	 * Visszaadja az �tmeneti rajzol�sokhoz haszn�lt BufferedImage-et.
	 * 
	 * @return		Az �tmeneti rajzol�sokhoz haszn�lt BufferedImage
	 */
	public Image getCanvas() {
		return canvas;
	}
	
	/**
	 * Visszaadja a kiv�lasztott b�but.
	 * 
	 * @return 		A kiv�lasztott b�bu
	 */
	public int getSelectedBall() {
		return selectedBall;
	}
	
	/**
	 * Visszaadja a mez�ket t�rol� k�tdimenzi�s t�mb�t.
	 * 
	 * @return		A mez�ket t�rol� k�tdimenzi�s t�mb
	 */
	public ArrayList<ArrayList<Integer>> getBallPlaces() {
		return ballPlaces;
	}
	
	/**
	 * Visszaadja az aktu�lis sor sorsz�m�t, ahova le lehet tenni b�bukat.
	 * 
	 * @return		az aktu�lis sor sorsz�ma, ahova le lehet tenni b�bukat
	 */
	public int getCurrentRow() {
		return currentRow;
	}
	
	/**
	 * Lecser�li az objektum MouseListener-�t az
	 * oszt�lyon bel�l defini�lt EndOfGameMouseListener
	 * egy p�ld�ny�ra.
	 */
	public void makeEndGameListener() {
		this.removeMouseListener(mouseListener);
		mouseListener = new EndOfGameMouseListener();
		this.addMouseListener(mouseListener);
	}
	
	/**
	 * Lecser�li az objektum MouseListener-�t az
	 * oszt�lyon bel�l defini�lt PauseMenuMouseListener
	 * egy p�ld�ny�ra.
	 */
	public void makePauseMenuListener() {
		this.removeMouseListener(mouseListener);
		mouseListener = new PauseMenuMouseListener();
		this.addMouseListener(mouseListener);
	}
	
	/**
	 * Lecser�li az objektum MouseListener-�t az
	 * oszt�lyon bel�l defini�lt GamePanelMouseListener
	 * egy p�ld�ny�ra.
	 */
	public void makeGameMouseListener() {
		this.removeMouseListener(mouseListener);
		mouseListener = new GamePanelMouseListener();
		this.addMouseListener(mouseListener);
	}
	
	/**
	 * Szerializ�lja az objektumot.
	 * 
	 * @param out				Az OutputStream, ahova ki�r�dnak az adatok.
	 * @throws IOException		
	 */
	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	/**
	 * Deszerializ�lja az objektumot.
	 * 
	 * @param in				Az InputStream, ahonnan az adatok beolvas�sra ker�lnek.
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
	 * megjelen�ti az �tmeneti t�rol�sra haszn�lt BufferedImage tartalm�t.
	 * 
	 * @param g		A Graphics objektum, amelyre rajzol a f�ggv�ny
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(canvas, 0, 0, null);
		this.grabFocus();
	}
	
	/**
	 * Ki�rt�keli a megadott sort a j�t�kszab�lyok szerint.
	 * 
	 * @param row	A ki�rt�kelend� sor sorsz�ma
	 * @return		Egy k�telem� t�mb, amiben az els� elem a fekete, a m�sodik pedig a feh�r visszajelz� b�buk sz�ma
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
	 * MouseListener, amely a b�buk v�laszt�s��rt �s
	 * egy mez�n val� elhelyez�s�k�rt felel�s, valamint
	 * felismeri, ha a j�t�kos nyert vagy vesztett.
	 */
	public class GamePanelMouseListener extends MouseAdapter {
		
		/**
		 * Ha megfelel� helyen t�rt�nt eg�rfelenged�s, akkor a helyt�l
		 * f�gg�en vagy lecser�li a v�lasztott b�but, vagy elhelyez egy b�but
		 * a helyhez tartoz� mez�n, majd �jrarajzolja a komponenst. Ha ezzel
		 * valamilyen okb�l kifoly�lag v�ge lesz a j�t�knak, akkor megh�vja a
		 * t�ma megfelel� f�ggv�ny�t.
		 *
		 * @param me	A figyelt komponensen t�rt�n� eg�rfelenged�s hat�s�ra l�trej�v� esem�ny
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
	 * MouseListener, amely a j�t�k v�g�n megjelen�
	 * men�ben val� v�laszt�s�rt felel�s.
	 */
	public class EndOfGameMouseListener extends MouseAdapter {
		
		/**
		 * Az eg�rfelenged�s hely�t�l f�gg�en �jraind�tja
		 * a j�t�kot, vagy kil�p a f�men�be.
		 *
		 * @param me	A figyelt komponensen t�rt�n� eg�rfelenged�s hat�s�ra l�trej�v� esem�ny
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
	 * KeyListener, amely a j�t�k meg�ll�t�s�rt felel�s,
	 * ha a felhaszn�l� ezt megfelel�en jelzi.
	 */
	public class PauseKeyListener extends KeyAdapter {
		
		/**
		 * Megjelen�ti a j�t�k k�zbeni men�t, ha a
		 * felhaszn�l� megnyomja az "Escape" vagy a "P"
		 * billenty�t.
		 * 
		 * @param ke	A figyelt komponensen t�rt�n� billenty�lenyom�s hat�s�ra l�trej�v� esem�ny
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
	 * MouseListener, amely a j�t�k le�ll�t�sakor megjelen�
	 * men�ben val� v�laszt�s�rt felel�s.
	 */
	public class PauseMenuMouseListener extends MouseAdapter {
		/**
		 * Az eg�rfelenged�s hely�t�l f�gg�en visszat�r a j�t�khoz,
		 * kil�p a f�men�be, vagy egy JFileChooser seg�ts�g�vel elmenti
		 * a j�t�k aktu�lis �ll�s�t a felhaszn�l� �ltal megadott f�jlba.
		 * 
		 * @param me	A figyelt komponensen t�rt�n� eg�rfelenged�s hat�s�ra l�trej�v� esem�ny
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
