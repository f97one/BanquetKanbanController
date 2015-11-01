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
     * 自身のインスタンスを保持するメンバー
     */
    private static DatabaseHelper mHelper;

    /**
     * システム上必要なコンストラクタ。<br />
     * こちらを使用されるとHelperがSingletonにならないため、開発者レベルでの普段の呼び出しには使用しないこと。<br />
     * ※使用したからといって、例外を投げられる等何かペナルティがあるというわけではない。
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

    /**
     * 自身のインスタンスを取得する。<br />
     * 開発者レベルでの普段の呼び出しは、こちらを使用すること。
     *
     * @param context データベース取得に使用するApplication Context
     * @return Singletonな自身のインスタンス
     */
    public static DatabaseHelper getHelper(Context context) {
        if (mHelper == null) {
            mHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        return mHelper;
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
