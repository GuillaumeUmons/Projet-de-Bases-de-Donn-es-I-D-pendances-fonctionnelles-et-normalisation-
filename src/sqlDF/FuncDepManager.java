package sqlDF;

import java.util.ArrayList;

import df.Df;

public class FuncDepManager {// gerer les tables de base de donn�e
	Connect conn;
	ArrayList<BdRelation> relations;
	private int ArrayList;
	public FuncDepManager(Connect conn) {
		relations = new ArrayList<BdRelation>();
		this.conn = conn;
		update();
	}
	public BdRelation get(int i) {
		return relations.get(i);
	}
	public BdRelation get(String a) {
		
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
	
	public boolean verifyfuncdef(String rel, Df df) {// verifier les dependances fonctionnelle
		boolean bool = true;
		BdRelation bd = get(rel);
		ArrayList<ArrayList<String>> arr = new ArrayList();
		ArrayList<ArrayList<String>> aa = new ArrayList();
		//Afficher les deux
		for(int i = 0;i <bd.getvalue().size();i++) {
			arr.add(bd.get(df.getX(), i));// les valeurs par rapport � x 
			aa.add(bd.get(df.getY(), i));// les valeurs par rapport � y
		}
		System.out.println(arr);
		System.out.println(aa);
		// regarder le nombre d'occurence
		for(ArrayList a:arr) {
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
							System.out.println("ca ne marche pas car "+lien+" est differents de "+li);
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
		return bool;
	}
	
	public void createfuncdep() {
		conn.createfuncdep();
		update();
	}
	
	private void update() {// pour mise a jour des tableaux si y'a un nouveau tableau
		for(int i = 0;i < conn.getnames().size();i++) {
			relations.add(conn.createtable(i));
		}
	}
	
	public void printFuncdep() {
		BdRelation funcdep = get("FuncDep");
		funcdep.affiche();	
	}
	
	private Df df(int i) {// la df � la ligne i dans funcdep on compte sur le fait que funcdep marche bien
		Df df;
		BdRelation funcdep = get("FuncDep");
		ArrayList<String> arr = funcdep.gettuple(i);// il est cens� y avoir que 3 valeur dans ca et on ne peut rien modifier dans ces arrayList normalement donc on peut facilement y toucher
		String a = arr.get(1);
		System.out.println(a);
		String b = arr.get(2);// normalement c'est une chaine de caract�re sans virgule la ou c'ezt verifi� avant c'est dans le main quand on va entrer le tuple
		ArrayList<String> X = new ArrayList<>();
		int k = 0;
		boolean espace = false;
		for(int j= 0;j < a.length();j++) {
			String add = a.substring(k,j);
			System.out.println(add);
			if(a.charAt(j) ==' ') {
				System.out.println(add);
				X.add(add);
				k = j+1;
				espace = true;
			}
		}
		if(espace == false) {
			X.add(a);
		}
		//String[] x = (String[]) X.toArray(); ceci ne marche pas donc je suis oblig� de faire manuellement mon tableau de String � la main
		String[] x = new String[X.size()];
		for(int j = 0; j < x.length;j++) {
			x[j] = X.get(j);
			
		}
		df = new Df(x,new String[]{b});
		return df;
	}
	
}