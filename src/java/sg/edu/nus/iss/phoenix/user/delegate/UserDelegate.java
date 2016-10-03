/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.delegate;

import java.util.ArrayList;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.user.service.UserService;

/**
 *
 * @author Guo Qi
 */
public class UserDelegate {
    private UserService userService;
    
    /**
     * Delegate for processCreate() method in UserService
     * @param user 
     */
    public void processCreate(User user) {
        userService = new UserService();
        userService.processCreate(user);
    }
    
    /**
     * Delegate for processDelete() method in UserService
     * @param uid 
     */
    public void processDelete(String uid){
        userService = new UserService();
        userService.processDelete(uid);
    }
    
    /**
     * Delegate for processModify() method in UserService
     * @param user 
     */
    public void processModify(User user){
        userService = new UserService();
        userService.processModify(user);
    }
    
    /**
     * Delegate for searchUser() method in UserService
     * @param user
     * @return ArrayList<User>
     */
    public ArrayList<User> searchUser(User user) {
        userService = new UserService();
        return userService.searchUser(user);
    }
}
