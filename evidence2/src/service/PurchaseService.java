/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import connection.MySqlDbConnection;
import domain.Purchase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class PurchaseService {

    static Connection conn = MySqlDbConnection.getConnection();

    public static void createTable() {
        String sql = "create table purchase(id int auto_increment primary key, name varchar(30), price double, date Date)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            System.out.println("Table created!");
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void insert(Purchase p) {
        String sql = "insert into purchase(name,price,date) values(?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setDate(3, new java.sql.Date(p.getDate().getTime()));
            ps.executeUpdate();
            System.out.println("Data Inserted!");
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update(Purchase p) {
        String sql = "update purchase set name = ?, price = ? where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getId());
            ps.executeUpdate();
            System.out.println("Data Updated!");
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void delete(Purchase p) {
        String sql = "delete from purchase where id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, p.getId());
            ps.executeUpdate();
            System.out.println("data deleted");
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Purchase> getPurchaseList() {
        List<Purchase> list = new ArrayList<>();
        String sql = "select * from purchase";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Purchase p = new Purchase();
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getDouble(3));
                p.setDate(rs.getDate(4));
                list.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public static Purchase getPurchaseByProductId(String purchaseId) {
        Purchase p = new Purchase();
        String sql = "select * from purchase where id=?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, purchaseId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                p.setId(rs.getInt(1));
                p.setName(rs.getString(2));
                p.setPrice(rs.getDouble(3));
                p.setDate(rs.getDate(4));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PurchaseService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }
    
    public static String getStringFromDate(Date date) {
        date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    
    
}
