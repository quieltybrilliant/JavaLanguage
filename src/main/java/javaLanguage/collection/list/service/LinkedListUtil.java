package javaLanguage.collection.list.service;

import java.util.Iterator;
import java.util.LinkedList;


public class LinkedListUtil {

    /**
     * 从LinkedList头部增加元素
     */
    public static void addFromHeaderTest(int DataNum)
    {
        java.util.LinkedList<String> list=new java.util.LinkedList<String>();
        int i=0;
        long timeStart=System.currentTimeMillis();
        while(i<DataNum)
        {
            list.addFirst(i+"aaavvv");
            i++;
        }
        long timeEnd=System.currentTimeMillis();

        System.out.println("LinkedList从集合头部位置新增元素花费的时间"+(timeEnd-timeStart));
    }

    /**
     * 从LinkedList中间增加元素
     */
    public static void addFromMidTest(int DataNum)
    {
        java.util.LinkedList<String> list=new java.util.LinkedList<String>();
        int i=0;
        long timeStart=System.currentTimeMillis();
        while(i<DataNum)
        {
            int temp = list.size();
            list.add(temp/2, i+"aaavvv");
            i++;
        }
        long timeEnd=System.currentTimeMillis();

        System.out.println("LinkedList从集合中间位置新增元素花费的时间"+(timeEnd-timeStart));
    }

    /**
     * 从LinkedList尾部增加元素
     */
    public static void addFromTailTest(int DataNum)
    {
        java.util.LinkedList<String> list=new java.util.LinkedList<String>();
        int i=0;
        long timeStart=System.currentTimeMillis();
        while(i<DataNum)
        {
            list.add(i+"aaavvv");
            i++;
        }
        long timeEnd=System.currentTimeMillis();

        System.out.println("LinkedList从集合尾部位置新增元素花费的时间"+(timeEnd-timeStart));
    }

    /**
     * 从LinkedList头部删除元素
     */
    public static void deleteFromHeaderTest(int DataNum)
    {
        java.util.LinkedList<String> list=new java.util.LinkedList<String>();
        int i=0;

        while(i<DataNum)
        {
            list.add(i+"aaavvv");
            i++;
        }
        long timeStart=System.currentTimeMillis();

        i=0;
        while(i<DataNum)
        {
            list.removeFirst();
            i++;
        }

        long timeEnd=System.currentTimeMillis();

        System.out.println("LinkedList从集合头部位置删除元素花费的时间"+(timeEnd-timeStart));
    }

    /**
     * 从LinkedList中间删除元素
     */
    public static void deleteFromMidTest(int DataNum)
    {
        java.util.LinkedList<String> list=new java.util.LinkedList<String>();

        int i=0;
        while(i<DataNum)
        {
            list.add(i+"aaavvv");
            i++;
        }

        long timeStart=System.currentTimeMillis();

        i=0;
        while(i<DataNum)
        {
            int temp = list.size();
            list.remove(temp/2);
            i++;
        }

        long timeEnd=System.currentTimeMillis();

        System.out.println("LinkedList从集合中间位置删除元素花费的时间"+(timeEnd-timeStart));
    }

    /**
     * 从LinkedList尾部删除元素
     */
    public static void deleteFromTailTest(int DataNum)
    {
        java.util.LinkedList<String> list=new java.util.LinkedList<String>();
        int i=0;
        while(i<DataNum)
        {
            list.add(i+"aaavvv");
            i++;
        }

        long timeStart=System.currentTimeMillis();

        i=0;
        while(i<DataNum)
        {
            list.removeLast();
            i++;
        }


        long timeEnd=System.currentTimeMillis();

        System.out.println("LinkedList从集合尾部位置删除元素花费的时间"+(timeEnd-timeStart));
    }


    /**
     * 通过for循环获取元素
     */
    public static void getByForTest(int DataNum) {
        java.util.LinkedList<String> list = new java.util.LinkedList<String>();
        int i = 0;

        while (i < DataNum) {
            list.add(i + "aaavvv");
            i++;
        }
        long timeStart = System.currentTimeMillis();

        for (int j=0; j < DataNum ; j++) {
            list.get(j);
        }

        long timeEnd = System.currentTimeMillis();

        System.out.println("LinkedList for(;;)循环花费的时间" + (timeEnd - timeStart));
    }

    /**
     * 通过迭代获取元素
     */
    public static void getByIteratorTest(int DataNum) {
        java.util.LinkedList<String> list = new java.util.LinkedList<String>();
        int i = 0;

        while (i < DataNum) {
            list.add(i + "aaavvv");
            i++;
        }
        long timeStart = System.currentTimeMillis();

        for (Iterator<String> it=list.iterator(); it.hasNext();) {
            it.next();
        }

        long timeEnd = System.currentTimeMillis();

        System.out.println("LinkedList 迭代器迭代循环花费的时间" + (timeEnd - timeStart));
    }
}
