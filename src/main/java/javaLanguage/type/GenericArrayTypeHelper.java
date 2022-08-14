package javaLanguage.type;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * extends 用来指定上边界，没有指定的话上边界默认是 Object
 * super 用来指定下边界，没有指定的话为 null。
 */
public class GenericArrayTypeHelper {
    public static void main(String[] args) {
        Method method = GenericArrayTypeBean.class.getDeclaredMethods()[0];
        System.out.println(method);
        // public void test(List<String>[] pTypeArray, T[]
        // vTypeArray,List<String> list, String[] strings, Person[] ints)
        Type[] types = method.getGenericParameterTypes(); // 这是 Method 中的方法
        for (Type type : types) {
            System.out.println(type instanceof GenericArrayType);// 依次输出true，true，false，false，false
        }
    }
}
