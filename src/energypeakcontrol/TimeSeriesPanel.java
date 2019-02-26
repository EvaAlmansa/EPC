package energypeakcontrol;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Class representing a canvas where a set of time series is shown.
 * 
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class TimeSeriesPanel extends javax.swing.JPanel {
    /**
     *  Time series shown in this panel.
     */
    private final ArrayList<TimeSeries> series = new ArrayList();
    /**
     *  Graphs of this time series.
     */
    private final ArrayList<GeneralPath> graph = new ArrayList();
    /**
     *  Affine transforms applied before the series plotting.
     */
    private final ArrayList<AffineTransformInfo> transformInfo = new ArrayList();   
    /**
     * Flags to set if the time series are visible or not.
     */
    private final ArrayList<Boolean> visible = new ArrayList(); 
    /**
     * Time scale used for plotting 
     */
    private int timeScale = 1;
    /**
     * Position of the current time (axis X) relative to the panel origin. 
     */
    private int originX;
    /**
     * Position of zero value (axis Y) relative to the panel origin. 
     */
    private int originY;    
    /**
     * Current time (used in the simulation process)
     */
    private int time = 0;    
    /**
     * Default colors to plott the graphs
     */
    private final Color colors[] = {Color.black, Color.blue, 
        Color.red, Color.orange, Color.green};
    /**
     * The Y coordinate of the limits lines. They are assocciated to the
     * parameters of a trapezoidal membeship function.
     */
    private float limitsLinesY[] = null;    
    
    
    
    
    /**
     * Creates new panel with and empty time series
     */
    public TimeSeriesPanel() {
        this(null);
    }

    /**
     * Creates new panel with a given time series.
     *
     * @param series the time series associated to this panel.
     */
    public TimeSeriesPanel(TimeSeries series) {
        initComponents();
        this.addTimeSeries(series);
    }
    
    /**
     * Add a time series assocciated to this panel. A transformation given by a
     * translation (tx,ty) and a scale (sx,sy) is applied when the time series
     * is plloted.
     *
     * @param series the new time series. 
     * @param tx 
     * @param ty 
     * @param sx 
     * @param sy 
     */
    public final void addTimeSeries(TimeSeries series, double tx, double ty,  double sx, double sy) {
        if (series != null) {
            this.series.add(series); //Note the parameter 'series' is a single time series
            // Generates the graph associated to the new time series
            GeneralPath gp = new GeneralPath();
            if (!series.isEmpty()) {
                gp.moveTo(series.get(0).getX(), -series.get(0).getY());
                for (int i = 1; i < series.size(); i++) {
                    gp.lineTo(series.get(i).getX() * this.timeScale, -series.get(i).getY());
                }                
            }
            graph.add(gp);
            // By default, it is visible
            visible.add(true);
            // Affine transform applied to the graph.
            transformInfo.add(new AffineTransformInfo(tx,ty,sx,sy));
        }
    }
        
    /**
     * Add a time series assocciated to this panel. By default, without plotting 
     * transformation.
     * 
     * @param series the new time series. 
     */
    public final void addTimeSeries(TimeSeries series) {
        addTimeSeries(series,0.0f,0.0f,1.0f,1.0f);
    }
    
    /**
     * 
     * @param timeSeriesIndex
     * @param data 
     */
    public void addTimeData(int timeSeriesIndex, Point2D data){
        TimeSeries ts = series.get(timeSeriesIndex);
        GeneralPath gp = graph.get(timeSeriesIndex);        
        //Update the graph
        if(ts.isEmpty()){
            gp.moveTo(data.getX(),-data.getY());
        } else{
            gp.lineTo(data.getX() * this.timeScale , -data.getY());
        }            
        ts.add(data);    
    }
    
    /**
     * Updates the graphs associated to the time series. This method is called 
     * when the time scale is changed.
     */
    private void updateGraphs() {
        graph.clear();
        for(TimeSeries ts: series){
            // Generates a graph for each time series
            GeneralPath gp = new GeneralPath();
            if (!series.isEmpty()) {
                gp.moveTo(ts.get(0).getX(), -ts.get(0).getY());
                for (int i = 1; i < ts.size(); i++) {
                    gp.lineTo(ts.get(i).getX() * this.timeScale, -ts.get(i).getY());
                }
            }
            graph.add(gp);
        }
    }
    
    /**
     * Returns the index-th time series assocciated to this panel.
     * 
     * @param index index of the time series to return.
     * @return the time series assocciated to this panel.
     */
    public TimeSeries getTimeSeries(int index) {
        return series.get(index);
    }
    
    /**
     * Returns the first time series assocciated to this panel.
     * 
     * @return the first time series assocciated to this panel.
     */
    public TimeSeries getTimeSeries() {
        return series.isEmpty() ? null : series.get(0);
    }
    
    /**
     * Returns the current time in the simulation process.
     * 
     * @return the current time.
     */
    public int getCurrentTime() {
        return time;
    }

    /**
     * Set the current time for the simulation process.
     * 
     * @param time the new current time.
     */
    public void setCurrentTime(int time) {
        this.time = time;       
    }

    /**
     * Returns the time scale.
     *
     * @return the time scale.
     */
    public int getTimeScale() {
        return timeScale;
    }

    /**
     * Set the time scale.
     *
     * @param timeScale
     */
    public void setTimeScale(int timeScale) {
        if (timeScale <= 0) {
            throw new InvalidParameterException("The time scale must be positive.");
        }
        if (this.timeScale != timeScale) {
            this.timeScale = timeScale;
            //If the time scale changes, the graph needs to be recalculated
            this.updateGraphs();
        }
    }
    
    /**
     * Set Y coordinates of the limits lines. 
     * 
     * @param a the Y coordinate of the upper limit.
     * @param b the Y coordinate of the upper-intermediate limit.
     * @param c the Y coordinate of the bellow-intermediate limit.
     * @param d the Y coordinate of the below limit.
     */
    public final void setLimitLines(float a, float b, float c, float d) { 
        if (a > b || b > c || c > d) {
            //The parameters must satisfy the following condition: a<=b<=c<=d
            limitsLinesY = null;
        } else {
            limitsLinesY = new float[4];
            limitsLinesY[0] = a;
            limitsLinesY[1] = b;
            limitsLinesY[2] = c;
            limitsLinesY[3] = d;
            this.repaint();
        }
    }
    
    /**
     * Returns the affine transform applied to the index-th series.
     * 
     * @param index index of the time series
     * @return 
     */
    public AffineTransformInfo getTransformInfo(int index){
        return transformInfo.get(index);
    }
    
    /**
     * Return the origin point of the coordinate system.
     * 
     * @return the origin point of the coordinate system.
     */
    public Point getOrigin(){
        return new Point(originX,originY);
    }
    
    /**
     * Returns the number of time series shown in this panel.
     * 
     * @return the number of time series shown in this panel.
     */
    public int numOfTimeSeries(){
        return series.size();
    }
    
    /**
     * Returns <tt>true</tt> if this panel contains no time series.
     *
     * @return <tt>true</tt> if this panel contains no time series.
     */
    public boolean isEmpty() {
        return series.isEmpty();
    }
    
    /**
     * Removes all of the time series from this panel. 
     */
    public void clear(){
        this.series.clear();
        this.graph.clear();
        this.transformInfo.clear();
        this.visible.clear();
    }
    
    /**
     * Removes the time series at the specified position.
     * 
     * @param index index index of the time series
     */
    public void removeSeries(int index){
        this.series.remove(index);
        this.graph.remove(index);
        this.transformInfo.remove(index);
        this.visible.remove(index);
    }
    
    /**
     * Removes all the time series except the first one.
     */
    public void keepOnlyFirst(){
        while(this.numOfTimeSeries()>1){
            removeSeries(numOfTimeSeries()-1);
        }
    }
    
    /**
     * Shows or hides this the index-th series depending on the value of
     * parameter {@code show}.
     *
     * @param index index of the time series
     * @param show <code>true</code> to show the series.
     */
    public void setVisible(int index, boolean show){
       this.visible.set(index,show);
    }
    
    /**
     * Invoked by Swing to draw the panel.
     * 
     * @param g the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        super.paint(g);
        // The antialiasing is activated  
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // The origin is updated taking into account the current size of the panel
        this.originX = this.getWidth()/2;
        this.originY = this.getHeight()/2;
        //The time axis is drawn
        this.drawTimeAxis(g2d);
        //The limits are drawn
        this.drawLimits(g2d);
        //The time series is drawn taking into account the current time        
        for (int i = 0; i < graph.size(); i++) {
            if (visible.get(i)) {
                // The transformation is done in reverse order: first we move the 
                // central axis to (x, 0), then we scale, then we move to its 
                // original position + displacement
                g2d.setTransform(AffineTransform.getTranslateInstance(originX - (time * timeScale) + transformInfo.get(i).tx, originY));
                g2d.scale(1, transformInfo.get(i).sy);
                g2d.translate(0, transformInfo.get(i).ty);
                g2d.setColor(colors[i % colors.length]);
                g2d.draw(graph.get(i));
            }
        }
    }
    
    /**
     * Draws the time axis.
     * 
     * @param g2d the <code>Graphics2D</code> context in which to paint
     */
    private void drawTimeAxis(Graphics2D g2d) {
        float dash[] = {10, 10};
        BasicStroke sk = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dash, 0);
        Stroke currentStroke = g2d.getStroke();
        g2d.setStroke(sk);
        //X axis
        g2d.setTransform(AffineTransform.getTranslateInstance(originX, 0));        
        g2d.setColor(Color.red);
        g2d.drawLine(0, 0, 0, 4000);
        //Y axis
        g2d.setTransform(AffineTransform.getTranslateInstance(0, originY));
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(0, 0, 4000, 0);    
        if(!transformInfo.isEmpty()){
            g2d.drawString(String.valueOf(transformInfo.get(0).ty),7,4);
        }
        g2d.setStroke(currentStroke);
    }
    
    /**
     * Draws the limit lines.
     * 
     * @param g2d the <code>Graphics2D</code> context in which to paint
     */
    private void drawLimits(Graphics2D g2d) {
        if (!this.isEmpty() && limitsLinesY!=null ) {
            double yA = ((transformInfo.get(0).ty - limitsLinesY[0])*transformInfo.get(0).sy)+originY;            
            double yB = ((transformInfo.get(0).ty - limitsLinesY[1])*transformInfo.get(0).sy)+originY;
            double yC = ((transformInfo.get(0).ty - limitsLinesY[2])*transformInfo.get(0).sy)+originY;
            double yD = ((transformInfo.get(0).ty - limitsLinesY[3])*transformInfo.get(0).sy)+originY;
            
            float dash[] = {3, 3};
            BasicStroke sk = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dash, 0);
            Stroke currentStroke = g2d.getStroke();
            g2d.setStroke(sk);
            g2d.setTransform(new AffineTransform());
            //Limits
            g2d.setColor(Color.GRAY);
            g2d.drawLine(0, (int) yA, 4000, (int) yA);
            g2d.drawLine(0, (int) yD, 4000, (int) yD);
            g2d.drawString(String.valueOf(limitsLinesY[0]),7,(int)yA+4);
            g2d.drawString(String.valueOf(limitsLinesY[3]),7,(int)yD+4);
            g2d.setColor(new Color(178, 178, 178));
            g2d.drawLine(0, (int) yB, 4000, (int) yB);
            g2d.drawLine(0, (int) yC, 4000, (int) yC);
            g2d.setStroke(currentStroke);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * 
     */
    public class AffineTransformInfo {

        public double tx, ty;
        public double sx, sy;
        
        public AffineTransformInfo(double tx, double ty, double sx, double sy) {
            this.setTransformValues(tx, ty, sx, sy);
        }
        
        public final void setTransformValues(double tx, double ty, double sx, double sy) {
            this.tx = tx;
            this.ty = ty;
            this.sx = sx;
            this.sy = sy;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
