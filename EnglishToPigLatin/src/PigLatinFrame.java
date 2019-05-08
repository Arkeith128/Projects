import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
public class PigLatinFrame extends JFrame implements ActionListener{
	
	public static final int WIDTH = 400;
	public static final int HEIGHT = 500;
	
	private JTextArea englishTextArea;
	private JTextArea pigLatinTextArea;
	
	public PigLatinFrame() {
		super("English to Pig Latin Translator");
		
		//set size, location, and layout of window
		setSize(WIDTH,HEIGHT);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel mainContent = new JPanel();
		mainContent.setLayout(new GridLayout(4,1));
		//mainContent.setPreferredSize(new Dimension(500,300));
		JLabel instructLabel = new JLabel("Type a sentence and click the button to translate it to Pig Latin.");
		mainContent.add(instructLabel);
		
		englishTextArea = new JTextArea(5,20);
		englishTextArea.setLineWrap(true);
		//will make my text area scrollable
		JScrollPane scroll = new JScrollPane (englishTextArea);
		scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
		mainContent.add(scroll);
		
		JLabel outputLabel = new JLabel("Pig Latin Translation:");
		mainContent.add(outputLabel);
		
		pigLatinTextArea = new JTextArea(5,20);
		pigLatinTextArea.setLineWrap(true);
		pigLatinTextArea.setEditable(false);
		scroll = new JScrollPane(pigLatinTextArea);
		mainContent.add(scroll);
		
		
		//create a button that,when clicked, will translate text to pig latin 
		JButton translate = new JButton("Translate");
		translate.addActionListener(this);
		
		add(mainContent);
		add(translate);
		
//		JPanel mainContent = new JPanel();
//		mainContent.setLayout(new FlowLayout());
//		JLabel instructLabel = new JLabel("Type a sentence and click the button to translate it to Pig Latin.");
//		mainContent.add(instructLabel);
//		
//		
//		//create a button that,when clicked, will translate text to pig latin 
//		JButton translateButton = new JButton("Translate");
//		translateButton.addActionListener(new Translate());
//		
//		//Add everything to window frame
//		add(mainContent,BorderLayout.CENTER);
//		add(translateButton,BorderLayout.SOUTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		
		String sentence = englishTextArea.getText();
		if(sentence.length() == 0)
			pigLatinTextArea.setText("Please type something to translate");
		else {
			int i = 0;
			int diff = 0; //this will help in telling us how far to advance the counter
			int startIndex; //beginning of the word
			int endIndex; //first space after the word
			String word; //original word before Pig Latin translation
			String pgWord; //new word after Pig Latin translation
	
			while(i<=sentence.length()) {
				
				//if the current character is a letter or number
				if (Character.isLetterOrDigit(sentence.charAt(i))) {
					startIndex = i; 
					
					endIndex = sentence.indexOf(' ', i);
					
					if(endIndex <= -1) {
						endIndex = sentence.length(); 					
					}
					
					//the substring will start where the first valid character starts and
					//ends at the specified index -1
					word = sentence.substring(startIndex,endIndex);	
					
					//convert word to Pig Latin depending on first letter
					pgWord = convert(word);
					
					//take the original word and subtract its length from the new word				
					diff = pgWord.length() - word.length();
					
					//start from beginning of sentence and stop right before the word
					//append that string to the translated word
					//append the rest of the old sentence to that word starting at the specified index
					sentence = sentence.substring(0, startIndex) + pgWord + sentence.substring(endIndex);
					
					//to keep from going one char at a time, this will jump i to the next character
					//endIndex will be where the original word ends. diff will be after the 'ay' is appended
					//i should now be sitting at a space. the +1 just moves it one extra character
					i = endIndex+diff+1; 								
				}
				else if (Character.isWhitespace(sentence.charAt(i))){
					i++; //current character is a space, try the next one
				}
				else {
					//skip translating this word because it doesn't start with a valid character
					endIndex = sentence.indexOf(' ', i);
					if(endIndex <= -1) {
						endIndex = sentence.length(); 					
					}
					i = endIndex+1;
				}
			}
			
			pigLatinTextArea.setText(sentence);
		}
	}
	
	/**
	 * test if a word starts with a vowel
	 * @param word - will test the first character of the word
	 * @return true or false
	 */
	private boolean startsWithVowel(String word) {
		char first = word.charAt(0);
		
		//return true if the first character is aeiou ignoring case
		switch(first) {
			case 'a':
			case 'A': return true;
			case 'e':
			case 'E': return true;
			case 'i':
			case 'I': return true;
			case 'o':
			case 'O': return true;
			case 'u':
			case 'U': return true;
			default: return false;
		}		
	}	
	
	/**
	 * Will test if a word starts with a number
	 * @param word - will test the first character of the word
	 * @return true or false
	 */
	private boolean startsWithNumber(String word) {
		//checks if the first character is a digit (number)
		if (Character.isDigit(word.charAt(0)))
			return true;
		else
			return false;
	}
	
	/**
	 * Will take a word and convert it to Pig Latin
	 * @param word - word to be converted
	 * @return the translated word
	 */
	private String convert(String word) {
		//if word doesn't start with a vowel
		if(!startsWithVowel(word)) {
			//if it doesn't end with punctuation, then move first letter to end
			if (!endsWithPunctuation(word)) {
				//take the word without the first character. 
				//Append the first character to the end of the word with 'ay'
				word = word.substring(1,word.length())+word.charAt(0)+"ay";
			}
			else // if word ends with punctuation
				
				//take the word between the first and last character
				//append the first character and 'ay' to the end of it
				//place the last character at the very end of the new word
				word = word.substring(1,word.length()-1)+word.charAt(0)+"ay"+word.substring(word.length()-1);
		}
		else if(startsWithVowel(word)) {
			
			if (!endsWithPunctuation(word))
				word = word+"way";
			else
				word = word.substring(0,word.length()-1)+"way"+word.substring(word.length()-1);
		}
		else if (startsWithNumber(word)) {
			if (!endsWithPunctuation(word)) {
				//will treat the number like a consonant
				word = word.substring(1,word.length())+word.charAt(0)+"ay";
			}
			else
				word = word.substring(1,word.length()-1)+word.charAt(0)+"ay"+word.substring(word.length()-1);
		}
		
		return word;
		
	}
	
	/**
	 * Will check the last character and test if it doesn't end with a letter or digit
	 * @param word
	 * @return true if the last character isn't a letter or number
	 */
	public boolean endsWithPunctuation(String word) {
		//get the last character in the word
		char lastChar = word.charAt(word.length()-1); 
		//if the last character is not a letter or digit 
		if ( !Character.isLetterOrDigit(lastChar) )
			return true;
		else
			return false;		
	}
}
