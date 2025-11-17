import java.util.List;

/**
 * A class representing a short answer question in a survey.
 * inherits from the Essay class but restricts the response to a 200 char limit.
 */

public class ShortAnswer extends Essay {
    /**
     * Constructor for the ShortAnswer class.
     */
    public ShortAnswer(String type) {
        super(type);
    }

    /**
     * Displays the short answer question prompt along with instructions for word limit.
     */
    @Override
    public void displayQuestion() {
        super.displayQuestion();
        Output.output("The word limit for this answer is 200 characters.");
    }

    /**
     * Checks the validity of the response to ensure it meets the char limit.
     * @param answer The answer provided by the user.
     * @return boolean True if the answer is within the 200-character limit, False otherwise.
     */
    @Override
    public boolean checkAnswerValidity(String answer) {
        if (answer.length() < 200) {
            return true;
        } else {
            Output.output("Answer must be below 200 characters.");
            return false;
        }
    }

    @Override
    public void tabulate(List<ResponseCorrectAnswer> responses, int i) {
        super.tabulate(responses, i, false);
    }
}
