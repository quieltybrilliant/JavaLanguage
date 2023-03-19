package javaLanguage.collection.stream.service;

import javaLanguage.collection.stream.entity.Student;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Streap操作
 */
public class SerialStreamUtil {

    /**
     * 获得最小值
     */
    public static void SerialStreamForIntTest(int[] arr) {
        long timeStart = System.currentTimeMillis();

        Arrays.stream(arr).min().getAsInt();

        long timeEnd = System.currentTimeMillis();
        System.out.println("SerialStream 比较int最小值 花费的时间" + (timeEnd - timeStart));
    }


    /**
     * 进行groupby汇总
     */
    public static void SerialStreamForObjectTest(List<Student> studentsList) {
        long timeStart = System.currentTimeMillis();

        Map<String, List<Student>> stuMap = studentsList.stream().filter((Student s) -> s.getHeight() > 160).collect(Collectors.groupingBy(Student::getSex));

        long timeEnd = System.currentTimeMillis();
        System.out.println("Stream并行花费的时间" + (timeEnd - timeStart));
    }
}
