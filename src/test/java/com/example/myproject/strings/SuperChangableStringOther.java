package com.example.myproject.strings;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class SuperChangableStringOther {
	
	//这个失败了
	
	
	public static void main(String[] args) {
		
		StringBuilder sb = new StringBuilder("hello");
//		CharSequence fakeSb = makeFakeSb(sb);
		BookFacadeCglib cglib=new BookFacadeCglib();  
		Object fakeSb = cglib.getInstance(sb);
		String fakeStr = fakeSb.toString();
		System.out.println(fakeStr);
		
//		sb.setCharAt(0, '_');
//		System.out.println(fakeStr);
		
	}
	
	
	
	public static class BookFacadeCglib implements MethodInterceptor {  
	    private Object target;  
	  
	    /** 
	     * 创建代理对象 
	     *  
	     * @param target 
	     * @return 
	     */  
	    public Object getInstance(Object target) {  
	        this.target = target;  
	        Enhancer enhancer = new Enhancer();  
	        enhancer.setSuperclass(this.target.getClass().getSuperclass());  
	        // 回调方法  
	        enhancer.setCallback(this);  
	        enhancer.setInterceptDuringConstruction(false);
	        // 创建代理对象  
	        Class<?>[] c = {int.class};
	        Object[] o = {16};
	        return enhancer.create(c, o);  
	    }
	  
	    @Override  
	    // 回调方法  
	    public Object intercept(Object obj, Method method, Object[] args,  
	            MethodProxy proxy) throws Throwable {  
	        System.out.println("事物开始");  
	        proxy.invokeSuper(obj, args);  
	        System.out.println("事物结束");  
	        return null;  
	  
	  
	    }  
	  
	}  
	
	
	
	
	
	
	
	
	
	
	
	public static class ProxyHandler implements InvocationHandler {
		private Object proxied;

		public ProxyHandler(Object proxied) {
			this.proxied = proxied;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// 在转调具体目标对象之前，可以执行一些功能处理

			//如果是toString，则改了他
			if("toString".equals(method.getName())) {
				proxied.toString();
				char[] chars = getCharArrayFromSb((StringBuilder)proxied);
				String fakeStr = getStringFromCharArray(chars);
				return fakeStr;
			}
			
			//转调具体目标对象的方法
			//其他就直接返回
			return method.invoke(proxied, args);
			// 在转调具体目标对象之后，可以执行一些功能处理
		}
	}
	
	public static char[] getCharArrayFromSb(StringBuilder sb) {
		Field valueFieldOfString;
		try {
			valueFieldOfString = StringBuilder.class.getSuperclass().getDeclaredField("value");
			// 改变value属性的访问权限
			valueFieldOfString.setAccessible(true);
			// 获取s对象上的value属性的值
			char[] value = (char[]) valueFieldOfString.get(sb);
			// 改变value所引用的数组中的第5个字符
			return value;
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getStringFromCharArray(char[] chars) {
		if (chars == null) {
			return new String();
		} try {
			//强制调用这个方法
			Constructor<String> c = String.class.getDeclaredConstructor(char[].class, boolean.class);
			c.setAccessible(true);
			return c.newInstance(chars, true);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String();
	}
	
	public static CharSequence makeFakeSb(StringBuilder sb) {
		InvocationHandler ih = new ProxyHandler(sb);//代理实例的调用处理程序。  
		//创建一个实现业务接口的代理类,用于访问业务类(见代理模式)。  
        //返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理程序，如ProxyHandler。 
		CharSequence fakeSb =  
	             (CharSequence)Proxy.newProxyInstance(
	            		 sb.getClass().getClassLoader(),
	            		 sb.getClass().getInterfaces(), ih); 
		return fakeSb;
	}
	
	
}
