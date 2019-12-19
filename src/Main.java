import java.util.ArrayList;
import java.util.Scanner;

import df.Df;
import df.Relation;
import sqlDF.FuncDepManager;
import sqlDF.BdRelation;
import sqlDF.Connect;

public class Main {
	public static void  main(String[] args) {
		// partie ou on demande le chemin de la ou se trouve la base de donnée
		System.out.println("Vous Voulez utiliser le truc avec Base de Données(1) ou avec une relation que tu crée (2)");
		Scanner scan = new Scanner(System.in);
//		int choice = scan.nextInt();
	//	if(choice == 1) {
		//	// entre un ensemble d'attribut et un ensemble de df ensuite proposer les opérations
			//System.out.println("veillez entrer le chemin vers la base de donnée sql");
		String url = scan.nextLine();
		Connect connect = new Connect(url);
		System.out.println(connect.getnames().get(0));
		FuncDepManager man = new FuncDepManager(connect);
		try {
		man.printFuncdep();
		}catch(IllegalArgumentException e) {
			man.createfuncdep();
			man.printFuncdep();
		}
		System.out.println(man.verifyfuncdef("AA", new Df(new String[] {"A"},new String[] {"B"})));
		System.out.println(man.df(0));
		System.out.println(" \n   \n  \n");
		man.attribuatedf();
		for(BdRelation i:man.getrels()) {
			i.affiche();
			System.out.println(i.getDf());
			System.out.println("  ");
		}
	}
		/**else if(choice == 2) {
			ArrayList<String> attribute = new ArrayList();
			// ici pn va faire une methode qui va demander a l'utilisateur les choses à faire avec une base de donnée
			boolean finished = false;
			while(finished == false) {
				Relation(attribute);
				int i = choice();
				if(i == 1) {
					finished = true;
				}else if (i == 2){
					finished = false;
				}
				else {
					choice();
				}
			}
			Relation rel = new Relation(attribute, new String("test"));
			System.out.println(rel);
		}*/
	//}
	private static void Relation(ArrayList<String> name) {
		Scanner scan = new Scanner(System.in);
		System.out.println("entrer un attribut");
		String att = scan.nextLine();
		name.add(att);
	}
	private static int choice() {
		Scanner scan = new Scanner(System.in);
		int i = 0;
		System.out.println("vous voulez arreter(1) ou pas(2)? ecrivez un chiffre");
		i= scan.nextInt();
		return i;
	}
	private static void df() {
		//demander les dependances en cherchant les parties de gauches et les parties de droites
	}
	
	
}
