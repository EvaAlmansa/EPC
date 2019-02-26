package events;

import java.util.EventListener;

/**
 * Interface that all time event listener must extend.
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public interface TimeListener extends EventListener{
    /**
     * The time has changed.
     * 
     * @param evt the time event.
     */
    public void timeChange(TimeEvent evt);
    /**
     * The time is out.
     * 
     * @param evt the time event.
     */
    public void timeOut(TimeEvent evt);    
}
