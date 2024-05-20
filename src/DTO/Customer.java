package DTO;
import Enum.*;

public class Customer extends User {
    public Customer(){

    }

    public Customer(String code,
                    String firstName,
                    String lastName,
                    String username,
                    String password,
                    String phone,
                    String address,
                    java.sql.Date birthday){

        super(code, firstName, lastName, username, password, phone, address, birthday);
        setPermission(EPermission.CUSTOMER);
    }
}
