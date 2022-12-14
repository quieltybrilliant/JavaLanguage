package javaLanguage.juc;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;


/*
 * CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制”
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择。
 * 适合读多操作
 */
public class juc_03_CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        DemoThread ht = new DemoThread();

        for (int i = 0; i < 2; i++) {
            new Thread(ht).start();
        }
    }


    static class DemoThread implements Runnable {

        //	private static List<String> list = Collections.synchronizedList(new ArrayList<String>());
        private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        static {
            list.add("AA");
            list.add("BB");
            list.add("CC");
        }

        @Override
        public void run() {
            Iterator<String> it = list.iterator();

            while (it.hasNext()) {
                System.out.println(it.next());

                list.add("AA");
            }
            System.out.println(list);
        }
    }
}


