package suggestions.example.com.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import suggestions.example.com.R;
import suggestions.example.com.bean.Images;
import suggestions.example.com.server.FileUploadService;
import suggestions.example.com.utils.FileUtils;
import suggestions.example.com.utils.XmlUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioButton rb_pro;
    private RadioButton rb_sug;
    private LinearLayout mRootView;
    private int[] image_ids = {R.id.rela_one ,R.id.rela_tow, R.id.rela_three};
    private View rela_one;
    private View rela_tow;
    private View rela_three;
    private TextView tv_app;
    private static int SUG_STATE = 1;
    private static int CURRENT_STATE = SUG_STATE;
    private static int NEW_STATE = 2;
    private FileUploadService fileUploadService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("aaa", "message====" + message);
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit sRetrofit = new Retrofit.Builder()
                .baseUrl("http://10.11.32.94:18080/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build();
        fileUploadService = sRetrofit.create(FileUploadService.class);


        String softwareVersion = XmlUtils.getSoftwareVersion(this);
        String hwVersion = XmlUtils.getHWVersion();
        Log.e("softwareVersion",softwareVersion);
        Log.e("hwVersion",hwVersion);
    }

    private void initView() {
        rb_pro = (RadioButton)findViewById(R.id.rb_pro);
        rb_sug = (RadioButton)findViewById(R.id.rb_sug);
        tv_app = (TextView)findViewById(R.id.tv_app);
        mRootView = (LinearLayout)findViewById(R.id.root_view);
        findViewById(R.id.tv_commit).setOnClickListener(this);
        tv_app.setOnClickListener(this);
        for (int s:image_ids){

           final View inflate = View.inflate(this, R.layout.item, null);
                inflate.setId(s);
                inflate.setOnClickListener(this);
                inflate.findViewById(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inflate.setTag(null);
                        inflate.findViewById(R.id.iv_image).setBackgroundResource(android.R.drawable.editbox_dropdown_light_frame);
                        inflate.setVisibility(View.GONE);
                        mRootView.findViewById(R.id.tv_choose).setVisibility(View.VISIBLE);

                    }
                });

            mRootView.addView(inflate);
        }
        TextView tv_choose = new TextView(this);
        tv_choose.setHeight(dip2px(80));
        tv_choose.setWidth(dip2px(80));
        tv_choose.setTextSize(15);
        tv_choose.setTextColor(Color.BLACK);
        tv_choose.setGravity(Gravity.CENTER);
        tv_choose.setId(R.id.tv_choose);
        tv_choose.setText("添加图片");
        tv_choose.setBackgroundResource(R.drawable.framedraw);
        tv_choose.setOnClickListener(this);
        mRootView.addView(tv_choose);
        rela_one = mRootView.findViewById(R.id.rela_one);
        rela_tow = mRootView.findViewById(R.id.rela_tow);
        rela_three = mRootView.findViewById(R.id.rela_three);
        rela_one.setVisibility(View.GONE);
        rela_tow.setVisibility(View.GONE);
        rela_three.setVisibility(View.GONE);


        rb_sug.setOnClickListener(this);
        rb_pro.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_sug:
                mRootView.setVisibility(View.INVISIBLE);
                rb_pro.setChecked(false);
                if(rb_sug.isChecked()){
                    CURRENT_STATE = SUG_STATE;
                    rb_sug.setChecked(true);
                }else {
                    rb_sug.setChecked(false);
                }
                break;
            case R.id.rb_pro:
                mRootView.setVisibility(View.VISIBLE);
                rb_sug.setChecked(false);
                if(rb_pro.isChecked()){
                    rb_pro.setChecked(true);
                    CURRENT_STATE = NEW_STATE;
                }else {
                    rb_pro.setChecked(false);
                }
                break;
            case R.id.tv_choose:
                if(rela_three.getTag()==null){
                    startActivityForResult(new Intent(
                            Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1);
                }
                break;
            case R.id.rela_one:
                break;
            case R.id.rela_tow:
                break;
            case R.id.rela_three:
                break;
            case R.id.tv_app:
                startActivityForResult(new Intent(this,ChooseAppActivity.class),3);
                break;
            case R.id.tv_commit:
                commit();
                break;
        }

    }

    private void commit() {
        ArrayList<String> image_path = new ArrayList<String>();
        if(CURRENT_STATE == SUG_STATE){
            if(rela_one.isShown()){
                image_path.add(FileUtils.convertImageToStringForServer((String) rela_one.getTag()));
            }
            if(rela_tow.isShown()){
                image_path.add(FileUtils.convertImageToStringForServer((String) rela_tow.getTag()));

            }
            if(rela_three.isShown()){
                image_path.add(FileUtils.convertImageToStringForServer((String) rela_three.getTag()));
            }
        }
        FileUtils.uploadImages(this,image_path,fileUploadService,new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                Log.e("onResponse",response.toString());
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onResponse",t.toString());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Object tag = rela_one.getTag();
            if(tag==null){
                setCurrentState(picturePath ,rela_one);
            }else if(rela_tow.getTag()==null){
                setCurrentState(picturePath ,rela_tow);
            }else if(rela_three.getTag()==null){
                setCurrentState(picturePath ,rela_three);
            }
        }else if (requestCode==3 && resultCode == RESULT_OK && null != data){
            tv_app.setText(data.getAction());
        }
    }
    private String convertImage;

    public void setCurrentState(String picturePath ,final View view){
        Glide.with(this).load(picturePath).asBitmap().into((ImageView) view.findViewById(R.id.iv_image));
        view.setTag(picturePath);
        view.setVisibility(View.VISIBLE);
        if(view.getId()==R.id.rela_three){
            mRootView.findViewById(R.id.tv_choose).setVisibility(View.GONE);
        }
    }
    public  int dip2px(float dpValue){
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale +0.5f);

    }

}
