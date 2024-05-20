package DTO;
import Enum.*;

public class Employee extends User {
    private double salary;
    private String job;

    public Employee(String code,
                    String firstName,
                    String lastName,
                    String username,
                    String password,
                    String phone,
                    String address,
                    java.sql.Date birthday,
                    String job,
                    double salary) {

        super(code, firstName, lastName, username, password, phone, address, birthday);
        setPermission(EPermission.EMPLOYEE);

        this.salary = salary;
        this.job = job;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJob() {
        return job;
    }
}
