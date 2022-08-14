package javaLanguage.reflection;

import java.lang.reflect.Constructor;

/*
 * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员；
 *
 * 1.获取构造方法：
 * 		1).批量的方法：
 * 			public Constructor[] getConstructors()：所有"公有的"构造方法
            public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)

 * 		2).获取单个的方法，并调用：
 * 			public Constructor getConstructor(Class... parameterTypes):获取单个的"公有的"构造方法：
 * 			public Constructor getDeclaredConstructor(Class... parameterTypes):获取"某个构造方法"可以是私有的，或受保护、默认、公有；
 *
 * 			调用构造方法：
 * 			Constructor-->newInstance(Object... initargs)
 */

public class ReflectionGetConstructor {
    public static void main(String[] args) throws Exception{
        Class clazz = Class.forName("Students");

        System.out.println("-------get all public  constructor methods---------");
        Constructor[] consArray = clazz.getConstructors();
        for (Constructor c : consArray) {
            System.out.println(c);
        }

        System.out.println("------ get all constructor methods------------");
        consArray = clazz.getDeclaredConstructors();
        for (Constructor c : consArray) {
            System.out.println(c);
        }

        System.out.println("-------get all public ,no parameters constructor methods------------");
        Constructor cons = clazz.getConstructor(null);
        //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
        //2>、返回的是描述这个无参构造函数的类对象。
        System.out.println(cons);

        Object obj = cons.newInstance();
        System.out.println(obj);
        Students stu = (Students) obj;
        System.out.println(stu);

        System.out.println("---------------get  private method----");
        cons = clazz.getDeclaredConstructor(int.class);
        System.out.println(cons);
        //暴力访问
        cons.setAccessible(true);
        obj = cons.newInstance(15);
        System.out.println(obj);

    }
}
