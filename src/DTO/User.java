package DTO;
import Enum.*;

import java.time.LocalDateTime;

public class User {
    private String firstName;
    private String lastName;
    private String code;
    private String username;
    private String password;
    private int phone;
    private String address;
    private EPermission permission;
    private EGender gender;
    private LocalDateTime birthday;

    public User(){

    }

    public User(String firstName,
                String lastName,
                String username,
                String password,
                int phone,
                String address,
                EGender gender,
                LocalDateTime birthday){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
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

    public int getPhone() {
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

    public EGender getGender() {
        return gender;
    }

    public LocalDateTime getBirthday() {
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

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setGender(EGender gender) {
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }
}
