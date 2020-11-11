

import java.io.IOException;
import java.util.*;


public class Matching extends Question {

	private static final long serialVersionUID = 1L;
	private ArrayList<String>choice2;

	/**
     * Default constructor
     */
    public Matching(String questionPrompt) {
    	super(questionPrompt);
    	this.choice2 =  new ArrayList<String>();
    }

	@Override
	public ResponseCorrectAnswer getResponse(Input in, Output out) throws Exception {
		ArrayList<String> choiceIndexes = new ArrayList<String>();
		
		for (int i = 0; i < choices.size(); i++) {
			int j = 0;
			while (j < 1 || j > choices.size()) {
				out.display("Please match choice " + (i+1));
				try {
					j = Integer.parseInt(in.getUserInput());
				}catch (NumberFormatException e){
					out.display("Invalid Input. Integer expected");
				}
				if (j < 1 || j > choices.size()) {
					out.display("Please enter the integer of the matching option that is in the range from 1 to: " + choices.size());
				}
			} 
			choiceIndexes.add(Integer.toString(j));
		}

		String str = String.join(" ", choiceIndexes);
		ResponseCorrectAnswer r = new ResponseCorrectAnswer(str);
		
		return r;
	}

	@Override
	protected void modifyChoices(Input in, Output out) throws Exception {

		//out.display("Do you wish to modify the choices? (y/n)");
		String userInput = " ";
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
					out.display("Invalid userInput, integer expected");
				}
			}

			// Replace the choice
			out.display("Enter the text of the choice:");
			this.choices.set(i-1, in.getUserInput());
		}

		out.display("Do you wish to modify the matching options? (y/n)");
		userInput = in.getUserInput();
		while (!userInput.equals("y") && !userInput.equals("n")){
            out.display("Do you wish to modify the matching options? (y/n)");
			userInput = in.getUserInput();
		}

		if (userInput.equals("y")) {
			out.display("Which matching option do you want to modify? (enter index)");

			int j = 0;
			while (j < 1 || j > this.choice2.size()){
				try {
					j = Integer.parseInt(in.getUserInput());
					if (j < 1 || j > this.choice2.size()) {
						out.display("Please enter an integer that is in the range from 1 to: " + this.choice2.size());
					}
				}
				catch (NumberFormatException e) {
					out.display("Invalid userInput, integer expected");
				}
			}

			// Replace the choice
			out.display("Enter the text of the matching option:");
			this.choice2.set(j-1, in.getUserInput());
		}

	}

	@Override
	protected void displayChoices(Output out) {
		for (int i = 0; i < this.choices.size(); i++) {
			out.display((i+1) + ") " + this.choices.get(i));
		}
		
		// Display the matching options
		for (int i = 0; i < this.choice2.size(); i++) {
			out.display((i+1) + ") " + this.choice2.get(i));
		}
		
	}

	@Override
	public void setChoice(Input in, Output out) throws IOException {
	int num = 0;
		
		// Prompt for number of choices for this question, must have at least 2 choices
		while (num <= 1) {
			try {
				out.display("Please enter number of choices to be matched (at least 2):");
				num = Integer.parseInt(in.getUserInput());
			} catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		}
		
		// Prompt for the text of each choice
		for (int i = 0; i < num; i++) {
			out.display("Choice " + (i+1));
			String userInput = in.getUserInput();
			this.choices.add(userInput);
		}
		
		// prompt for matching options which may be equal or greater than in number than the choices
		out.display("Please specify number of matching options (same amount or more than choices to be matched):");
		while (num < this.choices.size()) {
			try {
				num = Integer.parseInt(in.getUserInput());
				if (num < this.choices.size()) {
					out.display("Number of matching opts must be equal or greater than that of the choices");
				}
			} catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		} 
		
		// prompt for the text of each matching option
		for (int i = 0; i < num; i++) {
			out.display("Matching Option " + (i+1));
			String userInput = in.getUserInput();
			this.choice2.add(userInput);
		}
		
	}
}