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
		String a = "L'ensemble d'attriibut de  "+name+ " sont :|";
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
			if(!equaltoatt(fermetureattribut(i.getX()))) {
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
	private ArrayList<String> fermetureattribut(String[] left) {// une fermeture 
		// 
		ArrayList<String> array = new ArrayList<>();
		for(Df i: df) {
			Df d = new Df(left,i.getY());
			if(consequencelog(d)){
				for(String a:i.getY()) {
					array.add(a);
				}
			}
		}
		return array;
	}
	
	private boolean equaltoatt(ArrayList<String> att) {// pour regarder la fermeture d'attribut
		boolean bool = true;
		/**if(att.size() == names.size()) { A CHANGER C INCORRECT
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
		}**/
	}
	// on a pas besoin de toucher à tout les attributs de gauche ils doivet rester intact quand tu vas les toucher
	public boolean consequencelog(Df d) {// à ameliorer puisque ce n'est pas entierement bon
		boolean res = false;
		
		// on va le faire directement
		boolean direct = false;
		for(Df i: df) {
			if(i.equals(d)) {
				direct = true;
				res = true;
				break;
			}
		}
		
		// on va le faire indirectement si le truc a été trouvée
		if(direct == false) {
			for(Df i: df) {
				if(i.)
			}
			
		}
		return res;
	}

}
