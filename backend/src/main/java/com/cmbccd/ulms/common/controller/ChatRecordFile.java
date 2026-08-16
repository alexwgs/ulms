package com.cmbccd.ulms.common.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmbccd.ulms.common.config.UlmsConfig;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;

/**
 *
* <p>Title: ChatRecordFile.java</p>
* <p>Description:求助系统聊天记录文件操作 </p>
* @author WeiGenSheng
* @date 2020年12月21日
* @version 1.0
 */
@Component
@RequestMapping("/record")
@Controller
public class ChatRecordFile {
    @Resource
    private UlmsConfig ulmsConfig;

	public static ChatRecordFile staticInit;

	@PostConstruct
	public void init() {
		staticInit = this;
	}

	Logger log= LoggerFactory.getLogger(ChatRecordFile.class);

	 /**
	  * 按日期查询聊天记录（大厅 default / 案件房间），按行解析记录文件并按日期过滤。
	  * 记录文件路径：{uploadPath}chatRecord/{yyyyMM}/{room}.txt，每行一条 JSON 消息。
	  * @param date 日期 yyyy-MM-dd
	  * @param room 房间名，默认 default（大厅）
	  */
	 @GetMapping("/list")
	 @ResponseBody
	 public Msg list(@RequestParam("date") String date,
	         @RequestParam(value = "room", defaultValue = "default") String room) {
	     // 安全校验：日期与房间名白名单格式，防路径穿越
	     if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
	         return Msg.error("日期格式不正确");
	     }
	     if (room == null || !room.matches("[\\w-]+")) {
	         return Msg.error("房间名不合法");
	     }
	     String month = date.substring(0, 4) + date.substring(5, 7);
	     String path = ulmsConfig.getUploadPath() + "chatRecord/" + month + "/" + room + ".txt";
	     File f = new File(path);
	     List<Map<String, Object>> list = new ArrayList<>();
	     if (!f.exists()) {
	         return Msg.success(list);
	     }
	     try (BufferedReader br = new BufferedReader(
	             new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
	         String line;
	         while ((line = br.readLine()) != null) {
	             if (line.trim().isEmpty()) {
	                 continue;
	             }
	             try {
	                 JSONObject obj = JSON.parseObject(line);
	                 JSONObject data = obj == null ? null : obj.getJSONObject("data");
	                 if (data == null) {
	                     continue;
	                 }
	                 String ctime = data.getString("ctime");
	                 if (ctime != null && ctime.startsWith(date)) {
	                     list.add(data);
	                 }
	             } catch (Exception ignored) {
	                 // 单行解析失败不影响其余记录
	             }
	         }
	     } catch (IOException e) {
	         log.error("读取聊天记录文件失败: {}", path, e);
	     }
	     return Msg.success(list);
	 }

	 public void writeChatRecordFile(String roomName, String msgJson)
	  {
	    FileWriter fw = null;

	    String path = this.ulmsConfig.getUploadPath() + "chatRecord/"+Util.getMonth()+"/";

        //文件夹不存在则新建
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

	    try
	    {
	      File f = new File(path + roomName + ".txt");
	      fw = new FileWriter(f, true);
	    }
	    catch (IOException e) {
	      log.error("创建聊天记录文件失败", e);
	      // 审计修复：文件创建失败直接返回，避免 new PrintWriter(null) NPE 冒泡到 WS 消息链
	      return;
	    }
	    PrintWriter pw = new PrintWriter(fw);
	    pw.println(msgJson);
	    pw.flush();
	    try {
	      if (fw != null) {
	          fw.flush();
	      }
	      pw.close();
	      if (fw != null) {
	          fw.close();
	      }
	    } catch (IOException e) {
	      log.error("关闭聊天记录文件流失败", e);
	    }
	  }

	  public static void deleteChatRecordFile(String roomName) {
	    String path = System.getProperty("user.dir");

	    File f = new File(path + "\\webapps\\chatRoom\\chatRecord\\" + roomName + ".txt");
	    if (f.exists()) {
	      f.delete();
	    }
	  }
}
