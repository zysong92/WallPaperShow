package com.szy.wallpapershow;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class PullToRefreshView extends LinearLayout {

    private enum REFRESHSTATUS {STATUS_PULL_TOREFRESH, STATUS_RELEASE_TO_REFRESH, STATUS_REFRESHING, STATUS_REFRESH_FINISHED}

    ;

    //下拉头部回滚的速度
    public static final int SCROLL_SPEED = -20;

    //一分钟的毫秒值
    public static final int ONE_MINUTE = 60 * 1000;

    //一小时的毫秒值
    public static final int ONE_HOUR = 60 * 60 * 1000;

    //一天的毫秒值
    public static final int ONE_DAY = 24 * ONE_HOUR;

    public static final int ONE_MONTH = 30 * ONE_DAY;

    public static final int one_year = 12 * ONE_MONTH;

    private static final String UPDATE_AT = "latest_updated_at";

    private SharedPreferences sharedPreferences;

    private View headerView;

    private ListView listView;

    private ProgressBar progressBar;

    private ImageView ivArrow;

    private TextView tvDescription;

    private TextView tvLastUpdateTime;

    /**
     * 为了防止不同界面的下拉刷新在上次更新时间上互相有冲突，使用id来做区分
     */
    private int mId = -1;

    /**
     * 下拉头的高度
     */
    private int hideHeaderHeight;

    /**
     * 当前处理什么状态，可选值有STATUS_PULL_TO_REFRESH, STATUS_RELEASE_TO_REFRESH,
     * STATUS_REFRESHING 和 STATUS_REFRESH_FINISHED
     */
    private REFRESHSTATUS currentStatus = REFRESHSTATUS.STATUS_REFRESH_FINISHED;;

    /**
     * 记录上一次的状态是什么，避免进行重复操作
     */
    private REFRESHSTATUS lastStatus = currentStatus;

    /**
     * 手指按下时候的屏幕纵坐标
     */
    private float yDown;

    /**
     * 在被判定为滚动之前用户手指可以移动的最大值
     */
    private int touchSlop;

    /**
     * 是否已经加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private  boolean loadOnce;

    private boolean allowPull;
    public PullToRefreshView(Context context) {
        super(context);
        init(context);
    }

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullToRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        headerView = LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_view, null, true
        );
        progressBar = (ProgressBar) headerView.findViewById(R.id.progress_bar);
        ivArrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        tvDescription = (TextView) headerView.findViewById(R.id.tv_description);
        tvLastUpdateTime = (TextView) headerView.findViewById(R.id.tv_latestTime);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        refreshUpdateAtValue();
        setOrientation(VERTICAL);
        addView(headerView, 0);
    }

    private void refreshUpdateAtValue(){

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce){

            hideHeaderHeight = -headerView.getHeight();

        }
    }
}
