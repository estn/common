package com.argyranthemum.common.core.util;


import java.util.Random;
import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtil {

    private UUIDUtil() {
    }

    /**
     * 生成原始UUID
     *
     * @return
     */
    public static String random() {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13)
                + uuid.substring(14, 18) + uuid.substring(19, 23)
                + uuid.substring(24);

    }

    /**
     * UUID字符串，该字符串没有中划线
     *
     * @return 无中划线的UUID字符串
     */
    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 随机生成指定长度的字符串,该字符串只包括26个英文字母
     *
     * @param length 字符串长度
     * @return
     */
    public static String randomChar(int length) {
        char[] randomChar = {
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
                'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
                'n', 'm'};

        return random(randomChar, length);
    }

    /**
     * 随机生成指定长度的字符串,该字符串包括26个英文字母和数字组成
     *
     * @return
     */
    public static String randomString(int length) {
        char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
                'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
                'n', 'm'};

        return random(randomChar, length);
    }

    /**
     * 随机生成指定长度的字符串,该字符串包括数字
     *
     * @param length
     * @return
     */
    public static String randomNumber(int length) {
        char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        return random(randomChar, length);
    }

    public static String random(char[] randomChar, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length cannot be negative");
        }
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(randomChar[Math.abs(random.nextInt())
                    % randomChar.length]);
        }
        return stringBuilder.toString();
    }

}