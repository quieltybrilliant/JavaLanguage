package javaLanguage.annotation;

@Description("I am class java.annotation")
public class child implements people {

    @Override
    @Description("I am function java.annotation")
    public String name() {
        return null;
    }

    @Override
    public int age() {
        return 0;
    }

    @Override
    public void work() {

    }
}
