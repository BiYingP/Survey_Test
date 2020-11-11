
import java.io.*;

public abstract class Input {
	
	private BufferedReader reader;

	public Input() {
		
		this.reader = new BufferedReader(new InputStreamReader(System.in));
	}

	public abstract String getUserInput() throws IOException;
}
