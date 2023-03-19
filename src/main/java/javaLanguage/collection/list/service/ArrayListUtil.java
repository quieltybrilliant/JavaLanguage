package javaLanguage.collection.list.service;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListUtil {

    /**
     * 从ArrayList头部添加元素
     */
    public static void addFromHeaderTest(int DataNum) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>(DataNum);
        int i = 0;

        long timeStart = System.currentTimeMillis();

        while (i < DataNum) {
            list.add(0,i+"aaavvv");
            i++;
        }
        long timeEnd = System.currentTimeMillis();

        System.out.println("ArrayList从集合头部位置新增元素花费的时间" + (timeEnd - timeStart));
    }


    /**
     * 从ArrayList中间添加元素
     */
    public static void addFromMidTest(int DataNum) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>(DataNum);
        int i = 0;

        long timeStart = System.currentTimeMillis();
        while (i < DataNum) {
            int temp = list.size();
            list.add(temp/2+"aaavvv");
            i++;
        }
        long timeEnd = System.currentTimeMillis();

        System.out.println("ArrayList从集合中间位置新增元素花费的时间" + (timeEnd - timeStart));
    }


    /**
     * 从ArrayList尾部添加元素
     */
    public static void addFromTailTest(int DataNum) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>(DataNum);
        int i = 0;

        long timeStart = System.currentTimeMillis();

        while (i < DataNum) {
            list.add(i+"aaavvv");
            i++;
        }

        long timeEnd = System.currentTimeMillis();

        System.out.println("ArrayList从集合尾部位置新增元素花费的时间" + (timeEnd - timeStart));
    }


    /**
     * 从ArrayList头部删除元素
     * 先从尾部添加元素
     */
    public static void deleteFromHeaderTest(int DataNum) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>();
        int i = 0;

        while (i < DataNum) {
            list.add(i + "aaavvv");
            i++;
        }
        long timeStart = System.currentTimeMillis();
        i=0;

        while (i < DataNum) {
            list.remove(0);
            i++;
        }

        long timeEnd = System.currentTimeMillis();

        System.out.println("ArrayList从集合头部位置删除元素花费的时间" + (timeEnd - timeStart));
    }


    /**
     * 从ArrayList中间删除元素
     * 先从尾部添加元素
     */
    public static void deleteFromMidTest(int DataNum) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>();
        int i = 0;
        while (i < DataNum) {
            list.add(i + "aaavvv");
            i++;
        }
        long timeStart = System.currentTimeMillis();
        i=0;

        while (i < DataNum) {
            int temp = list.size();
            list.remove(temp/2);
            i++;
        }


        long timeEnd = System.currentTimeMillis();

        System.out.println("ArrayList从集合中间位置删除元素花费的时间" + (timeEnd - timeStart));
    }


    /**
     * 从ArrayList尾部删除元素
     * 先从尾部添加元素
     */
    public static void deleteFromTailTest(int DataNum) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>();
        int i = 0;
        while (i < DataNum) {
            list.add(i + "aaavvv");
            i++;
        }

        long timeStart = System.currentTimeMillis();

        i=0;

        while (i < DataNum) {
            int temp = list.size();
            list.remove(temp-1);
            i++;

        }

        long timeEnd = System.currentTimeMillis();

        System.out.println("ArrayList从集合尾部位置删除元素花费的时间" + (timeEnd - timeStart));
    }


    /**
     * 使用for循环元素
     */
    public static void getByForTest(int DataNum) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>();
        int i = 0;

        while (i < DataNum) {
            list.add(i + "aaavvv");
            i++;
        }
        long timeStart = System.currentTimeMillis();

        for (int j = 0; j < DataNum; j++) {
            list.get(j);
        }

        long timeEnd = System.currentTimeMillis();

        System.out.println("ArrayList for(;;)循环花费的时间" + (timeEnd - timeStart));
    }


    /**
     * 使用迭代器获取元素
     */
    public static void getByIteratorTest(int DataNum) {
        java.util.ArrayList<String> list = new java.util.ArrayList<String>();
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

        System.out.println("ArrayList 迭代器迭代循环花费的时间" + (timeEnd - timeStart));
    }

}