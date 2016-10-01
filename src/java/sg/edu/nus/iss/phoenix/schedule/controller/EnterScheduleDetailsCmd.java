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
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;
import sg.edu.nus.iss.phoenix.schedule.service.JsonUtil;

/**
 *
 * @author hippo
 */
@Action("enterschedule")
public class EnterScheduleDetailsCmd implements Perform{
    ScheduleDelegate sd=new ScheduleDelegate();
    @Override
    public String perform(String string, HttpServletRequest hsr, HttpServletResponse hsr1) throws IOException, ServletException {
        String programSlotString=hsr.getParameter("jsonStr");
        String isInsertString=hsr.getParameter("isInsert");
        System.out.println("programSlot:"+programSlotString);
        System.out.println("isInsert"+isInsertString);
        if(isStringEmpty(programSlotString)||isStringEmpty(isInsertString)){
            this.setInfo("Error:The information is empty!", hsr);
        }else{
                this.setInfo(exec(programSlotString, isInsert(isInsertString),hsr), hsr);
        }
       return "/pages/insertscheduleresult.jsp";
    }
    private String exec(String Str,boolean insert,HttpServletRequest request){
        try{
            String assignedByString=((User)request.getSession().getAttribute("user")).getId();
            ProgramSlotBean psBean;
            psBean=(ProgramSlotBean)JsonUtil.getObject4JsonString(Str, ProgramSlotBean.class);
            String startTimeString=psBean.getStartTime();
            String durationString=psBean.getDuration();
            String dateString=psBean.getDuration();
            if(isTimeOccupied(dateString, startTimeString, durationString)){
                return "Error:Confliction existed";
            }
            if(insert){
                if(sd.insertProgramSlot(psBean, assignedByString)){
                    return "Success";
                }else{
                    return"Error";
                }
            }else{
                if(sd.deleteProgramSlotById(psBean.getId()).equalsIgnoreCase("Success")&&sd.insertProgramSlot(psBean, assignedByString)){
                    return "Success";
                }else{
                    return"Error";
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            return "Internal Error";
        }
    }
    
    private boolean isTimeOccupied(String dateString,String startTime,String duration){
        return false;
    }
    
    
    private void setInfo(String msgString,HttpServletRequest request){
        request.setAttribute("Message", msgString);
        if(msgString.contains("Error")){
            request.setAttribute("error", "true");
        }else{
            request.setAttribute("error", "false");
        }
    }
    
    private boolean isStringEmpty(String str){
        return str==null||str.isEmpty();
    }
    
    private boolean isInsert(String isInsertString){
        if(isInsertString==null){
            return true;
        }
        if(isInsertString.equalsIgnoreCase("false")){
            return false;
        }else{
            return true;
        }
    }
    
}
