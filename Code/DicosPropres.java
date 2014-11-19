import java.util.*;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class DicosPropres
{
	// --------------------------------------------------------------Attributs--------------------------------------------------------------

	private Chapitres chaps_;
	AVLString dico_;
	private ArrayList<AVLString> dicProps_;






	// ------------------------------------------------------------Constructeurs------------------------------------------------------------

	public DicosPropres()
	{
		chaps_ = new Chapitres();
		dicProps_ = new ArrayList<AVLString>();
		dico_ = new AVLString();
	}

	public DicosPropres(Chapitres chaps)
	{
		chaps_ = chaps;
		dicProps_ = new ArrayList<AVLString>();

		// Initialisation d'un dictionnaire par chapitre
		for (int i = 0; i < chaps_.nbChaps() ;i++)
			dicProps_.add(new AVLString());
	}






	// --------------------------------------------------------------Getters--------------------------------------------------------------
	
	public Chapitres getChaps()	{ return chaps_; }

	public ArrayList<AVLString> getDicos()	{ return dicProps_; }






	// --------------------------------------------------------------Setters--------------------------------------------------------------

	public void setChaps(Chapitres chaps)	{ chaps_ = chaps; }

	public void setDicos(ArrayList<AVLString> dicos)	{ dicProps_ = dicos; }






	// --------------------------------------------------------------Methodes--------------------------------------------------------------
	
	public void constDicos()
	{
		int numChap = 0;
		for (int i = 0; i < dicProps_.size() ;i++)
		{
			for (int j = 0; j < chaps_.getPages().size() ;j++)
			{
				numChap = 0;
			}
		}
	}
}