package net.formula97.banquetkanbancontroller.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import net.formula97.banquetkanbancontroller.dto.BanquetSchedule;
import net.formula97.banquetkanbancontroller.dto.BanquetSummary;

import java.sql.SQLException;

/**
 * OrmLite経由でSQLiteOpenHelperを扱うクラス。<br />
 * Created by f97one on 2015/10/31.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    /**
     * データベースのバージョン
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * データベースファイルの名称
     */
    public static final String DATABASE_NAME = "BanquetKanban.db";

    /**
     * ユーザー定義のコンストラクタ。<br />
     * Context以外は定数。
     *
     * @param context SQLite Database取得に使うApplication Context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * システム上必要なコンストラクタ。<br />
     *
     * @param context データベース取得に使用するApplication Context
     * @param databaseName オープンするSQLite Databaseの名称
     * @param factory Cursor Factory、使用しない場合はnullを指定
     * @param databaseVersion オープンするSQLite Databaseのバージョン
     * @see OrmLiteSqliteOpenHelper
     */
    public DatabaseHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, BanquetSummary.class);
            TableUtils.createTable(connectionSource, BanquetSchedule.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // バージョンは1なので、現状特に何もしない
    }
}
