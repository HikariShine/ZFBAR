package com.example.myproject.strings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {

	static Integer i = null;
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("b");
		String[] s = list.stream().distinct().toArray(String[]::new);
        if(list.size()==1){
            System.out.println(list.get(0).toString());
        }
        String ii = null;
        System.out.println(ii + 10);
	}
	
	public String listToString(List<?> list){

        if(list.size()==1){
            return list.get(0).toString();
        }
        //去除重复
        Object[] array = list.stream().distinct().toArray(Object[]::new);
        if(array.length==1){
            return array[0].toString();
        }

        StringBuilder sb = new StringBuilder();
        if(list!=null && list.size()>1){
            for(int i=0; i<list.size()-1; i++){
                if(list.get(i)==null){
                    continue;
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof Integer) {
                    sb.append(list.get(i));
                    sb.append("");
                }else if(list.get(i) instanceof List){
                    sb.append(listToString((List<?>) list.get(i)));
                    sb.append("");
                }
            }
            sb.append(list.get(list.size()-1));
        }
        return sb.toString();
    }

	static String ts(String i) {
		System.out.println(i);
		return i;
	}
	
	static void tts() {
		Stream<Integer> natural = Stream.generate(() -> {
	    	return i++;
    	});
	    
	    Object[] o = natural.map(x -> x * x).limit(10).toArray(Integer[]::new);
	    System.out.println(o);
	    String[] array = {"a", "b", "c"};
	    for (Object a : o) {
	    	System.out.println(a);
			Stream.of(array).map(item -> String.valueOf(a))
			.forEach(System.out::println);
		}
	}
	
}
