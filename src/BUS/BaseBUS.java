package BUS;

import Utilities.AppManager;
import Utilities.Pair;

import java.util.ArrayList;

public abstract class BaseBUS<T> {
    public abstract Pair<Boolean, String> checkValidInput(T input);

    public abstract ArrayList<ArrayList<String>> getDataTable();

    public boolean isValidDate(String date) {
        // idk what this regex is
        return date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$");
    }

    public boolean isValidNumberTextField(String text, String type){
        type = type.toLowerCase();
        try{
            if (type.equals("int")) Integer.parseInt(text);
            else Double.parseDouble(text);

            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public boolean isValidNumberTextField(String text, String type, String message){
        type = type.toLowerCase();
        try{
            if (type.equals("int")) Integer.parseInt(text);
            else Double.parseDouble(text);

            return true;
        }
        catch (Exception ex){
            AppManager.showPopUpMessage(message);
            return false;
        }
    }

    public boolean isValidPhone(String phone){
        if (phone.length() != 10) return false;
        return phone.charAt(0) == '0';
    }
}
