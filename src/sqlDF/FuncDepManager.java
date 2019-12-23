package sqlDF;

import java.util.ArrayList;

import df.Df;

public class FuncDepManager {// gerer les tables de base de donnee
	private Connect conn;
	private ArrayList<BdRelation> relations;
	public FuncDepManager(Connect conn) {
		this.conn = conn;
		update();
	}
	/**
	 * 
	 * @param i the table at the position i
	 * @return the table at the position i
	 */
	public BdRelation get(int i) {// recevoir la relation se trouvant a la position i
		return relations.get(i);
	}
	/**
	 * 
	 * @param a the name of the table
	 * @return the table wich the name is a
	 */
	public BdRelation get(String a) {// la relation dont le nom est a si ca ne se trouve pas dans la table une exception est lance
		
		BdRelation bd = null;
		for(BdRelation i: relations) {
			if(i.getname().equals(a))
				bd = i;
		}
		if(bd == null) {
			throw (new IllegalArgumentException ());
		}
		
		return bd;
	}
	/**
	 * 
	 * @param a value name in 
	 * @param df
	 * @return
	 */
	public boolean verifyfuncdef(String rel, Df df,int b) {// verifier les dependances fonctionnelle par rapport a la table de nom rel  necessaire pour verifier les df dans funcdep et les df entre dans le cmd
		boolean bool = true;
		if(df.getY().length == 1 && conn.getnames().contains(rel) == true) {
			BdRelation bd = get(rel);
			ArrayList<ArrayList<String>> arr = new ArrayList();
			ArrayList<ArrayList<String>> aa = new ArrayList();
			//Afficher les deux
			for(int i = 0;i <bd.getvalue().size();i++) {
				arr.add(bd.get(df.getX(), i));// les valeurs par rapport a x 
				aa.add(bd.get(df.getY(), i));// les valeurs par rapport a y
			}
			System.out.println(arr);
			System.out.println(aa);
			// regarder le nombre d'occurence
			for(ArrayList<String> a:arr) {
				int ii = arr.indexOf(a);
				ArrayList<String> lien = aa.get(ii);
				boolean occur = true;
				for(int i =0;i < arr.size();i++) {
					if(arr.get(i).equals(a)) {
						ArrayList<String> li = aa.get(i);
						for(int j = 0;j < lien.size();j++) {
							if(lien.get(j).equals(li.get(j)) == false) {
								occur = false;
								bool = false;
								if(b == 1) {
								System.out.println("ca ne marche pas car "+lien+" est differents de "+li);
								}
								break;
							}
							
						}
					}
					if(occur == false) {
						break;
					}
				}
				if(occur == false) {
					break;
				}
			}
		}
		else {
			bool = false;
			if(b == 1) {
			System.out.println("soit le nom re la table n'est pas bonne soit la dependance fonctionnel n'est pas bonne");
			}
		}
		return bool;
	}
	
	public void createfuncdep() { // creer funcdep ca le cree si ca n'existe pas et on met a jour les tables de valeurs
		conn.createfuncdep();
		update();
	}
	
	private void update() {// pour mise a jour des tableaux si y'a un nouveau tableau
		relations = new ArrayList<BdRelation>();
		for(int i = 0;i < conn.getnames().size();i++) {
			relations.add(conn.createtable(i));
		}
	}
	
	public void printFuncdep() { // afficher funcdep
		BdRelation funcdep = get("FuncDep");
		funcdep.affiche();	
	}
	
	public Df df(int i) {// retourne la df se trouvant a la ligne i
		Df df;
		BdRelation funcdep = get("FuncDep");
		ArrayList<String> arr = funcdep.gettuple(i);// il est cense y avoir que 3 valeur dans ca et on ne peut rien modifier dans ces arrayList normalement donc on peut facilement y toucher
		String a = arr.get(1);
		String b = arr.get(2);// normalement c'est une chaine de caractere sans virgule la ou c'ezt verifie avant c'est dans le main quand on va entrer le tuple
		ArrayList<String> X = new ArrayList<>();
		int k = 0;
		boolean espace = false;
		String[] x = a.split(" ");
		df = new Df(x,new String[]{b});
		return df;
	}
	
	public void attribuatedf() {// attribuer les dans funcdep df au relations
		BdRelation funcdep = get("FuncDep");
		for(int i = 0;i < funcdep.getvalue().size();i++) {
			BdRelation bd = get((String) funcdep.gettuple(i).get(0));
			Df d = df(i);
			bd.adddf(d);
		}
	}
	/**
	 * 
	 * @return return all the repesentation of table
	 */
	public ArrayList<BdRelation> getrels(){// pour les relations
		return relations;
	}
	public void print() {
		for(BdRelation i : relations) {
			if(i.getname().equals("FuncDep") == false) {
				i.affiche();
			}
		}
	}
	public void adddf(String rel,Df df) {
		conn.addDF(df, rel);
		update();
	}
	public void removebaddf() {
		BdRelation d = get("FuncDep");
		ArrayList<ArrayList<String>> arr = d.getvalue();
		for(int i = 0;i < d.getvalue().size();i++) {
			Df df = df(i);
			boolean bool = verifyfuncdef(arr.get(i).get(0),df,0);//impossible de faire d.getvalue().get(
			if(bool == false) {
					conn.remove(df, arr.get(i).get(0));
			}
		}
		System.out.println("IL Y'A PEUT ETRE EU DES DF QUI ONT ETE ENLEVE PARCE QUE ELLES ETAIENT MAUVAISE");
		update();
	}
	
	// detecter les dependances fonctionnelles dans les tables pour pouvoir ecrire dans funcdep
}