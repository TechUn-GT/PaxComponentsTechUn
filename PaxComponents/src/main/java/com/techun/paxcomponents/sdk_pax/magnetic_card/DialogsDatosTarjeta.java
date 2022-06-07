package com.techun.paxcomponents.sdk_pax.magnetic_card;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

public class DialogsDatosTarjeta extends DialogFragment  {

    private static final String TAG = "DialogsDatosTarjeta";
    private static DialogsDatosTarjeta instance;
    private static DialogsDatosTarjeta exampleDialog;

    private CountDownTimer ReadCardTimeOut = new CountDownTimer(30000, 30000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            Log.w(TAG, "Read Card TimeOUT");
            cerrar();
        }
    };

    private void cerrar() {
        try {
            close();
            Log.e(TAG, "cerrar: Notificacion correcta");
        } catch (Exception ex) {
            Log.d(TAG, "onCreateView: " + ex.getMessage());
        }
    }

    private void close() {
        ReadCardTimeOut.cancel();
        LectorBanda.GetInstance().Stop();
    }

    //Identificador de opcion // identificador de pos
    public static DialogsDatosTarjeta display(FragmentManager fragmentManager) {
        if (instance == null) {
            exampleDialog = new DialogsDatosTarjeta();
            exampleDialog.setCancelable(false);
            exampleDialog.show(fragmentManager, TAG);
        }


        return exampleDialog;
    }

    public static DialogsDatosTarjeta getInstance() {
        return instance;
    }

    public int read(Context context) {
        int intValue = 0;


        try {
            //this.ReadCardTimeOut.start(); //Inicializar el Timer de TimeOUT de lectura de tarjeta.
            LectorBanda.GetInstance().Read_Card(context);
            //close();

        } catch (Exception ex) {
            Log.d(TAG, "onCreateView: " + ex.getMessage());
        }

        return intValue;
    }

}
