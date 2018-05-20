package com.mdy.ccvideo;

/**
 * CC视频接口的API地址
 *
 * @author MDY
 */
public enum THQSConstants {
    TITLE, TAG;

    public static final String get_user_info = "http://spark.bokecc.com/api/user?";
    public static final String get_video_list_by_category = "http://spark.bokecc.com/api/videos/category?";
    public static final String get_video_list_by_id = "http://spark.bokecc.com/api/videos/v3?";
    public static final String get_video_by_id = "http://spark.bokecc.com/api/video/v3?";
    public static final String get_video_category = "http://spark.bokecc.com/api/video/category/v2?";
    public static final String search_video = "http://spark.bokecc.com/api/videos/search?";
    public static final String get_room_list = "http://api.csslcloud.net/api/room/info?";
    public static final String update_video = "http://spark.bokecc.com/api/video/update?";

}
