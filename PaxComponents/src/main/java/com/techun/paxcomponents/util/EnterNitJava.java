package com.techun.paxcomponents.util;

import android.text.Editable;
import android.text.TextWatcher;

public class EnterNitJava implements TextWatcher {
    //9172784-7
    private static final int TOTAL_SYMBOLS = 9; // size of pattern 0000-0000-0000-0000
    private static final int TOTAL_DIGITS = 8; // max numbers of digits in pattern: 0000 x 4
    private static final int DIVIDER_MODULO = 8; // means divider position is every 5th symbol beginning with 1
    private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
    private static final char DIVIDER = '-';


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        int longit = s.length();
        Util.INSTANCE.logsUtils("Longitud " + longit,0);
        if (longit == 9) {
            Util.INSTANCE.logsUtils("Error " + longit,0);
        } else {
            try {
                boolean pref = isInputCorrect(s);
                if (!pref) {
                    String pos1 = s.toString().substring(0, 7);
                    String pos2 = s.toString().substring(7);
                    String postFix = pos1 + "-" + pos2;
                    Util.INSTANCE.logsUtils("Prefix " + pos1 + " " + pos2,0);
                    Util.INSTANCE.logsUtils("PostFix " + postFix,0);
                    if (longit > 9) {

                    } else
                        s.replace(0, longit, postFix);
                }
            } catch (Exception e) {
                Util.INSTANCE.logsUtils("Error " + e.getMessage(),1);
            }
        }


       /* boolean pref = isInputCorrect(s);
        Util.INSTANCE.logsUtils("Nit pref: " + pref);
        if (!pref) {
            Util.INSTANCE.logsUtils("Nit replace: " + s);
            int cantidad = s.length();
            Util.INSTANCE.logsUtils("Nit length: " + cantidad);
            s.replace(0, cantidad, buildCorrectString(getDigitArray(s)));
        }*/
    }

    private boolean isInputCorrect(Editable s) {
        boolean isCorrect = s.length() <= EnterNitJava.TOTAL_SYMBOLS; // check size of entered string

        for (int i = 0; i < s.length(); i++) { // check that every element is right
            if (i > 0 && ((i + 1) % EnterNitJava.DIVIDER_MODULO == 0)) {
                isCorrect &= EnterNitJava.DIVIDER == s.charAt(i);
            }
        }
        return isCorrect;
    }

    private String buildCorrectString(char[] digits) {
        final StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digits.length; i++) {
            if (digits[i] != ' ') {
                Util.INSTANCE.logsUtils("current: " + digits[i],0);
                formatted.append(digits[i]);
                Util.INSTANCE.logsUtils("current2: " + formatted.toString(),0);
                if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % EnterNitJava.DIVIDER_POSITION) == 0)) {
                    formatted.append(EnterNitJava.DIVIDER);
                }
            }
        }
        Util.INSTANCE.logsUtils("currentResult: " + formatted.toString(),0);
        return formatted.toString();
    }

    private char[] getDigitArray(final Editable s) {
        char[] digits = new char[EnterNitJava.TOTAL_DIGITS];
        int index = 0;
        for (int i = 0; i < s.length() && index < EnterNitJava.TOTAL_DIGITS; i++) {
            char current = s.charAt(i);
            if (current != '-') {
                digits[index] = current;
                index++;
            }
        }
        return digits;
    }
}
