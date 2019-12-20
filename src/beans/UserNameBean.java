package beans;

public class UserNameBean {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void clear(){
        this.setName(null);
    }

}
