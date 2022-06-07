package com.techun.paxcomponents.sdk_pax.module_emv.utils;

public class EMVUtils {
    public EMVUtils() {

    }

    public enum CardBrand {
        UNKNOWN,
        VISA,
        MASTERCARD,
        AMEX,
        JCB,
        DISCOVER
    }

    public static CardBrand GetCardBrandByAID(String AID) {
        String BaseAID = AID.replace(" ", "");
        if (BaseAID.length() > 10)
            BaseAID = BaseAID.substring(0, 10);

        switch (BaseAID) {
            case "A000000003":
                return CardBrand.VISA;
            case "A000000004":
                return CardBrand.MASTERCARD;
            case "A000000002":
                return CardBrand.AMEX;
            case "A000000065":
                return CardBrand.JCB;
            case "A000000152":
                return CardBrand.DISCOVER;
            default:
                return CardBrand.UNKNOWN;
        }
    }

    public static CardBrand GetCardBrandByAID(byte[] AID) {
        String sAID = MiscUtils.bytes2String(AID);
        return GetCardBrandByAID(sAID);
    }
}
