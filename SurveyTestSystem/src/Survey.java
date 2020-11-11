import java.io.*;
import java.util.*;

public class Survey implements Serializable {

	private static final long serialVersionUID = 1L;

	protected ArrayList<Question> questions;
	protected ArrayList<ArrayList<ResponseCorrectAnswer>> responses;
	protected Input in;
	protected Output out;
	protected String name;

	public Survey(Input in, Output out) {

		this.questions = new ArrayList<Question>();
		this.responses = new ArrayList<ArrayList<ResponseCorrectAnswer>>();

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addQuestion(Question q) {
		this.questions.add(q);
	}

	public void create(Input in, Output out) throws NumberFormatException, Exception {

		int numQuestions = 0;
		out.display("Please enter the name of survey");
		String name = "";
		while (name.length() == 0) {
			name = in.getUserInput();
			if (name.length() == 0) {
				out.display("The name cannot be zero-length.Please enter a name");
			}
		}

		this.setName(name);
		out.display("Please enter number of questions");

		while (numQuestions < 1) {
			try {
				numQuestions = Integer.parseInt(in.getUserInput());
			} catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}

		}
		for (int i = 0; i < numQuestions; i++) {
			this.addQuestion(question(in, out));
		}
		this.display(out);
	}

	public Question question(Input in, Output out) throws NumberFormatException, IOException {

		out.display("Please select a question by entering a number");
		out.display("1) True/False");
		out.display("2) Multiple Choice");
		out.display("3) Short Answer");
		out.display("4) Essay");
		out.display("5) Ranking");
		out.display("6) Matching");

		int num = 0;
		while (num < 1 || num > 6) {
			try {

				num = Integer.parseInt(in.getUserInput());
				if (num < 1 || num > 6) {
					out.display("Input out of range. Please enter 1 to 6");
				}
			} catch (NumberFormatException e) {
				out.display("Invaid input, integer expected");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		out.display("Please enter a question prompt");

		String questionPrompt = in.getUserInput();

		Question q = null;
		switch (num) {
			case 1:
				q = new TrueFalse(questionPrompt);
				break;
			case 2:
				q = new MultipleChoice(questionPrompt);
				break;
			case 3:
				q = new ShortAnswers(questionPrompt);
				break;
			case 4:
				q = new Essay(questionPrompt);
				break;
			case 5:
				q = new Ranking(questionPrompt);
				break;
			case 6:
				q = new Matching(questionPrompt);
				break;
		}
		q.setChoice(in, out);
		return q;
	}

	public void exit() {
		out.display("Quit");
	}

	public void display(Output out) {
		out.display("Survey name: " + name);
		out.display("Number of questions: " + questions.size());

		for (int i = 0; i < questions.size(); i++) {
			out.display("Question " + (i + 1) + " : ");
			questions.get(i).display(out);
		}
	}

	public void modify(Input in, Output out) throws Exception {

		String userInput = "";
		int i = 0;
		while (!userInput.equals("n")) {
			out.display("Which question do you want to modify?((enter index or quit by entering 'n')");
			//out.display("Enter existing question:( or quit by entering 'n')");
			try {
				userInput = in.getUserInput();

				if (!userInput.equals("n")) {
					i = Integer.parseInt(userInput);
					if (i < 1 || i > this.questions.size()) {
						out.display("Please enter an integer that is in the range from 1 to " + this.questions.size());
					} else {
						// modify the question
						this.questions.get(i - 1).modify(in, out);
						i = 0;
					}
				}
			} catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			}
		}
	}

	// Allow for user to give their responses to questions
	public void take(Input in, Output out) throws Exception {

		ArrayList<ResponseCorrectAnswer> userResponses = new ArrayList<ResponseCorrectAnswer>();
		for (int i = 0; i < this.questions.size(); i++) {
			out.display("Question " + (i + 1) + " of " + this.questions.size());
			Question q = this.questions.get(i);
			q.display(out);
			ResponseCorrectAnswer r = q.getResponse(in, out);
			userResponses.add(r);
		}
		// Add responses to the list
		this.responses.add(userResponses);
	}

	public void tabulate(Input in, Output out) {

		for (int i = 0; i < this.questions.size(); i++) {
			out.display("Question " + (i + 1) + ":");
			this.questions.get(i).display(out);

			out.display("Replies:");
			ArrayList<ResponseCorrectAnswer> nResponses = new ArrayList<>();
			for (int j = 0; j < this.responses.size(); j++) {
				this.responses.get(j).get(i).display(out);
				nResponses.add(this.responses.get(j).get(i));

			}
			out.display("Tabulation:");
			Set<ResponseCorrectAnswer> unique = new HashSet<>(nResponses);

			for (ResponseCorrectAnswer r : unique) {
				out.display(r.toString()+": "+ Collections.frequency(nResponses, r));
			}
		}
	}
}
