package events;

import java.util.EventListener;

/**
 *
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public interface TimeListener extends EventListener{
    public void timeChange(TimeEvent evt);
    public void timeOut(TimeEvent evt);    
}
