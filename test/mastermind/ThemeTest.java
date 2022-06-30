package mastermind;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * A Theme osztály paraméteres tesztelésére szolgáló osztály.
 */
@RunWith(Parameterized.class)
public class ThemeTest {
	
	/**
	 * Téma objektum alapértelmezett témával
	 */
	Theme theme = new DefaultTheme(new GamePanel(10, 10, true));
	/**
	 * A kérdéses pont x koordinátája
	 */
	int x;
	/**
	 * A kérdéses pont y koordinátája
	 */
	int y;
	/**
	 * A téglalap, vagy a kör köré írt négyzet bal felsõ sarkának x koordinátája
	 */
	int upperLeftX;
	/**
	 * A téglalap, vagy a kör köré írt négyzet bal felsõ sarkának y koordinátája
	 */
	int upperLeftY;
	/**
	 * A téglalap szélessége
	 */
	int width;
	/**
	 * A téglalap magassága
	 */
	int height;
	/**
	 * A kör átmérõje
	 */
	int d;
	/**
	 * Logikai érték, igaz, ha azt várjuk el, hogy a pont a téglalap területén belül legyen
	 */
	boolean inArea;
	/**
	 * Logikai érték, igaz, ha azt várjuk el, hogy a pont a kör területén belül legyen
	 */
	boolean inCircle;
	
	
	/**
	 * A tesztosztály konstruktora
	 * 
	 * @param x				A kérdéses pont x koordinátája
	 * @param y				A kérdéses pont y koordinátája
	 * @param upperLeftX	A téglalap, vagy a kör köré írt négyzet bal felsõ sarkának x koordinátája
	 * @param upperLeftY	A téglalap, vagy a kör köré írt négyzet bal felsõ sarkának y koordinátája
	 * @param width			A téglalap szélessége
	 * @param height		A téglalap magassága
	 * @param d				A kör átmérõje
	 * @param inArea		Logikai érték, igaz, ha azt várjuk el, hogy a pont a téglalap területén belül legyen
	 * @param inCircle		Logikai érték, igaz, ha azt várjuk el, hogy a pont a kör területén belül legyen
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
	 * A teszteléshez szükséges paramétereket hozza létre.
	 * 
	 * @return		A konstruktornak adandó paraméterek tömbje
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
	 * Teszteli a függvényt, amely visszaadja, hogy egy megadott pont a megadott téglalap területén belül van-e.
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
	 * Teszteli a függvényt, amely visszaadja, hogy egy megadott pont a megadott kör területén belül van-e.
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
