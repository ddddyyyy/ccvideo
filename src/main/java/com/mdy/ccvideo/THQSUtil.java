package com.mdy.ccvideo;

import com.alibaba.fastjson.JSONObject;
import com.mdy.ccvideo.dict.THQSConstants;
import com.mdy.ccvideo.dict.THQSErrorCode;
import com.mdy.ccvideo.exception.ExceptionHelper;
import com.mdy.ccvideo.exception.THQSException;
import com.mdy.ccvideo.util.IStringUtils;
import com.mdy.ccvideo.util.UserContextHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static com.mdy.ccvideo.util.RequestHelper.decode;


/**
 * CC视频工具类
 *
 * @author MDY
 */
public class THQSUtil {

    private static final Logger log = LogManager.getLogger(THQSUtil.class);

    //统一一返回JSON格式，并加上userid
    private static final String url_prefix_prefix = "format=json&userid=%s";

    /**
     * @param result 请求返回的报文
     * @return 请求返回的报文
     */
    private String checkResult(String result) {
        try {
            JSONObject jsonObject = JSONObject.parseObject(result);
            String error = jsonObject.getString("error");
            if (IStringUtils.isNotBlank(error)) {
                // 请求返回异常
                try {
                    throw new THQSException(THQSErrorCode.valueOf(error));
                } catch (IllegalArgumentException e) {
                    // 异常不在枚举内
                    throw new THQSException(error, THQSErrorCode.UNKNOWN.name());
                }
            }
        } catch (Exception e) {
            if (e instanceof THQSException) {
                throw e;
            } else {
                log.error(ExceptionHelper.getTrace(e));
                throw new THQSException(e.getMessage(), THQSErrorCode.UNKNOWN.name());
            }
        }
        return result;
    }

    /**
     * 根据视频id获取视频信息
     *
     * @param videoId   视频id
     * @param imageType 返回封面截图的类型,默认值:0(0:小图;1:大图)
     */
    public String getVideoInfo(String videoId, Integer imageType) {
        StringBuilder param = new StringBuilder();
        param.append("&videoid=").append(videoId);
        if (null != imageType) {
            param.append("&imagetype").append(imageType);
        }
        return request(THQSConstants.get_video_info, param.toString(), true);
    }

    /**
     * @param description 视频描述
     * @param tag         视频标签
     * @param categoryId  分类id
     * @param filename    文件名
     * @param filesize    文件大小
     * @param notify_url  回调地址
     * @return 视频上传的信息
     */
    public String createVideoUploadInfo(String title, String description, String tag, String categoryId, String filename, String filesize, String notify_url) throws UnsupportedEncodingException {
        StringBuilder param = new StringBuilder();
        if (tag != null) {
            param.append("&tag=").append(URLEncoder.encode(tag, "UTF-8"));
        }
        if (description != null) {
            param.append("&description=").append(URLEncoder.encode(description, "UTF-8"));
        }
        if (categoryId != null) {
            param.append("&categoryid=").append(categoryId);
        }
        if (filename != null) {
            param.append("&filename=").append(URLEncoder.encode(filename, "UTF-8"));
        }
        if (title != null) {
            param.append("&title=").append(URLEncoder.encode(title, "UTF-8"));
        }
        if (filesize != null) {
            param.append("&filesize=").append(filesize);
        }
        if (notify_url != null) {
            param.append("&notify_url=").append(URLEncoder.encode(notify_url, "UTF-8"));
        }
        return request(THQSConstants.VIDEO_CREATE_UPLOAD_INFO, param.toString(), true);
    }

    /**
     * @param videoid       视频id，不可为空
     * @param playerid      播放器id，可为空，若为空，返回默认播放器
     * @param player_width  播放器宽度，可为空，单位px，若为空，返回600
     * @param player_height 播放器高度，可为空，单位px，若为空，返回490
     * @param auto_play     是否自动播放，可为空，true 或false,若为空，返回false
     * @return 视频播放的js代码
     * @throws UnsupportedEncodingException 不支持编码
     */
    public String getVideoPlayUrl(String videoid, String playerid, String player_width, String player_height, String auto_play) throws UnsupportedEncodingException {
        StringBuilder param = new StringBuilder();
        param.append("&videoid=").append(URLEncoder.encode(videoid, "UTF-8"));
        param.append("&playerid=").append(URLEncoder.encode("85DF102AFC882C2C", "UTF-8"));
        if (playerid != null) {
            param.append("&playerid=").append(URLEncoder.encode(playerid, "UTF-8"));
        }
        if (player_width != null) {
            param.append("&player_width=").append(URLEncoder.encode(player_width, "UTF-8"));
        }
        if (player_height != null) {
            param.append("&player_height=").append(URLEncoder.encode(player_height, "UTF-8"));
        }
        if (auto_play != null) {
            param.append("&auto_play=").append(URLEncoder.encode(auto_play, "UTF-8"));
        }
        return request(THQSConstants.video_play, param.toString(), true);
    }

    /**
     * 根据视频id删除视频
     *
     * @param videoid 要删除的视频的id
     */
    public String deleteVideo(String videoid) {
        return request(THQSConstants.delete_video, "&videoid=" + videoid, true);
    }

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
        String param = "&categoryid=" + categoryId +
                "&num_per_page=" + numPerPage +
                "&page=" + page;
        return request(THQSConstants.get_video_list_by_category, param, true);
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
     * @param type       查询的类型，现在有标题，标签两类
     * @param content    查询的内容
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
            url = new URL(requestUri + decode(String.format(url_prefix_prefix, UserContextHelper.getUserId()) + params, isVideo));
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            try (InputStream inputStream = conn.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
                StringBuilder stringBuffer = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                return checkResult(stringBuffer.toString());
            }
        } catch (IOException e) {
            log.error(ExceptionHelper.getTrace(e));
            throw new THQSException(THQSErrorCode.NETWORK_ERROR);
        }
    }

}
