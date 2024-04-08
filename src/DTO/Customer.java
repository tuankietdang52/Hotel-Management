package DTO;
import Enum.*;

import java.time.LocalDateTime;

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
                    LocalDateTime birthday){

        super(firstName, lastName, username, password, phone, address, gender, birthday);
        setPermission(EPermission.CUSTOMER);
    }
}
