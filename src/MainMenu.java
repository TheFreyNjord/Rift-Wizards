/**
 * This program fuctions as a main menu for Rift Wizards
 * Author: Seannace Wilkins
 * Date: 11/21/2020
 */
import java.awt.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Create a JavaFX Stage for the Main Menu of the Game
 * 
 * @author Seannace Wilkins
 *
 */
public class MainMenu extends Application {
	
	// Creates a Gamestate object to be used when the game is running
	Gamestate play;
	
	// Create the stage (canvas)
	@Override
	public void start(Stage stage) {
		
		stage .setTitle("Rift Wizards");
		
		// Create the StackPane that will be used later
		StackPane pane;
		
		// Create variable for the dimensions of the screen to assist in portability between screen resolutions
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		// Create variable for the movement speed of players in pixels
		double move = 5;
		
		// Create variables for the width and height of the game that takes up a square 
		// slightly smaller than the height of the user's screen
		double height = screenSize.getHeight() / 1.1;
		double width = screenSize.getHeight() / 1.1;
		
		// Create variables used for resizing and moving buttons later based on the size of the user's screen
		double scaleButtonX = ((double)724 / 1080) * width;
		double scaleButtonY = ((double)231 / 1080) * height;
		double moveButtonY = ((double)380 / 1080) * height;
		
		// Create the image for the background of the game and adjust it to look good
		ImageView background = new ImageView("file:///../assets/Main Menu.png");
		
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setSaturation(0.3);
		Light.Spot light = new Light.Spot();
		light.setX(width / 2);
		light.setY(0);
		light.setZ(3000);
		light.setPointsAtX(width / 2);
		light.setPointsAtY(height);
		light.setPointsAtZ(0);
		light.setSpecularExponent(1);
		Lighting lighting = new Lighting();
		lighting.setLight(light);
		lighting.setSurfaceScale(5.0);
		colorAdjust.setInput(lighting);
		
		background.setPreserveRatio(true);
		background.setFitHeight(height);
		background.setFitWidth(width);
		background.setEffect(colorAdjust);
		
		// Sets the background to not see mouse events so that the button behind the background can see the mouse events
		background.setMouseTransparent(true);
		
		// Create the image for the start button on the main menu and use prior adjustments to improve quality
		ImageView button1 = new ImageView("file:///../assets/Start Button.png");
		button1.setPreserveRatio(true);
		button1.setFitHeight(scaleButtonY);
		button1.setFitWidth(scaleButtonX);
		button1.setEffect(colorAdjust);
		
		// Create listeners to change the button between hovered and unhovered mode
		button1.hoverProperty().addListener((event)-> {
			if (button1.isHover())
			{
				button1.toFront();
			}
			else
			{
				button1.toBack();
			}
		});
		
		// Fill the StackPane with the elements needed, then put them in proper positions
		pane = new StackPane(button1, background);
		pane.getChildren().get(0).setTranslateY(pane.getChildren().get(0).getTranslateY() + moveButtonY);
		
		// Add the background to the scene at location
		Scene scene = new Scene(pane, width, height);
		stage.setScene(scene);
		stage.show();
		
		// Creates an Animation timer that lets the game run by reading controls and any object collision
		AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	boolean canCast = true;
            	
                int p1x = 0, p1y = 0;
                int p2x = 0, p2y = 0;
                
                double p1bx = play.getPane().getChildren().get(2).getBoundsInLocal().getWidth()  / 2;
                double p1by = play.getPane().getChildren().get(2).getBoundsInLocal().getHeight() / 2;
                double p2bx = play.getPane().getChildren().get(3).getBoundsInLocal().getWidth()  / 2;
                double p2by = play.getPane().getChildren().get(3).getBoundsInLocal().getHeight() / 2;
                
                if (play.getP1Up()) p1y -= move;
                if (play.getP1Down()) p1y += move;
                if (play.getP1Right())  p1x += move;
                if (play.getP1Left())  p1x -= move;
                if (play.getP2Up()) p2y -= move;
                if (play.getP2Down()) p2y += move;
                if (play.getP2Right())  p2x += move;
                if (play.getP2Left())  p2x -= move;
                
                for (int i = 0; i < play.getPane().getChildren().size(); i++)
    			{
    				if (play.getPane().getChildren().get(i).getClass().getName().equals("SpellImage"))
    				{
    					((SpellImage)play.getPane().getChildren().get(i)).update();
    				}
    			}
                
                if (p1x != 0 || p1y != 0 || p2x != 0 || p2y != 0)
                {
                	double x1 = p1bx + play.getPane().getChildren().get(2).getLayoutX() + p1x;
                	double y1 = p1by + play.getPane().getChildren().get(2).getLayoutX() + p1y;
                	double x2 = p2bx + play.getPane().getChildren().get(3).getLayoutX() + p2x;
                	double y2 = p2by + play.getPane().getChildren().get(3).getLayoutX() + p2y;
                	if (x1 - p1bx >= 0 &&
                            x1 + p1bx <= width &&
                            y1 - p1by >= 0 &&
                            y1 + p1by <= height) {
                			play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() + p1x);
                			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() + p1y);
                		}
                	if (x2 - p2bx >= 0 &&
                            x2 + p2bx <= width &&
                            y2 - p2by >= 0 &&
                            y2 + p2by <= height) {
                			play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() + p2x);
                			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() + p2y);
                		}
                	}
                // Detects for various collision cases
                boolean collisionDetected = false;
            	for (int i = 2; i < play.getPane().getChildren().size(); i++)
            	{
            		for (int j = i + 1; j < play.getPane().getChildren().size(); j++)
            		{
            			Node n = play.getPane().getChildren().get(i); 
                		Node m = play.getPane().getChildren().get(j);
                		if (n.getBoundsInParent().intersects(m.getBoundsInParent())) 
                		{
                			collisionDetected = true;
                	      }
                		else
                		{
                			collisionDetected = false;
                		}
                		if (collisionDetected)
                		{
                			// Detects if players collide with each other
                			if (i == 2 && j == 3)
                			{
                				canCast = false;
                				// Both players are moving diagonally towards each other
                				if (p1x != 0 && p1y != 0 && p2x != 0 && p2y != 0)
                				{
                					play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                        			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                					play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                        			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                				}
                				// P1 is moving diagonally, P2 is stationary
                				else if (p1x != 0 && p1y != 0 && p2x == 0 && p2y == 0)
                				{
                					play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                        			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);	
                				}
                				// P2 is moving diagonally, P1 is stationary
                				else if (p1x == 0 && p1y == 0 && p2x != 0 && p2y != 0)
                				{
                					play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                        			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);	
                				}
                				// P1 is moving diagonally, P2 is moving horizontally
                				else if (p1x != 0 && p1y != 0 && p2x != 0 && p2y == 0)
                				{
                					play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                        			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                        			play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                				}
                				// P2 is moving diagonally, P1 is moving horizontally
                				else if (p1x != 0 && p1y == 0 && p2x != 0 && p2y != 0)
                				{
                					play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                        			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                        			play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                				}
                				// P1 is moving diagonally, P2 is moving vertically
                				else if (p1x != 0 && p1y != 0 && p2x == 0 && p2y != 0)
                				{
                					play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                        			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                        			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                				}
                				// P2 is moving diagonally, P1 is moving vertically
                				else if (p1x == 0 && p1y != 0 && p2x != 0 && p2y != 0)
                				{
                					play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                        			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                        			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                				}
                				// Both players are moving horizontally
                				else if (p1x != 0 && p2x != 0)
                				{
                					play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                        			play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                				}
                				// Both players are moving vertically
                				else if (p1y != 0 && p2y != 0)
                				{
                        			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                        			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                				}
                				// P1 is moving horizontally, P2 is stationary
                				else if (p1x != 0 && p2y == 0 && p2x == 0)
                				{
                        			play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                				}
                				// P2 is moving horizontally, P1 is stationary
                				else if (p2x != 0 && p1y == 0 && p1x == 0)
                				{
                					play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                				}
                				// P1 is moving vertically, P2 is stationary
                				else if (p1y != 0 && p2y == 0 && p2x == 0)
                				{
                        			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                				}
                				// P2 is moving vertically, P1 is stationary
                				else if (p2y != 0 && p1y == 0 && p1x == 0)
                				{
                        			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y); 
                				}
                				// P1 is moving vertically, P2 is moving horizontally
                				else if (p1y != 0 && p2x != 0)
                				{
                        			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                        			play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                				}
                				// P1 is moving horizontally, P2 is moving vertically
                				else if (p2y != 0 && p1x != 0)
                				{
                        			play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                        			play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                				}
                			}
                			// Detects if a player collides with an outer wall
                			if ((i == 2 && (j >= 4 && j <= 7)) || (i == 3 && (j >= 4 && j <= 7)))
                			{
                				if (i == 2)
                				{
                					if (j == 4)
                					{
                						play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                					}
                					if (j == 5)
                					{
                						play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                					}
                					if (j == 6)
                					{
                            			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                					}
                					if (j == 7)
                					{
                            			play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                					}
                				}
                				if (i == 3)
                				{
                    				if (j == 4)
                    				{
                    					play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                    				}
                    				if (j == 5)
                    				{
                    					play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                    				}
                    				if (j == 6)
                    				{
                    					play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                    				}
                    				if (j == 7)
                    				{
                    					play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                    				}
                				}
                			}
                			// Detects if a player collides with an inner wall
                			if ((i == 2 && (j >= 8 && j <= 15) || (i == 3 && (j >= 8 && j <= 15))))
                			{
                				if (i == 2)
                				{
                					if (p1x != 0 && p1y != 0)
                					{
                						play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                						play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                					}
                					else if (p1x != 0)
                					{
                						play.getPane().getChildren().get(2).setTranslateX(play.getPane().getChildren().get(2).getTranslateX() - p1x);
                					}
                					else if (p1y != 0)
                					{
                						play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                					}
                				}
                				if (i == 3)
                				{
                					if (p2x != 0 && p2y != 0)
                					{
                						play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                						play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                					}
                					else if (p2x != 0)
                					{
                						play.getPane().getChildren().get(3).setTranslateX(play.getPane().getChildren().get(3).getTranslateX() - p2x);
                					}
                					else if (p2y != 0)
                					{
                						play.getPane().getChildren().get(3).setTranslateY(play.getPane().getChildren().get(3).getTranslateY() - p2y);
                					}
                				}
                			}
                			if ((i >= 4 && i <= 15) && play.getPane().getChildren().get(j).getClass().getName().equals("SpellImage"))
                			{
                				if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("FB"))
                				{
                					play.getPane().getChildren().remove(j);
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("EB"))
                				{
                					play.getPane().getChildren().remove(j);
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("AP") && (i >=4 && i <= 7))
                				{
                					play.getPane().getChildren().remove(j);
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("WC"))
                				{
                					play.getPane().getChildren().remove(j);
                				}
                			}
                			if (i == 2 && play.getPane().getChildren().get(j).getClass().getName().equals("SpellImage"))
                			{
                				if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("FB"))
                				{
                					if (((SpellImage)play.getPane().getChildren().get(j)).getPlayer().equals("2"))
                					{
                						play.getPane().getChildren().remove(j);
                    					play.playerDeath(i);
                					}
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("EB"))
                				{
                					if (((SpellImage)play.getPane().getChildren().get(j)).getPlayer().equals("2"))
                					{
                						play.getPane().getChildren().remove(j);
                    					play.playerDeath(i);
                					}
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("AP"))
                				{
                					if (((SpellImage)play.getPane().getChildren().get(j)).getPlayer().equals("2"))
                					{
                						play.getPane().getChildren().remove(j);
                						play.getPane().getChildren().get(i).setTranslateX(play.getPane().getChildren().get(i).getTranslateX() - p1x);
                						play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                					}
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("WC"))
                				{
                					if (((SpellImage)play.getPane().getChildren().get(j)).getPlayer().equals("2"))
                					{
                						play.getPane().getChildren().remove(j);
                					}
                				}
                			}
                			if (i == 3 && play.getPane().getChildren().get(j).getClass().getName().equals("SpellImage"))
                			{
                				if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("FB"))
                				{
                					if (((SpellImage)play.getPane().getChildren().get(j)).getPlayer().equals("1"))
                					{
                						play.getPane().getChildren().remove(j);
                    					play.playerDeath(i);
                					}
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("EB"))
                				{
                					if (((SpellImage)play.getPane().getChildren().get(j)).getPlayer().equals("1"))
                					{
                						play.getPane().getChildren().remove(j);
                    					play.playerDeath(i);
                					}
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("AP"))
                				{
                					if (((SpellImage)play.getPane().getChildren().get(j)).getPlayer().equals("1"))
                					{
                						play.getPane().getChildren().remove(j);
                						play.getPane().getChildren().get(i).setTranslateX(play.getPane().getChildren().get(i).getTranslateX() - p1x);
                						play.getPane().getChildren().get(2).setTranslateY(play.getPane().getChildren().get(2).getTranslateY() - p1y);
                					}
                				}
                				else if (((SpellImage)play.getPane().getChildren().get(j)).getType().equals("WC"))
                				{
                					if (((SpellImage)play.getPane().getChildren().get(j)).getPlayer().equals("1"))
                					{
                						play.getPane().getChildren().remove(j);
                					}
                				}
                			}
                		}
            		}
            	}
            	// Creating the Spell Graphics on the Scene
            	if (canCast)
            	{
            		if (play.getP1F())
            		{
            			if (play.getP1Att())
            			{
            				if (p1x > 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "DR", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "DL", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x > 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "UR", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "UL", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "R", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "L", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "D", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "U", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "R", "FB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            			}
            			else if (play.getP1Def())
            			{
            				if (p1x > 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "DR", "FW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY() - 40));
            				}
            				else if (p1x < 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "DL", "FW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY() - 40));
            				}
            				else if (p1x > 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "UR", "FW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY() + 40));
            				}
            				else if (p1x < 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "UL", "FW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY() + 40));
            				}
            				else if (p1x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "R", "FW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "L", "FW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "D", "FW", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY() - 40));
            				}
            				else if (p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "U", "FW", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY() + 40));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "R", "FW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY()));
            				}
            			}
            		}
            		else if (play.getP1E())
            		{
            			if (play.getP1Att())
            			{
            				if (p1x > 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "DR", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "DL", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x > 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "UR", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "UL", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "R", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "L", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "D", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "U", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "R", "EB", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            			}
            			else if (play.getP1Def())
            			{
            				if (p1x > 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DR", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 20, play.getPane().getChildren().get(2).getTranslateY() + 60));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DR", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY() + 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DR", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 60, play.getPane().getChildren().get(2).getTranslateY() + 20));
            				}
            				else if (p1x < 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DL", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 20, play.getPane().getChildren().get(2).getTranslateY() + 60));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DL", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY() + 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DL", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 60, play.getPane().getChildren().get(2).getTranslateY() + 20));
            				}
            				else if (p1x > 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UR", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 20, play.getPane().getChildren().get(2).getTranslateY() - 60));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UR", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY() - 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UR", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 60, play.getPane().getChildren().get(2).getTranslateY() - 20));
            				}
            				else if (p1x < 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UL", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 20, play.getPane().getChildren().get(2).getTranslateY() - 60));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UL", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY() - 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UL", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 60, play.getPane().getChildren().get(2).getTranslateY() - 20));
            				}
            				else if (p1x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY() - 20));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY()));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY() + 20));
            				}
            				else if (p1x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY() - 20));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY()));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 40, play.getPane().getChildren().get(2).getTranslateY() + 20));
            				}
            				else if (p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "D", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 20, play.getPane().getChildren().get(2).getTranslateY() + 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "D", "EW", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY() + 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "D", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 20, play.getPane().getChildren().get(2).getTranslateY() + 40));
            				}
            				else if (p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "U", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() - 20, play.getPane().getChildren().get(2).getTranslateY() - 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "U", "EW", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY() - 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "U", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 20, play.getPane().getChildren().get(2).getTranslateY() - 40));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY() - 20));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY()));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "1", play.getPane().getChildren().get(2).getTranslateX() + 40, play.getPane().getChildren().get(2).getTranslateY() + 20));
            				}
            			}
            		}
            		else if (play.getP1A())
            		{
            			if (play.getP1Att())
            			{
            				if (p1x > 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "DR", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "DL", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x > 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "UR", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "UL", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "R", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "L", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "D", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "U", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "R", "AP", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            			}
            			else if (play.getP1Def())
            			{
            				play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Wall.png", "R", "AW", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            			}
            		}
            		else if (play.getP1W())
            		{
            			if (play.getP1Att())
            			{
            				if (p1x > 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "DR", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0 && p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "DL", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x > 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "UR", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0 && p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "UL", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "R", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "L", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "D", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else if (p1y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "U", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "R", "WC", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            				}
            			}
            			else if (play.getP1Def())
            			{
            				play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Shield.png", "R", "WS", "1", play.getPane().getChildren().get(2).getTranslateX(), play.getPane().getChildren().get(2).getTranslateY()));
            			}
            		}
            		
            		if (play.getP2F())
            		{
            			if (play.getP2Att())
            			{
            				if (p2x > 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "DR", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "DL", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x > 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "UR", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "UL", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "R", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "L", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "D", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "U", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/FB1.png", "L", "FB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            			}
            			else if (play.getP2Def())
            			{
            				if (p1x > 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "DR", "FW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY() - 40));
            				}
            				else if (p2x < 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "DL", "FW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY() - 40));
            				}
            				else if (p2x > 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "UR", "FW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY() + 40));
            				}
            				else if (p2x < 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "UL", "FW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY() + 40));
            				}
            				else if (p2x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "R", "FW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "L", "FW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "D", "FW", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY() - 40));
            				}
            				else if (p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "U", "FW", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY() + 40));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Fire Wall.png", "L", "FW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY()));
            				}
            			}
            		}
            		else if (play.getP2E())
            		{
            			if (play.getP2Att())
            			{
            				if (p2x > 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "DR", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "DL", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x > 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "UR", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "UL", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "R", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "L", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "D", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "U", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Blast.png", "L", "EB", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            			}
            			else if (play.getP2Def())
            			{
            				if (p2x > 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DR", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 20, play.getPane().getChildren().get(3).getTranslateY() + 60));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DR", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY() + 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DR", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 60, play.getPane().getChildren().get(3).getTranslateY() + 20));
            				}
            				else if (p2x < 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DL", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 20, play.getPane().getChildren().get(3).getTranslateY() + 60));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DL", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY() + 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "DL", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 60, play.getPane().getChildren().get(3).getTranslateY() + 20));
            				}
            				else if (p2x > 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UR", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 20, play.getPane().getChildren().get(3).getTranslateY() - 60));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UR", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY() - 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UR", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 60, play.getPane().getChildren().get(3).getTranslateY() - 20));
            				}
            				else if (p2x < 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UL", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 20, play.getPane().getChildren().get(3).getTranslateY() - 60));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UL", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY() - 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "UL", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 60, play.getPane().getChildren().get(3).getTranslateY() - 20));
            				}
            				else if (p2x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY() - 20));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY()));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "R", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 40, play.getPane().getChildren().get(3).getTranslateY() + 20));
            				}
            				else if (p2x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY() - 20));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY()));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY() + 20));
            				}
            				else if (p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "D", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 20, play.getPane().getChildren().get(3).getTranslateY() + 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "D", "EW", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY() + 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "D", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 20, play.getPane().getChildren().get(3).getTranslateY() + 40));
            				}
            				else if (p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "U", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 20, play.getPane().getChildren().get(3).getTranslateY() - 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "U", "EW", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY() - 40));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "U", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() + 20, play.getPane().getChildren().get(3).getTranslateY() - 40));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY() - 20));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY()));
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Rock Wall.png", "L", "EW", "2", play.getPane().getChildren().get(3).getTranslateX() - 40, play.getPane().getChildren().get(3).getTranslateY() + 20));
            				}
            			}
            		}
            		else if (play.getP2A())
            		{
            			if (play.getP2Att())
            			{
            				if (p2x > 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "DR", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "DL", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x > 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "UR", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "UL", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "R", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "L", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "D", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "U", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Push.png", "L", "AP", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            			}
            			else if (play.getP2Def())
            			{
            				play.getPane().getChildren().add(new SpellImage("file:///../assets/Wind Wall.png", "L", "AW", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            			}
            		}
            		else if (play.getP2W())
            		{
            			if (play.getP2Att())
            			{
            				if (p2x > 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "DR", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0 && p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "DL", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x > 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "UR", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0 && p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "UL", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "R", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2x < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "L", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y > 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "D", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else if (p2y < 0)
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "U", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            				else
            				{
            					play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Crash.png", "L", "WC", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            				}
            			}
            			else if (play.getP2Def())
            			{
            				play.getPane().getChildren().add(new SpellImage("file:///../assets/Water Shield.png", "L", "WS", "2", play.getPane().getChildren().get(3).getTranslateX(), play.getPane().getChildren().get(3).getTranslateY()));
            			}
            		}
            	}
            }
        };
		
		// Create listener to create a game window when the button is clicked
		button1.setPickOnBounds(true);
		button1.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				play = new Gamestate();
		        stage.setScene(play.createGame());
		        timer.start();
		        stage.show();
		    	}
		    });
	}
	
	/**
	 * Launch the GUI Window
	 * @param args String array of arguments. None used.
	 */
	public static void main(String[] args) {
		launch();
	}
}