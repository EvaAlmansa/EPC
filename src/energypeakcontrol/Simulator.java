package energypeakcontrol;

import events.TimeEvent;
import events.TimeListener;
import fuzzy.TrapezoidalFunction;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * Class representing a simulator for the voltaje adjustment process.
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class Simulator {    
    /**
     *  Main time series (in which the simulation is centered).
     */
    private final TimeSeries main_series;    
    /**
     * 'Danger' time series used for inner calculations. It is not needed to
     * save the whole series, only the last DEFAULT_MEANBEHIND_NUMBER_POINTS.
     */
    private TimeSeries danger_series = null;
    /**
     * Trapezoidal membership function of the fuzzy set "desired voltage"
     */
    TrapezoidalFunction mfDesired = null;
    /**
     * Thread where the simulation runs
     */
    private Thread simulation_thread = null;
    /**
     * Flag that indicates whether the thread is suspended or not
     */
    private boolean suspended = false; 
    /**
     * Waiting time (in simulation time units) between timedata processing.
     */
    private int wait_time = 10;
    /**
     * List of time event listeners.
     */
    ArrayList<TimeListener> timeEventListener = new ArrayList();
    /**
     * Last voltaje injected to the system.
     */
    private double inject_previous_time = 0;
    /**
     * Default window size used in the time series analisys.
     */
    private static final int DEFAULT_WINDOW_SIZE = 4;
    /**
     * Default number of points used to calculate the mean of the time series 
     * behind a given time.
     */
    private static final int DEFAULT_MEANBEHIND_NUMBER_POINTS = 40;
    
 
    
    /**
     * Constructs a simulator associated to the given time series.
     * 
     * @param series the time series associated to this simulator
     * @param a the desired voltage upper limit.
     * @param b the desired voltage upper-intermediate limit.
     * @param c the desired voltage bellow-intermediate limit.
     * @param d the desired voltage below limit.
     */
    public Simulator(TimeSeries series,float a, float b, float c, float d){
        this.main_series = series;
        mfDesired = new TrapezoidalFunction(a,b,c,d);
    }
    
    /**
     * Set the desired voltage limits.
     * 
     * @param a the desired voltage upper limit.
     * @param b the desired voltage upper-intermediate limit.
     * @param c the desired voltage bellow-intermediate limit.
     * @param d the desired voltage below limit.
     */
    public void setVoltageMargins(float a, float b, float c, float d){
        mfDesired.setParameters(a,b,c,d);
    }
    
    /**
     * Returns the four desired voltage limits.
     *
     * @return a vector with the four desired voltage limits stored as (upper,
     * upper-intermediate, bellow-intermediate, below).
     */
    public double[] getVoltageMargins() {       
        return mfDesired.getParameters();
    }

    /**
     * Resturns the pause time (in simulation time units) between time-data 
     * processing.
     * 
     * @return the pause time.
     */
    public int getPauseTime() {
        return wait_time;
    }

    /**
     * Set the pause time (in simulation time units) between time-data
     * processing.
     *
     * @param time pause time in simulation time units.
     */
    public void setPauseTime(int time) {
        if (wait_time > 0) {
            this.wait_time = time;
        }
    }
    
    /**
     * Starts the simulation of 'motion' over time.
     * 
     * @param unitTimes duration of the simulation (in unit times).
     */
    public void startSimulation(long unitTimes) {
        if (simulation_thread == null) {
            // An additional time series is created to store the danger degrees
            danger_series = new TimeSeries();
            // A thread is created by overloading the run() method
            simulation_thread = new Thread() {
                @Override
                public void run() {
                    for (int t = 0; t < unitTimes; t++) {
                        //The time series is processed
                        processTimeSeries(t);
                        try {
                            //The thread is asleep for a while
                            Thread.sleep(wait_time);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(EPCFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    simulation_thread = null;
                    notifyTimeOutEvent(null);
                }
            };
            simulation_thread.start(); // Thread is thrown
        }
    }
        
    /**
     * Process the time serie at the given time.
     * 
     * @param time the time to be processed. 
     */
    private void processTimeSeries(int time) {
        //Initialization: if time < DEFAULT_WINDOW_SIZE, that is, it is a point
        //at the beginig of the series which can not been processed, by default
        //the calculated values are set to the time series value. In the case
        //of the danger degree, the membeship degree function is applied to the
        //series value. For points satisfying time>=DEFAULT_WINDOW_SIZE, the
        //values will be calculated following the formulas.
        double estimation_time = main_series.get(time).getY();        
        double adjusted_value = estimation_time;
        double mean_behind = estimation_time;
        double danger_degree = 1.0-mfDesired.apply(estimation_time);
        if (time >= DEFAULT_WINDOW_SIZE) {
            //First,the membership degree to the fuzzy set 'danger' is calculated 
            //As reference set, the expected voltage (i.e., the estimation for 
            //the next time) is used
            SimpleRegression r = new SimpleRegression();
            Point2D p;            
            for (int t = 0; t < DEFAULT_WINDOW_SIZE; t++) {
                //Points used for the linear regression
                p = main_series.get(time - t);
                r.addData(p.getX(), p.getY());
            }            
            double estimation_next_time = r.predict(time+1); //time+1 estimation
            danger_degree = 1.0-mfDesired.apply(estimation_next_time);
                        
            //Second,the voltage to be injected (in time+1) is calculated as the 
            //difference between (1) the time series tendency behind 'time' and  
            //(2) the voltage estimated for time+1, weighted by the membership     
            //degree to 'danger'. The sign of the difference is taken into   
            //account to know if the injection is a positive or negative voltage
            mean_behind = meanBehind(time); //The tendency             
            double inject = (mean_behind-estimation_next_time)*danger_degree;
            
            //Main time series is adjusted on the basis of the injection value.
            //Note that the the adjustment value for 'time' must be calculated 
            //on the basis of the injection calculated on 'time-1'. Since in  
            //this method the simulation process analyzes the time 'time',  
            //we have to use the injection value calculated in the previuos time      
            adjusted_value =  main_series.get(time).getY()+inject_previous_time;            
            inject_previous_time = inject;
            
            //Only for visualization purposes: estimation of the current main  
            //series value (at 'time') on the basis of the previous values. It 
            //is not used for processing this 'time' (only to notify the value
            //in the event)
            r.clear();
            for (int t = 1; t < DEFAULT_WINDOW_SIZE+1; t++) {
                p = main_series.get(time - t);
                r.addData(p.getX(), p.getY());
            }
            estimation_time = r.predict(time);
        }
        // We store the 'danger' membeship degree in a series for use it in 
        // subsequent calculations (specifically, for the calculation of the 
        // mean series); we do not really need to save the whole series, only 
        // the last DEFAULT_MEANBEHIND_NUMBER_POINTS, but this improvement is 
        // left for future work
        danger_series.add(new Point2D.Double(time,danger_degree));
        //A new time event is notified
        notifyTimeChangeEvent(new TimeEvent(this,time,estimation_time,danger_degree,mean_behind,adjusted_value));
    }
    
    /**
     * Returns the tendendy of the time series calculated as the mean over the
     * values behind 'time' with a danger degree equals to zero.
     *
     * @param time the time to be processed.
     * @return the mean behind 'time'
     */
    private double meanBehind(int time) {                 
        double mean_behind = 0.0;
        double danger_t;
        int n = 0, t = 1; //The current time is not use to estimate the tendency        
        while (n < DEFAULT_MEANBEHIND_NUMBER_POINTS && time >= t) {
            danger_t = danger_series.get(time - t).getY();
            if (danger_t < 0.00001) {                
                mean_behind += main_series.get(time - t).getY();
                n++;
            }
            t++;
        }
        if (n > 0) {
            mean_behind /= n;
        } else {
            //Exceptional case: the series starts with points outside the 
            //margins and there is no data to estimate the mean. In this 
            //case, the central point of the core of the fuzzyset 'desired 
            //voltage' is used
            double a = mfDesired.getParameters()[0];
            double d = mfDesired.getParameters()[3];
            mean_behind = ((d - a) / 2.0) + a;
        }
        return mean_behind;
    }
    
     /**
     * Returns <code>true</code> if the simulation is running.
     * @return 
     */
    public boolean isRunning(){
        return simulation_thread!=null;
    }
    
    /**
     * Pause the simulation. 
     */
    synchronized public void pauseSimulation() {
        //NOTE: Uses deprecated methods, need updating
        if (simulation_thread != null) {
            if(!suspended) simulation_thread.suspend();
            else simulation_thread.resume();
            suspended = !suspended;
        }
    }
    
    /**
     * Reset the simulation
     */
    synchronized public void resetSimulation() {
        //NOTE: Uses deprecated methods, need updating
        if (simulation_thread != null) {
            simulation_thread.stop();
            simulation_thread = null;
        }
    }
    
    /**
     * Adds the specified time listener to receive times events from
     * this simulator
     * 
     * @param l the pixel listener
     */
    public void addTimeListener(TimeListener l){
        if (l != null) {
            this.timeEventListener.add(l);
        }        
    }
    
    /**
     * Notify the time listeners a new time change event
     *
     * @param evt the time event
     */
    private void notifyTimeChangeEvent(TimeEvent evt) {
        if (!timeEventListener.isEmpty()) {
            for (TimeListener l : timeEventListener) {
                l.timeChange(evt);
            }
        }
    }
    
    /**
     * Notify the time listeners a new time out event
     *
     * @param evt the time event
     */
    private void notifyTimeOutEvent(TimeEvent evt) {
        if (!timeEventListener.isEmpty()) {
            for (TimeListener l : timeEventListener) {
                l.timeOut(evt);
            }
        }
    }
    
}
