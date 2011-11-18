import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Stack;
import java.util.Collections;


/**
 * A deck that deals cards.
 * 
 * @author Michael Berry
 * @version 23/04/09
 */
public class Deck extends Actor
{

    private Stack<Card> cards;
    private boolean jokers;
    /** The number of sets of each of the suits. */
    private int spades, clubs, hearts, diamonds;
    private Card.Colour colour;
    /** The location of the picture of an empty deck (outline of a deck.) */
    private static final String EMPTY_DECK = "empty.png";
    private int showNum;

    /**
     * Create a new deck of a certain colour
     * @param colour the colour of the deck
     * @param jokers true if jokers should be included, false otherwise
     */
    public Deck(Card.Colour colour, boolean jokers)
    {
        this.colour = colour;
        this.jokers = jokers;
        spades = 1;
        hearts = 1;
        clubs = 1;
        diamonds = 1;
        setColour();
        fill();
        shuffle();
    }
    
    /**
     * Create a customised deck of a certain colour
     * @param colour the colour of the deck
     * @param jokers true if jokers should be included, false otherwise
     */
    public Deck(Card.Colour colour, boolean jokers, int spades, int clubs, int hearts, int diamonds)
    {
        this.colour = colour;
        this.jokers = jokers;
        this.diamonds = diamonds;
        this.clubs = clubs;
        this.spades = spades;
        this.hearts = hearts;
        setColour();
        fill();
        shuffle();
    }
    
    /**
     * Fill the deck with a complete set of cards. Get rid of any cards
     * still in the deck.
     */
    public void fill()
    {
        cards = new Stack<Card>();
        for(Card.Suit suit : Card.Suit.values()) {
            for(int i=0 ; i<num(suit) ; i++) {
                for(Card.Value value : Card.Value.values()) {
                    cards.add(new Card(colour, value, suit, false));
                }
            }
        }
        if(jokers) {
            cards.add(new Joker(colour, false));
            cards.add(new Joker(colour, false));
        }
        setColour();
    }
    
    /**
     * Get the number of sets needed of a particular suit.
     */
    private int num(Card.Suit suit) {
        if(suit==Card.Suit.CLUBS) {
            return clubs;
        }
        if(suit==Card.Suit.SPADES) {
            return spades;
        }
        if(suit==Card.Suit.DIAMONDS) {
            return diamonds;
        }
        if(suit==Card.Suit.HEARTS) {
            return hearts;
        }
        throw new RuntimeException("Invalid suit...");
    }
    
    /**
     * Draw a card from the deck.
     * @return the card that's been drawn, or null if no cards are left.
     */
    public Card drawCard()
    {
        if(getSize()==0) return null;
/*        
        Card card = cards.get(0);
        cards.remove(card);
*/
        Card card = cards.pop();
        if(getSize()==0) {
            setImage(new GreenfootImage(Card.CARD_IMAGE_LOCATION+EMPTY_DECK));
        }
        return card;
    }
    
    /**
     * Shows a card from the deck. Same as drawCard, but doesn't remove it.
     * @return the card that's been shown, or null if no cards are left.
     */
    public Card showCard()
    {
        if(getSize()==0) return null;
        showNum = showNum%getSize();
        Card card = cards.get(showNum);
        showNum = (showNum+1)%getSize();
        return card;
    }
    
    /**
     * Puts a card into the deck.
     * @param card the card to put in the deck.
     */
    public void addCard(Card card)
    {
//        cards.add(card);
        cards.push(card);
    }
    
    /**
     * Shuffle the deck
     */
    public void shuffle()
    {
        Collections.shuffle(cards);
    }
    
    /**
     * Determine whether the deck contains jokers.
     * @return true if the deck contains jokers, false otherwise.
     */
    public boolean hasJokers()
    {
        return jokers;
    }
    
    /**
     * Get the size of the deck
     * @return the number of cards left in the deck
     */
    public int getSize()
    {
        return cards.size();
    }

    /**
     * Set the deck to a certin colour
     */
    private void setColour()
    {
        if(colour==Card.Colour.BLUE) {
            setImage(new GreenfootImage("images/cards/blueflip.png"));
        }
        else {
            setImage(new GreenfootImage("images/cards/redflip.png"));
        }
    }  
}
