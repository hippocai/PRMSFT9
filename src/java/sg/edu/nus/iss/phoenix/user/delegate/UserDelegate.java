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
    
    public void processCreate(User user) {
        userService = new UserService();
        userService.processCreate(user);
    }
    
    public void processDelete(String uid){
        userService = new UserService();
        userService.processDelete(uid);
    }
    
    public void processModify(User user){
        userService = new UserService();
        userService.processModify(user);
    }
    
    public ArrayList<User> searchUser(User user) {
        userService = new UserService();
        return userService.searchUser(user);
    }
}
