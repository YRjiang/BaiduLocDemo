package com.baidu.location.demo;


import com.baidu.baidulocationdemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 常见问题
 */
public class QuestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quest_activity);
        Log.d("Mytest", "onCreate: Mytest - GPS c");
    }
}
