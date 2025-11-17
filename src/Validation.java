/**
 * @author Sean Grimes, sean@seanpgrimes.com
 * <p>
 * Validate that values are what they're supposed to be. Very helpful when getting user
 * input.
 * Edited by Noor e Hira to add additional methods needed
 */
@SuppressWarnings({"WeakerAccess", "unused", "BooleanMethodIsAlwaysInverted"})
public class Validation {
    /**
     * Validate that a string is a number
     *
     * @param num The string to be validated
     * @return True if the String can be parsed to an int, else false
     */
    public static boolean isInt(String num) {
        try {
            Integer.parseInt(num);
            return true;
        }
        // Not an int
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate that a string is a double
     *
     * @param num The string to be validated
     * @return True if it's a double, else false
     */
    public static boolean isDouble(String num) {
        try {
            Double.parseDouble(num);
            return true;
        }
        // Not a double
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate that a String is a valid integer between an inclusive range
     *
     * @param start The start of the inclusive range
     * @param end   The end of the inclusive range
     * @param num   The String to be validated
     * @return True if it's a valid int between the inclusive range, else false
     */
    public static boolean isIntBetweenInclusive(int start, int end, String num) {
        if (!isInt(num)) return false;
        int val = Integer.parseInt(num);
        return val >= start && val <= end;
    }

    /**
     * Converts a character to its corresponding integer position in the alphabet (A=1, B=2, etc.).
     * @param ch The character to convert.
     * @return The integer position of the character in the alphabet, or -1 if not a valid letter.
     */
    public static int convertCharToInt(char ch) {
        if (Character.isLetter(ch)) {
            return Character.toUpperCase(ch) - 'A' + 1;
        }
        return -1; // Not a valid letter
    }

    /**
     * Takes a single character input from the user and returns its integer equivalent if valid.
     * Validates that the character corresponds to a valid range based on input criteria.
     * @param upperRange The upper limit of the valid range.
     * @return The integer position of the character in the alphabet if valid, prompting until valid input is received.
     */
    public static int takeInputCharAndReturnInt(int upperRange) {
        while (true) {
            String input = Input.takeInputStr();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Please try again");
                continue;
            }

            char ch = input.charAt(0);
            int converted = convertCharToInt(ch);
            if (converted != -1) {
                if (isIntBetweenInclusive(1, upperRange, Integer.toString(converted))) {
                    return converted;
                } else {
                    System.out.println("Invalid input. Please try again");
                }
            } else {
                System.out.println("Invalid input. Please try again");
            }
        }
    }

    /**
     * Prompts the user and validates that the input is an integer within a specified range.
     *
     * @param prompt The prompt to display to the user.
     * @param range The upper range limit for valid input.
     * @return The validated integer within the range.
     */
    public static int takeInputAndValidateInt(String prompt, int range) {
        while (true) {
            System.out.println(prompt);
            String input = Input.takeInputStr();
            if (isInt(input)) {
                int result = Integer.parseInt(input);
                if (result > 0 && result <= range) {
                    return result;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    /**
     * Prompts the user for a string input until a non-empty string is provided.
     * @param prompt The prompt to display to the user.
     * @return The non-empty string input by the user.
     */
    public static String takeInputAndValidateStr(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = Input.takeInputStr();
            if (input.isEmpty()) {
                Output.output("Invalid input. Please try again");
            } else {
                return input;
            }
        }
    }

    /**
     * Validates a two-choice answer (e.g., Yes/No) from the user, ensuring the response matches one of the two provided choices.
     * @param choiceOne The first valid choice.
     * @param choiceTwo The second valid choice.
     * @return True if the user's input matches the first choice, false if it matches the second.
     */
    public static boolean takeInputAndValidateTwoChoiceAnswer(String choiceOne, String choiceTwo) {
        while (true) {
            String Ans = Input.takeInputStr();
            if (Ans.equalsIgnoreCase(choiceOne)) {
                return true;
            } else if (Ans.equalsIgnoreCase(choiceTwo)) {
                return false;
            } else {
                Output.output("Invalid input. Please try again");
            }
        }
    }

    /**
     * Prompts the user and validates that the input is an integer, prompting repeatedly until a valid integer is provided.
     * @param prompt The prompt to display to the user.
     * @return The integer entered by the user.
     */
    public static int takeInputAndValidateInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String input = Input.takeInputStr();
            if (isInt(input)) {
                return Integer.parseInt(input);
            } else {
                Output.output("Invalid input. Please try again");
            }
        }
    }
}
