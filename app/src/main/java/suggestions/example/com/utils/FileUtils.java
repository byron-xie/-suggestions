package suggestions.example.com.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import suggestions.example.com.bean.Items;
import suggestions.example.com.server.FileUploadService;

import static android.R.attr.description;

/**
 * Created by root on 17-7-14.
 */

public class FileUtils {
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    public static Items getDefaultJson(Context context){
        StringBuilder builder = null;
        Items items =null;

        try {
            InputStreamReader open = new InputStreamReader(context.getAssets().open("default.json"),"UTF-8");
            BufferedReader br = new BufferedReader(open);
            String line;
            builder= new StringBuilder();
            while((line = br.readLine()) != null){
                builder.append(line);
            }
            br.close();
            open.close();
        }catch (IOException e){
            Log.e("IOException",e.toString());
        }
        String s = builder == null ? null : builder.toString();
        if(s!=null){
            Gson goGson =new Gson();

            items = goGson.fromJson(s, Items.class);
        }

        return items;
    }
    public static void uploadImages(ArrayList<File> files,FileUploadService service,Callback call){
        MultipartBody multipartBody = filesToMultipartBody(files);

        service.uploadFiles(multipartBody).enqueue(call);

    }

    public static void uploadImages(Context context,ArrayList<String> image_path, FileUploadService service, Callback call){
        FileUploadService.Imagebody imagebody = new FileUploadService.Imagebody();
        imagebody.type="value";
        imagebody.title="value";
        imagebody.content="value";
        imagebody.module="value";
        imagebody.phone="value";
        imagebody.email="value";
        imagebody.images=image_path;
        imagebody.organization="DATA";
        imagebody.originator="user";
        imagebody.vname=XmlUtils.getSoftwareVersion(context);
        imagebody.imei1=XmlUtils.getSystemproString(context,"gsm.imei.sub0");
        imagebody.imei2=XmlUtils.getSystemproString(context,"gsm.imei.sub1");
        imagebody.swv= XmlUtils.getSystemproString(context,"ro.product.version.software");
        imagebody.hwv="value";


        service.uploadImages(imagebody).enqueue(call);

    }


    public static MultipartBody filesToMultipartBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            builder.addFormDataPart("dsad.zip", file.getName(), requestBody);
        }
        builder.setType(MultipartBody.FORM);
        MultipartBody multipartBody = builder.build();
        return multipartBody;
    }

    public static String convertImageToStringForServer(String imagepath){
        Bitmap imageBitmap = readBitmap(imagepath);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if(imageBitmap != null) {
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] byteArray = stream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        }else{
            return null;
        }
    }

    private static Bitmap readBitmap(String imgPath) {
        try {
            return BitmapFactory.decodeFile(imgPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }

    }
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

}
