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
    private long time;
    /**
     * Estimation of the time series value at this time.
     */
    private Double estimation;
    /**
     * Membeship degree to 'danger' at this time.
     */
    private Double danger_degree;
    /**
     * Tendency of the time series calculated as the mean over the values behind
     * this time
     */
    private Double mean_behind;
    /**
     * New value for the time series at this time (result of an adjustment
     * process)
     */
    private Double adjusted_value;

    public TimeEvent(Object source, long time, double estimation, double danger_degree, double mean_behind, double adjusted_value) {
        super(source);
        this.time = time;
        this.estimation = estimation;
        this.danger_degree = danger_degree;
        this.mean_behind = mean_behind;
        this.adjusted_value = adjusted_value;
    }
    
    public long getTime() {
        return time;
    }
    
    public void setTime(long time) {
        this.time = time;
    }    


    public Double getEstimationValue() {
        return estimation;
    }

    public void setEstimationValue(Double estimation) {
        this.estimation = estimation;
    }

    public Double getDangerDegree() {
        return danger_degree;
    }

    public void setSlopeDegree(Double degree) {
        this.danger_degree = degree;
    }

    public Double getAdjustedValue() {
        return adjusted_value;
    }

    public void setAdjustedValue(Double value) {
        this.adjusted_value = value;
    }

    public Double getMeanBehind() {
        return mean_behind;
    }

    public void setMeanBehind(Double mean_behind) {
        this.mean_behind = mean_behind;
    }
    
    
}
