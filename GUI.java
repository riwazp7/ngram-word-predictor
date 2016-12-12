import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private JTextField userInput;
    //private JLabel enterText;

    private JButton word1;
    private JButton word2;
    private JButton word3;
    private JButton word4;
    private JButton word5;

    public GUI() {
	createView();
	setTitle("N-Gram Word Predictor");
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setSize(1000,200); // of the window
	setLocationRelativeTo(null);
	setResizable(false);
	//pack();
    }

    private void createView() {
	JPanel panel = new JPanel();
	getContentPane().add(panel);

	JLabel enterText = new JLabel("Please enter input: ");
	panel.add(enterText);

	enterText = new JLabel();
	enterText.setPreferredSize(new Dimension(100,30));
	panel.add(enterText);

	userInput = new JTextField();
	userInput.setPreferredSize(new Dimension(750,30));
	panel.add(userInput);

	word1 = new JButton("click");
	// Peform this action every time the button is clicked
	word1.addActionListener(new buttonLabelActionListener());
	panel.add(word1);

	word2 = new JButton("click");
	// Peform this action every time the button is clicked
	word2.addActionListener(new buttonLabelActionListener());
	panel.add(word2);

	word3 = new JButton("click");
	// Peform this action every time the button is clicked
	word3.addActionListener(new buttonLabelActionListener());
	panel.add(word3);

	word4 = new JButton("click");
	// Peform this action every time the button is clicked
	word4.addActionListener(new buttonLabelActionListener());
	panel.add(word4);

	word5 = new JButton("click");
	// Peform this action every time the button is clicked
	word5.addActionListener(new buttonLabelActionListener());
	panel.add(word5);

	
    }
    
    public static void main(String [] args) {
	SwingUtilities.invokeLater(new Runnable() {
		@Override
		    public void run(){
		    new FinalGUI().setVisible(true);
		}
	    });
    }

    private class buttonLabelActionListener implements ActionListener {
	// This method is called every time the button is clicked
	@Override
	    public void actionPerformed(ActionEvent e) {
	    // add the text to the JTextField
	    // potentially change the text of the label to another predicted word
	    ((JButton) e.getSource()).setText("go");
	    String curInput = userInput.getText();
	    userInput.setText(curInput + " yes");
	}
    }
    
}
