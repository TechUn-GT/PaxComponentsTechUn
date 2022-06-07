/*
 * ===========================================================================================
 * = COPYRIGHT
 *          PAX Computer Technology(Shenzhen) CO., LTD PROPRIETARY INFORMATION
 *   This software is supplied under the terms of a license agreement or nondisclosure
 *   agreement with PAX Computer Technology(Shenzhen) CO., LTD and may not be copied or
 *   disclosed except in accordance with the terms in that agreement.
 *     Copyright (C) 2020-? PAX Computer Technology(Shenzhen) CO., LTD All rights reserved.
 * Description: // Detail description about the function of this module,
 *             // interfaces with the other modules, and dependencies.
 * Revision History:
 * Date                  Author	                 Action
 * 20200525  	         JackHuang               Create
 * ===========================================================================================
 */

package com.techun.paxcomponents.sdk_pax.module_emv.param;

import android.util.Log;

import com.techun.paxcomponents.sdk_pax.module_emv.utils.MiscUtils;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.clss.PayPassAid;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.clss.PayWaveParam;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.common.CapkParam;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.common.Config;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.contact.EmvAid;

import java.util.ArrayList;


public class EmvProcessParam {

    private EmvTransParam emvTransParam;
    private ArrayList<EmvAid> emvAidList;
    private ArrayList<PayPassAid> payPassAidList;
    private PayWaveParam payWaveParam;
    private CapkParam capkParam;
    private Config termConfig;

    private EmvProcessParam(Builder builder) {
        this.emvTransParam = builder.emvTransParam;
        this.capkParam = builder.capkParam;
        this.emvAidList = builder.emvAidList;
        this.payPassAidList = builder.payPassAidList;
        this.payWaveParam = builder.payWaveParam;
        this.termConfig = builder.termConfig;
    }

    public EmvTransParam getEmvTransParam() {
        return emvTransParam;
    }

    public CapkParam getCapkParam() {
        return capkParam;
    }

    public ArrayList<EmvAid> getEmvAidList() {
        return emvAidList;
    }

    public ArrayList<PayPassAid> getPayPassAidList() {
        return payPassAidList;
    }

    public PayWaveParam getPayWaveParam() {
        return payWaveParam;
    }

    private static final String TAG = "EmvProcessParam";

    //TODO: AQUI SE AÑADIO ESTO PARA PODER SACAR LOS COUNTRY CODE
   /* public Config getTermConfig() {
        Config mConfig;
        mConfig = termConfig;
        mConfig.setTerminalCountryCode("0320".getBytes());
        mConfig.setTransReferenceCurrencyCode("0320".getBytes());

        Log.e(TAG, "getTermConfig: " + mConfig.getMerchantNameAndLocation());
        return mConfig;
    }*/
    private void cargarConfiguracionesTerminal() {
//        configTerminal = new DBHelperRepository(mContext, false, true).getObtenerConfiguracionesDefault();
    }

    public Config getTermConfig() {
        Config mConfig;
        mConfig = termConfig;

        byte[] bcdCountryCode = MiscUtils.str2Bcd("0320");
        mConfig.setTerminalCountryCode(bcdCountryCode);
        mConfig.setTransReferenceCurrencyCode(bcdCountryCode);

        Log.d(TAG, "getTermConfig: Country Code: " + BCDtoString(mConfig.getTerminalCountryCode()));
        return mConfig;
    }


    public static String BCDtoString(byte[] bcd) {

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < bcd.length; i++) {
            sb.append(BCDtoString(bcd[i]));
        }

        return sb.toString();
    }

    public static String BCDtoString(byte bcd) {
        StringBuffer sb = new StringBuffer();

        byte high = (byte) (bcd & 0xf0);
        high >>>= (byte) 4;
        high = (byte) (high & 0x0f);
        byte low = (byte) (bcd & 0x0f);

        sb.append(high);
        sb.append(low);

        return sb.toString();
    }

    public final static class Builder {
        private EmvTransParam emvTransParam;

        private ArrayList<EmvAid> emvAidList;
        private ArrayList<PayPassAid> payPassAidList;
        private PayWaveParam payWaveParam;

        private CapkParam capkParam;
        private Config termConfig;

        public Builder(EmvTransParam transParam, Config termConfigParam, CapkParam capkParam) {
            this.emvTransParam = transParam;
            this.termConfig = termConfigParam;

            Log.d(TAG, "toClssReaderParam: Country Code: " + BCDtoString(termConfig.getTerminalCountryCode()));

            this.capkParam = capkParam;
        }

        public Builder setEmvAidList(ArrayList<EmvAid> emvAidList) {
            this.emvAidList = emvAidList;
            return this;
        }

        public Builder setPayPassAidList(ArrayList<PayPassAid> payPassAidList) {
            this.payPassAidList = payPassAidList;
            return this;
        }

        public Builder setPayWaveParam(PayWaveParam payWaveParam) {
            this.payWaveParam = payWaveParam;
            return this;
        }

        public EmvProcessParam create() {
            return new EmvProcessParam(this);
        }

    }


}
