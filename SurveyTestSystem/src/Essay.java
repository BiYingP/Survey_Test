
import java.io.IOException;
import java.util.*;

public class Essay extends Question {

	private static final long serialVersionUID = 1L;

    public Essay(String questionPrompt) {
    	super(questionPrompt);
    }
    public String length;

	@Override
	public ResponseCorrectAnswer getResponse(Input in, Output out) throws Exception {
		length = in.getUserInput();
		 while (length.length() <= 0 ) {
			length = in.getUserInput();
			if (length.length() <= 0 ) {
				out.display("An essay cannot be empty");
			}
		}
		
		ResponseCorrectAnswer r = new ResponseCorrectAnswer(length);
		
		return r;
	}

	@Override
	protected void modifyChoices(Input in, Output out) throws Exception { }

	@Override
	protected void displayChoices(Output out) {

	}

	@Override
	public void setChoice(Input in, Output out) throws IOException { }

}
