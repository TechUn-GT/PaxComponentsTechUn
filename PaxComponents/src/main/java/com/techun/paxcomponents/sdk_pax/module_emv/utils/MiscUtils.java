package com.techun.paxcomponents.sdk_pax.module_emv.utils;

import android.content.Context;
import android.util.Log;

import com.techun.paxcomponents.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MiscUtils {

    /**
     * 得到设备屏幕的宽度
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 把密度转换为像素
     */
    public static int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }

    public static void install(Context context, String name, String path) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = context.getAssets().open(name);
            File file = new File(path + name);
            out = new FileOutputStream(file);
            int count = 0;
            byte[] tmp = new byte[1024];
            while ((count = in.read(tmp)) != -1) {
                out.write(tmp, 0, count);
            }
            Runtime.getRuntime().exec("chmod 777 " + path + name);
        } catch (FileNotFoundException e) {
            Log.e("install", e.getMessage());
        } catch (IOException ex) {
            Log.e("install", ex.getMessage());
        } catch (Exception e) {
            Log.e("install", e.getMessage());
        } finally {
            try {
                if (out != null) {
                    //out.flush();
                    out.close();
                    in.close();
                }
            } catch (IOException e) {
                Log.e("install", e.getMessage());
            }
        }
    }

    /**
     * 获取主秘钥索引
     *
     * @param index 0~99的主秘钥索引值
     * @return 1~100的主秘钥索引值
     */
    public static int getMainKeyIndex(int index) {
        return index + 1;
    }

    /**
     * ASCII码字符串转数字字符串
     *
     * @param content ASCII字符串
     * @return 字符串
     */
    public static String asciiStringToString(String content) {
        StringBuffer result = new StringBuffer();
        int length = content.length() / 2;
        for (int i = 0; i < length; i++) {
            String c = content.substring(i * 2, i * 2 + 2);
            long a = hexStringToAlgorism(c);
            char b = (char) a;
            String d = String.valueOf(b);
            result.append(d);
        }
        return result.toString();
    }


    /**
     * 十六进制字符串装十进制
     *
     * @param hex 十六进制字符串
     * @return 十进制数值
     */
    public static long hexStringToAlgorism(String hex) {
        hex = hex.toUpperCase();
        int max = hex.length();
        long result = 0;
        for (int i = max; i > 0; i--) {
            char c = hex.charAt(i - 1);
            int algorism = 0;
            if (c >= '0' && c <= '9') {
                algorism = c - '0';
            } else {
                algorism = c - 55;
            }
            int k = max - i;
            result += Math.pow(16, k) * Double.valueOf(algorism);
        }
        return result;
    }

    public static String bcd2Str(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            byte left = (byte) ((bytes[i] & 0xf0) >>> 4);
            byte right = (byte) (bytes[i] & 0x0f);
            if (left >= 0x0a && left <= 0x0f) {
                left -= 0x0a;
                left += 'A';
            } else {
                left += '0';
            }

            if (right >= 0x0a && right <= 0x0f) {
                right -= 0x0a;
                right += 'A';
            } else {
                right += '0';
            }

            temp.append(String.format("%c", left));
            temp.append(String.format("%c", right));
        }
        return temp.toString();
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String bcd2Str(byte[] b, int len) {
        if (b == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(len * 2);
        int minLen = Math.min(b.length, len);
        for (int i = 0; i < minLen; ++i) {
            sb.append(HEX_DIGITS[((b[i] & 0xF0) >>> 4)]);
            sb.append(HEX_DIGITS[(b[i] & 0xF)]);
        }

        return sb.toString();
    }

    private static final String TAG = "MiscUtils";

    public static String bytes2HexStr(byte[] b) {
        if (b == null) {
            return null;
        }
        int Len = b.length;
        StringBuilder sb = new StringBuilder(Len * 2);
        for (int i = 0; i < Len; ++i) {
            char pos1 = (char) ((b[i] & 0xF0) >>> 4);
            char pos2 = (char) (b[i] & 0xF);

            Log.e(TAG, "bytes2HexStr: pos1: " + pos1 + ",  pos2: " + pos2);
            Log.e(TAG, "bytes2HexStr: pos1_c: " + HEX_DIGITS[pos1] + ",  pos2_c: " + HEX_DIGITS[pos2]);

            sb.append(HEX_DIGITS[pos1]);
            sb.append(HEX_DIGITS[pos2]);

            Util.INSTANCE.logsUtils("bytes2HexStr: " + sb.toString(),0);
//            Log.e(TAG, "bytes2HexStr: " + sb.toString());
        }

        return sb.toString();
    }

    public static byte[] str2Bcd(String asc) {
        int len = asc.length();
        int mod = len % 2;
        if (mod != 0) {
            asc = "0" + asc;
            len = asc.length();
        }
        if (len >= 2) {
            len /= 2;
        }
        byte[] bbt = new byte[len];
        byte[] abt = asc.getBytes();

        for (int p = 0; p < asc.length() / 2; p++) {
            int j;
            if ((abt[(2 * p)] >= 97) && (abt[(2 * p)] <= 122)) {
                j = abt[(2 * p)] - 97 + 10;
            } else {
                if ((abt[(2 * p)] >= 65) && (abt[(2 * p)] <= 90))
                    j = abt[(2 * p)] - 65 + 10;
                else
                    j = abt[(2 * p)] - 48;
            }
            int k;
            if ((abt[(2 * p + 1)] >= 97) && (abt[(2 * p + 1)] <= 122)) {
                k = abt[(2 * p + 1)] - 97 + 10;
            } else {
                if ((abt[(2 * p + 1)] >= 65) && (abt[(2 * p + 1)] <= 90))
                    k = abt[(2 * p + 1)] - 65 + 10;
                else {
                    k = abt[(2 * p + 1)] - 48;
                }
            }
            int a = (j << 4) + k;
            byte b = (byte) a;
            bbt[p] = b;
        }
        return bbt;
    }

    public static String bytes2String(byte[] source) {
        String result = "";
        if (source.length > 0)
            result = new String(source, StandardCharsets.UTF_8);
        return result;
    }


    public static String bytesToString(final byte[] pBytes) {
        return formatByte(pBytes, true, false);
    }

    /**
     * Integer bit size
     */
    private static final int MAX_BIT_INTEGER = 31;

    /**
     * Constant for Hexa
     */
    private static final int HEXA = 16;

    /**
     * Byte left mask
     */
    private static final int LEFT_MASK = 0xF0;

    /**
     * Byte right mask
     */
    private static final int RIGHT_MASK = 0xF;

    /**
     * Char digit 0 (0x30) :<br>
     * <ul>
     * <li>char 0 = 0x30 + 0x0
     * <li>char 1 = 0x30 + 0x1
     * <li>...
     * <li>char 9 = 0x30 + 0x9
     * </ul>
     */
    private static final int CHAR_DIGIT_ZERO = 0x30;

    /**
     * Char digit 7 (0x37) :<br>
     * <ul>
     * <li>char A = 0x37 + 0xA
     * <li>char B = 0x37 + 0xB
     * <li>...
     * <li>char F = 0x37 + 0xF
     * </ul>
     */
    private static final int CHAR_DIGIT_SEVEN = 0x37;

    /**
     * Char space
     */
    private static final char CHAR_SPACE = (char) 0x20;


    /**
     * Private method to format bytes to hexa string
     *
     * @param pByte     the bytes to format
     * @param pSpace    true if add spaces between bytes
     * @param pTruncate true to remove 0 left bytes value
     * @return a string containing the requested string
     */
    private static String formatByte(final byte[] pByte, final boolean pSpace, final boolean pTruncate) {
        String result;
        if (pByte == null) {
            result = "";
        } else {
            int i = 0;
            if (pTruncate) {
                while (i < pByte.length && pByte[i] == 0) {
                    i++;
                }
            }
            if (i < pByte.length) {
                int sizeMultiplier = pSpace ? 3 : 2;
                char[] c = new char[(pByte.length - i) * sizeMultiplier];
                byte b;
                for (int j = 0; i < pByte.length; i++, j++) {
                    b = (byte) ((pByte[i] & LEFT_MASK) >> 4);
                    c[j] = (char) (b > 9 ? b + CHAR_DIGIT_SEVEN : b + CHAR_DIGIT_ZERO);
                    b = (byte) (pByte[i] & RIGHT_MASK);
                    c[++j] = (char) (b > 9 ? b + CHAR_DIGIT_SEVEN : b + CHAR_DIGIT_ZERO);
                    if (pSpace) {
                        c[++j] = CHAR_SPACE;
                    }
                }
                result = pSpace ? new String(c, 0, c.length - 1) : new String(c);
            } else {
                result = "";
            }
        }
        return result;
    }

    public static String bytes2String(byte[] source, int len) {
        String result = "";
        if (len > source.length)
            len = source.length;
        if (len > 0)
            result = new String(source, 0, len, StandardCharsets.UTF_8);
        return result;
    }

    public static byte[] int2ByteArray(int i) {
        byte[] to = new byte[4];
        int offset = 0;
        to[offset] = (byte) (i >>> 24 & 0xFF);
        to[(offset + 1)] = (byte) (i >>> 16 & 0xFF);
        to[(offset + 2)] = (byte) (i >>> 8 & 0xFF);
        to[(offset + 3)] = (byte) (i & 0xFF);
        for (int j = 0; j < to.length; ++j) {
            if (to[j] != 0) {
                return Arrays.copyOfRange(to, j, to.length);
            }
        }
        return new byte[]{0x00};
    }

    public static byte[] int2VarByteArray(int i) {
        int arrayLenght = 1;
        if (i > 0xFF) {
            arrayLenght = 2;
            if (i > 0xFFFF) {
                arrayLenght = 3;
                if (i > 0xFFFFFF) {
                    arrayLenght = 4;
                }
            }
        }
        byte[] resultArray = new byte[arrayLenght];

        resultArray[arrayLenght - 1] = (byte) (i & 0xFF);
        if (arrayLenght > 1) {
            resultArray[arrayLenght - 2] = (byte) (i >>> 8 & 0xFF);
            if (arrayLenght > 2) {
                resultArray[arrayLenght - 3] = (byte) (i >>> 16 & 0xFF);
                if (arrayLenght > 3) {
                    resultArray[arrayLenght - 4] = (byte) (i >>> 24 & 0xFF);
                }
            }
        }
        return resultArray;

    }

    public static long bytes2Long(byte[] byteNum) {
        long num = 0;
        for (int ix = 0; ix < byteNum.length; ++ix) {
            num <<= 8;
            num |= (byteNum[ix] & 0xff);
        }
        return num;
    }

    public static byte[] LongToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }

    /**
     * bytes字符串转换为Byte值
     *
     * @param src Byte字符串，每个Byte之间没有分隔符
     * @return byte[]
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;

        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
//            ret[i] =  Byte.decode("0x" + src.substring(i * 2, m) + src.substring(m, n));
            ret[i] = (byte) (Integer.parseInt(src.substring(i * 2, m) + src.substring(m, n), 16) & 0xFF);
        }
        return ret;
    }

    public static char toHexChar(int hexValue) {
        if (hexValue <= 9 && hexValue >= 0)
            return (char) (hexValue + '0');
        else
            return (char) (hexValue - 10 + 'A');
    }

    public static String decimalToHex(int decimal) {
        String hex = "";
        while (decimal != 0) {
            int hexValue = decimal % 16;
            hex = toHexChar(hexValue) + hex;
            decimal = decimal / 16;
        }
        return hex;
    }

    public static String padLeft(final String valor, final int number, final String str) {
        return String.format("%1$" + number + "s", valor).replace(' ', str.charAt(0));
    }

    public static String padRight(final String str1, final int number, final String str) {
        String newStrin = str1;
        for (int i = str1.length(); i < number; i++) {
            newStrin += str;
        }
        return newStrin;
    }

}
