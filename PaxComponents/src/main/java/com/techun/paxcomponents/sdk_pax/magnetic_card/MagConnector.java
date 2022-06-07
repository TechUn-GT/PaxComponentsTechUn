package com.techun.paxcomponents.sdk_pax.magnetic_card;

import android.content.Context;
import android.util.Log;

import com.pax.dal.entity.TrackData;


public final class MagConnector {
    private static final String TAG = "MagConnector";
    private TrackData trackData;

    public MagConnector(Context context) {
        try {
            trackData = MagTester.getInstance(context).read();
        } catch (Exception e) {
            Log.e(TAG, "MagConnector: Error lectura: " + e.getMessage() );
            trackData = null;
        }

    }

    public final boolean isNullGetTrack() {
        return trackData == null;
    }

    public final boolean isTrack1OK() {
        return (getResultCode() & 0x01) == 0x01;
    }

    public final String getTrack1() {
        if (trackData == null) return "";
        return trackData.getTrack1();
    }

    public final boolean isTrack2OK() {
        return (getResultCode() & 0x02) == 0x02;
    }

    public final String getTrack2() {
        if (trackData == null) return "";
        return trackData.getTrack2();
    }

    final boolean isTrack3OK() {
        return (getResultCode() & 0x04) == 0x04;
    }

    final String getTrack3() {
        if (trackData == null) return "";
        return trackData.getTrack3();
    }

    private int getResultCode() {
        if (trackData == null) return 0;
        return trackData.getResultCode();
    }

    public final String isReadCardError() {
        if (getResultCode() == 0) {
            return "read card error";
        }
        return null;
    }


}
