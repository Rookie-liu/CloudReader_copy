package com.example.rookie.cloudreader_c.viewmodel.movie.gank;

import java.util.ArrayList;

/**
 * Created by rookie on 2018/4/4.
 */

public interface WelfareNavigator extends BigAndroidNavigator {
    /**
     * 更新请求到的图片的全部数据
     */
    void setImageList(ArrayList<String> list, ArrayList<String> imgList);
}

