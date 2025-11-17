import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A class for the responses to a survey.
 * It manages individual responses to questions and groups of responses for entire surveys.
 */
public class ResponseCorrectAnswer implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    protected String fileName; // File name for the serialized object

    // Path where the serialized file will be stored, local to this project
    private static final String basePath = FSConfig.serialDir + "Response" + File.separator;

    // A list of lists of responses, where each inside list represents responses to one survey
    private List<List<String>> responses = new ArrayList<>();
    private String surveyTestName;

    public ResponseCorrectAnswer() {}
    /**
     * Constructor for Response class.
     * Initializes a new list to collect responses to questions and generates a file name for serialization.
     */
    public ResponseCorrectAnswer(String name) {
        surveyTestName = name;

    }

    public List<String> getQuestionResponse(int i) {
        return responses.get(i);
    }

    public List<List<String>> getResponse(){
        return responses;
    }


    /**
     * Moves the current set of question responses to the main survey responses list
     * This marks completion of gathering responses for a particular survey
     */
    public void addResponsesToSurveyResponses(List<String> quesResponses) {
        responses.add(quesResponses);

    }

    /**
     * Static method to save the entire set of responses of a survey to a serialized file.
     *
     * @param response Response object containing all responses to be saved
     */
    public static void saveResponse(ResponseCorrectAnswer response) {
        System.out.println("Survey name: " + response.surveyTestName );
        String path = SerializationHelper.serialize(ResponseCorrectAnswer.class, response, basePath + response.surveyTestName
                + File.separator, SerializationHelper.generateFileName());
        Output.output("Response saved. Path: " + path);
    }


    public void displayResponse(int quesIndex, Question ques) {
        List<String> specificResponses = responses.get(quesIndex);
        if (ques.getType().equals("multiple choice")) {
            System.out.println("The correct choice(s) is ");
            MultipleChoice mcq = (MultipleChoice) ques;
            for (int i = 0; i < specificResponses.size(); i++) {
                int temp = Integer.parseInt(specificResponses.get(i));
                System.out.println(specificResponses.get(i) + ") " + mcq.getChoiceAtIndex(temp-1));
                //System.out.println((i+1) + ") " + mcq.getChoiceAtIndex(i));
            }
            System.out.println();
        } else if (ques.getType().equals("essay")) {
        }
        else {
            System.out.println("The correct answer(s) is:");
            for (int i = 0; i < specificResponses.size(); i++) {
                System.out.println(specificResponses.get(i));
            }
            System.out.println();
        }
    }
    /**
     * Modifies the response at a specific index
     */
    public void modifyResponseAtIndex(int index, List<String> response){
        responses.set(index, response);
    }

    public static ResponseCorrectAnswer loadResponse(String name) {
        String responsePath = FileUtils.listAndPickFileFromDir(basePath + name + File.separator);
        return SerializationHelper.deserialize(ResponseCorrectAnswer.class, responsePath);
    }

    /**
     * deserializes all responses
     */
    public static List<ResponseCorrectAnswer> loadAllResponses(String fileName) {
        List<ResponseCorrectAnswer> totalResponses = new ArrayList<>();

        List<String> totalPaths = FileUtils.getAllFilePathsInDir(basePath + fileName + File.separator);
        for (int i = 0; i < totalPaths.size(); i++) {
            String path = totalPaths.get(i);
            ResponseCorrectAnswer response = SerializationHelper.deserialize(ResponseCorrectAnswer.class, path);
            totalResponses.add(response);
        }
        return totalResponses;
    }


}

