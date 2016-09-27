/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.controller;

import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.user.delegate.ReviewSelectPresenterProducerDelegate;
import sg.edu.nus.iss.phoenix.user.delegate.UserDelegate;

/**
 *
 * @author Guo Qi
 */
public class DeleteUserCmd implements Perform{

    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserDelegate userDelegate = new UserDelegate();
        String uid = req.getParameter("id");
        userDelegate.processDelete(uid);
        ReviewSelectPresenterProducerDelegate reviewSelectPresenterProducerDelegate = new ReviewSelectPresenterProducerDelegate();
        List<User> userData = reviewSelectPresenterProducerDelegate.getAllUser();
        req.setAttribute("user", userData);
        return "/pages/cruduser.jsp";
    }
    
}
