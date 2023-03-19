package javaLanguage.collection.stream;

import javaLanguage.collection.stream.entity.Student;
import javaLanguage.collection.stream.service.IteratorUtil;
import javaLanguage.collection.stream.service.ParallelStreamUtil;
import javaLanguage.collection.stream.service.SerialStreamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author liuchao
 *
 */
public class collect_stream_01_app
{
    public static void main( String[] args )
    {
        List<Student> studentsList = new ArrayList<Student>();

        for(int i=0; i<10000000; i++) {
            Student stu = new Student();
            stu.setHeight(156 + i);
            stu.setSex(String.valueOf(i%2));
            studentsList.add(stu);
        }

        IteratorUtil.IteratorForObjectTest(studentsList);
        SerialStreamUtil.SerialStreamForObjectTest(studentsList);
        ParallelStreamUtil.ParallelStreamForObjectTest(studentsList);

    	int[] arr = new int[100000000];

    	Random r = new Random();
		for(int i=0; i<arr.length; i++){
			arr[i] = r.nextInt();
		}

    	IteratorUtil.IteratorForIntTest(arr);
    	SerialStreamUtil.SerialStreamForIntTest(arr);
    	ParallelStreamUtil.ParallelStreamForIntTest(arr);


    }
}