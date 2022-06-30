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
 * A GamePanel oszt�ly param�teres tesztel�s�re szolg�l� oszt�ly.
 */
@RunWith(Parameterized.class)
public class GamePanelTest {
	
	/**
	 * GamePanel objektum
	 */
	GamePanel game = new GamePanel(10, 10, false);
	/**
	 * A j�t�k legels� sor�ba ker�l� n�gy �rt�k
	 */
	int rowNumber1;
	int rowNumber2;
	int rowNumber3;
	int rowNumber4;
	/**
	 * A fekete visszajelz� b�buk elv�rt sz�ma
	 */
	int expectedBlack;
	/**
	 * A feh�r visszajelz� b�buk elv�rt sz�ma
	 */
	int expectedWhite;
	
	/**
	 * Az tesztoszt�ly konstruktora.
	 * 
	 * @param n1		A j�t�k legels� sor�nak els� mez�j�re ker�l� �rt�k
	 * @param n2		A j�t�k legels� sor�nak m�sodik mez�j�re ker�l� �rt�k
	 * @param n3		A j�t�k legels� sor�nak harmadik mez�j�re ker�l� �rt�k
	 * @param n4		A j�t�k legels� sor�nak negyedik mez�j�re ker�l� �rt�k
	 * @param black		A fekete visszajelz� b�buk elv�rt sz�ma
	 * @param white		A feh�r visszajelz� b�buk elv�rt sz�ma
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
	 * A tesztel�shez sz�ks�ges param�tereket hozza l�tre.
	 * 
	 * @return		A konstruktornak adand� param�terek t�mbje
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
	 * A j�t�k sorait ellen�rz� f�ggv�nyt teszteli.
	 */
	@Test
	public void testCheckRow() {
		game.getBallPlaces().set(0, new ArrayList<Integer>(Arrays.asList(new Integer[]{rowNumber1, rowNumber2, rowNumber3, rowNumber4})));
		int[] result = game.checkRow(0);
		Assert.assertArrayEquals(new int[] {expectedBlack, expectedWhite}, result);
	}

	/**
	 * A rejtett sor felt�teleknek megfelel� gener�l�s�t teszteli,
	 * vagyis hogyha csak k�l�nb�z� sz�neket lehet elrejteni, akkor
	 * val�ban ez t�rt�nik-e meg.
	 */
	@Test
	public void testHideColors() {
		// 5-sz�r tesztel, a param�teres tesztel�s miatt ez �sszesen 10*5 = 50 teszt,
		// �gy eleny�sz� a val�sz�n�s�ge annak, hogy pont olyan sz�mok gener�l�dtak mindig,
		// amik elrejten�k az algoritmus hib�j�t.
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
