package com.rain.mytoutiao.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.rain.mytoutiao.R;
import com.rain.mytoutiao.adapter.ChannelAdapter;
import com.rain.mytoutiao.base.AbsBaseActivity;
import com.rain.mytoutiao.db.ChannelDao;
import com.rain.mytoutiao.db.ChannelDao_Table;
import com.rain.mytoutiao.eventbus.IsRefreshTab;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author:rain
 * Date:2018/5/15 16:44
 * Description:
 */
public class NewsChannelActivity extends AbsBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_my_channel)
    RecyclerView recycler_my_channel;
    @BindView(R.id.recycler_hide_channel)
    RecyclerView recycler_hide_channel;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    private ChannelAdapter my_channel_adapter;
    private ChannelAdapter hide_channel_adapter;
    private static final String TAG = "NewsChannelActivity";
    private ItemDragAndSwipeCallback mItemDragAndSwipeCallback;
    private ItemTouchHelper mItemTouchHelper;
    private List<ChannelDao> channelDaos_enable;
    private List<ChannelDao> channelDaos_enable_copy;
    private List<ChannelDao> channelDaos_unable;
    private static final String EDIT = "编辑";
    private static final String OK = "完成";
    private boolean enableEdit = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_news_channel;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initToolBar(toolbar, true, "拖拽排序");
        initData();
        initRecycler();
        initListener();
    }

    private void initData() {
        channelDaos_enable = ChannelDao.queryChannel(true);
        channelDaos_unable = ChannelDao.queryChannel(false);
        channelDaos_enable_copy = channelDaos_enable;
    }

    private void initListener() {
        OnItemDragListener listener = new OnItemDragListener() {
            @Override
            public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e(TAG, "drag start");
                // 可以编辑
                if (!enableEdit) {
                    enableEdit = changEditState(true);
                    startOrEndEditMode(enableEdit);
                    Log.e(TAG, "onItemDragStart: ");
                }
            }

            @Override
            public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                Log.e(TAG, "move from: " + source.getAdapterPosition() + " to: " + target.getAdapterPosition());
            }

            @Override
            public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                Log.e(TAG, "drag end");
                BaseViewHolder holder = ((BaseViewHolder) viewHolder);
            }
        };

        mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(my_channel_adapter);
        mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(recycler_my_channel);
        my_channel_adapter.enableDragItem(mItemTouchHelper);
        my_channel_adapter.setOnItemDragListener(listener);

        hide_channel_adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                channelDaos_unable = adapter.getData();
                ChannelDao hide_channel = channelDaos_unable.get(position);
                hide_channel.is_enable = true;
                if (enableEdit) {
                    hide_channel.enableEdit = true;
                } else {
                    hide_channel.enableEdit = false;
                }
                my_channel_adapter.addData(hide_channel);
                channelDaos_enable.add(hide_channel);
                channelDaos_unable.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });

        my_channel_adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (enableEdit) {
                    channelDaos_enable = adapter.getData();
                    ChannelDao my_channel = channelDaos_enable.get(position);
                    my_channel.is_enable = false;
                    hide_channel_adapter.addData(0, my_channel);
                    channelDaos_unable.add(my_channel);
                    channelDaos_enable.remove(position);
                    adapter.notifyItemRemoved(position);
                }
            }
        });
    }

    private void initRecycler() {
        recycler_my_channel.setLayoutManager(new GridLayoutManager(this, 4));
        recycler_hide_channel.setLayoutManager(new GridLayoutManager(this, 4));
        my_channel_adapter = new ChannelAdapter(R.layout.layout_channel_item, channelDaos_enable);
        hide_channel_adapter = new ChannelAdapter(R.layout.layout_channel_item, channelDaos_unable);

        recycler_my_channel.setAdapter(my_channel_adapter);
        recycler_hide_channel.setAdapter(hide_channel_adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_edit)
    public void onViewClicked() {
        enableEdit = !enableEdit;
        changEditState(enableEdit);
        startOrEndEditMode(enableEdit);
    }

    // 改变text
    private boolean changEditState(boolean enableEdit) {
        if (enableEdit) {
            tvEdit.setText(OK);
        } else {
            tvEdit.setText(EDIT);
        }
        return enableEdit;
    }

    // 改变item布局x图标的显示
    private void startOrEndEditMode(boolean enableEdit) {
        ImageView imageView = null;
        channelDaos_enable = my_channel_adapter.getData();
        for (int i = 0; i < channelDaos_enable.size(); i++) {
            // note:必须制定所属的recyclerview，否则崩溃
            imageView = (ImageView) my_channel_adapter.getViewByPosition(recycler_my_channel,i, R.id.iv_close);
            ChannelDao dao = channelDaos_enable.get(i);
            if (enableEdit) {
                assert imageView != null;
                imageView.setVisibility(View.VISIBLE);
                dao.enableEdit = true;
            } else {
                assert imageView != null;
                imageView.setVisibility(View.GONE);
                dao.enableEdit = false;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
    }
    // 保存数据到数据库，同时通知HomeTabView重新加载tab和fragment
    @SuppressLint("CheckResult")
    private void saveData() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                e.onNext(!compare(channelDaos_enable_copy,my_channel_adapter.getData()));
            }
        })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        // 前后集合不相等
                        if (!aBoolean) {
                            SQLite.delete(ChannelDao.class).execute();
                            ChannelDao.addChannels(my_channel_adapter.getData());
                            ChannelDao.addChannels(hide_channel_adapter.getData());
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean refresh) throws Exception {
                        Log.e(TAG, "accept: "+refresh);
                        EventBus.getDefault().post(new IsRefreshTab(refresh));
                    }
                });
    }

    // 比较两个list集合是否相等
    // 注意T 需要实现相应的equals方法
    public synchronized <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
//        Collections.sort(a);
//        Collections.sort(b);
        if (a.size() != b.size())
            return false;
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
}
