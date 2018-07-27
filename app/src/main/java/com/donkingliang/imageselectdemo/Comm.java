package com.donkingliang.imageselectdemo;

import java.util.List;

/**
 * Created by Wisn on 2018/7/27 上午11:27.
 */
public class Comm {


    /**
     * token : 5024f7c0079e32d94b831518ccd63e2063
     * postType : 2
     * schoolId : 115
     * channelId : 7004
     * content : 这是一个测试帖
     * imagesUrl : ["http://cdn.oudianyun.com/102400/1532335899271_4938_126.jpg"]
     * postContextVO : {"authority":1,"forumCategoryIds":[1,2,3]}
     * labelsVO : [{"id":22,"name":"暑假旅行"},{"id":33,"name":"美食节"}]
     * lat : 23.25
     * lng : 15.23
     * address : 上海徐汇
     * appver : fwef
     * appplt : IOS
     * deviceId : grht
     */

    public String token;
    public int postType;
    public int schoolId;
    public int channelId;
    public String content;
    public PostContextVOBean postContextVO;
    public double lat;
    public double lng;
    public String address;
    public String appver;
    public String appplt;
    public String deviceId;
    public List<String> imagesUrl;
    public List<LabelsVOBean> labelsVO;

    public static class PostContextVOBean {
        /**
         * authority : 1
         * forumCategoryIds : [1,2,3]
         */

        public int authority;
        public List<Integer> forumCategoryIds;
    }

    public static class LabelsVOBean {
        /**
         * id : 22
         * name : 暑假旅行
         */

        public int id;
        public String name;
    }
}
