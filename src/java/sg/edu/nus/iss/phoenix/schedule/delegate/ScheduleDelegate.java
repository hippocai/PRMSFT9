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
 *The Schedule Service Delegate
 * @author CaiYicheng
 */
public class ScheduleDelegate {
     ScheduleService scheduleService=new ScheduleService();
     /**
      * Get the program slot of current week
      * @return 
      */
     public JSONArray getProgramSlotOfCurrentWeek(){
         return scheduleService.getProgramSlotOfCurrentWeek();
     }
     
     /**
      * Get all the existing years of current week
      * @return 
      */
     public JSONArray getAllExistingYears(){
         return scheduleService.getAllExistingYear();
     }
     
     /**
      * Get programslots by year and week
      * @param yearString
      * @param weekString
      * @return 
      */
     public JSONArray getProgramSlotByYearAndWeek(String yearString,String weekString){
         return scheduleService.getProgramSlotByYearAndWeek(yearString, weekString);
     }
     
     /**
      * delete program slot by id
      * @param id
      * @return 
      */
     public String deleteProgramSlotById(String id){
         return scheduleService.deleteProgramSlotById(id);
     }
     
     /**
      * Insert program slot
      * @param programSlotBean
      * @param assignedBy
      * @return 
      */
     public boolean insertProgramSlot(ProgramSlotBean programSlotBean,String assignedBy){
         return scheduleService.insertProgramSlot(programSlotBean, assignedBy);
     }
     
     /**
      * Get program slot by id
      * @param id
      * @return 
      */
     public ProgramSlotBean getProgramSlotById(String id){
         return scheduleService.getProgramSlotById(id);
     }
     
     /**
      * Copy the schedule
      * @param yearFrom
      * @param weekFrom
      * @param yearTo
      * @param weekTo
      * @param assignedBy
      * @return
      * @throws IllegalArgumentException 
      */
      public boolean copySchedule(int yearFrom,int weekFrom,int yearTo,int weekTo,String assignedBy)throws IllegalArgumentException{
          return scheduleService.copySchedule(yearFrom, weekFrom, yearTo, weekTo, assignedBy);
      }
      
      /**
       * Get program slot by date
       * @param dateOfProgram
       * @return 
       */
      public List<ProgramSlotBean> getProgramSlotByDate(String dateOfProgram){
          return scheduleService.getProgramSlotByDate(dateOfProgram);
      }
}
