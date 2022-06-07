package com.techun.paxcomponents.sdk_pax.module_emv.utils;


import com.pax.dal.entity.EReaderType;
import com.techun.paxcomponents.sdk_pax.module_emv.process.entity.IssuerRspData;
import com.techun.paxcomponents.sdk_pax.module_emv.process.enums.CvmResultEnum;
import com.techun.paxcomponents.sdk_pax.module_emv.process.enums.TransResultEnum;

public class glStatus {
    private static glStatus glstatusInstance = null;
    public EReaderType readerType = EReaderType.MAG_ICC_PICC;
    public int currentReaderType = EReaderType.DEFAULT.getEReaderType();
    public EMVTags tranEMVTags = new EMVTags();
    public EMVTags tranEMVResponseTags = new EMVTags();
    public String PAN = "";
    public String ExpirationDate = "";
    public byte PANSeqNo = (byte) 0xFF; //Default a FF, si es FF no debe enviarse en la trama
    public double TransactionAmount;
    public double TransactionCashBackAmount;
    public String Track2 = "";
    public String Track1 = "";
    public String CardHolderName = "";
    public String ApplicationLabel = "";
    public String TERMINAL_CAPABILITY = "";
    public String PINCARD = "";
    public String AID = ""; //Application ID TAG 84
    public String TVR = ""; //Terminal Verification Results TAG 95
    public String TSI = ""; //Terminal Status Information TAG 9B
    public String Crypto = ""; //APP Cryptogram TAG 9F26
    public String ResponseCode = ""; //Codigo de respuesta campo 39
    public String AuthCode = ""; //Codigo de autorizacion campo 38
    public String RRN = ""; //Numero de referencia , campo 37

    public Boolean Need2ndGAC = false;
    public IssuerRspData issuerRspData = new IssuerRspData();

    public String CurrencyCode; //Codigo de Moneda Local 0320 0840;
    public String CurrencySymbol; //Simbolo de Moneda Q $ o cualquiera
    public byte CurrencyExponent; // Cantidad de decimales , siempre 2
    public String TraceNo; //Numero trace de la transaccion.
    public TransResultEnum TransactionResult = TransResultEnum.RESULT_NONE;
    public CvmResultEnum currentTxnCVMResult = CvmResultEnum.CVM_NO_CVM;


    private glStatus() {
        Clear();
    }

    public String GetDump() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reader:" + this.readerType.name() + "\n");
        sb.append("TransactionResult:" + this.TransactionResult.name() + "\n");
        sb.append("CVMResult:" + this.currentTxnCVMResult.name() + "\n");
        sb.append("Amount:" + this.TransactionAmount + "\n");
        sb.append("CashBackAmount:" + this.TransactionCashBackAmount + "\n");
        sb.append("PAN:" + this.PAN + "\n");
        sb.append("TRK2:" + this.Track2 + "\n");
        sb.append("AID:" + this.AID + "\n");
        sb.append("TVR:" + this.TVR + "\n");
        sb.append("TSI:" + this.TSI + "\n");
        sb.append("Crypto:" + this.Crypto + "\n");
        sb.append("2nd GAC Needed:" + this.Need2ndGAC + "\n");
        sb.append("ISS Rsp Data OnlineResult:" + this.issuerRspData.getOnlineResult() + "\n");
        sb.append("ISS Rsp Data RespCode:" + LogUtils.ArrToHexString(this.issuerRspData.getRespCode(), 0) + "\n");
        sb.append("ISS Rsp Data AuthCode:" + LogUtils.ArrToHexString(this.issuerRspData.getAuthCode(), 0) + "\n");
        sb.append("ISS Rsp Data AuthData:" + LogUtils.ArrToHexString(this.issuerRspData.getAuthData(), 0) + "\n");
        sb.append("ISS Rsp Data Script:" + LogUtils.ArrToHexString(this.issuerRspData.getScript(), 0) + "\n");
        sb.append(this.tranEMVTags.GetDump());
        return sb.toString();
    }

    public void Clear() {
        readerType = EReaderType.MAG_ICC_PICC;
        currentReaderType = EReaderType.DEFAULT.getEReaderType();
        tranEMVTags = new EMVTags();
        PAN = "";
        ExpirationDate = "";
        PANSeqNo = (byte) 0xFF;
        Track2 = "";
        Track1 = "";
        CardHolderName = "";
        ApplicationLabel = "";
        TERMINAL_CAPABILITY = "";
        AID = "";
        PINCARD = "";
        CardHolderName = "";
        AID = ""; //Application ID TAG 84
        TVR = ""; //Terminal Verification Results TAG 95
        TSI = ""; //Terminal Status Information TAG 9B
        Crypto = ""; //APP Cryptogram TAG 9F26

        TransactionAmount = 0;
        TransactionCashBackAmount = 0;
        Need2ndGAC = false;
        issuerRspData = new IssuerRspData();
        CurrencyCode = "0320";
        CurrencySymbol = "Q";
        CurrencyExponent = 2;
        TraceNo = "000001";
        TransactionResult = TransResultEnum.RESULT_NONE;
        currentTxnCVMResult = CvmResultEnum.CVM_NO_CVM;
    }


    public static glStatus GetInstance() {
        if (glstatusInstance == null)
            glstatusInstance = new glStatus();
        return glstatusInstance;
    }

}
