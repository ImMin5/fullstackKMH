package day9;

public class Human implements Runnable,Speak {

    private String name;
    @Override
    public void run() {
    }

    @Override
    public void silent(String sound) {
        System.out.println(sound);
    }

    @Override
    public void loud(String sound) {
        System.out.println(sound);
    }
}


