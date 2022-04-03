// Import
import java.util.*;
import java.awt.*;

public class PlayGame {
  private boolean start;
  private int random;
  private int iterator;
  private String word;
  private Scanner console;
  private Scanner parse;
  private Map<Integer, String> map;
  private String top = "|---------+";
  private String pole1 = "|         |";
  private String pole2 = "|         ";
  private String pole3 = "|        ";
  private String pole4 = "|         ";
  private String pole5 = "|        ";
  private String bottom = "-";
  private int hangMan = 0;
  private String guess = "";
  private DrawingPanel panel = new DrawingPanel(400,200);
	private Graphics graphics = panel.getGraphics();
  private Helper help = new Helper();
  private ArrayList<String> temp2 = new ArrayList<String>();
  private ArrayList<String> checker = new ArrayList<String>();
  private String incorrectLetters = "";

  public PlayGame(boolean start, int random, int iterator, String word, Scanner console, Scanner parse, Map<Integer, String> map) {
    this.start = start;
    this.random = random;
    this.iterator = iterator;
    this.word = word;
    this.console = console;
    this.parse = parse;
    this.map = map;
  }

  // This method, when called, runs the game
  public void play() 
  {
    // Play the game
    while (start) 
    {
      // Get the random number
      random = (int) ((Math.random() * 449) + 1);
      iterator = 0;
      boolean finding = true;

      // Generate the random word from the dictionary
      try 
      {
        while (finding) 
        {
          if (iterator == random) 
          {
            word = parse.nextLine().toLowerCase();
            finding = false;
          } 
          else 
          {
            iterator++;
            parse.nextLine();
          }
        }
      } catch (NoSuchElementException e) {
        System.out.println("Error occurred...\nClosing Program");
        System.exit(0);
      }
      // Put each letter in the word in the map
      for (int i = 0; i < word.length(); i++) 
      {
        map.put(i, word.charAt(i) + "");
      }

      // Make and array of dashes to be printed out so the user can visually see
      // how many letters long the word is 
      String[] arr = new String[word.length()];
      for (int i = 0; i < arr.length; i++) 
      {
        arr[i] = "_";
      }

      // Print out the help instructions
      graphics.drawString("Type either a letter or the word", 110, 30);
      graphics.drawString("Type \"exit\" to exit out of the game", 110, 45);

      printMan();
      printArray(arr);
      
      boolean correct = false;
      
      // Continue to ask the user for input until they guess the word or letters correctly
      while (!correct) 
      {
        // Get user input
        System.out.print("\nYour guess: ");
        this.guess = console.nextLine();
        
        boolean valid = false;
        while (!valid)
        {
          if(this.guess.matches("[a-zA-Z]{1,}"))
          {
            this.guess = guess.toLowerCase();
            valid = true;
          }
          else
          {
            System.out.print("Enter a valid input: ");
            this.guess = console.nextLine();
          }
        }

        // Break out if the user guesses the full word
        if (guess.equalsIgnoreCase(word)) 
        {
          for(int i = 0; i < word.length(); i++)
          {
            if(!arr[i].equalsIgnoreCase(word.charAt(i) + ""))
            {
                arr[i] = word.charAt(i) + "";
            }
            printArray(arr);
          }
          break;
        }
        // If the typed a word as a guess
        else if (guess.length() > 1)
        {
          // Make sure it's not a command
          if(!guess.equalsIgnoreCase("exit"))
          {
            // Don't penalize or add to the hangman for repeat words
            if(!temp2.contains(guess))
            {
              temp2.add(guess);
              hangMan++;
              correct = buildMan();
              if(correct == false)
              {
                printMan();
                System.out.println("\nBooooo you didn't get the word!");
                break;
              }
            }
            // Tell them they've already guessed the word
            else
            {
              System.out.println("\nYou have already guessed that word!");
            }
          }
        }
        // Compare the guess to the word and see if it contains the same letter
        // If it does, then replace the dash in the array with the letter at the correct location(s)
        if(guess.length() == 1)
        {
          if(map.containsValue(guess))
          {
            // Make sure it isn't a guess they've already guessed correctly
            if(!checker.contains(guess))
            { 
              for (int i = 0; i < word.length(); i++) 
              {
                if (guess.equalsIgnoreCase(map.get(i))) 
                {
                  arr[i] = guess;
                  checker.add(guess);
                }
              }
            }
            // Tell them they've already guessed that letter correctly
            else
            {
              System.out.println("\nYou've already guessed that letter correctly!");
            }
          }
          // If it's not a correct letter guess, then add it to the incorrect letters
          // If they haven't already guessed it
          else if (!temp2.contains(guess))
          {
            temp2.add(guess);
            hangMan++;
            correct = buildMan();
            if(correct == false)
            {
              printMan();
              System.out.println("\nBooooo you didn't get the word!");
              break;
            }
          }
          // Tell them they've already guessed that letter
          else
          {
            System.out.println("\nYou have already guessed that letter!");
          }
        }
        // Check if the users guess(es) match the word generated randomly by the computer
        correct = checkCorrect(arr,  word);
        System.out.println();
        printMan();
        printArray(arr);
        
        // Updates the helper class and checks for any commands used
        help.update(this.guess, temp2, incorrectLetters);
        help.command(panel);
        help.printIncorrect();
        
      }
      // Print out end statements
      if(checkCorrect(arr, word) || guess.equalsIgnoreCase(word))
      {
        System.out.println("\nYou got it!");
      }
      graphics.drawString("\nThe word was: " + word, 110, 120);
      
      start = false;
    }
  }

  // This method prints the array so that the user can see which letters
  // have and have not been guessed
  public void printArray(String[] arr) 
  {
    int count = 0;
    for (int i = 0; i < arr.length; i++) 
    {
      this.graphics.drawString(arr[i] + " ", 10 + count, 110);
      count += 10;
      
    }
  }
  // This method starts adding body parts to the hangman if the player guesses incorrectly
  // Once the man is complete, it will end the program with the lose message
  public boolean buildMan()
  {
    // Don't add a body part for using a command
    if(guess.equalsIgnoreCase("exit"))
    {
      hangMan--;
    }
    else
    {
      if(this.hangMan == 1)
      {
        this.pole2 += "O";
      }
      else if(this.hangMan == 2)
      {
        this.pole3 += "/";
      }
      else if(this.hangMan == 3)
      {
        this.pole3 += "|";
      }
      else if(this.hangMan == 4)
      {
        this.pole3 += "\\";
      }
      else if(this.hangMan == 5)
      {
        this.pole4 += "|";
      }
      else if(this.hangMan == 6)
      {
        this.pole5 += "/";
      }
      else if(this.hangMan == 7)
      {
        this.pole5 += " \\";
        return false;
      }
    }
    return true;
  }
  // This method prints out the hangman
  public void printMan()
  {
    
    this.graphics.drawString(this.top, 10, 30);
    this.graphics.drawString(this.pole1, 10, 40);
    this.graphics.drawString(this.pole2, 10, 50);
    this.graphics.drawString(this.pole3, 10, 60);
    this.graphics.drawString(this.pole4, 10, 70);
    this.graphics.drawString(this.pole5, 10, 80);
    this.graphics.drawString(this.bottom, 10, 90);
    
  }

  // This method checks if the guess(es) match the computer generated word
  public static boolean checkCorrect(String[] arr, String word) 
  {
    String temp = "";
    for (int i = 0; i < arr.length; i++) 
    {
      temp += arr[i];
    }
    if (temp.equalsIgnoreCase(word)) 
    {
      return true;
    }
    return false;
  }
}