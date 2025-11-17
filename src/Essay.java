import java.util.List;

public class Essay extends Question {
    /**
     * A class representing an essay type question within a survey. inherits from Question
     */


    public Essay(String type) {
        super(type);
    }

    /**
     * Checks the format of the answer provided by the user.
     * Since essay type allows any answer, always returns true
     */
    public boolean checkAnswerValidity(String answer) {
        return true;
    }


    /**
     * overrides tabulate method in parent class
     */
    @Override
    public void tabulate(List<ResponseCorrectAnswer> responses, int quesIndex) {
        displayAllResponses(responses, quesIndex);
    }

    /**
     * Tabulates responses
     */
    public void tabulate(List<ResponseCorrectAnswer> responses, int quesIndex, boolean displayAll) {
        if (displayAll) {
            displayPrompt();
            System.out.println();
            displayResponses(responses, quesIndex);
            System.out.println();
        }
        else {
                super.tabulate(responses, quesIndex);}
    }

    /**
     * Displays all responses to a survey
     */
    private void displayAllResponses(List<ResponseCorrectAnswer> responses, int quesIndex) {
        tabulate(responses, quesIndex, true);
    }

    /**
     * Helper to displayAllResponses
     */
    private void displayResponses(List<ResponseCorrectAnswer> responses, int quesIndex) {
        for (ResponseCorrectAnswer response : responses) {
            List<String> responseList = response.getQuestionResponse(quesIndex);
            for (String answer : responseList) {
                System.out.println(answer);
            }
        }
    }
}
