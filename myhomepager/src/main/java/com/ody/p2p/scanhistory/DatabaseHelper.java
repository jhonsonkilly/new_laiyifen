package com.ody.p2p.scanhistory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ody.p2p.data.ScanHistoryBean;

import java.sql.SQLException;

/**
 * Created by admin on 2016/2/19.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "sqlite-ody_history.db";
    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 5);
    }

    /**
     * 单例获取该Helper
     */
    public static synchronized DatabaseHelper getHelper(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }
        return instance;
    }

    /**
     * riciDao ，每张表对于一个
     */
    private Dao<ScanHistoryBean, Integer> riciDao;

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ScanHistoryBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ScanHistoryBean.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得热词
     *
     * @return
     * @throws SQLException
     */
    public Dao<ScanHistoryBean, Integer> getriciDao() throws SQLException {
        if (riciDao == null) {
            riciDao = getDao(ScanHistoryBean.class);
        }
        return riciDao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        riciDao = null;
    }
}
