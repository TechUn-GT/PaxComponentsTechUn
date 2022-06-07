package com.techun.paxcomponents.sdk_pax.module_emv.utils;

public class EMVTag {
    int _Tag;
    int _TagLen;
    byte[] _TagValue;

    EMVTag(int _tag, int _taglen, byte[] _tagvalue) {
        this._Tag = _tag;
        this._TagLen = _taglen;
        this._TagValue = _tagvalue.clone();
    }

    public int Tag() {
        return _Tag;
    }

    ;

    public int Len() {
        return _TagLen;
    }

    ;

    public byte[] Value() {
        return _TagValue;
    }

    ;

    public byte[] asTLV() {
        byte[] tagarray = MiscUtils.int2VarByteArray(this._Tag);
        int arrLen = tagarray.length + 1 + _TagValue.length;
        byte[] result = new byte[arrLen];
        System.arraycopy(tagarray, 0, result, 0, tagarray.length);
        result[tagarray.length] = (byte) (this._TagLen & 0xFF);
        System.arraycopy(this._TagValue, 0, result, tagarray.length + 1, this._TagValue.length);
        return result;
    }

    public String valueAsHEXStr() {
        if (_TagValue.length > 0)
            return MiscUtils.bytes2HexStr(_TagValue);
        else
            return "";
    }

    public String Dump() {
        byte[] tagarray = MiscUtils.int2VarByteArray(this._Tag);
        return String.format("%6s:[%02X] %s", MiscUtils.bytes2HexStr(tagarray), _TagLen, MiscUtils.bytes2HexStr(_TagValue));
    }


}
