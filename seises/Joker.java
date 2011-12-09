import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A joker card.
 * 
 * @author Michael Berry
 * @version 23/04/09
 */
public class Joker  extends Card
{

    /**
     * Create a new joker with a certain colour
     * @param colour the colour of the joker
     * @param flipped true if the joker is face down, false otherwise
     */
    public Joker(Colour colour, boolean flipped) {
        super(colour, flipped);
    }

    /**
     * Select the image of the card based on its suit, value and colour
     * and draw it.
     */
    @Override protected void draw() {
        String fileName = CARD_IMAGE_LOCATION;
        if(isFlipped()) {
            fileName += getColour();
            fileName += "flip";
        }
        else {
            fileName += getColour();
            fileName += "joker";
        }
        fileName += ".png";
        fileName = fileName.toLowerCase();
        setImage(new GreenfootImage(fileName));
    }

}
