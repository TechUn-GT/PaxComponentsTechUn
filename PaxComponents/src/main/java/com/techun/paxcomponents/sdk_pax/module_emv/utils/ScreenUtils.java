package com.techun.paxcomponents.sdk_pax.module_emv.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtils {

    /**
     * set screen light off
     */
    public static void lightOff(Activity activity) {
        if (activity == null) {
            return;
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * set screen light on
     */
    public static void lightOn(Activity activity) {
        if (activity == null) {
            return;
        }
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
    }

    public static int dp2px(Context context, float dpValue) {
        if (context == null) {
            return 0;
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float densityVal = dm.density;

        return (int) (dpValue * densityVal + 0.5f);
    }
}
