package com.techun.paxcomponents.sdk_pax.module_emv.utils;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.pax.dal.IDAL;
import com.pax.jemv.device.DeviceManager;
import com.pax.neptunelite.api.NeptuneLiteUser;
import com.techun.paxcomponents.sdk_pax.module_emv.process.EmvBase;

import java.util.concurrent.ExecutorService;

public class DemoApp extends Application {

    private static IDAL dal;
    private static Context appContext;
    private static final String TAG = "DemoApp";
    private static ParamManager mParamManager;
    private static DemoApp instance = null;
    private ExecutorService backgroundExecutor;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        dal = getDal();

        // 2020-06-26 Se debe registar el Neptune Device Implementation, el SDK EMV llama funciones de esta implementacion
        DeviceManager.getInstance().setIDevice(DeviceImplNeptune.getInstance());

        instance = this;
        handler = new Handler();
        registerActivityLifecycleCallbacks(new AppActLifecycleCallback());
        backgroundExecutor = ThreadPoolManager.getInstance().getExecutor();
        initEmvModule();

    }

    public static ParamManager getParamManager() {
        return mParamManager;
    }

    public static DemoApp getApp() {
        if (instance == null)
            instance = new DemoApp();
        return instance;
    }

    public static IDAL getDal() {
        if (dal == null) {
            try {
                long start = System.currentTimeMillis();
                dal = NeptuneLiteUser.getInstance().getDal(appContext);
                // FileParse.parseAidFromAssets(appContext, "aid.ini");
                // FileParse.parseCapkFromAssets(appContext, "capk.ini");
                // FileParse.parseEmvTerminalFromAssets(appContext, "emvterminal.ini");
                Log.i("Test", "get dal cost:" + (System.currentTimeMillis() - start) + " ms");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(appContext, "error occurred,DAL is null.", Toast.LENGTH_LONG).show();
            }
        }
        return dal;
    }

    public void runInBackground(final Runnable runnable) {
        backgroundExecutor.execute(runnable);
    }

    public void runOnUiThread(final Runnable runnable) {
        handler.post(runnable);
    }

    private void initEmvModule() {
        runInBackground(() -> {
            LogUtils.d(TAG, " initEmvModule start");
            long startT = System.currentTimeMillis();
            mParamManager = ParamManager.getInstance(instance);
            long endT = System.currentTimeMillis();
            LogUtils.d(TAG, "initEmvModule  end:" + (endT - startT));
            EmvBase.loadLibrary();
        });
    }

}
