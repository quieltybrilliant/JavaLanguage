#### HashMap和TreeMap

​		HashMap 线程不安全，支持 null 键和值等。通常情况下，HashMap 进行 put 或者 get 操作，可以达到常数时间的性能，所以它是绝大部分利用键值对存取场景的首选。

​		TreeMap 则是基于红黑树的一种提供顺序访问的 Map，和 HashMap 不同，它的 get、put、remove 之类操作都是 O（log(n)）的时间复杂度，具体顺序可以由指定的 Comparator 来决定，或者根据键的自然顺序来判断。

​		HashMap采用位桶+链表+红黑树实现，当链表长度超过阈值（8）时，将链表转换为红黑树。

​		HashMap基于哈希思想，实现对数据的读写。假设桶的数量为n，table为桶的数组，当我们将键值对传递给put()方法时，它调用键对象的hashCode()方法来计算hashcode，然后根据 hashcode%n = t 找到bucket位置table[t] 来储存值对象，根据key是否相等来使用链表或者红黑树存储。当获取对象时，通过键对象的equals()方法找到正确的键值对，然后返回值对象。HashMap使用链表来解决碰撞问题，当发生碰撞了，对象将会储存在链表的下一个节点中。 HashMap在每个链表节点中储存键值对对象。当两个不同的键对象的hashcode相同时，它们会储存在同一个bucket位置的链表中，可通过键对象的equals()方法用来找到键值对。如果链表大小超过阈值（ 8），链表就会被改造为树形结构。

<img src="https://zgy-1300225777.cos.ap-guangzhou.myqcloud.com/ebc8c027e556331dc327e18feb00c7d9.jpg" alt="img" style="zoom:67%;" />