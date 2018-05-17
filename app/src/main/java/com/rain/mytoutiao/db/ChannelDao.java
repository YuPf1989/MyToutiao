package com.rain.mytoutiao.db;

import android.support.annotation.NonNull;

import com.rain.mytoutiao.MyApplication;
import com.rain.mytoutiao.R;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.FastStoreModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;
import java.util.Objects;

/**
 * Author:rain
 * Date:2018/5/15 11:44
 * Description:
 * 首页所有频道的表
 */
@Table(database = MyDatabase.class)
public class ChannelDao extends BaseModel implements Comparable<ChannelDao> {
    @PrimaryKey
    public String id;
    @Column
    public String name;
    @Column(defaultValue = "0")
    public boolean is_enable;
    @Column
    public int position;

    public boolean enableEdit = false;// 当前条目是否可编辑

    public ChannelDao() {

    }

    // note:注意一定要有无参构造
    public ChannelDao(String id, String name, boolean is_enable, int position) {
        this.id = id;
        this.name = name;
        this.is_enable = is_enable;
        this.position = position;
    }

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

    // true 表示返回我的频道，false 表示隐藏频道
    public static List<ChannelDao> queryChannel(boolean is_enable) {
        return SQLite.select()
                .from(ChannelDao.class)
                .where(ChannelDao_Table.is_enable.eq(is_enable))
                .orderBy(ChannelDao_Table.position, true)
                .queryList();
    }

    public static void addAllChannels(final List<ChannelDao> list) {
        FlowManager.getDatabase(MyDatabase.class)
                .executeTransaction(databaseWrapper -> {
                    FastStoreModelTransaction
                            .saveBuilder(FlowManager.getModelAdapter(ChannelDao.class))
                            .addAll(list)
                            .build()
                            .execute(databaseWrapper);
                });
    }

    public static void addChannels(final List<ChannelDao> list) {
        FlowManager.getDatabase(MyDatabase.class)
                .executeTransaction(databaseWrapper -> {
                    ChannelDao channelDao = null;
                    for (int i = 0; i < list.size(); i++) {
                        ChannelDao srcDao = list.get(i);
                        channelDao = new ChannelDao(srcDao.id,srcDao.name,srcDao.is_enable,i);
                        channelDao.insert();
                    }
                });
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelDao that = (ChannelDao) o;
        return is_enable == that.is_enable &&
                position == that.position &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, is_enable, position);
    }

    @Override
    public int compareTo(@NonNull ChannelDao o) {
        return this.position - o.position;
    }

    @Override
    public String toString() {
        return "ChannelDao{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", is_enable=" + is_enable +
                ", position=" + position +
                '}';
    }
}
