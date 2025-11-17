import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class representing a True/False question in a survey. Inherits from Question
 */
public class TrueFalse extends MultipleChoice {
    /**
     * Constructor for the TrueFalse class.
     */
    public TrueFalse(String type) {
        super(type);
    }
    /**
     * Adds a new True/False question to the survey.
     * limits the allowed responses to one.
     */
    @Override
    public void add() {
        prompt = Validation.takeInputAndValidateStr("Enter the prompt for the new true/false question:");
        allowedResponses = 1;
    }

    /**
     * Displays the True/False question along with instructions for answering.
     */
    @Override
    public void displayQuestion() {
        super.displayQuestion();
        System.out.println("Please answer T for true and F for false.\nT) True\nF) False");
    }


    /**
     * Checks the validity of the user's response to ensure it is either "T" for true or "F" for false.
     * @param answer The answer provided by the user.
     * @return boolean True if the answer is valid ("T" or "F"), False otherwise.
     */

    public boolean checkAnswerValidity(String answer) {

        if (answer.equalsIgnoreCase("T") || answer.equalsIgnoreCase("F")) {
            return true;
        } else {
            System.out.println("Invalid input. Please give either T for true or F for false");
            return false;
        }
    }
    /**
     * Takes correct choice from user
     */
    @Override
    public List<String> takeResponse(Question ques, boolean correctAns) {
        if (correctAns) {
            System.out.println("Enter the correct choice. Enter T for true and F for false:");
        }
        return takeResponseHelper(ques);
    }

    /**
     * Overrides modifyChoices to prevent choice modification
     */
    @Override
    public void modifyChoices(){

    }

    @Override
    public void tabulate(List<ResponseCorrectAnswer> responses, int questionIndex) {
        super.tabulate(responses,questionIndex );
    }

    @Override
    public Map<String, Integer> countResponses(List<ResponseCorrectAnswer> responses, int quesIndex,Map<String, Integer> frequencyMap ) {
        for (ResponseCorrectAnswer response : responses) {
            List<String> responseList = response.getQuestionResponse(quesIndex);
            for (String responseItem : responseList) {
                String standardizedResponse = responseItem.toUpperCase();
                if (frequencyMap.containsKey(standardizedResponse)) {
                    frequencyMap.merge(standardizedResponse, 1, Integer::sum);
                }
            }
        }

        return frequencyMap;
    }

    @Override
    protected Map<String, Integer> initializeFrequencyMap() {
        Map<String, Integer> frequencyMap = new HashMap<>();

        frequencyMap.put("T", 0);
        frequencyMap.put("F", 0);
        return frequencyMap;
    }

    protected void printFrequencies(Map<String, Integer> frequencies) {
        System.out.println("T: " + frequencies.get("T"));
        System.out.println("F: " + frequencies.get("F"));
    }


}
