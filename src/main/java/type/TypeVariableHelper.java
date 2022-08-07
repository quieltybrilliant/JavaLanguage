package type;

import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;

/**
 * Type[] getBounds()
 *      得到上边界的 Type数组，如 TypeVariableBean:
 *      K的上边界数组是 InputStream 和 Serializable。
 *      V没有指定，上边界是 Object
 * D getGenericDeclaration();
 *      返回的是声明这个Type所在的类的Type
 * String getName()
 *      返回的是这个 type variable 的名称
 */
public class TypeVariableHelper {
    public static void main(String[] args) throws NoSuchFieldException {
        Field fk = TypeVariableBean.class.getDeclaredField("value");
        TypeVariable keyType = (TypeVariable) fk.getGenericType();
        System.out.println(keyType.getBounds());
        System.out.println(keyType.getName());
        System.out.println(keyType.getGenericDeclaration());

    }
}
