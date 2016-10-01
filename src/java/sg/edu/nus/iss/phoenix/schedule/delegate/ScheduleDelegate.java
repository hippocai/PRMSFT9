/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.delegate;

import java.util.List;
import net.sf.json.JSONArray;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

/**
 *
 * @author hippo
 */
public class ScheduleDelegate {
     ScheduleService scheduleService=new ScheduleService();
     public JSONArray getProgramSlotOfCurrentWeek(){
         return scheduleService.getProgramSlotOfCurrentWeek();
     }
     
     public JSONArray getAllExistingYears(){
         return scheduleService.getAllExistingYear();
     }
     
     public JSONArray getProgramSlotByYearAndWeek(String yearString,String weekString){
         return scheduleService.getProgramSlotByYearAndWeek(yearString, weekString);
     }
     
     public String deleteProgramSlotById(String id){
         return scheduleService.deleteProgramSlotById(id);
     }
     
     public boolean insertProgramSlot(ProgramSlotBean programSlotBean,String assignedBy){
         return scheduleService.insertProgramSlot(programSlotBean, assignedBy);
     }
     
     public ProgramSlotBean getProgramSlotById(String id){
         return scheduleService.getProgramSlotById(id);
     }
     
      public boolean copySchedule(int yearFrom,int weekFrom,int yearTo,int weekTo,String assignedBy)throws IllegalArgumentException{
          return scheduleService.copySchedule(yearFrom, weekFrom, yearTo, weekTo, assignedBy);
      }
      
      public List<ProgramSlotBean> getProgramSlotByDate(String dateOfProgram){
          return scheduleService.getProgramSlotByDate(dateOfProgram);
      }
}
