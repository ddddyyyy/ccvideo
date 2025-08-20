package com.mdy.ccvideo.dict;

/**
 * CC视频接口的API地址
 *
 * @author MDY
 */
public enum THQSConstants {
    /**
     * 搜索视频时用到标题
     */
    TITLE,
    /**
     * 搜索视频时用到分类
     */
    TAG;

    // =================================== CC API 列表 =======================================  //

    public static final String VIDEO_CREATE_UPLOAD_INFO = "http://spark.bokecc.com/api/video/create/v2?";
    public static final String delete_video = "http://spark.bokecc.com/api/video/delete?";
    public static final String get_user_info = "http://spark.bokecc.com/api/user?";
    public static final String get_video_list_by_category = "http://spark.bokecc.com/api/videos/category?";
    public static final String get_video_list_by_id = "http://spark.bokecc.com/api/videos/v3?";
    public static final String get_video_by_id = "http://spark.bokecc.com/api/video/v3?";
    public static final String get_video_category = "http://spark.bokecc.com/api/video/category/v2?";
    public static final String search_video = "http://spark.bokecc.com/api/videos/search?";
    public static final String get_room_list = "http://api.csslcloud.net/api/room/info?";
    public static final String update_video = "http://spark.bokecc.com/api/video/update?";
    public static final String video_play = "http://spark.bokecc.com/api/video/playcode?";
    public static final String get_video_info = "https://spark.bokecc.com/api/video/v6?";

    // =================================== CC API 列表 =======================================  //


    //点播的key
    public static final String key = "";
    //直播的key
    public static final String room_key = "";
    //用户ID
    public static final String userid = "";
    //同一返回JSON格式，并加上userid
    public static final String url_prefix = String.format("format=json&userid=%s", userid);
    //日期的格式
    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

}
