package events;

import java.util.EventObject;

/**
 * Class representing a time event associated to a time series.
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class TimeEvent extends EventObject {
    /**
     * Time (in time units) associated to this event.
     */
    private final long time;
    /**
     * Estimation of the time series value at this time.
     */
    private final Double estimation;
    /**
     * Membeship degree to 'danger' at this time.
     */
    private final Double danger_degree;
    /**
     * Tendency of the time series calculated as the mean over the values behind
     * this time
     */
    private final Double mean_behind;
    /**
     * New value for the time series at this time (result of an adjustment
     * process)
     */
    private final Double adjusted_value;

    /**
     * Constructs a new time event.
     * 
     * @param source the object on which the event initially occurred
     * @param time time (in time units) associated to this event
     * @param estimation estimation of the time series value at this time.
     * @param danger_degree membeship degree to 'danger' at this time.
     * @param mean_behind tendency value of the time series at this time.
     * @param adjusted_value new value for the time series at this time
     */
    public TimeEvent(Object source, long time, double estimation, double danger_degree, double mean_behind, double adjusted_value) {
        super(source);
        this.time = time;
        this.estimation = estimation;
        this.danger_degree = danger_degree;
        this.mean_behind = mean_behind;
        this.adjusted_value = adjusted_value;
    }
    
    /**
     * Returns the time associated to this event.
     * @return 
     */
    public long getTime() {
        return time;
    }

    /**
     * Returns the estimation of the time series value associated to this event.
     * 
     * @return the estimation of the time series value
     */
    public Double getEstimationValue() {
        return estimation;
    }

    /**
     * Returns the danger degree associated to this event.
     * 
     * @return the danger degree.
     */
    public Double getDangerDegree() {
        return danger_degree;
    }

    /**
     * Retunrs the new value for the time series associated to this event.
     * 
     * @return the new adjusted value.
     */
    public Double getAdjustedValue() {
        return adjusted_value;
    }

    /**
     * Tendency of the time series associated to this event.
     * 
     * @return the tendency of the time series.
     */
    public Double getMeanBehind() {
        return mean_behind;
    }
}
