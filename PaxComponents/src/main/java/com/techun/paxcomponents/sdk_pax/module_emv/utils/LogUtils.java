package com.techun.paxcomponents.sdk_pax.module_emv.utils;

/*
 * ===========================================================================================
 * = COPYRIGHT
 *          PAX Computer Technology(Shenzhen) CO., LTD PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or nondisclosure
 *   agreement with PAX Computer Technology(Shenzhen) CO., LTD and may not be copied or
 *   disclosed except in accordance with the terms in that agreement.
 *     Copyright (C) 2019-? PAX Computer Technology(Shenzhen) CO., LTD All rights reserved.
 * Description: // Detail description about the function of this module,
 *             // interfaces with the other modules, and dependencies.
 * Revision History:
 * Date                  Author	                 Action
 * 20190108  	         guanjw                  Create
 * ===========================================================================================
 */


import com.techun.paxcomponents.BuildConfig;
import com.techun.paxcomponents.util.Util;

public class LogUtils {
    private static final boolean IS_DEBUG = BuildConfig.DEBUG;

    private LogUtils() {

    }

    public static void e(String tag, Object msg) {
        if (IS_DEBUG) {
            Util.INSTANCE.logsUtils(tag + " " + msg, 1);
//            Log.e(tag, "" + msg);
        }
    }

    public static void w(String tag, Object msg) {
        if (IS_DEBUG) {
            Util.INSTANCE.logsUtils(tag + " " + msg, 2);

//            Log.w(tag, "" + msg);
        }
    }

    public static void i(String tag, Object msg) {
        if (IS_DEBUG) {
            Util.INSTANCE.logsUtils(tag + " " + msg, 4);

//            Log.i(tag, "" + msg);
        }
    }

    public static void d(String tag, Object msg) {
        if (IS_DEBUG) {
            Util.INSTANCE.logsUtils(tag + " " + msg, 0);

//            Log.d(tag, "" + msg);
        }
    }

    public static void v(String tag, Object msg) {
        if (IS_DEBUG) {
            Util.INSTANCE.logsUtils(tag + " " + msg, 3);

//            Log.v(tag, "" + msg);
        }
    }

    public static void e(String tag, String msg, Throwable th) {
        Util.INSTANCE.logsUtils(tag + " " + msg + " " + th, 1);
//        Log.e(tag, msg, th);
    }

    public static void e(String tag, Exception exception) {
        Util.INSTANCE.logsUtils(tag + " " + exception, 1);

//        Log.e(tag, "", exception);
    }

    public static void w(String tag, String msg, Throwable th) {
        if (IS_DEBUG) {
//            Log.w(tag, msg, th);
            Util.INSTANCE.logsUtils(tag + " " + msg + " " + th, 2);

        }
    }

    public static void i(String tag, String msg, Throwable th) {
        if (IS_DEBUG) {
//            Log.i(tag, msg, th);
            Util.INSTANCE.logsUtils(tag + " " + msg + " " + th, 4);

        }
    }

    public static void d(String tag, String msg, Throwable th) {
        if (IS_DEBUG) {
//            Log.d(tag, msg, th);
            Util.INSTANCE.logsUtils(tag + " " + msg + " " + th, 0);

        }

    }

    public static void v(String tag, String msg, Throwable th) {
        if (IS_DEBUG) {
//            Log.v(tag, msg, th);

            Util.INSTANCE.logsUtils(tag + " " + msg + " " + th,3);
        }
    }

    public static String ArrToHexString(byte[] data, int len) {
        if (len == 0)
            len = data.length;
        StringBuilder Data = new StringBuilder();
        for (int index = 0; index < len; index++) {
            Data.append(Integer.toHexString(data[index]));
        }
        return Data.toString();
    }

}

