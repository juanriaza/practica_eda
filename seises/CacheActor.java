import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;

/**
 * Stores images previously accessed in a "cache", so next time the image
 * is accessed it doesn't need to be reloaded.
 * 
 * @author Michael Berry
 * @version 25/04/09
 */
public abstract class CacheActor extends Actor
{

    private static HashMap<String, GreenfootImage> images;
    
    public CacheActor()
    {
        super();
        images = new HashMap<String, GreenfootImage>();
    }

    @Override public void setImage(String file)
    {
        if(images.get(file)!=null) {
            setImage(images.get(file));
        }
        else {
            GreenfootImage image = new GreenfootImage(file);
            super.setImage(image);
            images.put(file, image);
        }
    }
}
