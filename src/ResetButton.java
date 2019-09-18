import processing.core.PApplet;

public class ResetButton {

  protected PApplet processing;
  private int width = 150;
  private int height = 30;
  private float positionX; // Center x position.
  private float positionY; // Center y position.
  private boolean containX;
  private boolean isActive;

  public ResetButton(PApplet processing, float x, float y) {
    this.processing = processing;
    this.positionX = x;
    this.positionY = y;
    this.containX = false;
    this.isActive = false;
  }

  public void draw() {
    this.processing.stroke(0); // Set line value to black.
    this.processing.strokeWeight(1); // Set line width to 1.
    if (this.isMouseOver())
      this.processing.fill(150); // Set the fill color to dark gray if the mouse is over the option.
    else
      this.processing.fill(255); // White
    // Draw the button (rectangle with a centered text)
    this.processing.rect(this.positionX - this.width / 2.0f, this.positionY - this.height / 2.0f,
        this.positionX + this.width / 2.0f, this.positionY + this.height / 2.0f);
    this.processing.fill(0);
    this.processing.text("Reset game", 75, 15);
  }

  public boolean isMouseOver() {
    if (this.processing.mouseX > (this.positionX - (this.width / 2.0f))
        && this.processing.mouseX < (this.positionX + (this.width / 2.0f))
        && this.processing.mouseY > (this.positionY - (this.height / 2.0f))
        && this.processing.mouseY < (this.positionY + (this.height / 2.0f))) {
      return true; // Mouse is over this option.
    }
    return false; // Mouse is not over this option.
  }

  public float getPositionX() {
    return this.positionX;
  }

  public float getPositionY() {
    return this.positionY;
  }

  public void setContainX(boolean containX) {
    this.containX = containX;
  }

  public boolean checkContainX() {
    return this.containX;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public boolean checkIsActive() {
    return this.isActive;
  }

}
