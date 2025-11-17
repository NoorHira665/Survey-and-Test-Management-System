import java.io.FileNotFoundException;
import java.io.Serial;
import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a survey, which contains various types of questions.
 * handles the creation, modification, display, serialization, and deserialization of surveys.
 */
public class Survey implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //private static final String basePath = FSConfig.serialDir + "Survey" + File.separator;
    protected String fileName; // Filename for serialization
    protected List<Question> questions = new ArrayList<>();// List of questions in the survey
   // protected Question question;
    protected ResponseCorrectAnswer answer;
   // protected static int responsesRecorded = 0;
    protected int quesToModifyIndex;
    // tracks if the survey has been modified in order to save it after modification without prompting user for a new changed name
    protected static boolean modified = false;

    /**
     * Constructor for the Survey class.
     * Initializes the survey with a default generated filename for serialization. This name is changed when a new name is entered by the user.
     */
    public Survey() {
        fileName = SerializationHelper.generateFileName();
    }



    /**
     * Menu to create different types of questions and add them to the survey.
     */
    public void create() {
        while (true) {
            String prompt = getQuestionMenu();
            int choice = Validation.takeInputAndValidateInt(prompt, 7);
            if (choice == 7) {
                break;
            }
            Question question = switch (choice) {
                case 1 -> new TrueFalse("true/false");
                case 2 -> new MultipleChoice("multiple choice");
                case 3 -> new ShortAnswer("short answer");
                case 4 -> new Essay("essay");
                case 5 -> new Date("date");
                case 6 -> new Matching("matching");
                default -> null;
            };
            if (question != null) {
                question.add();
                questions.add(question);
                processQuestion(question);
            }
        }
    }

    protected void processQuestion(Question question) {

    }

    /**
     * Sets the survey as modified for resaving a modified survey to the same file and not to a new file.
     */
    public void setSurveyModifiedStatus() {
        modified = true;
    }

    /**
     * Returns a string containing the options for adding different types of questions to the survey.
     * @return A string menu for adding questions.
     */
    public String getQuestionMenu() {
        return "1.Add a new T/F question\n2.Add a new multiple-choice question\n3.Add a new short answer question\n4.Add a new essay question\n5.Add a new date question\n6.Add a new matching question\n7.Return to previous menu";
    }

    /**
     * Displays all the questions in the survey with their prompts and additional information.
     */
    public void display(boolean includeAnswer) {
        for (int i = 0; i < questions.size(); i++) {
            displayQuestionAtIndex(i, includeAnswer, questions.get(i));
        }
    }

    protected void displayQuestionAtIndex(int index, boolean includeAnswer, Question ques) {
        String numString = Integer.toString(index + 1);
        System.out.println("Question " + numString + ": ");
        ques.displayQuestion();
        System.out.println("\n");
    }


    /**
     * Static method to load a survey from a file chosen by the user.
     * @return The loaded Survey object.
     */
    public static Survey load(String basePath, String type) {
        try {
            String path = FileUtils.listAndPickFileFromDir(basePath);
            if (path != null && !path.isEmpty()) {
                return SerializationHelper.deserialize(Survey.class, path);
            } else {
                System.out.println("No " +  type + " available to load");
                return null;
            }
        } catch (Exception e) {
            System.out.println("No " +  type + " available to load");
            return null;
        }
    }

    /**
     * Static method to save the current survey to a file.
     * @param survey The survey to save.
     */
    public static void save(Survey survey, String type, String Path) {
        if (!modified) {
            System.out.println("Enter the name you want to save this with");
             String name = Input.takeInputStr();
            survey.setFileName(name);
        }
        String path = SerializationHelper.serialize(Survey.class, survey, Path, survey.fileName);
        System.out.println(type + " saved. Path: " + path);
    }

    /**
     * Allows the user to modify an existing question within the survey.
     */
    public void modify() {
        System.out.println("Here are all the questions:");
        display(false);

        String s = "What question number do you want to modify?";
        quesToModifyIndex = Validation.takeInputAndValidateInt(s, questions.size());

        questions.get(quesToModifyIndex-1).displayPrompt();
        System.out.println("Do you wish to modify the prompt? (Y/N)");
        if (Validation.takeInputAndValidateTwoChoiceAnswer("Y", "N")) {
            questions.get(quesToModifyIndex-1).modify();

        }
        questions.get(quesToModifyIndex-1).modifyChoices();
        setSurveyModifiedStatus();
    }

    /**
     * Allows the user to take the survey by responding to each question and validating those responses.
     */
    public void take() {
        ResponseCorrectAnswer responseCorrectAnswer = new ResponseCorrectAnswer(fileName);
        for (Question ques : questions) {
            ques.displayQuestion();
            List<String> quesReponse = ques.takeResponse(ques, false);
            responseCorrectAnswer.addResponsesToSurveyResponses(quesReponse);
        }
        ResponseCorrectAnswer.saveResponse(responseCorrectAnswer);
    }



    /**
     * Asks user to input a survey name in order to save it
     */
    public void setFileName(String name) {
        fileName = name;
    }

    /**
     * tabulates all responses for the survey
     */
    public void tabulate() {
        try {
            List<ResponseCorrectAnswer> responses = ResponseCorrectAnswer.loadAllResponses(fileName);
            int questionNumber = 1;
            for (Question question : questions) {
                System.out.print("Question " + questionNumber  + ": ");
                question.tabulate(responses, questionNumber - 1);
                questionNumber++;
            }
        } catch (Exception e) {
            System.out.println("No response has been recorded yet.");
        }
    }
}

