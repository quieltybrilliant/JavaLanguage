public class test {
    public static void main(String[] args) {
        //第一个测试： 字符串常量池指向str1
        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        String strn = args[0];
        System.out.println(args[0]);
        System.out.println(str1 == strn.intern());
        System.out.println(str1 == strn);

        System.out.println(str1 == str2);
        System.out.println(str1 == "abc");
        System.out.println("abc" == strn);
        String[] strn1 = {"abc", "dddd"};
        System.out.println(strn1[0] == strn.intern());
        System.out.println(strn1[0] == "abc");

        System.out.println("--------------");


        System.out.println(str1 == str2.intern()); //true
        System.out.println(str1 == str3.intern()); //true
        System.out.println(str3 == str3.intern()); //false

//第二个测试： 字符串常量池指向str5
        String str4 = new String(new char[]{'e', 'f'});
        String str5 = "ef";
        System.out.println(str4.intern() == str4); //false
        System.out.println(str4.intern() == str5); //true

//第三个测试： 字符串常量池指向str6
        String str6 = new String(new char[]{'g', 'h'});
        str6.intern();
        String str7 = "gh";
        System.out.println(str6.intern() == str6); //true
        System.out.println(str6.intern() == str7); //true

/**
 * 第四个测试：toString使用第三种方式实现 (char)
 * 1) str8 == str8.intern()
 * 2) str9中因为"java"在系统初始化将会创建，所以字符串常量存储的是常量池中的java引用
 */
        String str8 = new StringBuilder("计算机").append("软件").toString();
        String str9 = new StringBuilder("ja").append("va").toString();
        String str10 = "java";
        String str11 = "计算机软件";
        System.out.println(str8 == str8.intern());  //true
        System.out.println(str9 == str9.intern()); //false
        System.out.println(str10 == str9.intern()); //true
        System.out.println(str11 == str8.intern()); //true
    }
}
