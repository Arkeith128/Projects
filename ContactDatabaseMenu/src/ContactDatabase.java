/**
 * ContactDatabase.java
 *
 * This class implements a database of contacts that can be
 * added to, searched, displayed, or items removed.  An ArrayList
 * is used to store the database. An inner class of Contact is used
 *
 * When run, this will display a menu on the console window 
 * @author
 * @version
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The ContactDatabase class stores each contact in an arraylist.
 * Methods exist to add new contacts, search contacts, delete, and print contacts
 * to the console.
 */
public class ContactDatabase
{
	private ArrayList<Contact> contacts;		// ArrayList of contact
	private static final int QUIT = 0;			// Menu choices
	private static final int ADD = 1;
	private static final int LISTALL = 2;
	private static final int SEARCH = 3;
	private static final int DELETE = 4;

	/**
	 * Default constructor - assign the class variable, contacts to a new ArrayList with the
	 *   type parameter of Contact
	 */
	ContactDatabase()
	{
		contacts = new ArrayList<Contact>();
	}

	/**
	 * inputContact - Prompt the user for information to fully populate a Contact.
	 * Validate the user's entries and store the new Contact in the contacts ArrayList.
	 */
	public void inputContact()
	{					
		Scanner input = new Scanner(System.in);
		
		System.out.println("\nPlease enter the first name");
		String first = input.nextLine();
		
		while(first==null || first.trim().length() ==0) {
			System.out.println("Invalid entry. Please enter a first name with more characters");
			first = input.nextLine();
		}
		
		System.out.println("\nPlease enter the last name");
		String last = input.nextLine();
		
		while(last==null || last.trim().length() ==0) {
			System.out.println("Invalid entry. Please enter a last name with more characters");
			last = input.nextLine();
		}
		
		System.out.println("\nPlease enter a valid phone number. (ex. 5555555555 or 555-555-5555");
		String phone = input.nextLine();
		
		while(phone==null || phone.trim().length() == 0 || testPhone(phone) == false) {
			System.out.println("Invalid entry. Please enter a valid phone number. (ex. 5555555555 or 555-555-5555");
			phone = input.nextLine();
		}
		
		System.out.println("\nPlease enter a valid email address");
		String email = input.nextLine();
		
		while(email==null || email.trim().length() < 5  || testEmail(email) == false) {
			System.out.println("Invalid entry. Most emails contain more than 5 characters and have an @ symbol. "
					+ "\nPlease enter a valid email address that contains the @ symbol");
			email = input.nextLine();
		}
		
		
		// made it through the tests successfully. 
		// create person object and then add it to the list
		Contact person = new Contact(first,last,phone,email);
		
		System.out.println("\nAdding contact\n");
		contacts.add(person);				
		System.out.println("Contact added\n");
		
		//closing the scanner here will cause the main class to crash
		//input.close();
	}
	
	/**
	 * Method to test user input of a phone number
	 * @param number - phone number entered by user
	 * @return - true if number is a valid phone number
	 */
	private boolean testPhone(String number) {				
		//10 digits 1234567890
		//12 characters 123-456-7890
		
		// number is outside of the accepted range
		if (number.length() != 10 && number.length() != 12)
			return false;		
			
		
		//if number is 10 characters long and all digits, then return true
		if (number.length() == 10) {
			try {
				//if this passes, then the string entered contained all digits
				Long.parseLong(number);
			}
			catch(NumberFormatException nfe) {
				return false; //user didn't enter a valid number 
			}
			
			return true; // user phone number was a valid 10 digit number
		}
		else if (number.length() == 12) { //if longer than 10 digits, the dashes (-) should be present
			// if dashes aren't in their correct spots then the number is invalid
			if(number.charAt(3) != '-' && number.charAt(7) != '-')
				return false;
			
			//will parse this to a long
			String newNumber = "";
			
			//loop through number string and combine the digits without the dashes
			for (int i = 0; i<number.length(); i++) {
				if(i == 3 || i == 7)
					continue; //dont add dashes to new string
				
				newNumber = newNumber + number.charAt(i);
			}
			
			try {
				//if this passes, then the string entered contained all digits (excluding the 2 dashes removed)
				Long.parseLong(newNumber);
			}
			catch(NumberFormatException nfe) {
				//even though the user entered 2 dashes in the correct spot, the other characters weren't numbers
				return false;
			}
			// try passed. user entered 10 numbers and 2 dashes
			return true;
		}
		
		return true;
	}
	
	/**
	 * Method to test if the user entered a valid email address
	 * @param email
	 * @return true or false
	 */
	private boolean testEmail(String email) {		
		//if the string contains the @ symbol
		if(email.contains("@"))
			return true;
		else 
			return false;		
	}

	/**
	 * displayAll iterates through the ArrayList of contacts and outputs each one
	 * to the screen.
	 */
	public void displayAll()
	{
		//if arraylist doesn't contain any records, return to main menu
		if(contacts.size() == 0) {
			System.out.println("Database is empty");
			return;
		}
		System.out.println(); //adding extra space
		for (Contact contact : contacts) {
			System.out.println(contact.toString());
		}
	}
	
	/**
	 * findMatch prompt the user for a keyword to search for in ANY attribute of the Contact.
	 * Iterate through the ArrayList of contacts and add each Contact to a new ArrayList of Contacts.
	 */
	public List<Contact> findMatch()
	{
		//will add found results to this new list
		List<Contact> match = new ArrayList<Contact>();
		Scanner keyword = new Scanner(System.in);
		
		System.out.println("Enter a keyword for your search");
		String search = keyword.nextLine().toLowerCase(); //change everything to lower case to make search easier
		
		//will iterate through the arraylist and compare the attributes to the search string entered by the user
		for (Contact contact : contacts) {
			
			//returns true if first,last,phone, or email contains the keyword given by user
			if(contact.getFirst().toLowerCase().contains(search) 
					|| contact.getLast().toLowerCase().contains(search)
					|| contact.getPhone().toLowerCase().contains(search)
					|| contact.getEmail().toLowerCase().contains(search)) 
				match.add(contact);			
			else {
				continue; //none of the above were true, so go to the next iteration
			}
		}
		
		return match;
	}

	/** Call the method findMatch() and print the details of each matching contact
	 */
	public void displayMatch() {
		
		//store the returned list in another list 
		List<Contact> found = findMatch();
		
		// if search returned no results, let the user know and then return to main
		if(found.size() == 0) {
			System.out.println("No records found");
			return;
		}
		
		//iterate through the returned list
		//print out the returned results if they exist
		for (Contact contact : found) {
			System.out.println(contact.toString());
		}
		
	}
	
	/**
	 * Similar to displayMatch(), calls the method findMatch which will return a list contacts 
	 * that has any attribute that matches the user's search word. For each Contact found, prompt 
	 *  the user for whether they want to delete the Contact.  If so, remove it from the contacts
	 *  List.
	 */
	public void deleteMatch()
	{
		//store the returned list in another list 
		List<Contact> found = findMatch();
		
		// if search returned no results, let the user know and then return to main
		if(found.size() == 0) {
			System.out.println("No records found");
			return;
		}
		
		System.out.println("Records to delete");
		System.out.println("----------------");
		
		int index = 0;
		//iterate through the returned list
		//print out the returned results if they exist
		for (Contact contact : found) {
			System.out.println(index+")" +contact.toString());
			index++;
		}
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Do you want to delete them all (Y/N)?");
		String answer = input.nextLine();
		
		//if user agrees to deleting all found records
		if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes")) {
			for (Contact contact : found) {
				contacts.remove(contact);
			}
			
			System.out.println("Records Deleted");
			return; // found records were deleted, no need to continue this method
		}
		
		System.out.println("Type the number next to the record you want to delete");
		//passing in the user input along with the found list
		int validate = validateInt(input.nextLine(),found); //will return the index that the user selected
		
		Contact record = found.get(validate); //grab the records based on the index
		
		contacts.remove(record); //remove the contact from the main arraylist
		
		System.out.println("Record Deleted");		
	}
	
	
	/**
	 * will take a string, convert it to a number and return it
	 */
	private int validateInt(String string, List<Contact> found){
		//make user input valid number
		Scanner input = new Scanner(System.in);		
		boolean validate = false;
		int index = -1;
		
		//loop until validate = true
		//validate will = true when user inputs a number that matches an index
		do {
			try {
				index = Integer.parseInt(string);
				found.get(index); //will return ooo if user types a number not in the array
				validate = true;
				return index;
			}
			catch (NumberFormatException nfe) {
				System.out.println("Invalid Entry. "
						+ "Please Type the number next to the record you want to delete");
			}
			catch (IndexOutOfBoundsException ioobe) {
				System.out.println("Invalid Entry. "
						+ "Please be sure to type the correct number that you see next to the record you want to delete");
			}
			catch (Exception e) {
				System.out.println("Something went wrong, please try again.");
			}
			
			string = input.nextLine();
		} while (validate == false);
		
		return index;
	}

	// Main class
	public static void main(String[] args)
	{				
		ContactDatabase cdb = new ContactDatabase();
		Scanner scan = new Scanner(System.in);
		int choice = ADD;

		// Main menu
		while (choice != QUIT)
		{
			System.out.println();
			System.out.println("Choose from the following:");
			System.out.println("0) Quit");
			System.out.println("1) Add new contact");
			System.out.println("2) List all contacts");
			System.out.println("3) Search contacts by keyword and display");
			System.out.println("4) Search contacts by keyword and remove");
			choice = scan.nextInt();
			switch (choice)
			{
			case ADD: 	cdb.inputContact();
			break;
			case LISTALL: cdb.displayAll();
			break;
			case SEARCH: cdb.displayMatch();
			break;
			case DELETE: cdb.deleteMatch();
			break;
			}
		}
	}

	/**
	 * The inner class, Contact, stores the details for a single contact.  
	 */
	class Contact
	{
		private String first, last, phone, email;

		/**
		 * Constructors.
		 */
		public Contact()
		{
		}

		public Contact(String first, String last, String phone, String email)
		{
			this.first = first;
			this.last = last;
			this.phone = phone;
			this.email = email;
		}

		/*
		 * Accessor Methods
		 */

		public String getFirst()
		{
			return first;
		}

		public String getLast()
		{
			return last;
		}

		public String getPhone()
		{
			return phone;
		}

		public String getEmail()
		{
			return email;
		}

		/* 
		 * Mutator Methods
		 */
		public void setFirst(String first)
		{
			this.first = first;
		}

		public void setLast(String last)
		{
			this.last = last;
		}

		public void setPhone(String phone)
		{
			this.phone = phone;
		}

		public void setEmail(String em)
		{
			this.email = em;
		}



		/*
		 * Return all fields concatenated into a string
		 */
		public String toString()
		{
			return last + ", " + first + ". " + phone + ", " + email;
		}


		public boolean equals(Object otherObject)
		{
			if (otherObject ==null)
			{
				return false;
			}
			else if (getClass() != otherObject.getClass())
			{
				return false;
			}
			else
			{
				Contact otherContact = (Contact)otherObject;
				return (first.equals(otherContact.first) && 
						last.equals(otherContact.last)&&
						phone.equals(otherContact.phone)&&
						email.equals(otherContact.email));
			}
		}

	} // end inner class, Contact
} // end class, ContactDatabase