package com.tgcity.pagelayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.tgcity.pagelayout.widget.PagerConfig;
import com.tgcity.pagelayout.widget.PagerGridLayoutManager;
import com.tgcity.pagelayout.widget.PagerGridSnapHelper;

public class MainActivity extends AppCompatActivity implements PagerGridLayoutManager.PageListener {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private PagerGridLayoutManager mLayoutManager;
    // 总页数
    private TextView mPageTotal;
    // 当前页数
    private TextView mPageCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPageTotal = (TextView) findViewById(R.id.page_total);
        mPageCurrent = (TextView) findViewById(R.id.page_current);

        mLayoutManager = new PagerGridLayoutManager(1, 3, PagerGridLayoutManager.HORIZONTAL);

        // 系统带的 RecyclerView，无需自定义
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // 水平分页布局管理器
        mLayoutManager.setPageListener(this);
        // 设置页面变化监听器
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 设置滚动辅助工具
        PagerGridSnapHelper pageSnapHelper = new PagerGridSnapHelper();
        pageSnapHelper.attachToRecyclerView(mRecyclerView);

        // 如果需要查看调试日志可以设置为true，一般情况忽略即可
        PagerConfig.setShowLog(true);

        // 使用原生的 Adapter 即可
        mAdapter = new MyAdapter();
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onPageSizeChanged(int pageSize) {
        mPageTotal.setText("共" + pageSize + "页");
    }

    @Override
    public void onPageSelect(int pageIndex) {
        mPageCurrent.setText("第" + (pageIndex + 1) + "页");
    }

    public void smoothPrePage(View view) {
        mLayoutManager.smoothPrePage();
    }

    public void smoothNextPage(View view) {
        mLayoutManager.smoothNextPage();
    }

    public void firstPage(View view) {
        mRecyclerView.smoothScrollToPosition(0);
    }

    public void lastPage(View view) {
        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
    }

}
