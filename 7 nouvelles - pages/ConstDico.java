import java.util.*;
import java.io.*;

class ConsDico
{
	public static void main(String args[])
	{
		Scanner input;
		
		//PrintWriter writer = new PrintWriter("dico.txt", "UTF-8");

		String str = "";
		String tmp = "";
		String[] wordArray;
		ArrayList<String> strList = new ArrayList<String>();

		try
		{
			File fileIn = new File("");
			File fileOut = new File("dico.txt");

			BufferedWriter output = new BufferedWriter(new FileWriter(fileOut));

			for (int i = 0; i < 43 ;i++)
			{
				fileIn = new File(String.valueOf(i + 1));
				input = new Scanner(fileIn);
				
				while (input.hasNextLine())
					str += input.nextLine();
				wordArray = str.split("\\W");
				for (int j = 0; j < wordArray.length ;j++)
					strList.add(wordArray[j]);
				strList.sort();
				//Arrays.sort(wordArray);
				output.write(strList.get(0) + "\n");
				//output.write(wordArray[0] + "\n");
				//for (int j = 1; j < wordArray.length ;j++)
				for (int j = 1; j < strList.size() ;j++)
				{
					//if (wordArray[j] != 
					output.write(strList.get(j) + "\n");
				}
			}
			output.close();
		} catch ( IOException e ) { e.printStackTrace(); }
	}
}