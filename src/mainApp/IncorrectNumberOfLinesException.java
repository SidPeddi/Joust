package mainApp;

/**
 * 
 * @author Siddarth Peddi & Tulsi Manohar prints error message when the number
 *         of lines in level text files do not meet requirements.
 */
public class IncorrectNumberOfLinesException extends Exception {
	private String message;

	IncorrectNumberOfLinesException(int totalExpected, int lines) {
		this.message = "File should have " + totalExpected + " lines, this file had " + lines + " lines.";
	}

	public String getMessage() {
		return message;

	}
}
