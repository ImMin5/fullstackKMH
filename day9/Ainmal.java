package day9;

public class Ainmal {
    private String name;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if(location.equals("land") || location.equals("sea") || location.equals("air")){
            this.location = location;
        }
        else
            System.out.println("land sea air만 입력하세요");
    }
}
