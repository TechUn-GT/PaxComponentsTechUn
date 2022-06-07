package com.techun.paxcomponents.sdk_pax.printer;

import static com.pax.dal.entity.EFontTypeAscii.FONT_8_16;
import static com.pax.dal.entity.EFontTypeExtCode.FONT_16_16;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.pax.gl.page.IPage;
import com.pax.gl.page.PaxGLPage;
import com.techun.paxcomponents.R;
import com.techun.paxcomponents.util.Util;

import java.util.Objects;

public class PrinterConector {

    private final Context mContext;
    private int statusB;
    private final PrinterTester print;
    private static final int PRINTER_OK = 0;
    private static final int TIME_BETWEEN_VOUCHERS = 5000;

    //Impresion de comprobantes
    public PaxGLPage iPaxGLPage;
    //    private static final int FONT_BIG = 28;
    private static final int FONT_NORMAL = 20;
//    private static final int FONT_BIGEST = 40;

    public PrinterConector(Context context) {
        this.mContext = context;
        print = new PrinterTester(context);
        statusB = print.instance();
        statusB = print.getStatusB();
    }


    public int statusPrinter() {
        return print.getStatusB();
    }

    public void printVoucherPerLine(String reporte, IPrinter onPrinter) {
        statusB = print.getStatusB();
        if (statusB == 0) {
            try {
                //Configuracion
                setConfiguration();
                //Obtener voucher
                String[] vComercio = reporte.split("\\|");

                //2022-01-11 Implementacion para que reconozca el voucher como una pagina segun respuesta enviada por PAX
                setImageProducto(getVoucherPaxImplementation(vComercio));
                printStart();
                onPrinter.onPrint();
            } catch (Exception e) {
                int statusB = print.getStatusB();
                onPrinter.onErrorPrinter(statusB);
            }
        } else {
            onPrinter.onErrorPrinter(statusB);
        }
    }


    private Bitmap getVoucherPaxImplementation(String[] voucher) {
        iPaxGLPage = PaxGLPage.getInstance(mContext);
        IPage page = iPaxGLPage.createPage();
        IPage.ILine.IUnit unit = page.createUnit();
        unit.setAlign(IPage.EAlign.CENTER);
        unit.setText("GLiPaxGlPage");
        Bitmap icon = BitmapFactory.decodeResource(Objects.requireNonNull(mContext).getResources(), R.drawable.visalogousar);
        int widthB = icon.getWidth();
        int height = icon.getHeight();
        float scaleWidth = ((float) 330) / widthB;
        float scaleHeight = ((float) 150) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(icon, 0, 0, widthB, height, matrix, false);
        page.addLine().addUnit(resizedBitmap, IPage.EAlign.CENTER);

        //AESC 2021-11-18 Se analiza la primera posicion del array para detectar que no sea 0 o 1
        for (int i = 0; i < voucher.length; i++) {
            String val = voucher[i];
            if (i == 0 && (val.equals("1") || val.equals("0"))) {
                //No hacer nada dado que trae un 1 o 0
                Util.INSTANCE.logsUtils("No hacer nada dado que trae un 1 o 0", 4);
            } else {
                String line = ((val.trim().equals("")) ? "\n" : val.trim());
                page.addLine().addUnit(line, FONT_NORMAL, IPage.EAlign.CENTER);
            }
        }

        page.addLine().addUnit("\n\n\n\n\n", FONT_NORMAL);
        int width = 384;
        return iPaxGLPage.pageToBitmap(page, width);
    }

    public void printThreeVouchers(String voucherComercio, String voucherCliente, String instantWinner, boolean printClientflag, IPrinter onPrinter) {
        validateAndPrintVocuher(voucherComercio, 0, false, onPrinter);
        if (printClientflag)
            validateAndPrintVocuher(voucherCliente, TIME_BETWEEN_VOUCHERS, false, onPrinter);
        else
            validateAndPrintVocuher(instantWinner, TIME_BETWEEN_VOUCHERS, true, onPrinter);
    }

    public void printVenta(String voucherComercio, String voucherCliente, boolean printClientflag, IPrinter onPrinter) {
        Util.INSTANCE.logsUtils("Entro printVenta", 0);
        boolean vouchers = voucherCliente.equals("");
        validateAndPrintVocuher(voucherComercio, 0, vouchers, onPrinter);
        if (!vouchers && printClientflag)
            validateAndPrintVocuher(voucherCliente, TIME_BETWEEN_VOUCHERS, true, onPrinter);
        else
            onPrinter.onPrint();
    }

    //AESC 2021-11-29 Metodo para imprimir validando el estado de la impresora
    private void validateAndPrintVocuher(String voucher, int sleep, boolean isTheLastVoucher, IPrinter onPrinter) {
        statusB = print.getStatusB();
        if (statusB == PRINTER_OK) {
            try {
                Util.INSTANCE.logsUtils("validateAndPrintVocuher Printer Init", 0);
                //Tiempo de espera entre cada voucher
                SystemClock.sleep(sleep);

                setConfiguration();
                String[] currentVoucher = voucher.split("\\|");
                Bitmap icon = BitmapFactory.decodeResource(Objects.requireNonNull(mContext).getResources(), R.drawable.visalogousar);
                setImageProducto(icon);
                printerBodyInicioDia(getVoucher(currentVoucher), 150);
                printStart();

                //Validamos antes de retornar la respuesta
                statusB = print.getStatusB();
                if (statusB == PRINTER_OK) {
                    if (isTheLastVoucher)
                        onPrinter.onPrint();
                } else {
                    onPrinter.onErrorPrinter(statusB);
                }
            } catch (Exception e) {
                int statusB = print.getStatusB();
                onPrinter.onErrorPrinter(statusB);
            }
        } else {
            onPrinter.onErrorPrinter(statusB);
        }
    }

    private void setImageProducto(Bitmap bitmap) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        print.printBitmap(bitmap);
    }

    private void setConfiguration() {
        print.init();
        print.fontSet(FONT_8_16, FONT_16_16);
        print.setGray(500);
        print.spaceSet((byte) 0, (byte) 0);
        print.leftIndents((short) 0);
        print.setDoubleWidth(false, false);
    }

    public static String centrarTexto(String texto) {
        int total = ((50 - texto.length()) / 2);
        return padLeft(texto, (total + texto.length()), " ");
    }

    @NonNull
    public static String padLeft(final String str1, final int number, final String str) {
        return String.format("%1$" + number + "s", str1).replace(' ', str.charAt(0));
    }


    private void stepText(int step) {
        print.step(step);
    }

    private void printStart() {
        print.start();
    }

    private String getVoucher(String[] voucher) {
        StringBuilder contenido = new StringBuilder();

        //AESC 2021-11-18 Se analiza la primera posicion del array para detectar que no sea 0 o 1
        for (int i = 0; i < voucher.length; i++) {
            String val = voucher[i];
            if (i == 0 && (val.equals("1") || val.equals("0"))) {
                //No hacer nada dado que trae un 1 o 0
                Util.INSTANCE.logsUtils("No hacer nada dado que trae un 1 o 0", 4);
            } else {
                contenido.append(centrarTexto(((val.trim().equals("")) ? "\n" : val.trim()))).append("\n");
            }
        }
        return contenido.toString();
    }

    private void printerBodyInicioDia(String cuerpo, int step) {
        if (cuerpo.length() > 0)
            print.printStr(cuerpo, null);
        stepText(step);
    }

}
