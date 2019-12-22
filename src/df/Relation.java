package df;

import java.util.ArrayList;

public class Relation { // relation pour les donnees qui ne viennent pas de la base de donnee
	protected String name;
	protected ArrayList<String> names;
	private ArrayList<Df> df = new ArrayList<Df>();
	public Relation(ArrayList<String> names,String name) {
		this.names = names;
		this.name = name;
	}
	/**
	 * 
	 * @return return all the column name in the table
	 */
	public ArrayList<String> getnames() {
		return names;
	}
	/**
	 * 
	 * @return return the name of the table
	 */
	public String getname() {
		return name;
	}
	public void adddf(Df d) {
		df.add(d);
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
	public String alldf() {
		String dfs = "|";
		for(Df d:df) {
			dfs = dfs+d.toString()+"|";
		}
		return dfs;
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
		return bool;
	}
	public boolean NF3() {
		boolean bool = false;
		// retourne la valeur par rapport a l'ensemble de df
		if(BCNF()) {
			bool = true;
		}
		else {
			// regarder si les attribut de Y  appartienne unne cle
			bool = true;
			
		}
		return bool;

	}
	// ajouter une methode String
	public ArrayList<String> fermetureattribut(String[] left) {// une fermeture 
		// FAIRE AVEC ALGORITHME DU LIVRE
		ArrayList<String> fermeture = new ArrayList<>();
		for(String i: left) {// quand on dit fermeture d'attribut = X au debut
			fermeture.add(i);
		}
		ArrayList<Boolean> utilise = new ArrayList<Boolean>();// les non utilise comme ca je ne touche pas aux dependances fonctionnels de la table
		for(Df d: df) {
			utilise.add(false);
		}
		for(Df d:df) {
			boolean inclus = true;
			for(String i:d.getX()) {//on va verifier si les attributs de la partie droite sont inclus a l'interieur de la fermeture
				if(fermeture.contains(i) == false) {
					inclus = false;
				}
			}
			int pos = df.indexOf(d);
			if(inclus == true && utilise.get(pos) == false ) {// si c'est inclus alors on va rajouter les attributs qui sont la dedans
				for(String i:d.getY()) {
					if(fermeture.contains(i) == false) {//rajouter les elements dans la fermeture seulement si ils ne sont pas a l'interieur
						fermeture.add(i);
					}
				}
				utilise.set(pos, true);
			}
		}
		return fermeture;
	}
	
	private boolean equaltoatt(ArrayList<String> att) {// pour regarder la fermeture d'attribut
		boolean bool = true;
		if(att.size() == names.size()) {
			for(int i = 0;i < att.size();i++) {
				boolean find = false;
				for(int j = 0;j < att.size();j++) {
					if(att.get(i).equals(names.get(i))) {
						find = true;
					}
				}
				if(find == false) {
					bool = false;
					break;
				}
			}
			return bool;
		}
		else {
			return false;
		}
		
	}
	// on a pas besoin de toucher a tout les attributs de gauche ils doivet rester intact quand tu vas les toucher
	public boolean consequencelog(Df d) {// a ameliorer puisque ce n'est pas entierement bon
		//consequence logique de l'ensemble des df cad que l'ensemble des df respecte cette ensemble
		// c'est a dire que si X->AC et (A->B ou C->B) alors X->B
		boolean bool = false;
		String[] X = d.getX();
		String[] Y = d.getY();
		for(Df dfs :df) {
			if(X.equals(dfs.getX()) && Y.equals(dfs.getY())) {
				bool = true;
			}
			else if(X.equals(dfs.getX())) {
				ArrayList<String[]> decompo = dfs.decompoY();
				for(String[] i:decompo) {
					Df depf = new Df(i,Y);
					if(consequencelog(depf) == true) {
						bool = true;
					}
				}
			}
		}
		return bool;
	}
	
	public ArrayList<Df> getDf(){// pour les df
		return df;
	}
}
