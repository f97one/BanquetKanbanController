/*
 * Copyright (c) 2015. Formula97.NET and HAJIME Fukuna(a.k.a. f97one) (f97one@hotmail.co.jp)
 *
 */

package net.formula97.banquetkanbancontroller.models;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import net.formula97.banquetkanbancontroller.entities.BanquetSummary;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 宴会サマリ情報(banquetSummary)を操作するModelクラス。<br />
 * Created by f97one on 2015/11/01.
 */
public class BanquetSummaryModel extends BaseModel<BanquetSummary> {

    /**
     * このテーブルを操作するDAO。
     */
    private Dao<BanquetSummary, Integer> mDao;

    /**
     * コンストラクタ。
     *
     * @param context SQLite Database取得に必要なApplication Context
     */
    public BanquetSummaryModel(Context context) {
        super(context);

        mDao = getDao();

        if (mDao == null) {
            throw new IllegalStateException("DAOが取得できませんでした。");
        }
    }

    @Override
    protected Dao<BanquetSummary, Integer> getDao() {
        try {
            return getHelper().getDao(BanquetSummary.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BanquetSummary> findAll() throws SQLException {
        return mDao.queryForAll();
    }

    @Override
    public List<BanquetSummary> findBySingleArg(String fieldName, Object value) throws SQLException {
        return mDao.queryForEq(fieldName, value);
    }

    @Override
    public List<BanquetSummary> findByFieldValuesArgs(Map<String, Object> fieldValues) throws SQLException {
        return mDao.queryForFieldValuesArgs(fieldValues);
    }

    /**
     * 宴会IDを生成する。
     *
     * @return 1～1000000までのランダム値で、かつユニークとなるID
     */
    public int createBanquetId() {
        int ret = -1;
        try {
            List<BanquetSummary> currentSummaries = findAll();

            Random random = new SecureRandom();

            boolean judge = false;
            // 初回の偏りを回避するため一旦空打ちする
            ret = random.nextInt(10 ^ 6) + 1;
            do {
                ret = random.nextInt(10 ^ 6) + 1;
                for (BanquetSummary summary : currentSummaries) {
                    if (summary.getBanquetId() == ret) {
                        judge = true;
                        break;
                    }
                }
            } while (judge);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
