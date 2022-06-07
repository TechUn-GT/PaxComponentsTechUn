package com.techun.paxcomponents.sdk_pax.module_emv.utils;

import static com.pax.dal.entity.ETermInfoKey.AP_VER;
import static com.pax.dal.entity.ETermInfoKey.SN;

import android.util.Log;

import com.pax.dal.entity.EBeepMode;
import com.pax.dal.entity.ETermInfoKey;

import java.util.HashMap;
import java.util.Map;


public class Device {
    private static final String TAG = "Device";

    private Device() {

    }

    /**
     * beep 成功
     */
    public static void beepOk() {
        DemoApp.getDal().getSys().beep(EBeepMode.FREQUENCE_LEVEL_3, 100);
        DemoApp.getDal().getSys().beep(EBeepMode.FREQUENCE_LEVEL_4, 100);
        DemoApp.getDal().getSys().beep(EBeepMode.FREQUENCE_LEVEL_5, 100);
    }

    public static Map<String, String> paramInternos() {
        Map<ETermInfoKey, String> param = DemoApp.getDal().getSys().getTermInfo();
        Map<String, String> paramR = new HashMap<>();

        paramR.put("SN", param.get(SN));
        paramR.put("AP_VER", param.get(AP_VER));

        return paramR;
    }
    public static void beepApproved() {
        DemoApp.getDal().getSys().beep(EBeepMode.FREQUENCE_LEVEL_5, 400);
        DemoApp.getDal().getSys().beep(EBeepMode.FREQUENCE_LEVEL_5, 500);
    }

    /**
     * beep 失败
     */
    public static void beepErr() {
        DemoApp.getDal().getSys().beep(EBeepMode.FREQUENCE_LEVEL_6, 200);
    }

    /**
     * beep 提示音
     */

    public static void beepPrompt() {
        DemoApp.getDal().getSys().beep(EBeepMode.FREQUENCE_LEVEL_6, 50);
    }

    public static void beepReadOK() {
        DemoApp.getDal().getSys().beep(EBeepMode.FREQUENCE_LEVEL_6, 800);
    }

    public static int updateAPK(String PATH) {
        int result = DemoApp.getDal().getSys().installApp(PATH);
        Log.e(TAG, "onReceive: valor: " + result );
        return result;
    }
}
