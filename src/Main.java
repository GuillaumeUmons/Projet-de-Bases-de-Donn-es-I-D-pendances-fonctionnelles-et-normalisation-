import java.util.ArrayList;
import java.util.Scanner;

import df.Relation;
import sqlDF.Connect;

public class Main {
	public static void  main(String[] args) {
		// partie ou on demande le chemin de la ou se trouve la base de donnée
		System.out.println("Vous Voulez utiliser le truc avec Base de Données(1) ou avec une relation que tu crée (2)");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		if(choice == 1) {
			// entre un ensemble d'attribut et un ensemble de df ensuite proposer les opérations
			System.out.println("veillez entrer le chemin vers la base de donnée sql");
		}
		else if(choice == 2) {
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
		}
	}
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
