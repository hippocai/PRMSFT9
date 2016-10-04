package sg.edu.nus.iss.phoenix.user.service;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sg.edu.nus.iss.phoenix.user.delegate.ReviewSelectPresenterProducerDelegate;

/**
 *
 * @author Guo Qi
 */
public class ReviewSelectPresenterProducerServiceTest {
    
    ReviewSelectPresenterProducerDelegate reviewSelectPresenterProducerDelegate;
    
    public ReviewSelectPresenterProducerServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        reviewSelectPresenterProducerDelegate = new ReviewSelectPresenterProducerDelegate();
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
    public void testGetAllUser(){
        try {
            Assert.assertTrue(!this.reviewSelectPresenterProducerDelegate.getAllUser().isEmpty());
            System.out.println("Size of all users:" + this.reviewSelectPresenterProducerDelegate.getAllUser().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetAllPresenter(){
        try{
            Assert.assertTrue(!this.reviewSelectPresenterProducerDelegate.getAllPresenter().isEmpty());
            System.out.println("Size of All Presenters: " + this.reviewSelectPresenterProducerDelegate.getAllPresenter().size());
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void testGetAllProducer(){
        try {
            Assert.assertTrue(!this.reviewSelectPresenterProducerDelegate.getAllProducer().isEmpty());
            System.out.println("Size of Producers: " + this.reviewSelectPresenterProducerDelegate.getAllProducer().size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
