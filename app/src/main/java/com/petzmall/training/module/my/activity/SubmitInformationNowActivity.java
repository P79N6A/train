package com.petzmall.training.module.my.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.customview.MyLinearLayout;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.petzmall.training.GetSign;
import com.petzmall.training.MainActivity;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseActivity;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.network.ApiRequest;
import com.petzmall.training.network.response.UploadImageObj;
import com.petzmall.training.network.response.UploadImgItem;
import com.petzmall.training.tools.BitmapUtils;
import com.petzmall.training.tools.ImageUtils;
import com.petzmall.training.tools.ImgUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import rx.Subscriber;
import top.zibin.luban.Luban;

public class SubmitInformationNowActivity extends BaseActivity {


    private static final String TAG = "SubmitInformation";

    @BindView(R.id.tv_editaddress_area)
    TextView addressArea;


    private BottomSheetDialog selectPhotoDialog;

    CityPickerView mCityPickerView = new CityPickerView();
    private int picOrCamera; //判断选择的是拍照还是选择图片

    private  int flag;//判断选择的第一个还是第二个。
    private String imgSaveName = "";
    Bitmap bitmap;
    File file;
    private  String ImagePath1;
    @Override
    protected int getContentView() {
        return R.layout.act_submit_information_step2;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .init();
    }


    @Override
    protected void initView() {
        setAppTitle("门店信息填写");
        /**
         * 预先加载仿iOS滚轮实现的全部数据
         */
        mCityPickerView.init(this);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_editaddress_area,R.id.tv_submit})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_editaddress_area:
                wheel();
                break;
            case R.id.tv_submit:
                STActivity(MainActivity.class);
                break;
        }
    }


    /**
     * 弹出选择器
     */
    private void wheel() {



        CityConfig cityConfig = new CityConfig.Builder().title("选择城市")//标题
                .build();

        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
//                sb.append("选择的结果：\n");
                if (province != null) {
                    sb.append(province.getName() + " " );
                }

                if (city != null) {
                    sb.append(city.getName() + " ");
                }

                if (district != null) {
                    sb.append(district.getName() );
                }
//                if (province != null) {
//                    sb.append(province.getName() + " " + province.getId() + "\n");
//                }
//
//                if (city != null) {
//                    sb.append(city.getName() + " " + city.getId() + ("\n"));
//                }
//
//                if (district != null) {
//                    sb.append(district.getName() + " " + district.getId() + ("\n"));
//                }

                addressArea.setText(sb.toString());

            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(SubmitInformationNowActivity.this, "已取消");
            }
        });
        mCityPickerView.showCityPicker();
    }
    private void showSelectPhotoDialog() {
        if (selectPhotoDialog == null) {
            View sexView = LayoutInflater.from(mContext).inflate(R.layout.popu_select_photo, null);
            sexView.findViewById(R.id.tv_select_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    selectPhoto();

                }
            });
            sexView.findViewById(R.id.tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    takePhoto();
                }
            });
            sexView.findViewById(R.id.tv_photo_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                }
            });
            selectPhotoDialog = new BottomSheetDialog(mContext);
            selectPhotoDialog.setCanceledOnTouchOutside(true);
            selectPhotoDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            selectPhotoDialog.setContentView(sexView);
        }
        selectPhotoDialog.show();
    }


    //选择相册
    private void selectPhoto() {
        picOrCamera = 1;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3000);
    }


    private void takePhoto() {
        picOrCamera=2;
        requestPermissions();
    }

    @SuppressLint("CheckResult")
    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(SubmitInformationNowActivity.this);
        rxPermission
                .requestEach(
                        Manifest.permission.CAMERA
                )
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + "/test/" + System.currentTimeMillis() + ".jpg");
                            file.getParentFile().mkdirs();
                            //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
                            Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.xykj.customview.fileprovider", file);

                            //添加权限
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                startActivityForResult(intent, REQUEST_CAMERA);
                            startActivityForResult(intent, 2000);
                            Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {

                            showMissingPermissionDialog();
                            // 用户拒绝了该权限，并且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                        }
                    }
                });


    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode != RESULT_OK) {
//            return;
//        }
//        switch (requestCode) {
//            case 2000:
//                imgSaveName = file.getAbsolutePath();
////                uploadImg();
//                break;
//            case 3000:
//                Uri uri = data.getData();
//                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//                if (cursor != null && cursor.moveToFirst()) {
//                    imgSaveName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
//                }
//                uploadImg();
//                if (resultCode == RESULT_OK) {
//                    bitmap = null;
//                    //判断手机系统版本号
//                    if (Build.VERSION.SDK_INT >= 19) {
//                        //4.4及以上系统使用这个方法处理图片
//                        bitmap = ImgUtil.handleImageOnKitKat(this, data);        //ImgUtil是自己实现的一个工具类
//                    } else {
//                        //4.4以下系统使用这个方法处理图片
//                        bitmap = ImgUtil.handleImageBeforeKitKat(this, data);
//                    }
//                }
//                break;
//        }
//    }

//    private void uploadImg() {
//        showLoading();
//        Log.i("========", "========" + imgSaveName);
//        RXStart(new IOCallBack<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                String newPath = ImageUtils.filePath;
//                ImageUtils.makeFolder(newPath);
//                FileInputStream fis = null;
//                try {
//                    List<File> files = Luban.with(mContext).load(imgSaveName).get();
//                    String imgStr = BitmapUtils.bitmapToString2(files.get(0));
//                    ivBusinessLicence.setImageBitmap(bitmap);
//                    subscriber.onNext(imgStr);
//                    subscriber.onCompleted();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    subscriber.onError(e);
//                }
//            }
//            @Override
//            public void onMyNext(String baseImg) {
//                UploadImgItem item = new UploadImgItem();
//                item.setFile(baseImg);
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("sign", GetSign.getSign(map));
//                ApiRequest.uploadImg(map, item, new MyCallBack<UploadImageObj>(mContext) {
//                    @Override
//                    public void onSuccess(UploadImageObj obj) {
//                        if(flag ==1){ //如果选择的是第一个上传
//                            ImagePath1 = obj.getImg();
//                            if(picOrCamera ==1){//同时不是选择拍照。则执行
////                                ivId1.setVisibility(View.VISIBLE);
//                                ivBusinessLicence.setImageBitmap(bitmap);
////                                linearImage1.setVisibility(View.GONE);//隐藏界面
//                            }else{
//                                ivBusinessLicence.setImageBitmap(BitmapFactory.decodeFile(imgSaveName));
////                                ivId1.setVisibility(View.VISIBLE);
////                                linearImage1.setVisibility(View.GONE);//隐藏界面
//                            }
//                        }else{
////                            ImagePath2 = obj.getImg();
////                            if(picOrCamera ==1){//同时不是选择拍照。则执行
////                                ivId2.setVisibility(View.VISIBLE);
////                                ivId2.setImageBitmap(bitmap);
////                                linearImage2.setVisibility(View.GONE);//隐藏界面
////                            }else{
////                                ivId2.setImageBitmap(BitmapFactory.decodeFile(imgSaveName));
////                                ivId2.setVisibility(View.VISIBLE);
////                                linearImage2.setVisibility(View.GONE);//隐藏界面
////                            }
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onMyError(Throwable e) {
//                super.onMyError(e);
//                dismissLoading();
//                showToastS("图片处理失败");
//            }
//        });
//    }
}
