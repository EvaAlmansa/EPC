package energypeakcontrol;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class TimeSeries extends ArrayList<Point2D> {
    /**
     * Time unit.
     */
    private TimeUnit timeUnit = TimeUnit.MINUTES;
    
    /**
     * Start time in time units (by default, 0)
     */
    private int startTime = 0;
    
    /**
     * Constructs an empty time series with an initial capacity of ten.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Constructs a time series containing the points of the specified
     * collection, in the order they are returned by the collection's iterator.
     *
     * @param c the collection whose points are to be placed into this time
     * series
     * @throws NullPointerException if the specified collection is null
     */
    public TimeSeries(Collection<? extends Point2D> c) {
        super(c);
    }

    /**
     * Returns the time unit of this series.
     *
     * @return the time unit of this series.
     */
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    /**
     * Set the time unit of this series.
     *
     * @param timeUnit the new time unit.
     */
    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    /**
     * Returns the start time of this series in time units.
     *
     * @return the start time of this series.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Set the start time of this series in time units, that is, the time
     * associatted to the first data of this series.
     *
     * @param startTime the new start time.
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }
    
    /**
     * Returns a string representation of this time series.
     * 
     * @return a string representation of this time series. 
     */
    @Override
    public String toString(){
        return "Time series: ["+super.toString()+"]";
    }
}
