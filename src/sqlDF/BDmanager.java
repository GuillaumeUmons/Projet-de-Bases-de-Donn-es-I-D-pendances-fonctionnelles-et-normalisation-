package sqlDF;

import java.util.ArrayList;

public class BDmanager {// gerer les tables de base de donn�e
	Connect conn;
	ArrayList<BdRelation> relations;
	public BDmanager(Connect conn) {
		relations = new ArrayList<BdRelation>();
		this.conn = conn;
		for(int i = 0;i < conn.getnames().size();i++) {
			relations.add(conn.createtable(i));
		}
	}
	// on va
}
