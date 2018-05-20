package com.mdy.ccvideo;

public class Main {

    public static void main(String[] args) {
        THQSUtil thqsUtil = new THQSUtil();
        System.out.println(thqsUtil.getVideoList(null, null, 100, 1));
        System.out.println(thqsUtil.getVideoCategory());
        //System.out.println(thqsUtil.getRoomList(null, null));
        //System.out.println(thqsUtil.getVideoListByCategory(100, 1, "0E4886C912E0E7A6"));
       System.out.println(thqsUtil.searchVideo(THQSConstants.TAG,"免费", false, "685636B586D6F1B1", 10, 1));
       // System.out.println(thqsUtil.updateVideo("D0C698CE280D7AF39C33DC5901307461","测试","测试","测试",null,null,null));
    }
}
