import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Font;
import java.util.List;

/**
 * Displays large text for displaying new levels and announcements.
 * Disappears after a while.
 * 
 * @author Michael Berry
 * @version 19/07/08
 */
public class BigText extends Actor
{

    private static Font font;
    private int life = 100;
    
    /**
     * Decrease the time left. If 0, remove from the world.
     */
    public void act()
    {
        life--;
        if(life==0) getWorld().removeObject(this);
    }

    /**
     * Create some new text
     */
    public void addedToWorld(World world)
    {
        clearBigText();
        setImage(new GreenfootImage(getWorld().getWidth(), getWorld().getHeight()));
        font = new Font("Comic Sans MS", Font.ITALIC, 60);
    }

    /**
     * Changes the text on the display
     */
    public void setText(String s)
    {
        getImage().clear();
        getImage().setColor(java.awt.Color.RED);
        getImage().setFont(font);
        getImage().drawString(s, 160, getImage().getHeight()/2);
    }
    
    /**
     * Clears the big text already on the screen.
     */
    private void clearBigText()
    {
        List<Actor> text = getWorld().getObjects(BigText.class);
        for(Actor a : text) {
            if(a!=this) getWorld().removeObject(a);
        }
    }
}
