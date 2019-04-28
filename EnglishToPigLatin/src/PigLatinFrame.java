import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
public class PigLatinFrame extends JFrame implements ActionListener{
	
	public static final int WIDTH = 300;
	public static final int HEIGHT = 200;
	
	private JTextArea englishTextArea;
	private JTextArea pigLatinTextArea;
	
	public PigLatinFrame() {
		super("English to Pig Latin Translator");
		
		//set size, location, and layout of window
		setSize(WIDTH,HEIGHT);
		setLocationRelativeTo(null);
		setLayout(new FlowLayout());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel instructLabel = new JLabel("Type a sentence and click the button to translate it to Pig Latin.");
		add(instructLabel);
		
		englishTextArea = new JTextArea(5,20);
		englishTextArea.setLineWrap(true);
		add(englishTextArea);
		
		JLabel outputLabel = new JLabel("Pig Latin Translation:");
		add(outputLabel);
		
		pigLatinTextArea = new JTextArea();
		pigLatinTextArea.setLineWrap(true);
		pigLatinTextArea.setEditable(false);
		add(pigLatinTextArea);
		
		
		//create a button that,when clicked, will translate text to pig latin 
		JButton translate = new JButton("Translate");
		translate.addActionListener(this);
		
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
		pigLatinTextArea.setText(englishTextArea.getText());
		
	}
}
