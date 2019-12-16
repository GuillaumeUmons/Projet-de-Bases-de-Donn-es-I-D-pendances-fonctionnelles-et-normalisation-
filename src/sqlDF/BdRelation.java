package sqlDF;

import java.util.ArrayList;

import df.Relation;

public class BdRelation extends Relation {
	private ArrayList<String[]> value = new ArrayList<>();
	public BdRelation(ArrayList<String> names,String name) {
		super(names, name);
		// TODO Auto-generated constructor stub
	}
	// il y'aura les calculs à faire c ici que on peut essayer funcdef hein
	// les fermetures d'attributs
	public void add(int i, String[] val) {
		if(i >= 0) {
			value.add(i,val);
		}		
	}	
	public String toString() {
		String a = "nom = "+ name+"/";
		String b = " ";
		for(int i = 0;i < value.size();i++) {
			String c = "|";
			for(int j = 0;j < value.get(i).length;j++) {
				c = c+value.get(i)[j]+"|";
			}
			b = b+ names.get(i)+" ="+c;
		}
		System.out.println("taille valeur "+value.size()+"   taille nom   "+names.size());
		return a+b;
	}
	public ArrayList<String[]> getvalue(){
		return value;
	}
}
