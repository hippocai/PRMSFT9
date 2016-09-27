/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;

/**
 *
 * @author Guo Qi
 */
public class UserService {
    DAOFactoryImpl factory;
    UserDao userDao;
    
    public UserService(){
        super();
        factory = new DAOFactoryImpl();
        userDao = factory.getUserDAO();
    }
    
    //search
    public ArrayList<User> searchUser(User user){
        ArrayList<User> list = new ArrayList<User>();
        try {
            list = (ArrayList<User>) userDao.searchMatching(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    //create
    public void processCreate(User user){
        try {
            userDao.create(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //delete
    public void processDelete(String uid){
        try {
            User user = new User();
            userDao.delete(user);
        } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    
    //modify
    public void processModify(User user){
        try {
            userDao.save(user);
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	} catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
    }
            
}
