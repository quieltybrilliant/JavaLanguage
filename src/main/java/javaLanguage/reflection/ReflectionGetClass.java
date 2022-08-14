package javaLanguage.reflection;

public class ReflectionGetClass {
    public static void main(String[] args) {

        // first method: Object
        Students students = new Students();
        Class  studentsClass = students.getClass();
        System.out.println(studentsClass.getName());

        // second method: clas property
        Class studentsClass2 = Students.class;
        System.out.println(studentsClass2);


        //third method: static method
        try {
            Class studentsClass3 = Class.forName("Students");
            System.out.println(studentsClass2 == studentsClass3);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

