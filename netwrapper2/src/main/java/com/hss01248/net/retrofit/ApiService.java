package com.hss01248.net.retrofit;


import com.hss01248.net.config.BaseNetBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/8/6 0006.
 */
public interface  ApiService {


    /**
     * 注意:
     * 1.retrofit默认转换string成json obj,如果不需要gson转换,那么就指定泛型为ResponseBody,
     *  只能是ResponseBody,子类都不行,同理,下载上传时,也必须指定泛型为ResponseBody
     * 2. map不能为null,否则该请求不会执行,但可以size为空
     * 3使用@url,而不是@Path注解,后者放到方法体上,会强制先urlencode,然后与baseurl拼接,请求无法成功
     * @param url
     * @param maps
     * @return
     */
    @FormUrlEncoded
    @POST()
    Call<ResponseBody> executePost(@Url String url, @FieldMap Map<String, String> maps);


    /**
     * 直接post体为一个json格式时,使用这个方法.注意:@Body 不能与@FormUrlEncoded共存
     * @param url
     * @param body
     * @return
     */
    @POST()
    Call<ResponseBody> executeJsonPost(@Url String url, @Body RequestBody body);//

    @GET()
    Call<ResponseBody> executGet(@Url String url, @QueryMap Map<String, String> maps);

    @Streaming //流式下载,不加这个注解的话,会整个文件字节数组全部加载进内存,可能导致oom
    @GET
    Call<ResponseBody> download(@Url String fileUrl);


    @Multipart
    @POST()
    Call<ResponseBody> upload(@Url String url,@PartMap Map<String,RequestBody> params,@Part() List<MultipartBody.Part> parts);


    /**
     * 无法实现进度回调
     * @param url
     * @param multipartBody
     * @return
     */
    @POST()
    Call<ResponseBody> upload(@Url String url,@Body MultipartBody multipartBody);


    /**
     * 可以有进度回调
     * @param url
     * @param options
     * @param externalFileParameters
     * @return
     */
    @POST()
    @Multipart
    Call<ResponseBody> uploadWithProgress(@Url String url,@QueryMap Map<String, String> options,
                                            @PartMap Map<String, RequestBody> externalFileParameters) ;



    /**
     * 如下的泛型是无法使用的:
     *      Method return type must not include a type variable or wildcard: retrofit2.Call<T>
     *
     *     JakeWharton:You cannot. Type information needs to be fully known at runtime in order for deserialization to work.
     *     https://github.com/square/retrofit/issues/2012
     */
//
    //

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST("users/image")
    Call<ResponseBody> uploadFilesWithParts(@Part() List<MultipartBody.Part> parts);


    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("users/image")
    Call<ResponseBody> uploadFileWithRequestBody(@Body MultipartBody multipartBody);



    /**
     * 标准格式的json(data,msg,code)解析:泛型嵌套 无法实现: retrofit不支持二次泛型
     * */
    @FormUrlEncoded
    @POST()
    <T>  Call<BaseNetBean<T>> postStandradJson(@Url String url, @FieldMap Map<String, String> maps);

    @GET()
    <T>  Call<BaseNetBean<T>> getStandradJson(@Url String url, @QueryMap Map<String, String> maps);



    /**
     * 普通格式的json(data,msg,code)解析:泛型嵌套
     * */
    @FormUrlEncoded
    @POST()
    <T>  Call<T> postCommonJson(@Url String url, @FieldMap Map<String, String> maps);

    @GET()
    <T>  Call<T> getCommonJson(@Url String url, @QueryMap Map<String, String> maps);




   /* @GET()
    Call<String> get(@Url String url, @QueryMap Map<String, String> params, @Header("Cache-Time") String time);

    @GET()
    Call<String> getArray(@Url String url, @QueryMap Map<String, String> params, @Header("Cache-Time") String time);

    @FormUrlEncoded
    @POST()
    Call<String> post(@Url String url, @FieldMap Map<String, String> params, @Header("Cache-Time") String time);

    @FormUrlEncoded
    @POST()
    Call<String> postArray(@Url String url, @FieldMap Map<String, String> params, @Header("Cache-Time") String time);*/

/*

    Observable<String> Obget(@Url String url, @QueryMap Map<String, String> params, @Header("Cache-Time") String time);

    @GET()
    Observable<jsonarray> ObgetArray(@Url String url, @QueryMap Map<String, String> params, @Header("Cache-Time") String time);

    @FormUrlEncoded
    @POST()
    Observable<String> Obpost(@Url String url, @FieldMap Map<String, String> params, @Header("Cache-Time") String time);

    @FormUrlEncoded
    @POST()
    Observable<jsonarray> ObpostArray(@Url String url, @FieldMap Map<String, String> params, @Header("Cache-Time") String time);*/

    //post json:  http://blog.csdn.net/qqyanjiang/article/details/50948908








}
