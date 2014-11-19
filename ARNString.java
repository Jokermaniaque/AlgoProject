import java.util.*;

class ARNString
{
	private String rac_;
	private ARNString filsG_;
	private ARNString filsD_;
	private int col_; // Noir => 0 ; Rouge => 1

	public ARNString()
	{
		rac_ = '@';
		filsG_ = null;
		filsD_ = null;
		col_ = 0;
	}

	public ARNString(String rac)
	{
		rac_ = rac;
		filsG_ = new ARNString();
		filsD_ = new ARNString();
		col_ = 0;
	}

	public ARNString(String rac, ARNString filsG, ARNString filsD_, int col)
	{
		rac_ = rac;
		filsG_ = filsG;
		filsD_ = filsD;
		col_ = col;
	}

	public String elt()	{ return rac_; }

	public ARNString sag()	{ return filsG_; }

	public ARNString sad()	{ return filsD_; }

	public int getCol()	{ return col_; }

	public void setRac(String rac)	{ rac_ = rac; }
	public void setRac(ARNString fils)	{ filsG_ = filsG; }
	public void setRac(ARNString fils)	{ filsD_ = filsD; }
	public void setRac(int col)	{ col_ = col; }



	public void arnVide()
	{
		rac_ = "@";
		filsG_ = null;
		filsD_ = null;
		col = 0;
	}

	public boolean estNIL()
	{
		return ((rac_ == "@") && (filsG_ == null) && (filsD_ == null));
	}

	public boolean estFeuille()
	{
		if (!estNIL())
			return ((filsG_.estNIL()) && (filsD_.estNIL()));
		else
			return false;
	}

	public int hauteur()
	{
		if ((estNIL()) || (estFeuille())
			return 0;
		else
			return Math.max(filsG_.hauteur(), filsD_.hauteur()) + 1;
	}

	public int hauteurNoire()
	{
		if ((estNIL()) || (estFeuille())
			return 0;
		else
		{
			if (filsG_.col_ == 0)
				return Math.max(filsG_.hauteurNoire(), filsD_.hauteurNoire()) + 1;
			else
				return Math.max(filsG_.hauteurNoire(), filsD_.hauteurNoire());
		}
	}

	public boolean element(String elt)
	{
		if (estNIL())
			return false;
		else if (rac_.compareTo(elt) == 0)
			return true;
		else if (elt.compareTo(rac_) < 0)
			return filsG_.element(elt);
		else
			return filsD_.element(elt);
	}

	public void ajout(String elt)
	{
		if ()
	}
}