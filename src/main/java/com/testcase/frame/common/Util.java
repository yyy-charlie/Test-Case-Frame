package com.testcase.frame.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @ClassName Util
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-20
 **/
public class Util {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 特殊符号
     */
    private static final char[] SPECIAL_CHARS = {'`', '~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '=', '+', '[', ']', '{', '}', '\\', '|', ';', ':', '\'', '"', ',', '<', '.', '>', '/', '?'};

    public static void main(String[] args) {
//        System.out.println(Util.getRandomChars(9));
        System.out.println(Util.getRandomCharsBetween(15));
    }

    /**
     * 获取随机的字符串
     *
     * @param length 长度
     * @return
     */
    public static StringBuffer getRandomStr(int length) {
        return new StringBuffer(RandomStringUtils.randomAlphabetic(length));
    }

    /**
     * 获取在一个范围内的字符串
     *
     * @param minLength 最小长度数
     * @param maxLength 最大长度数
     * @return
     */
    public static StringBuffer getRandomStrBetween(int minLength, int maxLength) {
        return new StringBuffer(RandomStringUtils.randomAlphabetic(minLength, maxLength));
    }

    /**
     * 随机生成中文
     *
     * @param length 长度
     * @return
     */
    public static StringBuffer getRandomChinese(int length) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < length; i++) {
            String randomChar = getRandomChar();
            result.append(randomChar);
        }
        return result;
    }

    /**
     * 随机生成一个范围内的中文
     *
     * @param minLength 长度
     * @return
     */
    public static StringBuffer getRandomChineseBetween(int minLength) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i <= minLength; i++) {
            String randomChar = getRandomChar();
            result.append(randomChar);
        }
        return result;
    }


    /**
     * 随机生成数字
     *
     * @param length 位数
     * @return
     */
    public static StringBuffer getRandomNum(int length) {
        return new StringBuffer(RandomStringUtils.randomNumeric(length));
    }

    /**
     * 随机生成符号
     *
     * @param length 位数
     * @return
     */
    public static StringBuffer getRandomChars(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(SPECIAL_CHARS[random.nextInt(32)]);
        }
        return buffer;
    }

    /**
     * 随机生成一个范围内的特殊符号
     *
     * @param minLength
     * @return
     */
    public static StringBuffer getRandomCharsBetween(int minLength) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i <= minLength; i++) {
            buffer.append(SPECIAL_CHARS[random.nextInt(32)]);
        }
        return buffer;
    }

    /**
     * 随机生成一个范围内的数字
     *
     * @param minLength
     * @param maxLength
     * @return
     */
    public static StringBuffer getRandomNumBetween(int minLength, int maxLength) {
        return new StringBuffer(RandomStringUtils.randomNumeric(minLength, maxLength));
    }

    /**
     * 随机生成常见汉字
     *
     * @return
     */
    private static String getRandomChar() {
        String str = "";
        int highCode;
        int lowCode;

        Random random = new Random();

        //B0 + 0~39(16~55) 一级汉字所占区
        highCode = (176 + Math.abs(random.nextInt(39)));
        //A1 + 0~93 每区有94个汉字
        lowCode = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(highCode)).byteValue();
        b[1] = (Integer.valueOf(lowCode)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 封装返回结果
     *
     * @param b
     * @param description
     * @return
     */
    public static BaseResponse getBaseResponseByBool(boolean b, String description) {
        if (b) {
            return ParseUtils.parse2Response(HttpStatus.OK, description + "成功");
        }
        return ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "失败");
    }

    /**
     * 封装返回结果
     *
     * @param b
     * @param description
     * @return
     */
    public static BaseResponse getBaseResponse(boolean b, String description) {
        BaseResponse baseResponse;
        if (b) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }
        baseResponse = ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "失败");
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

}
