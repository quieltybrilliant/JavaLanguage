package javaLanguage.reflection;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

public class RefectionGetFromProperties<getValue> {
    public static void main(String[] args) throws Exception {
        //通过反射获取Class对象
        Class stuClass = Class.forName(getValue("className"));
        //2获取show()方法
        Method m = stuClass.getMethod(getValue("methodName"),String.class);//show
        //3.调用show()方法
        m.invoke(stuClass.getConstructor().newInstance(),"zgy");

    }

    public static String getValue(String key) throws IOException {

        Properties pro = new Properties();
        FileReader in = new FileReader(RefectionGetFromProperties.class.getResource("read.properties").getPath());
        pro.load(in);
        in.close();
        return pro.getProperty(key);
    }

}
