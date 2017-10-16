package cn.mldn.zd.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.mldn.zd.dbc.DatabaseConnection;

public class ServiceProxy implements InvocationHandler{
	private Object target;
	public Object bind(Object target) {
		this.target=target;
		return Proxy.newProxyInstance(this.target.getClass().getClassLoader(),this.target.getClass().getInterfaces(),this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		System.out.println(method.getName());
		Object resultBack=null;
		if(checkTransactionMethod(method.getName())) {
			DatabaseConnection.getConnection().setAutoCommit(false);
		}
		try {
			resultBack=method.invoke(this.target, args);
			if(checkTransactionMethod(method.getName())) {
				DatabaseConnection.getConnection().commit();
			}	
		}catch(Exception e) {
			if(checkTransactionMethod(method.getName())) {
				DatabaseConnection.getConnection().rollback();
			}
			throw e;
		}finally {
			DatabaseConnection.close();
		}
		return resultBack;
	}

	private boolean checkTransactionMethod(String methodName) {
		if(methodName.startsWith("add")||methodName.startsWith("edit")||methodName.startsWith("delete")) {
			return true;
		}
		return false;
	}

}
