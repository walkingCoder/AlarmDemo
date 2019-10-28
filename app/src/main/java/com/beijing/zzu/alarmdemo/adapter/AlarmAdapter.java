package com.beijing.zzu.alarmdemo.adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beijing.zzu.alarmdemo.manager.DialogManager;
import com.beijing.zzu.alarmdemo.R;
import com.beijing.zzu.alarmdemo.bean.Alarm;
import com.beijing.zzu.alarmdemo.manager.DialogManager2;
import com.beijing.zzu.alarmdemo.widget.CommonDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiayk
 * @date 2019/10/27
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {

    private Context mContext;
    private List<Alarm> alarmList = new ArrayList<>();

    private SparseArray<CountDownTimer> countDownCounters = new SparseArray<>();

    public AlarmAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.alarm_item, null);
        AlarmHolder holder = new AlarmHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmHolder alarmHolder, int i) {
        CountDownTimer countDownTimer = countDownCounters.get(alarmHolder.alarmTime.hashCode());
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        final Alarm alarm = alarmList.get(i);
        alarmHolder.alarmName.setText(alarm.getName());
        alarmHolder.alarmTime.setText(String.valueOf(alarm.getInterval()));
        if (alarm.getInterval() > 0) {
            if (countDownTimer == null) {
                countDownTimer = new CountDownTimer(alarm.getInterval() * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        alarmHolder.alarmTime.setText(String.valueOf(Math.round((double) millisUntilFinished / 1000)));
                    }

                    @Override
                    public void onFinish() {
                        alarmHolder.alarmTime.setText("0");
                        cancel();
                        CommonDialog dialog = new CommonDialog(mContext);
                        dialog.setPriority(alarm.getLevel()).setTitle(alarm.getName());
                        DialogManager.getInstance().canShow(dialog);
                    }
                }.start();
            }else {
                countDownTimer.start();
            }
            countDownCounters.put(alarmHolder.alarmTime.hashCode(), countDownTimer);
        }

    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    public void setData(List<Alarm> alarms) {
        this.alarmList = alarms;
        notifyDataSetChanged();
    }

    static class AlarmHolder extends RecyclerView.ViewHolder {

        TextView alarmName, alarmTime;

        public AlarmHolder(@NonNull View itemView) {
            super(itemView);
            alarmName = itemView.findViewById(R.id.alarm_name);
            alarmTime = itemView.findViewById(R.id.alarm_time);
        }
    }

    /**
     * 清空当前 CountTimeDown 资源
     */
    public void cancelAllTimers() {
        if (countDownCounters == null) {
            return;
        }
        for (int i = 0, length = countDownCounters.size(); i < length; i++) {
            CountDownTimer cdt = countDownCounters.get(countDownCounters.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }
}