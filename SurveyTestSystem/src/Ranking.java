import java.io.IOException;
import java.util.*;

public class Ranking extends Question {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Ranking(String questionPrompt) {
		super(questionPrompt);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResponseCorrectAnswer getResponse(Input in, Output out) throws Exception {
		ArrayList<String> numChoices = new ArrayList<String>();
		
		// For each choice, set its numeric ranking
		for (int i = 0; i < this.choices.size(); i++) {
			int j = 0;
			while (j < 1 || j > this.choices.size()) {
				try {
					out.display("Please rank choice " + (i+1));
					j = Integer.parseInt(in.getUserInput());
					if (j < 1 || j > this.choices.size()) {
						out.display("Please enter an integer rank that is in the range from 1 to: " + this.choices.size());
					}
				}
				catch (NumberFormatException e) {
					out.display("Invalid input, integer expected");
				}
			} ;
			numChoices.add(Integer.toString(j));
		}
		
		// Combine rankings into a single Response string for easier comparison of answers
		String str = String.join(" ", numChoices);
		ResponseCorrectAnswer r = new ResponseCorrectAnswer(str);
		
		return r;
	}

	@Override
	protected void modifyChoices(Input in, Output out) throws Exception {
		out.display("Do you wish to modify the choices? (y/n)");

		String userInput = in.getUserInput();
		while (!userInput.equals("y") && !userInput.equals("n")){
		    out.display("Do you wish to modify the choices? (y/n)");
			userInput = in.getUserInput();

		}

		if (userInput.equals("y")) {
			out.display("Which choice do you want to modify? (enter index)");

			int i = 0;
			while (i < 1 || i > this.choices.size()){
				try {
					i = Integer.parseInt(in.getUserInput());
					if (i < 1 || i > this.choices.size()) {
						out.display("Please enter an integer that is in the range from 1 to: " + this.choices.size());
					}
				}
				catch (NumberFormatException e) {
					out.display("Invalid input, integer expected");
				}
			} 

			// Replace the choice
			out.display("Enter the text of the choice:");
			this.choices.set(i-1, in.getUserInput());
		}

	}

	@Override
	protected void displayChoices(Output out) {
		for (int i = 0; i < this.choices.size(); i++) {
			out.display((i+1) + ") " + this.choices.get(i));
		}
	}

	@Override
	public void setChoice(Input in, Output out) throws IOException {
		int numChoices = 0;
		
		// Prompt for number of choices, must have at least 2 choices
		out.display("Please enter number of choices (at least 2):");
		 while (numChoices <= 1) {
			try {
				numChoices = Integer.parseInt(in.getUserInput());
			} catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		}
		
		// Prompt for the text of each choice
		for (int i = 0; i < numChoices; i++) {
			out.display("Choice " + (i+1));
			String userInput = in.getUserInput();
			this.choices.add(userInput);
		}
	}

}
