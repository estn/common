package com.argyranthemum.common.core.util;

/**
 * 16进制工具类，主要完成16进制和二进制之间的转换
 * User: estn
 * Date: 7/14/14
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class HexUtil {

    private HexUtil() {
    }

    /**
     * 字节数组转换为16进制字符串
     *
     * @param bytes 二进制数组
     * @return
     */
    public static String byteToHex(byte[] bytes) {
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex.toUpperCase();
        }
        return ret;
    }

    /**
     * 16进制字符串转换成二进制
     *
     * @param hex 16进制字符串
     * @return
     */
    public static byte[] hexToByte(String hex) {
        if (hex == null || hex.equals("")) {
            return null;
        }
        hex = hex.toUpperCase();
        int length = hex.length() / 2;
        char[] hexChars = hex.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
