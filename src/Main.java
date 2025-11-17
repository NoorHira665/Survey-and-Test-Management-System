import java.io.File;

/**
 * Main class responsible for handling the survey application
 * It facilitates survey creation, loading, saving, displaying, modifying, and taking surveys through a console-based menu system.
 * To run this code, press the shortcut for 'Run' or click the execute icon in the gutter.
 */
public class Main {

    /**
     * Constructor for the main class
     */
    public static void main(String[] args) {
        Output.output("Welcome! Please select an option by entering the corresponding menu number");
        mainMenu();

    }


    public static void mainMenu() {
        while (true) {
            String prompt = "1.Survey\n2.Test\n3.Quit\n";
            int input = Validation.takeInputAndValidateInt(prompt, 3);
            if (input == 1) {
                SurveyMenu();
            } else if (input == 2) {
                TestMenu();
            } else {
                break;
            }
        }
    }

    /**
     * Displays the survey menu and handles the user input to navigate through various survey operations.
     * It continues to show the menu until the user chooses to quit.
     */
    public static void SurveyMenu() {
        Survey survey = null;
        while (true) {
            String prompt = "1.Create a new Survey\n2.Display an existing Survey\n3.Load an existing Survey\n4.Save the current Survey\n5.Take the current Survey\n6.Modify the current Survey\n7.Tabulate a Survey\n8.Return to previous menu";
            int input = Validation.takeInputAndValidateInt(prompt, 8);
            if (input == 8) {
                break;
            } else if (input == 1) {
                survey = new Survey();
                survey.create();
            } else if (input == 3) {
                survey = Survey.load(FSConfig.serialDir + "Survey" + File.separator, "Survey");
            } else if (input == 4) {
                if (survey != null) {
                    Survey.save(survey, "survey", FSConfig.serialDir + "Survey" + File.separator);
                } else {
                    Output.output("You must have a survey loaded in order to save it.");
                }
            } else if (input == 2) {
                if (survey == null) {
                    Output.output("You must have a survey loaded in order to display it.");
                } else {
                    survey.display(false);
                }
            } else if (input == 6) {
                if (survey == null) {
                    Output.output("You must have a survey loaded in order to modify it.");
                } else {
                    survey.modify();
                }
            } else if (input == 5) {
                if (survey == null) {
                    Output.output("You must have a survey loaded in order to take it.");
                } else {
                    survey.take();
                }
            } else if (input == 7) {
                if (survey == null) {
                    Output.output("You must have a survey loaded in order to tabulate it.");
                } else {
                    survey.tabulate();
                }
            }
        }
    }

    /**
     * Displays the test menu and handles the user input to navigate through various test operations.
     * It continues to show the menu until the user chooses to quit.
     */
    public static void TestMenu() {
        Test test = null;
        while (true) {
            String prompt = "1.Create a new Test\n2.Display an existing Test without correct answers\n3.Display an existing Test with correct answers\n4.Load an existing Test\n5.Save the current Test\n6.Take the current Test\n7.Modify the current Test\n8.Tabulate a Test\n9.Grade a Test\n10.Return to previous menu";
            int input = Validation.takeInputAndValidateInt(prompt, 10);
            if (input == 10) {
                break;
            } else if (input == 1) {
                test = new Test();
                test.create();
            } else if (input == 2) {
                if (test == null) {
                    Output.output("You must have a test loaded in order to display it.");
                } else {
                    test.display(false);
                }
            } else if (input == 3) {
                if (test == null) {
                    Output.output("You must have a test loaded in order to display it.");
                } else {
                    test.display(true);
                }
            } else if (input == 4) {
                test = (Test) Test.load(FSConfig.serialDir + "Test" + File.separator, "test");
            } else if (input == 5) {
                if (test != null) {
                    Test.save(test, "test", FSConfig.serialDir + "Test" + File.separator);
                } else {
                    Output.output("You must have a test loaded in order to save it.");
                }
            } else if (input == 6) {
                if (test == null) {
                    Output.output("You must have a test loaded in order to take it.");
                } else {
                    test.take();
                }
            } else if (input == 7) {
                if (test == null) {
                    Output.output("You must have a test loaded in order to modify it.");
                } else {
                    test.modify();
                }
            } else if (input == 8) {
                if (test == null) {
                    Output.output("You must have a test loaded in order to tabulate it.");
                } else {
                    test.tabulate();
                }
            } else if (input == 9) {
                if (test == null) {
                    Output.output("You must have a test loaded in order to grade it.");
                } else {
                    Test.grade(test);
                }
            }
        }
    }
}


