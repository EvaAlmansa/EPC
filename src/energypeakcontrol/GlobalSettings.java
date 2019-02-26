package energypeakcontrol;

/**
 *
 * @author Jesús Chamorro Martínez (jesus@decsai.ugr.es)
 */
public class GlobalSettings {
    static private double zoom = 1.0;
    static private int pause_time = 500; //in milliseconds
    static private int y_axis_position = 230;
    static private float mfHighA = 220.0f;
    static private float mfHighB = 225.0f;
    static private float mfHighC = 235.0f;
    static private float mfHighD = 240.0f;
    
    
    static public double getZoom(){
        return zoom;
    }
    
    static public void setZoom(double zoom){
        if(zoom>0){
            GlobalSettings.zoom = zoom;
        }
    }

    public static int getPauseTime() {
        return pause_time;
    }

    public static void setPauseTime(int sleep_time) {
        if (pause_time > 0) {
            GlobalSettings.pause_time = sleep_time;
        }
    } 

    public static int getYAxisPosition() {
        return y_axis_position;
    }

    public static void setYAxisPosition(int position) {
        GlobalSettings.y_axis_position = position;
    }

    public static float getHighA() {
        return mfHighA;
    }

    public static void setHighA(float a) {
        GlobalSettings.mfHighA = a;
    }
    
    public static float getHighB() {
        return mfHighB;
    }

    public static void setHighB(float b) {
        GlobalSettings.mfHighB = b;
    }
    
    public static float getHighC() {
        return mfHighC;
    }

    public static void setHighC(float c) {
        GlobalSettings.mfHighC = c;
    }
    
    public static float getHighD() {
        return mfHighD;
    }

    public static void setHighD(float d) {
        GlobalSettings.mfHighD = d;
    }
    
}
