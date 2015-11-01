/*
 * Copyright (c) 2015. Formula97.NET and HAJIME Fukuna(a.k.a. f97one) (f97one@hotmail.co.jp)
 *
 */

package net.formula97.banquetkanbancontroller.models;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * DB操作の基礎部分を実装したModelの基底クラス。<br />
 * Created by f97one on 2015/11/01.
 */
public abstract class BaseModel<T> {

    /**
     * DatabaseHelperのインスタンス
     */
    private DatabaseHelper mHelper;
    /**
     * SQLite Database取得に使用するContext
     */
    private Context mContext;

    /**
     * コンストラクタ。
     *
     * @param context SQLite Database取得に必要なApplication Context
     */
    public BaseModel(Context context) {
        mHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        mContext = context;
    }

    /**
     * 取得したDatabaseHelperを返す。
     *
     * @return 取得したDatabaseHelper
     */
    protected DatabaseHelper getHelper() {
        return mHelper;
    }

    /**
     * 保持しているContextを返す。
     *
     * @return SQLite Database取得に使用したContext
     */
    protected Context getContext() {
        return mContext;
    }

    /**
     * 取得したDatabaseHelperを開放する。
     */
    protected void release() {
        if (mHelper != null) {
            OpenHelperManager.releaseHelper();
            mHelper = null;
        }
    }

    /**
     * OrmLite経由でDAOを取得する。
     *
     * @return 指定テーブルのDAO
     */
    protected abstract Dao<T, Integer> getDao();

    /**
     * 現在保持しているすべてのレコードを取得する。
     *
     * @return 現在保持しているすべてのレコード
     * @throws SQLException SQL処理に何らかの問題があったとき
     */
    public abstract List<T> findAll() throws SQLException;

    /**
     * 指定フィールドの値が一致するレコードを取得する。
     *
     * @param fieldName 検索対象のフィールド名
     * @param value 検索対象の値
     * @return 条件に一致するすべてのレコード
     * @throws SQLException SQL処理に何らかの問題があったとき
     */
    public abstract List<T> findBySingleArg(String fieldName, Object value) throws SQLException;

    /**
     * 指定する「フィールドと値のセット」に該当するレコードを取得する。
     *
     * @param fieldValues 検索対象のフィールドと値のセット
     * @return 条件に一致するすべてのレコード
     * @throws SQLException SQL処理に何らかの問題があったとき
     */
    public abstract List<T> findByFieldValuesArgs(Map<String, Object> fieldValues) throws SQLException;

    /**
     * 指定エンティティを保存(INSERT/UPDATE)する。
     *
     * @param entity 保存対象のEntity
     * @return 保存を行ったときの状況
     * @throws SQLException SQL処理に何らかの問題があったとき
     * @see Dao.CreateOrUpdateStatus#isCreated()
     * @see Dao.CreateOrUpdateStatus#isUpdated()
     * @see Dao.CreateOrUpdateStatus#getNumLinesChanged()
     */
    public Dao.CreateOrUpdateStatus save(final T entity) throws SQLException {
        return TransactionManager.callInTransaction(
                getHelper().getConnectionSource(), new Callable<Dao.CreateOrUpdateStatus>() {
            @Override
            public Dao.CreateOrUpdateStatus call() throws Exception {
                Dao<T, Integer> dao = getHelper().getDao((Class<T>) entity.getClass());
                return dao.createOrUpdate(entity);
            }
        });
    }

    /**
     * 指定エンティティのまとまりを保存する。
     *
     * @param entities 保存対象のEntityのリスト
     * @throws SQLException SQL処理に何らかの問題があったとき
     */
    public void save(final List<T> entities) throws SQLException {
        TransactionManager.callInTransaction(getHelper().getConnectionSource(), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Dao<T, Integer> dao = getHelper().getDao((Class<T>) entities.get(0).getClass());

                for (Object entity : entities) {
                    dao.createOrUpdate((T) entity);
                }

                return null;
            }
        });
    }

    /**
     * 指定エンティティを消去する。
     *
     * @param entity 消去対象のエンティティ
     * @return 影響のあった（＝削除された）レコード数
     * @throws SQLException SQL処理に何らかの問題があったとき
     */
    public int erase(final T entity) throws SQLException {
        return TransactionManager.callInTransaction(getHelper().getConnectionSource(), new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Dao<T, Integer> dao = getHelper().getDao((Class<T>) entity.getClass());
                return dao.delete(entity);
            }
        });
    }
}
