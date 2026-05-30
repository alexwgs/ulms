package com.cmbccd.ulms.common.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmbccd.ulms.common.config.UlmsConfig;
import com.cmbccd.ulms.common.util.Util;

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

	 public void writeChatRecordFile(String roomName, String msgJson)
	  {
	    FileWriter fw = null;

	    String path = staticInit.ulmsConfig.getUploadPath() + "chatRecord/"+Util.getMonth()+"/";

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
