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
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;

/**
 *
 * @author hippo
 */
@Action("deleteSchedule")
public class DeleteScheduleCmd implements Perform{
    ScheduleDelegate sd=new ScheduleDelegate();
    @Override
    public String perform(String string, HttpServletRequest hsr, HttpServletResponse hsr1) throws IOException, ServletException {
      String id=hsr.getParameter("id");
      if(id==null||id.equals("")){
          hsr.setAttribute("message", "Id Is Empty");
      }else{
          hsr.setAttribute("message", sd.deleteProgramSlotById(id));
      }
      
      return "/pages/deleteschedule.jsp";
    }
    
}
