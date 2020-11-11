
import java.io.*;
import java.util.*;


public abstract class Question implements Serializable {

    
	private static final long serialVersionUID = 1L;
	protected ArrayList<String>choices;
	protected String questionPrompt;

    public Question(String questionPrompt) {
    	this.questionPrompt = questionPrompt;
    	this.choices = new ArrayList<String>();
    }

    //To modify the question prompt and choices
    public void modify(Input in, Output out) throws Exception {
        this.display(out); //display the question to be modified first
        out.display("Do you wish to modify the prompt? (y/n)");

        String userInput = in.getUserInput();
        while (!userInput.equals("y") && !userInput.equals("n")){
            out.display("Do you wish to modify the prompt? (y/n)");
            userInput = in.getUserInput();
        }
        if (userInput.equals("y")) {
            out.display("Old Prompt: " + this.questionPrompt);
            out.display("Enter a new prompt:");
            //userInput = in.getUserInput();
            this.setQuestionPrompt(in, out);
        }
        //modify choices
        modifyChoices(in, out);
        this.display(out);
    }

    public ArrayList<String>getChoice(){ return choices; }
    public abstract void setChoice(Input in, Output out) throws IOException;

    public void setQuestionPrompt(Input in, Output out){
        try {
            String userInput = in.getUserInput();
            while (userInput.length() == 0){
                userInput = in.getUserInput();
                if (userInput.length() == 0) {
                    out.display("The prompt cannot be empty, try again");
                }
            }
            this.questionPrompt = userInput;
        } catch (Exception e) {
            out.display("Error setting the prompt:");
        }
    }

    public String getQuestionPrompt() { return this.questionPrompt; }
    public void display(Output out) {
    	out.display(questionPrompt);
    	displayChoices(out);
    }
    protected abstract void displayChoices(Output out);
    public abstract ResponseCorrectAnswer getResponse(Input in, Output out) throws Exception;
    protected abstract void modifyChoices(Input in, Output out) throws Exception;

}
