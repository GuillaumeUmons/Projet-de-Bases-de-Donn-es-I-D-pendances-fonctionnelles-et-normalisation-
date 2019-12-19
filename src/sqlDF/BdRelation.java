package sqlDF;

import java.util.ArrayList;

import df.Relation;

public class BdRelation extends Relation {
	private ArrayList<ArrayList> value ;
	@SuppressWarnings("unchecked")
	public BdRelation(ArrayList<String> names,String name) {
		super(names, name);
		value = new ArrayList();
	}
	// il y'aura les calculs à faire c ici que on peut essayer funcdef hein
	// les fermetures d'attributs
	public void add(ArrayList<String> val) {
			value.add(val);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<String> gettuple(int i){
		return value.get(i);
	}
	
	public void affiche(){
		System.out.println("name :"+name);
		System.out.println(names);
		for(ArrayList i: value) {
			System.out.println(i);
		}
	}
	public ArrayList getvalue(){
		return value;
	}
	public ArrayList<String> get(String[] a,int i) {
		ArrayList <String>res = new ArrayList<String>();
		for(String aa : a) {
			int ind = names.indexOf(aa);
			res.add((String) value.get(i).get(ind));
		}
		return res; //c'est comme un select des noms des valeurs des attributs dans la liste a
	}
}
