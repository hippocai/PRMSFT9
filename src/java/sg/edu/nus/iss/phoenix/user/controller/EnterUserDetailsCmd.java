/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.user.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Action("enteruser")
public class EnterUserDetailsCmd implements Perform{

    @Override
    public String perform(String path, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserDelegate userDelegate = new UserDelegate();
        ReviewSelectPresenterProducerDelegate reviewSelectPresenterProducerDelegate = new ReviewSelectPresenterProducerDelegate();
        User user = new User();
        user.setAll(req.getParameter("id"), req.getParameter("password"), req.getParameter("name"), req.getParameter("role"));
        String insert = (String)req.getParameter("insert");
        Logger.getLogger(getClass().getName()).log(Level.INFO, "Insert Flag: " + insert);
        if (insert.equalsIgnoreCase("true")) {
            userDelegate.processCreate(user);
        }else{
            userDelegate.processModify(user);
        }
        List<User> userData = reviewSelectPresenterProducerDelegate.getAllUser();
        req.setAttribute("user", userData);
        return "/pages/cruduser.jsp";
    }
    
    
}
