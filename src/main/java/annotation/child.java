package annotation;

@Description("I am class annotation")
public class child implements people {

    @Override
    @Description("I am function annotation")
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
