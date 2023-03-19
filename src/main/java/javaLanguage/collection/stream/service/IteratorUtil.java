package javaLanguage.collection.stream.service;

import javaLanguage.collection.stream.entity.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 迭代器
 */
public class IteratorUtil {

    /**
     * 迭代器获取最小元素
     * @param arr
     */
    public static void IteratorForIntTest(int[] arr) {
        long timeStart = System.currentTimeMillis();

        int min = Integer.MAX_VALUE;
        for(int i=0; i<arr.length; i++){
            if(arr[i]<min)
                min = arr[i];
        }

        long timeEnd = System.currentTimeMillis();
        System.out.println("Iterator 比较int最小值 花费的时间" + (timeEnd - timeStart));
    }

    /**
     * 迭代器获取元素
     * @param studentsList
     */
    public static void IteratorForObjectTest(List<Student> studentsList) {
        long timeStart = System.currentTimeMillis();
        Map<String, List<Student>> stuMap = new HashMap<String, List<Student>>();

        for (Student stu : studentsList) {
            if (stu.getHeight() > 160) { // 如果身高大于160
                if (stuMap.get(stu.getSex()) == null) { // 该性别还没分类
                    List<Student> list = new ArrayList<Student>(); // 新建该性别学生的列表
                    list.add(stu);// 将学生放进去列表
                    stuMap.put(stu.getSex(), list);// 将列表放到map中
                } else { // 该性别分类已存在
                    stuMap.get(stu.getSex()).add(stu);// 该性别分类已存在，则直接放进去即可
                }
            }
        }

        long timeEnd = System.currentTimeMillis();
        System.out.println("Iterator花费的时间" + (timeEnd - timeStart));
    }

}