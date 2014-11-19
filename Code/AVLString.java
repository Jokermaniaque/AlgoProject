// Classe définissant les AVL (pour des éléments de type String seulement)
// 
// Un AVL est défini par sa racine (String), ses fils gauches et droits (AVL également) et sa balance
// 
// Un AVL vide est défini comme ayant sa racine égale à "@", ses deux fils égaux à null et sa balance égale à 0.
// 
// Un AVL feuille est défini comme ayant sa racine différente de "@", ses deux fils égaux à des arbres vides  et sa balance égale à 0 (ainsi un AVL vide n'est pas considéré comme un AVL feuille)
// 
// Il ne peut y avoir deux fois le même élément (String) dans l'AVLString avec cette implémentation

import java.util.*;

public class AVLString
{
	// --------------------------------------------------------------Attributs--------------------------------------------------------------
	
	private String rac_;		/*!< racine  String */
	private AVLString filsG_;	/*!< fils gauche -> lui-même AVL */
	private AVLString filsD_;	/*!< fils droit -> lui-même AVL */
	private int bal_;			/*!< balance -> entier */







	// ------------------------------------------------------------Constructeurs------------------------------------------------------------
	//
	// Trois constructeurs sont disponibles.
	// Le premier construit un AVL vide
	// Les suivant sont renvoyés sur le premier via un test en cas de mauvais paramètres -> passer "@" comme racine ou null pour l'un des sous-arbres
	//

	/**
	 * [AVLString => Construit un AVL "vide"]
	 * @return [équivalent d'un AVL vide -> racine et sous arbres non initialisés]
	 */
	public AVLString()
	{
		rac_ = "@";
		filsG_ = null;
		filsD_ = null;
		bal_ = 0;
	}

	/**
	 * [AVLString => Construit un AVL "feuille"]
	 * @param  rac [valeur de la racine]
	 * @return     [équivalent d'un AVL feuille -> racine initialisée et sous-arbres vides]
	 */
	public AVLString(String rac)
	{
		this();
		if (rac != "@")
		{
			rac_ = rac;
			filsG_ = new AVLString();
			filsD_ = new AVLString();
		}
	}

	/**
	 * [AVLString => Construit un AVL donc la forme dépendra des paramètres passés]
	 * @param  rac   [valeur de la racine]
	 * @param  filsG [AVL qui sera fils gauche]
	 * @param  filsD [AVL qui sera fils droit]
	 * @return       [AVL de forme quelconque, selon les paramètres passés]
	 */
	public AVLString(String rac, AVLString filsG, AVLString filsD)
	{
		this();
		if ((rac != "@") && (filsG != null) && (filsD != null))
		{
			rac_ = rac;
			filsG_ = filsG;
			filsD_ = filsD;
			if ((!filsG_.estVide()) && (!filsD_.estVide()))
				bal_ = filsD_.hauteur() - filsG_.hauteur() + 1;
		}
	}






	// --------------------------------------------------------------Getters--------------------------------------------------------------
	
	/**
	 * [elt => Retourne la racine de l'AVL]
	 * @return [String (valeur de la racine)]
	 */
	public String elt()	{return rac_;}
	
	/**
	 * [sag => Retourne le sous-arbre gauche de l'AVL]
	 * @return [AVL (sous-arbre gauche)]
	 */
	public AVLString sag()	{return filsG_;}
	
	/**
	 * [sad => Retourne le sous-arbre droit de l'AVL]
	 * @return [AVL (sous-arbre droit)]
	 */
	public AVLString sad()	{return filsD_;}

	/**
	 * [getBal => Retourne la balance de l'AVL]
	 * @return [entier (balance)]
	 */
	public int getBal()	{return bal_;}








	// --------------------------------------------------------------Setters--------------------------------------------------------------
	
	/**
	 * [setRac => Remplace la valeur de la racine]
	 * @param rac [String]
	 */
	public void setRac(String rac)	{rac_ = rac;}
	
	/**
	 * [setFilsG => Remplace le fils gauche]
	 * @param filsG [AVL]
	 */
	public void setFilsG(AVLString filsG)	{filsG_ = filsG;}
	
	/**
	 * [setFilsD => Remplace le fils droit]
	 * @param filsD [AVL]
	 */
	public void setFilsD(AVLString filsD)	{filsD_ = filsD;}

	/**
	 * [setBal => Remplace la balance]
	 * @param bal [entier]
	 */
	public void setBal(int bal)	{bal_ = bal;}








	// --------------------------------------------------------------Methodes--------------------------------------------------------------
	
	/**
	 * [avlVide => Remplace l'AVL par un arbre vide]
	 */
	public void avlVide()
	{
		rac_ = "@";
		filsG_ = null;
		filsD_ = null;
		bal_ = 0;
	}
	
	/**
	 * [estVide => Renvoie vrai si l'AVL est vide, faux sinon]
	 * @return [booléen]
	 */
	public boolean estVide()
	{
		return ((rac_ == "@"));// && (filsG_ == null) && (filsD_ == null));
	}

	/**
	 * [estFeuille => Renvoie vrai si l'AVL correspond à un AVL feuille, faux sinon]
	 * @return [booléen]
	 */
	public boolean estFeuille()
	{
		if (!estVide())
			return ((rac_ != "@") && (filsG_.estVide()) && (filsD_.estVide()));
		else
			return false;
	}

	/**
	 * [bal => Recalcule récursivement la balance de l'AVL et celles de tous ses fils]
	 */
	public void bal()
	{
		if ((estVide()) || (estFeuille()))
			bal_ = 0;
		else
		{
			bal_ = filsD_.hauteur() - filsG_.hauteur();
			filsG_.bal();
			filsD_.bal();
		}
	}
	
	/**
	 * [hauteur => Renvoie la hauteur de l'AVL (la racine étant à la hauteur 0)]
	 * @return [entier]
	 */
	public int hauteur()
	{
		if ((estVide()) || ((filsG_.estVide()) && (filsD_.estVide())))
			return 0;
		else
			return Math.max(filsG_.hauteur(), filsD_.hauteur()) + 1;
	}
	
	/**
	 * [element => Renvoie vrai si elt est présent dans l'AVL, faux sinon]
	 * @param  elt [String]
	 * @return     [booléen]
	 */
	public boolean element(String elt)
	{
		if (estVide())
			return false;
		else if (rac_.compareTo(elt) == 0)
			return true;
		else if (elt.compareTo(rac_) < 0)
			return filsG_.element(elt);
		else
			return filsD_.element(elt);
	}

	/**
	 * [ajout => Ajoute elt à l'AVL, et retourne la variation de hauteur]
	 * @param elt [String]
	 * @return     [entier]
	 */
	public int ajout(String elt)
	{
		int h = 0;
		if (elt != "@")
		{
			if (estVide())
			{
				rac_ = elt;
				filsG_ = new AVLString();
				filsD_ = new AVLString();
				bal_ = 0;
				return 1;
			}
			else if (elt == rac_)
				return 0;
			else
			{
				if (elt.compareTo(rac_) > 0)
					h = filsD_.ajout(elt);
				else if (elt.compareTo(rac_) < 0)
					h = 0 - filsG_.ajout(elt);
				if (h == 0)
					return 0;
				else
				{
					bal_ += h;
					equilibrer();
					if (bal_ == 0)
						return 0;
					else
						return 1;
				}
			}
		}
		else
			return 0;
	}
	
	/**
	 * [suppr => Retire le noeud ayant elt pour élément de l'AVL (s'il existe)]
	 * @param elt [String]
	 */
	public int suppr(String elt)
	{
		int h = 0;
		if (!estVide())
		{
			if (elt.compareTo(rac_) > 0)
				h = filsD_.suppr(elt);
			else if (elt.compareTo(rac_) < 0)
				h = 0 - filsG_.suppr(elt);
			else /* if (elt == rac_) */ if (filsG_.estVide())
			{
				oterMin();
				return -1;
			}
			else if (filsD_.estVide())
			{
				oterMax();
				return -1;
			}
			else
			{
				rac_ = filsD_.min();
				h = filsD_.oterMin();
			}
			if (h == 0)
				return 0;
			else
			{
				bal_ += h;
				equilibrer();
				if (bal_ == 0)
					return -1;
				else
					return 0;
			}
		}
		else
			return 0;
	}

	/**
	 * [copy => Retourne une copie de l'AVL passé en paramètre]
	 * @param av [AVL]
	 */
	public void copy(AVLString av)
	{
		if ((av.rac_ == "@"))// || (av.filsG_ == null) || (av.filsD_ == null))
		{
			rac_ = "@";
			bal_ = 0;
			filsG_ = null;
			filsD_= null;
		}
		else
		{
			rac_ = av.rac_;
			bal_ = av.bal_;
			filsG_ = new AVLString();
			filsG_.copy(av.filsG_);
			filsD_ = new AVLString();
			filsD_.copy(av.filsD_);
		}
	}

	/**
	 * [rotG => Effectue une rotation gauche (simple) sur l'AVL]
	 */
	public void rotG()
  	{
  		int a, b;
  		AVLString av = new AVLString();

  		System.out.println("ROTG(" + rac_ + ")");
	
		av.copy(filsD_);
		a = bal_;
		b = av.bal_;
		
		if (filsD_ == null)
			filsD_ = new AVLString();
		if (av.filsG_ == null)
			av.filsG_ = new AVLString();
		
		filsD_.copy(av.filsG_);
		av.filsG_.copy(this);
		
		copy(av);
		
		//bal_ = a - Math.max(b, 0) - 1;
		//filsG_.bal_ = Math.min(a - 2, a + b - 2);
		//filsG_.bal_ = Math.min(filsG_.bal_, b - 1);
		
		if (filsG_ == null)
			filsG_ = new AVLString();

		if (b == 2)
		{
			bal_ = 0;
			filsG_.bal_ = -1;
		}
		else if (b == 1)
		{
			bal_ = 0;
			filsG_.bal_ = 0;
		}
		else if (b == 0)
		{
			bal_ = -1;
			if (a == 1)
				filsG_.bal_ = 0;
			else
				filsG_.bal_ = 1;
		}
		else // if (b == -1)
		{
			bal_ = -2;
			filsG_.bal_ = 0;
		}
	}

	/**
	 * [rotD  => Effectue une rotation droite (simple) sur l'AVL]
	 */
	public void rotD()
	{
		int a, b;
		AVLString av = new AVLString();

		System.out.println("ROTD(" + rac_ + ")");

		av.copy(filsG_);
		a = bal_;
		b = av.bal_;

		if (filsG_ == null)
			filsG_ = new AVLString();
		if (av.filsD_ == null)
			av.filsD_ = new AVLString();

		filsG_.copy(av.filsD_);
		av.filsD_.copy(this);
		
		copy(av);
		
		//bal_ = -a + Math.min(b, 0) - 1;
		//filsD_.bal_ = Math.min(a + 2, a - b + 2);
		//filsD_.bal_ = Math.min(filsD_.bal_, b + 1);
		
		if (filsD_ == null)
			filsD_ = new AVLString();

		if (b == -2)
		{
			bal_ = 0;
			filsD_.bal_ = 1;
		}
		else if (b == -1)
		{
			bal_ = 0;
			filsD_.bal_ = 0;
		}
		else if (b == 0)
		{
			bal_ = 1;
			if (a == -1)
				filsD_.bal_ = 0;
			else
				filsD_.bal_ = -1;
		}
		else // if (b == 1)
		{
			bal_ = 2;
			filsG_.bal_ = 0;
		}
	}

	/**
	 * [dRotG => Effectue une double rotation gauche sur l'AVL]
	 */
	public void dRotG()
	{
		System.out.print("DROTG(" + rac_ + ") : ");

		filsD_.rotD();
		rotG();
	}

	/**
	 * [dRotD => Effectue une double rotation droite sur l'AVL]
	 */
	public void dRotD()
	{
		System.out.print("DROTD(" + rac_ + ") : ");

		filsG_.rotG();
		rotD();
	}

	/**
	 * [equilibrer => Méthode appellée pour équilibrer l'AVL après ajout ou suppression]
	 */
	public void equilibrer()
	{
		if (bal_ == 2)
		{
			if (filsD_.bal_ >= 0)
				rotG();
			else
				dRotG();
		}
		else if (bal_ == -2)
		{
			if (filsG_.bal_ <= 0)
				rotD();
			else
				dRotD();
		}
	}

	/**
	 * [max => Retourne le plus grand élément de l'AVL (-1 si l'AVL est vide)]
	 * @return [entier]
	 */
	public String max()
	{
		if (estVide())
			return "@";
		else if (filsD_.estVide())
			return rac_;
		else
			return filsD_.max();
	}
	
	/**
	 * [min => Retourne le plus petit élément de l'AVL ("@" si l'AVL est vide)]]
	 * @return [entier]
	 */
	public String min()
	{
		if (estVide())
			return "@";
		else if (filsG_.estVide())
			return rac_;
		else
			return filsG_.min(); 
	}
	
	/**
	 * [oterMax => Retire le noeud contenant le plus grand élément de l'AVL (si celui-ci n'est pas vide)]
	 * @return [entier (correspondant à la variation de hauteur)]
	 */
	public int oterMax()
	{
		int h;

		if (!estVide())
		{
			if (estFeuille())
			{
				this.avlVide();
				return 0;
			}
			else if (filsD_.estVide())
			{
				rac_ = filsG_.max();
				bal_ += filsG_.oterMax();
				return -1;
			}
			else
			{
				h = filsD_.oterMax();
				if (h == 0)
					return 1;
				else
				{
					bal_ += h;
					equilibrer();
					if (bal_ == 0)
						return 1;
					else
						return 0;
				}
			}
		}
		else
			return 0;
	}
	
	/**
	 * [oterMin => Retire le noeud contenant le plus petit élément de l'AVL (si celui-ci n'est pas vide)]
	 * @return [entier (correspondant à la variation de hauteur)]
	 */
	public int oterMin()
	{
		int h;

		if (!estVide())
		{
			if (estFeuille())
			{
				this.avlVide();
				return 1;
			}
			else if (filsG_.estVide())
			{
				rac_ = filsD_.min();
				bal_ -=	filsD_.oterMin();
				return -1;
			}
			else
			{
				h = 0 - filsG_.oterMin();
				if (h == 0)
					return 0;
				else
				{
					bal_ += h;
					equilibrer();
					if (bal_ == 0)
						return -1;
					else
						return 0;
				}
			}
		}
		else
			return 0;
	}
	
	/**
	 * [trier => Trie une liste de String en utilisant un AVL (ordre lexicographique)]
	 *  NB: La méthode n'utilise pas l'AVL auquel la méthode est appliquée au cas ou celui-ci ne serait pas vide, ce qui fausserait le résultat
	 * @param  l [ArrayList<String> liste de Strings dont l'ordre est quelconque]
	 * @return   [ArrayList<String> liste de Strings triées par ordre croissant]
	 */
	public ArrayList<String> trier(ArrayList<String> l)
	{
		AVLString a = new AVLString();
		for (int i = 0; i < l.size() ;i++)
			a.ajout(l.get(i));
		return a.symm();
	}
	
	/**
	 * [pref => Retourne le parcours préfixe de l'AVL]
	 * @return [ArrayList<String>]
	 */
	public ArrayList<String> pref()
	{
		ArrayList<String> a = new ArrayList<String>();

		if (!estVide())
		{
			a.add(rac_);
			//b.add(bal_);
			if (!filsG_.estVide())
				a.addAll(filsG_.pref());
			if (!filsD_.estVide())
				a.addAll(filsD_.pref());
		}
		return a;
	}
	
	/**
	 * [symm => Retourne le parcours symétrique de l'AVL]
	 * @return [ArrayList<String>]
	 */
	public ArrayList<String> symm()
	{
		ArrayList<String> a = new ArrayList<String>();

		if (!estVide())
		{
			if (!filsG_.estVide())
				a.addAll(filsG_.symm());
			a.add(rac_);
			//a.add(bal_);
			if (!filsD_.estVide())
				a.addAll(filsD_.symm());
		}
		return a;
	}
	
	/**
	 * [post => Retourne le parcours postfixe de l'AVL]
	 * @return [ArrayList<String>]
	 */
	public ArrayList<String> post()
	{
		ArrayList<String> a = new ArrayList<String>();

		if (!estVide())
		{
			if (!filsG_.estVide())
				a.addAll(filsG_.post());
			if (!filsD_.estVide())
				a.addAll(filsD_.post());
			a.add(rac_);
			//a.add(bal_);
		}
		return a;
	}
	
	/**
	 * [print => Renvoie une chaîne représentant l'AVL en liste par son parcours préfixe, symétrique ou postfixe selon la valeur de l'entier passé en paramètre]
	 * @param  ch [entier, définissant le parcours devant être utilisé pour représenter l'AVL]
	 *            		si ch == 1 : parcours préfixe
	 *					si ch == 2 : parcours symétrique
	 *					si ch == 3 : parcours postfixe
	 * @return    [chaîne de caractères]
	 */
	public String print(int ch)
	{
		ArrayList<String> a = new ArrayList<String>();
		switch (ch)
		{
			case (1):
				a = pref();
				break;
			case (2):
				a = symm();
				break;
			case (3):
				a = post();
				break;
			default:
				a = symm();
				break;
		}
		String str = "[";
		if (!estVide())
		{
			//for (int i = 0; i < a.size() - 2 ;i++)
			for (int i = 0; i < a.size() -1 ;i++)
			{
				//if (i % 2 == 0)
				//	str += "(" + a.get(i) + ',';
				//else
				//	str += a.get(i) + "),";
				str += a.get(i) + ",";
			}
			//str += "(" + a.get(a.size() - 2) + "," + a.get(a.size() - 1) + ")";
			str += a.get(a.size() - 1);
		}
		str += "]";
		//str += "]";
		return str;
	}

	//public String printSansBals(int ch)
	//{
	//	ArrayList<String> a = new ArrayList<String>();
	//	switch (ch)
	//	{
	//		case (1):
	//			a = pref();
	//			break;
	//		case (2):
	//			a = symm();
	//			break;
	//		case (3):
	//			a = post();
	//			break;
	//		default:
	//			a = symm();
	//			break;
	//	}
	//	String str = "[";
	//	if (!estVide())
	//	{
	//		for (int i = 0; i < a.size() - 2 ;i++)
	//		{
	//			if (i % 2 == 0)
	//				str += a.get(i) + ',';
	//		}
	//		str += a.get(a.size() - 2);
	//	}
	//	str += "]";
	//	return str;
	//}
}