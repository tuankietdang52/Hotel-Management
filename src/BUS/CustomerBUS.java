package BUS;

import DAO.CustomerDAO;
import DTO.Customer;
import Utilities.Pair;

import java.util.ArrayList;

public class CustomerBUS extends BaseBUS<Customer>{

    private final CustomerDAO customerDAO;

    public CustomerBUS() {
        customerDAO = new CustomerDAO();
    }

    public Customer getCustomerByCode(String code) {
        return customerDAO.findCustomerByCode(code);
    }

    @Override
    public ArrayList<ArrayList<String>> getDataTable() {
        ArrayList<Customer> customers = customerDAO.getCustomerData();
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (Customer customer : customers) {
            result.add(new ArrayList<>() {{
                add(customer.getCode());
                add(String.format("%s %s", customer.getFirstName(), customer.getLastName()));
                add(customer.getUsername());
                add(customer.getPassword());
                add(customer.getAddress());
                add(customer.getPhone());
                add(customer.getBirthday().toString());
            }});
        }

        return result;
    }

    public ArrayList<ArrayList<String>> getDataTable(ArrayList<Customer> customers) {
        ArrayList<ArrayList<String>> result = new ArrayList<>();

        for (Customer customer : customers) {
            result.add(new ArrayList<>() {{
                add(customer.getCode());
                add(customer.getFullName());
                add(customer.getUsername());
                add(customer.getPassword());
                add(customer.getAddress());
                add(customer.getPhone());
                add(customer.getBirthday().toString());
            }});
        }

        return result;
    }

    public Pair<Boolean, String> addCustomerToDatabase(Customer customer){
        boolean res = customerDAO.addCustomer(customer);
        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    public Pair<Boolean, String> adjustCustomer(Customer customer) {
        boolean res = customerDAO.adjustCustomer(customer);
        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    public Pair<Boolean, String> removeCustomer(Customer customer) {
        boolean res = customerDAO.deleteCustomer(customer);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    @Override
    public Pair<Boolean, String> checkValidInput(Customer customer) {
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty()){
            return new Pair<>(false, "Họ tên khách hàng không được để trống");
        }

        else if (customer.getUsername().isEmpty() || customer.getPassword().isEmpty()){
            return new Pair<>(false, "Tài khoản, mật khẩu không được để trống");
        }

        else if (!isValidNumberTextField(customer.getPhone(), "int") || !isValidPhone(customer.getPhone())){
            return new Pair<>(false, "SDT sai định dạng hoặc trống");
        }

        else if (customer.getAddress().isEmpty()){
            return new Pair<>(false, "Địa chỉ không được để trống");
        }

        return new Pair<>(true, "");
    }
}
