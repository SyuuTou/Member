package cn.mldn.zd.util;

import java.util.ResourceBundle;

public class MessageResource {
	private ResourceBundle resourceBundle=null;
	
	public MessageResource(String baseName) {
		if(baseName.contains(".")) {
			this.resourceBundle=ResourceBundle.getBundle(baseName);
		}else {
			this.resourceBundle=ResourceBundle.getBundle("cn.mldn.zd.resources."+baseName);
		}
	}
	
	public String getMessage(String key) {
		try{
			return this.resourceBundle.getString(key);
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
