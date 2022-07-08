import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
public class AI {
	// To store the states that still need to be explored into a stack.
	public static LinkedHashSet<String> STACK = new LinkedHashSet<String>();
	public static LinkedHashSet<String> STACK2 = new LinkedHashSet<String>();
	// To store the states that has been visited.
	public static HashSet<String> VISITED = new HashSet<String>();
	public static HashSet<String> VISITED2 = new HashSet<String>();
	public static HashSet<String> INTERSECTION = new HashSet<String>();

	public static void main(String args[]) throws IOException{
		
		// Asks for Start state in the form of a string with 9 characters. 
		// 0 is considered to be the blank space.
		// In the 8-puzzle problem, we will represent the 9 characters in a string.
		/* 
		 * For example 123456780 will represent:
		 * 123
		 * 456
		 * 780
		 */
		String inputLine = "";
		String inputLine2 = "";
		Scanner myScanner = new Scanner(System.in);
		do {
			System.out.println("Enter the first start state in the form of a 9 digit string: ");
			inputLine = myScanner.nextLine();
			System.out.println("Enter the second start state in the form of a 9 digit string: ");
			inputLine2 = myScanner.nextLine();
		} while(!(inputLine.length() == 9) && !(inputLine2.length() == 9));
		String start = inputLine;
		String start2 = inputLine2;
	    myScanner.close();
	    
	    // We start to get all the states from the input state 1.
	    String topState = "";
	    String tempState = "";
	 // Add start to the stack.
	    STACK.add(start);
	    while (!STACK.isEmpty()) {
	    	// The stack is empty when all the states have been visited
	        topState = STACK.iterator().next();
	        STACK.remove(topState);
	        // Iterates through the stack and removes the states that have been fully explored
	        int blank = topState.indexOf('0');
	        // Gets the position of the blank space in the string.
	            // Gets all the states accessible from this state with one move.
	            VISITED.add(topState);
	            // Moves the state to visited list
	            tempState = movementUp(topState, blank);
	            if (verify(tempState) == true)
	            	// Checks if the state is not already in VISITED or STACK.
	                STACK.add(tempState);
	            tempState = movementLeft(topState, blank);
	            if (verify(tempState) == true)
	                STACK.add(tempState);
	            tempState = movementDown(topState, blank);
	            if (verify(tempState) == true)
	                STACK.add(tempState);
	            tempState = movementRight(topState, blank);
	            if (verify(tempState) == true)
	                STACK.add(tempState);
	    }
	    // Now we do the same process for the input state 2.
	    String topState2 = "";
	    String tempState2 = "";
	    STACK2.add(start2);
	    // Adds start to the stack
	    while (!STACK2.isEmpty()) {
	    	// The stack is empty when all the states have been visited
	        topState2 = STACK2.iterator().next();
	        STACK2.remove(topState2);
	        // Iterates through the stack and removes the states that have been fully explored
	        int blank2 = topState2.indexOf('0');
	        // Gets the position of the blank space in the string.
	        	// Gets all the states accessible from this state with one move.
	            VISITED2.add(topState2);
	            // Moves the state to visited list
	            tempState2 = movementUp(topState2, blank2);
	            if (verify2(tempState2) == true)
	            	// if tempState2 is not in STACK or VISITED.
	                STACK2.add(tempState2);
	            tempState2 = movementLeft(topState2, blank2);
	            if (verify2(tempState2) == true)
	                STACK2.add(tempState2);
	            tempState2 = movementDown(topState2, blank2);
	            if (verify2(tempState2) == true)
	                STACK2.add(tempState2);
	            tempState2 = movementRight(topState2, blank2);
	            if (verify2(tempState2) == true)
	                STACK2.add(tempState2);
	    }
	    // Gets the first element of VISITED
	    String firstElement = "";
	    for (String element : VISITED) {
            firstElement = element;
            break;
        }
	    // See if the first element (or any element) of VISITED is in VISITED2
	    // If it is this means that they both contain the same elements.
	    // As there exists 2 set of possible states that don't intersect.
	    if (VISITED2.contains(firstElement)){
	    	INTERSECTION = VISITED;
	    }
	 // Writing to the output file that will be created in the same directory as the program.
	    // Search for 'a.', 'b.', 'c.', 'd.', 'e.' and 'f.' in the text file for the answers.
		FileWriter outputFile;
		outputFile = new FileWriter("output.txt");
		BufferedWriter bw = new BufferedWriter(outputFile);
		try {
			// Writes VISITED into the text file.
			bw.write("a. R(S1) = \n");
			for (String state : VISITED) {
				bw.write(state + "\n");
				}
			bw.write("b. Size of R(S1) is: " + VISITED.size() + "\n");
			// Writes VISITED2 into the text file.
			bw.write("c. R(S2) = \n");
			for (String state2 : VISITED2) {
				bw.write(state2 + "\n");
				}
			bw.write("d. Size of R(S2) is: " + VISITED2.size() + "\n");
			bw.write("e. R(S1) intersection R(S2) = \n");
			// Checks if the intersection is empty, if not writes INTERSECTION into the text file.
		    if (INTERSECTION.isEmpty()) {
		    	bw.write("[] \n");
		    }else {
			for (String state3 : INTERSECTION) {
				bw.write(state3 + "\n");
				}
		    }
			bw.write("f. Size of R(S1) intersection R(S2) is: " + INTERSECTION.size());
			bw.close();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	// Function to check if state is in STACK or VISITED
	public static boolean verify(String string) {
		if (!STACK.contains(string) && !VISITED.contains(string))
			return true;
		else
			return false;
	}
	
	// Function to check if state is in STACK2 or VISITED2
		public static boolean verify2(String string) {
			if (!STACK2.contains(string) && !VISITED2.contains(string))
				return true;
			else
				return false;
		}

	 // movementUp function
	public static String movementUp(String stateString, int posBlank) {
	    String str = stateString;
	    if (posBlank >= 3) {
	    	char switched = str.charAt(posBlank - 3);
	    	// Swaps 2 characters in the string.
	    	str = str.substring(0, posBlank - 3) + "0" + str.substring(posBlank - 2, posBlank) + switched + str.substring(posBlank + 1);
	    }
	        return str;
	}

	// movementDown function
	public static String movementDown(String stateString, int posBlank) {
	    String str = stateString;
	    if (posBlank <= 5) {
	        char switched = str.charAt(posBlank + 3);
	        str = str.substring(0, posBlank) + switched + str.substring(posBlank + 1, posBlank + 3) + '0' + str.substring(posBlank + 4);}
	        return str;
	}

	// movementLeft function
	public static String movementLeft(String stateString, int posBlank) {
	    String str = stateString;
	    if (posBlank != 0 && posBlank != 3 && posBlank != 7) {
	        char switched = str.charAt(posBlank - 1);
	        str = str.substring(0, posBlank - 1) + '0' + switched + str.substring(posBlank + 1);
	    }
	        return str;
	}

	// movementRight function
	public static String movementRight(String stateString, int posBlank) {
	    String str = stateString;
	    if (posBlank != 2 && posBlank != 5 && posBlank != 8) {
	        char switched = str.charAt(posBlank + 1);
	        str = str.substring(0, posBlank) + switched + '0' + str.substring(posBlank + 2);
	    }
	        return str;
	}

}