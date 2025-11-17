import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * A class representing a date type question within a survey. inherits from Question
 */
public class Date extends Question {

    public Date(String type) {
        super(type);
    }

    /**
     * Displays the date question along with specific instructions for the date format.
     */
    @Override
    public void displayQuestion() {
        super.displayQuestion();
        Output.output("Please provide your answer in the following date format: MM/DD/YYYY");
    }

    /**
     * Checks the format of the answer provided by the user.
     * If invalid, prompts user to provide the date in correct format.
     * @param answer date string provided by the user.
     * @return boolean True if the date is valid, False otherwise.
     */
    public boolean checkAnswerValidity(String answer) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        try {
            LocalDate.parse(answer, formatter);
            return true;
        } catch (DateTimeParseException e) {
            Output.output("Please provide a date in the format: MM/DD/YYYY");
            return false;
        }
    }

}
