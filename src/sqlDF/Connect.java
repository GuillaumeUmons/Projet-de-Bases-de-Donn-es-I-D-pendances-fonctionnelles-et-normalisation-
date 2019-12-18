package sqlDF;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import df.Df;

public class Connect {
	private Connection conn;
	private DatabaseMetaData dmd;
	private ArrayList<String> tablenames;
	private String path;

	/**
	 * 
	 * @param str
	 *        the path of the data base
	 */
	public Connect(String path) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+path);
			this.path = path;
			dmd = conn.getMetaData();// le truc qui va nous permettre de toucher au tables sql
			tablenames = gettablesname();
			if(conn != null)
				System.out.println("c'est bon");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e.getMessage());
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(conn == null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println(e.getMessage());
				}
			}
		}
	}
	public Connection getconnection() {
		return conn;
	}
	
	private ArrayList<String> gettablesname() { // mettre des null pointer exception quand on a appelle ca
		ArrayList<String> name = new ArrayList<String>(); 
		try {
			ResultSet result = dmd.getTables(null, null, null, new String[]{"TABLE"});
			while(result.next()) {
				name.add(result.getString("TABLE_NAME"));
			}
		}catch(NullPointerException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return name;
	}// voila c'est la liste des nom des tables

	public ArrayList<String> getattribute(int i) {// pour avoir les attribut d'une table se trouvant à la position i de l'arraylist tablenames
		ArrayList<String> colname = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("SELECT * FROM "+tablenames.get(i));
			ResultSetMetaData rsmd= result.getMetaData();
			int nbCols = rsmd.getColumnCount();
			for(int a = 1;a <= nbCols;a++) {
				String name = rsmd.getColumnName(a);
				colname.add(name);
			}
	        result.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return colname;
	}// pour avoir les colones d'une table
	/*pour faire une table de base de donnée ce que l'on va faire c'est on recupere les noms des tables et grace au nom des tables ont va
	 * 
	 */
	public BdRelation createtable(int i) {//on va creer une bdrelation avec des tables
		Statement stmnt;// pour executer une requete sql en java
		BdRelation table = null; // la table qui represente les donnée sql en java
		try {
			stmnt = conn.createStatement();
			ArrayList<String> attribute = getattribute(i); // normalement c'est la methode pour recevoir les attributs en java
			table = new BdRelation(attribute,tablenames.get(i));
			for(int o = 0;o <attribute.size();o++) {
				ArrayList<String> col = new ArrayList<String>();// dans BdRelation on a une arraylist qui contiendra des tableau de string et ces strings la ce seront
				ResultSet result = stmnt.executeQuery("SELECT "+attribute.get(o)+" FROM "+tablenames.get(i));
				System.out.println("SELECT "+attribute.get(o)+" FROM "+tablenames.get(i));
				ResultSetMetaData rsmd = result.getMetaData();
				int nbcol =rsmd.getColumnCount();
				System.out.println("la taille des tableau est "+nbcol);
				System.out.println("moment "+o);
					for(int b = 0;b < nbcol;b++) {// le problème est là
						while(result.next()) {
						System.out.println("l'element ajouté pour "+attribute.get(o)+" est "+b+" c'est a dire"+ result.getString(b+1));
						col.add(result.getObject(b+1).toString());
						}
					table.add(o, col);
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("ca n'a pas marché");
		}
		return table;// normalement ca marche mais on va regarder ce que ca donne hein puisque on ne sait jamais
	}// maintenant j'ai un moyen d'accedder à la base de donnée et j'ai theoriquement reussi a représenter ca sous forme d'un truc en java
	public ArrayList<String> getnames(){
		return tablenames;
	}
	public DatabaseMetaData getdmd() {
		return dmd;
	}
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addfuncdep(Df df,String Relation) {// ajouter les def
	}
	
	public void createfuncdep() {
		try {
			Statement stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS FuncDep(\n"
	                + "    table_name TEXT NOT NULL,\n"
	                + "    lhs TEXT NOT NULL,\n"
	                + "    rhs TEXT NOT NULL,\n"
	                + ");";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	public void addDF(Df df,String rel) {
		if(df.getY().length == 1) {
			try {
				String X = "";
				for(int i = 0;i < df.getX().length;i++) {
					if(i < df.getX().length-1) {
						X = X+df.getX()[i]+",";
					}
				}	
				Statement stmt = conn.createStatement();
				String sql = "INSERT INTO FuncDep VALUES('"+rel+"','"+X+"','"+df.getY()[0]+"')";
				stmt.execute(sql);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Ce n'est pas une bonne dependance fonctionnel");
		}
	}	
}