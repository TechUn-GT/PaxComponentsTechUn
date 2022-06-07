package com.techun.paxcomponents.sdk_pax.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.pax.dal.IDAL;
import com.pax.dal.IPrinter;
import com.pax.dal.entity.EFontTypeAscii;
import com.pax.dal.entity.EFontTypeExtCode;
import com.pax.dal.exceptions.PrinterDevException;
import com.pax.neptunelite.api.NeptuneLiteUser;
import com.techun.paxcomponents.sdk_pax.BaseTester;
import com.techun.paxcomponents.util.Util;

public class PrinterTester extends BaseTester {

    private static PrinterTester printerTester;
    private IPrinter printer;
    private static IDAL dal;
    private Context context;

    public PrinterTester(Context c) {
        this.context = c;
    }


    public static PrinterTester getInstance(Context context) {
        if (printerTester == null) {
            try {
                //Device.configuracionesPOS(appContext);
                long start = System.currentTimeMillis();
                dal = NeptuneLiteUser.getInstance().getDal(context);
                printerTester = (PrinterTester) dal.getPrinter();
                Log.i("Test", "get dal cost:" + (System.currentTimeMillis() - start) + " ms");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(context, "error occurred,DAL is null.", Toast.LENGTH_LONG).show();
            }
        }
        return printerTester;
    }

    public int instance() {
        int status = 0;
        try {
            //Device.configuracionesPOS(appContext);
            long start = System.currentTimeMillis();
            dal = NeptuneLiteUser.getInstance().getDal(context);
            printer = dal.getPrinter();
            init();
            status = getStatusB();
            Util.INSTANCE.logsUtils("get dal cost:" + (System.currentTimeMillis() - start) + " ms", 0);
        } catch (Exception e) {
            status = 1;
            Toast.makeText(context, "error occurred,DAL is null.", Toast.LENGTH_LONG).show();
            Util.INSTANCE.logsUtils("error occurred,DAL is null.", 1);
        }
        return status;
    }


    public void init() {
        try {
            printer.init();
            logTrue("init");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("init", e.toString());
        }
    }


    public String getStatus() {
        try {
            int status = printer.getStatus();
            logTrue("getStatus");
            return statusCode2Str(status);
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("getStatus", e.toString());
            return "";
        }

    }

    public int getStatusB() {
        try {
            return printer.getStatus();
        } catch (PrinterDevException e) {
            return -1;
        }
    }

    public void fontSet(EFontTypeAscii asciiFontType, EFontTypeExtCode cFontType) {
        try {
            printer.fontSet(asciiFontType, cFontType);
            logTrue("fontSet");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("fontSet", e.toString());
        }

    }

    public void spaceSet(byte wordSpace, byte lineSpace) {
        try {
            printer.spaceSet(wordSpace, lineSpace);
            logTrue("spaceSet");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("spaceSet", e.toString());
        }
    }

    public void printStr(String str, String charset) {
        try {
            printer.printStr(str, charset);
            logTrue("printStr");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("printStr", e.toString());
        }

    }

    public void step(int b) {
        try {
            printer.step(b);
            logTrue("setStep");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("setStep", e.toString());
        }
    }

    public void printBitmap(Bitmap bitmap) {
        try {
            printer.printBitmap(bitmap);
            logTrue("printBitmap");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("printBitmap", e.toString());
        }
    }

    public String start() {
        try {
            int res = printer.start();
            logTrue("start");
            return statusCode2Str(res);
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("start", e.toString());
            return "";
        }

    }

    public void leftIndents(short indent) {
        try {
            printer.leftIndent(indent);
            logTrue("leftIndent");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("leftIndent", e.toString());
        }
    }

    public int getDotLine() {
        try {
            int dotLine = printer.getDotLine();
            logTrue("getDotLine");
            return dotLine;
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("getDotLine", e.toString());
            return -2;
        }
    }

    public void setGray(int level) {
        try {
            printer.setGray(level);
            logTrue("setGray");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("setGray", e.toString());
        }

    }

    public void setDoubleWidth(boolean isAscDouble, boolean isLocalDouble) {
        try {
            printer.doubleWidth(isAscDouble, isLocalDouble);
            logTrue("doubleWidth");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("doubleWidth", e.toString());
        }
    }

    public void setDoubleHeight(boolean isAscDouble, boolean isLocalDouble) {
        try {
            printer.doubleHeight(isAscDouble, isLocalDouble);
            logTrue("doubleHeight");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("doubleHeight", e.toString());
        }

    }

    public void setInvert(boolean isInvert) {
        try {
            printer.invert(isInvert);
            logTrue("setInvert");
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("setInvert", e.toString());
        }

    }

    public String cutPaper(int mode) {
        try {
            printer.cutPaper(mode);
            logTrue("cutPaper");
            return "cut paper successful";
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("cutPaper", e.toString());
            return e.toString();
        }
    }

    public String getCutMode() {
        String resultStr = "";
        try {
            int mode = printer.getCutMode();
            logTrue("getCutMode");
            switch (mode) {
                case 0:
                    resultStr = "Only support full paper cut";
                    break;
                case 1:
                    resultStr = "Only support partial paper cutting ";
                    break;
                case 2:
                    resultStr = "support partial paper and full paper cutting ";
                    break;
                case -1:
                    resultStr = "No cutting knife,not support";
                    break;
                default:
                    break;
            }
            return resultStr;
        } catch (PrinterDevException e) {
            e.printStackTrace();
            logErr("getCutMode", e.toString());
            return e.toString();
        }
    }

    public String statusCode2Str(int status) {
        String res = "";
        switch (status) {
            case 0:
                res = "Exitoso ";
                break;
            case 1:
                res = "La impresora está ocupada ";
                break;
            case 2:
                res = "Sin papel ";
                break;
            case 3:
                res = "El formato del paquete de datos de impresión da error ";
                break;
            case 4:
                res = "Mal funcionamiento de la impresora ";
                break;
            case 8:
                res = "Impresora Calentada ";
                break;
            case 9:
                res = "El voltaje de la impresora es demasiado bajo.";
                break;
            case 240:
                res = "La impresora no ha terminado ";
                break;
            case 252:
                res = " PrinterBody no ha instalado la biblioteca de fuentes ";
                break;
            case 254:
                res = "El paquete de datos es demasiado largo. ";
                break;
            default:
                break;
        }
        return res;
    }
}
