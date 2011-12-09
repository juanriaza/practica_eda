/**
 * Actors implements this interface if they want to control the turn of players
 * 
 * @author Felipe I. Anfurrutia
 * @version 29/10/2011
 */
public interface Turn<T>{
   public void clear();
   public T next();
   public boolean isMyturn(T player);
}
   
