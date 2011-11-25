import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.List;
import java.util.LinkedList;


/**
 * Write a description of class CardRow here.
 * 
 * @author Felipe I. Anfurrutia
 * @version 29/10/2011
 */
public class CardRow  extends Actor implements Accepter
{
    private List<Card> cards;
    private Card.Suit suit;
    
    public CardRow(){
        cards = new LinkedList<Card>();
    }
    
    /**
     * Adds the cardRow without cards
     */
    public void addedToWorld(World world){
        setImage("images/cards/empty.png");
        int expand = 440;
        int middle = (expand-getImage().getWidth()) / 2;
        GreenfootImage image = new GreenfootImage(getImage().getWidth()+expand, getImage().getHeight());
        image.drawImage(new GreenfootImage("images/cards/empty.png"), middle, 0);
        image.drawRect( 0, 0, expand, image.getHeight()-1);
        setImage(image);
    }
    
    /**
     * Determina si la fila está complenta
     * @return True si la fila está completa y False, en caso contrario
     */
    public boolean isCompleted(){
        return cards.size() == 13;
    }

    /**
     * Add a card if it's legal.
     * @return True if the card has been added, and False if it's been rejected.
     */
    public boolean addCard(Card card){
        //To-DO: verifica si se cumplen las restricciones y/o las reglas del juego que se comentan en la descipción de la prácica. En base a eso se decide aceptar o denegar
        System.out.println("Se ha llamado al mŽtodo addCard de CardRow");
        //comprobamos si se cumplen las reglas
        //Si la fila no tiene palo asignado y la carta es un seis
        if (this.suit == null && card.getValue() == Card.Value.SIX) {
            this.suit = card.getSuit();
            add(card, 1);
            return true;
        }
        else if (this.suit == card.getSuit()){
            //calculamos posici—n À?
            add(card, 1);
            return true;
        }
        return false;        
    }
    
    /**
     * Adds a card on the structure of data and place on the table
     */    
    private void add(Card card, int pos){
        //To-DO: Además de realizar lo que tiene que hacer, la carta ya no se va a poder arrastrar con el ratón
        
        //-----
        card.setAccepter(this);
        //----
        card.setDraggable(false);
        placeCard(card);
    }
    
    /**
     * Coloca la carta en la fila de la mesa
     */
    private void placeCard(Card card){
        int x = getX(); 
        int y = getY();
        int dif;
        if (card.getValue() == Card.Value.SIX)
            x = x - 35;
        else if (card.getValue().ordinal() >= Card.Value.SEVEN.ordinal()){
            dif = card.getValue().ordinal() - Card.Value.SEVEN.ordinal();
            x = x + 35 + dif*15;
        }
        else {
            dif = Card.Value.SEVEN.ordinal() - card.getValue().ordinal();
            x = x - 75 - dif*15;
        }
        card.setLocation(x, y);
        //card.reAdd();
    }
      
}
