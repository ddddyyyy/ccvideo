package com.mdy.ccvideo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;


/**
 * CC视频工具类
 *
 * @author MDY
 */
public class THQSUtil {
    //点播的key
    private static final String key = "";
    //直播的key
    private static final String room_key = "";
    //同一返回JSON格式，并加上userid
    private static final String url_prefix = "format=json&userid=";
    //日期的格式
    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";


    /**
     * 修改视频信息
     *
     * @param videoid     视频id，不可为空
     * @param title       视频标题
     * @param tag         视频标签
     * @param description 视频描述
     * @param categoryid  视频子分类id
     * @param playurl     视频播放页面地址，如果不编辑播放地址，请勿加入此参数
     * @param imageindex  视频封面截图序号，如果不编辑封面截图，请勿加入此参数 注:只可编辑正常可播放状态的视频截图
     */
    public String updateVideo(String videoid, String title, String tag, String description,
                              String categoryid, String playurl, Integer imageindex) {
        StringBuilder param = new StringBuilder();
        param.append("&videoid=").append(videoid);
        try {
            if (title != null) param.append("&title=").append(URLEncoder.encode(title, "UTF-8"));
            if (tag != null) param.append("&tag=").append(URLEncoder.encode(tag, "UTF-8"));
            if (description != null) param.append("&description=").append(URLEncoder.encode(description, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (categoryid != null) param.append("&categoryid=").append(categoryid);
        if (playurl != null) param.append("&playurl=").append(playurl);
        if (imageindex != null) param.append("&imageindex=").append(imageindex);
        return request(THQSConstants.update_video, param.toString(), true);
    }

    /**
     * 获取按视频id范围内所有视频列表
     *
     * @param videoIdFrom 起始 videoid，若为空，则从上传的第一个视频开始(可以为空)
     * @param videoIdTo   终止 videoid，若为空，则到最后一个上传的视频（可以为空）
     * @param numPerPage  返回信息时，每页包含的视频个数 注:允许范围为 1~100
     * @param page        当前页码
     */
    public String getVideoList(String videoIdFrom, String videoIdTo, Integer numPerPage, Integer page) {
        StringBuilder param = new StringBuilder();
        if (videoIdFrom != null) {
            param.append("&videoid_from=").append(videoIdFrom);
        }
        if (videoIdTo != null) {
            param.append("&videoid_to=").append(videoIdTo);
        }
        if (numPerPage > 100) {
            numPerPage = 100;
        } else if (numPerPage < 0) {
            numPerPage = 1;
        }
        param.append("&num_per_page=").append(numPerPage)
                .append("&page=").append(page);
        return request(THQSConstants.get_video_list_by_id, param.toString(), true);
    }

    /**
     * 获取按视频标签内所有视频列表
     *
     * @param numPerPage 返回信息时，每页包含的视频个数 注:允许范围为 1~100
     * @param page       当前页码
     * @param categoryId 视频分类的id，不可为空
     */
    public String getVideoListByCategory(Integer numPerPage, Integer page, String categoryId) {
        StringBuilder param = new StringBuilder();
        param.append("&categoryid=").append(categoryId)
                .append("&num_per_page=").append(numPerPage)
                .append("&page=").append(page);
        return request(THQSConstants.get_video_list_by_category, param.toString(), true);
    }

    /**
     * 获得视频所有的分类
     */
    public String getVideoCategory() {
        return request(THQSConstants.get_video_category, "", true);
    }


    /**
     * 按视频标题和分类获取视频
     *
     * @param type 查询的类型，现在有标题，标签两类
     * @param content      查询的内容
     * @param sort       是否排序，否为升序，是为降序
     * @param numPerPage 返回信息时，每页包含的视频个数 注:允许范围为 1~100
     * @param page       当前页码
     * @param categoryId 视频分类的id，可为空
     */
    public String searchVideo(THQSConstants type, String content, Boolean sort, String categoryId, Integer numPerPage, Integer page) {
        StringBuilder param = new StringBuilder();
        if (categoryId != null) {
            param.append("&categoryid=").append(categoryId);
        }
        param.append("&num_per_page=").append(numPerPage)
                .append("&page=").append(page);
        switch (type) {
            case TITLE:
                param.append("&q=TITLE%3A");
                break;
            case TAG:
                param.append("&q=TAG%3A");
                break;
        }
        try {
            //对中文进行转码，：也需要转码
            param.append(URLEncoder.encode(content, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (sort) {
            param.append("&sort=CREATION_DATE%3ADESC");
        } else {
            param.append("&sort=CREATION_DATE%3AASC");
        }
        return request(THQSConstants.search_video, param.toString(), true);
    }


    /**
     * 获得直播间列表
     *
     * @param pagenum   每页显示的个数	可选，系统默认值为50
     * @param pageindex 页码	可选，系统默认值为1
     */
    public String getRoomList(Integer pagenum, Integer pageindex) {
        StringBuilder param = new StringBuilder();
        if (pagenum != null) {
            param.append("&pagenum=").append(pagenum);
        }
        if (pageindex != null) {
            param.append("&pageindex=").append(pageindex);
        }
        return request(THQSConstants.get_room_list, param.toString(), false);
    }


    /**
     * 将参数按字典序排序
     *
     * @param queryString 查询的参数
     * @return 按字典序的参数
     */
    private String order(String queryString) {
        String[] list = queryString.split("&");
        return Arrays.stream(list)
                .sorted()
                .collect(Collectors.joining("&"));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     */
    private String DateToTimeStamp(String dateStr, String format) {
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
    private String getMD5(String message) {
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
    private String bytesToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        // 把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
            digital = bytes[i];
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
    private String decode(String queryString, boolean isVideo) {

        //获得当前时间
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        //最終的MD5后的結果除了&hash=以外的参数
        String hashedQueryString = order(queryString)
                + "&time=" + DateToTimeStamp(simpleDateFormat.format(now), dateFormat);
        //进行md5的参数
        String temp;
        if (isVideo) {
            temp = hashedQueryString + "&salt=" + key;
        } else {
            temp = hashedQueryString + "&salt=" + room_key;
        }
        return hashedQueryString + "&hash=" + getMD5(temp);
    }

    /**
     * 使用HttpURLConnection
     * 访问CC视频接口的函数
     *
     * @param requestUri 調用的api的地址
     * @param isVideo    是否调用点播，true为点播api，false为直播api
     * @return 请求结果
     */
    private String request(String requestUri, String params, boolean isVideo) {
        URL url;
        HttpURLConnection conn;
        try {
            url = new URL(requestUri + decode(url_prefix + params, isVideo));
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
