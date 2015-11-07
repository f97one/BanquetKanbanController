package net.formula97.banquetkanbancontroller.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 宴会のサマリを保持するDTO。<br />
 * Created by f97one on 2015/10/31.
 */
@DatabaseTable(tableName = "BanquetSummary")
public class BanquetSummary implements Serializable {

    /**
     * 対応するテーブル名
     */
    public static final String TABLE_NAME = BanquetSummary.class.getSimpleName();

    /**
     * フィールド名：宴会ID(BANQUET_ID)
     */
    public static final String FIELD_BANQUET_ID = "BANQUET_ID";
    /**
     * フィールド名：宴会名称(BANQUET_NAME)
     */
    public static final String FIELD_BANQUET_NAME = "BANQUET_NAME";
    /**
     * フィールド名：開始予定日時(START_AT)
     */
    public static final String FIELD_START_AT = "START_AT";

    /**
     * シリアライズのUID
     */
    private static final long serialVersionUID = 5427755325678055643L;

    /**
     * DB管理上必要な一貫番号
     */
    @DatabaseField(generatedId = true)
    private Integer _id;
    /**
     * 宴会ID
     */
    @DatabaseField(unique = true, columnName = FIELD_BANQUET_ID)
    private Integer banquetId;
    /**
     * 宴会名称
     */
    @DatabaseField(canBeNull = false, columnName = FIELD_BANQUET_NAME)
    private String banquetName;
    /**
     * 開始予定日時
     */
    @DatabaseField(columnName = FIELD_START_AT, dataType = DataType.DATE_TIME)
    private Calendar startAt;

    /**
     * システム上必要なコンストラクタ。
     */
    public BanquetSummary() {
        // システム上必要なので、中身は空
    }

    /**
     * 開始時刻を現在時刻で生成するコンストラクタ。
     *
     * @param newBanquetName 生成する宴会名称
     */
    public BanquetSummary(String newBanquetName) {
        this.banquetName = newBanquetName;
        this.startAt = Calendar.getInstance();
    }

    public Integer getBanquetId() {
        return banquetId;
    }

    public void setBanquetId(Integer banquetId) {
        this.banquetId = banquetId;
    }

    public String getBanquetName() {
        return banquetName;
    }

    public void setBanquetName(String banquetName) {
        this.banquetName = banquetName;
    }

    public Calendar getStartAt() {
        return startAt;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public void setStartAt(Calendar startAt) {
        this.startAt = startAt;
    }
}
