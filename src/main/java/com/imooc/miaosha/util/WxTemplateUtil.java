package com.imooc.miaosha.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;



public class WxTemplateUtil {
	
	
	/**
	 * 发奖
	 */
	public static JSONObject send_award(String openid,String templateId,String token,String award,String activityName,String title,String desc){
		JSONObject json = new JSONObject();
		WxTemplate t = new WxTemplate();
		t.setTouser(openid);
        t.setTopcolor("#000000");
        t.setTemplate_id(templateId);
        Map<String,TemplateData> m = new HashMap<String,TemplateData>();  
        
        TemplateData first = new TemplateData();  
        first.setColor("#EAC100");  
        first.setValue(title+"\n");  
        m.put("first", first);  
       
        TemplateData keyword1 = new TemplateData();  
        keyword1.setColor("#000000");  
        keyword1.setValue(activityName+"\n");  
        m.put("keyword1", keyword1);  
      
        TemplateData keyword2 = new TemplateData();  
        keyword2.setColor("#000000");  
        keyword2.setValue(award+"\n");  
        m.put("keyword2", keyword2);  
        
        TemplateData keyword3 = new TemplateData();  
        keyword3.setColor("#000000");  
        keyword3.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\n");  
        m.put("keyword3", keyword3);  
        
        
        TemplateData remark = new TemplateData();  
        remark.setColor("#EAC100");  
        remark.setValue("\n"+desc);  
        m.put("remark", remark);  
        t.setData(m);
        String result = HttpUtils.postJsonRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+token, json.fromObject(t).toString());
		JSONObject results = JSONObject.fromObject(result);
        return results;
	}
}
