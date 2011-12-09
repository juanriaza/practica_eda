/**
 * Actors implement this interface if they can have cards
 * placed on them.
 * 
 * @author Michael Berry 
 * @version 23/04/09
 */
public interface Accepter  
{
    /**
     * Add a card if it's legal.
     * @return true if the card has been added, false if it's been rejected.
     */
    public boolean addCard(Card card);
}
