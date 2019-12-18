package sqlDF;

import java.util.ArrayList;

import df.Df;

public class BDmanager {// gerer les tables de base de donnée
	Connect conn;
	ArrayList<BdRelation> relations;
	private int ArrayList;
	public BDmanager(Connect conn) {
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
		if(bd == null)
			throw (new IllegalArgumentException ());
		return bd;
	}
	public boolean verifyfuncdef(String rel, Df df) {
		// retourner si la df est bonne par rapport à la relation
		/*
		 * aller dans la table si regarder l'autre attribut et compte le nombre d'occurence
		 * pour des trucs si tout les occurences sont egales à 1 alors la dependance est bonne sinon ce n'est pas bon
		 */
		BdRelation bd = get(rel);
		boolean bool = false;
		ArrayList<String> Y = bd.get(df.getY()[0]);
		ArrayList<String>[] X = new ArrayList[df.getX().length];
		for(int i = 0;i < df.getX().length;i++) {
			X[i] = bd.get(df.getX()[i]);
		}
		
		for(int i = 0;i < X[0].size();i++) {
			String[]
			for(int j = 0;j < X.length())
		}
		return false;
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
}
