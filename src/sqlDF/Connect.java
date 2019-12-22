/**
 * the class to connect into a database
 * @author Junior Lole, Guillaume Cardinal
 */
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
	 * the constructor to connect in the database
	 * @param str
	 *        the path of the database
	 */
	public Connect(String path) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:"+path);
			this.path = path;
			dmd = conn.getMetaData();// le truc qui va nous permettre de toucher au tables sql
			tablenames = gettablesname();
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
				close();
			}
		}
	}
	/**
	 * 
	 * @return the connection object wich allow to connect in the database
	 */
	public Connection getconnection() {
		return conn;
	}
	/**
	 * 
	 * @return return an arrayList of that contains all table's name of the database C
	 */
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
	/**
	 * 
	 * @param i the position of the name of the table in the arrayList tablesname
	 * @return all the column's name at in a arrayList 
	 */
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
	/**
	 * 
	 * @param i create a representation of the table that has a name at the position i in tablenames
	 * @return return a BdRelation object with table data inside
	 */
	public BdRelation createtable(int i) {//on va creer une bdrelation avec des tables
		Statement stmnt;// pour executer une requete sql en java
		BdRelation table = null; // la table qui represente les donnée sql en java
		try {
			stmnt = conn.createStatement();
			ArrayList<String> attribute = getattribute(i); // normalement c'est la methode pour recevoir les attributs en java
			table = new BdRelation(attribute,tablenames.get(i));
			ResultSet result = stmnt.executeQuery("SELECT *"+" FROM "+tablenames.get(i));
			ResultSetMetaData rsmd = result.getMetaData();
			int nbcol =rsmd.getColumnCount();
			while(result.next()) {
				ArrayList<String> col = new ArrayList<String>();// dans BdRelation on a une arraylist qui contiendra des tableau de string et ces strings la ce seront
				for(int b = 0;b < nbcol;b++) {// le problème est là	
					col.add(result.getObject(b+1).toString());
				}
				table.add(col);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("ca n'a pas marché");
		}
		return table;// normalement ca marche mais on va regarder ce que ca donne hein puisque on ne sait jamais
	}// maintenant j'ai un moyen d'accedder à la base de donnée et j'ai theoriquement reussi a représenter ca sous forme d'un truc en java
	/**
	 * 
	 * @return the tablenames
	 */
	public ArrayList<String> getnames(){
		return tablenames;
	}
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void createfuncdep() {
		String sql = "CREATE TABLE IF NOT EXISTS FuncDep(\n"
                + "    table_name TEXT NOT NULL,\n"
                + "    lhs TEXT NOT NULL,\n"
                + "    rhs TEXT NOT NULL\n"
                + ");";
		try {
			Statement stmt = conn.createStatement();

			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * to add the df in th data base table funcdep
	 * @param df the df to add we have the method to verify the df in another class
	 * @param rel the name of the table
	 */
	public void addDF(Df df,String rel) {
		ArrayList<ArrayList> j = new ArrayList();
		if(df.getY().length == 1) {
			try {
				String X = "";
				for(int i = 0;i < df.getX().length;i++) {
					if(i < df.getX().length-1) {
						X = X+df.getX()[i]+" ";
					}
				}	
				Statement stmt = conn.createStatement();
				if(df.getY().length == 1) {
					String sql = "INSERT INTO FuncDep VALUES('"+rel+"','"+X+"','"+df.getY()[0]+"')";//on considère que y'a q'un seul 
					stmt.executeUpdate(sql);// pas encore testé a faire maintenant
				}
				else {
					System.out.println("ca n'entre pas dans funcdep");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Ce n'est pas une bonne dependance fonctionnel");
		}
	}
	public void remove(Df df, String rel) {
		String X = "DELETE FROM FuncDep WHERE table_name =";
		for(String i:df.getX()) {
			if(i.equals(df.getX()[df.getX().length - 1]) == false) {
				X = X+i+" ";
			}
		}
		X = X+" AND lhs = "+df.getY()[0];
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(X);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}