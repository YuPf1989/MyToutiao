package com.rain.mytoutiao.db;

import com.rain.mytoutiao.MyApplication;
import com.rain.mytoutiao.R;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Author:rain
 * Date:2018/5/15 11:44
 * Description:
 * 首页所有频道的表
 */
@Table(database = MyDatabase.class)
public class ChannelDao extends BaseModel{
    @PrimaryKey
    public String id;
    @Column
    public String name;
    @Column(defaultValue = "0")// TODO: 2018/5/15 1 应该是true
    public boolean is_enable;
    @Column
    public int position;

    // 初始化频道数据
    public static void addInitData() {
        ChannelDao channelDao;
        MyApplication app = MyApplication.getInstance();
        String[] categoryName = app.getResources().getStringArray(R.array.mobile_news_name);
        String[] categoryid = app.getResources().getStringArray(R.array.mobile_news_id);
        for (int i = 0; i < 8; i++) {
            channelDao = new ChannelDao();
            channelDao.id = categoryid[i];
            channelDao.is_enable = true;
            channelDao.name = categoryName[i];
            channelDao.position = i;
            channelDao.insert();
        }
        for (int i = 8; i < categoryid.length; i++) {
            channelDao = new ChannelDao();
            channelDao.id = categoryid[i];
            channelDao.is_enable = false;
            channelDao.name = categoryName[i];
            channelDao.position = i;
            channelDao.insert();
        }
    }


}
