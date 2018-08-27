package com.classcheck.service;


import okhttp3.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-27 18:00
 * @Description:
 */
@Service
public class StuAuthenticationImpl implements StuAuthentication {
    @Override
    public Map<String, String> getStuData(String name,String password) {
        Map<String,String> map = new HashMap<>();
      try{
          OkHttpClient client = new OkHttpClient();
          RequestBody body = new FormBody.Builder()
                  .add("WebUserNO",name)
                  .add("Password",password)
                  .build();
          Request request = new Request.Builder()
                  .url("http://202.118.120.84:800/ACTIONLOGON.APPPROCESS")
                  .post(body)
                  .build();
          Response response = client.newCall(request).execute();
          Headers headers = response.headers();
          HttpUrl url = request.url();
          List<Cookie> cookies = Cookie.parseAll(url, headers);
          //获取需要提交的CookieStr
          StringBuilder cookieStr = new StringBuilder();
          //将Cookie数据弄成一行
          for(Cookie cookie : cookies){
              cookieStr.append(cookie.name()).append("=").append(cookie.value()+";");
          }

          Request request2 = new Request.Builder()
                  .url("http://202.118.120.84:800/ACTIONZZSHOW.APPPROCESS?op=101")
                  .header("Cookie", cookieStr.toString())
                  .build();
          response = client.newCall(request2).execute();
          String str = response.body().string();
          Pattern pattern = Pattern.compile("(学号:){1}.*(院系:){1}");
          Matcher matcher =  pattern.matcher(str);
          while (matcher.find()){
              System.out.println(matcher.group());
              map.put("stuid",matcher.group().substring(3,13));
              map.put("name",matcher.group().substring(28,matcher.group().length()-15));
          }

      }catch (Exception e){

      }
        return map;
    }
}
