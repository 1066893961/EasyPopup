package com.zyyoona7.easypopup.easypop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zyyoona7.easypopup.R;
import com.zyyoona7.lib.EasyPopup;
import com.zyyoona7.lib.HorizontalGravity;
import com.zyyoona7.lib.VerticalGravity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewActivity";

    private RecyclerPopAdapter mPopAdapter;
    private RecyclerView mRecyclerView;
    private EasyPopup mRvPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_pop);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        mPopAdapter = new RecyclerPopAdapter();
        mPopAdapter.setNewData(list);
        mRecyclerView.setAdapter(mPopAdapter);
        initPop();
        initEvents();
    }

    private void initPop() {
        mRvPop = new EasyPopup(this)
                .setContentView(R.layout.layout_right_pop)
                .setAnimationStyle(R.style.QQPopAnim)
//                .setHeight(700)
//                .setWidth(600)
                .setFocusAndOutsideEnable(true)
//                .setBackgroundDimEnable(true)
//                .setDimValue(0.5f)
//                .setDimColor(Color.RED)
//                .setDimView(mTitleBar)
                .createPopup();

        //回调在所有Show方法之后updateLocation方法之前执行
        //只有调用showAtAnchorView方法才会执行updateLocation方法
        mRvPop.setOnAttachedWindowListener(new EasyPopup.OnAttachedWindowListener() {
            @Override
            public void onAttachedWindow(int width, int height, EasyPopup easyPop) {
                Log.i(TAG, "onAttachedWindow: width=" + width);
                int offsetX = (getResources().getDisplayMetrics().widthPixels - width) / 2
                        - getResources().getDimensionPixelSize(R.dimen.dp_30);
                //重新设置偏移量
                easyPop.setOffsetX(-offsetX);
            }
        });
    }

    private void initEvents() {
        mPopAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                int[] locations = new int[2];
                view.getLocationOnScreen(locations);
                Log.i(TAG, Arrays.toString(locations));
                if (locations[1] > getResources().getDisplayMetrics().heightPixels / 2) {
                    mRvPop.showAtAnchorView(view, VerticalGravity.ABOVE, HorizontalGravity.LEFT);
                } else {
                    mRvPop.showAtAnchorView(view, VerticalGravity.BELOW, HorizontalGravity.LEFT);
                }
            }
        });
    }
}
