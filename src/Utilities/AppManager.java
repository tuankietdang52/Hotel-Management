package Utilities;

import javax.swing.*;
import java.util.Random;

public class AppManager {
    public static String generateRandomCode(int length){
        String alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder code = new StringBuilder();

        Random rand = new Random();

        for (int i = 0; i < length; i++){
            code.append(alphabet.charAt(rand.nextInt(alphabet.length())));
        }

        return code.toString();
    }

    public static boolean showConfirmMessage(String message){
        int choice = JOptionPane.showConfirmDialog(null, message, "", JOptionPane.YES_NO_OPTION);

        return choice == JOptionPane.YES_OPTION;
    }

    public static void showPopUpMessage(String message){
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.INFORMATION_MESSAGE);
    }
}
