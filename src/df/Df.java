package df;

import java.util.ArrayList;

public class Df {
	private String[] X;
	private String[] Y;
	public Df(String x,String y) {// c normalement pour un attribut chacun a ameliorer
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
	
	public boolean equals(Df df) {
		return (X.equals(df.getX()) && Y.equals(df.getY()));// si ils ont le même ensemble d'attribut pour les deux parties
	}
	
	public ArrayList<String[]> decompoY(){// on va decomposer Y en differente partition ca va servir pour les consequenses logiques
		ArrayList<String[]> decomp = new ArrayList<>();
		for(int i = 0;i < Y.length;i++) {
			String[] st = new String[i+1];
			for(int a = 0;a<st.length - 1;a++) {
				st[a] = Y[a];
			}
			for(int j = i+1;j <Y.length;j++ ) {
				st[st.length-1] = Y[j];
				decomp.add(st);
			}
		}
		return decomp;// c'est un truc comme ca que il faut experimenter
	}
	
}
