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
    
    public UserService(UserDao userDao){
        this.userDao = userDao;
    }
    
    public UserService(){
        super();
        factory = new DAOFactoryImpl();
        userDao = factory.getUserDAO();
    }
    
    /**
     * This function is used for search users
     * @param user
     * @return ArrayList<User>
     */
    public ArrayList<User> searchUser(User user){
        ArrayList<User> list = new ArrayList<User>();
        try {
            list = (ArrayList<User>) userDao.searchMatching(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * This method is used for processing create user function
     * @param user 
     */
    public void processCreate(User user){
        try {
            userDao.create(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method is used for processing delete user function
     * @param uid 
     */
    public void processDelete(String uid){
        try {
            User user = new User(uid);
            userDao.delete(user);
        } catch (NotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
    
    /**
     * This method is used for processing modify user function
     * @param user 
     */
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
