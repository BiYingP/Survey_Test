import java.io.IOException;

//import java.util.*;

public class ShortAnswers extends Question {

	private static final long serialVersionUID = 1L;


	public ShortAnswers(String questionPrompt) {
    	super(questionPrompt);
    }

    public String length;

	@Override
	public ResponseCorrectAnswer getResponse(Input in, Output out) throws Exception {
		
		length = in.getUserInput();
		while (length.length() < 0 || length.length() >100 ) {
			out.display("A short answer cannot be empty or have more than 100 characters");
			
		}
		ResponseCorrectAnswer response = new ResponseCorrectAnswer(length);
		return response;
	}

	@Override
	protected void modifyChoices(Input in, Output out) throws Exception { }

	@Override
	protected void displayChoices(Output out) {

	}

	@Override
	public void setChoice(Input in, Output out) throws IOException { }
	
}
