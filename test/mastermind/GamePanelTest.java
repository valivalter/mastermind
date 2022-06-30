package mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Test;

/**
 * A GamePanel osztály paraméteres tesztelésére szolgáló osztály.
 */
@RunWith(Parameterized.class)
public class GamePanelTest {
	
	/**
	 * GamePanel objektum
	 */
	GamePanel game = new GamePanel(10, 10, false);
	/**
	 * A játék legelsõ sorába kerülõ négy érték
	 */
	int rowNumber1;
	int rowNumber2;
	int rowNumber3;
	int rowNumber4;
	/**
	 * A fekete visszajelzõ bábuk elvárt száma
	 */
	int expectedBlack;
	/**
	 * A fehér visszajelzõ bábuk elvárt száma
	 */
	int expectedWhite;
	
	/**
	 * Az tesztosztály konstruktora.
	 * 
	 * @param n1		A játék legelsõ sorának elsõ mezõjére kerülõ érték
	 * @param n2		A játék legelsõ sorának második mezõjére kerülõ érték
	 * @param n3		A játék legelsõ sorának harmadik mezõjére kerülõ érték
	 * @param n4		A játék legelsõ sorának negyedik mezõjére kerülõ érték
	 * @param black		A fekete visszajelzõ bábuk elvárt száma
	 * @param white		A fehér visszajelzõ bábuk elvárt száma
	 */
	public GamePanelTest(int n1, int n2, int n3, int n4, int black, int white) {
		game.getBallPlaces().set(game.getNumberOfAttempts(), new ArrayList<Integer>(Arrays.asList(new Integer[]{2, 0, 4, 7})));
		rowNumber1 = n1;
		rowNumber2 = n2;
		rowNumber3 = n3;
		rowNumber4 = n4;
		expectedBlack = black;
		expectedWhite = white;
	}
	
	/**
	 * A teszteléshez szükséges paramétereket hozza létre.
	 * 
	 * @return		A konstruktornak adandó paraméterek tömbje
	 */
	@Parameters
	public static List<Object[]> parameters() {
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[] {1, 1, 1, 1, 0, 0});
		params.add(new Object[] {0, 1, 1, 1, 0, 1});
		params.add(new Object[] {2, 0, 3, 6, 2, 0});
		params.add(new Object[] {1, 7, 4, 0, 1, 2});
		params.add(new Object[] {2, 0, 7, 4, 2, 2});
		params.add(new Object[] {4, 2, 0, 7, 1, 3});
		params.add(new Object[] {2, 0, 4, 8, 3, 0});
		params.add(new Object[] {4, 2, 0, 8, 0, 3});
		params.add(new Object[] {7, 2, 0, 4, 0, 4});
		params.add(new Object[] {2, 0, 4, 7, 4, 0});
		return params;
	}

	/**
	 * A játék sorait ellenõrzõ függvényt teszteli.
	 */
	@Test
	public void testCheckRow() {
		game.getBallPlaces().set(0, new ArrayList<Integer>(Arrays.asList(new Integer[]{rowNumber1, rowNumber2, rowNumber3, rowNumber4})));
		int[] result = game.checkRow(0);
		Assert.assertArrayEquals(new int[] {expectedBlack, expectedWhite}, result);
	}

	/**
	 * A rejtett sor feltételeknek megfelelõ generálását teszteli,
	 * vagyis hogyha csak különbözõ színeket lehet elrejteni, akkor
	 * valóban ez történik-e meg.
	 */
	@Test
	public void testHideColors() {
		// 5-ször tesztel, a paraméteres tesztelés miatt ez összesen 10*5 = 50 teszt,
		// így elenyészõ a valószínûsége annak, hogy pont olyan számok generálódtak mindig,
		// amik elrejtenék az algoritmus hibáját.
		for (int i = 0; i < 5; i++) {
			GamePanel different = new GamePanel(4, 10, true);
			GamePanel notDifferent = new GamePanel(10, 10, false);
			// a konstruktorban is lefut
			different.hideColors(true);
			notDifferent.hideColors(false);
			for (int j = 0; j < 4; j++) {
				Assert.assertNotEquals(-1, (int)different.getBallPlaces().get(different.getNumberOfAttempts()).get(j));
				Assert.assertNotEquals(-1, (int)notDifferent.getBallPlaces().get(notDifferent.getNumberOfAttempts()).get(j));
			}
			for (int j = 0; j < 4; j++) {
				for (int k = j+1; k < 4; k++) {
					ArrayList<Integer> hiddenRow = different.getBallPlaces().get(different.getNumberOfAttempts());
					Assert.assertNotEquals(hiddenRow.get(j), hiddenRow.get(k));
				}
			}
		}
	}
}
