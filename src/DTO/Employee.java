package DTO;
import Enum.*;

public class Employee extends User {
    private double salary;

    public Employee(String firstName,
                    String lastName,
                    String username,
                    String password,
                    int phone,
                    String address,
                    EGender gender,
                    java.sql.Date birthday,
                    double salary) {

        super(firstName, lastName, username, password, phone, address, gender, birthday);
        setPermission(EPermission.EMPLOYEE);
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}
