
import java.io.*;

public class Main {

    private static Survey survey;
    public static ConsoleInput in = new ConsoleInput();
    public static ConsoleOutput out = new ConsoleOutput();

    private static void menu() throws Exception {
    	in = new ConsoleInput();

        int num1 = 0;
        while (num1 != 1 && num1 != 2) {

            try {
                num1 = Integer.parseInt(in.getUserInput());
                if (num1 < 1 || num1 > 2) {
                    out.display("Input out of range, options are 1 through 2");
                }

                if (num1 == 1) {
                    while (true) {
                        surveyMenu();
                    }
                }
                if (num1 == 2) {
                    while (true) {
                        testMenu();
                    }
                }
            } catch (NumberFormatException e) {
                out.display("Invalid input, integer expected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void surveyMenu() throws NumberFormatException, Exception{

        // menu 1
        int num2 = 0;
        out.display("Choose option");
        out.display("1) Create a new survey");
        out.display("2) Display a survey");
        out.display("3) Load a survey");
        out.display("4) Save a survey");
        out.display("5) Modify an exiting survey");
        out.display("6) Take a survey");
        out.display("7) Tabulate a survey");
        out.display("8) Quit");


        while (num2 < 1 || num2 > 8) {

            try {
                num2 = Integer.parseInt(in.getUserInput());
                if (num2 < 1 || num2 > 8) {
                    out.display("Input out of range. Please enter 1 to 8");
                }
            } catch (NumberFormatException e) {
                out.display("Invalid input, integer expected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        switch (num2) {
            case 1:
                create("surveys");
                break;
            case 2:
                display();
                break;
            case 3:
                load("surveys");
                break;
            case 4:
                save("surveys");
                break;
            case 5:
                modify("surveys");
                break;
            case 6:
                take("surveys");
                break;
            case 7:
                tabulate("surveys");
                break;
            case 8:
                quit();
                System.exit(0);
                break;
            default:
                break;
        }
    }

    private static void modify(String type) {
        out.display("What " + type + " do you wish to modify?");
        try {
            load(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        survey.display(out);
        try {
            survey.modify(in, out);
            save(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void take(String type) {
        out.display("What " + type + " do you wish to take?");
        try {
            load(type);
            //survey.display(out);
            out.display("Begin "+ type);
            survey.take(in, out);
            out.display("Finish " + type);
            save(type);
        } catch (Exception e) {
            e.printStackTrace();
        }

}


	private static void testMenu() throws NumberFormatException, Exception {
		//menu 2
		int num2 = 0;
		out.display("Choose option");
		out.display("1) Create a new test");
		out.display("2) Display a test");
		out.display("3) Load a test");
		out.display("4) Save a test");
		out.display("5) Modify an exiting test");
		out.display("6) Take a test");
		out.display("7) Tabulate a test");
		out.display("8) Gade a test");
		out.display("9) Quit");


		while (num2 < 1 || num2 > 9 ){

			try {
				num2 = Integer.parseInt(in.getUserInput());
				if (num2 < 1 || num2 > 9) {
					out.display("Input out of range. Please enter 1 to 9");
				}
			} catch (NumberFormatException e) {
				out.display("Invalid input, integer expected");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		switch (num2) {
			case 1:
				create("tests");
				break;
			case 2:
				display();
				break;
			case 3:
				load("tests");
				break;
			case 4:
				save("tests");
				break;
			case 5:
				modify("tests");
				break;
			case 6:
				take("tests");
				break;
			case 7:
				tabulate("tests");
				break;
			case 8:
				grade();
				break;
			case 9:
				quit();
				System.exit(0);
				break;
			default:
				break;
		}
	}

	private static void tabulate(String type) {
		if (survey == null){
			out.display("No " + type + " created or loaded into memory. Create or load one first.");
		}
		else{
			out.display("Begin Tabulating");
			survey.tabulate(in, out);
		}
	}
	private static void grade(){
		if (survey == null){
			out.display("No tests created or loaded into memory. Create or load one first.");
		}
		else {

			try {
				//load("tests");
				if (survey instanceof Test) {
					Test t = (Test) survey;
					t.grade(in, out);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private static void create(String type) throws Exception {
		if (type.equals("surveys") ) {
			survey = new Survey(in, out);
			survey.create(in, out);

		}else{
			survey = new Test(in,out);
			survey.create(in, out);
		}
	}

	private static void quit() throws Exception{

		out.display("Quit");
	}

	private static void save(String type) throws IOException, Exception {
		String path;
		path = "" + type +"/";

		if (survey == null) {
			out.display("Nothing in memory to save! Create something first.");
		}
		else {

			try {
				path = path + survey.getName() + ".ser";

				FileOutputStream fileOut = new FileOutputStream(path);
				ObjectOutputStream os = new ObjectOutputStream(fileOut);
				os.writeObject(survey);

				os.close();
				fileOut.close();
				out.display("Successfully saved to: " + path);

			} catch (IOException e) {
				out.display("Failure to save to file, error given: ");

			}
		}

	}

	private static void load(String type) throws NumberFormatException, Exception {

    	// Determine which folder to start looking in
		String path = "" + type + "/";

		// Get all files in folder
		File folder = new File(path);
		File[] files = folder.listFiles();

		if (files.length == 0) {
			out.display("No files found");

		} else {

				// Prompt user to select file and attempt to load and deserialize it
			int numfile = 0;
			while (numfile < 1 || numfile > files.length) {
				out.display("Choose a file to load:");
				for (int i = 0; i < files.length; i++) {
					out.display((i+1) + ") " + files[i].getName());
				}
				try {
					numfile = Integer.parseInt(in.getUserInput());
					if (numfile < 1 || numfile > files.length) {
						out.display("Input out of range, options are 1 through " + files.length);
					}
				} catch (NumberFormatException e) {
					out.display("Invalid input, integer expected");
				} catch (IOException e) {
					out.display("Failure to load file, error given: ");

				}
			}

			path = files[numfile-1].getPath();

			try {
				FileInputStream fileIn = new FileInputStream(path);
				ObjectInputStream inputStream = new ObjectInputStream(fileIn);

				if (type.equals("surveys") ) {
					survey = (Survey)inputStream.readObject();
				}else{
					survey = (Test)inputStream.readObject();
				}

				inputStream.close();
				fileIn.close();
				out.display("File successfully loaded");
			} catch (Exception e) {
				out.display("Failure to load file, error given: ");
			}
		}
	}

	private static void display() {
		if (survey == null) {
			out.display("No survey/test created or loaded into memory! Create or load one first");
		}
		else {
			survey.display(out);
		}
	}

	public static void main(String[] args) throws Exception {
		ConsoleOutput out = new ConsoleOutput();

		out.display("Please choose survey or test by entering its corresponding number");
		out.display("1) Survey");
		out.display("2) Test");

		menu();
	}
}

