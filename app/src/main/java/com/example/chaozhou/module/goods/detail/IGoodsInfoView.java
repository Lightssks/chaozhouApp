package com.example.chaozhou.module.goods.detail;

import com.example.chaozhou.api.bean.GoodsInfo;
import com.example.chaozhou.module.base.IBaseView;

public interface IGoodsInfoView extends IBaseView{

    /**
     * 显示数据
     * @param goodsInfo 特产详情
     */
    void loadData(GoodsInfo goodsInfo);

}
