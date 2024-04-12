package DTO;
import Enum.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee extends User {
    private double salary;

    public Employee(String firstName,
                    String lastName,
                    String username,
                    String password,
                    int phone,
                    String address,
                    EGender gender,
                    LocalDate birthday,
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
