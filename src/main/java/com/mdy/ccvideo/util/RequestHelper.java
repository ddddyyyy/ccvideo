package com.mdy.ccvideo.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import static com.mdy.ccvideo.dict.THQSConstants.dateFormat;
import static com.mdy.ccvideo.dict.THQSConstants.key;
import static com.mdy.ccvideo.dict.THQSConstants.room_key;

public class RequestHelper {
    /**
     * 将参数按字典序排序
     *
     * @param queryString 查询的参数
     * @return 按字典序的参数
     */
    private static String order(String queryString) {
        String[] list = queryString.split("&");
        return Arrays.stream(list).sorted().collect(Collectors.joining("&"));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     */
    private static String DateToTimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 生成md5
     */
    private static String getMD5(String message) {
        String md5str = "";
        try {
            // 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 2 将消息变成byte数组
            byte[] input = message.getBytes();
            // 3 计算后获得字节数组,这就是那128位了
            byte[] buff = md.digest(input);
            // 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
            md5str = bytesToHex(buff);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return md5str;
    }

    /**
     * 二进制转十六进制
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder md5str = new StringBuilder();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (byte aByte : bytes) {
            digital = aByte;
            if (digital < 0) {
                digital += 256;
            }
            if (digital < 16) {
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString().toUpperCase();
    }

    /**
     * @param queryString 要求转化的参数
     * @param isVideo     是否调用点播，true为点播api，false为直播api
     * @return 有效的请求参数
     */
    public static String decode(String queryString, boolean isVideo) {

        //获得当前时间
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        //最終的MD5后的結果除了&hash=以外的参数
        String hashedQueryString = order(queryString) + "&time=" + DateToTimeStamp(simpleDateFormat.format(now), dateFormat);
        //进行md5的参数
        String temp;
        if (isVideo) {
            temp = hashedQueryString + "&salt=" + key;
        } else {
            temp = hashedQueryString + "&salt=" + room_key;
        }
        return hashedQueryString + "&hash=" + getMD5(temp);
    }
}
