package sqlDF;

import java.util.ArrayList;

import df.Relation;

public class BdRelation extends Relation {
	private ArrayList<String>[] value ;
	@SuppressWarnings("unchecked")
	public BdRelation(ArrayList<String> names,String name) {
		super(names, name);
		value = new ArrayList[names.size()];
		// TODO Auto-generated constructor stub
	}
	// il y'aura les calculs à faire c ici que on peut essayer funcdef hein
	// les fermetures d'attributs
	public void add(int i, ArrayList<String> val) {
		if(i >= 0) {
			value[i] = val;
		}		
	}
	public ArrayList getatt(int i){
		return value[i];
	}
	public ArrayList<String> get(String a) {
		ArrayList<String> arr = null;
		if(names.contains(a)) {
			arr = value[names.indexOf(a)];
		}
		if(arr== null)
			throw (new IllegalArgumentException ());
		return arr;
	}
	
	public String toString() {
		String a = "nom = "+ name+"/";
		String b = " ";
		for(int i = 0;i < value.length;i++) {
			String c = value[i].toString();
			b = b+ names.get(i)+" ="+c;
		}
		System.out.println("taille valeur "+value.length+"   taille nom   "+names.size());
		return a+b;
	}
	public ArrayList[] getvalue(){
		return value;
	}
}
