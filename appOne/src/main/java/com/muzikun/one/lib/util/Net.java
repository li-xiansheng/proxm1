package com.muzikun.one.lib.util;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by muzikun on 2015-11-15.
 */
public class Net {


    /**
     * 功能：获取服务器时间
     *
     * 参数：String 服务器时间
     *
     * 返回：String 返回10位数的时间戳
     *
     * 说明：返回服务器的实时时间
     *
     * Leek
     * @return
     */
    public static String getHostTime(){
        String timeString;
        //timeString = getApiJson(Api.HOSTTIME);
        timeString = String.valueOf(System.currentTimeMillis());
        return timeString;
    }


/*功能：加载网络接口数据
*
* 参数：String 类型的 Api 地址
*
* 返回：String 类型的Json字符串
*
* 说明：获取给定API地址返回的网络JSON字符串返回给调用方
*
* 作者：李坤 2015-11-12
*
* */
    public static String getApiJson(String apiString) {
        String json = "";
        try {
            URL url = new URL(apiString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader buf = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line=buf.readLine())!=null){
                json += line;
            }
            buf.close();
        } catch (MalformedURLException e) {
            json="error";
            System.err.println("API初始化错误！");
            //e.printStackTrace();
        } catch (IOException e) {
            json = "error";
            System.err.println("网络初始化失败！");
            //如果没有网络数据
            //e.printStackTrace();
        }
        return json;
    }
    /**
     * 通过HttpURLConnection模拟post表单提交
     *
     * @param path
     * @param params 例如"name=zhangsan&age=21"
     * @return
     * @throws Exception
     */
    public static byte[] sendPostRequestByForm(String path, String params) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream=conn.getInputStream();
        return readInputStream(inStream);
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        outStream.close();
        inStream.close();
        return data;
    }

}
