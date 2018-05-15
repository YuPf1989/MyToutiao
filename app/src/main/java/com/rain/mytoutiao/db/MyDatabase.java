package com.rain.mytoutiao.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Author:rain
 * Date:2018/5/15 11:39
 * Description:
 * 创建数据库
 */
@Database(name = MyDatabase.DB_NAME,version = MyDatabase.VERSION)
public class MyDatabase {
    static final String DB_NAME = "User";
    static final int VERSION = 1;
}
