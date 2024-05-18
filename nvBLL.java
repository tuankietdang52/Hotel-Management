/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
package BUS;
import DAO.nccDAO;
import DTO.nccDTO;
import java.util.ArrayList;
public class nccBLL {
    nccDAO empDAO = new nccDAO();
    
    public ArrayList<nccDTO> getAllNHACUNGCAP(){
        return empDAO.getAllNHACUNGCAP();
    }
    public String addNHACUNGCAP(nccDTO emp){
        if(empDAO.hasMancc(emp.getMancc())){
            return "ma da ton tai";
        }
        if(empDAO.addNHACUNGCAP(emp)){
            return "them thanh cong";
        }
        return "that bai";
    }
}
*/
package BUS;
import DTO.nvDTO;
import DAO.nvDAO;
import java.util.ArrayList;
public class nvBLL {
    nvDAO nvDAO = new nvDAO();
    
    public ArrayList<nvDTO> getAllNHANVIEN(){
        return nvDAO.getAllNHANVIEN();
    }
    public String addNHANVIEN(nvDTO nv){
        if(nvDAO.hasMaNV(nv.getMaNV())){
            return "ma da ton tai";
        }
        if(nvDAO.addNHANVIEN(nv)){
            return "them thanh cong";
        }
        return "that bai";
    }
}