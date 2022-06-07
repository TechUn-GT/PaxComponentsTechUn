package com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.parser.pull;

import static com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.parser.ParseException.CODE_FILE_OPEN_ERR;
import static com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.parser.ParseException.CODE_NODE_NOT_FOUND;
import static com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.parser.ParseException.CODE_RAW_NAME_EMPTY;

import android.content.Context;
import android.text.TextUtils;
import android.util.Xml;

import androidx.annotation.NonNull;

import com.techun.paxcomponents.sdk_pax.module_emv.utils.ConvertHelper;
import com.techun.paxcomponents.sdk_pax.module_emv.utils.LogUtils;
import com.techun.paxcomponents.sdk_pax.module_emv.utils.interfaces.IConvert;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.clss.PayWaveAid;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.clss.PayWaveInterFloorLimit;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.clss.PayWaveParam;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.entity.clss.PayWaveProgramId;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.parser.IXmlParser;
import com.techun.paxcomponents.sdk_pax.module_emv.xmlparam.parser.ParseException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class PayWavePullParser implements IXmlParser<PayWaveParam> {
    private static final String TAG = "PayWavePullParser";
    private HashMap<String, String> defConfigs = new HashMap<>();
    private IConvert convert = ConvertHelper.getConvert();
    private Context mContext;

    public PayWavePullParser(Context context) {
        mContext = context;
    }

    @Override
    public PayWaveParam parse(@NonNull InputStream inputStream) throws ParseException {
        if (inputStream == null) {
            throw new ParseException(ParseException.CODE_COMMON_ERROR, "InputStream is null");
        }

        PayWaveParam payWaveParam = null;
        ArrayList<PayWaveAid> payWaveAidArrayList = null;
        PayWaveAid payWaveAid = null;

        ArrayList<PayWaveInterFloorLimit> payWaveInterFloorLimitArrayList = null;
        PayWaveInterFloorLimit payWaveInterFloorLimit = null;

        ArrayList<PayWaveProgramId> payWaveProgramIdArrayList = null;
        PayWaveProgramId payWaveProgramId = null;

        XmlPullParser parser = Xml.newPullParser();

        try {
            parser.setInput(inputStream, "UTF-8");
        } catch (XmlPullParserException e) {
            LogUtils.e(TAG, e);
            throw new ParseException(CODE_FILE_OPEN_ERR, "Set input stream fail");

        }

        int eventType = 0;
        try {
            eventType = parser.getEventType();
        } catch (Exception e) {
            throw new ParseException(ParseException.CODE_COMMON_ERROR, "Event type exception in PayWave file", e);
        }

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    break;

                case XmlPullParser.START_TAG:
                    String rawStartName = parser.getName();
                    String startName = "";
                    if (!TextUtils.isEmpty(rawStartName)) {
                        startName = rawStartName.replaceAll(" ", "");
                    }

                    if (TextUtils.equals("PAYWAVEPARAM", startName)) {
                        payWaveParam = new PayWaveParam();
                    }
                    if (payWaveParam != null) {
                        if (TextUtils.equals("AIDLIST", startName)) {
                            payWaveAidArrayList = new ArrayList<>();
                        }

                        if (payWaveAidArrayList != null) {
                            if (TextUtils.equals("AID", startName)) {
                                payWaveAid = new PayWaveAid();
                            }

                            if (payWaveAid != null) {
                                try {
                                    parsePayWaveAid(parser, payWaveAid);
                                } catch (ParseException e) {
                                    throw e;
                                } catch (Exception e) {
                                    throw new ParseException(ParseException.CODE_COMMON_ERROR, "Parse CLSS PayWaveAid failed", e);
                                }

                                if (TextUtils.equals("INTERWARELIST", startName)) {
                                    payWaveInterFloorLimitArrayList = new ArrayList<>();
                                }

                                if (payWaveInterFloorLimitArrayList != null) {
                                    if (TextUtils.equals("Inter_WareFloorlimitByTransactionType", startName)) {
                                        payWaveInterFloorLimit = new PayWaveInterFloorLimit();
                                    }

                                    if (payWaveInterFloorLimit != null) {
                                        try {
                                            parseInterWareFloorLimit(parser, payWaveInterFloorLimit);
                                        } catch (ParseException e) {
                                            throw e;
                                        } catch (Exception e) {
                                            throw new ParseException(ParseException.CODE_COMMON_ERROR, "Parse CLSS PayPassAid failed", e);
                                        }
                                    }
                                }
                            }
                        }

                        if (TextUtils.equals("PROGRAMIDLIST", startName)) {
                            payWaveProgramIdArrayList = new ArrayList<>();
                        }

                        if (payWaveProgramIdArrayList != null) {
                            if (TextUtils.equals("PROGRAMID", startName)) {
                                payWaveProgramId = new PayWaveProgramId();
                            }

                            if (payWaveProgramId != null) {
                                try {
                                    parseProgramId(parser, payWaveProgramId);
                                } catch (Exception e) {
                                    throw new ParseException(ParseException.CODE_COMMON_ERROR, "Parse CLSS PayWaveProgramId failed", e);
                                }
                            }
                        }
                    }

                    break;

                case XmlPullParser.END_TAG:
                    String rawEndName = parser.getName();
                    String endName = "";
                    if (!TextUtils.isEmpty(rawEndName)) {
                        endName = rawEndName.replaceAll(" ", "");
                    }
                    if (payWaveParam != null) {
                        if (TextUtils.equals("AIDLIST", endName)) {
                            payWaveParam.setPayWaveAidArrayList(payWaveAidArrayList);
                            payWaveAidArrayList = null;
                        }

                        if (payWaveAidArrayList != null) {
                            if (TextUtils.equals("AID", endName)) {
                                payWaveAidArrayList.add(payWaveAid);
                                payWaveAid = null;
                            }

                            if (payWaveAid != null) {
                                if (TextUtils.equals("INTERWARELIST", endName)) {
                                    payWaveAid.setPayWaveInterFloorLimitList(payWaveInterFloorLimitArrayList);
                                    payWaveInterFloorLimitArrayList = null;
                                }

                                if (payWaveInterFloorLimitArrayList != null) {
                                    if (TextUtils.equals("Inter_WareFloorlimitByTransactionType", endName)) {
                                        payWaveInterFloorLimitArrayList.add(payWaveInterFloorLimit);
                                        payWaveInterFloorLimit = null;
                                    }
                                }
                            }
                        }

                        if (TextUtils.equals("PROGRAMIDLIST", endName)) {
                            payWaveParam.setWaveProgramIdArrayList(payWaveProgramIdArrayList);
                            payWaveProgramIdArrayList = null;
                        }

                        if (payWaveProgramIdArrayList != null) {
                            if (TextUtils.equals("PROGRAMID", endName)) {
                                payWaveProgramIdArrayList.add(payWaveProgramId);
                                payWaveProgramId = null;
                            }
                        }
                    }
                    break;
                default:
                    break;
            }

            try {
                eventType = parser.next();
            } catch (Exception e) {
                LogUtils.e(TAG, e);
            }
        }

        //AESC 2021-11-18 Configuramos los valores que se obtienen de visa

        return payWaveParam;
    }

    private void parsePayWaveAid(XmlPullParser parser, PayWaveAid payWaveAid) throws IOException, XmlPullParserException, ParseException {
        String rawName = parser.getName();
        if (TextUtils.isEmpty(rawName)) {
            throw new ParseException(CODE_RAW_NAME_EMPTY, "parser get name err");
        }

        String name = rawName.replaceAll(" ", "");
        if (TextUtils.isEmpty(name)) {
            throw new ParseException(CODE_RAW_NAME_EMPTY, "parser get name err");
        }

        switch (name) {
            case "LocalAIDName":
                payWaveAid.setLocalAidName(parser.nextText());
                break;

            case "ApplicationID":
                payWaveAid.setApplicationId(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_RIGHT));
                break;

            case "PartialAIDSelection":
                payWaveAid.setPartialAidSelection(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "CryptogramVersion17Supported":
                payWaveAid.setCryptogramVersion17Supported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "ZeroAmountNoAllowed":
                payWaveAid.setZeroAmountNoAllowed(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "StatusCheckSupported":
                payWaveAid.setStatusCheckSupported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "ReaderTTQ":
                payWaveAid.setReaderTtq(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_RIGHT));
                break;

            case "SecurityCapability":
                payWaveAid.setSecurityCapability(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "TermType":
                payWaveAid.setTermType(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;
            case "AID":
            case "INTERWARELIST":
            case "Inter_WareFloorlimitByTransactionType":
            case "TransactionType":
            case "ContactlessCVMLimit":
            case "ContactlessTransactionLimit":
            case "ContactlessFloorLimit":
            case "ContactlessTransactionLimitSupported":
            case "CVMLimitSupported":
            case "ContactlessFloorLimitSupported":
                break;

            default:
                throw new ParseException(CODE_NODE_NOT_FOUND, "node " + name + " not exist");
        }
    }

    private void parseInterWareFloorLimit(XmlPullParser parser, PayWaveInterFloorLimit limit) throws IOException, XmlPullParserException, ParseException {
        String rawName = parser.getName();
        if (TextUtils.isEmpty(rawName)) {
            throw new ParseException(CODE_RAW_NAME_EMPTY, "parser get name err");
        }

        String name = rawName.replaceAll(" ", "");
        if (TextUtils.isEmpty(name)) {
            throw new ParseException(CODE_RAW_NAME_EMPTY, "parser get name err");
        }

        switch (name) {
            case "TransactionType":
                limit.setTransactionType(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "ContactlessCVMLimit":
                limit.setContactlessCvmLimit(Long.parseLong(parser.nextText()));
                break;

            case "ContactlessTransactionLimit":
                limit.setContactlessTransactionLimit(Long.parseLong(/*"10000"*/parser.nextText()));
                break;

            case "ContactlessFloorLimit":
                limit.setContactlessFloorLimit(Long.parseLong(parser.nextText()));
                break;


            case "ContactlessTransactionLimitSupported":
                limit.setContactlessTransactionLimitSupported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "CVMLimitSupported":
                limit.setCvmLimitSupported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "ContactlessFloorLimitSupported":
                limit.setContactlessFloorLimitSupported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;
            case "Inter_WareFloorlimitByTransactionType":
                break;
            default:
                throw new ParseException(CODE_NODE_NOT_FOUND, "node " + name + " not exist");
        }
    }

    private void parseProgramId(XmlPullParser parser, PayWaveProgramId payWaveProgramId) throws IOException, XmlPullParserException, ParseException {
        String rawName = parser.getName();
        if (TextUtils.isEmpty(rawName)) {
            throw new ParseException(CODE_RAW_NAME_EMPTY, "parser get name err");
        }

        String name = rawName.replaceAll(" ", "");
        if (TextUtils.isEmpty(name)) {
            throw new ParseException(CODE_RAW_NAME_EMPTY, "parser get name err");
        }

        switch (name) {
            case "ProgramId":
                payWaveProgramId.setProgramId(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_RIGHT));
                break;


            case "ContactlessCVMLimit":
                payWaveProgramId.setContactlessCvmLimit(Long.parseLong(parser.nextText()));
                break;

            case "ContactlessTransactionLimit":
                payWaveProgramId.setContactlessTransactionLimit(Long.parseLong(parser.nextText()));
                break;

            case "ContactlessFloorLimit":
                payWaveProgramId.setContactlessFloorLimit(Long.parseLong(parser.nextText()));
                break;


            case "ContactlessTransactionLimitSupported":
                payWaveProgramId.setContactlessTransactionLimitSupported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "CVMLimitSupported":
                payWaveProgramId.setCvmLimitSupported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "ContactlessFloorLimitSupported":
                payWaveProgramId.setContactlessFloorLimitSupported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "CryptogramVersion17Supported":
                payWaveProgramId.setCryptogramVersion17Supported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "ZeroAmountNoAllowed":
                payWaveProgramId.setZeroAmountNoAllowed(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "StatusCheckSupported":
                payWaveProgramId.setStatusCheckSupported(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_LEFT)[0]);
                break;

            case "ReaderTTQ":
                payWaveProgramId.setReaderTtq(convert.strToBcd(parser.nextText(), IConvert.EPaddingPosition.PADDING_RIGHT));
                break;

            case "PROGRAMID":
                break;
            default:
                throw new ParseException(CODE_NODE_NOT_FOUND, "node " + name + " not exist");
        }
    }
}
