package model;

public class Calc {
	
	public double calc15(double jahresbrutto){
		
		return jahresbrutto/15;
		
	}
	
	public double calc14(double jahresbrutto){
		
		return jahresbrutto/14;
		
	}
	
	public double calcStunden(double stunden, double monatsbrutto){
		
		return monatsbrutto/38.5 * stunden;
		
	}
	
	
	public double calcMoBrAdd(double monatsbrutto, double ueberzahlung){
		
		return monatsbrutto + ueberzahlung;
		
	}
	
	public double calcErhoehung(double monatsbrutto, double prozent){
		
		return monatsbrutto + (monatsbrutto/100) * prozent;
		
	}

}
