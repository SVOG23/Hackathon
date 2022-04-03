// Import
import java.util.*;
import java.awt.*;

// This method is a helper class and it executes the various
// commands that can be used by the user
public class Helper
{
  private String guess;
  private ArrayList<String> temp2;
  private String incorrectLetters;
  private Graphics graphics;
  
  // This method executes commands typed in by the user
  public void command(DrawingPanel panel)
  {
    graphics = panel.getGraphics();
    // Exit the program if they want to exit
    if (this.guess.equalsIgnoreCase("exit")) 
    {
      System.exit(0);
    } 
  }
  // This method updates the helper class so that it can
  // display correct information
  public void update(String guess, ArrayList<String> temp2, String incorrectLetters)
  {
    this.guess = guess;
    this.temp2 = temp2;
    this.incorrectLetters = incorrectLetters;
  }
  public void printIncorrect()
  {
    // Show which incorrect letters the user has guessed already
    graphics.drawString("Incorrect letters used: ", 110, 90);
    for (int i = 0; i < temp2.size(); i++) 
    {
      incorrectLetters += temp2.get(i) + " ";
      
    }
    graphics.drawString(incorrectLetters, 255, 90);
    incorrectLetters = "";
  }
}