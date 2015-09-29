package com.wildcardtechnologies.Util;



public class Converter {

    private Converter(){}

    public static String toPDU( String command) {

        String bin = "";
        for(int i = 0; i < command.length(); i++) {
            char ch = command.charAt(i);
            int ord = (int) ch;
            String binary = Integer.toString(ord, 2);
            binary = pad(binary, 7);
            String reversed = reverse(binary);
            bin += reversed;
        }

        bin += repeat('0', 8 - (bin.length() % 8));
        String pdu = "";
        while(bin.length() > 0)
        {
            String symbol = bin.substring(0, 8);
            symbol = reverse(symbol);
            bin = bin.substring(8);
            pdu += binhex(symbol.substring(0,4)) + binhex(symbol.substring(4));
        }
        return pdu;
    }

    public static String toText( String pduEncoded) {
        if(pduEncoded == null || pduEncoded.trim().length() == 0) return "";
        pduEncoded = pduEncoded.trim();

        int[] pdu = packH(pduEncoded);

        String bin = "";
        for (int ord: pdu) {
            String binary = Integer.toString(ord, 2);
            binary = pad(binary, 8);
            String reversed = reverse(binary);
            bin += reversed;
        }

        String hex = "";
        while(bin.length() >= 7) {
            String symbol = "0" + reverse(bin.substring(0, 7));
            hex += binhex(symbol.substring(0,4)) + binhex(symbol.substring(4));

            bin = bin.substring(7);
        }

        return new String(javax.xml.bind.DatatypeConverter.parseHexBinary(hex));
    }

    private static String pad(String string, int padSize) {
        //we only cater for 7 and 8
        String zeroPad = repeat('0', padSize);

        if(string.length() > padSize)
            throw new IllegalArgumentException(String.format("String length greater than specified padSize. %s : %d", string, string.length()));

        String str = zeroPad.substring(string.length()) + string;

        return str;
    }

    private static int[] packH(String hex) {
        // hexToString that works at a byte level, not a character level
        int[] output = new int[(hex.length() + 1) / 2];
        for (int i = hex.length() - 1; i >= 0; i -= 2) {
            int from = i - 1;
            if (from < 0) {
                from = 0;
            }
            String str = hex.substring(from, i + 1);
            int res = Integer.parseInt(str, 16);
            output[i/2] = res;
        }
        return output;
    }

    private static String binhex( String string) {
        int decimalValue = Integer.parseInt(string, 2);
        return Integer.toHexString(decimalValue).toUpperCase();
    }

    private static String reverse( String string) {
        return new StringBuilder(string).reverse().toString();
    }

    private static String repeat(char ch, int repeat) {
        char[] buf = new char[repeat];
        for (int i = repeat - 1; i >= 0; i--) {
            buf[i] = ch;
        }
        return new String(buf);
    }
}
