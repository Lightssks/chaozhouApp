package com.example.chaozhou.local.db;

import android.content.Context;

import com.example.chaozhou.local.table.User;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class UserDaoOpe {
    /**
     * 添加数据至数据库
     * @param context
     * @param user
     */

    public static void insertData(Context context, User user) {
        DbManager.getDaoSession(context).getUserDao().insert(user);
    }

    /**
     * 将数据实体通过事务添加至数据库
     *
     * @param context
     * @param list
     */
    public static void insertData(Context context, List<User> list) {
        if (null == list || list.size() <= 0) {
            return;
        }
        DbManager.getDaoSession(context).getUserDao().insertInTx(list);
    }

    /**
     * 添加数据至数据库，如果存在，将原来的数据覆盖
     * 内部代码判断了如果存在就update(entity);不存在就insert(entity)；
     *
     * @param context
     * @param user
     */
    public static void saveData(Context context, User user) {
        DbManager.getDaoSession(context).getUserDao().save(user);
    }

    /**
     * 删除数据至数据库
     *
     * @param context
     * @param user 删除具体内容
     */
    public static void deleteData(Context context, User user) {
        DbManager.getDaoSession(context).getUserDao().delete(user);
    }

    /**
     * 根据id删除数据至数据库
     *
     * @param context
     * @param id      删除具体内容
     */
    public static void deleteByKeyData(Context context, long id) {
        DbManager.getDaoSession(context).getUserDao().deleteByKey(id);
    }

    /**
     * 删除全部数据
     *
     * @param context
     */
    public static void deleteAllData(Context context) {
        DbManager.getDaoSession(context).getUserDao().deleteAll();
    }

    /**
     * 更新数据库
     *
     * @param context
     * @param user
     */
    public static void updateData(Context context, User user) {
        DbManager.getDaoSession(context).getUserDao().update(user);
    }


    /**
     * 查询所有数据
     *
     * @param context
     * @return
     */
    public static List<User> queryAll(Context context) {
        QueryBuilder<User> builder = DbManager.getDaoSession(context).getUserDao().queryBuilder();

        return builder.build().list();
    }


}
