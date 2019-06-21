package com.xy.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xy.taotao.common.utils.JsonUtils;
import com.xy.taotao.utils.FastDFSClient;

@Controller
public class UploadController {

	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping("/pic/upload")
	@ResponseBody
	public String upload(MultipartFile uploadFile) {
		try {
			FastDFSClient client = new FastDFSClient("classpath:resource/client.conf");
			String name = uploadFile.getOriginalFilename();
			String low = name.substring(name.lastIndexOf(".") + 1);
			String url = client.uploadFile(uploadFile.getBytes(), low);
			url = IMAGE_SERVER_URL + url;
			Map map = new HashMap<>();
			map.put("error", 0);
			map.put("url", url);
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map map = new HashMap<>();
			map.put("error", 1);
			map.put("message","上传失败");
			return JsonUtils.objectToJson(map);
		}
	}

}
