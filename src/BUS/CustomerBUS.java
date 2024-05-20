package BUS;

import DAO.CustomerDAO;
import DTO.Customer;
import Utilities.AppManager;
import Utilities.Pair;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomerBUS {

    private final CustomerDAO customerDAO;

    public CustomerBUS() {
        customerDAO = new CustomerDAO();
    }

    public Customer getCustomerByCode(String code) {
        return customerDAO.findCustomerByCode(code);
    }

    public ArrayList<ArrayList<String>> getCustomerDataTable() {
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

    public ArrayList<ArrayList<String>> getCustomerDataTable(ArrayList<Customer> customers) {
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

    public Pair<Boolean, String> addCustomerToDatabase(ArrayList<String> input) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = format.parse(input.get(6));
        java.sql.Date birthday = new java.sql.Date(date.getTime());

        Customer customer = new Customer(AppManager.generateRandomCode(10),
                input.getFirst(), input.get(1), input.get(2), input.get(3), input.get(4),
                input.get(5), birthday);

        boolean res = customerDAO.addCustomer(customer);
        return res ? new Pair<>(true, "Thêm thành công") : new Pair<>(false, "Thêm thất bại");
    }

    public Pair<Boolean, String> adjustCustomer(ArrayList<String> input, String customerCode) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = format.parse(input.get(6));
        java.sql.Date birthday = new java.sql.Date(date.getTime());

        Customer customer = new Customer(customerCode,
                input.getFirst(), input.get(1), input.get(2), input.get(3), input.get(4),
                input.get(5), birthday);

        boolean res = customerDAO.adjustCustomer(customer);
        return res ? new Pair<>(true, "Sửa thành công") : new Pair<>(false, "Sửa thất bại");
    }

    public Pair<Boolean, String> removeCustomer(String customerCode) {
        boolean res = customerDAO.deleteCustomer(customerCode);
        return res ? new Pair<>(true, "Xóa thành công") : new Pair<>(false, "Xóa thất bại");
    }

    public Pair<Boolean, String> checkValidInput(ArrayList<String> dataInput) {
        for (int i = 0; i < dataInput.size(); i++) {
            String input = dataInput.get(i);
            switch (i) {
                case 0, 1 -> {
                    if (input.isEmpty()) {
                        return new Pair<>(false, "Họ tên khách hàng không được để trống");
                    }
                }
                case 2, 3 -> {
                    if (input.isEmpty()) {
                        return new Pair<>(false, "Tài khoản, mật khẩu không được để trống");
                    }
                }
                case 4 -> {
                    if (input.isEmpty() || !isValidNumberTextField(input)) {
                        return new Pair<>(false, "SDT sai định dạng hoặc trống");
                    }
                }
                case 5 -> {
                    if (input.isEmpty()) {
                        return new Pair<>(false, "Địa chỉ không được để trống");
                    }
                }
                case 6 -> {
                    if (!isValidDate(input) || input.isEmpty()) {
                        return new Pair<>(false, "Ngày sinh sai định dạng hoặc trống");
                    }
                }
            }
        }

        return new Pair<>(true, "");
    }

    public boolean isValidDate(String date) {
        // idk what the fac this regex is
        return date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
    }

    public boolean isValidNumberTextField(String text){
        if (text.isEmpty()) return false;
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) < 48 || text.charAt(i) > 57) return false;
        }

        return true;
    }
}
