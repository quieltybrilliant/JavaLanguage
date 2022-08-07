### IO

#### 1  IO原理

~~~
I/O是Input/Output的缩写， I/O技术是非常实用的技术，如读/写文件，网络通讯等等。
流（Stream）是指从源节点到目标节点的数据流动。
源节点和目标节点可以是文件、网络、内存、键盘、显示器等等。
源节点的数据流称为输入流（用来读取数据）。
目标节点的数据流称为输出流（用来写入数据）。
~~~



#### 2  IO流体系

字节流(8 bit)，字符流(16 bit)

字节流读取单个字节，字符流读取单个字符（一个字符根据编码的不同，对应的字节也不同，如 UTF-8 编码是 3 个字节，中文编码是 2 个字节。）字节流用来处理二进制文件（图片、MP3、视频文件），字符流用来处理文本文件（可以看做是特殊的二进制文件，使用了某种编码，人可以阅读）。简而言之，字节是个计算机看的，字符才是给人看的。

![image-20200411135631179](..\..\images\IO流体系.png)

节点流

- FileInputStream，FileOutputStrean，FileReader，FileWriter，它们都会直接操作文件，直接与 OS 底层交互。因此他们被称为节点流 ，注意：使用这几个流的对象之后，需要关闭流对象，因为 java 垃圾回收器不会主动回收。不过在 Java7 之后，可以在 try() 括号中打开流，最后程序会自动关闭流对象，不再需要显示地 close。
- 数组流：ByteArrayInputStream，ByteArrayOutputStream，CharArrayReader，CharArrayWriter，对数组进行处理的节点流。
- 字符串流：StringReader，StringWriter，其中 StringReader 能从 String 中读取数据并保存到 char 数组。
- 管道流：PipedInputStream，PipedOutputStream，PipedReader，PipedWrite，对管道进行处理的节点流。

处理流

处理流是对一个已存在的流的连接和封装，通过所封装的流的功能调用实现数据读写。如 BufferedReader。

处理流的构造方法总是要带一个其他的流对象做参数。

常用处理流（通过关闭处理流里面的节点流来关闭处理流）

- 缓冲流 ：BufferedImputStrean，BufferedOutputStream，BufferedReader ，BufferedWriter，需要父类作为参数构造，增加缓冲功能，避免频繁读写硬盘，可以初始化缓冲数据的大小，由于带了缓冲功能，所以就写数据的时候需要使用 flush 方法，另外，BufferedReader 提供一个 readLine( ) 方法可以读取一行，而 FileInputStream 和 FileReader 只能读取一个字节或者一个字符，因此 BufferedReader 也被称为行读取器。
- 转换流：InputStreamReader，OutputStreamWriter，要 inputStream 或 OutputStream 作为参数，实现从字节流到字符流的转换，编码转换，我们经常在读取**键盘输入（System.in）**或网络通信的时候，需要使用这两个类。
- 数据流：DataInputStream，DataOutputStream，提供将基础数据类型写入到文件中，或者读取出来。

~~~
InputStream 和 Reader 是所有输入流的基类。
OutputStream & Writer 是所有输出流的基类。
程序中打开的文件 IO 资源不属于内存里的资源，垃圾回收机制无法回收该资源，所以应该显式关闭文件 IO 资源。
~~~



##### 2.1  FileReader vs FileInputStream

**字节流是以字节为单位进行操作的，而字符流是以字符为单位进行操作的。** 

所有读取的东西本质上是字节，然后需要一套字符编码方案，把字节转换成文本。Reader类使用字符编码来解码字节，并返回字符给调用者。

Reader类要么使用运行Java程序平台的默认字符编码，要么使用Charset对象或者String类型的字符编码名称

FileReader类**继承**了`InputStreamReader`类，使用的字符编码，要么由类提供，要么是平台默认的字符编码。请记住，InputStreamReader会缓存的字符编码。

~~~java
package IO;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Java程序通过字节流和字符流的方式来读取文件数据。
 * 需强调FileInputStream和FileReader的关键区别在于：FileReader用于读取字符流，而FileInputStream用来读取原始字节流。
 *
 * @author Javin Paul
 */
public class HowToReadFileInJava {
    public static void main(String args[]) {

        // 例1 – 使用FileInputStream 读取文件内容
        try (FileInputStream fis = new FileInputStream("data.txt")) {
            int data = fis.read();
            while (data != -1) {
                System.out.print(Integer.toHexString(data));
                data = fis.read();
            }
        } catch (IOException e) {
            System.out.println("Failed to read binary data from File");
            e.printStackTrace();
        }
        // 例2 – Java中使用FileReader 读取文件数据
        try (FileReader reader = new FileReader("data.txt")) {
            int character = reader.read();
            while (character != -1) {
                System.out.print((char) character);
                character = reader.read();
            }
        } catch (IOException io) {
            System.out.println("Failed to read character data from File");
            io.printStackTrace();
        }
    }
}
~~~

~~~
4157532d416d617a6f6e205765622053657276696365da474f4f472d476f6f676c65da4150504c2d4170706c65da47532d476f6c646d616e205361636873
AWS-Amazon Web Service
GOOG-Google
APPL-Apple
GS-Goldman Sachs
~~~

第1个例子是按字节从文件中读取数据，因此势必会非常慢。FileInputStream的read() 方法是阻塞式的，读取字节或数据块，直到无数据输入。它要么返回数据的下一个字节，当到达文件末尾时，返回-1。这意味着，我们每循环读取一个字节，将其打印为十六进制字符串。顺便说一句，将InputStream转换成字节数组是可选的。另一方面，例2是按字符读取数据。继承自FileReader的InputStreamReader 的read() 方法读取单个字符，并返回该字符，当到达流末尾时，返回-1。这就是为什么你看到例2输出的文字跟文件中的完全一样。

这就是所有关于Java中FileInputStream和FileReader之间的区别。归根结底：使用FileReader或BufferedReader从文件中读取字符或文本数据，并总是指定字符编码；使用FileInputStream从Java中文件或套接字中读取原始字节流。



**System.in**

键盘输入的是默认的字节输入流

InputStreamReader(System.in)

InputStreamReader 转换是为了每次可以读取一行的方法



##### 2.2  节点流和处理流

![image-20200411143043589](..\..\images\字符输入流输出流.png)





#### 3  InputStream ,OutputStream ,Reader ,Writer

##### 3.1  **InputStream 类**

| 方法                                         | 方法介绍                                                     |
| -------------------------------------------- | ------------------------------------------------------------ |
| public abstract int read()                   | 读取数据                                                     |
| public int read(byte b[])                    | 将读取到的数据放在 byte 数组中，该方法实际上是根据下面的方法实现的，off 为 0，len 为数组的长度 |
| public int read(byte b[], int off, int len)  | 从第 off 位置读取 len 长度字节的数据放到 byte 数组中，流是以 -1 来判断是否读取结束的（注意这里读取的虽然是一个字节，但是返回的却是 int 类型 4 个字节，这里当然是有原因，这里就不再细说了，推荐这篇文章，[链接](https://blog.csdn.net/congwiny/article/details/18922847)） |
| public long skip(long n)                     | 跳过指定个数的字节不读取，想想看电影跳过片头片尾             |
| public int available()                       | 返回可读的字节数量                                           |
| public void close()                          | 读取完，关闭流，释放资源                                     |
| public synchronized void mark(int readlimit) | 标记读取位置，下次还可以从这里开始读取，使用前要看当前流是否支持，可以使用 markSupport() 方法判断 |
| public synchronized void reset()             | 重置读取位置为上次 mark 标记的位置                           |
| public boolean markSupported()               | 判断当前流是否支持标记流，和上面两个方法配套使用             |

##### 3.2  **OutputStream 类**

| 方法                                          | 方法介绍                                                     |
| --------------------------------------------- | ------------------------------------------------------------ |
| public abstract void write(int b)             | 写入一个字节，可以看到这里的参数是一个 int 类型，对应上面的读方法，int 类型的 32 位，只有低 8 位才写入，高 24 位将舍弃。 |
| public void write(byte b[])                   | 将数组中的所有字节写入，和上面对应的 read() 方法类似，实际调用的也是下面的方法。 |
| public void write(byte b[], int off, int len) | 将 byte 数组从 off 位置开始，len 长度的字节写入              |
| public void flush()                           | 强制刷新，将缓冲中的数据写入                                 |
| public void close()                           | 关闭输出流，流被关闭后就不能再输出数据了                     |

再来看 Reader 和 Writer 类中的方法，你会发现和上面两个抽象基类中的方法很像。

##### 3.3  **Reader 类**

| 方法                                                    | 方法介绍                                                     |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| public int read(java.nio.CharBuffer target)             | 读取字节到字符缓存中                                         |
| public int read()                                       | 读取单个字符                                                 |
| public int read(char cbuf[])                            | 读取字符到指定的 char 数组中                                 |
| abstract public int read(char cbuf[], int off, int len) | 从 off 位置读取 len 长度的字符到 char 数组中                 |
| public long skip(long n)                                | 跳过指定长度的字符数量                                       |
| public boolean ready()                                  | 和上面的 available() 方法类似                                |
| public boolean markSupported()                          | 判断当前流是否支持标记流                                     |
| public void mark(int readAheadLimit)                    | 标记读取位置，下次还可以从这里开始读取，使用前要看当前流是否支持，可以使用 markSupport() 方法判断 |
| public void reset()                                     | 重置读取位置为上次 mark 标记的位置                           |
| abstract public void close()                            | 关闭流释放相关资源                                           |

##### 3.4  **Writer 类**

| 方法                                                       | 方法介绍                                                     |
| ---------------------------------------------------------- | ------------------------------------------------------------ |
| public void write(int c)                                   | 写入一个字符                                                 |
| public void write(char cbuf[])                             | 写入一个字符数组                                             |
| abstract public void write(char cbuf[], int off, int len)  | 从字符数组的 off 位置写入 len 数量的字符                     |
| public void write(String str)                              | 写入一个字符串                                               |
| public void write(String str, int off, int len)            | 从字符串的 off 位置写入 len 数量的字符                       |
| public Writer append(CharSequence csq)                     | 追加吸入一个字符序列                                         |
| public Writer append(CharSequence csq, int start, int end) | 追加写入一个字符序列的一部分，从 start 位置开始，end 位置结束 |
| public Writer append(char c)                               | 追加写入一个 16 位的字符                                     |
| abstract public void flush()                               | 强制刷新，将缓冲中的数据写入                                 |
| abstract public void close()                               | 关闭输出流，流被关闭后就不能再输出数据了                     |



**1、读取控制台中的输入**



```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOTest {

    public static void main(String[] args) throws IOException {
//        test01();
        test02();
    }

    public static void test01() throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("input:");
        char[] ch = new char[1024];
        bufferedReader.read(ch);
        System.out.println(ch);
    }


    public static void test02() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("input:");
        char[] ch = new char[1024];
        do {
            bufferedReader.read(ch);   //read 会读取输入的长度length替换掉ch，length后面的字符不变
            System.out.println(ch);
        } while (ch[0] != 'q' );
    }


    public static void test03() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入一行字符");
        String str = bufferedReader.readLine();
        System.out.println("你输入的字符为" + str);
    }
    
}
```

至于控制台的输出，我们其实一直都在使用呢，`System.out.println()` ，out 其实是 PrintStream 类对象的引用，PrintStream 类中当然也有 write() 方法，但是我们更常用 print() 方法和 println() 方法，因为这两个方法可以输出的内容种类更多，比如一个打印一个对象，实际调用的对象的 toString() 方法。

**2、二进制文件的写入和读取**

注意这里文件的路径，可以根据自己情况改一下，虽然这里的文件后缀是txt，但该文件却是一个二进制文件，并不能直接查看。

```java
    @Test
    public void  test04() throws IOException {
        byte[] bytes = {12,21,34,11,21,12};
        // resource路径是 编译后的路径  target/.....
        FileOutputStream fileOutputStream = new FileOutputStream(IOTest.class.getClassLoader().getResource("iotest.txt").getPath());
        // 写入二进制文件，直接打开会出现乱码
        fileOutputStream.write(bytes);
        fileOutputStream.close();
    }

    @Test
    public void  test05() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(IOTest.class.getClassLoader().getResource("iotest.txt").getPath());
        int c;
        // 读取写入的二进制文件，输出字节数组
        while ((c = fileInputStream.read()) != -1) {
            System.out.print(c);  //122134112112
        }
    }

    @Test
    public void  test06() throws IOException {
         FileReader fileReader = new FileReader(IOTest.class.getClassLoader().getResource("iotest.txt").getPath());
        int c;
        // 读取写入的二进制文件，输出字符数组
        while ((c = fileReader.read()) != -1) {
            System.out.print(c);  //122134112112
        }
    }
```

**3、文本文件的写入和读取**

write() 方法和 append() 方法**并不是**像方法名那样，一个是覆盖内容，一个是追加内容，append() 内部也是 write() 方法实现的，也非说区别，也就是 append() 方法可以直接写 null，而 write() 方法需要把 null 当成一个字符串写入，所以两者并无本质的区别。**需要注意的是这里并没有指定文件编码，可能会出现乱码的问题。**

apend方法中参数可以为null  会以字符串"null"的形式添加到文件中  writer会报空指针异常
append可以这么写 BufferWriter.append("").append("").append("").append("").append("")   
writer只能写一个  也就是说writer的返回值是空  而append的返回值Writer(BufferWriter的超类)

```java
@Test
    public void test06() throws IOException {
        FileWriter fileWriter = new FileWriter(new File("").getAbsolutePath()+"/io/test.txt");
        fileWriter.write("Hello，world！\n欢迎来到 java 世界\n");
        fileWriter.write("不会覆盖文件原本的内容\n");
//        fileWriter.write(null); 不能直接写入 null
        fileWriter.append("并不是追加一行内容，不要被方法名迷惑\n");
        fileWriter.append(null);
        fileWriter.flush();
        System.out.println("文件的默认编码为" + fileWriter.getEncoding());
        fileWriter.close();
    }

    @Test
    public void test08() throws IOException {
        FileWriter fileWriter = new FileWriter("iotest.txt",true);// 追加模式
        fileWriter.write("Hello，world！\n 欢迎来到 java 世界\n");
        fileWriter.flush();
        System.out.println("文件的默认编码为" + fileWriter.getEncoding());
        fileWriter.close();
    }

    @Test
    public void test08() throws IOException {
        FileReader fileReader = new FileReader(new File("").getAbsolutePath()+"/io/test.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }
        fileReader.close();
        bufferedReader.close();
    }

    @Test
    public void test09() throws IOException {
        FileReader fileReader = new FileReader(new File("").getAbsolutePath()+"/io/test.txt");
        int c;
        while ((c = fileReader.read()) != -1) {
            System.out.print((char) c);
        }
    }
```

使用字节流和字符流的转换类 InputStreamReader 和 OutputStreamWriter 可以指定文件的编码，使用 Buffer 相关的类来读取文件的每一行。



```java
@Test
    public void test10() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("").getAbsolutePath()+"/io/test2.txt");
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK"); // 使用 GBK 编码文件
        outputStreamWriter.write("Hello，world！\n欢迎来到 java 世界\n");
        outputStreamWriter.append("另外一行内容");
        outputStreamWriter.flush();
        System.out.println("文件的编码为" + outputStreamWriter.getEncoding());
        outputStreamWriter.close();
        fileOutputStream.close();
    }

    @Test
    public void test11() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(new File("").getAbsolutePath()+"/io/test2.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "GBK"); // 使用 GBK 解码文件
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
    }
```

**4、复制文件**

这里笔者做了一些测试，不使用缓冲对文件复制时间的影响，文件的复制实质还是文件的读写。缓冲流是处理流，是对节点流的装饰。

注：这里的时间是在我这台华硕笔记本上测试得到的，只是为了说明使用缓冲对文件的读写有好处。



```java
@Test
    public void  test12() throws IOException {
        // 输入和输出都使用缓冲流
        FileInputStream in = new FileInputStream("E:\\视频资料\\大数据原理与应用\\1.1大数据时代.mp4");
        BufferedInputStream inBuffer = new BufferedInputStream(in);
        FileOutputStream out = new FileOutputStream("1.1大数据时代.mp4");
        BufferedOutputStream outBuffer = new BufferedOutputStream(out);
        int len = 0;
        byte[] bs = new byte[1024];
        long begin = System.currentTimeMillis();
        while ((len = inBuffer.read(bs)) != -1) {
            outBuffer.write(bs, 0, len);
        }
        System.out.println("复制文件所需的时间：" + (System.currentTimeMillis() - begin)); // 平均时间约 200 多毫秒
        inBuffer.close();
        in.close();
        outBuffer.close();
        out.close();
    }


    @Test
    public void  test13() throws IOException {
        // 只有输入使用缓冲流
        FileInputStream in = new FileInputStream("E:\\视频资料\\大数据原理与应用\\1.1大数据时代.mp4");
        BufferedInputStream inBuffer = new BufferedInputStream(in);
        FileOutputStream out = new FileOutputStream("1.1大数据时代.mp4");
        int len = 0;
        byte[] bs = new byte[1024];
        long begin = System.currentTimeMillis();
        while ((len = inBuffer.read(bs)) != -1) {
            out.write(bs, 0, len);
        }
        System.out.println("复制文件所需时间：" + (System.currentTimeMillis() - begin)); // 平均时间约 500 多毫秒
        inBuffer.close();
        in.close();
        out.close();
    }

    @Test
    public void test14() throws IOException {
        // 输入和输出都不使用缓冲流
        FileInputStream in = new FileInputStream("E:\\视频资料\\大数据原理与应用\\1.1大数据时代.mp4");
        FileOutputStream out = new FileOutputStream("1.1大数据时代.mp4");
        int len = 0;
        byte[] bs = new byte[1024];
        long begin = System.currentTimeMillis();
        while ((len = in.read(bs)) != -1) {
            out.write(bs, 0, len);
        }
        System.out.println("复制文件所需时间：" + (System.currentTimeMillis() - begin)); // 平均时间 700 多毫秒
        in.close();
        out.close();
    }

    @Test
    public void test15() throws IOException {
        // 不使用缓冲
        FileInputStream in = new FileInputStream("E:\\视频资料\\大数据原理与应用\\1.1大数据时代.mp4");
        FileOutputStream out = new FileOutputStream("1.1大数据时代.mp4");
        int len = 0;
        long begin = System.currentTimeMillis();
        while ((len = in.read()) != -1) {
            out.write(len);
        }
        System.out.println("复制文件所需时间：" + (System.currentTimeMillis() - begin)); // 平均时间约 160000 毫秒，约 2 分多钟
        in.close();
        out.close();
    }
```







#### 4  File 类

 Java文件类以抽象的方式代表文件名和目录路径名。该类主要用于文件和目录的创建、文件的查找和文件的删除等。 

| 序号 | 方法描述                                                     |
| :--- | :----------------------------------------------------------- |
| 1    | **public String getName()** 返回由此抽象路径名表示的文件或目录的名称。 |
| 2    | **public String getParent()****、**  返回此抽象路径名的父路径名的路径名字符串，如果此路径名没有指定父目录，则返回 `null`。 |
| 3    | **public File getParentFile()** 返回此抽象路径名的父路径名的抽象路径名，如果此路径名没有指定父目录，则返回 `null`。 |
| 4    | **public String getPath()** 将此抽象路径名转换为一个路径名字符串。 |
| 5    | **public boolean isAbsolute()** 测试此抽象路径名是否为绝对路径名。 |
| 6    | **public String getAbsolutePath()** 返回抽象路径名的绝对路径名字符串。 |
| 7    | **public boolean canRead()** 测试应用程序是否可以读取此抽象路径名表示的文件。 |
| 8    | **public boolean canWrite()** 测试应用程序是否可以修改此抽象路径名表示的文件。 |
| 9    | **public boolean exists()** 测试此抽象路径名表示的文件或目录是否存在。 |
| 10   | **public boolean isDirectory()** 测试此抽象路径名表示的文件是否是一个目录。 |
| 11   | **public boolean isFile()** 测试此抽象路径名表示的文件是否是一个标准文件。 |
| 12   | **public long lastModified()** 返回此抽象路径名表示的文件最后一次被修改的时间。 |
| 13   | **public long length()** 返回由此抽象路径名表示的文件的长度。 |
| 14   | **public boolean createNewFile() throws IOException** 当且仅当不存在具有此抽象路径名指定的名称的文件时，原子地创建由此抽象路径名指定的一个新的空文件。 |
| 15   | **public boolean delete()**  删除此抽象路径名表示的文件或目录。 |
| 16   | **public void deleteOnExit()** 在虚拟机终止时，请求删除此抽象路径名表示的文件或目录。 |
| 17   | **public String[] list()** 返回由此抽象路径名所表示的目录中的文件和目录的名称所组成字符串数组。 |
| 18   | **public String[] list(FilenameFilter filter)** 返回由包含在目录中的文件和目录的名称所组成的字符串数组，这一目录是通过满足指定过滤器的抽象路径名来表示的。 |
| 19   | **public File[] listFiles()**  返回一个抽象路径名数组，这些路径名表示此抽象路径名所表示目录中的文件。 |
| 20   | **public File[] listFiles(FileFilter filter)** 返回表示此抽象路径名所表示目录中的文件和目录的抽象路径名数组，这些路径名满足特定过滤器。 |
| 21   | **public boolean mkdir()** 创建此抽象路径名指定的目录。      |
| 22   | **public boolean mkdirs()** 创建此抽象路径名指定的目录，包括创建必需但不存在的父目录。 |
| 23   | **public boolean renameTo(File dest)**  重新命名此抽象路径名表示的文件。 |
| 24   | **public boolean setLastModified(long time)** 设置由此抽象路径名所指定的文件或目录的最后一次修改时间。 |
| 25   | **public boolean setReadOnly()** 标记此抽象路径名指定的文件或目录，以便只可对其进行读操作。 |
| 26   | **public static File createTempFile(String prefix, String suffix, File directory) throws IOException** 在指定目录中创建一个新的空文件，使用给定的前缀和后缀字符串生成其名称。 |
| 27   | **public static File createTempFile(String prefix, String suffix) throws IOException** 在默认临时文件目录中创建一个空文件，使用给定前缀和后缀生成其名称。 |
| 28   | **public int compareTo(File pathname)** 按字母顺序比较两个抽象路径名。 |
| 29   | **public int compareTo(Object o)** 按字母顺序比较抽象路径名与给定对象。 |
| 30   | **public boolean equals(Object obj)** 测试此抽象路径名与给定对象是否相等。 |
| 31   | **public String toString()**  返回此抽象路径名的路径名字符串。 |