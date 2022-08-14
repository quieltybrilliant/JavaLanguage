package javaLanguage.reflection;

import java.lang.reflect.Field;
/*
 * 获取成员变量并调用：
 *
 * 1.批量的
 * 		1).Field[] getFields():获取所有的"公有字段"
 * 		2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有；
 * 2.获取单个的：
 * 		1).public Field getField(String fieldName):获取某个"公有的"字段；
 * 		2).public Field getDeclaredField(String fieldName):获取某个字段(可以是私有的)
 *
 * 	 设置字段的值：
 * 		Field --> public void set(Object obj,Object value):
 * 					参数说明：
 * 					1.obj:要设置的字段所在的对象；
 * 					2.value:要为字段设置的值；
 */


public class ReflectionGetFiled {

    public static void main(String[] args) throws Exception {

        Class stuclass = Class.forName("Students");

        System.out.println("************获取所有公有的字段********************");
        Field[] fieldArray = stuclass.getFields();
        for (Field f : fieldArray) {
            System.out.println(f);
        }

        System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
        fieldArray = stuclass.getDeclaredFields();
        for (Field f : fieldArray) {
            System.out.println(f);
        }

        System.out.println("*************获取公有字段**并调用***********************************");
        Field f = stuclass.getField("name");
        System.out.println(f);

        // set name
        Object obj = stuclass.getConstructor().newInstance();
        f.set(obj,"zgy");

        //验证
        Students stu = (Students) obj;
        System.out.println("name:" + stu.name);

        System.out.println("**************获取私有字段****并调用********************************");
        f = stuclass.getDeclaredField("phoneNum");
        System.out.println(f);
        f.setAccessible(true);

        f.set(obj,"11111111");
        System.out.println("phone" + stu);

    }

}
