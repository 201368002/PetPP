package com.example.tacademy.petpp.util;

/**
 * Created by Tacademy on 2017-01-20.
 */
public class Log {
    private static Log ourInstance = new Log();

    public static Log getInstance() {
        return ourInstance;
    }

    private Log() {
    }

    // LOG
    public void log(String msg){
        android.util.Log.i("LOGMSG", "=========================================");
        android.util.Log.i("LOGMSG", ""+msg);    // ""+를 해주는 이유 : null을 출력하면 죽으니까!!
        android.util.Log.i("LOGMSG", "=========================================");
    }

    public void menuLog(String msg){
        android.util.Log.i("MENULOG", "=========================================");
        android.util.Log.i("MENULOG", ""+msg);    // ""+를 해주는 이유 : null을 출력하면 죽으니까!!
        android.util.Log.i("MENULOG", "=========================================");
    }

    public void gpsLog(String msg){
        android.util.Log.i("GPSLOG", "=========================================");
        android.util.Log.i("GPSLOG", ""+msg);    // ""+를 해주는 이유 : null을 출력하면 죽으니까!!
        android.util.Log.i("GPSLOG", "=========================================");
    }
}
