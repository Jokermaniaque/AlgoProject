import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Chapitres
{
	// --------------------------------------------------------------Attributs--------------------------------------------------------------

	private ArrayList<Page> pages_;			/*!< Tableau de pages => Arraylist<Pages> */
	private ArrayList<Integer> peres_;		/*!< Tableau de pères => ArrayList<Integer> */
	private AVLString dicoGen_;				/*!< Dictionnaire général => AVLString */





	// ------------------------------------------------------------Constructeurs------------------------------------------------------------

	/**
	 * [Chapitre => Constructeur vide]
	 * @return [Chapitre]
	 */
	public Chapitres()
	{
		pages_ = new ArrayList<Page>();
		peres_ = new ArrayList<Integer>();
		dicoGen_ = new AVLString();
	}

	/**
	 * [Chapitres => Initialise les chapitres, et construit le dictionnaire général avec tous les mots de toutes les pages, i.e. le dictionnaire contient tous les mots du livre]
	 * @param  pages [Tableau de Pages]
	 * @return       [Chapitres]
	 */
	public Chapitres(ArrayList<Page> pages)
	{
		pages_ = pages;
		peres_ = new ArrayList<Integer>();
		dicoGen_ = new AVLString();

		AVLString tmp = new AVLString();

		// Pour chaque page p, équivalent de Créer(p)
		for (int i = 0; i < pages_.size() ;i++)
			peres_.add(-1);

		dicoGen_.copy(pages_.get(0).getData()); // dictionnaire = copie des mots de la première page

		for (int i = 1; i < pages_.size() ;i++) // pour chaque autre page, ajout de ses mots au dictionnaire s'ils n'y sont pas déjà
		{
			tmp.copy(pages_.get(i).getData());
			while (!tmp.estVide())
			{
				dicoGen_.ajout(tmp.max());
				tmp.oterMax();
			}
		}
	}

	/**
	 * [Chapitres => Initialise les chapitres, et le dictionnaire est celui passé en entrée]
	 * @param  pages   [Tableau de Pages]
	 * @param  dicoGen [AVLString]
	 * @return         [Chapitres]
	 */
	public Chapitres(ArrayList<Page> pages, AVLString dicoGen)
	{
		pages_ = pages;
		peres_ = new ArrayList<Integer>();
		dicoGen_ = dicoGen;

		// Pour chaque page p, équivalent de Créer(p)
		for (int i = 0; i < pages_.size() ;i++)
			peres_.add(-1);
	}






	// --------------------------------------------------------------Getters--------------------------------------------------------------
	 
	/**
	 * [getPages => Retourne la page]
	 * @return [Page]
	 */
	public ArrayList<Page> getPages()	{ return pages_; }
	
	/**
	 * [getPeres => Retourne le tableau de pères]
	 * @return [ArrayList<Integer>]
	 */
	public ArrayList<Integer> getPeres()	{ return peres_; }

	/**
	 * [getDicoGen => Retourne le dictionnaire général]
	 * @return [AVLString]
	 */
	public AVLString getDicoGen()	{ return dicoGen_; }






	// --------------------------------------------------------------Setters--------------------------------------------------------------
	 
	/**
	 * [setPages => Remplace le tableau de page par celui passé en paramètre]
	 * @param pages [description]
	 */
	public void setPages(ArrayList<Page> pages)	{ pages_ = pages; }
	
	/**
	 * [setPeres => Remplace le tableau de pères par celui passé en paramètre]
	 * @param peres [description]
	 */
	public void setPeres(ArrayList<Integer> peres)	{ peres_ = peres; }

	/**
	 * [setDicoGen => Remplace le dictionnaire général par celui passé en paramètre]
	 * @param dicoGen [description]
	 */
	public void setDicoGen(AVLString dicoGen)	{ dicoGen_ = dicoGen; }






	// --------------------------------------------------------------Methodes--------------------------------------------------------------
	
	/**
	 * [classe => Retourne la page représentante de son chapitre]
	 * @param  p [Page]
	 * @return   [Page]
	 */
	public Page classe(Page p)
	{
		int ind = pages_.indexOf(p);
		//System.out.print("Page " + p.getId() + ", index dans pages_ : " + ind + ", indice dans peres_ : " + peres_.get(ind) + "\n"); 
		Page repr;

		// si p est représentant de sa classe
		if (peres_.get(ind) == -1)
			return p;
		else
		{	// sinon appel récursif de classe sur le père de p
			repr = classe(pages_.get(peres_.get(ind)));
			// p prend pour père le représentant de sa classe (compression du chemin du représentant de la classe de p vers p)
			peres_.set(ind, pages_.indexOf(repr));
			// retour du représentant de la classe de p
			return repr;
		}
	}

	/**
	 * [union => Réalise l'union des classes des pages P1 et P2 si elles ne font pas partie du même chapitre]
	 * @param p1 [Page]
	 * @param p2 [Page]
	 */
	public void union(Page p1, Page p2)
	{
		int indP1;
		int indP2;

		//if (classe(p1).getId() != classe(p2).getId())
		//{
			indP2 = pages_.indexOf(p2);
			peres_.set(indP2, pages_.indexOf(classe(p1)));
		//}
	}

	/**
	 * [nbPages => Retourne le nombre de pages du livre]
	 * @return [int]
	 */
	public int nbPages()
	{
		return pages_.size();
	}

	/**
	 * [reinitialiser => Réinitialise tous les chapitres (chaque page redevient l'unique page de son chapitre, i.e. nombre de chapitres == nombre de pages du livre)]
	 */
	public void reinitialiser()
	{
		for (int i = 0; i < pages_.size() ;i++)
			peres_.set(i, -1);
	}

	/**
	 * [nbChaps => Retourne le nombre de chapitres du livre (calculé ou non)]
	 * @return [description]
	 */
	public int nbChaps()
	{
		int cpt = 0;
		for (int i = 0; i < peres_.size() ;i++)
		{
			if (peres_.get(i) == -1)
				cpt++;
		}
		return cpt;
	}

	/**
	 * [formerChapsWithK => Forme les chapitres en fonction de l'entier k définissant le nombre de mots en commun que deux pages doivent avoir pour être dans le même chapitre. N.B.: Avec les classes-union, la transitivité est "native"]
	 * @param k [description]
	 */
	public void formerChapsWithK(int k)
	{
		for (int i = 0; i < pages_.size() - 1 ;i++) // de la première à l'avant-dernière page
		{
			for (int j = i + 1; j < pages_.size() ;j++) // de la (i+1)ème page à la dernière page
			{
				if (classe(pages_.get(i)).getId() != classe(pages_.get(j)).getId())
				{
					if (pages_.get(i).memeChap(pages_.get(j), k))
						union(pages_.get(i), pages_.get(j));
					//k = kTmp; // if k is passed by reference in memeChap ?
				}
			}
		}
	}
	
	/**
	 * [result => Retourne un tableau contenant le résultat : chaque case est un tableau correspondant à un chapitre, contenant les identifiants des pages y appartenant]
	 * @return [ArrayList<Arraylist<Integer>>]
	 */
	public ArrayList<ArrayList<Integer>> result()
	{
		int idReprTmp;
		boolean chapTrouve;
		int i,j;
		Page p;

		ArrayList<ArrayList<Integer>> chaps = new ArrayList<ArrayList<Integer>>();

		// Initialisation de la première page du premier chapitre comme étant la page représentante du chapitre contenant la première page de la liste de pages (difficile d'être plus clair !)
		chaps.add(new ArrayList<Integer>());
		chaps.get(0).add(classe(pages_.get(0)).getId());

		// Si la première page de la liste de pages n'est pas la représentante de son chapitre, on l'ajoute également à celui-ci
		if (pages_.get(0).getId() != chaps.get(0).get(0))
			chaps.get(0).add(pages_.get(0).getId());
		
		// Puis boucle à partir de la deuxième page de la liste jusqu'à la dernière
		for (i = 1; i < pages_.size() ;i++)
		{
			p = pages_.get(i);
			chapTrouve = false;

			idReprTmp = classe(p).getId(); // idReprTmp == id du représentant du chapitre dont fait partie la page courante
			
			j = 0;
			
			// pour chaque chapitre déjà créé, on regarde si la page courante y appartient
			while ((j < chaps.size()) && (!chapTrouve))
			{
				// si oui, on l'ajoute au chapitre (et on sort de la boucle)
				if (idReprTmp == chaps.get(j).get(0))
				{
					if (p.getId() != idReprTmp) // (pour éviter d'ajouter deux fois la page au chapitre, si elle en est la représentante)
						chaps.get(j).add(p.getId());
					chapTrouve = true;
				}
				j++;
			}

			// si la page n'appartenait à aucun chapitre déjà créé, alors on le crée
			if (!chapTrouve)
			{
				chaps.add(new ArrayList<Integer>());
				// on ajoute le représentant du chapitre en premier lieu
				chaps.get(chaps.size() - 1).add(idReprTmp);
				// si la page courante n'est pas sa propre représentante, on l'ajoute aussi
				if (p.getId() != idReprTmp)
					chaps.get(chaps.size() - 1).add(p.getId());
			}
			//System.out.println(i + ", repr = " + idReprTmp);
		}

		return chaps;
	}

	/**
	 * [ajoutPage => Ajoute la page passée en paramètre au tableau de pages]
	 * @param p [Page]
	 */
	public void ajoutPage(Page p)
	{
		pages_.add(p);
	}

	/**
	 * [enleverPage => Retire du tableau de page la page dont l'identifiant est égal à celui passé en paramètre, et renvoie vrai si elle était présente (elle est alors supprimée), faux sinon]
	 * @param  idPage [entier]
	 * @return        [booléen]
	 */
	public boolean enleverPage(int idPage)
	{
		int i  = 0;
		boolean trouve = false;

		while ((i < pages_.size()) && (!trouve))
		{
			if (pages_.get(i).getId() == idPage)
			{
				trouve = true;
				pages_.remove(pages_.get(i));
			}
			i++;
		}

		return trouve;
	}
}