package com.techun.paxcomponents.sdk_pax.module_emv.utils;

public enum EReaderType {
    MAG(1),
    ICC(2),
    PICC(4),
    MAG_ICC(3),
    MAG_PICC(5),
    ICC_PICC(6),
    MAG_ICC_PICC(7),
    DEFAULT(0);

    private byte readertype;

    private EReaderType(int readertype) {
        this.readertype = (byte)readertype;
    }

    public byte getEReaderType() {
        return this.readertype;
    }

    public static EReaderType fromInt(int i) {
        for (EReaderType b : EReaderType.values()) {
            if (b.getEReaderType() == i) { return b; }
        }
        return null;
    }
}
