// Main : Laisse le choix entre :
//			Construire un dictionnaire contenant tous les mots du livre et faire l'analyse des chapitres
//			Fair l'analyse avec un dictionnaire fourni en tant que fichier			

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Main
{
	public static void main(String args[])
	{
		Scanner input; // file in
		int ch;
		int nbChar = 2;

		String str = "";
		String tmp;
		String[] wordArray;
		File fileIn = new File("");
		File fileOut = new File("");
		BufferedWriter output;

		ArrayList<String> strList = new ArrayList<String>();

		AVLString dicoGen = new AVLString();

		System.out.println("(1) Construire automatiquement un dictionnaire contenant tous les mots du livre ?");
		System.out.println("(2) Utiliser un fichier contenant les mots que vous voulez ?");

		Scanner sc = new Scanner(System.in);

		ch = sc.nextInt();
		sc.nextLine();

		try
		{
			fileOut = new File("../Test/dicoGen.txt"); // file out
			output = new BufferedWriter(new FileWriter(fileOut)); // to write to output

			if (ch == 1)
			{
				for (int i = 0; i < 146 ;i++) // for all pages
				{
					fileIn = new File("../Andersen - pages/" + String.valueOf(i + 1)); // open page (i + 1)
					input = new Scanner(fileIn);
					
					// build string containing all text from the page
					while (input.hasNextLine())
						str += " " + input.nextLine();
				}
			}
			else if (ch == 2)
			{
				fileIn = new File("../dico1.txt");
				input = new Scanner(fileIn);
				while (input.hasNextLine())
					str += " " + input.nextLine();
			}
			
			// separating words (only words)
			wordArray = str.split("(\\W)|(\\d)|(\\s)");
			
			// adding words to an AVL if length >= 2 (all in lower case)
			if (ch == 1)
			{
				System.out.print("Donner le nombre minimal de caractères qu'un mot doit contenir pour être retenu dans le dictionnaire : ");
				nbChar = sc.nextInt();
				sc.nextLine();
			} 
			for (int j = 0; j < wordArray.length ;j++)
			{
				tmp = wordArray[j];
				System.out.println(tmp);
				if (ch == 1)
				{
					Pattern pattern = Pattern.compile(".{" + String.valueOf(nbChar) + ",}");
					Matcher matcher = pattern.matcher(tmp);
					if (matcher.find())
					{
						System.out.println("test : " + tmp);
						dicoGen.ajout(wordArray[j].toLowerCase(Locale.ENGLISH));
					}
				}
				else if (ch == 2)
					dicoGen.ajout(wordArray[j].toLowerCase(Locale.ENGLISH));
			}
			
			// getting words in lexicographic order from the AVL
			strList = dicoGen.symm();
			
			// écriture de tous les mots du dictionnaire) dans le fichier dicoGen.txt
			output.write(strList.get(0) + "\n");
			for (int j = 1; j < strList.size() ;j++)
				output.write(strList.get(j) + "\n");
			output.close();
		//	} catch ( IOException e ) { e.printStackTrace(); }

		
		//else if (sc == 1)
		//{
		//	System.out.println("Le fichier utilisé est '../dico1.txt', si vous ne l'avez pas déjà remplacé\navec votre propre dictionnaire, faites puis relancez le programme SVP");
		//}



		

		/***************************************************************************************/

			Page p;
			int k;
			AVLString a = new AVLString();
			ArrayList<Page> pages = new ArrayList<Page>();
	
			for (int i = 0; i < 146 ;i++)
			{
				fileIn = new File("../Andersen - pages/" + String.valueOf(i + 1)); // open page (i + 1)
				//input = new Scanner(fileIn);
				
				p = new Page(fileIn,dicoGen);
				
				//p.consDicoPage(dicoGen);

				a.copy(p.getData());
				fileOut = new File("../Test/dicoPage" + String.valueOf(p.getId()) + ".txt"); // file out
				output = new BufferedWriter(new FileWriter(fileOut)); // to write to output

				// écriture de tous les mots de la page i (faisant aussi partie du dictionnaire) dans le fichier dicoPage\i\.txt
				while (!(a.estVide()))
				{
					output.write(a.max() + " ");
					a.oterMax();
				}
				output.close();
				pages.add(p);
			}
	
			Chapitres c = new Chapitres(pages, dicoGen);

			System.out.print("	K : ");
			k = sc.nextInt();

			c.formerChapsWithK(k);

			ArrayList<ArrayList<Integer>> res = c.result();

			// Ecriture du résultat dans le fichier results(K=\k\).txt
			if (ch == 1)
				fileOut = new File("../Test/results(Mots d'au moins " + String.valueOf(nbChar) + " caractères, (K=" + String.valueOf(k) + ").txt");
			else
				fileOut = new File("../Test/results(Dico prédéfini, K=" + String.valueOf(k) + ").txt");
			output = new BufferedWriter(new FileWriter(fileOut)); // to write to output

			for (int i = 0; i < res.size() ;i++)
			{
				output.write("	Le chapitre " + (i + 1) + " contient les pages d'identifiants : ");
				for (int j = 0; j < res.get(i).size() ;j++)
				{
					output.write(res.get(i).get(j) + ", ");
					//System.out.print(res.get(i).get(j) + " ");
				}
				//System.out.println("\n");
				output.write("\n");
			}
			output.close();

			str = "[";
			for (int i = 0; i < c.getPeres().size() - 1 ;i++)
				str += c.getPeres().get(i) + ",";
			str += c.getPeres().get(c.getPeres().size() - 1);
			str += "]";
			System.out.println(str);
		} catch ( IOException e ) { e.printStackTrace(); }
	}
}