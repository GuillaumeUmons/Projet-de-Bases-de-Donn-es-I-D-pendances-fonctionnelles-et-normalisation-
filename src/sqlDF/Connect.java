package sqlDF;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Connect {
	private Connection conn;
	private DatabaseMetaData dmd;
	private ResultSet result;
	/**
	 * 
	 * @param str
	 *        the path of the data base
	 */
	public Connect(String str) {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:"+str);
			dmd = conn.getMetaData();// le truc qui va nous permettre de toucher au tables sql
			result = dmd.getTables(null, null, null, new String[]{"TABLE"});
			System.out.println("database open succefuly");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e.getMessage());
		}catch(IllegalArgumentException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
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
	public ArrayList<String> gettablesname() { // mettre des null pointer exception quand on a appelle ca
		ArrayList<String> name = new ArrayList<String>(); 
		try {
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
	public ArrayList gettable(int i) {
		return null;
	}
	/*pour faire une table de base de donnée ce que l'on va faire c'est on recupere les noms des tables et grace au nom des tables ont va
	 * 
	 */
	public DatabaseMetaData getdmd() {
		return dmd;
	}
	public ResultSet getresult() {
		return result;
	}
}