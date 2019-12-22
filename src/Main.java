import java.sql.SQLException;
import java.util.*;
import df.Df;
import df.Relation;
import sqlDF.FuncDepManager;
import sqlDF.BdRelation;
import sqlDF.Connect;

public class Main {
	public static Connect con;
	public static FuncDepManager func;
	public static void  main(String[] args) {
		presentation();	
		Menue(choice());
	}
	
	private static void Relation(ArrayList<String> name) {
		Scanner scan = new Scanner(System.in);
		System.out.println("entrer un attribut");
		String att = scan.nextLine();
		name.add(att);
	}
	private static int choice() {
		System.out.println("\nMAKE SOME CHOICE");
		Scanner scan = new Scanner(System.in);
		int i  = 0;
		try {
		i= scan.nextInt();
		}catch(InputMismatchException e) {
			choice();
		}
		return i;
	}
	private static void df() {
		//demander les dependances en cherchant les parties de gauches et les parties de droites
	}
	
	public static void presentation() {
		System.out.println(";;;;;;;;;;;;;;;;;;;;;;;;;;;;");
		System.out.println("WELCOME TO THE APPLICATION MADE BY JUNIOR LOLE AND GUILLAUME CARDINAL   ;)   WE HOPE THAT YOU'LL HAVE FUN WITH THIS APP\n");
		System.out.println("C'EST BIEN DE SE SENTIR CHEZ SOI MON CHER\n");
		System.out.println("TU VEUX FAIRE AVEC LES BASES DE DONNEES(WRITE 1 AND PRESS ENTER) OR OU JUSTE AVEC UN ENSEMBLE DE RELATIONS(WRITE 2 AND PRESS ENTER)  ?\n");
	}
	
	public static void Menue(int i) {
		if(i == 1) {
			System.out.println("oubliez pas que vous entrez n'importe quel nom ca cree un fichier si ca n'existe pas et si ca existe ça se connecte dans la base de donnée et si vous entrer un fichier qui n'est pas une base de donnée eh ben");
			Scanner scan = new Scanner(System.in);
			String str = scan.nextLine();
			con = new Connect(str);
			System.out.println("Veillez patienter");
			func = new FuncDepManager(con);
			func.print();
			try {
				BdRelation funcdep = func.get("FuncDep");
			}catch(IllegalArgumentException e) {
				System.out.println("IL Y'A UN PROBLEME YA PAS DE FUNCDEP FAUT AJOUTER LES  DEPENDANCES FONCTIONNELLES");
				ArrayList<String> rel = new ArrayList<String>();
				ArrayList<Df> d = new ArrayList<Df>();
				adddf(rel,d);
			}
			options();
			
		}
		else if(i == 2) {
			System.out.println("ENTER LES NOM DE RELATION \n");
			// recuperer les nom des attributs
			System.out.println("VOILA LES NOM DES ATTRIBUTS \n");
			System.out.println("MAINTENANT ENTRE LES NOMS DES DEPENDANCES FONCTIONNELS");
			System.out.println("AAAAAH OUI VOILA COMMENT TU DOIS ENTRER LES DF  TU AS X LA PARTIE A GAUCHE ET Y LA PARTIE A DROITE TU VAS ECRIS   X->Y   ET PAS D'ESPACE AVANT OU APRES QUAND TU ENTRE SINON CA NE VA PAS MARCHER \n");
			//Recuperer avec les boucles les differents trucs
			System.out.println("VOILA EN LES DEPENDANCES FONCTIONNELES");
		}
		else {
			int a = choice();
			Menue(a);
		}
	}
	private static Df createDF() { //ATTENTION à améliorer ne marche que pour 1(voir constructeur classe DF) 
		System.out.println("Partie de gauche de la DF"); //changer en plus précis
		Scanner scan = new Scanner(System.in);
		String gauche= scan.nextLine();
		String X[] = gauche.split(" ");
		System.out.println("Partie de droite de la DF");
		String droite= scan.nextLine();
		String Y[] = droite.split(" ");
		Df d= new Df(X,Y);
		return d;
	}
	private static void adddf(ArrayList<String> rel,ArrayList<Df> d) {
		System.out.println("veillez entrer des dependances fonctionneles vous en voulez combien ?");
		int ch = choice();
		int a = 0;
		while(a < ch) {
			System.out.println("entrer le nom d'une relation");
			Scanner scan = new Scanner(System.in);
			String i = scan.nextLine();
			rel.add(i);
			Df df = createDF();
			d.add(df);
		}
		System.out.println("on verifie les df");
		for(int o = 0;o < rel.size();o++) {
			boolean bool = func.verifyfuncdef(rel.get(o), d.get(o),1);
			if( bool == true) {
				func.adddf(rel.get(o), d.get(o));
			}
		}
	}
	public static void option(int i) {
		if(i == 1) {
			System.out.println("VOICI LES DF");
			BdRelation bd = func.get("FuncDep");
			func.printFuncdep();
			options();
		}else if(i == 2) {
			ArrayList<String> rel = new ArrayList<>();
			ArrayList<Df> d = new ArrayList<>();
			adddf(rel,d);
			options();
		}else if(i == 3) {
			
		}else if(i == 4) {
			
		}else if(i == 5) {
			
		}else if(i == 6) {
			
		}else if(i == 7) {
			
		}else if(i == 8) {
			System.out.println("BON C'EST LA FIN MERCI POUR TOUT");
		}
	}
	public static void options() {
		System.out.println("LA BASE DE DONNEE EST ENTREE ET MAINTENANT QUE VEUX TU FAIRE ? ");
		System.out.println("LES OPTIONS :");
		System.out.println("(1) VOIR LES DEPENDANCES FONCTIONNELLES\n"
				         + "(2) AJOUTER UN ENSEMBLE DE DF\n"
				         + "(3) VERIFICATION BCNF DES TABLES\n"
				         + "(4) VERIFICATION 3NF DES TABLES\n"
				         + "(5) QUITTER TOUT CA\n");
		option(choice());
	}
}