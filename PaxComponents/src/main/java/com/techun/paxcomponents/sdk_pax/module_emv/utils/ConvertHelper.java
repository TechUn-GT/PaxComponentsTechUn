package com.techun.paxcomponents.sdk_pax.module_emv.utils;


import com.techun.paxcomponents.sdk_pax.module_emv.utils.interfaces.IConvert;


public class ConvertHelper {
    private ConvertHelper() {
    }

    private static ConverterImp converterImp;


    public static IConvert getConvert() {
        if (converterImp == null) {
            converterImp = new ConverterImp();
        }
        return converterImp;
    }
}