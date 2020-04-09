package com.example.mvp_frame.mvp.model.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService<T> {
    //get请求
    @GET
    Observable<ResponseBody> requestData(@Url String url);

    //多个参数的get请请
    //有多个参数的时候可以通过map集合来传入参数，后边参数键值对，键是string类型，值是任意类型。
    @GET
    Observable<ResponseBody> requestData(@Url String url, @QueryMap Map<String,T> params);

    //下载文件
    //下载文件只需要传入要下载的url地址，所以参数只要url就可以
    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url);

    //没有参数的post请求
    //没用参数的post请求和json串的post请求，不需要加@FormUrlEncoded
    @POST
    Observable<ResponseBody> requestFormData(@Url String url);

    //多个参数的post请求，参数是键值对
    //如果如果上传的是键值对是Feld字段的话就要加@FormUrlEncoded，有Form表单请求就加@FormUrlEncoded注解
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> requestFormData(@Url String url, @FieldMap Map<String,T> params);

    //有请求头并且参数是键值对的post请求
    //如果如果上传的是键值对是Feld字段的话就要加@FormUrlEncoded，如果有Form表单请求就加@FormUrlEncoded注解
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> requestFormData(@Url String url, @HeaderMap Map<String, T> headers,
                                             @FieldMap Map<String, T> params);

    //参数是json串的post请求
    @POST
    Observable<ResponseBody> requestFormData(@Url String url, @Body RequestBody requestBody);

    //单个文件上传
    //@Part是存文件的
    @POST
    @Multipart
    Observable<ResponseBody> uploadSingleFile(@Url String url, @Part MultipartBody.Part part);

    //多个文件上传方法1
    //Part...的三个点代表可变长度参数，可以是一个part也可以是多个part
    //@Part是存多个文件的
    @POST
    @Multipart
    Observable<ResponseBody> uploadMultipleFiles(@Url String url, @Part MultipartBody.Part... part);

    //多个文件上传方法2
    //@PartMap是存多个文件的
    @POST
    @Multipart
    Observable<ResponseBody> uploadMultipleFiles(@Url String url, @PartMap Map<String, T> filesMap);

    //单个文件+字符串上传
    //就相当也微信朋友圈发表文字+图片
    // @Body就是字符串，@Part是文件
    @POST
    @Multipart
    Observable<ResponseBody> uploadStrFile(@Url String url, @Body RequestBody requestBody, @Part MultipartBody.Part part);

    //多个文件+键值对 上传1
    @POST
    @Multipart
    Observable<ResponseBody> uploadStrFiles(@Url String url, @Body RequestBody requestBody, @PartMap Map<String,T> filesMap);

    //多个文件+键值对 上传2
    @POST
    @Multipart
    Observable<ResponseBody> uploadStrFiles(@Url String url, @Body RequestBody requestBody, @Part MultipartBody.Part...parts);



}
