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

    private JButton endSentence = new JButton("Done");

    private String [] files = new String[]{"fixed.txt"};
    private WordPredictor predictor;
    private List<String> predict;

    public GUI() throws FileNotFoundException {
		predictor = new WordPredictor(files);
		createView();
		setTitle("N-Gram Word Predictor");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(1000,200); // of the window
		setLocationRelativeTo(null);
		setResizable(false);

		//predictor = new WordPredictor(files);
    }

    private void createView() {
		panel = new JPanel();
		getContentPane().add(panel);

		JLabel enterText = new JLabel("Type:");
		enterText.setPreferredSize(new Dimension(100,30));
		panel.add(enterText);

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

		predict = predictor.getPrediction("");
		for(int i = 0; i < 5; i++) {
			//JButton jButton = new JButton(" ");
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

		panel.add(endSentence);
		endSentence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = userInput.getText();
				predictor.addSentence(text);
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
