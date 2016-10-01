/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.user.delegate.ReviewSelectPresenterProducerDelegate;
import sg.edu.nus.iss.phoenix.user.service.UserService;

/**
 *
 * @author Guo Qi
 */
public class CRUDUserTest {
    
    UserService userService;
    ReviewSelectPresenterProducerDelegate reviewSelectPresenterProducerDelegate;
    User user;
    User userUpdate;
       
    
    public CRUDUserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        userService = new UserService();
        reviewSelectPresenterProducerDelegate = new ReviewSelectPresenterProducerDelegate();
        user = new User();
        userUpdate = new User();
        user.setAll("123", "123","guoqi", "manager");
        userUpdate.setAll("123", "123", "qiGuo", "presenter");
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void ProcessCreateAndModifyAndDeleteUser(){
        try {
           this.userService.processCreate(user);
           Assert.assertTrue(this.reviewSelectPresenterProducerDelegate.getUserById("123").getRoleString().equals("manager"));
           System.out.println("name: " + this.reviewSelectPresenterProducerDelegate.getUserById("123").getName());
              this.userService.processModify(userUpdate);
            Assert.assertTrue(this.reviewSelectPresenterProducerDelegate.getUserById("123").getRoleString().equals("presenter"));
            System.out.println("new name: " + this.reviewSelectPresenterProducerDelegate.getUserById("123").getName());
              this.userService.processDelete("123");
           // Assert.assertTrue(this.reviewSelectPresenterProducerDelegate.getUserById("123") == null);
            System.out.println("Delete Succesfully");
           
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

}
