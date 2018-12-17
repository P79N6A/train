package com.petzmall.training.module.my.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;
import com.petzmall.training.MainActivity;
import com.petzmall.training.R;
import com.petzmall.training.base.BaseActivity;
import com.petzmall.training.base.BaseObj;
import com.petzmall.training.base.MyCallBack;
import com.petzmall.training.network.ApiRequest;
import com.petzmall.training.tools.KeyboardUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoImpl;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.InvokeParam;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;
import org.devio.takephoto.permission.InvokeListener;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.permission.TakePhotoInvocationHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

public class SubmitInformationNowActivity extends BaseActivity   implements TakePhoto.TakeResultListener, InvokeListener {


    private static final String TAG = "SubmitInformation";

    @BindView(R.id.tv_editaddress_area)
    TextView addressArea;

    @BindView(R.id.et_shop_name)
    MyEditText etShopName;
    @BindView(R.id.et_name)
    MyEditText etName;
    @BindView(R.id.et_phone)
    MyEditText etPhone;
    @BindView(R.id.et_address_detail)
    MyEditText etAddressDetail;
    @BindView(R.id.et_license_number)
    MyEditText etLicenseNumber;
    @BindView(R.id.iv_store_license)
    ImageView ivStoreLicense;
    @BindView(R.id.iv_store_license1)
    ImageView ivStoreLicense1;
    @BindView(R.id.iv_store_license2)
    ImageView ivStoreLicense2;
    @BindView(R.id.iv_store_license3)
    ImageView ivStoreLicense3;
    @BindView(R.id.tv_submit)
    MyTextView tvSubmit;

    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    private BottomSheetDialog selectPhotoDialog;

    CityPickerView mCityPickerView = new CityPickerView();//选择城市
    private int picOrCamera; //判断选择的是拍照还是选择图片

    private int flag;//判断选择的第一个还是第二个。
    private String imgSaveName = "";
    Bitmap bitmap;
    File file;
    String baseImg;//图片
    int size;
     int selectPicturePosition;//选择的图片位置
    List<String> picUrl = new ArrayList<>();//图片url集合
    NiftyDialogBuilder dialogBuilder;
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
        size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_editaddress_area,R.id.iv_store_license,R.id.iv_store_license1,R.id.iv_store_license2,R.id.iv_store_license3,R.id.tv_submit})
    protected void onViewClick(View v) {
        switch (v.getId()) {

            case R.id.tv_editaddress_area:
                KeyboardUtils.hideKeyboard(addressArea);
                selectCity();
                break;

            case R.id.iv_store_license:
                selectPicturePosition = 1;
                showSelectPhotoDialog();
                break;

            case R.id.iv_store_license1:
                selectPicturePosition = 2;
                showSelectPhotoDialog();
                break;

            case R.id.iv_store_license2:
                selectPicturePosition = 3;
                showSelectPhotoDialog();
                break;

            case R.id.iv_store_license3:
                selectPicturePosition = 4;
                showSelectPhotoDialog();
                break;
                case R.id.tv_submit:


//                                    File file = new File(baseImg);//filePath 图片地址
//                MultipartBody.Builder builder = new MultipartBody.Builder()
//                        .setType(MultipartBody.FORM);//表单类型;//ParamKey.TOKEN 自定义参数key常量类，即参数名
//                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                builder.addFormDataPart("file", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
//                List<MultipartBody.Part> parts = builder.build().parts();

                    List<MultipartBody.Part> parts = new ArrayList<>();
                    for (int i = 0; i < picUrl.size(); i++) {

                        File file = new File(picUrl.get(i));
                        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                        parts.add(part);
                    }

                 ApiRequest.uploadImg(parts, new MyCallBack<BaseObj>(mContext) {
                    @Override
                    public void onSuccess(BaseObj obj) {
//                        if(selectImgIndex ==-1){
//                            if(isEmpty(addImgAdapter.getList())){
//                                List<String> list=new ArrayList<String>();
//                                list.add(obj.getImg());
//                                addImgAdapter.setList(list);
//                            }else{
//                                addImgAdapter.getList().add(obj.getImg());
//                            }
//                        }else{
//                            addImgAdapter.getList().set(selectImgIndex,obj.getImg());
//                        }
//                        addImgAdapter.notifyDataSetChanged();
                    }
                });

                break;
        }
    }


    /**
     * 弹出城市选择器
     */
    private void selectCity() {
        CityConfig cityConfig = new CityConfig.Builder().title("选择城市")
                .province("四川省")
                .city("成都市")
                .district("成华区").build();
        mCityPickerView.setConfig(cityConfig);
        mCityPickerView.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
//                sb.append("选择的结果：\n");
                if (province != null) {
                    sb.append(province.getName() + " ");
                }
                if (city != null) {
                    sb.append(city.getName() + " ");
                }
                if (district != null) {
                    sb.append(district.getName());
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
                    selectPhotoOrCamera("photo");

                }
            });
            sexView.findViewById(R.id.tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    selectPhotoOrCamera("camera");
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

    // 选择拍照还是相册
    public void selectPhotoOrCamera(String  takePhotoOrCamera) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Uri imageUri = Uri.fromFile(file);
        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        switch (takePhotoOrCamera) {
            case "photo":
                int limit = 1;
//                if (limit > 1) {
//                    if (rgCrop.getCheckedRadioButtonId() == R.id.rbCropYes) {
//                        takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
//                    } else {
//                        takePhoto.onPickMultiple(limit);
//                    }
//                    return;
//                }
//                if (rgFrom.getCheckedRadioButtonId() == R.id.rbFile) {
//                    if (rgCrop.getCheckedRadioButtonId() == R.id.rbCropYes) {
//                        takePhoto.onPickFromDocumentsWithCrop(imageUri, getCropOptions());
//                    } else {
//                        takePhoto.onPickFromDocuments();
//                    }
//                    return;
//                } else {
//                    if (rgCrop.getCheckedRadioButtonId() == R.id.rbCropYes) {
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
//                    } else {
//                        takePhoto.onPickFromGallery();
//                    }
//                }
                break;
            case "camera":
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                break;
            default:
                break;
        }
    }



    @Override
    public void takeSuccess(TResult result) {
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {

                    subscriber.onNext(result.getImages().get(0).getOriginalPath());
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
            @Override
            public void onMyNext(String baseImg) {
                if(selectPicturePosition == 1 ){
                    Glide.with(SubmitInformationNowActivity.this).load(new File(baseImg)).into(ivStoreLicense);
                }else if(selectPicturePosition == 2){
                    Glide.with(SubmitInformationNowActivity.this).load(new File(baseImg)).into(ivStoreLicense1);
                }else if(selectPicturePosition == 3){
                    Glide.with(SubmitInformationNowActivity.this).load(new File(baseImg)).into(ivStoreLicense2);
                }else {
                    Glide.with(SubmitInformationNowActivity.this).load(new File(baseImg)).into(ivStoreLicense3);
                }

                picUrl.add(baseImg);

                baseImg = baseImg;
//                String imgStr = BitmapUtils.bitmapToString2(new File(baseImg));
//                UploadImgItem item=new UploadImgItem();
//                item.setFile(baseImg);
//                String rnd = getRnd();

//                File file = new File(baseImg);
//                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("image", file.getName(), imageBody);



            }
            @Override
            public void onMyError(Throwable e) {
                super.onMyError(e);
                dismissLoading();
                showToastS("图片处理失败");
            }
        });
    }

    @Override
    public void takeFail(TResult result, String msg) {

    }

    @Override
    public void takeCancel() {

    }


    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
    }
    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    private void configCompress(TakePhoto takePhoto) {
        //设置压缩规则，最大500kb
        LubanOptions option=new LubanOptions.Builder()
                .setMaxHeight(800)
                .setMaxWidth(800)
                .setMaxSize(500*1024)
                .create();
        CompressConfig config=CompressConfig.ofLuban(option);
        takePhoto.onEnableCompress(config,false);


    }

    // 设置TakePhoto相关配置
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//是否使用TakePhoto自带的相册进行图片选择，默认不使用，但选择多张图片会使用
        builder.setCorrectImage(true);//是对拍的照片进行旋转角度纠正
        takePhoto.setTakePhotoOptions(builder.create());
    }
    //设置剪裁
    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(size).setAspectY(size);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    @Override
    public void onBackPressed() {
        dialogBuilder
                .withTitle("温馨提示")                                  //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")                                  //def
                .withDividerColor("#5A96F0")                              //def
                .withMessage("是否取消提交门头照片.")                     //.withMessage(null)  no Msg
                .withMessageColor("#FFFFFF")                              //def  | withMessageColor(int resid)
                .withDialogColor("#5A96F0")                               //def  | withDialogColor(int resid)
                .withDuration(700)                                          //def
                .withEffect(Effectstype.Fadein)                                         //def Effectstype.Slidetop
                .withButton1Text("确定")                                      //def gone
                .withButton2Text("取消")                                  //def gone
                .isCancelableOnTouchOutside(true)                           //def    | isCancelable(true)
                //.setCustomView(View or ResId,context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }


//    @SuppressLint("CheckResult")
//    private void requestPermissions() {
//        RxPermissions rxPermission = new RxPermissions(SubmitInformationNowActivity.this);
//        rxPermission
//                .requestEach(
//                        Manifest.permission.CAMERA
//                )
//                .subscribe(new Consumer<Permission>() {
//                    @Override
//                    public void accept(Permission permission) throws Exception {
//                        if (permission.granted) {
//                            // 用户已经同意该权限
//
//                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//
//                            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
//                                    + "/test/" + System.currentTimeMillis() + ".jpg");
//                            file.getParentFile().mkdirs();
//                            //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
//                            Uri uri = FileProvider.getUriForFile(getApplicationContext(), "com.xykj.customview.fileprovider", file);
//
//                            //添加权限
//                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
////                startActivityForResult(intent, REQUEST_CAMERA);
//                            startActivityForResult(intent, 2000);
//                            Log.d(TAG, permission.name + " is granted.");
//                        } else if (permission.shouldShowRequestPermissionRationale) {
//                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
//                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
//                        } else {
//
//                            showMissingPermissionDialog();
//                            // 用户拒绝了该权限，并且选中『不再询问』
//                            Log.d(TAG, permission.name + " is denied.");
//                        }
//                    }
//                });
//
//
//    }



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
