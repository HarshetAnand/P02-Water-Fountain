//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P02 Water Fountain
// Course: CS 300 Spring 2022
//
// Author: Harshet Anand
// Email: hanand2@wisc.edu
// Lecturer: Mouna Kacem
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////


/**
 * This class allows arrays as well as the use of the Utility class to create a fountain that runs
 * continuously. It involves developing a graphical implementation of a particle system and uses
 * several small images or shapes that together give the appearance of a large amorphous object that
 * does not necessarily have a clearly defined surface. The Graphical User Interface (GUI) of this
 * assignment will be written using the java processing graphic library. Additionally it works with
 * callback methods and the response to mouse and key input and testing couple of methods from this
 * class.
 * 
 * @author Harshet Anand
 */


import java.util.Random;
import java.io.File;
import processing.core.PImage;

public class Fountain {

  private static Random randGen;
  private static PImage fountainImage;
  private static int positionX;
  private static int positionY;
  private static float velocityX;
  private static float velocityY;
  private static Droplet[] droplets;
  private static int startColor;
  private static int endColor;

  
  /**
   * The main method runs all the methods and tests in this class using runApplication.
   * 
   * @param args
   */
  public static void main(String[] args) {
    Utility.runApplication();
  }


  /**
   * This method first runs the updateDroplet and removeOldDroplets tests to see if those methods
   * have successfully passed. PositionX and PositionY as well as the startColor of the droplet and
   * endColor of the droplet have been given their values in this method as well. This method also
   * loads the image of the fountain and sets an array of 800 droplets to run each loop.
   */
  public static void setup() {
    testUpdateDroplet();
    testRemoveOldDroplets();

    randGen = new Random();
    positionX = Utility.width() / 2;
    positionY = Utility.height() / 2;
    startColor = Utility.color(23, 141, 235); // Blue
    endColor = Utility.color(23, 200, 255); // Light blue

    // Loads the image of the fountain
    fountainImage = Utility.loadImage("images" + File.separator + "fountain.png");
    droplets = new Droplet[800];
  }


  /**
   * This method sets the colour of the background, adds the default position of the fountain, and
   * calls both the updateDroplet and createNewDroplets methods each time. After every ten drops,
   * the value begins from one again and continuously loops each time whilst updating the droplet
   * each time which then removes the old droplets from the screen.
   */
  public static void draw() {
    Utility.background(Utility.color(253, 245, 230)); // Light yellow
    Utility.image(fountainImage, positionX, positionY);
    createNewDroplets(10);

    for (int j = 0; j < droplets.length; j++) {
      if (!(droplets[j] == null)) {
        updateDroplet(j);
      }
    }
    removeOldDroplets(80);
  }


  /**
   * This method allows several droplets to be created, updated, moved, and removed as well as
   * change the colour and the transparency of each droplet from our water fountain through our
   * draw() method. This method is tested in the testUpdateDroplet() method.
   * 
   * @param index represents the index of a specific droplet within the array
   */
  private static void updateDroplet(int index) {
    droplets[index].setVelocityX(droplets[index].getVelocityX());
    droplets[index].setVelocityY(droplets[index].getVelocityY());
    droplets[index].setVelocityY(droplets[index].getVelocityY() + 0.3f);
    droplets[index].setAge(droplets[index].getAge() + 1);
    droplets[index].setPositionX(droplets[index].getPositionX() + droplets[index].getVelocityX());
    droplets[index].setPositionY(droplets[index].getPositionY() + droplets[index].getVelocityY());

    Utility.fill(Utility.color(23, 141, 235), droplets[index].getTransparency());
    Utility.circle(droplets[index].getPositionX(), droplets[index].getPositionY(),
        droplets[index].getSize());
  }


  /**
   * This method creates new droplets from the fountain to be output on the screen by stepping
   * through indexes of the droplets array. Once the new droplets have been created, the
   * dropletsCount will be incremented after each droplet and each droplet has a range for their
   * position, velocity, size, and transparency.
   * 
   * @param numberOfNewDroplets Creates the specified number of new droplets and stores references
   *                            to them within the droplets array
   */
  private static void createNewDroplets(int numberOfNewDroplets) {
    int dropletsCount = 0;

    for (int j = 0; j < droplets.length; j++) {
      if ((dropletsCount < numberOfNewDroplets) && (droplets[j] == null)) {
        droplets[j] = new Droplet();
        dropletsCount++;
        droplets[j].setPositionX(positionX + randGen.nextFloat() * (6.0f - 3.0f));
        droplets[j].setPositionY(positionY + randGen.nextFloat() * (6.0f - 3.0f));
        droplets[j].setSize(4.0f + randGen.nextFloat() * (11.0f - 4.0f));
        droplets[j].setColor(Utility.lerpColor(startColor, endColor, randGen.nextFloat()));
        droplets[j].setVelocityX(-1.0f + randGen.nextFloat() * (1.0f + 1.0f));
        droplets[j].setVelocityY(-10.0f + randGen.nextFloat() * (-5.0f + 10.0f));
        droplets[j].setAge(randGen.nextInt(40));
        droplets[j].setTransparency(randGen.nextInt(127 - 32) + 32);
      }
    }
  }


  /**
   * This method removes old droplets and replaces them with new ones. Each time the updateDroplet()
   * method is called on its index, the age is incremented by one in this method.
   * 
   * @param maxAge Searches through the droplets array for references to droplets with an age that
   *               is greater than the specified max age and remove them with the max age set to 80.
   */
  private static void removeOldDroplets(int maxAge) {
    for (int j = 0; j < droplets.length; j++) {
      if (!(droplets[j] == null)) {
        if (maxAge < droplets[j].getAge()) {
          droplets[j] = null;
        }
      }
    }
  }


  /**
   * This method moves the position of the X and Y which will cause the fountain to move to that
   * respective position of where the mouse has been clicked on the screen.
   */
  public static void mousePressed() {
    positionX = Utility.mouseX();
    positionY = Utility.mouseY();
  }


  /**
   * This method checks if a key has been pressed. The only valid keys pressed are lower-case 's'
   * and capital 's'. Pressing the key 's' will take a screenshot of the fountain and save this as a
   * .png file in the images folder after refreshing the project folder.
   * 
   * @param keyPressed lower and upper case 's' assigned to keyPressed as any other character will
   *                   not take a screenshot of fountain but only the character 's' will.
   */
  public static void keyPressed(char keyPressed) {
    if (keyPressed == 's' || keyPressed == 'S') {
      Utility.save("screenshot.png");
    }
  }


  /**
   * This tester initializes the droplets array to hold at least three droplets. Creates a single
   * droplet at position (3,3) with velocity (-1,-2). Then checks whether calling updateDroplet() on
   * this droplets index correctly results in changing its position to (2.0, 1.3).
   *
   * @return true when no defect is found, and false otherwise
   */
  private static boolean testUpdateDroplet() {
    droplets = new Droplet[3];

    for (int j = 0; j < droplets.length; j++) {
      droplets[j] = new Droplet();
    }

    droplets[0].setPositionX(3);
    droplets[0].setPositionY(3);
    droplets[0].setVelocityX(-1);
    droplets[0].setVelocityY(-2);

    updateDroplet(0);

    if (droplets[0].getPositionX() == 2.0f && droplets[0].getPositionY() == 1.3f) {
      System.out.println("updateDroplet() method passed.");
      return true;
    }
    return false;
  }


  /**
   * This tester initializes the droplets array to hold at least three droplets. Calls
   * removeOldDroplets(6) on an array with three droplets (two of which have ages over six and
   * another that does not). Then checks whether the old droplets were removed and the young droplet
   * was left alone.
   *
   * @return true when no defect is found, and false otherwise
   */
  private static boolean testRemoveOldDroplets() {
    droplets = new Droplet[4];

    for (int j = 0; j < droplets.length; j++) {
      droplets[j] = new Droplet();
    }

    droplets[0].setAge(3);
    droplets[1].setAge(12);
    droplets[2].setAge(1);
    droplets[3].setAge(25);

    removeOldDroplets(6);

    if (!(droplets[0] == null && droplets[2] == null && droplets[1] != null
        && droplets[3] != null)) {
      System.out.println("removeOldDroplets() method passed.");
      return true;
    }
    return false;
  }
}
