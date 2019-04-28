import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
public class Translate implements ActionListener{

	public static final int WIDTH = 300;
	public static final int HEIGHT = 200;
	public static void main(String[] args) {
		JFrame window = new JFrame();		
		window .setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton translate = new JButton("Translate");
		translate.addActionListener(new Translate());
		window.add(translate);
		window.setVisible(true);

	}
	@Override
	public void actionPerformed(ActionEvent event) {

		System.exit(0);
		
	}

}
