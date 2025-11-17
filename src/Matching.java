import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing a matching question in a survey. Inherits from Question
 */
public class Matching extends Question {
    List<String> leftSide;
    List<String> rightSide;

    public Matching(String type) {
        super(type);
        this.leftSide = new ArrayList<>();
        this.rightSide = new ArrayList<>();
    }

    @Override
    public List<String> takeResponse(Question ques, boolean correctAns) {
        if (correctAns) {
            System.out.println("Enter the correct choice(s) in the format: NumChar, e.g 1C:");
        }
        return takeResponseHelper(ques);
    }

    /**
     * Adds a new matching question with user-defined choices
     */
    @Override
    public void add() {
        prompt = Validation.takeInputAndValidateStr("Enter the prompt for the new matching question");
        String s = "Enter the number of choices.";
        int numChoices = Validation.takeInputAndValidateInt(s);
        addChoices(numChoices);
        allowedResponses = numChoices;
    }
    /**
     * Helper to add() method to allow user to add choices for the left and right sides of the matching question.
     */
    private void addChoices(int numChoices) {
        for (int i = 1; i <= numChoices; i++) {

            System.out.println("Enter choice " + i + " on the left side:");
            String choiceText = Input.scanner.nextLine();
            leftSide.add(choiceText);
        }

        for (int i = 1; i <= numChoices; i++) {
            System.out.println("Enter choice " + i + " on the right side:");
            String choiceText = Input.scanner.nextLine();
            rightSide.add(choiceText);
        }
    }
    /**
     * Displays the matching question along with instructions on how to answer and the available choices.
     */
    @Override
    public void displayQuestion() {
        displayPrompt();
        Output.output("Please answer in the format: NumChar, e.g 1C");
        displayChoices();
    }

    /**
     * displays choices formatted in 2 columns
     */
    private void displayChoices() {
        for (int i = 0; i < leftSide.size(); i++) {
            char letter = (char) ('A' + i);
            String leftLabel = (i + 1) + ") " + leftSide.get(i);
            String rightLabel = letter + ") " + rightSide.get(i);
            System.out.printf("%-40s %s\n", leftLabel, rightLabel);
        }
    }
    /**
     * modifies existing prompt and the choices for the matching question.
     */
    @Override
    public void modifyChoices() {
        Output.output("Do you wish to modify choices? (Y/N)");
        if (Validation.takeInputAndValidateTwoChoiceAnswer("Y", "N")) {
            Output.output("Here are the choices:");
            displayChoices();
            Output.output("Which side do you want to modify? (left/right)");
            boolean temp = Validation.takeInputAndValidateTwoChoiceAnswer("left", "right");

            if (temp) {
                Output.output("Which choice do you want to modify in the selected side? (Enter Number)");
                int choiceToModify = Validation.takeInputAndValidateInt("", leftSide.size());
                String s = "Enter new value for choice:";
                String newValue = Validation.takeInputAndValidateStr(s);
                leftSide.set(choiceToModify - 1, newValue);
            } else {
                Output.output("Which choice do you want to modify in the selected side? (Enter alphabet)");
                int choiceToModify = Validation.takeInputCharAndReturnInt(rightSide.size());

                String s = "Enter new value for choice:";
                String newValue = Validation.takeInputAndValidateStr(s);
                rightSide.set(choiceToModify - 1, newValue);
            }

        }
    }
    /**
     * Checks if the user's answer to the matching question is valid based on the format and range of choices.
     */
    public boolean checkAnswerValidity(String answer) {
        if (!answer.matches("(1[0-9]|2[0-6]|[1-9])[a-zA-Z]")) {
            Output.output("Invalid input. Answer must be in the format: NumChar, e.g 1B, 2C etc.");
            return false;
        }
        int number = Integer.parseInt(answer.substring(0, 1));
        char character = answer.toUpperCase().charAt(1);
        if ((number >= 1 && number <= leftSide.size()) && (character >= 'A' && character <= intToAlphabet(rightSide.size()))) {
            return true;
        } else {
            Output.output("Invalid answer. Please give an answer within the given range");
            return false;
        }
    }

    public static char intToAlphabet(int number) {
        return (char) ('A' + number - 1);
    }


}



