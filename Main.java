// Hangman Game by Suraj Vaghela

// Import
import java.util.*;
import java.io.*;

// This class is the main class that is executed when the program is run.
// This class starts the game and asks the user if they want to play again
class Main {
  public static void main(String[] args) throws FileNotFoundException {

    boolean start = true;

    // Start the game when they run the program
    while (start)
    {
      Map<Integer, String> map = new HashMap<Integer, String>();

      // Read user input and file
      Scanner console = new Scanner(System.in);
      Scanner parse = new Scanner(new File("dictionary.txt"));

      int random = 0;
      int iterator = 0;
      String word = "";
  
      PlayGame game = new PlayGame(start, random, iterator, word, console, parse, map);
    
    // Run the game
      game.play();
    
      // Ask to play the game again
      System.out.print("Would you like to play again? ");
      String playAgain = console.next();
      if(!(playAgain.equalsIgnoreCase("y") || playAgain.equalsIgnoreCase("yes")))
      {
        start = false;
        console.close();
        parse.close();
      }
    }
    System.exit(0);
  }
}
