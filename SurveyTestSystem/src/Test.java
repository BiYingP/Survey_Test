

import java.io.*;
import java.util.*;

public class Test extends Survey implements Serializable {
	private static final long serialVersionUID = 1L;
	protected ArrayList<ArrayList<ResponseCorrectAnswer> >correctResponse;

    public Test(Input in, Output out) {
    	super(in, out);
    	this.correctResponse = new ArrayList<ArrayList<ResponseCorrectAnswer>>();
    }

	public void create(Input in, Output out) throws Exception {

		int numQuestions = 0;

		out.display("Please specify the name of the test:");
		String name = "";
		while (name.length() == 0){
			name = in.getUserInput();
			if (name.length() == 0) {
				out.display("The name cannot be zero-length");
			}
		}

		setName(name);

		// Set the number of questions for this survey
		out.display("Please enter a number of questions:");
		while (numQuestions < 1){
			try {
				numQuestions = Integer.parseInt(in.getUserInput());
			}
			catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		}

		for (int i = 0; i < numQuestions; i++) {
			// Make the question and its prompt
			Question q = question(in, out);
			addQuestion(q);

			// Make correct answers for question
			ArrayList<ResponseCorrectAnswer> correctResponses = makeResponseList(q, in, out);

			addAnswer(correctResponses);
		}
		display(out);
	}

    public void display(Output out) {
		out.display("Test name: " + name);
		out.display("Number of questions: " + this.questions.size());

		for (int i = 0; i < this.questions.size(); i++) {
			out.display("Question " + (i+1) + ": ");
			this.questions.get(i).display(out);

			// display all answers
			ArrayList<ResponseCorrectAnswer> questionAnswers = this.correctResponse.get(i);

			for (int j = 0; j < questionAnswers.size(); j++) {
				out.display("Correct answer " + (j+1) + ": ");
				questionAnswers.get(j).display(out);
			}
		}
    }

    public ArrayList<ResponseCorrectAnswer> makeResponseList(Question q, Input in, Output out) throws Exception {

		int numAnswers = 0;

		out.display("Please specify the number of correct responses for this question, which must be greater than 0:");
		while (numAnswers < 1) {
			try {
				numAnswers = Integer.parseInt(in.getUserInput());
			}
			catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		}

		ArrayList<ResponseCorrectAnswer> correctResponses = new ArrayList<ResponseCorrectAnswer>();

		for (int i = 0; i < numAnswers; i++) {
			out.display("Setting correct response number " + (i+1) + " of " + numAnswers );
			correctResponses.add(q.getResponse(in, out));
		}

		return correctResponses;
	}

    public void addAnswer(ArrayList<ResponseCorrectAnswer> a) { this.correctResponse.add(a); }

	public void grade (Input in, Output out) {

		// essay not count for grade
		int numEssays = 0;
		for (Question q : this.questions) {
			if (q instanceof Essay) {
				numEssays++;
			}
		}
		// start grading each user
		for (int i = 0; i < this.responses.size(); i++) {
			int numCorrect = 0;

			out.display("User " + (i + 1) + "'s grade:");

			// set correct responses to each question
			for (int j = 0; j < this.correctResponse.size(); j++) {
				ResponseCorrectAnswer userResponse = this.responses.get(i).get(j);

				// compare to at least one of the correct responses
				for (int k = 0; k < this.correctResponse.get(j).size(); k++) {
					ResponseCorrectAnswer correctResponse = this.correctResponse.get(j).get(k);
					if (userResponse.compare(correctResponse)) {
						numCorrect++;
						break;
					} else if (!(this.questions.get(j) instanceof Essay)) {
						out.display("Question " + (j + 1) + " was incorrect");
					}
				}
			}

			double grade = ((double) numCorrect / (this.questions.size() - numEssays)) * 100D;
			out.display(numCorrect + " out of " + (this.questions.size() - numEssays) + " questions correct.");
			out.display("Overall Grade: " + grade + "%");
			out.display("Number of ungraded essay questions: " + numEssays);
		}
	}

	@Override
	public void modify(Input in, Output out) throws Exception {
		//this.display(out);

		String userInput = "";
		int i = 0;
		while ((i < 1 || i > this.questions.size()) && !userInput.equals("n")){
			out.display("Which question do you want to modify? (enter index or quit by entering 'n')");
			try {
				userInput = in.getUserInput();

				if (!userInput.equals("n")) {
					i = Integer.parseInt(userInput);
					if (i < 1 || i > this.questions.size()) {
						out.display("Please enter an integer that is in the range from 1 to " + this.questions.size());
					}
					else {
						//modify the question
						this.questions.get(i-1).modify(in, out);

						// modify the question's answer(s)
						String ansIn = "";
						//out.display("Do you wish to modify Question " + i + "'s answer(s)? (y/n)");
						while (!ansIn.equals("y") && !ansIn.equals("n")){
                            out.display("Do you wish to modify Question " + i + "'s answer(s)? (y/n)");
							ansIn = in.getUserInput();
						}

						if (ansIn.equals("y")) {
							Question q = this.questions.get(i-1);

							// Make the set of answers for this question, minimum size being 1
							int numAnswers = 0;
							out.display("Please specify the number of correct responses for this question, which must be greater than 0:");
							while (numAnswers < 1){
								try {
									numAnswers = Integer.parseInt(in.getUserInput());
								}
								catch (NumberFormatException e) {
									out.display("Invalid input, integer expected");
								}
							}

							ArrayList<ResponseCorrectAnswer> questionAnswers = new ArrayList<ResponseCorrectAnswer>();
							for (int j = 0; j < numAnswers; j++) {
								out.display("Setting correct response number " + (j+1) + " of " + numAnswers );
								questionAnswers.add(q.getResponse(in, out));
							}
							this.display(out);

							// add the set of answers for this question
							this.correctResponse.set(i-1, questionAnswers);
						}

						i = 0;
					}
				}
			}
			catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		}
	}
}