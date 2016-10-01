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
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;

/**
 *
 * @author hippo
 */
@Action("copySchedule")
public class CopyScheduleCmd implements Perform{

    ScheduleDelegate sd=new ScheduleDelegate();
    @Override
    public String perform(String string, HttpServletRequest hsr, HttpServletResponse hsr1) throws IOException, ServletException {
        try {
            String yearFromString=hsr.getParameter("yearFrom");
            String weekFromString=hsr.getParameter("weekFrom");
            String yearToString=hsr.getParameter("yearTo");
            String weekToString=hsr.getParameter("weekTo");
            String assignedByString=((User)hsr.getSession().getAttribute("user")).getId();
            int yearFrom=Integer.parseInt(yearFromString);
            int weekFrom=Integer.parseInt(weekFromString);
            int yearTo=Integer.parseInt(yearToString);
            int weekTo=Integer.parseInt(weekToString);
            sd.copySchedule(yearFrom, weekFrom, yearTo, weekTo, assignedByString);
            hsr.setAttribute("Message", "Success");
            
        } catch (IllegalArgumentException iae) {
            hsr.setAttribute("Message", "Error:"+iae.getMessage());
        }catch(Exception e){
             hsr.setAttribute("Message", "Error:"+e.getMessage());
             e.printStackTrace();
        }
        
        return "/pages/copyschedule.jsp";
    }
    
}
