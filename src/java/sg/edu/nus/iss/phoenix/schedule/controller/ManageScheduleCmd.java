/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hippo
 */
@Action("crudSchedule")
public class ManageScheduleCmd implements Perform{

    @Override
    public String perform(String string, HttpServletRequest hsr, HttpServletResponse hsr1) throws IOException, ServletException {
        return "/pages/crudschedule.jsp";
    }
    
}
