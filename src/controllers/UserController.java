/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import com.mysql.jdbc.PreparedStatement;
import utils.DatabaseUtil;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;
import models.User;

/**
 *
 * @author Dedi
 */
public class UserController {
    private DatabaseUtil koneksi;
    private ResultSet rs;
    private PreparedStatement pre;
    
    public UserController(){
        koneksi = new DatabaseUtil();
    }
    
    public ArrayList<User> getAll(){
        ArrayList<User> result = new ArrayList<User>();
        rs = koneksi.executeSelect("SELECT * FROM users");
        if(rs != null){
            try{
                while(rs.next()){
                    Timestamp timestamp = rs.getTimestamp(5);
                    User pengguna = new User(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            timestamp.toLocalDateTime()
                        );
                    result.add(pengguna);
                }
            } catch (Exception ex){
                
            }
        }
        return result;
    }
    
    public User getById(int user_id){
        ArrayList<String> data = new ArrayList<String>();
        data.add(String.valueOf(user_id));
        User pengguna = null;
        
        rs = koneksi.executeSelect("SELECT * FROM users WHERE id = 7", data);
        if(rs != null){
            try{
                while(rs.next()){
                    Timestamp timestamp = rs.getTimestamp(5);
                    pengguna = new User(
                            Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            timestamp.toLocalDateTime()
                        );
                    break;
                } 
            }catch (Exception ex){
                
            }
        }
        return pengguna;
    }
    
    public Boolean update(String user_id, String name, String username, String password){
        ArrayList<String> data = new ArrayList<String>();
        data.add(name);
        data.add(username);
        data.add(username);
        data.add(user_id);
        if (koneksi.execute("UPDATE users SET name = ?, username ?," + "password = ? WHERE id = ?", data)){
            return true;
        } else {
            return false;
        }
    }
    
    public Boolean delete(String user_id){
        ArrayList<String> data = new ArrayList<String>();
        data.add(user_id);
        if (koneksi.execute("DELETE FROM users WHERE id = ?", data)){
            return true;
        } else {
            return false;
        }
    }
    
    public Boolean insert(User pengguna){
        ArrayList<String> data = new ArrayList<String>();
        if (pengguna.getId() != 0){
            data.add(String.valueOf(pengguna.getId()));
        }
        data.add(pengguna.getName());
        data.add(pengguna.getUsername());
        data.add(pengguna.getPassword());
        
        if(pengguna.getId() != 0){
            if (koneksi.execute("INSERT INTO users (id, name, username, password) VALUES (?, ?, ?)", data)){
                return true;
            } else {
                return false;
            }
        }else{
            if (koneksi.execute("INSERT INTO users (name, username, password) VALUES (?, ?, ?)", data)){
                return true;
            } else {
                return false;
            }
        } 
    }
    
    public User getLogin(String username, String password){
        ArrayList<String> data = new ArrayList<String>();
        data.add(String.valueOf(username));
        data.add(String.valueOf(password));
        User pengguna = null;
        
        rs = koneksi.executeSelect("SELECT * FROM users WHERE username = ? AND password = ?", data);
        if (rs != null){
            try{
                while (rs.next()){
                    Timestamp timestamp = rs.getTimestamp(5);
                    pengguna = new User(Integer.parseInt(rs.getString(1)),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            timestamp.toLocalDateTime()
                    );
                    break;
                }
            } catch(Exception e){
                
            }
        }
         return pengguna;
}
    
    public void close(){
        koneksi.stop();
    }
}
    
   
    



    