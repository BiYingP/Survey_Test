

import java.io.IOException;
import java.util.*;

/**
 * 
 */
public class MultipleChoice extends Question {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public MultipleChoice(String questionPrompt) {
		super(questionPrompt);
	}

	public int numChoices;


	@Override
	public ResponseCorrectAnswer getResponse(Input in, Output out) throws Exception {
		int i = 0;

		while (i < 1 || i > this.choices.size()) {
			try {
				i = Integer.parseInt(in.getUserInput());
				if (i < 1 || i > this.choices.size()) {
					out.display("Please enter an integer that is in the range from 1 to: " + this.choices.size());
				}
			} catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		}

		// Store the given choice index as the response 
		ResponseCorrectAnswer r = new ResponseCorrectAnswer(Integer.toString(i));

		return r;

	}

	@Override
	protected void modifyChoices(Input in, Output out) throws Exception {
		out.display("Do you wish to modify the choices? (y/n)");

		//keep polling for response if not a y or n for yes or no
		String input = in.getUserInput();
		while (!input.equals("y") && !input.equals("n")) {
			out.display("Do you wish to modify the choices? (y/n)");
			input = in.getUserInput();
		}
		;

		if (input.equals("y")) {
			out.display("Which choice do you want to modify? (enter index)");

			int i = 0;
			while (i < 1 || i > this.choices.size()) {
				try {
					i = Integer.parseInt(in.getUserInput());
					if (i < 1 || i > this.choices.size()) {
						out.display("Please enter an integer that is in the range from 1 to: " + this.choices.size());
					}
				} catch (NumberFormatException e) {
					out.display("Invalid input, integer expected");
				}
			}

			// Replace the choice
			out.display("Enter the text of the choice:");
			this.choices.set(i - 1, in.getUserInput());
		}
	}


	@Override
	protected void displayChoices(Output out) {
		
		for (int i = 0; i < choices.size(); i++) {
			out.display((i+1) + ") " + choices.get(i));
		}
		
	}

	@Override
	public void setChoice(Input in, Output out) throws IOException {
	
		// Prompt for number of choices, must have at least 2 choices
		while (numChoices <= 1) {
			try {
				out.display("Please enter number of choices for multiple choice(at least 2):");
				numChoices = Integer.parseInt(in.getUserInput());
			}catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		}
		
		// Prompt for the text of each choice
		for (int i = 0; i < numChoices; i++) {
			out.display("Choice " + (i+1));
			
			String userChoise = in.getUserInput();
			choices.add(userChoise);
		}
	}

}
