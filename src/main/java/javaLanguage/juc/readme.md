#### 基本概念
##### 1.JUC
JUC是java.util.concurrent包的简称，在Java5.0添加，目的就是为了更好的支持高并发任务。
让开发者进行多线程编程时减少竞争条件和死锁的问题！

##### 2.并发，并行
并发
同一个资源(处理器)处理多个任务,交替执行

并行
多个资源(处理器)处理多个任务,同时执行

串行
同一个资源(处理器)处理多个任务,顺序执行


##### 3.线程状态
新生      NEW
运行      RUNNABLE
阻塞      BLOCKED
等待      WAITING
超时等待   TIMED_WAITING
终止      TERMINATED


##### 4.wait/sleep的区别：
1. 来自不同的类
wait来自object类, sleep来自线程类

2. 关于锁的释放 
wait会释放锁, sleep不会释放锁

3. 使用的范围不同 
wait必须在同步代码块中
sleep可以在任何地方睡

4. 是否需要捕获异常
wait不需要捕获异常
sleep需要捕获异常

#### JUC

##### 1. JMM 模型特性

- 原子性

- 可见性

- 有序性



不同的硬件生产商和不同的操作系统下，内存的访问逻辑有一定的差异。Java内存模型，就是为了屏蔽系统和硬件的差异，让一套代码在不同平台下能到达相同的访问结果。

JMM规定了内存主要划分为主内存和工作内存两种。此处的主内存和工作内存跟JVM内存划分（堆、栈、方法区）是在不同的层次上进行的，从更底层的来说，主内存对应的是硬件的物理内存，工作内存对应的是寄存器和高速缓存。



JVM在设计时候考虑到，如果JAVA线程每次读取和写入变量都直接操作主内存，对性能影响比较大，所以每条线程拥有各自的工作内存，工作内存中的变量是主内存中的一份拷贝，线程对变量的读取和写入，直接在工作内存中操作，而不能直接去操作主内存中的变量。但是这样就会出现一个问题，当一个线程修改了自己工作内存中变量，对其他线程是不可见的，会导致线程不安全的问题。因为JMM制定了一套标准来保证开发者在编写多线程程序的时候，能够控制什么时候内存会被同步给其他线程。





##### 2. 基本结构

1，tools（工具类）：又叫信号量三组工具类，包含有
1）CountDownLatch（闭锁） 是一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待

2）CyclicBarrier（栅栏） 之所以叫barrier，是因为是一个同步辅助类，允许一组线程互相等待，
直到到达某个公共屏障点 ， 并且在释放等待线程后可以重用。

3）Semaphore（信号量） 是一个计数信号量，它的本质是一个“共享锁“。信号量维护了一个信号量许可集。
线程可以通过调用 acquire()来获取信号量的许可；当信号量中有可用的许可时，线程能获取该许可；
否则线程必须等待，直到有可用的许可为止。 线程可以通过release()来释放它所持有的信号量许可。

2，executor(执行者)：是Java里面线程池的顶级接口，但它只是一个执行线程的工具，
真正的线程池接口是ExecutorService，里面包含的类有：
1）ScheduledExecutorService 解决那些需要任务重复执行的问题
2）ScheduledThreadPoolExecutor 周期性任务调度的类实现

3，atomic(原子性包)：是JDK提供的一组原子操作类，包含有
AtomicBoolean、AtomicInteger、AtomicIntegerArray等原子变量类，
他们的实现原理大多是持有它们各自的对应的类型变量value，而且被volatile关键字修饰了。
这样来保证每次一个线程要使用它都会拿到最新的值。

4，locks（锁包）：是JDK提供的锁机制，相比synchronized关键字来进行同步锁，功能更加强大，
它为锁提供了一个框架，该框架允许更灵活地使用锁包含的实现类有：
1）ReentrantLock 它是独占锁，是指只能被独自占领，即同一个时间点只能被一个线程锁获取到的锁。
2）ReentrantReadWriteLock 它包括子类ReadLock和WriteLock。ReadLock是共享锁，而WriteLock是独占锁。
3）LockSupport 它具备阻塞线程和解除阻塞线程的功能，并且不会引发死锁。

5，collections(集合类)：主要是提供线程安全的集合， 比如：
1）ArrayList对应的高并发类是CopyOnWriteArrayList，
2）HashSet对应的高并发类是 CopyOnWriteArraySet，
3）HashMap对应的高并发类是ConcurrentHashMap等等





##### 3. synchronized和lock锁的区别

synchronized内置的java关键字,Lock是一个java类
synchronized无法判断获取锁的状态, Lock可以判断是否获取到了锁
synchronized会自动释放锁,Lock必须要手动释放锁!如果不是释放锁,会产生死锁
synchronized 线程1(获得锁,阻塞),线程2(等待); Lock锁就不一定会等待下去
synchronized 可重入锁,不可以中断的,非公平的; Lock锁,可重入的,可以判断锁,非公平(可自己设置);
synchronized 适合锁少量的代码同步问题,Lock 适合锁大量的同步代码



##### 4. Condition

Condition是个接口，基本的方法就是await()和signal()方法；
Condition依赖于Lock接口，生成一个Condition的基本代码是lock.newCondition()
调用Condition的await()和signal()方法，都必须在lock保护之内，就是说必须在lock.java.lock()和lock.unlock之间才可以使用
Conditon中的await()对应Object的wait()；

Condition中的signal()对应Object的notify()；

Condition中的signalAll()对应Object的notifyAll()。