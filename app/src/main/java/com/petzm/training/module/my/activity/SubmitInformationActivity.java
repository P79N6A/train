package com.petzm.training.module.my.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.petzm.training.R;
import com.petzm.training.base.BaseActivity;
import com.petzm.training.base.BaseObj;
import com.petzm.training.base.MyCallBack;
import com.petzm.training.network.ApiRequest;

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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;

public class SubmitInformationActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {


    @BindView(R.id.iv_store)
    ImageView ivStore;
    private BottomSheetDialog selectPhotoDialog;
    private static final String TAG = SubmitInformationActivity.class.getName();
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;

    int size;
    String ImgUrl;//图片
    NiftyDialogBuilder dialogBuilder;
    @Override
    protected int getContentView() {
        return R.layout.act_submit_information_step1;
    }


    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColor(R.color.white)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .init();
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
    protected void initView() {
        setAppTitle("门店门头照片");
        size = Math.min(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
    }

    @Override
    protected void initData() {
         dialogBuilder=NiftyDialogBuilder.getInstance(this);
    }

    @OnClick({R.id.tv_submit,R.id.iv_store})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit://提交
                if(ImgUrl==null){
                    showToastS("还没有选择门头照片");
                    return;
                }
                File file = new File(ImgUrl);//filePath 图片地址
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);//表单类型;//ParamKey.TOKEN 自定义参数key常量类，即参数名
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                builder.addFormDataPart("file", file.getName(), imageBody);//imgfile 后台接收图片流的参数名
                List<MultipartBody.Part> parts = builder.build().parts();
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
                STActivity(SubmitInformationNowActivity.class);
                break;
            case R.id.iv_store://选图片
                showSelectPhotoDialog();
                break;
        }
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
                ImgUrl = baseImg;
                Glide.with(SubmitInformationActivity.this).load(new File(baseImg)).into(ivStore);

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

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
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



}
