import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Font;
/**
 * Write a description of class TableTurn here.
 * 
 * @author Felipe I. Anfurrutia
 * @version 29/10/2011
 */
public class TableTurn<T>  extends Actor implements Turn<T>
{
        private int numOfTurn;
        private int turn;
        private T[] players;
        private Font font;
            
        TableTurn(T[] players){
            this.players = players;
            turn = 0;
            numOfTurn = 0;
        }
        
        public void clear(){
            turn = 0;
            numOfTurn = 0;
            setText("num: " + numOfTurn,"turno: " + (turn+1));
        }
        
        public T next(){
            numOfTurn++;
            T player = players[turn];
            turn = (turn + 1) % players.length;
            setText("num: " + numOfTurn,"turno: " + (turn+1));
            return player;
        }
        
        public boolean isMyturn(T player){
            return players[turn] == player;
        }
        
        /**
          * Create some new text
          */
        public void addedToWorld(World world)
        {
            setImage(new GreenfootImage(150, 100));
            font = new Font("Comic Sans MS", Font.ITALIC, 24);
            setText("num: " + numOfTurn,"turno: " + (turn+1));
        }

        /**
          * Changes the text on the display
          */
        public void setText(String s1, String s2)
        {
             getImage().clear();
             getImage().setColor(java.awt.Color.RED);
             getImage().setFont(font);
             getImage().drawString(s1, 0, 25);
             getImage().drawString(s2, 0, 75);
        }
   
}
