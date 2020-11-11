import java.io.*;


public class ConsoleInput extends Input{

    private BufferedReader reader;
    public ConsoleInput() {

        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }
    public String getUserInput() throws IOException{
        String in = this.reader.readLine();
        return in;
    }
}
