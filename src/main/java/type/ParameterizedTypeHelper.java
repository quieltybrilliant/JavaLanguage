package type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeHelper {
    public static void main(String[] args) {
        testParameterizedType();
    }

    public static void testParameterizedType() {
        Field f = null;
        try {
            /**
             * getFields()：获得某个类的所有的公共（public）的字段，包括父类中的字段。
             * getDeclaredFields()：获得某个类的所有声明的字段，即包括public、private和proteced，但是不包括父类的申明字段
             */
            Field[] fields = ParameterizedTypeBean.class.getDeclaredFields();
            // 打印出所有的 Field 的 TYpe 是否属于 ParameterizedType
            for (int i = 0; i < fields.length; i++) {
                f = fields[i];
                /**
                 * getType()：返回一个Class对象，它标识了此Field对象所表示字段的声明类型
                 * getGenericType()：返回一个Type对象，它表示此Field对象所表示字段的声明类型
                 */
                System.out.println((f.getName() + " getGenericType() instanceof ParameterizedType "
                        + (f.getGenericType() instanceof ParameterizedType)));
            }
            getParameterizedTypeMes("map");  // 所在类为null
            getParameterizedTypeMes("entry"); // 所在类为Map


        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    /**
     * 1) Type[] getActualTypeArguments()
     * 返回这个Type类型的参数的实际类型数组。
     * 2) Type getRawType()
     * 返回的是当前这个ParameterizedType的类型
     * 3) Type getOwnerType()
     * 返回的是这个ParameterizedType所在的类的Type
     */
    private static void getParameterizedTypeMes(String fieldName) throws NoSuchFieldException {
        Field f;
        f = ParameterizedTypeBean.class.getDeclaredField(fieldName);
        f.setAccessible(true);
        System.out.println(fieldName + "的getGenericType为： " + f.getGenericType());
        boolean b = f.getGenericType() instanceof ParameterizedType;
        System.out.println(b);
        if (b) {
            ParameterizedType pType = (ParameterizedType) f.getGenericType();
            System.out.println(fieldName + "的getRawType() :" + pType.getRawType());
            for (Type type : pType.getActualTypeArguments()) {
                System.out.println(fieldName + "的参数getActualTypeArguments()：" + type);
            }
            System.out.println(fieldName + "的getOwnerType(): " + pType.getOwnerType()); // null
        }
    }
}