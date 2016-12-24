package com.example.myproject.strings;

import java.lang.reflect.Field;

public class ChangeString {

	public static void main(String[] args) {

		//str1与str2引用自常量池中同一个对象，内部char[]数组同样是引用类型
		String str1 = "hello";
		String str2 = "hello";
		//预定使用反射修改后字符串
		String newStr = "_ello";
		//看看hello的hash值
		System.out.println("hello's hash is " + str1.hashCode());
		//看看_ello的hash值
		System.out.println("_ello's hash is " + newStr.hashCode());
		//通过反射修改value数组为_ello
		String str3 = changeStringValue(str1);
		//看他们几个是否是同一引用
		System.out.println("str1 and str2 is the same instance?" + (str1 == str2));
		System.out.println("str2 and str3 is the same instance?" + (str2 == str3));
		//看看用反射修改之后的值
		System.out.println("change str1's value--" + str1);
		
		//对比两个显示相同字符串是否是同一对象
		System.out.println("the value same string is the same instance?" + (str1 == newStr));
		//通过equal方法比较
		System.out.println("is equal?" + str1.equals(newStr));
		
		//再看看hash值
		System.out.println("hello mod to _ello then hash is " + str1.hashCode());
		
		//把hash设置为0以便重新计算hash值
		rehash(str1);
		
		//再看看hash值
		System.out.println("hello mod to _ello and then rehash then hash is " + str1.hashCode());
		
		System.out.println("now two string object have same hashcode and same value but not the same instance");
		
		//再重新定义一个hello变量，看看jvm是否会查看常量池中hello常量的value改变，如果检测到改变的话这里应该会重新创建一个string对象
		String str4 = "hello";
		//打印出来是_ello，说明常量字符串引用同一个实例是在编译期做到的，之前好像看过这方面内容，编译时把符号常量放在固定的区域，再看看
		System.out.println("the new string 'hello'" + str4);
		
		System.out.println("haha, that's strange! i defined the str4 as hello, but print it is _ello");
		
		//使用new创建String实例。引用同一个value数组。
		String str5 = new String("hello");

		//new的String值是什么
		System.out.println("use new create string hello result is " + str5);
		
		//与str1等是否是同一个引用
		System.out.println("is one instance of str1?" + (str1 == str5));
		
		System.out.println("h" + "ello");

		String s1 = "h";
		
		String s2 = "ello";
		//StringBuilder的toString用自己的value构建了一个String对象
		System.out.println(s1 + s2);
		
	}

	public static String changeStringValue(String str) {
		Field valueFieldOfString;
		try {
			valueFieldOfString = String.class.getDeclaredField("value");
			// 改变value属性的访问权限
			valueFieldOfString.setAccessible(true);
			// 获取s对象上的value属性的值
			char[] value = (char[]) valueFieldOfString.get(str);
			// 改变value所引用的数组中的第5个字符
			value[0] = '_';
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	
	public static String rehash(String str) {
		Field valueFieldOfString;
		try {
			valueFieldOfString = String.class.getDeclaredField("hash");
			// 改变hash属性的访问权限
			valueFieldOfString.setAccessible(true);
			//设置为0以便重新计算hash值
			valueFieldOfString.set(str, 0);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

}
