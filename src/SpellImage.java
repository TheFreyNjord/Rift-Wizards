/**
 * This program fuctions as the spell object class of the game Rift Wizards
 * Author: Seannace Wilkins
 * Date: 12/01/2020
 */
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * Creates an Object that holds an image for the spells
 * 
 * @author Seannace Wilkins
 *
 */
public class SpellImage extends ImageView {
	
	// Creates Variables that are used in the manipulation of the objects within the scene
	private int projSpeed = 5;
	private String direction, type, player;
	private int count = 0;
	
	/**
	 * Constructor for the SpellImage class
	 * 
	 * @author Seannace Wilkins
	 * 
	 * @param p The Path to the Image File
	 * @param d The Direction the Spell Moves
	 * @param t The Type of Spell Cast
	 * @param pl The player that the spell belongs to
	 * @param x The Starting X location of the spell
	 * @param y The Starting Y location of the spell
	 */
	public SpellImage(String p, String d, String t, String pl, double x, double y)
	{
		super(p);
		direction = d;
		type = t;
		player = pl;
		this.setTranslateX(this.getTranslateX() + x);
		this.setTranslateY(this.getTranslateY() + y);
	}
	
	/**
	 * Returns the player who cast the spell
	 * 
	 * @author Seannace Wilkins
	 * @return player
	 */
	public String getPlayer()
	{
		return player;
	}
	
	/**
	 * Returns the type of spell cast
	 * 
	 * @author Seannace Wilkins
	 * @return type
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * Returns the direction the Spell was cast
	 * 
	 * @author Seannace Wilkins
	 * @return direction
	 */
	public String getDirection()
	{
		return direction;
	}
	
	/**
	 * Does various updates to the spell based on direction, player, and spell type
	 * 
	 * @author Seannace Wilkins
	 * 
	 */
	public void update()
	{
		if (type.equals("FB"))
		{
			count++;
			if (count == 0)
			{
				Image temp = new Image("file:///../assets/FB2.png");
				this.setImage(temp);
			}
			else if (count == 5)
			{
				Image temp = new Image("file:///../assets/FB3.png");
				this.setImage(temp);
			}
			else if (count == 10)
			{
				Image temp = new Image("file:///../assets/FB2.png");
				this.setImage(temp);
			}
			else if (count == 15)
			{
				Image temp = new Image("file:///../assets/FB1.png");
				this.setImage(temp);
				count -= 15;
			}
			if (direction.equals("DR"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("DL"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("UR"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
			else if (direction.equals("UL"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
			else if (direction.equals("L"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
			}
			else if (direction.equals("R"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
			}
			else if (direction.equals("D"))
			{
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("U"))
			{
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
		}
		else if (type.equals("FW"))
		{
			count++;
			if (count == 60)
			{
				((StackPane)this.getParent()).getChildren().remove(this);
			}
		}
		else if (type.equals("EB"))
		{
			if (direction.equals("DR"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("DL"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("UR"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
			else if (direction.equals("UL"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
			else if (direction.equals("L"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
			}
			else if (direction.equals("R"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
			}
			else if (direction.equals("D"))
			{
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("U"))
			{
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
		}
		else if (type.equals("EW"))
		{
			count++;
			if (count == 60)
			{
				((StackPane)this.getParent()).getChildren().remove(this);
			}
		}
		else if (type.equals("AP"))
		{
			if (direction.equals("DR"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("DL"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("UR"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
			else if (direction.equals("UL"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
			else if (direction.equals("L"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
			}
			else if (direction.equals("R"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
			}
			else if (direction.equals("D"))
			{
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("U"))
			{
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
		}
		else if (type.equals("AW"))
		{
			if (player.equals("1"))
			{
				this.setTranslateX(((StackPane)this.getParent()).getChildren().get(2).getTranslateX());
				this.setTranslateY(((StackPane)this.getParent()).getChildren().get(2).getTranslateY());
			}
			else if (player.equals("2"))
			{
				this.setTranslateX(((StackPane)this.getParent()).getChildren().get(3).getTranslateX());
				this.setTranslateY(((StackPane)this.getParent()).getChildren().get(3).getTranslateY());
			}
			count++;
			if (count == 60)
			{
				((StackPane)this.getParent()).getChildren().remove(this);
			}
		}
		else if (type.equals("WC"))
		{
			if (direction.equals("DR"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("DL"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("UR"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
			else if (direction.equals("UL"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
			else if (direction.equals("L"))
			{
				this.setTranslateX(this.getTranslateX() - projSpeed);
			}
			else if (direction.equals("R"))
			{
				this.setTranslateX(this.getTranslateX() + projSpeed);
			}
			else if (direction.equals("D"))
			{
				this.setTranslateY(this.getTranslateY() + projSpeed);
			}
			else if (direction.equals("U"))
			{
				this.setTranslateY(this.getTranslateY() - projSpeed);
			}
		}
		else if (type.equals("WS"))
		{
			if (player.equals("1"))
			{
				this.setTranslateX(((StackPane)this.getParent()).getChildren().get(2).getTranslateX());
				this.setTranslateY(((StackPane)this.getParent()).getChildren().get(2).getTranslateY());
			}
			else if (player.equals("2"))
			{
				this.setTranslateX(((StackPane)this.getParent()).getChildren().get(3).getTranslateX());
				this.setTranslateY(((StackPane)this.getParent()).getChildren().get(3).getTranslateY());
			}
			count++;
			if (count == 60)
			{
				((StackPane)this.getParent()).getChildren().remove(this);
			}
		}
	}
}
