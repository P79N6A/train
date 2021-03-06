package com.petzm.training.module.player.theme;

import com.petzm.training.module.player.view.control.ControlView;
import com.petzm.training.module.player.view.guide.GuideView;
import com.petzm.training.module.player.view.quality.QualityView;
import com.petzm.training.module.player.view.speed.SpeedView;
import com.petzm.training.module.player.view.tipsview.ErrorView;
import com.petzm.training.module.player.view.tipsview.NetChangeView;
import com.petzm.training.module.player.view.tipsview.ReplayView;
import com.petzm.training.module.player.view.tipsview.TipsView;
import com.petzm.training.module.player.widget.AliyunVodPlayerView;

/*
 * Copyright (C) 2010-2018 Alibaba Group Holding Limited.
 */

/**
 * 主题的接口。用于变换UI的主题。
 * 实现类有{@link ErrorView}，{@link NetChangeView} , {@link ReplayView} ,{@link ControlView},
 * {@link GuideView} , {@link QualityView}, {@link SpeedView} , {@link TipsView},
 * {@link AliyunVodPlayerView}
 */

public interface ITheme {
    /**
     * 设置主题
     * @param theme 支持的主题
     */
    void setTheme(AliyunVodPlayerView.Theme theme);
}
