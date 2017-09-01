package com.muzikun.lhfsyczl.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.muzikun.lhfsyczl.R;
import com.muzikun.lhfsyczl.base.BaseActivity;
import com.muzikun.lhfsyczl.config.Constants;
import com.muzikun.lhfsyczl.entity.BaseResponse2Entity;
import com.muzikun.lhfsyczl.entity.UserDB;
import com.muzikun.lhfsyczl.net.HttpRequest;
import com.muzikun.lhfsyczl.net.HttpService;
import com.muzikun.lhfsyczl.net.RxSchedulersHelper;
import com.muzikun.lhfsyczl.net.RxSubscriber;
import com.muzikun.lhfsyczl.uihelper.GlideCircleTransform;
import com.muzikun.lhfsyczl.util.ImageUtil;
import com.muzikun.lhfsyczl.util.LogUtils;
import com.muzikun.lhfsyczl.util.SPUtil;
import com.muzikun.lhfsyczl.util.ToastUtils;
import com.muzikun.lhfsyczl.view.SelectedLayerTextView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 功能描述：个人中心
 */

public class MyInfoActivity extends BaseActivity {
    private static final int REQUEST_CAMERA = 0x11;
    private static final int REQUEST_CODE_CUTTING = 0x12;
    private static final int REQUEST_CODE_PICK = 0x13;
    //
    public static final int REQUEST_CODE_NICKNAME = 0x14;
    @BindView(R.id.ivHead)
    AppCompatImageView ivHead;
    @BindView(R.id.lilUpdateHead)
    LinearLayout lilUpdateHead;
    @BindView(R.id.lilNickName)
    LinearLayout lilNickName;

    @BindView(R.id.tvNickName)
    AppCompatTextView tvNickName;
    @BindView(R.id.tvTel)
    AppCompatTextView tvTel;
    @BindView(R.id.lilChangePwd)
    LinearLayout lilChangePwd;
    @BindView(R.id.tvLogout)
    SelectedLayerTextView tvLogout;


    //
    private Uri cameraFileUri;
    private File cameraFile;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void initView() {
        setTitle("个人中心");

        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String avatar = sp.getString(UserDB.AVATAR, "");
        String nickName = sp.getString(UserDB.NAME, "");
        String tel = sp.getString(UserDB.TEL, "");
        tvNickName.setText(nickName);
        tvTel.setText(tel);


        Glide.with(mContext)
                .load(avatar)
                .placeholder(R.drawable.icon_default_head)
                .transform(new GlideCircleTransform(mContext))
                .into(ivHead);


    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.lilUpdateHead, R.id.lilChangePwd, R.id.tvLogout, R.id.lilNickName})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lilUpdateHead:
                showDialog();
                break;
            case R.id.lilChangePwd:
                jumpToActivity(ChangePwdActivity.class, false);
                break;
            case R.id.tvLogout:
                SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
                sp.putBoolean(UserDB.isLogin, false);
                this.finish();
                break;
            case R.id.lilNickName://
                Intent intent = new Intent(this, ChangeNickNameActivity.class);
                startActivityForResult(intent, REQUEST_CODE_NICKNAME);

                break;
        }
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("上传头像");
        builder.setItems(new String[]{"相机", "图库"},
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which) {
                            case 0:
                                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                                    useCamera();
                                } else {
                                    // 申请单个权限。
                                    AndPermission.with(mActivity)
                                            .requestCode(REQUEST_CAMERA)
                                            .permission(Manifest.permission.CAMERA,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                                            )
                                            .callback(new PermissionListener() {
                                                @Override
                                                public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
                                                    if (requestCode == REQUEST_CAMERA) {
                                                        useCamera();
                                                    }
                                                }

                                                @Override
                                                public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                                    builder.setTitle("权限提示");
                                                    builder.setMessage("你已经拒绝使用相机，为了能发送视频，请到设置中开启相机和文件权限");
                                                    builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                            intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                                                            startActivity(intent);
                                                        }
                                                    });
                                                    builder.show();
                                                }
                                            })
                                            // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                                            // 这样避免用户勾选不再提示，导致以后无法申请权限。
                                            // 你也可以不设置。
                                            .rationale(new RationaleListener() {
                                                @Override
                                                public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                                    // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
                                                    AndPermission.rationaleDialog(mContext, rationale).show();
                                                }
                                            })
                                            .start();
                                }
                                break;
                            case 1:

                                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                                startActivityForResult(pickIntent, REQUEST_CODE_PICK);
                                break;
                            default:
                                break;
                        }
                    }
                });
        builder.create().show();
    }

    /**
     * 使用相机
     */
    private void useCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pathName = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + File.separator + System.currentTimeMillis() + ".jpg";
        cameraFile = new File(pathName);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
            cameraFileUri = FileProvider.getUriForFile(mContext, mActivity.getPackageName() + ".fileprovider", cameraFile);
            //添加权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            cameraFileUri = Uri.fromFile(cameraFile);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /**
     * 裁剪照片
     *
     * @param uri
     * @param isTakePhoto 是否来自于拍照
     */
    public void startPhotoZoom(Uri uri, boolean isTakePhoto) {


        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && isTakePhoto) {
            uri = FileProvider.getUriForFile(mContext, mActivity.getPackageName() + ".fileprovider", cameraFile);
            //添加权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);//保存在bitmap中返回
        intent.putExtra("noFaceDetection", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, REQUEST_CODE_CUTTING);
    }

    /**
     * 保存拍照
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            LogUtils.i(TAG, "裁剪图片成功");
            Bitmap photo = extras.getParcelable("data");
//            Drawable drawable = new BitmapDrawable(getResources(), photo);

            String path = ImageUtil.saveBitmap(mContext, photo);
            upLoadImage(new File(path));
        }

    }

    /**
     * 上传图片
     *
     * @param file
     */
    private void upLoadImage(File file) {
        SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
        String tel = sp.getString(UserDB.TEL, "");
        //
        Map<String, RequestBody> partMap = new HashMap<>();
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
        partMap.put("username", RequestBody.create(MediaType.parse("text/plain"), tel));
        partMap.put("file" + "\";filename=\"" + file.getName(), fileBody);
        HttpService httpService = HttpRequest.provideClientApi();
        httpService.uploadPicToMine(partMap)
                .compose(RxSchedulersHelper.<BaseResponse2Entity<String>>io_main())
                .subscribe(new RxSubscriber<BaseResponse2Entity<String>>() {
                    @Override
                    public Activity getCurrentActivity() {
                        return mActivity;
                    }

                    @Override
                    public void _onNext(int status, BaseResponse2Entity<String> response) {
                        LogUtils.i(TAG, "upLoadImage ---->" + response.getErrmsg());
                        LogUtils.i(TAG, "upLoadImage ---->" + response.getData());
                        if (response != null && response.getFlag() == 1) {
                            SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
                            sp.putString(UserDB.AVATAR, "http://" + response.getData());
                            Glide.with(mActivity)
                                    .load("http://" + response.getData())
                                    .placeholder(R.drawable.icon_default_head)
                                    .transform(new GlideCircleTransform(mContext))
                                    .into(ivHead);
                        } else {
                            ToastUtils.show("上传失败，请重新上传");
                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData(), false);

                break;
            case REQUEST_CODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            case REQUEST_CAMERA:
                if (cameraFileUri != null) {
                    startPhotoZoom(cameraFileUri, true);
                }
                break;
            case REQUEST_CODE_NICKNAME:
                if (requestCode == REQUEST_CODE_NICKNAME) {
                    SPUtil sp = new SPUtil(mContext, Constants.USER_TABLE);
                    String nickName = sp.getString(UserDB.NAME, "");
                    tvNickName.setText(nickName);
                }
                break;
            default:
                break;
        }

        super.onActivityResult(requestCode, resultCode, data);


    }
}
