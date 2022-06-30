package mastermind;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * A Theme oszt�ly param�teres tesztel�s�re szolg�l� oszt�ly.
 */
@RunWith(Parameterized.class)
public class ThemeTest {
	
	/**
	 * T�ma objektum alap�rtelmezett t�m�val
	 */
	Theme theme = new DefaultTheme(new GamePanel(10, 10, true));
	/**
	 * A k�rd�ses pont x koordin�t�ja
	 */
	int x;
	/**
	 * A k�rd�ses pont y koordin�t�ja
	 */
	int y;
	/**
	 * A t�glalap, vagy a k�r k�r� �rt n�gyzet bal fels� sark�nak x koordin�t�ja
	 */
	int upperLeftX;
	/**
	 * A t�glalap, vagy a k�r k�r� �rt n�gyzet bal fels� sark�nak y koordin�t�ja
	 */
	int upperLeftY;
	/**
	 * A t�glalap sz�less�ge
	 */
	int width;
	/**
	 * A t�glalap magass�ga
	 */
	int height;
	/**
	 * A k�r �tm�r�je
	 */
	int d;
	/**
	 * Logikai �rt�k, igaz, ha azt v�rjuk el, hogy a pont a t�glalap ter�let�n bel�l legyen
	 */
	boolean inArea;
	/**
	 * Logikai �rt�k, igaz, ha azt v�rjuk el, hogy a pont a k�r ter�let�n bel�l legyen
	 */
	boolean inCircle;
	
	
	/**
	 * A tesztoszt�ly konstruktora
	 * 
	 * @param x				A k�rd�ses pont x koordin�t�ja
	 * @param y				A k�rd�ses pont y koordin�t�ja
	 * @param upperLeftX	A t�glalap, vagy a k�r k�r� �rt n�gyzet bal fels� sark�nak x koordin�t�ja
	 * @param upperLeftY	A t�glalap, vagy a k�r k�r� �rt n�gyzet bal fels� sark�nak y koordin�t�ja
	 * @param width			A t�glalap sz�less�ge
	 * @param height		A t�glalap magass�ga
	 * @param d				A k�r �tm�r�je
	 * @param inArea		Logikai �rt�k, igaz, ha azt v�rjuk el, hogy a pont a t�glalap ter�let�n bel�l legyen
	 * @param inCircle		Logikai �rt�k, igaz, ha azt v�rjuk el, hogy a pont a k�r ter�let�n bel�l legyen
	 */
	public ThemeTest(int x, int y, int upperLeftX, int upperLeftY, int width, int height, int d, boolean inArea, boolean inCircle) {
		this.x = x;
		this.y = y;
		this.upperLeftX = upperLeftX;
		this.upperLeftY = upperLeftY;
		this.width = width;
		this.height = height;
		this.d = d;
		this.inArea = inArea;
		this.inCircle = inCircle;
	}
	
	/**
	 * A tesztel�shez sz�ks�ges param�tereket hozza l�tre.
	 * 
	 * @return		A konstruktornak adand� param�terek t�mbje
	 */
	@Parameters
	public static List<Object[]> parameters() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] {0, 0, 0, 0, 1, 1, 2, true, false});
		params.add(new Object[] {1, 1, 0, 0, 2, 2, 2, true, true});
		params.add(new Object[] {2, 2, 0, 0, 1, 1, 1, false, false});
		params.add(new Object[] {2, 3, 1, 2, 1, 2, 4, true, true});
		params.add(new Object[] {112, 115, 110, 110, 1, 10, 1, false, false});
		return params;
	}

	/**
	 * Teszteli a f�ggv�nyt, amely visszaadja, hogy egy megadott pont a megadott t�glalap ter�let�n bel�l van-e.
	 */
	@Test
	public void testInArea() {
		if (inArea) {
			Assert.assertTrue(theme.inArea(x, y, upperLeftX, upperLeftY, width, height));
		}
		else {
			Assert.assertFalse(theme.inArea(x, y, upperLeftX, upperLeftY, width, height));
		}
	}
	
	/**
	 * Teszteli a f�ggv�nyt, amely visszaadja, hogy egy megadott pont a megadott k�r ter�let�n bel�l van-e.
	 */
	@Test
	public void testInCircle() {
		if (inCircle) {
			Assert.assertTrue(theme.inCircle(x, y, upperLeftX, upperLeftY, d));
		}
		else {
			Assert.assertFalse(theme.inCircle(x, y, upperLeftX, upperLeftY, d));
		}
	}

}
