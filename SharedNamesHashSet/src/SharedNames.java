/**
 * SharedNames.java
 *
 * This program reads through 2 files of names and finds
 * the names that are common to both boys and girls.
 * The names are stored in HashSets
 * 
 * @author
 * @version
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;

class SharedNames
{
	/** Number of names in the file */
//	public static final int NUMNAMES = 1000;
	public static final String GIRLNAMESFILE = "girlnames.txt";
	public static final String BOYNAMESFILE = "boynames.txt";

	/** Main method
	 */
	public static void main(String[] args)
	{
		File girlFile = new File(GIRLNAMESFILE);
		File boyFile = new File(BOYNAMESFILE);
		
		//if Boy or Girl file can't be found, exit.
		if (!girlFile.exists() || !boyFile.exists() ) {
			System.out.println("One or both files can't be found. "
					+ "Please make sure they are in the current directory and try again");
			
			System.out.println("System exiting...");
			System.exit(0);
		}
	   
		// Names read in so far
	   HashSet<String> hashNames = new HashSet<String>();
	   
	   // Names in common between boys and girls
	 //using LinkedHashSet because it's insertion and retrieval is O(1)
	   LinkedHashSet<String> commonNames = new LinkedHashSet<String>();
	   
	   //iterate through file and bring in names
	   Scanner namesFromFile = null;
	   
	   //will count the unique boy/girl/common names
	   int boyCount = 0;
	   int girlCount = 0;	   
	   	   
	   try {
		   namesFromFile = new Scanner(girlFile); //open file for reading
		   
		   //check to see if another name is in the file
		   //if so, read it and store in a set
		   while(namesFromFile.hasNextLine()) {
			   
			   String name = namesFromFile.nextLine();
			   
			   //test to see if name is already in set. If so, add it to commonNames set 
			   if( hashNames.contains(name) ) {
				   
				   commonNames.add(name);					   
			   }
			   else { //name isn't in the hashSet, add to the hashSet
				   hashNames.add(name);				   
			   }
		   }
		   
		   //capture the count of unique girl names before the boys are added
		   girlCount = hashNames.size();
		   
	   } catch (Exception e) {
			System.out.println("Something went wrong with the Girl file. System will exit");
			System.exit(0);
	   } 
	   
	   
	   try {
		   namesFromFile = new Scanner(boyFile); //open file for reading
		   
		   //check to see if another name is in the file
		   //if so, read it and store in a set
		   while(namesFromFile.hasNextLine()) {
			   
			   String name = namesFromFile.nextLine();
			   
			   //test to see if name is already in set. If so, add it to commonNames set 
			   if( hashNames.contains(name) ) {
				   
				   //using LinkedHashSet because it's insertion and retrieval is O(1)
				   commonNames.add(name);				   
			   }
			   else { //name isn't in the hashSet, add to the hashSet
				   hashNames.add(name);
				   boyCount++;
			   }
		   }		   		  
		   
	   } catch (Exception e) {
			System.out.println("Something went wrong with the Boy file. System will exit");
			System.exit(0);
	   } 
	   
	   namesFromFile.close();
	   
	   System.out.println("Count of unique girl names: " +girlCount);
	   System.out.println("Count of unique boy names: " +boyCount);
	   System.out.println("Count of Common names: " +commonNames.size());
	   System.out.println("\nList of common names\n----------------------");
	   
	   //loop through and print the common names
	   for (String common : commonNames) {
		   System.out.println(common);
	   }

	}
} 