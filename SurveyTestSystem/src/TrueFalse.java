import java.io.IOException;

public class TrueFalse extends Question {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TrueFalse(String questionPrompt) {
		super(questionPrompt);
		this.choices.add("True");
		this.choices.add("False");
	}

	@Override
	public ResponseCorrectAnswer getResponse(Input in, Output out) throws Exception {
		String userInput = "";

		while (!userInput.equals("t") && !userInput.equals("f")){
			userInput = in.getUserInput();
			if (!userInput.equals("t") && !userInput.equals("f")) {
				out.display("Please enter t or f for true and false, respectively");
			}
		}
		ResponseCorrectAnswer r = new ResponseCorrectAnswer(userInput);
		return r;
	}

	@Override
	protected void modifyChoices(Input in, Output out) throws Exception {

	}


	@Override
	protected void displayChoices(Output out) {
		for (String choice: getChoice()) {
			out.display(choice );
		}

	}

	@Override
	public void setChoice(Input in, Output out) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
