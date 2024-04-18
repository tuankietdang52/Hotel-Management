package DTO;
import Enum.*;

public class Customer extends User {
    public Customer(){

    }

    public Customer(String firstName,
                    String lastName,
                    String username,
                    String password,
                    int phone,
                    String address,
                    EGender gender,
                    java.sql.Date birthday){

        super(firstName, lastName, username, password, phone, address, gender, birthday);
        setPermission(EPermission.CUSTOMER);
    }
}
