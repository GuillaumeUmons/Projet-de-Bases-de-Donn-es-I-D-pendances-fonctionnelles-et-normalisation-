import java.util.*;
import df.*;
import sqlDF.*;

public class Main {
	public static Connect con;
	public static FuncDepManager func;
	public static Relation relation;
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
			i = scan.nextInt();
		}catch(InputMismatchException e) {
			i = choice();
		}
		return i;
	}
	private static void df() {
		//demander les dependances en cherchant les parties de gauches et les parties de droites
	}
	
	public static void presentation() {
		System.out.println("WELCOME TO THE APPLICATION MADE BY      JUNIOR LOLE                 AND                        GUILLAUME CARDINAL");
		System.out.println("TU VEUX FAIRE AVEC LES BASES DE DONNEES(WRITE 1 AND PRESS ENTER) OR OU JUSTE AVEC UN ENSEMBLE DE RELATIONS(WRITE 2 AND PRESS ENTER)  ?\n");
	}
	
	public static void Menue(int i) {
		if(i == 1) {
			boolean phase1 = false;
			System.out.println("oubliez pas que vous entrez n'importe quel nom ca cree un fichier si ca n'existe pas et si ca existe ca se connecte dans la base de donnee et si vous entrer un fichier qui n'est pas une base de donnee eh ben");
			Scanner scan = new Scanner(System.in);
			String str = scan.nextLine();
			con = new Connect(str);
			System.out.println("Veillez patienter");
			func = new FuncDepManager(con);
			func.print();
			try {
				BdRelation funcdep = func.get("FuncDep");
				phase1 = true;
			}catch(IllegalArgumentException e) {
				if(func.getrels().size() ==0) {
					System.out.println("IL N'Y A RIEN DANS LA BASE DE DONNEE\n");
					System.out.println("CETTE APPLICATION EST CHARGE DE VERIFIER DES DEPENDANCES FONCTIONNELLES PAS DE CREER DES TABLES\nSI VOUS VOULEZ ECRIRE DES TABLES NOUS VOUS CONSEILLONS D'UTILISER DB BROWSER FOR SQLITE UN EXELLENT OUTIL\n");
					phase1 = false;
				}
				else {
					System.out.println("IL Y'A UN PROBLEME YA PAS DE FUNCDEP FAUT AJOUTER LES  DEPENDANCES FONCTIONNELLES");
					ArrayList<String> rel = new ArrayList<String>();
					ArrayList<Df> d = new ArrayList<Df>();
					adddf(rel,d);
					phase1 = true;
				}
			}
			if(phase1 ==true) {
				System.out.println("LA BASE DE DONNEE EST ENTREE ET MAINTENANT QUE VEUX TU FAIRE ? ");
				func.removebaddf();
				options1();
			}
			else {
				System.out.println("POUR NOUS FAIRE EXCUSER VOUS ETES BASCULE DANS L'OPTION SANS BASE DE DONNEE\n");
				Menue(2);
			}
		}
		else if(i == 2) {
			System.out.println("ENTER LES NOM DE LA RELATION \n");
			Scanner scan = new Scanner(System.in);
			String rel = scan.nextLine();
			ArrayList<String> arr = new ArrayList<>();
			System.out.println("COMBIEN D'ATTRIBUTs ?");
			int e = choice();
			for(int o = 0;o <e;o++) {
				Relation(arr);
			}
			relation = new Relation(arr,rel);
			System.out.println(arr);
			System.out.println("MAINTENANT C'EST LE MOMENT POUR ENTRER LES DEPENDANCES FONCTIONNELLES");
			boolean finished = false;
			while(finished == false) {
				Df d = createDF();
				while(verify(d,relation) == false) {
					System.out.println("C'EST N'EST PAS UNE BONNE DF");
					d = createDF();
				}
				relation.adddf(d);
				System.out.println("FINI(1) OU TU VEUX CONTINUER(autre nombre)");
				int choix = choice();
				if(choix == 1)
					finished = true;
			}
			System.out.println(relation);
			System.out.println("Et les dependance fonctionnelles"+relation.alldf());
			
			System.out.println("C'EST PENIBLE NOUS LE SAVONS");
			System.out.println("TU VEUX FAIRE QUOI MAINTENANT");
			options2();
		}
		else {
			Menue(choice());
		}
	}
	
	private static Df createDF() {
		System.out.println("IL FAUT METTRE DES ESPACES ENTRE ATTRIBUT POUR LES DELIMITER");
		System.out.println("Partie de gauche de la DF"); //changer en plus precis
		Scanner scan = new Scanner(System.in);
		String gauche= scan.nextLine();
		String X[] = gauche.split(" ");
		System.out.println("Partie de droite de la DF");
		String droite= scan.nextLine();
		String Y[] = droite.split(" ");
		Df d= new Df(X,Y);
		return d;
	}
	
	private static void adddf(ArrayList<String> rel,ArrayList<Df> d) {// pour la base de donnée
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
			a++;
		}
		System.out.println(rel);
		System.out.println(d);
		System.out.println("on verifie les df");
		for(int o = 0;o < rel.size();o++) {
			boolean bool = func.verifyfuncdef(rel.get(o), d.get(o),1);
			if( bool == true) {
				func.adddf(rel.get(o), d.get(o));
			}
		}
	}
	
	private static void option1(int i) {
		if(i == 1) {
			System.out.println("VOICI LES DF");
			BdRelation bd = func.get("FuncDep");
			for(int o = 0;o < bd.getvalue().size();o++) {
				System.out.print("table_name:"+bd.gettuple(o).get(0)+" ");
				System.out.println(func.df(o));
			}
			options1();
		}else if(i == 2) {
			ArrayList<String> rel = new ArrayList<>();
			ArrayList<Df> d = new ArrayList<>();
			adddf(rel,d);
			options1();
		}else if(i == 3) {
			func.attribuatedf();//normalement les df à l'interieur sont bonnes
			System.out.println("VEILLEZ ENTRER LE NOM DE LA TABLE A VERIFIER");
			Scanner scan = new Scanner(System.in);
			String tablename = scan.nextLine();
			BdRelation table = func.get(tablename);
			if(table.BCNF() == true)
				System.out.println("C'EST EN BCNF DONC EN 3NF AUSSI");
			else
				System.out.println("C'EST PAS EN BCNF");
		}else if(i == 4) {
			
		}else if(i == 5) {
			System.out.println("BON C'EST FINI MERCI POUR TOUT");
		}
	}
	
	private static void option2(int i) {
		if(i == 1) {
			System.out.println("POUR QUEL ATTRIBUT DANS L'ENEMBLE DE DEPENDANCE?"+ relation.alldf());
			System.out.println("ENTRER LA POSITION DE LA DEPENDANCE FONCTIONNEL ET ON REGARDE LA FERMETURE D'ATTRIBUT DE LA PARTIE GAUCHE DE LA DF ON COMMENCE PAR 0");
			int choice = choice();
			try {
			ArrayList<String> arr = relation.fermetureattribut(relation.getDf().get(choice).getX());
			System.out.println(arr);
			System.out.println("CONTINUES(1)?");
			if(choice() == 1) {
				option2(1);
			}
			else {
				options2();
			}
			}catch(IndexOutOfBoundsException e) {
				option2(1);
			}
		}else if(i == 2) {
			boolean bool = relation.BCNF();
			if(bool == true) {
				System.out.println("C'EST EN BCNF\n");
			}
			options2();
			
		}else if(i == 3) {
			System.out.println("CREE UN UNE DEPENDANCE AVEC L'ENSEMBLE D'ATTRIBUT QUE TU AS ENTRE");
			Df d = createDF();
			while(verify(d,relation) == false) {
				System.out.println("CE N'EST PAS UNE BONNE DF");
				d = createDF();
			}
			if(relation.consequencelog(d) == true) {
				System.out.println("C'EST UNE CONSEQUENCE LOGIQUE DE L'ENSEMBLE D'ATTRIBUT");
			}else {
				System.out.println("C'EST PAS UNE CONSEQUENCE LOGIQUE");
			}
			System.out.println("CONTINUES(1)?");
			if(choice() == 1) {
				option2(3);
			}
			else {
				options2();
			}
		}else if(i == 4) {
			Menue(1);
		}else if(i == 5) {
			System.out.println("BON C'EST LA FIN MERCI POUR TOUT");
		}
	}
	
	private static void options1() {
		System.out.println("LES OPTIONS :");
		System.out.println("(1) VOIR LES DEPENDANCES FONCTIONNELLES\n"
				         + "(2) AJOUTER UN ENSEMBLE DE DF\n"
				         + "(3) VERIFICATION BCNF DES TABLES\n"
				         + "(4) VERIFICATION 3NF DES TABLES\n"
				         + "(5) QUITTER\n");
		option1(choice());
	}
	
	private static void options2() {
		System.out.println("(1) FERMETURE D'ATTRIBUT\n"
						 + "(2)BCNF OU PAS\n"
						 + "(3)TESTER DES CONSEQUENCES LOGIQUE DE L'ENSEMBLE DES DF VOUS INVENTER DES DF ET ON REGARDE SI C'EST UNE CONSEQUENCE LOGIQUE DE L'ENSEMBLE DES DF\n"
						 + "(4)CHANGER DE MODE\n"
						  +"(5) QUITTER\n");
		option2(choice());
	}
	
	private static boolean verify(Df d,Relation rel){
		boolean X = true;
		boolean Y = true;
		for(String j :d.getX()) {
			if(relation.getnames().contains(j) == false)// verifier si les elements de la partie gauche est inclus dans l'ensemble d'attribut
				X = false;
		}
		for(String j:d.getY()) {
			if(relation.getnames().contains(j) == false)// verifier si les elements de la partie droite est inclus dans l'ensemble d'attribut
				Y = false;
		}
		return (X && Y);
	}
}