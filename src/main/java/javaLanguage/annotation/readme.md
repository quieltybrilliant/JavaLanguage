### 注解

#### 1 常用注解

##### 1.1  jdk注解

~~~java
@Override    //覆盖父类的方法
@Deprecated  //过时
@Suppvisewarning   //忽略了deprecation的警告
~~~



##### 1.2  第三方注解

#### 2  注解分类

**1）按照运行机制划分：**
 [  源码注解→编译时注解→运行时注解  ]

源码注解：只在源码中存在，编译成.class文件就不存在了。

编译时注解：在源码和.class文件中都存在。像前面的@Override、@Deprecated、@SuppressWarnings，他们都属于编译时注解。

运行时注解：在运行阶段还起作用，甚至会影响运行逻辑的注解。像@Autowired自动注入的这样一种注解就属于运行时注解，它会在程序运行的时候把你的成员变量自动的注入进来。

**2）按照来源划分：**
 【来自JDK的注解——来自第三方的注解——自定义注解】

**3）元注解：**
 元注解是给注解进行注解，可以理解为注解的注解就是元注解。



#### 3  自定义注解

##### 3.1  语法要求

~~~java
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Description {
    String desc();
    String author();
    int age() default 18;
}
~~~

这不是一个接口，它是使用**@interface关键字**定义的一个注解

`String desc();`虽然它很类似于接口里面的方法，其实它在注解里面只是一个成员变量（成员以无参无异常的方式声明），`int age() default 18;`（成员变量可以用default指定一个默认值的）。
 最后我们要知道：
 ①.成员类型是受限制的，合法的类型包括基本的数据类型以及String，Class，Annotation, Enumeration等。
 ②.如果注解只有一个成员，则成员名必须取名为value()，在使用时可以忽略成员名和赋值号（=）。
 ③.注解类可以没有成员，没有成员的注解称为标识注解。

##### 3.2 元注解

@Target是这个注解的作用域，`ElementType.METHOD`是这个注解的作用域的列表，`METHOD`是方法声明，除此之外，还有：
 `CONSTRUCTOR（构造方法声明）,FIELD（字段声明）,LOCAL VARIABLE（局部变量声明）,METHOD（方法声明）,PACKAGE（包声明）,PARAMETER（参数声明）,TYPE（类接口）`

@Retention是它的生命周期，前面不是说注解按照运行机制有一个分类嘛，`RUNTIME`就是在运行时存在，可以通过反射读取。除此之外，还有:
 `SOURCE（只在源码显示，编译时丢弃）,CLASS（编译时记录到class中，运行时忽略）,RUNTIME（运行时存在，可以通过反射读取）`

@Inherited是一个标识性的元注解，它允许子注解继承它。

@Documented，生成javadoc时会包含注解。

##### 3.3  使用自定义注解

使用注解的语法：
@<注解名>(<成员名1>=<成员值1>,<成员名1>=<成员值1>,...)

~~~java
@Description(desc="i am Color",author="boy",age=18)
    public String Color() {
        return "red";
    }
~~~



