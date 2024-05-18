/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;
//import tongketform.form.tabTongKet;
//import javax.swing.*;
import DAO.khDAO;
import DTO.khDTO;
//import DTO.khDTO;
import java.util.ArrayList;
//import javax.swing.table.DefaultTableModel;
public class khBLL {
//    tabTongKet tb = new tabTongKet();
    khDAO empDAO = new khDAO();
    
    public ArrayList<khDTO> getAllKHACHHANG(){
        return empDAO.getAllKHACHHANG();
    }
    public String addKHACHHANG(khDTO emp){
        if(empDAO.hasMaKH(emp.getMaKH())){
            return "ma da ton tai";
        }
        if(empDAO.addKHACHHANG(emp)){
            return "them thanh cong";
        }
        return "that bai";
    }
    
}
