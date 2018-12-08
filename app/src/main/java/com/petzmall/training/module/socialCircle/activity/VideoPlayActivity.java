package com.petzmall.training.module.socialCircle.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVidSts;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.bumptech.glide.Glide;
import com.github.baseclass.rx.MySubscriber;
import com.github.customview.MyImageView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.petzmall.training.PopupWindow.CommonPopupWindow;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseActivity;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.module.player.activity.AliyunPlayerSkinActivity;
import com.petzmall.training.module.player.constants.PlayParameter;
import com.petzmall.training.module.player.playlist.AlivcVideoInfo;
import com.petzmall.training.module.player.utils.ScreenUtils;
import com.petzmall.training.module.player.utils.VidStsUtil;
import com.petzmall.training.module.player.view.choice.AlivcShowMoreDialog;
import com.petzmall.training.module.player.view.control.ControlView;
import com.petzmall.training.module.player.view.more.AliyunShowMoreValue;
import com.petzmall.training.module.player.view.more.ShowMoreView;
import com.petzmall.training.module.player.view.more.SpeedValue;
import com.petzmall.training.module.player.view.tipsview.ErrorInfo;
import com.petzmall.training.module.player.widget.AliyunScreenMode;
import com.petzmall.training.module.player.widget.AliyunVodPlayerView;
import com.petzmall.training.module.socialCircle.adapter.CommentSecondAdapter;
import com.petzmall.training.module.socialCircle.adapter.MyPagerAdapter;
import com.petzmall.training.module.socialCircle.bean.SecondCommentBean;
import com.petzmall.training.module.socialCircle.event.PopwindowEvent;
import com.petzmall.training.module.socialCircle.fragment.CommentsFragment;
import com.petzmall.training.module.socialCircle.fragment.VideoIntroduceFragment;
import com.petzmall.training.module.socialCircle.network.ApiRequest;
import com.petzmall.training.tools.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class VideoPlayActivity extends BaseActivity {


    @BindView(R.id.tv_player)
    TextView mTvPlayer;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    private static final String[] CHANNELS = new String[]{"简介", "大纲", "讨论"};
    @BindView(R.id.comment_linear)
    LinearLayout commentLinear;

   //播放器相关
    @BindView(R.id.video_view)
    AliyunVodPlayerView mAliyunVodPlayerView; //播放器
    private static final String DEFAULT_URL = "http://player.alicdn.com/video/aliyunmedia.mp4";
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss.SS");
    private List<String> logStrs = new ArrayList<>();
    private ErrorInfo currentError = ErrorInfo.Normal;
    private ArrayList<AlivcVideoInfo.Video> alivcVideoInfos = new ArrayList<AlivcVideoInfo.Video>();
    private TextView tvLogs;
    private AlivcShowMoreDialog showMoreDialog;
    private AliyunScreenMode currentScreenMode = AliyunScreenMode.Small;
    List<AliyunDownloadMediaInfo> aliyunDownloadMediaInfoList;
    /**
     * get StsToken stats
     */
    private boolean inRequest;



    //    @BindView(R.id.button)
//    Button button;
//    @BindView(R.id.v_bottom_line)
//    View vBottomLine;
    private List<String> mDataList = Arrays.asList(CHANNELS);

    private List<Fragment> fragments = new ArrayList<>();
    private MyPagerAdapter adapter;
    private CollapsingToolbarLayoutState state;


    VideoIntroduceFragment videoIntroduceFragment;
    CommentsFragment commentsFragment;

    CommonPopupWindow popupWindow;
    SmartRefreshLayout refreshLayout;
    CommentSecondAdapter commentSecondAdapter;
    private List<SecondCommentBean.DataBean.FirstVideoReplyVosBean> firstVideoReplyVos = new ArrayList<>();
    SecondCommentBean.DataBean dataBean;
    //popwindow 里面的一级评论人
    CircleImageView circleImageView;
    TextView commentUserName, commentTime, commentContent;
    boolean addHeader = false;//是否添加了头部
    XRecyclerView recyclerView;

    int i0, i1;
    View appBarChildAt;
    AppBarLayout.LayoutParams appBarParams;


    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.white)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_video_detail;
    }

    @Override
    protected void initRxBus() {
        super.initRxBus();
        getRxBusEvent(PopwindowEvent.class, new MySubscriber<PopwindowEvent>() {
            @Override
            public void onMyNext(PopwindowEvent event) {
                showPopListView();
                Log.e("initRxBus", "initRxBus------>0");
            }
        });
    }

    @Override
    protected void initView() {
        initAppBar();
        initAliyunPlayerView();
        commentSecondAdapter = new CommentSecondAdapter(mContext, 0);
//        //设置图片
//        Glide.with(mContext)
//                .load("https://p.pstatp.com/weili/bl/55311344760656633.jpg")
//                .into(mIvVideoPreview);

        //设置TabLayout点击事件
        fragments.add(new VideoIntroduceFragment());
        fragments.add(new VideoIntroduceFragment());
        fragments.add(new CommentsFragment());
        initMagicIndicator();

        //控制AppBarLayout折叠
        i0 = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
        i1 = AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
        appBarChildAt = mAppBar.getChildAt(0);
        appBarParams = (AppBarLayout.LayoutParams) appBarChildAt.getLayoutParams();


    }



    private void initAliyunPlayerView() {

        //保持屏幕敞亮
        mAliyunVodPlayerView.setKeepScreenOn(true);
        PlayParameter.PLAY_PARAM_URL = DEFAULT_URL;
        String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/test_save_cache";
        mAliyunVodPlayerView.setPlayingCache(false, sdDir, 60 * 60 /*时长, s */, 300 /*大小，MB*/);
        mAliyunVodPlayerView.setTheme(AliyunVodPlayerView.Theme.Blue);
        mAliyunVodPlayerView.setCirclePlay(true);
        mAliyunVodPlayerView.setAutoPlay(true);

        mAliyunVodPlayerView.setOnPreparedListener(new MyPrepareListener(this));
        mAliyunVodPlayerView.setNetConnectedListener(new MyNetConnectedListener(this));
        mAliyunVodPlayerView.setOnCompletionListener(new MyCompletionListener(this));
        mAliyunVodPlayerView.setOnFirstFrameStartListener(new MyFrameInfoListener(this));
        mAliyunVodPlayerView.setOnChangeQualityListener(new MyChangeQualityListener(this));
        mAliyunVodPlayerView.setOnStoppedListener(new MyStoppedListener(this));
        mAliyunVodPlayerView.setmOnPlayerViewClickListener(new MyPlayViewClickListener());
        mAliyunVodPlayerView.setOrientationChangeListener(new MyOrientationChangeListener(this));
        mAliyunVodPlayerView.setOnUrlTimeExpiredListener(new MyOnUrlTimeExpiredListener(this));
        mAliyunVodPlayerView.setOnShowMoreClickListener(new MyShowMoreClickLisener(this));
        mAliyunVodPlayerView.setOnPlayStateBtnClickListener(new MyPlayStateBtnClickListener(this));
        mAliyunVodPlayerView.setOnSeekCompleteListener(new MySeekCompleteListener(this));
        mAliyunVodPlayerView.setOnSeekStartListener(new MySeekStartListener(this));
        mAliyunVodPlayerView.enableNativeLog();

        if ("localSource".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            AliyunLocalSource.AliyunLocalSourceBuilder alsb = new AliyunLocalSource.AliyunLocalSourceBuilder();
            alsb.setSource(PlayParameter.PLAY_PARAM_URL);
            Uri uri = Uri.parse(PlayParameter.PLAY_PARAM_URL);
            if ("rtmp".equals(uri.getScheme())) {
                alsb.setTitle("");
            }
            AliyunLocalSource localSource = alsb.build();
            if (mAliyunVodPlayerView != null) {
                mAliyunVodPlayerView.setLocalSource(localSource);
            }

        } else if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
            if (!inRequest) {
                AliyunVidSts vidSts = new AliyunVidSts();
                vidSts.setVid(PlayParameter.PLAY_PARAM_VID);
                vidSts.setAcId(PlayParameter.PLAY_PARAM_AK_ID);
                vidSts.setAkSceret(PlayParameter.PLAY_PARAM_AK_SECRE);
                vidSts.setSecurityToken(PlayParameter.PLAY_PARAM_SCU_TOKEN);
                if (mAliyunVodPlayerView != null) {
                    mAliyunVodPlayerView.setVidSts(vidSts);
                }
//                downloadManager.prepareDownloadMedia(vidSts);
            }
        }


    }


    //seekstart
    private static class MySeekStartListener implements AliyunVodPlayerView.OnSeekStartListener {
        WeakReference<VideoPlayActivity> weakReference;

        MySeekStartListener(VideoPlayActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSeekStart() {
            VideoPlayActivity activity = weakReference.get();
            if (activity != null) {
                activity.onSeekStart();
            }
        }
    }

    private void onSeekStart() {
        tvLogs.append(format.format(new Date()) + getString(R.string.log_seek_start) + "\n");
    }



    //seek完成
    private static class MySeekCompleteListener implements IAliyunVodPlayer.OnSeekCompleteListener {
        WeakReference<VideoPlayActivity> weakReference;

        MySeekCompleteListener(VideoPlayActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onSeekComplete() {
            VideoPlayActivity activity = weakReference.get();
            if (activity != null) {
                activity.onSeekComplete();
            }
        }
    }
    private void onSeekComplete() {
        tvLogs.append(format.format(new Date()) + getString(R.string.log_seek_completed) + "\n");
    }


    private static class MyPlayStateBtnClickListener implements AliyunVodPlayerView.OnPlayStateBtnClickListener {
        WeakReference<VideoPlayActivity> weakReference;

        MyPlayStateBtnClickListener(VideoPlayActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onPlayBtnClick(IAliyunVodPlayer.PlayerState playerState) {
            VideoPlayActivity activity = weakReference.get();
            if (activity != null) {
                activity.onPlayStateSwitch(playerState);
            }

        }
    }

    /**
     * 播放状态切换
     *
     * @param playerState
     */
    private void onPlayStateSwitch(IAliyunVodPlayer.PlayerState playerState) {
        if (playerState == IAliyunVodPlayer.PlayerState.Started) {
            tvLogs.append(format.format(new Date()) + " 暂停 \n");
        } else if (playerState == IAliyunVodPlayer.PlayerState.Paused) {
            tvLogs.append(format.format(new Date()) + " 开始 \n");
        }

    }



    private static class MyShowMoreClickLisener implements ControlView.OnShowMoreClickListener {
        WeakReference<VideoPlayActivity> weakReference;

        MyShowMoreClickLisener(VideoPlayActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void showMore() {
            VideoPlayActivity activity = weakReference.get();
            activity.showMore(activity);
        }
    }

    private void showMore(final VideoPlayActivity activity) {
        showMoreDialog = new AlivcShowMoreDialog(activity);
        AliyunShowMoreValue moreValue = new AliyunShowMoreValue();
        moreValue.setSpeed(mAliyunVodPlayerView.getCurrentSpeed());
        moreValue.setVolume(mAliyunVodPlayerView.getCurrentVolume());
        moreValue.setScreenBrightness(mAliyunVodPlayerView.getCurrentScreenBrigtness());

        ShowMoreView showMoreView = new ShowMoreView(activity, moreValue);
        showMoreDialog.setContentView(showMoreView);
        showMoreDialog.show();
        showMoreView.setOnDownloadButtonClickListener(new ShowMoreView.OnDownloadButtonClickListener() {
            @Override
            public void onDownloadClick() {
                // 点击下载
                showMoreDialog.dismiss();
                if (!"vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
                    Toast.makeText(activity, "Url类型不支持下载", Toast.LENGTH_SHORT).show();
                    return;
                }
//                showAddDownloadView(AliyunScreenMode.Full);
            }
        });

        showMoreView.setOnScreenCastButtonClickListener(new ShowMoreView.OnScreenCastButtonClickListener() {
            @Override
            public void onScreenCastClick() {
                Toast.makeText(VideoPlayActivity.this, "功能开发中, 敬请期待...", Toast.LENGTH_SHORT).show();
            }
        });

        showMoreView.setOnBarrageButtonClickListener(new ShowMoreView.OnBarrageButtonClickListener() {
            @Override
            public void onBarrageClick() {
                Toast.makeText(VideoPlayActivity.this, "功能开发中, 敬请期待...", Toast.LENGTH_SHORT).show();
            }
        });

        showMoreView.setOnSpeedCheckedChangedListener(new ShowMoreView.OnSpeedCheckedChangedListener() {
            @Override
            public void onSpeedChanged(RadioGroup group, int checkedId) {
                // 点击速度切换
                if (checkedId == R.id.rb_speed_normal) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.One);
                } else if (checkedId == R.id.rb_speed_onequartern) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneQuartern);
                } else if (checkedId == R.id.rb_speed_onehalf) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.OneHalf);
                } else if (checkedId == R.id.rb_speed_twice) {
                    mAliyunVodPlayerView.changeSpeed(SpeedValue.Twice);
                }

            }
        });

        // 亮度seek
        showMoreView.setOnLightSeekChangeListener(new ShowMoreView.OnLightSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                mAliyunVodPlayerView.setCurrentScreenBrigtness(progress);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

        showMoreView.setOnVoiceSeekChangeListener(new ShowMoreView.OnVoiceSeekChangeListener() {
            @Override
            public void onStart(SeekBar seekBar) {

            }

            @Override
            public void onProgress(SeekBar seekBar, int progress, boolean fromUser) {
                mAliyunVodPlayerView.setCurrentVolume(progress);
            }

            @Override
            public void onStop(SeekBar seekBar) {

            }
        });

    }


    private static class MyOnUrlTimeExpiredListener implements IAliyunVodPlayer.OnUrlTimeExpiredListener {
        WeakReference<VideoPlayActivity> weakReference;

        public MyOnUrlTimeExpiredListener(VideoPlayActivity activity) {
            weakReference = new WeakReference<VideoPlayActivity>(activity);
        }

        @Override
        public void onUrlTimeExpired(String s, String s1) {
            VideoPlayActivity activity = weakReference.get();
            activity.onUrlTimeExpired(s, s1);
        }
    }

    private void onUrlTimeExpired(String oldVid, String oldQuality) {
        //requestVidSts();
        AliyunVidSts vidSts = VidStsUtil.getVidSts(oldVid);
        PlayParameter.PLAY_PARAM_VID = vidSts.getVid();
        PlayParameter.PLAY_PARAM_AK_SECRE = vidSts.getAkSceret();
        PlayParameter.PLAY_PARAM_AK_ID = vidSts.getAcId();
        PlayParameter.PLAY_PARAM_SCU_TOKEN = vidSts.getSecurityToken();

    }

    private static class MyOrientationChangeListener implements AliyunVodPlayerView.OnOrientationChangeListener {

        private final WeakReference<VideoPlayActivity> weakReference;

        public MyOrientationChangeListener(VideoPlayActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void orientationChange(boolean from, AliyunScreenMode currentMode) {
            VideoPlayActivity activity = weakReference.get();
            activity.hideDownloadDialog(from, currentMode);
            activity.hideShowMoreDialog(from, currentMode);
        }
    }

    private void hideShowMoreDialog(boolean from, AliyunScreenMode currentMode) {
        if (showMoreDialog != null) {
            if (currentMode == AliyunScreenMode.Small) {
                showMoreDialog.dismiss();
                currentScreenMode = currentMode;
            }
        }
    }

    private void hideDownloadDialog(boolean from, AliyunScreenMode currentMode) {

//        if (downloadDialog != null) {
            if (currentScreenMode != currentMode) {
//                downloadDialog.dismiss();
                currentScreenMode = currentMode;
            }
//        }
    }



    private class MyPlayViewClickListener implements AliyunVodPlayerView.OnPlayerViewClickListener {
        @Override
        public void onClick(AliyunScreenMode screenMode, AliyunVodPlayerView.PlayViewType viewType) {
            // 如果当前的Type是Download, 就显示Download对话框
            if (viewType == AliyunVodPlayerView.PlayViewType.Download) {
//                showAddDownloadView(screenMode);
            }
        }
    }
    private static class MyStoppedListener implements IAliyunVodPlayer.OnStoppedListener {

        private WeakReference<VideoPlayActivity> activityWeakReference;

        public MyStoppedListener(VideoPlayActivity skinActivity) {
            activityWeakReference = new WeakReference<VideoPlayActivity>(skinActivity);
        }

        @Override
        public void onStopped() {

            VideoPlayActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onStopped();
            }
        }
    }

    private void onStopped() {
        Toast.makeText(VideoPlayActivity.this.getApplicationContext(), R.string.log_play_stopped,
                Toast.LENGTH_SHORT).show();
    }


    private static class MyPrepareListener implements IAliyunVodPlayer.OnPreparedListener {

        private WeakReference<VideoPlayActivity> activityWeakReference;

        public MyPrepareListener(VideoPlayActivity skinActivity) {
            activityWeakReference = new WeakReference<VideoPlayActivity>(skinActivity);
        }

        @Override
        public void onPrepared() {
            VideoPlayActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onPrepared();
            }
        }
    }
    private void onPrepared() {
        logStrs.add(format.format(new Date()) + getString(R.string.log_prepare_success));

//        for (String log : logStrs) {
//            tvLogs.append(log + "\n");
//        }
        Toast.makeText(VideoPlayActivity.this.getApplicationContext(), R.string.toast_prepare_success,
                Toast.LENGTH_SHORT).show();
    }


    /**
     * 判断是否有网络的监听
     */
    private class MyNetConnectedListener implements AliyunVodPlayerView.NetConnectedListener {
        WeakReference<VideoPlayActivity> weakReference;

        public MyNetConnectedListener(VideoPlayActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void onReNetConnected(boolean isReconnect) {
            VideoPlayActivity activity = weakReference.get();
            if (activity != null) {
                activity.onReNetConnected(isReconnect);
            }
        }

        @Override
        public void onNetUnConnected() {
            VideoPlayActivity activity = weakReference.get();
            if (activity != null) {
                activity.onNetUnConnected();
            }
        }
    }


    private void onNetUnConnected() {
        currentError = ErrorInfo.UnConnectInternet;
        if (aliyunDownloadMediaInfoList != null && aliyunDownloadMediaInfoList.size() > 0) {
//            downloadManager.stopDownloadMedias(aliyunDownloadMediaInfoList);
        }
    }

    private void onReNetConnected(boolean isReconnect) {
        currentError = ErrorInfo.Normal;
        if (isReconnect) {
            if (aliyunDownloadMediaInfoList != null && aliyunDownloadMediaInfoList.size() > 0) {
                int unCompleteDownload = 0;
                for (AliyunDownloadMediaInfo info : aliyunDownloadMediaInfoList) {
                    if (info.getStatus() == AliyunDownloadMediaInfo.Status.Stop) {
                        unCompleteDownload++;
                    }
                }

                if (unCompleteDownload > 0) {
                    Toast.makeText(this, "网络恢复, 请手动开启下载任务...", Toast.LENGTH_SHORT).show();
                }
            }
            // 如果当前播放列表为空, 网络重连后需要重新请求sts和播放列表, 其他情况不需要
            if (alivcVideoInfos != null && alivcVideoInfos.size() == 0) {
                VidStsUtil.getVidSts(PlayParameter.PLAY_PARAM_VID, new VideoPlayActivity.MyStsListener(this));
            }
        }
    }

    private static class MyStsListener implements VidStsUtil.OnStsResultListener {

        private WeakReference<VideoPlayActivity> weakctivity;

        MyStsListener(VideoPlayActivity act) {
            weakctivity = new WeakReference<VideoPlayActivity>(act);
        }

        @Override
        public void onSuccess(String vid, String akid, String akSecret, String token) {
            VideoPlayActivity activity = weakctivity.get();
            if (activity != null) {
                activity.onStsSuccess(vid, akid, akSecret, token);
            }
        }

        @Override
        public void onFail() {
            VideoPlayActivity activity = weakctivity.get();
            if (activity != null) {
                activity.onStsFail();
            }
        }
    }

    private void onStsFail() {

        Toast.makeText(getApplicationContext(), R.string.request_vidsts_fail, Toast.LENGTH_LONG).show();
        inRequest = false;
        //finish();
    }

    private void onStsSuccess(String mVid, String akid, String akSecret, String token) {
        PlayParameter.PLAY_PARAM_VID = mVid;
        PlayParameter.PLAY_PARAM_AK_ID = akid;
        PlayParameter.PLAY_PARAM_AK_SECRE = akSecret;
        PlayParameter.PLAY_PARAM_SCU_TOKEN = token;

        inRequest = false;

        // 视频列表数据为0时, 加载列表
        if (alivcVideoInfos != null && alivcVideoInfos.size() == 0) {
            alivcVideoInfos.clear();
//            loadPlayList();
        }
    }


    private static class MyCompletionListener implements IAliyunVodPlayer.OnCompletionListener {

        private WeakReference<VideoPlayActivity> activityWeakReference;

        public MyCompletionListener(VideoPlayActivity skinActivity) {
            activityWeakReference = new WeakReference<VideoPlayActivity>(skinActivity);
        }

        @Override
        public void onCompletion() {

            VideoPlayActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onCompletion();
            }
        }
    }

    private void onCompletion() {
        logStrs.add(format.format(new Date()) + getString(R.string.log_play_completion));
        for (String log : logStrs) {
            tvLogs.append(log + "\n");
        }
        Toast.makeText(VideoPlayActivity.this.getApplicationContext(), R.string.toast_play_compleion,
                Toast.LENGTH_SHORT).show();

        // 当前视频播放结束, 播放下一个视频
        onNext();
    }
    private void onNext() {
        if (currentError == ErrorInfo.UnConnectInternet) {
            // 此处需要判断网络和播放类型
            // 网络资源, 播放完自动波下一个, 无网状态提示ErrorTipsView
            // 本地资源, 播放完需要重播, 显示Replay, 此处不需要处理
            if ("vidsts".equals(PlayParameter.PLAY_PARAM_TYPE)) {
                mAliyunVodPlayerView.showErrorTipView(4014, -1, "当前网络不可用");
            }
            return;
        }

//        currentVideoPosition++;
//        if (currentVideoPosition >= alivcVideoInfos.size() - 1) {
//            //列表循环播放，如发现播放完成了从列表的第一个开始重新播放
//            currentVideoPosition = 0;
//        }
//
//        if (alivcVideoInfos.size() > 0) {
//            AlivcVideoInfo.Video video = alivcVideoInfos.get(currentVideoPosition);
//            if (video != null) {
//                changePlayVidSource(video.getVideoId(), video.getTitle());
//            }
//        }

    }



    private static class MyFrameInfoListener implements IAliyunVodPlayer.OnFirstFrameStartListener {

        private WeakReference<VideoPlayActivity> activityWeakReference;

        public MyFrameInfoListener(VideoPlayActivity skinActivity) {
            activityWeakReference = new WeakReference<VideoPlayActivity>(skinActivity);
        }

        @Override
        public void onFirstFrameStart() {

            VideoPlayActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onFirstFrameStart();
            }
        }
    }
    private void onFirstFrameStart() {
        Map<String, String> debugInfo = mAliyunVodPlayerView.getAllDebugInfo();
        long createPts = 0;
        if (debugInfo.get("create_player") != null) {
            String time = debugInfo.get("create_player");
            createPts = (long)Double.parseDouble(time);
            logStrs.add(format.format(new Date(createPts)) + getString(R.string.log_player_create_success));
        }
        if (debugInfo.get("open-url") != null) {
            String time = debugInfo.get("open-url");
            long openPts = (long)Double.parseDouble(time) + createPts;
            logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_open_url_success));
        }
        if (debugInfo.get("find-stream") != null) {
            String time = debugInfo.get("find-stream");
            long findPts = (long)Double.parseDouble(time) + createPts;
            logStrs.add(format.format(new Date(findPts)) + getString(R.string.log_request_stream_success));
        }
        if (debugInfo.get("open-stream") != null) {
            String time = debugInfo.get("open-stream");
            long openPts = (long)Double.parseDouble(time) + createPts;
            logStrs.add(format.format(new Date(openPts)) + getString(R.string.log_start_open_stream));
        }
        logStrs.add(format.format(new Date()) + getString(R.string.log_first_frame_played));
//        for (String log : logStrs) {
//            tvLogs.append(log + "\n");
//        }
    }


    private static class MyChangeQualityListener implements IAliyunVodPlayer.OnChangeQualityListener {

        private WeakReference<VideoPlayActivity> activityWeakReference;

        public MyChangeQualityListener(VideoPlayActivity skinActivity) {
            activityWeakReference = new WeakReference<VideoPlayActivity>(skinActivity);
        }

        @Override
        public void onChangeQualitySuccess(String finalQuality) {

            VideoPlayActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onChangeQualitySuccess(finalQuality);
            }
        }

        @Override
        public void onChangeQualityFail(int code, String msg) {
            VideoPlayActivity activity = activityWeakReference.get();
            if (activity != null) {
                activity.onChangeQualityFail(code, msg);
            }
        }
    }

    private void onChangeQualitySuccess(String finalQuality) {
        logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_success));
        Toast.makeText(VideoPlayActivity.this.getApplicationContext(),
                getString(R.string.log_change_quality_success), Toast.LENGTH_SHORT).show();
    }

    void onChangeQualityFail(int code, String msg) {
        logStrs.add(format.format(new Date()) + getString(R.string.log_change_quality_fail) + " : " + msg);
        Toast.makeText(VideoPlayActivity.this.getApplicationContext(),
                getString(R.string.log_change_quality_fail), Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onResume() {
        super.onResume();
        updatePlayerViewMode();
        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onResume();
        }
    }

    private void updatePlayerViewMode() {
        if (mAliyunVodPlayerView != null) {
            int orientation = getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                //转为竖屏了。
                //显示状态栏
                //                if (!isStrangePhone()) {
                //                    getSupportActionBar().show();
                //                }

                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

                //设置view的布局，宽高之类
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams)mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = (int)(ScreenUtils.getWidth(this) * 9.0f / 16);
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //                if (!isStrangePhone()) {
                //                    aliVcVideoViewLayoutParams.topMargin = getSupportActionBar().getHeight();
                //                }

            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                //转到横屏了。
                //隐藏状态栏
                if (!Utils.isStrangePhone()) {
                    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mAliyunVodPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }

                //设置view的布局，宽高
                LinearLayout.LayoutParams aliVcVideoViewLayoutParams = (LinearLayout.LayoutParams)mAliyunVodPlayerView
                        .getLayoutParams();
                aliVcVideoViewLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                aliVcVideoViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                //                if (!isStrangePhone()) {
                //                    aliVcVideoViewLayoutParams.topMargin = 0;
                //                }
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mAliyunVodPlayerView != null) {
            mAliyunVodPlayerView.onStop();
        }

//        if (downloadManager != null && downloadDataProvider != null) {
//            downloadManager.stopDownloadMedias(downloadDataProvider.getAllDownloadMediaInfo());
//        }

    }


    private void initAppBar() {
        mAppBar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            int scrollRangle = appBarLayout.getTotalScrollRange();
            //初始verticalOffset为0，不能参与计算。
            if (verticalOffset == 0) {
                mToolbar.setAlpha(0.0f);
                mImmersionBar.statusBarColor(R.color.white)
                        .statusBarDarkFont(true, 0.2f)
                        .init();

            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    mTvPlayer.setVisibility(View.VISIBLE);
                    mToolbar.setVisibility(View.VISIBLE);
                    mImmersionBar.statusBarColor(R.color.video_toolbar_background)
                            .statusBarDarkFont(true, 0.2f)
                            .init();
                }
            } else {
                //保留一位小数
                float alpha = Math.abs(Math.round(1.0f * verticalOffset / scrollRangle) * 10) / 10;
                mToolbar.setAlpha(alpha);
                mImmersionBar.statusBarColor(R.color.white)
                        .statusBarDarkFont(true, 0.2f)
                        .init();
            }
        });
    }


    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.circle_tablayout_unselect_color));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.circle_tablayout_select_color));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                        if (index == 2) {
                            commentLinear.setVisibility(View.VISIBLE);
                        } else {
                            commentLinear.setVisibility(View.GONE);
                        }
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(getResources().getColor(R.color.circle_tablayout_select_color));
                return linePagerIndicator;
            }
        });

        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        adapter = new MyPagerAdapter(getSupportFragmentManager(), mDataList, fragments);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 2) {
                    commentLinear.setVisibility(View.VISIBLE);
                } else {
                    commentLinear.setVisibility(View.GONE);
                }
                // arg0是当前选中的页面的Position
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // arg0 :当前页面，及你点击滑动的页面；arg1:当前页面偏移的百分比；arg2:当前页面偏移的像素位置
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                //arg0 ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没做。
//                if(arg0 == 0){
//                    Log.e(TAG, "onPageScrollStateChanged------>0");
//                }else if(arg0 == 1){
//                    Log.e(TAG, "onPageScrollStateChanged------>1");
//                }else if(arg0 == 2){
//                    Log.e(TAG, "onPageScrollStateChanged------>2");
//                }

            }
        });


    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_back, R.id.tv_player})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                appBarParams.setScrollFlags(i0 | i1);//重置折叠效果
                appBarChildAt.setLayoutParams(appBarParams);
                break;
            case R.id.tv_player:
                appBarParams.setScrollFlags(0);//这个加了之后不可滑动
                appBarChildAt.setLayoutParams(appBarParams);
                break;
        }
    }


    private void showPopListView() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_list, null);
        handleListView(contentView);
        popupWindow = new CommonPopupWindow.Builder(this)
                //设置PopupWindow布局
                .setView(contentView)
                //设置宽高
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                //设置动画
                .setAnimationStyle(R.style.popupAnimation)
                //设置背景颜色，取值范围0.0f-1.0f 值越小越暗 1.0f为透明
                .setBackGroundLevel(1f)
                //设置PopupWindow里的子View及点击事件
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId) {
//                        TextView tv_child = (TextView) view.findViewById(R.id.tv_child);
//                        tv_child.setText("我是子View");
                    }
                })
                //设置外部是否可点击 默认是true
                .setOutsideTouchable(false)
                //开始构建
                .create();
        //弹出PopupWindow
        appBarParams.setScrollFlags(0);//这个加了之后不可滑动
        appBarChildAt.setLayoutParams(appBarParams);
        popupWindow.showAsDropDown(mAliyunVodPlayerView);
    }

    private void handleListView(View contentView) {

        //关闭popwindow
        MyImageView cancelPop = contentView.findViewById(R.id.cancel);
        cancelPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarParams.setScrollFlags(i0 | i1);//重置折叠效果
                appBarChildAt.setLayoutParams(appBarParams);
                popupWindow.dismiss();
                addHeader = false;
            }
        });

        recyclerView = contentView.findViewById(R.id.recyclerview);
        refreshLayout = contentView.findViewById(R.id.refreshLayout);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                getData(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                getData(false);  //上拉加载添加数据
            }
        });
        recyclerView.setPullRefreshEnabled(false);
        recyclerView.setLoadingMoreEnabled(false);
        recyclerView.setAdapter(commentSecondAdapter);
        getData(true);
    }

    private void getData(boolean refresh) {
        Map<String, String> map = new HashMap<String, String>();

        ApiRequest.getSecondComments(map, new MyCallBack<SecondCommentBean>(mContext) {
            @Override
            public void onSuccess(SecondCommentBean obj) {
                if (refresh) {
                    if (obj.getData().getFirstVideoReplyVos().size() > 0) {
                        dataBean = obj.getData();
                        addHeader(dataBean);
                        firstVideoReplyVos.addAll(obj.getData().getFirstVideoReplyVos());
                        commentSecondAdapter.setList(obj.getData().getFirstVideoReplyVos(), true);
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                } else {
                    if (obj.getData().getFirstVideoReplyVos().size() > 0) {
                        firstVideoReplyVos.addAll(obj.getData().getFirstVideoReplyVos());
                        commentSecondAdapter.notifyDataSetChanged();
                        refreshLayout.finishLoadMore();
                    } else {
                        Toast.makeText(getContext(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                        refreshLayout.finishLoadMoreWithNoMoreData();//将不会再次触发加载更多事件
                    }
                }
            }
        });
    }

    public void addHeader(SecondCommentBean.DataBean dataBean) {
        if (!addHeader) {
            //添加轮播图
            View view = LayoutInflater.from(this).inflate(R.layout.comment_second_title_layout, (ViewGroup) findViewById(android.R.id.content), false);
            circleImageView = view.findViewById(R.id.comment_item_logo);
            commentUserName = view.findViewById(R.id.comment_item_userName);
            commentTime = view.findViewById(R.id.comment_item_time);
            commentContent = view.findViewById(R.id.comment_item_content);
            commentUserName.setText(dataBean.getFromUname());
            commentTime.setText(dataBean.getReplyTime());
            commentContent.setText(dataBean.getContent());
            Glide.with(mContext).load(dataBean.getFromUimg())
                    .into(circleImageView);
            recyclerView.addHeaderView(view);
        }
        addHeader = true;
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            appBarParams.setScrollFlags(i0 | i1);//重置折叠效果
            appBarChildAt.setLayoutParams(appBarParams);
            popupWindow.dismiss();
            addHeader = false;
        } else {
            super.onBackPressed();
        }
    }
}
