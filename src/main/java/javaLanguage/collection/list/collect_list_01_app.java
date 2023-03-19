package javaLanguage.collection.list;

import javaLanguage.collection.list.service.ArrayListUtil;
import javaLanguage.collection.list.service.LinkedListUtil;

/**
 * ArrayList和ListedList集合性能测试对比
 *
 * @author liuchao
 */
public class collect_list_01_app {
    public static void main(String[] args) {

        ArrayListUtil.addFromHeaderTest(100000);
        LinkedListUtil.addFromHeaderTest(100000);


        ArrayListUtil.addFromMidTest(10000);
        LinkedListUtil.addFromMidTest(10000);

        ArrayListUtil.addFromTailTest(1000000);
        LinkedListUtil.addFromTailTest(1000000);

        ArrayListUtil.deleteFromHeaderTest(100000);
        LinkedListUtil.deleteFromHeaderTest(100000);

        ArrayListUtil.deleteFromMidTest(100000);
        LinkedListUtil.deleteFromMidTest(100000);

        ArrayListUtil.deleteFromTailTest(1000000);
        LinkedListUtil.deleteFromTailTest(1000000);

        ArrayListUtil.getByForTest(10000);
        LinkedListUtil.getByForTest(10000);

        ArrayListUtil.getByIteratorTest(100000);
        LinkedListUtil.getByIteratorTest(100000);
    }
}