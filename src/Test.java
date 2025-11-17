import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test extends Survey implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private ResponseCorrectAnswer correctAnswers = new ResponseCorrectAnswer();


    public Test() {
    }
    /**
     * processes questions to add to test. Prompts user for adding a correct answer for all question types except essay
     */
    @Override
    protected void processQuestion(Question question) {
        if (!question.getType().equals("essay")){
        List<String> response = question.takeResponse(question, true);
        correctAnswers.addResponsesToSurveyResponses(response);}
        else {
            List<String> response = null;
            correctAnswers.addResponsesToSurveyResponses(response);
        }
    }
    /**
     * displays the question at a specific index
     */
    @Override
    public void displayQuestionAtIndex(int index, boolean includeAnswer, Question ques) {
        super.displayQuestionAtIndex(index, includeAnswer, ques);

        if (includeAnswer) {
            correctAnswers.displayResponse(index, ques);
        }
    }
    /**
     * modifies a test
     */
    @Override
    public void modify(){
        super.modify();
        if (!questions.get(quesToModifyIndex-1).getType().equals("essay")){
        Output.output("Do you wish to modify the correct answer? (Y/N)");
        if (Validation.takeInputAndValidateTwoChoiceAnswer("Y", "N")) {
            List<String> newResponse = questions.get(quesToModifyIndex-1).takeResponse(questions.get(quesToModifyIndex-1), true);
            correctAnswers.modifyResponseAtIndex(quesToModifyIndex-1, newResponse);
        }
    }}
    /**
     * getter for correctAnswer
     */
    public ResponseCorrectAnswer getCorrectAnswer(){
        return correctAnswers;
    }
    /**
     * grades a response for a test against the correct answers
     */
    public static void grade(Test currentTest) {
        int correctCount = 0;
        int autoGradableQuestions = 0;
        ResponseCorrectAnswer currentResponse = ResponseCorrectAnswer.loadResponse(currentTest.fileName);
        ResponseCorrectAnswer correctResponse = currentTest.getCorrectAnswer();

        List<List<String>> correctAnswers = correctResponse.getResponse();
        List<List<String>> currentAnswers = currentResponse.getResponse();

        for (int i = 0; i < correctAnswers.size(); i++) {
            if (correctAnswers.get(i) != null) {
                autoGradableQuestions++;
                if (compareResponses(correctAnswers.get(i), currentAnswers.get(i))) {
                    correctCount++;
                }
            }
        }

        int totalQuestions = correctAnswers.size();
        double score = (correctCount / (double) totalQuestions) * 100;
        double autoGradableScore = (autoGradableQuestions / (double) totalQuestions) * 100;
        int essayQuestions = totalQuestions - autoGradableQuestions;

        System.out.println("You received a " + String.format("%.2f", score) + " on the test.");
        System.out.print("The test was worth 100 points, ");

        if (essayQuestions > 0) {
            System.out.println("but only " + String.format("%.2f", autoGradableScore) + " of those points could be auto-graded because there was " +
                    essayQuestions + "essay question(s).");
        } else {
            System.out.println("and all could be auto graded because there were 0 essay questions.");
        }
    }
    /**
     * compares a response with the correct Answer
     */
    private static boolean compareResponses(List<String> correctResponse, List<String> currentResponse) {
        if (correctResponse == null || currentResponse == null) {
            return false;
        }
        Set<String> correctSet = new HashSet<>(correctResponse);
        Set<String> currentSet = new HashSet<>(currentResponse);
        return correctSet.equals(currentSet);
    }



    }


