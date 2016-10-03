/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao;

import java.util.List;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;

/**
 *
 * @author hippo
 */
public interface ScheduleDao {
    
    /**
     * Get program slot by year and week
     * @param year
     * @param week
     * @return 
     */
     public List<ProgramSlotBean> getProgramSlotByYearAndWeek(int year,int week);
    
     /**
      * Get all the program slots of current week
      * @return 
      */
     public List<ProgramSlotBean> getCurrentWeekProgramSlots();
     /**
      * Get all existing year of Anual Schedule
      * @return 
      */
     public List<String>getAllExistingYear();
     /**
      * Delete the program slot by ID
      * @param id
      * @return 
      */
     public String deleteProgramSlotById(String id);
     /**
      * Insert program slot
      * @param programSlotBean
      * @param assignedBy
      * @return 
      */
     public boolean insertProgramSlot(ProgramSlotBean programSlotBean,String assignedBy);
     /**
      * Get program slot by ID
      * @param id
      * @return 
      */
     public ProgramSlotBean getProgramSlotById(String id);
     /**
      * Copy the schedule of week
      * @param yearFrom
      * @param weekFrom
      * @param yearTo
      * @param weekTo
      * @param assignedBy
      * @return
      * @throws IllegalArgumentException 
      */
     public boolean copySchedule(int yearFrom,int weekFrom,int yearTo,int weekTo,String assignedBy)throws IllegalArgumentException;
     
     /**
      * Get the program slot by date
      * @param date
      * @return 
      */
     public List<ProgramSlotBean>getProgramSlotByDate(String date);

     
}
