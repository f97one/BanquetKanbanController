package net.formula97.banquetkanbancontroller.dto;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 宴会予定の詳細を保持するDTO。
 * Created by f97one on 2015/10/31.
 */
@DatabaseTable(tableName = "BanquetSchedule")
public class BanquetSchedule implements Serializable {

    /**
     * 対応するテーブル名
     */
    public static final String TABLE_NAME = BanquetSummary.class.getSimpleName();

    /**
     * フィールド名：宴会ID(BANQUET_ID)
     */
    public static final String FIELD_BANQUET_ID = "BANQUET_ID";
    /**
     * フィールド名：表示シーケンス番号(SEQ_NO)
     */
    public static final String FIELD_SEQ_NO = "SEQ_NO";
    /**
     * フィールド名：発動までの経過時間／時間(ELAPSED_HOUR)
     */
    public static final String FIELD_ELAPSED_HOUR = "ELAPSED_HOUR";
    /**
     * フィールド名：発動までの経過時間／分(ELAPSED_MINUTE)
     */
    public static final String FIELD_ELAPSED_MINUTE = "ELAPSED_MINUTE";
    /**
     * フィールド名：イベントの詳細(DESCRIPTION)
     */
    public static final String FIELD_DESCRIPTION = "DESCRIPTION";

    /**
     * シリアライズのUID
     */
    private static final long serialVersionUID = 6010282847394602371L;

    /**
     * 宴会ID
     */
    @DatabaseField(id = true, columnName = FIELD_BANQUET_ID, foreign = true, foreignColumnName = "BanquetSummary.BANQUET_ID")
    private Integer banquetId;
    /**
     * 表示シーケンス番号
     */
    @DatabaseField(id = true, columnName = FIELD_SEQ_NO, defaultValue = "1")
    private int seqNo;
    /**
     * 発動までの経過時間／時間
     */
    @DatabaseField(columnName = FIELD_ELAPSED_HOUR, defaultValue = "0")
    private int elapsedHour;
    /**
     * 発動までの経過時間／分
     */
    @DatabaseField(columnName = FIELD_ELAPSED_MINUTE, defaultValue = "0")
    private int elapsedMinute;
    /**
     * イベントの詳細
     */
    @DatabaseField(columnName = FIELD_DESCRIPTION)
    private String description;

    public Integer getBanquetId() {
        return banquetId;
    }

    public void setBanquetId(Integer banquetId) {
        this.banquetId = banquetId;
    }

    public int getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(int seqNo) {
        this.seqNo = seqNo;
    }

    public int getElapsedHour() {
        return elapsedHour;
    }

    public void setElapsedHour(int elapsedHour) {
        this.elapsedHour = elapsedHour;
    }

    public int getElapsedMinute() {
        return elapsedMinute;
    }

    public void setElapsedMinute(int elapsedMinute) {
        this.elapsedMinute = elapsedMinute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
