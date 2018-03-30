package com.example.rookie.cloudreader_c.viewmodel.movie;

import com.example.rookie.cloudreader_c.bean.HotMovieBean;

/**
 * @author jingbin
 * @data 2017/12/26
 * @Description
 */

public interface OnMovieLoadListener {

    void onSuccess(HotMovieBean bean);

    void onFailure();
}
