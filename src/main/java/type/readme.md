Type是所有类型的父接口
1) 原始类型(raw types 对应 Class)
2) 参数化类型(parameterized types 对应 ParameterizedType) 
3) 数组类型(array types 对应 GenericArrayType)
4) 类型变量(type variables 对应 TypeVariable )
5) 基本(原生)类型(primitive types 对应 Class)

子接口
1) ParameterizedType
   比如 Collection<String>
2) TypeVariable
   比如 K, V 都是属于类型变量。
3) GenericArrayType
   范型数组,组成数组的元素中有范型则实现了该接口; 
   它的组成元素是 ParameterizedType 或 TypeVariable 类型
4) WildcardType
   {@code ?}
   {@code ? extends Number} 
   {@code ? super Integer}
   extends 用来指定上边界，没有指定的话上边界默认是 Object， super 用来指定下边界，没有指定的话为 null

实现类
1)Class

**ParameterizedType**
1) Map<String, Person> map
2) Set<String> set1
3) Class<?> clz
4) Holder<String> holder
5) List<String> list
6) static class Holder<V>{}


**ParameterizedType主要方法**
1) Type[] getActualTypeArguments()
   返回这个Type类型的参数的实际类型数组。
2) Type getRawType()
   返回的是当前这个 ParameterizedType 的类型
3) Type getOwnerType()
   返回的是这个ParameterizedType所在的类的Type

