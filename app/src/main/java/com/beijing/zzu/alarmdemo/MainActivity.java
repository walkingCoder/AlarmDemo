package com.beijing.zzu.alarmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.beijing.zzu.alarmdemo.adapter.AlarmAdapter;
import com.beijing.zzu.alarmdemo.bean.Alarm;
import com.beijing.zzu.alarmdemo.utils.GsonUtil;
import com.beijing.zzu.alarmdemo.utils.JsonFileReader;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private static final String alarmPath = "alarms.json";

    private RecyclerView alarmRecyclerView;
    private AlarmAdapter alarmAdapter;
    Disposable observable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        observable = Observable.just(alarmPath).map(new Function<String, List<Alarm>>() {
            @Override
            public List<Alarm> apply(String s) throws Exception {
                String alarmJson = JsonFileReader.getJson(MainActivity.this, "alarms.json");
                return GsonUtil.GsonToList(alarmJson, Alarm.class);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Alarm>>() {
                    @Override
                    public void accept(List<Alarm> alarms) throws Exception {
                        alarmAdapter.setData(alarms);
                    }
                });

    }

    private void initView() {

        alarmRecyclerView = findViewById(R.id.alarm_recy);
        alarmRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        alarmAdapter = new AlarmAdapter(this);
        alarmRecyclerView.setAdapter(alarmAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmAdapter != null) {
            alarmAdapter.cancelAllTimers();
        }

        if (observable != null){
            observable.dispose();
            observable = null;
        }
    }
}
