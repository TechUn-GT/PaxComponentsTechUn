package com.techun.paxcomponents.sdk_pax.magnetic_card;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;


public class LectorBanda {
    private static final String TAG = "MagRead";
    private Context context;
    private String[] tracks = {"", "-", ""};
    private onTracks onTrack;
    private cardReaderThread magReadThread;
    static LectorBanda instance = null;
    boolean isInTransaction = false;
    Activity ac;

    private LectorBanda() {
    }

    public static LectorBanda GetInstance()
    {
        if (instance == null)
            instance = new LectorBanda();
        return instance;
    }

    public boolean Start(Context context, onTracks onTrack) throws Exception {
        if (isInTransaction)
            throw new Exception("LectorBanda. Ya se inicio una transaccion Previa");
        this.context = context;
        this.onTrack = onTrack;
        isInTransaction = true;
        startThread();
        return true;
    }

    public boolean Read_Card(Context context) throws Exception{
        if (isInTransaction)
            throw new Exception("LectorBanda. Ya se inicio una transaccion Previa");
        this.context = context;
        isInTransaction = true;
        startThread();
        return true;
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public synchronized void handleMessage(Message msg) {
            String[] tracks = (String[]) msg.obj;

            if (tracks.length > 1) onTrack.onTrack(true, tracks);
            else onTrack.onTrack(false, null);

        /*    if (msg.what == 0) {
                parts = msg.obj.toString().split("-");
                if (parts.length == 2) {
                    Stop();
                    onTrack.onTrack(true, parts);
                } else {
                    //Error
                    Stop();
                    onTrack.onTrack(false, null);
                }
            }*/
        }
    };


    private void startThread() {
        try {
            magReadThread = new cardReaderThread(this.context);
            MagTester.getInstance(this.context).open();
            MagTester.getInstance(this.context).reset();
            magReadThread.start();
        } catch (Exception ex) {
            Log.d(TAG, "start: Error: " + ex.getMessage());
        }
    }

    public void Stop() {
        try {
            if (magReadThread != null) {
                MagTester.getInstance(this.context).close();
                magReadThread.interrupt();
                magReadThread = null;
                isInTransaction = false;
            }
            Log.d(TAG, "Stop: Entro aqui!");

        } catch (Exception ex) {
            Log.i(TAG, "Stop: EXCEPTION: " + ex.getMessage());
        }

    }
/*
    private class cardReaderThread extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                while (!Thread.interrupted()) {
                    if (!MagTester.getInstance().isSwiped()) {
                        continue;
                    }

                    try {
                        MagConnector m = null;

                        try {
                            m = new MagConnector();
                        } catch (Exception ex) {
                            Log.e(TAG, "run new: " + ex.getMessage());
                        }


                        if (m.isNullGetTrack()) {
                            SystemClock.sleep(100);
                            continue;
                        }

                        String resStr = "";

                        if (m.isReadCardError() != null) {
                            Message.obtain(handler, 0, m.isReadCardError()).sendToTarget();
                            continue;
                        }

                        if (m.isTrack1OK()) {
                            Log.d(TAG, "Track 1 cargado");
                            resStr += tracks[0] + m.getTrack1();
                            Log.d(TAG, "run: ");
                            Log.d(TAG, resStr);
                            Message.obtain(handler, 0, resStr).sendToTarget();
                        } else {
                            Log.e(TAG, "run: Track 1 No encontrado");
                        }

                        if (m.isTrack2OK()) {
                            Log.d(TAG, "Track 2 cargado");
                            resStr += tracks[1] + m.getTrack2();
                            Log.d(TAG, resStr);
                            Message.obtain(handler, 0, resStr).sendToTarget();
                        } else {
                            Log.e(TAG, "run: Track 2 No encontrado");
                        }

                    } catch (Exception ex) {
                        Log.d(TAG, "run: Error Lectura + " + ex.getMessage());
                        MagTester.getInstance().reset();
                        MagTester.getInstance().open();
                        MagTester.getInstance().reset();
                    }
                    break;
                }
            } catch (Exception e) {
                Log.e(TAG, "ERROR", e);
            }

        }
    }*/

    private class cardReaderThread extends Thread {

        Context context;

        public cardReaderThread (Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            super.run();
            try {
                while (!Thread.interrupted()) {
                    if (!MagTester.getInstance(context).isSwiped()) {
                        SystemClock.sleep(100);
                        continue;
                    }

                    try {
                        MagConnector m = null;

                        try {
                            m = new MagConnector(context);
                        } catch (Exception ex) {
                            Log.e(TAG, "run new: " + ex.getMessage());
                        }


                        if (m.isNullGetTrack()) {
                            SystemClock.sleep(100);
                            continue;
                        }

                        String resStr = "";

                        if (m.isReadCardError() != null) {
                            Message.obtain(handler, 0, m.isReadCardError()).sendToTarget();
                            continue;
                        }

                        if (m.isTrack1OK()) {
                            Log.d(TAG, "Track 1 cargado");
                            tracks[0] = m.getTrack1();
                            Log.i(TAG, "TRACK1: " + tracks[0]);
                        } else {
                            Log.e(TAG, "Track1 No encontrado");
                        }

                        if (m.isTrack2OK()) {
                            tracks[1] = m.getTrack2();
                            Log.i(TAG, "TRACK2: " + tracks[1]);
                            String[] track2_separacion = tracks[1].split("=");
                            Log.i(TAG, "PAN_NO: " + track2_separacion[0]);
                            Log.i(TAG, "EXP_DATE: " + track2_separacion[1].substring(0,4));
                        } else {
                            Log.e(TAG, "Track2 No encontrado");
                        }

                        if (tracks[0].length() >0 || tracks[1].length() >0) {
                            //Message.obtain(handler, 0, tracks).sendToTarget();
                            //Toast.makeText(context, "LECTURA EXITOSA", Toast.LENGTH_SHORT).show();
                            MagTester.getInstance(context).clear();
                            MagTester.getInstance(context).close();
                        } else {
                            //Toast.makeText(context, "ERROR DE LECTURA DE BANDA", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "run: Error Lectura + ");
                            MagTester.getInstance(context).reset();
                            MagTester.getInstance(context).open();
                            MagTester.getInstance(context).reset();
                        }

                    } catch (Exception ex) {
                        Log.d(TAG, "run: Error Lectura + " + ex.getMessage());
                        MagTester.getInstance(context).reset();
                        MagTester.getInstance(context).open();
                        MagTester.getInstance(context).reset();
                        magReadThread.start();
                    }
                    break;
                }
            } catch (Exception e) {
                Log.e(TAG, "ERROR", e);
            }
            isInTransaction = false;
        }
    }


}
