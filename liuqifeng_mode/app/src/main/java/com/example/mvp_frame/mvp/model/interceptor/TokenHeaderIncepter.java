package com.example.mvp_frame.mvp.model.interceptor;


import android.content.SharedPreferences;
import android.util.Log;

import com.example.mvp_frame.app.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenHeaderIncepter implements Interceptor {
    private SharedPreferences mSp;

    public TokenHeaderIncepter() {
        mSp = App.daggerAppComponent().provideSp();
    }

    /**
     * 统一token的封装拦截器
     * 思路：
     * 首先请求登录，然后获取token，保存到SP中，
     * 以后每次请求在请求头里面都需要携带token进行请求
     * 如果token过期需要重新请求刷新token,如果没过期就个token放在拦截器里面进行网络请求
     * <p>
     * <p>
     * 当前开发的 App 遇到一个问题：
     * 当请求某个接口时，由于 token 已经失效，所以接口会报错。
     * 但是产品经理希望 app 能够马上刷新 token ，然后重复请求刚才那个接口，这个过程对用户来说是无感的。
     * 请求 A 接口－》服务器返回 token 过期－》请求 token 刷新接口－》请求 A 接口
     *
     * @param chain
     * @return
     * @throws IOException
     */


    @Override
    public Response intercept(final Chain chain) throws IOException {
        // 判断服务器返回的token是否过期
        Request request = chain.request();  //request  有请求头  有请求体  有请求报文
        Response response = chain.proceed(request); // response 有响应报文 响应头 响应体
        Log.e("TAG", "token====00===");
        //如果token过期了 需要重新获取并刷新token 需要重新刷新token，然后将新的token设置给request对象得到response对象返回
        if (isTokenExpire(response)) {
            Log.e("TAG", "token过期=======");
          // LoginManager.refreshToken();
            String newToken =mSp.getString("token", "");
            Request request1 = chain.request().newBuilder().addHeader("token", newToken).build();
            return chain.proceed(request1);
        } else {
            Log.e("TAG", "token没过期=======");
            //如果token没过期(需要拿到原先没过期的token，然后设置给request对象得到response对象返回)
            String originalToken = mSp.getString("token", "");
            Request request2 = request.newBuilder().addHeader("token", originalToken).build();
            return chain.proceed(request2);
        }

    }


    private boolean isTokenExpire(Response response) {
        if (response.code() == 402) {
            return true;
        }
        return false;
    }
}
