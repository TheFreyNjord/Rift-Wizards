/**
 * This program fuctions as the Primary Gameplay object for Rift Wizards
 * Author: Seannace Wilkins
 * Date: 12/01/2020
 */
import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Creates an object that will handle the main gameplay
 * @author Seannace Wilkins
 */
public class Gamestate {
	
	// Create boolean values that change on player input to affect player movement
	private boolean p1Up, p2Up, p1Down, p2Down, p1Left, p2Left, p1Right, p2Right;
	
	// Create boolean values for using abilities based on player input
	private boolean p1F, p1E, p1A, p1W, p2F, p2E, p2A, p2W, p1Att, p1Def, p2Att, p2Def;
	
	// Creates Variables to Hold Points for Each Player
	int p1S = 0, p2S = 0;
	
	// Creates Variables for Player Names
	String p1N = "P1", p2N = "P2";
	
	// Create the StackPane that will be used later
	private StackPane pane;
	
	// Create variable for the dimensions of the screen to assist in portability between screen resolutions
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
							
	// Create variables for the width and height of the game that takes up a square 
	// slightly smaller than the height of the user's screen
	private double height = screenSize.getHeight() / 1.1;
	private double width = screenSize.getHeight() / 1.1;
	
	// Create variable to resize the players based on the user's screen size as well as variables to move them to their starting locations
	private double playerScaleHeight = ((double)45 / 1080) * height;
	private double playerScaleWidth = ((double)45 / 1080) * width;
			
	private double player1MoveY = ((double)-495 / 1080) * height;
	private double player1MoveX = ((double)-495 / 1080) * width;
	private double player2MoveY = ((double)480 / 1080) * height;
	private double player2MoveX = ((double)485 / 1080) * width;
	
	// Create a Label that will hold the score of the game
	Label l = new Label(p1N + ": " + p1S + " " + p2N + ": " + p2S);
	/**
	 * Updates multiple variables to reflect a player dying
	 * @author Seannace Wilkins
	 * @param i The index number that belongs to the dying player object within the stack pane
	 */
	public void playerDeath(int i)
	{
		if (i == 2)
		{
			pane.getChildren().get(2).setTranslateY(player1MoveY);
			pane.getChildren().get(2).setTranslateX(player1MoveX);
			p2S++;
			refreshScore();
		}
		else
		{
			pane.getChildren().get(3).setTranslateY(player2MoveY);
			pane.getChildren().get(3).setTranslateX(player2MoveX);
			p1S++;
			refreshScore();
		}
	}
	
	/**
	 * Refreshes the Scoreboard
	 * @author Seannace Wilkins
	 */
	private void refreshScore()
	{
		l.setText(p1N + ": " + p1S + " " + p2N + ": " + p2S);
	}
	
	/**
	 * Creates the game scene and returns it to the driver
	 * @author Seannace Wilkins
	 * @return scene
	 */
	public Scene createGame() {
		
		// Create variables to resize and move the invisible barriers to their correct positions
		double barrierVScaleHeight = height;
		double barrierVScaleWidth = ((double)25 / 1080) * width;
		double barrierHScaleHeight = ((double)25 / 1080) * height;
		double barrierHScaleWidth = width;
		
		double smallVBarrierScaleHeight = ((double)190 / 1080) * height;
		double smallVBarrierScaleWidth = ((double)15 / 1080) * width;
		double smallHBarrierScaleHeight = ((double)15 / 1080) * width;
		double smallHBarrierScaleWidth = ((double)180 / 1080) * height;
		
		double bigV1BarrierScaleHeight = ((double)185 / 1080) * height;
		double bigV1BarrierScaleWidth = ((double)40 / 1080) * width;
		double bigH1BarrierScaleHeight = ((double)40 / 1080) * height;
		double bigH1BarrierScaleWidth = ((double)185 / 1080) * width;
		
		double bigV2BarrierScaleHeight = ((double)325 / 1080) * height;
		double bigV2BarrierScaleWidth = ((double)40 / 1080) * width;
		double bigH2BarrierScaleHeight = ((double)40 / 1080) * height;
		double bigH2BarrierScaleWidth = ((double)400 / 1080) * width;
		
		double diagBarrierScaleHeight = ((double)40 / 1080) * height;
		double diagBarrierScaleWidth = ((double)700 / 1080) * width;
		
		double wall1MoveY = 0;
		double wall1MoveX = ((double)-528 / 1080) * width;
		double wall2MoveY = 0;
		double wall2MoveX = ((double)528 / 1080) * width;
		double wall3MoveY = ((double)-528 / 1080) * height;
		double wall3MoveX = 0;
		double wall4MoveY = ((double)528 / 1080) * height;
		double wall4MoveX = 0;
		double wall5MoveY = ((double)160 / 1080) * height;
		double wall5MoveX = ((double)-400 / 1080) * width;
		double wall6MoveY = ((double)-425 / 1080) * height;
		double wall6MoveX = ((double)260 / 1080) * width;
		double wall7MoveY = ((double)-170 / 1080) * height;
		double wall7MoveX = ((double)415 / 1080) * width;
		double wall8MoveY = ((double)425 / 1080) * height;
		double wall8MoveX = ((double)-245 / 1080) * width;
		double wall9MoveY = ((double)85  / 1080) * height;
		double wall9MoveX = ((double)135 / 1080) * width;
		double wall10MoveY = ((double)-235 / 1080) * height;
		double wall10MoveX = ((double)45 / 1080) * width;
		double wall11MoveY = ((double)-160 / 1080) * height;
		double wall11MoveX = ((double)-135 / 1080) * width;
		double wall12MoveY = ((double)228 / 1080) * height;
		double wall12MoveX = ((double)63 / 1080) * width;
		double wall13MoveY = ((double)-50 / 1080) * height;
		double wall13MoveX = ((double)45 / 1080) * width;
		
		double scoreMoveY = ((double)300 / 1080) * height;
		double scoreMoveX = ((double)-500 / 1080) * width;
		l.setTextFill(Color.WHITE);
		l.setScaleX(((double)2 / 1080) * width);
		l.setScaleY(((double)2  / 1080) * height);
		
		double player1Movement = ((double)20 / 1080) * height;
		
		
		// Create the image for the background of the game and adjust it to look good
		ImageView background = new ImageView("file:///../assets/Map.png");
		
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
				
		// Create the image for the view of the walls and apply the same effects to them as the background
		ImageView wall = new ImageView("file:///../assets/Walls.png");
		wall.setPreserveRatio(true);
		wall.setFitHeight(height);
		wall.setFitWidth(width);
		wall.setEffect(colorAdjust);
		
		// Create the two player images and apply effects to them
		ImageView player1 = new ImageView("file:///../assets/Right Red.png");
		player1.setPreserveRatio(true);
		player1.setFitHeight(playerScaleHeight);
		player1.setFitWidth(playerScaleWidth);
		player1.setEffect(colorAdjust);
		
		// Create the two player images and apply effects to them
		ImageView player2 = new ImageView("file:///../assets/Blue Left.png");
		player2.setPreserveRatio(true);
		player2.setFitHeight(playerScaleHeight);
		player2.setFitWidth(playerScaleWidth);
		player2.setEffect(colorAdjust);
		
		// Create the invisible barriers for the game's walls
		ImageView w1 = new ImageView();
		w1.setFitHeight(barrierVScaleHeight);
		w1.setFitWidth(barrierVScaleWidth);
		
		ImageView w2 = new ImageView();
		w2.setFitHeight(barrierVScaleHeight);
		w2.setFitWidth(barrierVScaleWidth);
		
		ImageView w3 = new ImageView();
		w3.setFitHeight(barrierHScaleHeight);
		w3.setFitWidth(barrierHScaleWidth);
		
		ImageView w4 = new ImageView();
		w4.setFitHeight(barrierHScaleHeight);
		w4.setFitWidth(barrierHScaleWidth);
		
		ImageView w5 = new ImageView();
		w5.setFitHeight(smallVBarrierScaleHeight);
		w5.setFitWidth(smallVBarrierScaleWidth);
		
		ImageView w6 = new ImageView();
		w6.setFitHeight(smallHBarrierScaleHeight);
		w6.setFitWidth(smallHBarrierScaleWidth);
		
		ImageView w7 = new ImageView();
		w7.setFitHeight(smallVBarrierScaleHeight);
		w7.setFitWidth(smallVBarrierScaleWidth);
		
		ImageView w8 = new ImageView();
		w8.setFitHeight(smallHBarrierScaleHeight);
		w8.setFitWidth(smallHBarrierScaleWidth);
		
		ImageView w9 = new ImageView();
		w9.setFitHeight(bigH1BarrierScaleHeight);
		w9.setFitWidth(bigH1BarrierScaleWidth);
		
		ImageView w10 = new ImageView();
		w10.setFitHeight(bigV1BarrierScaleHeight);
		w10.setFitWidth(bigV1BarrierScaleWidth);
		
		ImageView w11 = new ImageView();
		w11.setFitHeight(bigH2BarrierScaleHeight);
		w11.setFitWidth(bigH2BarrierScaleWidth);
		
		ImageView w12 = new ImageView();
		w12.setFitHeight(bigV2BarrierScaleHeight);
		w12.setFitWidth(bigV2BarrierScaleWidth);
		
		// Fill the StackPane with the elements needed, then put the players in their proper positions
		pane = new StackPane(background, wall, player1, player2, w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, l);
		pane.getChildren().get(2).setTranslateY(pane.getChildren().get(2).getTranslateY() + player1MoveY);
		pane.getChildren().get(2).setTranslateX(pane.getChildren().get(2).getTranslateX() + player1MoveX);
		pane.getChildren().get(3).setTranslateY(pane.getChildren().get(3).getTranslateY() + player2MoveY);
		pane.getChildren().get(3).setTranslateX(pane.getChildren().get(3).getTranslateX() + player2MoveX);
		pane.getChildren().get(4).setTranslateX(pane.getChildren().get(4).getTranslateX() + wall1MoveX);
		pane.getChildren().get(4).setTranslateY(pane.getChildren().get(4).getTranslateY() + wall1MoveY);
		pane.getChildren().get(5).setTranslateX(pane.getChildren().get(5).getTranslateX() + wall2MoveX);
		pane.getChildren().get(5).setTranslateY(pane.getChildren().get(5).getTranslateY() + wall2MoveY);
		pane.getChildren().get(6).setTranslateX(pane.getChildren().get(6).getTranslateX() + wall3MoveX);
		pane.getChildren().get(6).setTranslateY(pane.getChildren().get(6).getTranslateY() + wall3MoveY);
		pane.getChildren().get(7).setTranslateX(pane.getChildren().get(7).getTranslateX() + wall4MoveX);
		pane.getChildren().get(7).setTranslateY(pane.getChildren().get(7).getTranslateY() + wall4MoveY);
		pane.getChildren().get(8).setTranslateX(pane.getChildren().get(8).getTranslateX() + wall5MoveX);
		pane.getChildren().get(8).setTranslateY(pane.getChildren().get(8).getTranslateY() + wall5MoveY);
		pane.getChildren().get(9).setTranslateX(pane.getChildren().get(9).getTranslateX() + wall6MoveX);
		pane.getChildren().get(9).setTranslateY(pane.getChildren().get(9).getTranslateY() + wall6MoveY);
		pane.getChildren().get(10).setTranslateX(pane.getChildren().get(10).getTranslateX() + wall7MoveX);
		pane.getChildren().get(10).setTranslateY(pane.getChildren().get(10).getTranslateY() + wall7MoveY);
		pane.getChildren().get(11).setTranslateX(pane.getChildren().get(11).getTranslateX() + wall8MoveX);
		pane.getChildren().get(11).setTranslateY(pane.getChildren().get(11).getTranslateY() + wall8MoveY);
		pane.getChildren().get(12).setTranslateX(pane.getChildren().get(12).getTranslateX() + wall9MoveX);
		pane.getChildren().get(12).setTranslateY(pane.getChildren().get(12).getTranslateY() + wall9MoveY);
		pane.getChildren().get(13).setTranslateX(pane.getChildren().get(13).getTranslateX() + wall10MoveX);
		pane.getChildren().get(13).setTranslateY(pane.getChildren().get(13).getTranslateY() + wall10MoveY);
		pane.getChildren().get(14).setTranslateX(pane.getChildren().get(14).getTranslateX() + wall11MoveX);
		pane.getChildren().get(14).setTranslateY(pane.getChildren().get(14).getTranslateY() + wall11MoveY);
		pane.getChildren().get(15).setTranslateX(pane.getChildren().get(15).getTranslateX() + wall12MoveX);
		pane.getChildren().get(15).setTranslateY(pane.getChildren().get(15).getTranslateY() + wall12MoveY);
		pane.getChildren().get(16).setTranslateX(pane.getChildren().get(15).getTranslateX() + scoreMoveX);
		pane.getChildren().get(16).setTranslateY(pane.getChildren().get(15).getTranslateY() + scoreMoveY);
		
		
		// Add the background to the scene at location
		Scene scene = new Scene(pane, width, height);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:  p1Up = true; break;
                    case S:  p1Down = true; break;
                    case A:  p1Left  = true; break;
                    case D:  p1Right  = true; break;
                    case UP: p2Up = true; break;
                    case DOWN: p2Down= true; break;
                    case LEFT: p2Left = true; break;
                    case RIGHT: p2Right = true; break;
                    case R: p1F = true; break;
                    case T: p1E = true; break;
                    case Y: p1A = true; break;
                    case U: p1W = true; break;
                    case F: p1Att = true; break;
                    case I: p1Def = true; break;
                    case NUMPAD4: p2F = true; break;
                    case NUMPAD5: p2E = true; break;
                    case NUMPAD6: p2A = true; break;
                    case ADD: p2W = true; break;
                    case NUMPAD1: p2Att = true; break;
                    case ENTER: p2Def = true; break;
                }
            }
        });
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:  p1Up = false; break;
                    case S:  p1Down = false; break;
                    case A:  p1Left  = false; break;
                    case D:  p1Right  = false; break;
                    case UP: p2Up = false; break;
                    case DOWN: p2Down= false; break;
                    case LEFT: p2Left = false; break;
                    case RIGHT: p2Right = false; break;
                    case R: p1F = false; break;
                    case T: p1E = false; break;
                    case Y: p1A = false; break;
                    case U: p1W = false; break;
                    case F: p1Att = false; break;
                    case I: p1Def = false; break;
                    case NUMPAD4: p2F = false; break;
                    case NUMPAD5: p2E = false; break;
                    case NUMPAD6: p2A = false; break;
                    case ADD: p2W = false; break;
                    case NUMPAD1: p2Att = false; break;
                    case ENTER: p2Def = false; break;
                    case ESCAPE: pause(); break;
                }
            }
        });
		
		return scene;
	}
	/**
	 * A Funtion that causes a pause menu to appear
	 * 
	 * @author Seannace Wilkins
	 * 
	 */
	private void pause()
	{
		Gamestate save = this;
		Button b = new Button("Reset");
		Button b1 = new Button("Exit");
		b1.setTranslateY(b1.getTranslateY() - 100);
		Button b2 = new Button("Save");
		b2.setTranslateY(b2.getTranslateY() + 100);
		StackPane menu = new StackPane(b, b1, b2);
		
		
		
		Scene s = new Scene(menu, width, height);
		Stage st = new Stage();
		st.setScene(s);
		st.show();
	}
	
	/**
	 * Returns the pane that holds the game pieces
	 * 
	 * @author Seannace Wilkins
	 * @return pane
	 */
	public StackPane getPane()
	{
		return pane;
	}
	
	/**
	 * Returns whether Player 1 is moving up
	 * 
	 * @author Seannace Wilkins
	 * @return p1Up
	 */
	public boolean getP1Up()
	{
		return p1Up;
	}
	
	/**
	 * Returns whether Player 1 is moving Down
	 * 
	 * @author Seannace Wilkins
	 * @return p1Down
	 */
	public boolean getP1Down()
	{
		return p1Down;
	}
	
	/**
	 * Returns whether Player 1 is moving Left
	 * 
	 * @author Seannace Wilkins
	 * @return p1Left
	 */
	public boolean getP1Left()
	{
		return p1Left;
	}
	
	/**
	 * Returns whether Player 1 is moving Right
	 * 
	 * @author Seannace Wilkins
	 * @return p1Right
	 */
	public boolean getP1Right()
	{
		return p1Right;
	}
	
	/**
	 * Returns whether Player 2 is moving Up
	 * 
	 * @author Seannace Wilkins
	 * @return p2Up
	 */
	public boolean getP2Up()
	{
		return p2Up;
	}
	
	/**
	 * Returns whether Player 2 is moving Down
	 * 
	 * @author Seannace Wilkins
	 * @return p2Down
	 */
	public boolean getP2Down()
	{
		return p2Down;
	}
	
	/**
	 * Returns whether Player 2 is moving Left
	 * 
	 * @author Seannace Wilkins
	 * @return p2Left
	 */
	public boolean getP2Left()
	{
		return p2Left;
	}
	
	/**
	 * Returns whether Player 2 is moving Right
	 * 
	 * @author Seannace Wilkins
	 * @return p2Right
	 */
	public boolean getP2Right()
	{
		return p2Right;
	}
	
	/**
	 * Returns whether Player 1 is using Fire
	 * 
	 * @author Seannace Wilkins
	 * @return p1F
	 */
	public boolean getP1F()
	{
		return p1F;
	} 
	
	/**
	 * Returns whether Player 1 is using Earth
	 * 
	 * @author Seannace Wilkins
	 * @return p1E
	 */
	public boolean getP1E()
	{
		return p1E;
	}
	
	/**
	 * Returns whether Player 1 is using Air
	 * 
	 * @author Seannace Wilkins
	 * @return p1A
	 */
	public boolean getP1A()
	{
		return p1A;
	}
	
	/**
	 * Returns whether Player 1 is using Water
	 * 
	 * @author Seannace Wilkins
	 * @return p1W
	 */
	public boolean getP1W()
	{
		return p1W;
	}
	
	/**
	 * Returns whether Player 1 is using offense
	 * 
	 * @author Seannace Wilkins
	 * @return p1Att
	 */
	public boolean getP1Att()
	{
		return p1Att;
	}
	
	/**
	 * Returns whether Player 1 is using defense
	 * 
	 * @author Seannace Wilkins
	 * @return p1Def
	 */
	public boolean getP1Def()
	{
		return p1Def;
	}
	
	/**
	 * Returns whether Player 2 is using Fire
	 * 
	 * @author Seannace Wilkins
	 * @return p2F
	 */
	public boolean getP2F()
	{
		return p2F;
	}
	
	/**
	 * Returns whether Player 2 is using Earth
	 * 
	 * @author Seannace Wilkins
	 * @return p2E
	 */
	public boolean getP2E()
	{
		return p2E;
	}
	
	/**
	 * Returns whether Player 2 is using air
	 * 
	 * @author Seannace Wilkins
	 * @return p2A
	 */
	public boolean getP2A()
	{
		return p2A;
	}
	
	/**
	 * Returns whether Player 2 is using water
	 * 
	 * @author Seannace Wilkins
	 * @return p2W
	 */
	public boolean getP2W()
	{
		return p2W;	
	}
	
	/**
	 * Returns whether Player 2 is using an attack
	 * 
	 * @author Seannace Wilkins
	 * @return p2Att
	 */
	public boolean getP2Att()
	{
		return p2Att;	
	}
	
	/**
	 * Returns whether Player 2 is using defense
	 * 
	 * @author Seannace Wilkins
	 * @return p2Def
	 */
	public boolean getP2Def()
	{
		return p2Def;
	}
}