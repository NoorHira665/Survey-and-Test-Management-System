import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for a generic question structure for the survey
 * designed to handle various types of questions that can be used in surveys.
 * implements Serializable to allow question objects to be serialized for storage and retrieval.
 */
public abstract class Question implements Serializable {

    private String type;
    protected String prompt;
    protected int allowedResponses;
    private static final long serialVersionUID = 1L;
    protected List<String> response;

    /**
     * Constructor for creating a question with a specific type.
     * @param type The type of question, such as "Multiple Choice", "True/False", etc.
     */
    public Question(String type) {
        this.type = type;
        response = new ArrayList<>();
    }

    public String getType(){
        return type;
    }


    /**
     * Displays the question prompt and, if necessary, information about how many responses are allowed.
     */
    public void displayQuestion() {
        displayPrompt();
        if (allowedResponses != 1) {
            displayAllowedResponsesInfo();

        }
    }

    /**
     * takes a response or correct answer from user
     */
    public List<String> takeResponse(Question ques, boolean correctAns) {
        if (correctAns) {
            System.out.println("Enter the correct answer(s):");
        }
        return takeResponseHelper(ques);
    }

    /**
     * helper to take a response or correct answer from user and check its validity
     */
    public List<String> takeResponseHelper(Question ques) {
        List<String> response = new ArrayList<>();
        for (int j = 0; j < allowedResponses; j++) {

            while (true) {
                String ans = Input.takeInputStr();
                boolean ansValid = ques.checkAnswerValidity(ans);
                if (ansValid) {
                    response.add(ans);
                    break;
                }
            }
        }
        return response;
    }


    /**
     * Displays the prompt of the question to the user. Used when the user chooses to display the survey and not take it.
     */
    public void displayPrompt() {
        Output.output(prompt);
    }

    /**
     * Displays information about the number of responses allowed for the question, if more than one response is allowed.
     */
    public void displayAllowedResponsesInfo() {
        String strChoices = Integer.toString(allowedResponses);
        Output.output("Please give " + strChoices + " answers.");
    }

    /**
     * adds a new question to survey by getting the prompt from the user and the number of allowed responses.
     */
    public void add() {
        prompt = Validation.takeInputAndValidateStr("Enter the prompt for the new " + type + " question:");
        inputResponsesInfo();
    }

    /**
     * Sets the number of allowed responses for a question and ensures at least one response is allowed by checking the user input.
     */
    public void inputResponsesInfo() {
        while (true) {
            String s = "Enter the number of responses you want for this question.";
            allowedResponses = Validation.takeInputAndValidateInt(s);
            if (allowedResponses == 0) {
                System.out.println("Please allow at least 1 response");
            } else {
                break;
            }
        }
    }
    /**
     * Modifies the existing prompt of the question.
     */
    public void modify() {
        String s = "Enter new prompt:";
        prompt = Validation.takeInputAndValidateStr(s);
    }
    /**
     * Abstract method to be implemented by subclasses to check the validity of a provided answer.
     * @param answer The answer to validate.
     * @return boolean indicating whether the answer is valid according to question-specific criteria.
     */
    public abstract boolean checkAnswerValidity(String answer);


    public void modifyChoices() {

    }

    /**
     * tabulates a response
     */
    public void tabulate(List<ResponseCorrectAnswer> responses, int questionIndex) {
        displayQuestion();
        System.out.println();
        Map<String, Integer> frequencyMap = initializeFrequencyMap();
        frequencyMap = countResponses(responses, questionIndex, frequencyMap);
        printFrequencies(frequencyMap);
        System.out.println();
    }

    /**
     * initializes a frequency map to store the responses for tabulation
     */
    protected Map<String, Integer> initializeFrequencyMap() {
        return new HashMap<>();
    }
    /**
     * counts responses of each type for tabulation
     */
    public Map<String, Integer> countResponses(List<ResponseCorrectAnswer> responses, int quesIndex, Map<String, Integer> frequencyMap) {
        for (ResponseCorrectAnswer response : responses) {
            List<String> responseList = response.getQuestionResponse(quesIndex);
            for (String responseItem : responseList) {
                frequencyMap.merge(responseItem, 1, Integer::sum);
            }
        }
        return frequencyMap;
    }
    /**
     * prints frequencies of responses for tabulation
     */
    protected void printFrequencies(Map<String, Integer> frequencies) {
        for (Map.Entry<String, Integer> entry : frequencies.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    protected Map<String, Integer> initializeFrequencyMap(int optionCount){
        return new HashMap<>();
    };
}