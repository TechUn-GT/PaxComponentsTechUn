package com.techun.paxcomponents.sdk_pax;

import android.util.Log;

import com.techun.paxcomponents.util.Util;

public class BaseTester {
    private static final String TAG = "BaseTester";
    private String childName = "";

    public BaseTester() {

    }

    protected void logTrue(String method) {
        try {
            childName = getClass().getSimpleName() + ".";
            String trueLog = childName + method;
            Util.INSTANCE.logsUtils("IPPITest " + trueLog, 0);
            Log.i("IPPITest", trueLog);
        } catch (Exception e) {
            Util.INSTANCE.logsUtils("logTrue mio: " + e.getMessage(), 1);
        }

    }

    protected void logErr(String method, String errString) {
        childName = getClass().getSimpleName() + ".";
        String errorLog = childName + method + "   errorMessage：" + errString;
        Util.INSTANCE.logsUtils("IPPITest " + errorLog, 1);
    }

    public void clear() {
//        GetObj.logStr = "";
    }
}
