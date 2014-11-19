
// Programme de test de la classe AVLString

import java.util.*;

class TestAVLString
{
	public static void main(String args[])
	{
		Scanner sc = new Scanner(System.in);

		int ch = -1;
		String elt;
		boolean aff = true;
		
		AVLString a = new AVLString();
		ArrayList<String> l;

		System.out.println("\n---- Programme de test des AVL pour manipuler des Strings, AVL initialisé à vide ----");

		do
		{
			System.out.println("	Que souhaitez-vous faire ?");
			System.out.println("\n		---- Initialisation ----");
			System.out.println("		Réinitialiser l'AVL à vide					(1)");
			System.out.println("\n		---- Tests ---- ");
			System.out.println("		Tester si l'AVL est un arbre vide				(2)");
			System.out.println("		Tester si l'AVL est un arbre feuille				(3)");
			System.out.println("		Tester si une String est présente dans l'arbre			(4)");
			System.out.println("\n		---- Modifications de l'arbre ---- ");
			System.out.println("		Ajouter un élément 						(5)");
			System.out.println("		Supprimer un élément						(6)");
			System.out.println("		Supprimer le plus grand élément					(7)");
			System.out.println("		Supprimer le plus petit élément					(8)");

			System.out.println("\n		---- Autres ---- ");
			System.out.println("		Trier une liste de Strings gràce à un AVL			(9)");

			
			System.out.println("\n		---- Affichage ---- ");
			System.out.println("		Afficher la hauteur de l'arbre					(10)");
			System.out.println("		Afficher la balance de l'arbre (à la racine)			(11)");
			System.out.println("		Afficher le plus grand élément					(12)");
			System.out.println("		Afficher le plus petit élément					(13)");
			System.out.println("		Activer/Désactiver l'affichage en liste				(14)");

			System.out.println("		Quitter le programme						(0)");

			System.out.print("		 => ");
			ch = sc.nextInt();
			sc.nextLine();
			switch (ch)
			{
				case (1):
					a.avlVide();
					System.out.println("			AVL réinitialisé à vide");
					break;
				case (2):
					System.out.println("			" + a.estVide());
					break;
				case (3):
					System.out.println("			" + a.estFeuille());
					break;
				case (4):
					System.out.println("			String à rechercher : ");
					elt = sc.nextLine();
					System.out.println("			" + a.element(elt));
					break;
				case (5):
					System.out.println("			String à ajouter : ");
					elt = sc.nextLine();
					System.out.println("			VarH : " + a.ajout(elt));
					break;
				case (6):
					System.out.println("			String à supprimer : ");
					elt = sc.nextLine();
					a.suppr(elt);
					break;
				case (7):
					a.oterMax();
					break;
				case (8):
					a.oterMin();
					break;
				case (9):
					System.out.println("			Entrez un à un les Strings de la liste en validant entre chaque avec entrée, et entrez '@' pour indiquer que vous avez terminé\n");
					l = new ArrayList<String>();
					elt = " ";
					while (elt.charAt(0) != '@')
					{
						System.out.print("			=> ");
						elt = sc.nextLine();
						l.add(elt);
					}
					l.remove(l.size() - 1);
					System.out.print("		Liste saisie : [");
					for (int i = 0; i < l.size() - 1 ;i++)
						System.out.print(l.get(i) + ",");
					System.out.print(l.get(l.size() - 1) + "]\n");
					
					l = a.trier(l);
					System.out.print("		Liste triée : [");
					for (int i = 0; i < l.size() - 1 ;i++)
						System.out.print(l.get(i) + ",");
					System.out.println(l.get(l.size() - 1) + "]");
					break;
				case (10):
					System.out.println("			" + a.hauteur());
					break;
				case (11):
					System.out.println("			" + a.getBal());
					break;
				case (12):
					System.out.println("			" + a.max());
					break;
				case (13):
					System.out.println("			" + a.min());
					break;
				case (14):
					aff = !aff;
					break;					
				case (0):
					break;
				default:
					System.out.println("			Erreur de saisie utilisateur\n");
					break;
			}
			if (aff && (ch != 0))
			{
				System.out.println("\n 		Parcours préfixe de l'arbre : 		" + a.print(1));
				System.out.println(" 		Parcours symétrique de l'arbre : 	" + a.print(2));
				System.out.println(" 		Parcours postfixe de l'arbre : 		" + a.print(3));
			}
		} while (ch != 0);
	}
}