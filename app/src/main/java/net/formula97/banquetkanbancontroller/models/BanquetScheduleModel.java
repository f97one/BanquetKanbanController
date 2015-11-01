/*
 * Copyright (c) 2015. Formula97.NET and HAJIME Fukuna(a.k.a. f97one) (f97one@hotmail.co.jp)
 *
 */

package net.formula97.banquetkanbancontroller.models;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import net.formula97.banquetkanbancontroller.dto.BanquetSchedule;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 宴会予定詳細(BanquetSchedule)を操作するModelクラス。<br />
 * Created by HAJIME on 2015/11/01.
 */
public class BanquetScheduleModel extends BaseModel<BanquetSchedule> {

    /**
     * このテーブルを操作するDAO。
     */
    private Dao<BanquetSchedule, Integer> mDao;

    /**
     * コンストラクタ。
     *
     * @param context SQLite Database取得に必要なApplication Context
     */
    public BanquetScheduleModel(Context context) {
        super(context);

        mDao = getDao();
        if (mDao == null) {
            throw new IllegalStateException("DAOが取得できませんでした。");
        }
    }

    @Override
    protected Dao<BanquetSchedule, Integer> getDao() {
        try {
            return getHelper().getDao(BanquetSchedule.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BanquetSchedule> findAll() throws SQLException {
        return mDao.queryForAll();
    }

    @Override
    public List<BanquetSchedule> findBySingleArg(String fieldName, Object value) throws SQLException {
        return mDao.queryForEq(fieldName, value);
    }

    @Override
    public List<BanquetSchedule> findByFieldValuesArgs(Map<String, Object> fieldValues) throws SQLException {
        return mDao.queryForFieldValuesArgs(fieldValues);
    }
}
