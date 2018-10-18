package com.example.chaozhou.module.personal_Info.detail;

import com.example.chaozhou.local.table.Personal_Info;
import com.example.chaozhou.module.base.ILoadDataView;

import java.util.List;

public interface Personal_InfoView extends ILoadDataView<List<Personal_Info>> {
    /**
     * 显示数据
     * @param personal_info 个人详情
     */
    void loadData(Personal_Info Info);
}
