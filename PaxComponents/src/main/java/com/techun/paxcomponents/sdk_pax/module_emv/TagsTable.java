package com.techun.paxcomponents.sdk_pax.module_emv;



import com.techun.paxcomponents.sdk_pax.module_emv.utils.MiscUtils;

import java.util.HashMap;

/**
 * Created by xionggd on 2017/8/16.
 */
public class TagsTable {
    public static final int T_9F09_APP_VER = 0x9F09;

    public static final int T_DF8117_CARD_DATA = 0xDF8117;
    public static final int T_DF8118_CVM_REQ = 0xDF8118;
    public static final int T_DF8119_CVM_NO = 0xDF8119;
    public static final int T_DF811F_DEF_UDOL = 0xDF811F;
    public static final int T_DF811A_SEC = 0xDF811A;
    public static final int T_DF811E_MAG_CVM_REQ = 0xDF811E;
    public static final int T_DF812C_MAG_CVM_NO = 0xDF812C;

    public static final int T_9F02_AMOUNT = 0x9F02;
    public static final int T_9F03_AMOUNT_OTHER = 0x9F03;

    public static final int T_9C_TRANS_TYPE = 0x9C;
    public static final int T_9A_TRANS_DATE = 0x9A;
    public static final int T_9F21_TRANS_TIME = 0x9F21;

    //TAC Online
    public static final int T_DF8120_TERM_DEFAULT = 0xDF8120;
    public static final int T_DF8121_TERM_DENIAL = 0xDF8121;
    public static final int T_DF8122_TERM_ONLINE = 0xDF8122;

    //limit  set for AID
    public static final int T_DF8123_FLOOR_LIMIT = 0xDF8123;
    public static final int T_DF8124_TRANS_LIMIT = 0xDF8124;
    public static final int T_DF8125_TRANS_CVM_LIMIT = 0xDF8125;
    public static final int T_DF8126_CVM_LIMIT = 0xDF8126;

    public static final int T_DF811D_MAX_TORN = 0xDF811D;

    public static final int T_9F10_ISSUER_APP_DATA = 0x9F10;

    public static final int T_9F1A_COUNTRY_CODE = 0x9F1A;
    public static final int T_5F2A_CURRENCY_CODE = 0x5F2A;

    public static final int T_DF811B_KERNEL_CFG = 0xDF811B;
    //FRS 2020-08-23 El la constante T_82_AIP tenia asignado el valor 0x84 , debe ser 0x82
    public static final int T_82_AIP = 0x82; //Application Interchange Profile

    public static final int T_84_DEDICATED_FILENAME = 0x84;

    public static final int T_8F_CAPK_ID = 0x8F;
    public static final int T_4F_CAPK_RID = 0x4F;

    public static final int T_9F5A_PRO_ID = 0x9F5A;


    public static final int T_5A_PAN = 0x5A;

    public static final int T_56_TRACK1 = 0x56;
    public static final int T_57_TRACK2 = 0x57;
    public static final int T_9F6B_TRACK2_1 = 0x9F6B;
    public static final int T_9F6E_FORM_FACTOR_INDICATOR = 0x9F6E;

    public static final int T_5F20_CARDHOLDER_NAME = 0x5F20;

    public static final int T_5F24_APP_EXPIRATION_DATE = 0x5F24;
    public static final int T_5F25_APP_EFFECTIVE_DATE = 0x5F25;
    public static final int T_5F34_PAN_SEQ_NO = 0x5F34;
    public static final int T_50_APP_LABEL = 0x50;
    public static final int T_95_TVR = 0x95;
    public static final int T_9B_TSI = 0x9B;
    public static final int T_8E_CVM = 0x8E;
    public static final int T_9F34_CVM_RESULTS = 0x9F34;
    public static final int T_9F36_ATC = 0x9F36;
    public static final int T_9F37_UNPREDICTABLE_NUMBER = 0x9F37;
    public static final int T_9F26_APP_CRYPTO = 0x9F26;
    public static final int T_9F12_APP_NAME = 0x9F12;

    public static final int T_DF8115_ERROR_INDICATION = 0xDF8115;
    public static final int T_DF8116_USR_INTF_REQUEST_DATA = 0xDF8116;
    public static final int T_DF8129_LIST = 0xDF8129;

    public static final int T_9F27_CRYPTO = 0x9F27;

    public static final int T_5F57_ACCOUNT_TYPE = 0x5F57;
    public static final int T_9F01_ACQUIRER_ID = 0x9F01;
    public static final int T_9F1E_INTER_DEV_NUM = 0x9F1E;
    public static final int T_9F66_TTQ = 0x9F66;
    public static final int T_9F15_MERCHANT_CATEGORY_CODE = 0x9F15;
    public static final int T_9F16_MERCHANT_ID = 0x9F16;
    public static final int T_9F4E_MERCHANT_NAME_LOCATION = 0x9F4E;
    public static final int T_9F33_TERMINAL_CAPABILITY = 0x9F33;
    public static final int T_9F1C_TERMINAL_ID = 0x9F1C;
    public static final int T_9F7E_MOB_SUP = 0x9F7E;

    public static final int T_DF8104_BALANCE_BEFORE_GAC = 0xDF8104;
    public static final int T_DF8105_BALANCE_AFTER_GAC = 0xDF8105;
    public static final int T_DF812D_MESS_HOLD_TIME = 0xDF812D;

    public static final int T_DF8108_DS_AC_TYPE = 0xDF8108;
    public static final int T_DF60_DS_INPUT_CARD = 0xDF60;
    public static final int T_DF8109_DS_INPUT_TERMINAL = 0xDF8109;
    public static final int T_DF62_DS_ODS_INFO = 0xDF62;
    public static final int T_DF810A_DS_ODS_READER = 0xDF810A;
    public static final int T_DF63_DS_ODS_TERMINAL = 0xDF63;

    public static final int T_DF8110_FST_WRITE = 0xDF8110;
    public static final int T_DF8112_READ = 0xDF8112;
    public static final int T_FF8102_WRiTE_BEFORE_AC = 0xFF8102;
    public static final int T_FF8103_WRiTE_AFTER_AC = 0xFF8103;
    public static final int T_DF8127_TIMEOUT = 0xDF8127;

    public static final int T_9F40_ADDITIONAL_CAPABILITY = 0x9F40;
    public static final int T_9F41_TRAN_SEQUENCE_COUNTER = 0x9F41;
    public static final int T_9F5C_DS_OPERATOR_ID = 0x9F5C;

    private static final HashMap<Integer, byte[]> byteArrTags = new HashMap<>();

    private static void addConvertTag(int tag)
    {
        byteArrTags.put(tag, MiscUtils.int2VarByteArray(tag));
    }
    private static void FiilTagsArray()
    {
        addConvertTag(T_9F09_APP_VER);
        addConvertTag(T_DF8117_CARD_DATA);
        addConvertTag(T_DF8118_CVM_REQ);
        addConvertTag(T_DF8119_CVM_NO);
        addConvertTag(T_DF811F_DEF_UDOL);
        addConvertTag(T_DF811A_SEC);
        addConvertTag(T_DF811E_MAG_CVM_REQ);
        addConvertTag(T_DF812C_MAG_CVM_NO);
        addConvertTag(T_9F02_AMOUNT);
        addConvertTag(T_9F03_AMOUNT_OTHER);
        addConvertTag(T_9C_TRANS_TYPE);
        addConvertTag(T_9A_TRANS_DATE);
        addConvertTag(T_9F21_TRANS_TIME);
        addConvertTag(T_DF8120_TERM_DEFAULT);
        addConvertTag(T_DF8121_TERM_DENIAL);
        addConvertTag(T_DF8122_TERM_ONLINE);
        addConvertTag(T_DF8123_FLOOR_LIMIT);
        addConvertTag(T_DF8124_TRANS_LIMIT);
        addConvertTag(T_DF8125_TRANS_CVM_LIMIT);
        addConvertTag(T_DF8126_CVM_LIMIT);
        addConvertTag(T_DF811D_MAX_TORN);
        addConvertTag(T_9F10_ISSUER_APP_DATA);
        addConvertTag(T_9F1A_COUNTRY_CODE);
        addConvertTag(T_5F2A_CURRENCY_CODE);
        addConvertTag(T_DF811B_KERNEL_CFG);
        addConvertTag(T_82_AIP);
        addConvertTag(T_84_DEDICATED_FILENAME);
        addConvertTag(T_8F_CAPK_ID);
        addConvertTag(T_4F_CAPK_RID);
        addConvertTag(T_9F5A_PRO_ID);
        addConvertTag(T_5A_PAN);
        addConvertTag(T_56_TRACK1);
        addConvertTag(T_57_TRACK2);
        addConvertTag(T_9F6B_TRACK2_1);
        addConvertTag(T_9F6E_FORM_FACTOR_INDICATOR);
        addConvertTag(T_5F20_CARDHOLDER_NAME);
        addConvertTag(T_5F24_APP_EXPIRATION_DATE);
        addConvertTag(T_5F25_APP_EFFECTIVE_DATE);
        addConvertTag(T_5F34_PAN_SEQ_NO);
        addConvertTag(T_50_APP_LABEL);
        addConvertTag(T_95_TVR);
        addConvertTag(T_9B_TSI);
        addConvertTag(T_8E_CVM);
        addConvertTag(T_9F34_CVM_RESULTS);
        addConvertTag(T_9F36_ATC);
        addConvertTag(T_9F37_UNPREDICTABLE_NUMBER);
        addConvertTag(T_9F26_APP_CRYPTO);
        addConvertTag(T_9F12_APP_NAME);
        addConvertTag(T_DF8115_ERROR_INDICATION);
        addConvertTag(T_DF8116_USR_INTF_REQUEST_DATA);
        addConvertTag(T_DF8129_LIST);
        addConvertTag(T_9F27_CRYPTO);
        addConvertTag(T_5F57_ACCOUNT_TYPE);
        addConvertTag(T_9F01_ACQUIRER_ID);
        addConvertTag(T_9F1E_INTER_DEV_NUM);
        addConvertTag(T_9F15_MERCHANT_CATEGORY_CODE);
        addConvertTag(T_9F16_MERCHANT_ID);
        addConvertTag(T_9F4E_MERCHANT_NAME_LOCATION);
        addConvertTag(T_9F33_TERMINAL_CAPABILITY);
        addConvertTag(T_9F1C_TERMINAL_ID);
        addConvertTag(T_9F7E_MOB_SUP);
        addConvertTag(T_DF8104_BALANCE_BEFORE_GAC);
        addConvertTag(T_DF8105_BALANCE_AFTER_GAC);
        addConvertTag(T_DF812D_MESS_HOLD_TIME);
        addConvertTag(T_DF8108_DS_AC_TYPE);
        addConvertTag(T_DF60_DS_INPUT_CARD);
        addConvertTag(T_DF8109_DS_INPUT_TERMINAL);
        addConvertTag(T_DF62_DS_ODS_INFO);
        addConvertTag(T_DF810A_DS_ODS_READER);
        addConvertTag(T_DF63_DS_ODS_TERMINAL);
        addConvertTag(T_DF8110_FST_WRITE);
        addConvertTag(T_DF8112_READ);
        addConvertTag(T_FF8102_WRiTE_BEFORE_AC);
        addConvertTag(T_FF8103_WRiTE_AFTER_AC);
        addConvertTag(T_DF8127_TIMEOUT);
        addConvertTag(T_9F40_ADDITIONAL_CAPABILITY);
        addConvertTag(T_9F41_TRAN_SEQUENCE_COUNTER);
        addConvertTag(T_9F5C_DS_OPERATOR_ID);
        addConvertTag(T_9F66_TTQ);
    }

    public static byte[] Tag2Arr(int Tag)
    {
        if (byteArrTags.isEmpty())
            FiilTagsArray();
        return byteArrTags.get(Tag);
    }
}
