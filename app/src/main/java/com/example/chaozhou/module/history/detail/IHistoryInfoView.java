package com.example.chaozhou.module.history.detail;

import com.example.chaozhou.api.bean.HistoryInfo;
import com.example.chaozhou.module.base.IBaseView;

public interface IHistoryInfoView extends IBaseView {

    /**
     * 显示数据
     * @param historyInfo 历史景点
     */
    void loadData(HistoryInfo historyInfo);
}
