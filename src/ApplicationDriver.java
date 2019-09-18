import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PFont;

public class ApplicationDriver extends PApplet {
  private ArrayList<Square> table3X3;
  private PFont font;
  private int[][] winCombo;
  private boolean xRatherThanO;
  private boolean all9Active;
  private boolean gameEnds;
  private int displayTimeControl;
  private int gameResult;
  private ResetButton resetButton;

  public static void main(String[] args) {
    PApplet.main("ApplicationDriver");

  }

  @Override
  public void settings() {
    size(500, 500);
  }

  @Override
  public void setup() {
    this.rectMode(ApplicationDriver.CORNERS);
    this.font = this.createFont("Times New Roman", 14, true);
    this.textFont(this.font, 14);
    this.textAlign(ApplicationDriver.CENTER, ApplicationDriver.CENTER);

    this.table3X3 = new ArrayList<Square>();
    this.addSquare();
    this.winCombo = new int[][] {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}};

    this.fill(200);
    this.rect(-1, -1, 500, 500);
    this.fill(255);
    this.resetButton = new ResetButton(this, 75, 15);
    this.resetButton.draw();

    this.xRatherThanO = true;
    this.all9Active = false;
    this.gameEnds = false;
    this.displayTimeControl = 0;
    this.gameResult = -1;
  }

  @Override
  public void draw() {
    this.textFont(this.font, 14);
    this.resetButton.draw();
    if (this.gameEnds) {
      this.displayTimeControl++;
    }
    if (this.displayTimeControl < 150) {
      for (int i = 0; i < this.table3X3.size(); i++) {
        this.table3X3.get(i).draw();
      }
      this.activatedSquare();
    }
    if (!this.gameEnds) {
      this.gameResult = this.checkWin();
    }
    if (this.displayTimeControl >= 150) {
      this.fill(200);
      this.rect(-1, -1, 500, 500);
      this.fill(0);
      this.textFont(this.font, 14);
      this.resetButton.draw();
      this.textFont(this.font, 25);
      if (this.gameResult == 0) {
        this.text("Congratulations! Player X wins!!!", 250, 250);
      } else if (this.gameResult == 1) {
        this.text("Congratulations! Player O wins!!!", 250, 250);
      } else if (this.gameResult == 2) {
        this.text("Deadlock, none of the player wins", 250, 250);
      }
    }

  }

  public void addSquare() {
    this.table3X3.add(new Square(this, 150, 150));
    this.table3X3.add(new Square(this, 250, 150));
    this.table3X3.add(new Square(this, 350, 150));
    this.table3X3.add(new Square(this, 150, 250));
    this.table3X3.add(new Square(this, 250, 250));
    this.table3X3.add(new Square(this, 350, 250));
    this.table3X3.add(new Square(this, 150, 350));
    this.table3X3.add(new Square(this, 250, 350));
    this.table3X3.add(new Square(this, 350, 350));
  }

  public void activatedSquare() {
    fill(0);
    this.textFont(this.font, 80);
    for (int i = 0; i < this.table3X3.size(); i++) {
      if (this.table3X3.get(i).checkIsActive()) {
        float positionX = this.table3X3.get(i).getPositionX(); // Get x center.
        float positionY = this.table3X3.get(i).getPositionY(); // Get y center.
        if (this.table3X3.get(i).checkContainX()) {
          this.text("X", positionX, positionY);
        } else {
          this.text("O", positionX, positionY);
        }
      }
    }
  }

  public void mousePressed() {
    if (this.resetButton.isMouseOver()) {
      this.setup();
      this.draw();
    }
    if (!this.gameEnds) {
      for (int i = 0; i < this.table3X3.size(); i++) {
        if (this.table3X3.get(i).isMouseOver()) { // Only if mouse is over the square.
          if (!this.table3X3.get(i).checkIsActive()) { // Only activate when not active yet.
            this.table3X3.get(i).setIsActive(true); // Change to active.
            // float positionX = this.table3X3.get(i).getPositionX(); // Get x center.
            // float positionY = this.table3X3.get(i).getPositionY(); // Get y center.
            if (this.xRatherThanO) {
              // this.text("X", positionX, positionY);
              this.table3X3.get(i).setContainX(true);
              this.xRatherThanO = false;
            } else {
              // this.text("O", positionX, positionY);
              this.table3X3.get(i).setContainX(false);
              this.xRatherThanO = true;
            }
          }
        }
      }
    }
  }

  public int checkWin() {
    if (!this.gameEnds) {
      for (int i = 0; i < this.winCombo.length; ++i) {
        int firstSquare = this.winCombo[i][0];
        int secondSquare = this.winCombo[i][1];
        int thirdSquare = this.winCombo[i][2];
        boolean all3Active = this.table3X3.get(firstSquare).checkIsActive()
            && this.table3X3.get(secondSquare).checkIsActive()
            && this.table3X3.get(thirdSquare).checkIsActive();
        if (all3Active) {
          boolean allSameX = this.table3X3.get(firstSquare).checkContainX()
              && this.table3X3.get(secondSquare).checkContainX()
              && this.table3X3.get(thirdSquare).checkContainX();
          boolean allSameO = !this.table3X3.get(firstSquare).checkContainX()
              && !this.table3X3.get(secondSquare).checkContainX()
              && !this.table3X3.get(thirdSquare).checkContainX();
          if (allSameX) {
            this.gameEnds = true;
            return 0;
          } else if (allSameO) {
            this.gameEnds = true;
            return 1;
          }
        }
      }
      for (int i = 0; i < this.table3X3.size(); ++i) {
        this.all9Active = this.table3X3.get(i).checkIsActive();
        if (!this.all9Active) {
          break;
        }
        if (i == 8) {
          this.gameEnds = true;
          return 2;
        }
      }
    }
    return -1;
  }
}
