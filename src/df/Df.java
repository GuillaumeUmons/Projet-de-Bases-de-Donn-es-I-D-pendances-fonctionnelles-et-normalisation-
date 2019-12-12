package df;

public class Df {
	private String[] X;
	private String[] Y;
	public Df(String x,String y) {// c normalement pour un attribut chacun à ameliorer
		X = new String[1];
		X[0] = x;
		Y = new String[1];
		Y[0] = y;
	}
	
	public Df(String[] x, String[] y) {//un ensemble d'attribut qui determine un ensemble d'attribut
		X = x;
		Y = y;
	}
	public Df(String x, String[] y) {// dependance qui determine plein de chose
		X = new String[1];
		X[0] = x;
		Y = y;
	}
	public Df(String[] x, String y) {// dependance singulière
		X = x;
		Y = new String[1];
		Y[0] = y;
	}
	
	public String[] getX() {
		return X;
	}
	public String[] getY() {
		return Y;
	}
	public String toString() {// pour afficher l'ensemble des attributs
		String x = "";
		String y = "";
		for(int i = 0;i < X.length;i++) {// pour concatener X
			if(i == X.length - 1) {
				x = x+X[i];
			}
			else {
				x = x+X[i]+",";
			} 
		}
		for(int i = 0;i < Y.length;i++) { // pour concatener Y
			if(i == Y.length - 1) {
				y = y+Y[i];
			}
			else {
				y = y+Y[i]+",";
			} 
		}
		return (x +" -> "+ y);
	}
}
