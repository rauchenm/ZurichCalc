package model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Calc_Test {

	Calc tester = new Calc();

	@Test
	public void test_calc15() {

		double jahresbrutto = 35050.97;
		double expected = 2336.73;
		
		double actual = tester.calc15(jahresbrutto);
		assertEquals(expected, actual, 0.1);

	}
	
	@Test
	public void test_calc14() {

		double jahresbrutto = 35050.97;
		double expected = 2503.64;
		
		double actual = tester.calc14(jahresbrutto);
		assertEquals(expected, actual, 0.1);

	}
	
	@Test
	public void test_calcStunden() {

		double monatsbrutto = 2336.73;
		int stunden = 29;
		double expected =  1760.135;
		
		double actual = tester.calcStunden(stunden, monatsbrutto);
		assertEquals(expected, actual, 0.1);

	}
	
	@Test
	public void test_calcMoBrAdd() {

		double monatsbrutto = 2336.73;
		double ueberzahlung = 1705;
		double expected =  4041.73;
		
		double actual = tester.calcMoBrAdd(monatsbrutto, ueberzahlung);
		assertEquals(expected, actual, 0.1);

	}
	
	@Test
	public void test_calcErhoehung() {

		double monatsbrutto = 2336.73;
		double prozent = 1.76;
		double expected =  2377.86;
		
		double actual = tester.calcErhoehung(monatsbrutto, prozent);
		assertEquals(expected, actual, 0.1);

	}

}
