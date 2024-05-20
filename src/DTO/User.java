package DTO;
import Enum.*;

import java.sql.Date;

public class User {
    private String code;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String phone;
    private String address;
    private EPermission permission;
    private java.sql.Date birthday;

    public User(){

    }

    public User(String code,
                String firstName,
                String lastName,
                String username,
                String password,
                String phone,
                String address,
                java.sql.Date birthday){
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName(){
        return firstName + lastName;
    }

    public EPermission getPermission() {
        return permission;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCode() {
        return code;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public java.sql.Date getBirthday() {
        return birthday;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPermission(EPermission permission) {
        this.permission = permission;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }
}
