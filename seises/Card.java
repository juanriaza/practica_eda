import greenfoot.*; // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
* A playing card.
*
* @author Felipe I. Anfurrutia
* @version 29/10/2011
*/
public class Card extends CacheActor
{

    /** The suits a card can belong to */
    public enum Suit {CLUBS, HEARTS, SPADES, DIAMONDS};
    /** The numbers a card can take */
    public enum Value {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING};
    /** The colours a card can be */
    public enum Colour {RED, BLUE};

    /** The card's colour */
    private Colour colour;
    /** The card's suit */
    private Suit suit;
    /** The card's value */
    private Value value;
    /** Keeps how the card to be show */
    private boolean flipped;
    /** Keeps whether the card can drag or not */
    private boolean canDrag;
    /** Keeps where is the card: in the players hand (Player) or in the row (CardRow) */
    private Accepter accepter;
    protected static final String CARD_IMAGE_LOCATION = "images/cards/";
    private int xOffset;
    private int yOffset;
    /** Keeps the initial position when starts dragging */
    private int initialx;
    private int initialy;
    /** Keeps the cards the must be reAdd if the card can't put in a row */
    private List<Card> cards;
    
    /**
* Generate a card with random properties (but not a joker)
*/
    public Card() {
        int rand = Greenfoot.getRandomNumber(Suit.values().length);
        suit = Suit.values()[rand];
        rand = Greenfoot.getRandomNumber(Value.values().length);
        value = Value.values()[rand];
        rand = Greenfoot.getRandomNumber(Colour.values().length);
        colour = Colour.values()[rand];
        draw();
    }
    
    /**
* Generate a card with a colour, suit and value
* @param colour the colour of the card
* @param value the value of the card
* @param suit the suit of the card
* @param flipped true if the card is face down, false otherwise
*/
    public Card(Colour colour, Value value, Suit suit, boolean flipped) {
        this.colour = colour;
        this.value = value;
        this.suit = suit;
        this.flipped = flipped;
        draw();
    }
    
    /**
* Special constructor called by the joker
*/
    protected Card(Colour colour, boolean flipped) {
        this.colour = colour;
        this.flipped = flipped;
        value = null;
        suit = null;
        draw();
    }
    
    /**
* Select the image of the card based on its suit, value and colour
* and draw it.
*/
    protected void draw() {
        String fileName = CARD_IMAGE_LOCATION;
        if(flipped) {
            fileName += colour;
            fileName += "flip";
        }
        else {
            fileName += value;
            fileName += suit;
        }
        fileName += ".png";
        fileName = fileName.toLowerCase();
        setImage(fileName);
    }
    
    /**
* Set whether the card is flipped over or not
* @param flipped true if the card is face down, false otherwise
*/
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
        draw();
    }
    
    /**
* Set whether we can drag the card around or not
* @param drag true if we can drag it around, false otherwise
*/
    public void setDraggable(boolean drag) {
        this.canDrag = drag;
    }
    
    /**
* Determine whether we can drag the card around or not
* @return true if we can drag it around, false otherwise
*/
    public boolean isDraggable() {
        return canDrag;
    }
    
    /**
* Determine whether the card is flipped or not
* @return true if the card is face down, false otherwise
*/
    public boolean isFlipped() {
        return flipped;
    }
    
    /**
* Get the colour of the card 
* @return the colour of the card
*/
    public Colour getColour() {
        return colour;
    }
    
    /**
* Get the value of the card 
* @return the value of the card
*/
    public Value getValue() {
        return value;
    }
    
    /**
* Get the suit of the card (devuelve el palo)
* @return the suit of the card
*/
    public Suit getSuit() {
        return suit;
    }
    
    /**
* If we're allowed to drag the card around and manage the dragging.
*/
    public void act() {
        //If we're not allowed to move the card about, don't do anything
        if(!canDrag) return; 
                
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if(! (mouse!=null && mouse.getActor()==this)) return; //If the mouse isn't used on this card, don't do anything
        
        /*
* If we double click a card on top of a players's card, try and add it to a row
*/
        if(mouse.getClickCount()==2 && ((getAccepter() instanceof Player && ((Player)getAccepter()).canSelect(this)) )) {
            placeOnArow();
            nextTurn();
        }
        
        /*
* Do this on the initial press (at the start of the drag operation
*/
        if (Greenfoot.mousePressed(this)) {
            xOffset = getX()-mouse.getX();
            yOffset = getY()-mouse.getY();
            setInitial(getX(), getY());
            getCardsOn();
        }
        
        /*
* Do this constantly while the card is being dragged
*/
        if(Greenfoot.mouseDragged(this)) {
            setLocation(mouse.getX()+xOffset, mouse.getY()+yOffset);
            reAdd();
        }
        
        /*
* Do this when the drag has ended
*/
        if(Greenfoot.mouseDragEnded(this)) {
            placeInAccepter();
        }
    }
    
    /**
 Pasa al siguiente turno
 @return void
*/
    private void nextTurn(){
       ((TableGame)getWorld()).next();
    }
    
    /**
* Coloca automaticamente la carta en la fila correspondiente
* @return void
*/
   private void placeOnArow(){
        
        if (!(accepter instanceof Player))
            return;
       List<CardRow> rows = getWorld().getObjects(CardRow.class);   
        //intentamos anadir la carta a alguna fila
        boolean added = false;
        Iterator<CardRow> it = rows.iterator();
        while(!added && it.hasNext()){
            CardRow crAux = it.next();
            added = crAux.addCard(this);
        }
        if (added){
            Greenfoot.playSound("sounds/card.wav");
            Player pAux = (Player)this.getAccepter();
            pAux.remove(this);
            leaveAllCardsAsBefore();
            
        }
        else {
            Player pAux = (Player)getAccepter();
            pAux.incrementFailures(this);
        }
    }
    
 
/** Calcula las cartas que estan encima de la carta actual
* @return una lista con las cartas que estan encima de la carta actual
*/
    private void getCardsOn(){
        if (accepter instanceof Player) {
            Player p = (Player)accepter;
            cards = p.getCardsOn(this);
            
        }
    }

    /**
* Dejar las cartas como antes, si se ha arrastrado una carta de la mitad
* @return void
*/
    private void leaveAllCardsAsBefore(){
      if (accepter instanceof Player){
            Player pAux = (Player)accepter;
            pAux.placeAllCards();
            
       }
    }
    
    /**
* Intenta colocar la carta en las manos de un jugador o una fila, que hacen interseccion con la carta (controla inserciones a la hora de arrastrar las cartas)
* @return void
*/
    private void placeInAccepter(){
       List<Accepter> accepters = getIntersectingObjects(Accepter.class);
       boolean added = false;
       
        if (accepters.size() != 0)
       {
            Accepter accept = accepters.get(0);
            if (accept instanceof Player)
            {
                Player pAux = (Player)accept;
                added = pAux.addCard(this);
            }
            else if (accept instanceof CardRow)
            {
                CardRow cRow = (CardRow)accept;
                added = cRow.addCard(this);
            }
        }
       if (added){
           Player pAux = (Player)this.getAccepter();
           pAux.remove(this);
           leaveAllCardsAsBefore();
           nextTurn();
           Greenfoot.playSound("sounds/card.wav");
       }
       else {
           //If we haven't made a legal move, put all the cards back where they were
           if (getAccepter() instanceof Player) { // The card returns to the player
               leaveAllCardsAsBefore();
               Player player = (Player)getAccepter();//Si arrastramos una carta no valida esta vuelve a la mano inmediatamente
               if (player.canSelect(this)){
                    player.incrementFailures(this);
                    nextTurn();
               }
               else if(!player.canSelect(this)){
                    leaveAllCardsAsBefore();
               }
           }
           else if (getAccepter() == null) { //The card returns to the deck
               setLocation(getInitialx(), getInitialy());
           }
       }
    }
    
    /**
* Set the initial co-ordinates of the card.
* @return void
*/
    public void setInitial(int x, int y) {
        initialx = x;
        initialy = y;
    }
    
    /**
* Get the initial x value of the card.
* @return void
*/
    public int getInitialx() {
        return initialx;
    }
    
    
    /**
* Get the initial y value of the card.
* @return void
*/
    private int getInitialy() {
        return initialy;
    }
    
    /**
* Get the Player or CardRow is part of.
* @return void
*/
    public Accepter getAccepter() {
        return accepter;
    }
    
    /**
* Set the Player or CardRow is part of.
* @return void
*/
    public void setAccepter(Accepter accepter) {
        this.accepter = accepter;
    }
    
    /**
* Remove and add the object to the world
* @return void
*/
    public void reAdd() {
        int x = getX();
        int y = getY();
        int rotation = getRotation();
        World world = getWorld();
        world.removeObject(this);
        world.addObject(this, x, y);
    }
  /** intenta colocar la carta en una fila de la mesa, es un método publico para que pueda ser utilizado desde la clase Player y realizar asi el modo automatico de juego
   @return void
   */
    public void try2PutCard(){
        Greenfoot.delay(3);
        placeOnArow();
        }   
    
       
    /** el método toString que sustituye al de la clase object y hace que devuelva el palo y el valor de la carta
     * @return un String con el valor y el palo de la carta.
     */
    public String toString(){
        return this.value + ":" + this.suit;
    }
}