public class Student {
    private int id;
    private String name;
    private String address;
    private double GPA;

    /*Constructor*/
    public Student(int id, String name, String address, double GPA) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.GPA = GPA;
    }
    /*Getter and Setter methods*/
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    @Override
    public String toString() {
        return "Student(id:" + getId() + ", name:" + getName() + ", address:" + getAddress() + ", GPA:" + getGPA() + ")";
    }

}
