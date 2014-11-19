// TestBattery : Effectue une batterie de tests en construisant automatiquement un dictionnaire, à partir de tous les mots du livre pour des mots de longueurs minimale allant de nbCharDeb à nbCharFin caractères et des entier k allant de kDeb à kFin
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class TestBattery
{
	public static void main(String args[])
	{
		Scanner input; // file in
		Scanner sc = new Scanner(System.in);

		int ch;
		int nbChar = 2;

		int nbCharDeb, nbCharFin, kDeb, kFin;

		String str = "";
		String tmp;
		String[] wordArray;
		File fileIn = new File("");
		File fileOut = new File("");
		BufferedWriter output;
		Page p;
		int k;
		ArrayList<Page> pages;
		Chapitres c;
		ArrayList<ArrayList<Integer>> res;// = new ArrayList<ArrayList<Integer>>();

		ArrayList<String> strList = new ArrayList<String>();

		AVLString a;

		AVLString dicoGen;

		ArrayList<ArrayList<Integer>> resultNbCharK = new ArrayList<ArrayList<Integer>>();

		System.out.println("********** Pensez à supprimer tous les fichiers dans le répertoire Test avant de lancer le programme **********\n");

		System.out.println("Intervalle de nombre minimal de caractères pour qu'un mot soit ajouté au dictionnaire : ");
		System.out.println("	Nombre de début d'intervalle (conseil trivial : au moins 2) : ");
		nbCharDeb = sc.nextInt();
		System.out.println("	Nombre de fin d'intervalle : ");
		nbCharFin = sc.nextInt();
		System.out.println("Intervalle pour k, le nombre de mots en commun nécessaire pour que deux pages fassent partie du même chapitre : ");
		System.out.println("	Nombre de début d'intervalle : ");
		kDeb = sc.nextInt();
		System.out.println("	Nombre de fin d'intervalle (nb : rentrez -1 si vous voulez que le programme continue jusqu'à ce qu'il n'y ait plus de chapitre contenant deux pages ou plus) : ");
		kFin = sc.nextInt();
		
		try
		{
			// ******************************** Constuction d'une chaîne de caractères contenant tous les mots du livre ********************************
			for (int i = 0; i < 146 ;i++) // i == numéro de page
			{
				fileIn = new File("../Andersen - pages/" + String.valueOf(i + 1)); // ouverture fichier page (i + 1)
				input = new Scanner(fileIn);
				
				// Construction d'une chaîne contenant les mots de toutes les pages
				while (input.hasNextLine())
					str += " " + input.nextLine();

			}
			wordArray = str.split("(\\W)|(\\d)|(\\s)"); // coupure pour n'avoir que les mots
			// *****************************************************************************************************************************************



			// ************************************************ Constuction des dictionnaires et des pages *********************************************
			for (int l = nbCharDeb; l <= nbCharFin ;l++) // l = nombre de caractères minimal pour qu'un mot oit ajouté au dictionnaire
			{

				// **************** Constuction du dictionnaire contenant les mots de taille au moins l ****************
				dicoGen = new AVLString(); // initialisation dictionnaire

				fileOut = new File("../Test/nbChar=" + String.valueOf(l) + "/dicoGen.txt"); // écriture des mots du dictionnaire général dans un fichier
				output = new BufferedWriter(new FileWriter(fileOut));

				for (int j = 0; j < wordArray.length ;j++) // tous les mots
				{
					tmp = wordArray[j];
					Pattern pattern = Pattern.compile(".{" + String.valueOf(l) + ",}"); // mots de longueur au moins l
					Matcher matcher = pattern.matcher(tmp);
					if (matcher.find())
						dicoGen.ajout(wordArray[j].toLowerCase(Locale.ENGLISH)); // ajout dans l'AVL contenant le dictionnaire
				}

				strList = dicoGen.symm(); // récupération des mots du dictionnaire dans l'ordre lexicographique
			
				// écriture de tous les mots du dictionnaire dans le fichier dicoGen.txt
				for (int j = 0; j < strList.size() ;j++)
					output.write(strList.get(j) + "\n");
				output.close();
				// *****************************************************************************************************
				
				// *************** Construction de toutes les pages avec des mots de taille au moins l *****************
				pages = new ArrayList<Page>(); // initialisations tableau de pages

				for (int i = 0; i < 146 ;i++) // Page (i + 1)
				{
					a = new AVLString(); // Initialisation dictionnaire de la page

					fileIn = new File("../Andersen - pages/" + String.valueOf(i + 1)); // ouverture fichier page (i + 1)
					
					p = new Page(fileIn,dicoGen); // Construction de la page (i + 1)
	
					a.copy(p.getData());

					fileOut = new File("../Test/nbChar=" + String.valueOf(l) + "/dicoPage" + String.valueOf(p.getId()) + ".txt"); // écriture des mots du dictionnaire de la page (i + 1) dans un fichier 
					output = new BufferedWriter(new FileWriter(fileOut));

					// écriture de tous les mots de la page i (faisant aussi partie du dictionnaire) dans le fichier dicoPage\i\.txt
					while (!(a.estVide()))
					{
						output.write(a.max() + " ");
						a.oterMax();
					}
					output.close();
					pages.add(p); // ajout de la page (i + 1) au tableau de pages
				}
				// *****************************************************************************************************
				
				c = new Chapitres(pages, dicoGen); // Initialisation classe-union de chapitres
				// **************** Constuction des chapitres pour  le nombre de charactères et le nombre de mots courant dans la boucle ****************
				k = kDeb;
				res = new ArrayList<ArrayList<Integer>>();
					
				while (res.size() < 146)
				{
					if ((kFin != -1) && (k >= kFin))
						break;

					c.reinitialiser();
					c.formerChapsWithK(k); // formation des chapitres
					res = c.result(); // Récupération des chapitres dans un tableau

					fileOut = new File("../Test/nbChar=" + String.valueOf(l) + "/results(K=" + String.valueOf(k) + ").txt"); // écriture des résultats dans un fichier
					output = new BufferedWriter(new FileWriter(fileOut));

					for (int m = 0; m < res.size() ;m++) // Pour tous les chapitres
					{
						output.write("	Le chapitre " + (m + 1) + " contient les pages d'identifiants : ");
						for (int n = 0; n < res.get(m).size() ;n++) // Pour toutes les pages
							output.write(res.get(m).get(n) + ", ");
						output.write("\n");
					}
					output.close();
					ArrayList<Integer> resultsTestTmp = new ArrayList<Integer>(); // initialisation enregistrement résultats sous la forme [nombre de charactères,entier k,nombre de chapitres obtenus]
					resultsTestTmp.add(l);
					resultsTestTmp.add(k);
					resultsTestTmp.add(res.size());
					resultNbCharK.add(resultsTestTmp); // enregistrement du résultat pour le nombre de charactères et le nombre de mots courant dans la boucle
					k++;
				}
			}

		} catch ( IOException e ) { e.printStackTrace(); }

		// Affichage de tous les résultats
		for (int i = 0; i < resultNbCharK.size() ;i++)
			System.out.println("Résultat obtenu pour un dictionnaire contenant tous les mots d'au moins " + resultNbCharK.get(i).get(0) + " caractères et pour k = " + resultNbCharK.get(i).get(1) + " : " + resultNbCharK.get(i).get(2));
	}
}