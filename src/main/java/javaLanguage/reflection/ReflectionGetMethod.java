package javaLanguage.reflection;


import java.lang.reflect.Method;

/*
 * 获取成员方法并调用：
 *
 * 1.批量的：
 * 		public Method[] getMethods():获取所有"公有方法"；（包含了父类的方法也包含Object类）
 * 		public Method[] getDeclaredMethods():获取所有的成员方法，包括私有的(不包括继承的)
 * 2.获取单个的：
 * 		public Method getMethod(String name,Class<?>... parameterTypes):
 * 					参数：
 * 						name : 方法名；
 * 						Class ... : 形参的Class类型对象
 * 		public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
 *
 * 	 调用方法：
 * 		Method --> public Object invoke(Object obj,Object... args):
 * 					参数说明：
 * 					obj : 要调用方法的对象；
 * 					args:调用方式时所传递的实参；
):
 */
public class ReflectionGetMethod {
    public static void main(String[] args) throws Exception{
        Class stuClass = Class.forName("Students");
        System.out.println("***************获取所有的”公有“方法*******************");
        Method[] methodsArray = stuClass.getMethods();

        for (Method m : methodsArray) {
            System.out.println(m);
        }

        System.out.println("***************获取公有的show1()方法*******************");
        Method m = stuClass.getMethod("show1", String.class);
        System.out.println(m);

        Object obj = stuClass.getConstructor().newInstance();
        m.invoke(obj, "zgy");

        System.out.println("***************获取公有的show4()方法*******************");
        m = stuClass.getDeclaredMethod("show4", int.class);
        System.out.println(m);
        m.setAccessible(true);
        Object result = m.invoke(obj, 20);
        System.out.println("返回值：" + result);


    }
}
