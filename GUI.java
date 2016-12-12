import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class GUI extends JFrame {

	// where the user will type their input
    private JTextField userInput;

    // visual display for user
    private JPanel panel;

    public static final long serialVersionUID = 1L;

    // used to suggest words to the user
	List<JButton> buttons = new ArrayList<>();

	// user should push this button when they have completed a sentence
    private JButton endSentence = new JButton("Done");

	// corpus we will use to predict words
    private String [] files = Params.TRAINING_FILES;

    private WordPredictor predictor;

    // store predicted words to suggest
    private List<String> predict;

    public GUI() throws FileNotFoundException {
		predictor = new WordPredictor(files);
		createView();
		setTitle("N-Gram Word Predictor");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1000,200); // of the window
		setLocationRelativeTo(null);
		setResizable(false);
    }

	/**
	 * This method does all the updating and visual work for the user.
	 **/
	private void createView() {
		panel = new JPanel();
		getContentPane().add(panel);

		JLabel enterText = new JLabel("Type:");
		enterText.setPreferredSize(new Dimension(100,30));
		panel.add(enterText);

		/**
		 * To update predicted words in real-time, we must collect the words
		 * that are currently entered, and use the text to get predictions
		 * using NGrams. After we have retrieved the words with the highest
		 * probability, we update the JButtons so the user has options of words
		 * to choose from.
		**/
		userInput = new JTextField();
		userInput.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) { buttons(); }

			@Override
			public void removeUpdate(DocumentEvent e) { buttons(); }

			@Override
			public void changedUpdate(DocumentEvent e) { buttons(); }

			public void buttons(){
				String curInput = userInput.getText();
				predict = predictor.getPrediction(curInput);
				int j = 0;
				while(j < predict.size()) {
					JButton jButton = buttons.get(j);
					jButton.setText(predict.get(j++));
				}
			}
		});

		userInput.setPreferredSize(new Dimension(850,30));
		panel.add(userInput);

		/**
		 * Here is where we change the text of a button to be predicted words
		 * ranked by NGram probability.
		**/
		predict = predictor.getPrediction("");
		for(int i = 0; i < 5; i++) {
			JButton jButton;
			if(predict.size() > i ) {
				jButton = new JButton(predict.get(i));
			} else {
				jButton = new JButton("");
			}
			jButton.addActionListener(new buttonLabelActionListener());
			buttons.add(jButton);
			panel.add(jButton);
		}

		/**
		 * Once the user has decided they've written a sentence, we clear the
		 * text field so they can start over and have new NGram trees.
		 **/
		panel.add(endSentence);
		endSentence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = userInput.getText();
				predictor.addString(text);
				userInput.setText("");
			}
		});
	}

    public static void main(String [] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
		    public void run(){
				try {
					new GUI().setVisible(true);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
	    });
    }

	/**
	 * This class is called when a button is clicked.
	 * Once a button is pressed, we retrieve all the text currently inputted
	 * by the user and store it in a string, add the word to the string, and then
	 * add the text back into the text field.
	 **/
	private class buttonLabelActionListener implements ActionListener {
		// This method is called every time a word button is clicked
		@Override
	    public void actionPerformed(ActionEvent e) {
	    	String curInput = userInput.getText();
	    	if(userInput.getText().endsWith(" ")){
				userInput.setText(curInput + ((JButton) e.getSource()).getText());
			} else {
				userInput.setText(curInput + " " + ((JButton) e.getSource()).getText());
			}
		}
    }
}
