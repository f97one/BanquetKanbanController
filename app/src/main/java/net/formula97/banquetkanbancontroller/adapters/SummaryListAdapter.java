/*
 * Copyright (c) 2015. Formula97.NET and HAJIME Fukuna(a.k.a. f97one) (f97one@hotmail.co.jp)
 *
 */

package net.formula97.banquetkanbancontroller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.formula97.banquetkanbancontroller.R;
import net.formula97.banquetkanbancontroller.entities.BanquetSummary;

import java.text.DateFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 宴会サマリリストの表示を行うArrayAdapter。<br />
 * Created by f97one on 2015/11/05.
 */
public class SummaryListAdapter extends ArrayAdapter<BanquetSummary> {

    /**
     * 行表示で使用するViewの保持をするクラス。
     */
    static class ViewHolder {
        @Bind(R.id.summaryName)
        TextView summaryName;
        @Bind(R.id.startsAt)
        TextView startsAt;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private int mLayoutResId;
    private List<BanquetSummary> mSummaryList;

    /**
     * コンストラクタ。
     *
     * @param context 表示するActivityのContext
     * @param resource 行表示に使用するResId
     * @param objects 宴会サマリのList
     */
    public SummaryListAdapter(Context context, int resource, List<BanquetSummary> objects) {
        super(context, resource, objects);

        this.mLayoutResId = resource;
        this.mSummaryList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayoutResId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BanquetSummary summary = mSummaryList.get(position);

        holder.summaryName.setText(summary.getBanquetName());
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext());
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(getContext());
        String startAtDay = android.text.format.DateFormat.format(dateFormat.toString(), summary.getStartAt()).toString();
        String startAtTime = android.text.format.DateFormat.format(timeFormat.toString(), summary.getStartAt()).toString();
        holder.startsAt.setText(startAtDay + " " + startAtTime);

        return convertView;
    }
}
