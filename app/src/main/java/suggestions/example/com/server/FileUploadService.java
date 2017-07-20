package suggestions.example.com.server;


import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

/**
 * Created by root on 17-7-14.
 */

public interface FileUploadService {
    @POST("buglog/uploadlog")
    Call<ResponseBody> uploadFiles(
            @Body MultipartBody multipartBody);

    @POST("/v1/message")
    Call<ResponseBody> uploadImages(@Body Imagebody requestBody);

    public class Imagebody {
        public String type;
        public String title;
        public String content;
        public String module;
        public String phone;
        public String email;
        public ArrayList<String> images;
        public String organization;
        public String originator;
        public String vname;
        public String imei1;
        public String imei2;
        public String swv;
        public String hwv;
    }
}

