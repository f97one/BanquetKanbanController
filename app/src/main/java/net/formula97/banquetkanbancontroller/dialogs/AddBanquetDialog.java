/*
 * Copyright (c) 2015. Formula97.NET and HAJIME Fukuna(a.k.a. f97one) (f97one@hotmail.co.jp)
 *
 */

package net.formula97.banquetkanbancontroller.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import net.formula97.banquetkanbancontroller.R;
import net.formula97.banquetkanbancontroller.entities.BanquetSummary;
import net.formula97.banquetkanbancontroller.models.BanquetSummaryModel;

import java.util.Calendar;
import java.util.EventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 宴会サマリを追加、編集するときに使用するDialogFragment。<br />
 * Created by f97one on 2015/11/07.
 */
public class AddBanquetDialog extends DialogFragment {

    /**
     * このFragmentを探す時のタグ
     */
    public static final String FRAGMENT_TAG = AddBanquetDialog.class.getName() + ".FRAGMENT_TAG";

    /**
     * このFragmentから送出するリクエストコード。
     */
    public static final int REQUEST_CODE = 0x1001;

    private static final String sArgsBanquetSummary = "sArgsBanquetSummary";

    private BanquetSummary mSummary;
    private OnBanquetSummaryCreatedListener mListener;

    @Bind(R.id.banquetNameBox)
    EditText banquetNameBox;
    @Bind(R.id.banquetStartDate)
    DatePicker banquetStartDate;
    @Bind(R.id.banquetStartTime)
    TimePicker banquetStartTime;

    /**
     * 宴会サマリを追加した時に呼び出されるコールバック。
     */
    public interface OnBanquetSummaryCreatedListener extends EventListener {
        /**
         * 宴会サマリを追加した時に呼び出される。
         *
         * @param summary 追加された宴会サマリのインスタンス
         */
        void onSummaryCreated(BanquetSummary summary);
    }

    public AddBanquetDialog() {
        // keep empty
    }

    /**
     * 新規追加モードでダイアログを生成する。
     *
     * @return 空の宴会サマリ追加ダイアログ
     */
    public static AddBanquetDialog getInstance() {
        return getInstance(new BanquetSummary());
    }

    /**
     * 編集モードでダイアログを生成する。
     *
     * @param summary 編集対象の宴会サマリのインスタンス
     * @return 空の宴会サマリ追加ダイアログ
     */
    public static AddBanquetDialog getInstance(BanquetSummary summary) {
        AddBanquetDialog dialog = new AddBanquetDialog();

        if (summary == null) {
            throw new IllegalArgumentException("BanquetSummary must not be null.");
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(sArgsBanquetSummary, summary);
        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mListener = (OnBanquetSummaryCreatedListener) (getTargetFragment() == null ? activity : getTargetFragment());
        } catch (ClassCastException e) {
            e.printStackTrace();
            mListener = null;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSummary = (BanquetSummary) getArguments().getSerializable(sArgsBanquetSummary);
    }

    @SuppressWarnings("deprecated")
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_add_banquet, null);

        ButterKnife.bind(this, view);

        // 名称
        if (!TextUtils.isEmpty(mSummary.getBanquetName())) {
            banquetNameBox.setText(mSummary.getBanquetName());
        }
        // 開始日／開始時刻は、エンティティの設定値がnullのときは現在日時に設定
        Calendar showCal;
        if (mSummary.getStartAt() == null) {
            showCal = Calendar.getInstance();
        } else {
            showCal = mSummary.getStartAt();
        }
        banquetStartDate.setMinDate(Calendar.getInstance().getTimeInMillis());
        banquetStartDate.updateDate(showCal.get(Calendar.YEAR), showCal.get(Calendar.MONTH), showCal.get(Calendar.DAY_OF_MONTH));

        int hour = showCal.get(Calendar.HOUR_OF_DAY);
        int min = showCal.get(Calendar.MINUTE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            // TimePicker#setCurrentHour(Integer), TimePicker#setCurrentMinute(Integer)がAPI 23以降でdeprecatedになる対策
            banquetStartTime.setHour(hour);
            banquetStartTime.setMinute(min);
        } else {
            banquetStartTime.setCurrentHour(hour);
            banquetStartTime.setCurrentMinute(min);
        }
        // 端末が24時間制表示になっているかの判断
        String format1224 = Settings.System.getString(getActivity().getContentResolver(), Settings.System.TIME_12_24);
        boolean is24Format = format1224.equals("24");
        banquetStartTime.setIs24HourView(is24Format);

        String title = mSummary.getBanquetId() == null ? getString(R.string.add_banquet) : getString(R.string.edit_banquet);

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setView(view)
                .setTitle(title)
                .setPositiveButton(android.R.string.ok, mPositiveClicked);
        
        return dialog.create();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    /**
     * ダイアログのOKボタンを押したときの処理。
     */
    @SuppressWarnings("deprecated")
    private DialogInterface.OnClickListener mPositiveClicked = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // 設定されている日時を取得
            int year = banquetStartDate.getYear();
            int month = banquetStartDate.getMonth();
            int dom = banquetStartDate.getDayOfMonth();
            int hour;
            int min;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                hour = banquetStartTime.getHour();
                min = banquetStartTime.getMinute();
            } else {
                hour = banquetStartTime.getCurrentHour();
                min = banquetStartTime.getCurrentMinute();
            }
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dom, hour, min, 0);
            if (mSummary.getBanquetId() == null) {
                BanquetSummaryModel model = new BanquetSummaryModel(getActivity().getApplicationContext());
                mSummary.setBanquetId(model.createBanquetId());
            }
            mSummary.setBanquetName(banquetNameBox.getText().toString());
            mSummary.setStartAt(cal);

            if (mListener != null) {
                mListener.onSummaryCreated(mSummary);
            }
        }
    };
}
