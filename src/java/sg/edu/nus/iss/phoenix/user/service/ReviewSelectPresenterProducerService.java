/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sg.edu.nus.iss.phoenix.authenticate.dao.RoleDao;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.Role;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.core.exceptions.NotFoundException;
import sg.edu.nus.iss.phoenix.radioprogram.service.ReviewSelectProgramService;

/**
 *
 * @author Guo Qi
 */
public class ReviewSelectPresenterProducerService {
    DAOFactoryImpl factory;
    UserDao userDao;
    
    public ReviewSelectPresenterProducerService(UserDao userDao){
        this.userDao = userDao;
    }
    
    public ReviewSelectPresenterProducerService(){
        super();
	// TODO Auto-generated constructor stub
	factory = new DAOFactoryImpl();
        userDao = factory.getUserDAO();
        
    }
    
    /**
     * This method is used for getting all the users
     * @return List<User>
     */
    public List<User> getAllUser(){
        List<User> userData = null;
        try {
            userData = userDao.loadAll();
        } catch (Exception e) {
             Logger.getLogger(ReviewSelectPresenterProducerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return userData;
    }
    
    /**
     * This method is used for getting users by user's id
     * @param uid
     * @return user
     */
    public User getUserById(String uid){
        User user = null;
        try {
            user = userDao.getObject(uid);
        }catch(NotFoundException e){
            System.out.println("FUCKYOU!!!");
        } 
        catch (Exception e) {
            Logger.getLogger(ReviewSelectPresenterProducerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return user;
    }
    
    /**
     * This method is used for getting all the presenters
     * @return 
     */
    public List<User> getAllPresenters(){
        List<User> presenterData = null;
        try {
            ArrayList<Role> role = new ArrayList<Role>();
            role.add(new Role("presenter"));
            User user = new User();
            user.setRoles(role);
            presenterData = userDao.searchMatching(user);
            
        } catch (Exception e) {
            Logger.getLogger(ReviewSelectPresenterProducerService.class.getName()).log(Level.SEVERE, null, e);
        }
        return presenterData;
    }
    
    /**
     * This method is used for getting all the producers
     * @return 
     */
    public List<User> getAllProducers(){
        List<User> producerData = null;
        try {
            ArrayList<Role> role = new ArrayList<Role>();
            role.add(new Role("producer"));
            User user = new User();
            user.setRoles(role);
            producerData = userDao.searchMatching(user);
        } catch (Exception e) {
            Logger.getLogger(ReviewSelectPresenterProducerService.class.getName()).log(Level.SEVERE,null,e);
        }
        return producerData;
    }
}
