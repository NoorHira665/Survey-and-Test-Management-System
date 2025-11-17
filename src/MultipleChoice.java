import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing a multiple choice question within a survey. Inherits from Question
 */
public class MultipleChoice extends Question {
    private List<String> choices;
    private int numChoices;

    public MultipleChoice(String type) {
        super(type);
        this.choices = new ArrayList<>();
    }
    /**
     * Adds a new multiple choice question. collects and validates the number of responses allowed for this question.
     */
    @Override
    public void add() {
        prompt = Validation.takeInputAndValidateStr("Enter the prompt for the new multiple choice question:");
        String prompt = "Enter the number of choices.";
        numChoices = Validation.takeInputAndValidateInt(prompt);
        addChoices(numChoices);
        inputResponsesInfo();
    }

    /**
     * Displays the multiple choice question along with all the available choices.
     */
    @Override
    public void displayQuestion() {
        super.displayQuestion();
        displayChoices();
    }

    /**
     * Displays the choices for the multiple choice question, numbering each choice.
     */
    private void displayChoices() {
        if (!choices.isEmpty()) {
            for (int i = 0; i < choices.size(); i++) {
                String strNumber = Integer.toString(i + 1);
                System.out.println(strNumber + ") " + choices.get(i));
            }
        }
    }
    /**
     * gets the choice at a specific index
     */
    public String getChoiceAtIndex(int i){
        return choices.get(i);
    }

    /**
     * Prompts the user to add choices for the multiple choice question.
     * @param numChoices The number of choices to be added.
     */
    public void addChoices(int numChoices) {
        for (int i = 1; i <= numChoices; i++) {
            System.out.println("Enter choice " + i + ":");
            String choiceText = Input.scanner.nextLine();
            choices.add(choiceText);
        }
    }


    /**
     * Allows modification of the multiple choice question's prompt its choices.
     */
    @Override
    public void modifyChoices() {
            Output.output("Do you wish to modify choices? (Y/N)");
            if (Validation.takeInputAndValidateTwoChoiceAnswer("Y", "N")) {
                Output.output("Which choice do you want to modify? (Enter Number)");
                displayChoices();
                int choiceToModify = Validation.takeInputAndValidateInt("", choices.size());
                String s = "Enter new value for choice";
                s = Validation.takeInputAndValidateStr(s);
                choices.set(choiceToModify - 1, s);

        }

    }

    /**
     * Checks the validity of the user's response to ensure it is a valid choice number.
     * @param answer answer provided by the user.
     * @return boolean True if the answer is a valid choice number, False otherwise.
     */
    public boolean checkAnswerValidity(String answer) {
        if (Validation.isInt(answer) && Validation.isIntBetweenInclusive(1, choices.size(), answer)) {
            return true;
        } else {
            Output.output("Invalid input. Please enter a valid choice number.");
            return false;
        }
    }

    /**
     * Takes a users input for a response or correct answer
     */
    @Override
    public List<String> takeResponse(Question ques, boolean correctAns) {
        if (correctAns) {
            System.out.println("Enter the correct choice(s):");
        }
        return takeResponseHelper(ques);
    }

    /**
     * tabulates responses
     */
    @Override
    public void tabulate(List<ResponseCorrectAnswer> responses, int questionIndex){
        super.tabulate(responses,questionIndex);

    }

    /**
     * initializes a frequency map to store frequency of responses for tabulation
     */
    @Override
    protected Map<String, Integer> initializeFrequencyMap() {
        return initializeFrequencyMap(numChoices);

    }

    @Override
    protected Map<String, Integer> initializeFrequencyMap(int optionCount) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (int i = 1; i <= optionCount; i++) {
            frequencyMap.put(String.valueOf(i), 0);
        }
        return frequencyMap;
    }
}

