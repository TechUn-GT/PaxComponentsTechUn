package com.techun.paxcomponents.sdk_pax.module_emv.utils;

import android.util.Log;

import com.techun.paxcomponents.util.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EMVTags {
    private static final String TAG = "EMVTags";


    Map<Integer, EMVTag> emvTags = new HashMap<>();

    public EMVTags() {

    }


    public boolean ParseTLV(boolean ClearFirst, String HexTLV) {
        //0A60C5BBA11542B1
        // 5F25031901179F2701809F360201EC9F3303E008E85F3401009F0306000000000000950500000080018407A00000000410109A032007029C01009F2608F10B0242AFAA248B5F24032109309F02060000000001009F10120110A04003240000000000000000000000FF
        if (ClearFirst)
            this.Clear();
        int OffSet = 0;
        int Len = HexTLV.length();
        while (OffSet < Len) {
            String Tag = "";
            int TagBytes = 2;
            int TagLen = 0;
            String TagValue = "";
            if ((OffSet + 2) <= Len) {
                Tag = HexTLV.substring(OffSet, OffSet + 2);
                if (Tag.charAt(1) == 'F') {
                    byte char2 = (byte) Integer.parseInt(Tag.substring(0, 1), 16);
                    if ((char2 & 0x01) != 0) {
                        Tag = HexTLV.substring(OffSet, OffSet + 4);
                        TagBytes = 4;
                    }
                }
                OffSet += TagBytes;
                if ((OffSet + 2) <= Len) {
                    TagLen = Integer.parseInt(HexTLV.substring(OffSet, OffSet + 2), 16) * 2;
                    OffSet += 2;
                    if ((OffSet + TagLen) <= Len) {
                        TagValue = HexTLV.substring(OffSet, OffSet + TagLen);
                        AddTag(Integer.parseInt(Tag, 16), MiscUtils.hexStr2Bytes(TagValue));
                        OffSet += TagLen;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return true;
    }

    public void AddTag(int Tag, byte[] TagValue) {
        Log.e(TAG, "AddTag: " + Tag);
        EMVTag tagObject = new EMVTag(Tag, TagValue.length, TagValue);
        emvTags.put(Tag, tagObject);
    }

    public void AddTag(int Tag, byte[] TagValue, int len) {
        byte[] tmp = Arrays.copyOf(TagValue, len);
        EMVTag tagObject = new EMVTag(Tag, len, tmp);
        emvTags.put(Tag, tagObject);
    }

    public void Clear() {
        Log.d("DEBUG", "eliminando anterior");
        emvTags.clear();
    }

    public boolean ContainsTag(int Tag) {
        if (emvTags.containsKey(Tag))
            return true;
        else
            return false;
    }

    public EMVTag GetTag(int Tag) {
        if (emvTags.containsKey(Tag)) {
            return emvTags.get(Tag);
        } else {
            return null;
        }
    }

    public byte[] GetTLV() {
        byte[] tlvtemp = new byte[1024];
        int offset = 0;

//40720
        Log.e(TAG, "GetTLV: POS-> " + emvTags);
        for (EMVTag tag : emvTags.values()) {
            byte[] tagtlv = tag.asTLV();
            System.arraycopy(tagtlv, 0, tlvtemp, offset, tagtlv.length);
            offset += tagtlv.length;
        }
        byte[] tlvresult = Arrays.copyOf(tlvtemp, offset);
        return tlvresult;
    }

    public byte[] GetTLV(int TagList[]) {
        byte[] tlvtemp = new byte[1024];
        int offset = 0;

        Log.e(TAG, "GetTLV - Tags[" + emvTags + "]");
        //Escribir que trae el Map
        for (Map.Entry<Integer, EMVTag> entry : emvTags.entrySet()) {
            Log.d("DEBUG", " Key = " + entry.getKey() + ", Value = " + entry.getKey());

//            MiscUtils.bytes2HexStr(tlvData)
        }



        for (int tagNumber : TagList) {
            EMVTag tag = GetTag(tagNumber);
            if (tag != null) {
                byte[] tagTlv = tag.asTLV();
                System.arraycopy(tagTlv, 0, tlvtemp, offset, tagTlv.length);
                offset += tagTlv.length;
            }
        }
        byte[] tlvResult = Arrays.copyOf(tlvtemp, offset);
        return tlvResult;
    }

    public String GetTLVasHexString() {
        byte[] tlvdata = GetTLV();
        return MiscUtils.bytes2HexStr(tlvdata);
    }

    public String GetTLVasHexString(int TagList[]) {
        byte[] tlvData = GetTLV(TagList);
        return MiscUtils.bytes2HexStr(tlvData);
    }
//  FRS 2021-11-15 Se elimina la evaluacion por # de tarjeta, se usa ahora el AID
//  FRS 2021-11-15 Se elimina la evaluacion por # de tarjeta, se usa ahora el AID
    public String ordenarTLV(int tipoLector) {
        String val = GetTLVasHexString();
        String tlv = "";
        int[] tags = new int[]{};

        Log.e(TAG, "ordenarTLV: Input: " + val);
        if (glStatus.GetInstance().AID.isEmpty()) {
            Util.INSTANCE.logsUtils("********* ERROR --- AID NO PUEDE ESTAR VACIO ********************",1);
            return tlv;
        }

        EMVUtils.CardBrand tipoTarjeta = EMVUtils.GetCardBrandByAID(glStatus.GetInstance().AID);
        Log.e(TAG, "ordenarTLV: tipoTarjeta: " + tipoTarjeta);
        switch (tipoTarjeta) {
            case UNKNOWN:
                break;
            case VISA:
                if (tipoLector == 1)
                    tags = new int[]{0x5F2A, 0x82, 0x95, 0x9A, 0x9C, 0x9F1E, 0x9F02, 0x9F03, 0x9F1A, 0x9F26, 0x9F27, 0x9F33, 0x9F36, 0x9F37, 0x9F41, 0x5F34, 0x9F10, 0x9F6E};
                else if (tipoLector == 2)
                    tags = new int[]{0x5F2A, 0x82, 0x95, 0x9A, 0x9C, 0x9F66, 0x9F02, 0x9F03, 0x9F10, 0x9F1A, 0x9F1E, 0x9F26, 0x9F27, 0x9F33, 0x9F36, 0x9F37, 0x9F41, 0x5F34, 0x9F6E};
                break;
            case MASTERCARD:
                //ARS 2021-11-11 MCC-MTIP-14 Se agrega el TAG 9F34 para MasterCard
                if (tipoLector == 1) {
                    tags = new int[]{0x5F2A, 0x82, 0x95, 0x9A, 0x9C, 0x9F1E, 0x9F02, 0x9F03, 0x9F10, 0x9F1A,         0x9F26, 0x9F27, 0x9F33, 0x9F34, 0x9F36, 0x9F37, 0x9F41, 0x5F34, 0x84, 0x9F6E};
                } else if (tipoLector == 2) {
                    tags = new int[]{0x5F2A, 0x82, 0x95, 0x9A, 0x9C,         0x9F02, 0x9F03, 0x9F10, 0x9F1A, 0x9F1E, 0x9F26, 0x9F27, 0x9F33, 0x9F34, 0x9F36, 0x9F37, 0x9F41, 0x5F34, 0x84, 0x9F6E};
                }
                break;
            case AMEX:
            case JCB:
            case DISCOVER:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + tipoTarjeta);
        }
        tlv = GetTLVasHexString(tags);
        Util.INSTANCE.logsUtils("ordenarTLV: Output: " + tlv,0);
        Clear();
        return tlv;
    }

    private String validarTrack2(String track2) {
        String[] partes = track2.split("D");
        return null;
    }

    //Calcular la longitud correcta si la traen en byte
    private int longitudCorrecta() {
        return 0;
    }

    private String validarLongitudTAGs(String tagConsulta) {
        int v = Integer.valueOf(tagConsulta);
        if (v >= 10)
            return String.valueOf(v + 6);


        return "0" + v;
    }


    /**
     * METODO EN EL CUAL SE REMUEVEN EL TAG 95 Y 9F33 SEGUN EL REQUERIMENTO 04/03/2021
     *
     * @param tipoLector
     * @return
     */
    public String ordenarTLV_V2(int tipoLector) {
        String val = GetTLVasHexString();
        Log.e(TAG, "ordenarTLV: val: " + val);
//,"9F34"
        String[] tags = new String[]{};
        if (tipoLector == 1)
            tags = new String[]{"5F2A", "82", "9A", "9C", "9F02", "9F03", "9F10", "9F1A", "9F1E", "9F26", "9F27", "9F34", "9F36", "9F37", "9F41", "5F34"};
        else if (tipoLector == 2)
            tags = new String[]{"5F2A", "82", "9A", "9C", "9F02", "9F03", "9F10", "9F1A", "9F1E", "9F26", "9F27", "9F36", "9F37", "9F41", "5F34"};
//
        String tlv = "";
        for (String k : tags) {
            int pos_inicio = val.indexOf(k);
            int longitud_tag = k.length();
            int longitud_con_tag = (pos_inicio + longitud_tag);
            int longitud_tamanio = longitud_con_tag + 2;
            String tamanio_tag = val.substring(longitud_con_tag, longitud_tamanio);
            int longitud_valor_tag = Integer.parseInt(tamanio_tag) * 2;
            int longitud_valor = longitud_tamanio + longitud_valor_tag;

            tlv += val.substring(pos_inicio, longitud_con_tag) + tamanio_tag + val.substring(longitud_tamanio, longitud_valor);

//            Log.e("", "" + k + " -> " + val.substring(pos_inicio, longitud_con_tag) + " " + tamanio_tag + " " + val.substring(longitud_tamanio, longitud_valor));
        }

        return tlv;

    }

    public String GetDump() {
        StringBuilder sb = new StringBuilder();
        sb.append("EMV Tags\n");
        sb.append("-------------\n");
        for (EMVTag tag : emvTags.values()) {
            sb.append(tag.Dump());
            sb.append("\n");
        }
        return sb.toString();
    }


}
