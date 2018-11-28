package com.wslint.wisdomreport.adpter.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.wslint.wisdomreport.adpter.IRCGServerAdpater;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.springframework.stereotype.Service;

@Service
public class RCGServerAdpaterImpl implements IRCGServerAdpater {
  private Gson gson;
  private Socket socket;
  private String imageServerIp;
  private int imageServerPort;
  private String imageFilterKey;

  public RCGServerAdpaterImpl() {
    gson = new Gson();
    Properties properties = new Properties();
    try {

      InputStream stream =
          this.getClass().getClassLoader().getResourceAsStream("adapter/imageEngion.properties");
      properties.load(stream);
      stream.close();

      imageServerIp = properties.getProperty("imageEngionServerIP");
      imageServerPort = Integer.parseInt(properties.getProperty("imageEngionServerPort"));
      imageFilterKey = properties.getProperty("imageFilterKey");
      System.out.println("IP:" + imageServerIp);
      System.out.println("port:" + imageServerPort + "");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private String sendMsg(String infoStr) {
    String result = "";
    try {
      socket = new Socket(imageServerIp, imageServerPort);
      PrintWriter out = new PrintWriter(socket.getOutputStream()); // 输出，to 服务器 socket
      java.io.InputStream inStream = socket.getInputStream();
      DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      String infoString = infoStr;
      byte[] sendBytes = new byte[1032];
      byte[] infoBytes = infoString.getBytes();
      for (int i = 0; i < infoBytes.length; i++) {
        sendBytes[i] = infoBytes[i];
      }
      dos.write(sendBytes, 0, sendBytes.length);
      byte buffer[] = new byte[1024];
      int len = 0;
      while ((len = inStream.read(buffer)) > 0) {
        result += new String(buffer);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public Map<String, Object> recognitionImage(String imageInfo, String filePath) {
    // 获取文件名
    filePath = "\"" + filePath + "\"";
    imageInfo =
        imageInfo.substring(0, imageInfo.length() - 1) + "," + ("\"filePath\":" + filePath) + "}";
    String result = sendMsg(imageInfo);
    StringReader json = new StringReader(result);
    JsonReader jsonReader = new JsonReader(json);

    Map<String, Object> map =
        gson.fromJson(jsonReader, new TypeToken<HashMap<String, Object>>() {}.getType());
    return map;
  }

  public String getImageFilterKey() {
    return imageFilterKey;
  }
}
