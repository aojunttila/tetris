package Utils;
public class UtilTester{
    public UtilTester(){
        Util.colorPrint("hi guys",100,200,0);
        Util.colorPrint("hi guys 2",10,20,200);
        Util.colorPrint("hi guys 3",200,20,200);
        Util ut=new Util();
        ut.startTimerChannel(0);
        ut.startTimerChannel(1);
        ut.stopTimerChannel(1,false);
        ut.stopTimerChannel(0,true);
        Util.getObjectSize(ut);
    }

    public static void main(String[]args){
        new UtilTester();
    }
}