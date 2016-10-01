/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import sg.edu.nus.iss.phoenix.schedule.dao.TimeUtil;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;

/**
 *
 * @author hippo
 */
@Action("crudSchedule")
public class ManageScheduleCmd implements Perform{
    ScheduleDelegate sd=new ScheduleDelegate();
    @Override
    public String perform(String string, HttpServletRequest request, HttpServletResponse hsr1) throws IOException, ServletException {
        
        String yearString=request.getParameter("year");
        String weekString=request.getParameter("week");
        if(yearString==null||yearString.equals("")){
            yearString=TimeUtil.getCurrentYear()+"";
            weekString=TimeUtil.getWeekOfYear(new Date())+"";
        }
        if(yearString!=null&&!yearString.equals("")&&(weekString==null||weekString.equals(""))){
            weekString=-1+"";
        }
         JSONArray programSlotsResultArray=sd.getProgramSlotByYearAndWeek(yearString, weekString);
         if(programSlotsResultArray==null){
             request.setAttribute("programSlots","null");
         }else{
             System.out.println(programSlotsResultArray.toString());
             request.setAttribute("programSlots",programSlotsResultArray.toString());
         }
        
        request.setAttribute("allYears", sd.getAllExistingYears().toString());
        request.setAttribute("selectedYear", yearString);
        request.setAttribute("selectedWeek", weekString);
        
        return "/pages/crudschedule.jsp";
    }
    

    
    
}
