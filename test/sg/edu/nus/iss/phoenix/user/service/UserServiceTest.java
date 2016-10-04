/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import sg.edu.nus.iss.phoenix.authenticate.dao.UserDao;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;

/**
 *
 * @author Guo Qi
 */
public class UserServiceTest {
    
    public UserServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of searchUser method, of class UserService.
     */
    @Test
    public void testSearchUser() {
        try {
            System.out.println("searchUser");
            ArrayList<User> userList = new ArrayList<>();
            User user = new User();
            user.setAll("123", "guoqi","guoqi", "manager");
            UserDao userDao = mock(UserDao.class);
            when(userDao.searchMatching(user)).thenReturn(userList);
            UserService instance = new UserService();
            ArrayList<User> result = instance.searchUser(user);
            
            assertEquals(result.get(0).getName(), "guoqi");
        } catch (SQLException ex) {
            Logger.getLogger(UserServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Test of processCreate method, of class UserService.
     */
    @Test
    public void testProcessCreate() {
        System.out.println("processCreate");
        User user = new User();
        user.setAll("111", "123","hippo", "manager");
        UserService instance = new UserService();
        instance.processCreate(user);
        assertEquals(instance.searchUser(user).get(0).getName(), "hippo");
    }

    
    
    
    
    /**
     * Test of processDelete method, of class UserService.
     */
    @Test
    public void testProcessDelete() {
        System.out.println("processDelete");
        String uid = "111";
        UserService instance = new UserService();
        instance.processDelete(uid);
    }


    
}
