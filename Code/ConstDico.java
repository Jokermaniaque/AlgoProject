
























import java.util.*;
import java.io.*;

class ConsDico
{
	public static void main(String args[])
	{
		Scanner input; // file in

		String str = "";
		String[] wordArray;

		ArrayList<String> strList = new ArrayList<String>();

		AVLString av = new AVLString();

		try
		{
			File fileIn = new File(""); // file in
			File fileOut = new File("../../7 nouvelles - pages/dico.txt"); // file out

			BufferedWriter output = new BufferedWriter(new FileWriter(fileOut)); // to write to output

			for (int i = 0; i < 43 ;i++) // for all 43 pages
			{
				fileIn = new File("../../7 nouvelles - pages/" + String.valueOf(i + 1)); // open page (i + 1)
				input = new Scanner(fileIn);
				
				// build string containing all text from the page
				while (input.hasNextLine())
					str += input.nextLine();
			}
			// all in min
			str = str.toLowerCase();
			
			// separating words
			wordArray = str.split("\\W");
			
			// adding words to an AVL
			for (int j = 0; j < wordArray.length ;j++)
				av.ajout(wordArray[j]);
			
			// getting words in lexicographic order from the AVL
			strList = av.symm();
			
			// writing words to output
			output.write(strList.get(0) + "\n");
			for (int j = 1; j < strList.size() ;j++)
				output.write(strList.get(j) + "\n");
			output.close();
		} catch ( IOException e ) { e.printStackTrace(); }
	}
}