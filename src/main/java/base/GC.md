

### 垃圾回收

垃圾回收（Garbage Collection，GC），顾名思义就是释放垃圾占用的空间，防止内存泄露。有效的使用可以使用的内存，对内存堆中已经死亡的或者长时间没有使用的对象进行清除和回收。

#### 1  垃圾定义

##### 1.1.  引用计数算法

引用计数算法（Reachability Counting）是通过在对象头中分配一个空间来保存该对象被引用的次数（Reference Count）。如果该对象被其它对象引用，则它的引用计数加1，如果删除对该对象的引用，那么它的引用计数就减1，当该对象的引用计数为0时，那么该对象就会被回收。

引用计数的垃圾收集不属于严格意义上的"Stop-The-World"的垃圾收集机制。

##### 1.2  可达性分析算法

可达性分析算法（Reachability Analysis）的基本思路是，通过一些被称为引用链（GC Roots）的对象作为起点，从这些节点开始向下搜索，搜索走过的路径被称为（Reference Chain)，当一个对象到 GC Roots 没有任何引用链相连时（即从 GC Roots 节点到该节点不可达），则证 明该对象是不可用的。

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/%E5%8F%AF%E8%BE%BE%E6%80%A7%E5%88%86%E6%9E%90%E7%AE%97%E6%B3%95.png)

#### 2 Java内存区域

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/d439b6003af33a87f1ef2c76c6273c3d5243b55f.jpeg)

在 Java 语言中，可作为 GC Root 的对象包括以下4种：

虚拟机栈（栈帧中的本地变量表）中引用的对象

方法区中类静态属性引用的对象

方法区中常量引用的对象

本地方法栈中 JNI（即一般说的 Native 方法）引用的对象

##### 2.1  虚拟机栈（栈帧中的本地变量表）中引用的对象

~~~java
public class test {
    public static void testGC() {
        StackLocalParameter s = new StackLocalParameter("localParameter");
        s = null;
    }
}

class StackLocalParameter {
    public StackLocalParameter(String name) {}
}
~~~

此时的 s，即为 GC Root，当s置空时，localParameter 对象也断掉了与 GC Root 的引用链，将被回收。



##### 2.2  方法区中类静态属性引用的对象

~~~java
public class MethodAreaStaicProperties {
    public static MethodAreaStaicProperties m;

    public MethodAreaStaicProperties(String name) {
    }

    public static void testGC() {
        MethodAreaStaicProperties s = new MethodAreaStaicProperties("properties");
        s.m = new MethodAreaStaicProperties("parameter");
        s = null;
    }
}
~~~

s 为 GC Root，s 置为 null，经过 GC 后，s 所指向的 properties 对象由于无法与 GC Root 建立关系被回收。而 m 作为类的静态属性，也属于 GC Root，parameter 对象依然与 GC root 建立着连接，所以此时 parameter 对象并不会被回收。

##### 2.3  方法区中常量引用的对象

~~~java
public class MethodAreaStaicProperties {
    public static final MethodAreaStaicProperties m = new MethodAreaStaicProperties("");

    public MethodAreaStaicProperties(String name) {
    } 

    public static void testGC() {
        MethodAreaStaicProperties s = new MethodAreaStaicProperties("staticProperties");
        s = null;
    }
}
~~~

m 即为方法区中的常量引用，也为 GC Root，s 置为 null 后，final 对象也不会因没有与 GC Root 建立联系而被回收。



##### 2.4  本地方法栈中引用的对象

任何 Native 接口都会使用某种本地方法栈，实现的本地方法接口是使用 C 连接模型的话，那么它的本地方法栈就是 C 栈。当线程调用 Java 方法时，虚拟机会创建一个新的栈帧并压入 Java 栈。然而当它调用的是本地方法时，虚拟机会保持 Java 栈不变，不再在线程的 Java 栈中压入新的帧，虚拟机只是简单地动态连接并直接调用指定的本地方法。

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/java%E6%96%B9%E6%B3%95%E5%92%8C%E6%9C%AC%E5%9C%B0%E6%96%B9%E6%B3%95.png)

#### 3  回收垃圾

##### 3.1  标记 --- 清除算法

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/%E6%A0%87%E8%AE%B0%E6%B8%85%E9%99%A4%E7%AE%97%E6%B3%95.png)

标记清除算法（Mark-Sweep）是最基础的一种垃圾回收算法，它分为2部分，先把内存区域中的这些对象进行标记，哪些属于可回收标记出来，然后把这些垃圾拎出来清理掉。就像上图一样，清理掉的垃圾就变成未使用的内存区域，等待被再次使用。它存在一个很大的问题，那就是内存碎片。

##### 3.2  复制算法

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/%E5%A4%8D%E5%88%B6%E7%AE%97%E6%B3%95.png)

复制算法（Copying）是在标记清除算法上演化而来，解决标记清除算法的内存碎片问题。它将可用内存按容量划分为大小相等的两块，每次只使用其中的一块。当这一块的内存用完了，就将还存活着的对象复制到另外一块上面，然后再把已使用过的内存空间一次清理掉。保证了内存的连续可用，内存分配时也就不用考虑内存碎片等复杂情况，逻辑清晰，运行高效。

##### 3.3  标记整理算法

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/%E6%A0%87%E8%AE%B0%E6%95%B4%E7%90%86%E7%AE%97%E6%B3%95.png)

标记整理算法（Mark-Compact）标记过程仍然与标记 --- 清除算法一样，但后续步骤不是直接对可回收对象进行清理，而是让所有存活的对象都向一端移动，再清理掉端边界以外的内存区域。



分代收集算法分代收集算法（Generational Collection）严格来说并不是一种思想或理论，而是融合上述3种基础的算法思想，而产生的针对不同情况所采用不同算法的一套组合拳。对象存活周期的不同将内存划分为几块。一般是把 Java 堆分为新生代和老年代，这样就可以根据各个年代的特点采用最适当的收集算法。在新生代中，每次垃圾收集时都发现有大批对象死去，只有少量存活，那就选用复制算法，只需要付出少量存活对象的复制成本就可以完成收集。而老年代中因为对象存活率高、没有额外空间对它进行分配担保，就必须使用标记-清理或者标记 --- 整理算法来进行回收。

##### 3.4 内存模型与回收策略

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/java%E5%86%85%E5%AD%98%E6%A8%A1%E5%9E%8B.png)

Java 堆（Java Heap）是JVM所管理的内存中最大的一块，堆又是垃圾收集器管理的主要区域.

Java 堆主要分为2个区域-年轻代与老年代，其中年轻代又分 Eden 区和 Survivor 区，其中 Survivor 区又分 From 和 To 2个区。

**Eden 区**

IBM 公司的专业研究表明，有将近98%的对象是朝生夕死，所以针对这一现状，大多数情况下，对象会在新生代 Eden 区中进行分配，当 Eden 区没有足够空间进行分配时，虚拟机会发起一次 Minor GC，Minor GC 相比 Major GC 更频繁，回收速度也更快。

通过 Minor GC 之后，Eden 会被清空，Eden 区中绝大部分对象会被回收，而那些无需回收的存活对象，将会进到 Survivor 的 From 区（若 From 区不够，则直接进入 Old 区）。

**Survivor 区**

Survivor 区相当于是 Eden 区和 Old 区的一个缓冲，类似于我们交通灯中的黄灯。Survivor 又分为2个区，一个是 From 区，一个是 To 区。每次执行 Minor GC，会将 Eden 区和 From 存活的对象放到 Survivor 的 To 区（如果 To 区不够，则直接进入 Old 区）。在发生一次Minor GC后，from区就会和to区互换。

Survivor 的存在意义就是减少被送到老年代的对象，进而减少 Major GC 的发生。Survivor 的预筛选保证，只有经历16次 Minor GC 还能在新生代中存活的对象，才会被送到老年代。

**Old 区**

老年代占据着2/3的堆内存空间，只有在 Major GC 的时候才会进行清理，每次 GC 都会触发“Stop-The-World”。内存越大，STW 的时间也越长，所以内存也不仅仅是越大就越好。由于复制算法在对象存活率较高的老年代会进行很多次的复制操作，效率很低，所以老年代这里采用的是标记 --- 整理算法。

在内存担保机制下，无法安置的对象会直接进到老年代，以下几种情况也会进入老年代。

-  大对象

大对象指需要大量连续内存空间的对象，这部分对象不管是不是“朝生夕死”，都会直接进到老年代。这样做主要是为了避免在 Eden 区及2个 Survivor 区之间发生大量的内存复制。需要减少大对象的使用

- 长期存活对象

虚拟机给每个对象定义了一个对象年龄（Age）计数器。正常情况下对象会不断的在 Survivor 的 From 区与 To 区之间移动，对象在 Survivor 区中每经历一次 Minor GC，年龄就增加1岁。当年龄增加到15岁时，这时候就会被转移到老年代。当然，这里的15，JVM 也支持进行特殊设置。

- 动态对象年龄

虚拟机并不重视要求对象年龄必须到15岁，才会放入老年区，如果 Survivor 空间中相同年龄所有对象大小的总合大于 Survivor 空间的一半，年龄大于等于该年龄的对象就可以直接进去老年区，无需等你“成年”。

#### 4  垃圾收集器

https://www.cnblogs.com/cxxjohnson/p/8625713.html

https://www.cnblogs.com/swordfall/p/10734403.html

垃圾收集器就是内存回收的具体实现

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/java%E5%9E%83%E5%9C%BE%E6%94%B6%E9%9B%86%E5%99%A8.png)





##### 4.1  并发垃圾收集和并行垃圾收集的区别

​    并发是指一个处理器同时处理多个任务。 
​    并行是指多个处理器或者是多核的处理器同时处理多个不同的任务。 
​    并发是逻辑上的同时发生（simultaneous），而并行是物理上的同时发生。 

> （A）、并行（Parallel）
>
> ​    如ParNew、Parallel Scavenge、Parallel Old；
>
> （B）、并发（Concurrent）
>
> ​    如CMS、G1（也有并行）；



##### 4.2  Minor GC和Full GC的区别

> （A）、Minor GC
>
> ​    又称新生代GC，指发生在新生代的垃圾收集动作；
>
> ​    因为Java对象大多是朝生夕灭，所以Minor GC非常频繁，一般回收速度也比较快；
>
> （B）、Full GC
>
> ​    又称Major GC或老年代GC，指发生在老年代的GC；
>
> ​    出现Full GC经常会伴随至少一次的Minor GC（不是绝对，Parallel Sacvenge收集器就可以选择设置Major GC策略）；
>
>    Major GC速度一般比Minor GC慢10倍以上；





##### 4.3  Serial收集器（串行收集器）

针对新生代；

   采用复制算法；

   单线程收集；

​    进行垃圾收集时，必须暂停所有工作线程，直到完成；      

​    即会"Stop The World"；

**应用场景**

Client模式下默认的新生代收集器；



##### 4.4  ParNew收集器

除了多线程外，其余的行为、特点和Serial收集器一样；

   如Serial收集器可用控制参数、收集算法、Stop The World、内存分配规则、回收策略等；

   两个收集器共用了不少代码；

**应用场景**

 在Server模式下，ParNew收集器是一个非常重要的收集器，因为除Serial外，目前只有它能与CMS收集器配合工作；

   但在单个CPU环境中，不会比Serail收集器有更好的效果，因为存在线程交互开销。



##### 4.5   Parallel Scavenge收集器

A）、有一些特点与ParNew收集器相似

   新生代收集器；

   采用复制算法；

   多线程收集；

（B）、主要特点是：它的关注点与其他收集器不同

   CMS等收集器的关注点是尽可能地缩短垃圾收集时用户线程的停顿时间；

   而Parallel Scavenge收集器的目标则是达一个可控制的吞吐量（Throughput）；

**应用场景**

   高吞吐量为目标，即减少垃圾收集时间，让用户代码获得更长的运行时间；

   当应用程序运行在具有多个CPU上，对暂停时间没有特别高的要求时，即程序主要在后台进行计算，而不需要与用户进行太多交互；

   例如，那些执行批量处理、订单处理、工资支付、科学计算的应用程序；



 （1）、停顿时间  

   停顿时间越短就适合需要与用户交互的程序；

   良好的响应速度能提升用户体验；

（2）、吞吐量

   高吞吐量则可以高效率地利用CPU时间，尽快完成运算的任务；

   主要适合在后台计算而不需要太多交互的任务；

（3）、覆盖区（Footprint）

   在达到前面两个目标的情况下，尽量减少堆的内存空间；

   可以获得更好的空间局部性；

##### 4.6  Serial Old收集器

 主要用于Client模式；

##### 4.7  Parallel Old收集器

JDK1.6及之后用来代替老年代的Serial Old收集器；

   特别是在Server模式，多CPU的情况下；

   这样在注重吞吐量以及CPU资源敏感的场景，就有了Parallel Scavenge加Parallel Old收集器的"给力"应用组合；



##### 4.8  CMS收集器

与用户交互较多的场景；    

希望系统停顿时间最短，注重服务的响应速度；

 以给用户带来较好的体验；

##### 4.9  参数设计

![img](https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/java%E5%9E%83%E5%9C%BE%E6%94%B6%E9%9B%86%E7%9B%B8%E5%85%B3%E5%8F%82%E6%95%B0%E8%AE%BE%E8%AE%A1.png)  