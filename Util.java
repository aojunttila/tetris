package Utils;
public class Util {
    long startTime;
    long[]startTimes=new long[256];

    public Util(){

    }

    public void startTimer(){
        startTime=System.nanoTime();
    }

    public float stopTimer(boolean print){
        float duration=(float)(System.nanoTime()-startTime);
        duration=(float)(Math.round(duration/1000))/1000;
        if(print){System.out.println("Finished in "+duration+" ms");}
        return duration;
    }

    public void startTimerChannel(int channel){
        startTimes[channel]=System.nanoTime();
    }

    public float stopTimerChannel(int channel,boolean print){
        float duration=(float)(System.nanoTime()-startTimes[channel]);
        duration=duration/1000000;
        if(print){System.out.println("Channel "+channel+" finished in "+duration+" ms");}
        return duration;
    }










    /**
     * Return the input object as a colored string, with colors r, g, b
     * @param input Input object to convert to string
     * @param r Red value for string
     * @param g Green value for string
     * @param b Blue value for string
     * @return The colored text string
     */
    public static String colorText(Object input,int r,int g,int b){
        return "\033[38;2;"+r+";"+g+";"+b+"m"+input;
    }

    /**
     * Prints the input object as a colored string, with colors r, g, b
     * @param input Input object to print
     * @param r Red value for string
     * @param g Green value for string
     * @param b Blue value for string
     */
    public static void colorPrint(String input,int r,int g,int b){
        System.out.println("\033[38;2;"+r+";"+g+";"+b+"m"+input);
    }
    

}