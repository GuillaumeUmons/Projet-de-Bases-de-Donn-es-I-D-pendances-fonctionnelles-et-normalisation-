package df;

import java.util.ArrayList;

public class Relation { // relation pour les données qui ne viennent pas de la base de donnée
	protected String name;
	protected ArrayList<String> names;
	private ArrayList<Df> df;
	public Relation(ArrayList<String> names,String name) {
		this.names = names;
		this.name = name;
	}
	public ArrayList<String> getnames() {
		return names;
	}
	public String get(int i) {
		String res = null;
		try {
			res = names.get(i);
		}catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return res;
	}//tester demain les trucs
	public String toString() {
		String a = "L'ensemble d'attriibut "+name+ "|";
		for(String i:names) {
			a = a+i+"|";
		}
		return a;
	}
	public void setdf() {
		
	}
	public boolean BCNF() {
		boolean bool = true;
		/**
		 * regarder si tout les attributs determine tout
		 */
		for(Df i : df) {
			if(equaltoatt(fermetureattribut(i,i.getX()))) {
				bool = false;
			}
		}
		// retourne la valeur de
		return bool;
	}
	public boolean NF3() {
		// retourne la valeur par rapport a l'ensemble de df
		if(BCNF()) {
			return true;
		}
		else {
			return (Boolean)null;
		}

	}
	// ajouter une methode String
	private ArrayList<String> fermetureattribut(Df df,String[] left) {
		// 
		ArrayList<String> array = new ArrayList<>();
		if(left.length == 1) {
			
		}
		else {
			
		}
		return array;
	}
	
	private boolean equaltoatt(ArrayList<String> att) {// pour regarder la fermeture d'attribut
		boolean bool = true;
		if(att.size() == names.size()) {
			for(int i = 0;i < att.size();i++) {
				if(!att.get(i).equals(names.get(i))) {
					bool = false;
					break;// on arrete la boucle c'est tout on a trouvé celui qui
				}
			}
			return bool;
		}
		else {
			return false;
		}
	}
}
