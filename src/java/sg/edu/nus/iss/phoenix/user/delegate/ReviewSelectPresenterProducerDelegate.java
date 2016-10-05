/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.delegate;

import java.util.List;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.user.service.ReviewSelectPresenterProducerService;

/**
 *
 * @author Guo Qi
 */
public class ReviewSelectPresenterProducerDelegate {
    private ReviewSelectPresenterProducerService reviewSelectPresenterProducerService;
    public ReviewSelectPresenterProducerDelegate(){
        reviewSelectPresenterProducerService = new ReviewSelectPresenterProducerService();
    }
    
    /**
     * Delegate for getAllUser() method in ReviewSelectPresenterProducerService
     * @return List()
     */
    public List<User> getAllUser(){
        return reviewSelectPresenterProducerService.getAllUser();
    }
    
    /**
     * Delegate for getAllPresenter() method in ReviewSelectPresenterProducerService
     * @return List()
     */
    public List<User> getAllPresenter() {
        return reviewSelectPresenterProducerService.getAllPresenters();
    }
    
    /**
     * Delegate for getAllProducer() method in ReviewSelectPresenterProducerService
     * @return List()
     */
    public List<User> getAllProducer() {
        return reviewSelectPresenterProducerService.getAllProducers();
    }
    
    /**
     * Delegate for getUserById() method in ReviewSelectPresenterProducerService
     * @param uid
     * @return User
     */
    public User getUserById(String uid) {
        return reviewSelectPresenterProducerService.getUserById(uid);
    }
}
