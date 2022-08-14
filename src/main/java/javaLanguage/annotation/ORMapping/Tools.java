package javaLanguage.annotation.ORMapping;

import java.lang.reflect.Field;

public class Tools {

    /**
     * 根据注解获取表名
     *
     * @param clazz
     * @return
     */
    public static String getTable(Class<?> clazz) {
        String tableName = "";
        Bean bean = clazz.getAnnotation(Bean.class);

        if (bean != null) {
            // 获取注解中的值
            tableName = bean.value();
        } else {
            // 如果为空，就直接获取类名
            tableName = clazz.getSimpleName();
        }

        return tableName;
    }

    /**
     * 根据注解获取属性名称
     *
     * @param field
     * @return
     */
    public static String getColume(Field field) {
        String colume = "";
        CustomField customFieldName = field.getAnnotation(CustomField.class);
        if (customFieldName != null) {
            colume = customFieldName.value();

        } else {
            colume = field.getName();
        }

        return colume;
    }

    /**
     * 根据字段获取对应的get方法
     *
     * @param field
     * @return
     */
    public static String getMethod(Field field) {

        String filedName = field.getName();

        String name = filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        return "get" + name;
    }



}
