package javaLanguage.type;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ParameterizedTypeBean {
    // 如下field的Type属于ParameterizedType
    Map<String, Long> map;
    Set<String> set1;
    Class<?> clz;
    Holder<String> holder;
    List<String> list;
    // Map<String,Long> map ,这个ParameterizedType的getOwnerType()为null
    // Map.Entry<String, String> entry 的getOwnerType()为Map所属于的Type。
    Map.Entry<String, String> entry;
    // 如下的field的Type不属于ParameterizedType
    String str;
    Integer i;
    Set set;
    List aList;

    static class Holder<V> {
    }
}

