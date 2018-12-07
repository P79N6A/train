//package com.petzmall.training.activity;
//
//import android.Manifest;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.provider.Settings;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.view.KeyEvent;
//import android.view.View;
//
//import com.petzmall.training.R;
//import com.petzmall.training.base.BaseActivity;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by Administrator on 2018/5/15 0015.
// */
//
//public class CheckPermissionsActivity extends BaseActivity implements
//        ActivityCompat.OnRequestPermissionsResultCallback {
//    /**
//     * 需要进行检测的权限数组
//     */
//    protected String[] needPermissions = {
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.READ_PHONE_STATE
//    };
//
//    private static final int PERMISSON_REQUESTCODE = 0;
//    private static final int SETTING_REQUESTCODE = 1;
//
//    /**
//     * 判断是否需要检测，防止不停的弹框
//     */
//    private boolean isNeedCheck = true;
//
//    @Override
//    protected int getContentView() {
//        return 0;
//    }
//
//    @Override
//    protected void initView() {
//
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    protected void onViewClick(View v) {
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (isNeedCheck) {
//            checkPermissions(needPermissions);
//        }
//    }
//
//    /**
//     * @param permissions
//     * @since 2.5.0
//     */
//    private void checkPermissions(String... permissions) {
//        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
//        if (null != needRequestPermissonList
//                && needRequestPermissonList.size() > 0) {
//            ActivityCompat.requestPermissions(this,
//                    needRequestPermissonList.toArray(
//                            new String[needRequestPermissonList.size()]),
//                    PERMISSON_REQUESTCODE);
//        }
//    }
//
//    /**
//     * 获取权限集中需要申请权限的列表
//     *
//     * @param permissions
//     * @return
//     * @since 2.5.0
//     */
//    private List<String> findDeniedPermissions(String[] permissions) {
//        List<String> needRequestPermissonList = new ArrayList<String>();
//        for (String perm : permissions) {
//            if (ContextCompat.checkSelfPermission(this,
//                    perm) != PackageManager.PERMISSION_GRANTED
//                    || ActivityCompat.shouldShowRequestPermissionRationale(
//                    this, perm)) {
//                needRequestPermissonList.add(perm);
//            }
//        }
//        return needRequestPermissonList;
//    }
//
//    /**
//     * 检测是否说有的权限都已经授权
//     *
//     * @param grantResults
//     * @return
//     * @since 2.5.0
//     */
//    private boolean verifyPermissions(int[] grantResults) {
//        for (int result : grantResults) {
//            if (result != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions, int[] paramArrayOfInt) {
//        if (requestCode == PERMISSON_REQUESTCODE) {
//            if (!verifyPermissions(paramArrayOfInt)) {
//                showMissingPermissionDialog();
//                isNeedCheck = false;
//            }
//        }
//    }
//
//
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            this.finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SETTING_REQUESTCODE) {
//            checkPermissions(needPermissions);
//        }
//    }
//}