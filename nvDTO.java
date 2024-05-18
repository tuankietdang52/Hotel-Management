/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class nvDTO {
    private String maNV;
    private String tenDangNhap;
    private String matKhau;
    private String ho;
    private String ten;
    private String diaChi;
    private java.sql.Date ngaySinh;
    private String tenCongViec;
    private int luong;

//    public nvDTO(String maNV, String tenDangNhap, String matKhau, String ho, String ten, String diaChi, Date ngaySinh, String tenCongViec, int luong) {
//        this.maNV = maNV;
//        this.tenDangNhap = tenDangNhap;
//        this.matKhau = matKhau;
//        this.ho = ho;
//        this.ten = ten;
//        this.diaChi = diaChi;
//        this.ngaySinh = ngaySinh;
//        this.tenCongViec = tenCongViec;
//        this.luong = luong;
//    }

    
    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }

    public int getLuong() {
        return luong;
    }

    public void setLuong(int luong) {
        this.luong = luong;
    }
    
    
    
}
