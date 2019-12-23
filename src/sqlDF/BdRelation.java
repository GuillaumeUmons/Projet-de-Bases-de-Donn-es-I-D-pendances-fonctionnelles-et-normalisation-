package sqlDF;

import java.util.ArrayList;

import df.Relation;

public class BdRelation extends Relation {
	private ArrayList<ArrayList<String>> value ;
	public BdRelation(ArrayList<String> names,String name) {// constructeur avec lle
		super(names, name);
		value = new ArrayList<>();
	}
	
	public void add(ArrayList<String> val) {// ajoute des tuples dans la table necessair
			value.add(val);
	}
	/**
	 * 
	 * @param i the position in the arrayList tuple
	 * @return the tuple at the position i
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> gettuple(int i){//le tuple a la ligne i de la table
		return value.get(i);
	}
	
	public void affiche(){// afficher la table
		System.out.println("name :"+name);
		System.out.println(names);
		for(ArrayList i: value) {
			System.out.println(i);
		}
		System.out.println("\n");
	}
	/**
	 * 
	 * @return return the arrayList of the tuple in the table
	 */
	public ArrayList getvalue(){
		return value;
	}
	/**
	 * 
	 * @param a some names of column in the table
	 * @param i position of a tuple
	 * @return an arrayList of attribute value in the tuple at the position i
	 */
	public ArrayList<String> get(String[] a,int i) {// recevoir les elements du tuple a la ligne i des attribut se trouvant dans la table de String a necessaire pour
		ArrayList <String>res = new ArrayList<String>();
		for(String aa : a) {
			int ind = names.indexOf(aa);
			res.add((String) value.get(i).get(ind));
		}
		return res; //c'est comme un select des noms des valeurs des attributs dans la liste a
	}
}
