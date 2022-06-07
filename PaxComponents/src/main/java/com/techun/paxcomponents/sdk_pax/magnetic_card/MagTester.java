package com.techun.paxcomponents.sdk_pax.magnetic_card;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.pax.dal.IDAL;
import com.pax.dal.IMag;
import com.pax.dal.entity.TrackData;
import com.pax.dal.exceptions.MagDevException;
import com.pax.neptunelite.api.NeptuneLiteUser;
import com.techun.paxcomponents.sdk_pax.BaseTester;

public class MagTester extends BaseTester {

    private static MagTester magTester;

    private IMag iMag;
    private static IDAL dal;

    private MagTester(Context context) {
        try {
            long start = System.currentTimeMillis();
            dal = NeptuneLiteUser.getInstance().getDal(context);
            Log.i("Test", "get dal cost:" + (System.currentTimeMillis() - start) + " ms");
            iMag = dal.getMag();
            System.out.println("xD");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "error occurred,DAL is null.", Toast.LENGTH_LONG).show();
        }
    }

    public static MagTester getInstance(Context context) {
        if (magTester == null) {
            magTester = new MagTester(context);
        }
        return magTester;
    }

    public void open() {
        try {
            iMag.open();
            logTrue("open");
        } catch (MagDevException e) {
            e.printStackTrace();
            logErr("open", e.toString());
        }
    }

    public void close() {
        try {
            iMag.close();
            logTrue("close");
        } catch (MagDevException e) {
            e.printStackTrace();
            logErr("close", e.toString());
        }
    }

    // Reset magnetic stripe card reader, and clear buffer of magnetic stripe card.
    public void reset() {
        try {
            iMag.reset();
            logTrue("reSet");
        } catch (MagDevException e) {
            e.printStackTrace();
            logErr("reSet", e.toString());
        }
    }

    // Check whether a card is swiped
    public boolean isSwiped() {
        boolean b = false;
        try {
            b = iMag.isSwiped();
            // logTrue("isSwiped");
        } catch (MagDevException e) {
            e.printStackTrace();
            logErr("isSwiped", e.toString());
        }
        return b;
    }

    public TrackData read() {
        try {
            TrackData trackData = iMag.read();
            logTrue("read");
            return trackData;
        } catch (MagDevException e) {
            e.printStackTrace();
            reset();
            logErr("read", e.toString());
            return null;
        }
    }

}
