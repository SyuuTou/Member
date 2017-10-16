package cn.mldn.zd.fatory;

import cn.mldn.zd.service.proxy.ServiceProxy;
import cn.mldn.zd.util.MessageResource;

public class Factory {
	private static final MessageResource SERVICE_RESOURCES=new MessageResource("service");
	private static final MessageResource DAO_RESOURCES=new MessageResource("dao");
	
	public static <T> T getInstance(String classKey) { //定义在资源文件里面的 dao & service 的key
		String suffix=classKey.substring(classKey.indexOf(".")+1);
		switch(suffix){
			case "dao":{
				return getIDAOInstance(classKey);
			}
			case "service":{
				return getISreviceInstance(classKey);
			}
			default : {
				return null;
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T getISreviceInstance(String classKey) {
		try {
			return (T) new ServiceProxy().bind(Class.forName(SERVICE_RESOURCES.getMessage(classKey)).newInstance());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	@SuppressWarnings("unchecked")
	private static <T> T getIDAOInstance(String classKey) {
		try {
			System.out.println("-----");
			return (T) Class.forName(DAO_RESOURCES.getMessage(classKey)).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
}
