// Classe définissant une page
// 
// Une page est définie par son identifiant (entier), un AVLString (data_) contenant tous les mots de la page, et l'identifiant (entier) du chapitre auquel elle appartient (-1 si indéterminé)
// 
// Une page vide a son identifiant égal à zéro, le numéro de son chapitre égal à -1 et l'AVLString contenant ses mots étant un arbre vide.

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Page
{
	// --------------------------------------------------------------Attributs--------------------------------------------------------------

	private int id_;			/*!< identifiant de la page => int */
	private int chap_;			/*!< numéro du chapitre de la page, égal à -1 si chapitre indéterminé => int */
	private AVLString data_;	/*!< contient tous les mots de la page => AVLString */

	 
	 
	
	

	// ------------------------------------------------------------Constructeurs------------------------------------------------------------

	/**
	 * [Page => Constructeur vide]
	 * @return [Page vide]
	 */
	public Page()
	{
		id_ = 0;
		chap_ = -1;
		data_ = new AVLString();
		//dicoPage_ = new AVLString();
	}

	/**
	 * [Page => Constructeur initialisé à partir d'un fichier texte]
	 * @param  f [fichier texte contenant une page d'un livre]
	 * @return   [Page initialisée]
	 */
	public Page(File f, AVLString dicoGen)
	{
		String[] wordArray;
		String str = "";
		
		chap_ = -1;
		data_ = new AVLString();
		//dicoPage_ = new AVLString();
		
		// création d'une chaine contenant tout le texte du fichier f
		try
		{
			Scanner input = new Scanner(f);

			// Getting id
			id_ = input.nextInt();
			input.nextLine();
		
			// Getting content in String
			while (input.hasNextLine())
				str += " " + input.nextLine();
	
				
			// séparation des mots de la chaîne, chaque mot est placé dans une case d'un tableau de String
			wordArray = str.split("(\\W)|(\\d)|(\\s)");
	
			// ajout des mots du tableau de String à l'AVLString data_ si ceux-ci sont référencés dans le dictionnaire général
			for (int j = 0; j < wordArray.length ;j++)
			{
				String tmp = wordArray[j].toLowerCase(Locale.ENGLISH);
				//Pattern pattern = Pattern.compile(".{2,}");
				//Matcher matcher = pattern.matcher(tmp);
				//if (matcher.find())
				if (dicoGen.element(tmp))
					data_.ajout(tmp);
			}
		} catch ( IOException e ) { e.printStackTrace(); }
	}






	// --------------------------------------------------------------Getters--------------------------------------------------------------

	/**
	 * [getId => Retourne l'identifiant de la page]
	 * @return [entier]
	 */
	public int getId()	{ return id_; }

	/**
	 * [getData => retourne les mots de la page]
	 * @return [AVLString]
	 */
	public AVLString getData()	{ return data_; }

	//public AVLString getDicoPage()	{ return dicoPage_; }





	// --------------------------------------------------------------Setters--------------------------------------------------------------

	/**
	 * [setId => remplace l'identifiant par l'entier en paramètre]
	 * @param id [entier]
	 */
	public void setId(int id)	{ id_ = id; }

	/**
	 * [setData remplace data_ par l'AVLString en paramètre]
	 * @param data [description]
	 */
	public void setData(AVLString data)	{ data_ = data; }

	//public void setDicoPage(AVLString dicoPage)	{ dicoPage_ = dicoPage; }






	// --------------------------------------------------------------Methodes--------------------------------------------------------------
	 
	//public void consDicoPage(AVLString dicoGen)
	//{
	//	AVLString a = new AVLString();
	//	String str;

	//	a.copy(data_);

	//	while (!(a.estVide()))
	//	{
	//		str = a.max();
	//		
	//		if (dicoGen.element(str))
	//			dicoPage_.ajout(str);
	//		a.oterMax();
	//	}
	//}

	/**
	 * [memeChap => Retourne vrai si la page fait partie du même chapitre que celle passée en paramètre, i.e. si elles ont k mots en commun]
	 * @param  p [Page]
	 * @param  k [Entier]
	 * @return   [Booléen]
	 */
	public boolean memeChap(Page p, int k)
	{
		AVLString a = new AVLString();

		a.copy(data_);
		String str;

		while ((k > 0) && (!(a.estVide())))
		{
			str = a.max();
			
			if (p.getData().element(str))
				k--;
			a.oterMax();
		}

		return (k == 0);
	}
}