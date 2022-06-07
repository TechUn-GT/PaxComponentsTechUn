package com.techun.paxcomponents.sdk_pax.module_emv.utils.interfaces;

import java.util.HashMap;

public interface ILectura {

    void onManual(HashMap<Object, String> tarjeta);

    void onEMV();

    void onErrorEMV(String msg);
}
