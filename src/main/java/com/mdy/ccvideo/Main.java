package com.mdy.ccvideo;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        THQSUtil thqsUtil = new THQSUtil();
        System.out.println(thqsUtil.getVideoList(null, null, 100, 1));
        System.out.println(thqsUtil.getVideoCategory());
        System.out.println(thqsUtil.getRoomList(null, null));
        System.out.println(thqsUtil.getVideoListByCategory(100, 1, "0E4886C912E0E7A6"));
        System.out.println(thqsUtil.searchVideo("罗", false, "685636B586D6F1B1", 10, 1));
    }
}
