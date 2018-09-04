package com.classcheck.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.classcheck.mapper.SignMapper;
import com.classcheck.mapper.StuMapper;
import com.classcheck.model.Sign;
import com.classcheck.model.SignItem;
import com.classcheck.model.Stu;
import com.classcheck.service.math.MathUtil;
import com.classcheck.service.time.TimeUtil;
import com.classcheck.service.time.TimeUtilImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Auther: 简单DI年华
 * @Date: 18-8-28 00:46
 * @Description:
 */
@ServerEndpoint(value = "/websocket")
@Component

public class MyWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    private static TimeUtilImpl time =  new TimeUtilImpl();

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;


    boolean flag = true;

    static SignMapper  signMapper;
    static StuMapper stuMapper;
    static MathUtil mathUtil;

    @Autowired
    public MyWebSocket(SignMapper signMapper, StuMapper stuMapper, MathUtil mathUtil){

        this.signMapper = signMapper;
        this.mathUtil= mathUtil;
        this.stuMapper = stuMapper;
    }

    public MyWebSocket(){}


    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
        try {
            sendMessage("有新连接加入！当前在线人数为" + getOnlineCount());


        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }


    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        flag = !flag;
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);

        //群发消息
        for (MyWebSocket item : webSocketSet) {
           while (flag ){
               try {
//                   item.sendMessage(message);
                   sendMessage(JSON.toJSONString(getData(message)));
                   Thread.sleep(1000);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
        }
    }


    public List getData(String message){
        List<Sign> list =  signMapper.getsignbyid(message);
        Sign sign = list.get(0);
        JSONArray json= JSON.parseArray(sign.getStulist());
        List data = new ArrayList();
        for (int i = 0; i < json.size(); i++) {
            Map<String, String> item = new HashMap();
//            System.out.println(json.get(i));
            Stu studata = stuMapper.findnamebyuserid(JSON.parseObject((String)(json.get(i))).get("id").toString()).get(0);
            item.put("name", studata.getName());
            item.put("stuid",studata.getStuid() );
            item.put("time",JSON.parseObject((String)(json.get(i))).get("signtime").toString() );
            item.put("signstate",JSON.parseObject((String)(json.get(i))).get("signstate").toString());
            item.put("signid", sign.getSignid().toString());
//            double longitude1 = Double.parseDouble(JSON.parseObject((String)(json.get(i))).get("longitude").toString());
//            double latitude1 =  Double.parseDouble(JSON.parseObject((String)(json.get(i))).get("latitude").toString());
//            double longitude2 =  Double.parseDouble(sign.getLongitude());
//            double latitude2 =  Double.parseDouble(sign.getLatitude());
//            item.put("distance", mathUtil.getDistangce(longitude1,latitude1,longitude2,latitude2)+"");
            data.add(item);
        }
        return data;
    }
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }


    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}
