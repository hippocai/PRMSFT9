/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.schedule.dao.TimeUtil;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;
import sg.edu.nus.iss.phoenix.schedule.service.JsonUtil;

/**
 *Enter Schedule Details Controller
 * @author CaiYicheng
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
    /**
     * Execute the add/modify
     * @param Str
     * @param insert
     * @param request
     * @return 
     */
    private String exec(String Str,boolean insert,HttpServletRequest request){
        try{
            String assignedByString=((User)request.getSession().getAttribute("user")).getId();
            ProgramSlotBean psBean;
            psBean=(ProgramSlotBean)JsonUtil.getObject4JsonString(Str, ProgramSlotBean.class);
            String startTimeString=psBean.getStartTime();
            String durationString=psBean.getDuration();
            String dateString=psBean.getDateOfProgram();
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
    
    /**
     * Check if there is some confliction of the program slot time
     * @param dateString
     * @param startTimeString
     * @param duration
     * @return 
     */
    private boolean isTimeOccupied(String dateString,String startTimeString,String duration){
        String timeString=dateString+" "+startTimeString;
        Date startTime=this.parseStringToDate(timeString);
      
        Date endTime=this.addDuration(startTime, duration);
        
        long startTimeMills=startTime.getTime();
        long endTimeMills=endTime.getTime();
        List<ProgramSlotBean> programSlotOfTheDay=sd.getProgramSlotByDate(dateString);
        for(ProgramSlotBean programSlot:programSlotOfTheDay){
            Date startCheckTimeDate=this.parseStringToDate(programSlot.getDateOfProgram()+" "+programSlot.getStartTime());
            Date endCheckTimeDate=this.addDuration(startTime, programSlot.getDuration());
            if(startTimeMills>startCheckTimeDate.getTime()&&startTimeMills<endCheckTimeDate.getTime()){
                return true;
            }
            if(endTimeMills>startCheckTimeDate.getTime()&&endTimeMills<endCheckTimeDate.getTime()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Add the duration to the date
     * @param startTime
     * @param duration
     * @return 
     */
    private Date addDuration(Date startTime,String duration){
          String[] durationArr=duration.split(":");
        int hour=Integer.parseInt(durationArr[0]);
        int minute=Integer.parseInt(durationArr[1]);
        Date endTime=TimeUtil.addHour(startTime, hour);
        endTime=TimeUtil.addMinute(endTime, minute);
        return endTime;
    }
    /**
     * Set the message info to the HttpServletRequest object
     * @param msgString
     * @param request 
     */
    private void setInfo(String msgString,HttpServletRequest request){
        request.setAttribute("Message", msgString);
        if(msgString.contains("Error")){
            request.setAttribute("error", "true");
        }else{
            request.setAttribute("error", "false");
        }
    }
    
    /**
     * Check if the string is empty
     * @param str
     * @return 
     */
    private boolean isStringEmpty(String str){
        return str==null||str.isEmpty();
    }
    
    /**
     * Check if the job is insert
     * @param isInsertString
     * @return 
     */
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

        /**
         * Parse the time String such to the date object
         * @param dateString yyyy-MM-dd HH:mm:ss
         * @return 
         */
         private Date parseStringToDate(String dateString){
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    	Date date = null;  
	    	try {  
	    	    date = format.parse(dateString); 
	    	    return date;
	    	} catch (ParseException e) {  
	    	    // TODO Auto-generated catch block  
	    	    e.printStackTrace();  
	    	    return null;
	    	}
	    }
    
}
