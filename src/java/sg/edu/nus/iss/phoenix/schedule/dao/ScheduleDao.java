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
     public List<ProgramSlotBean> getProgramSlotByYearAndWeek(int year,int week);
     public List<ProgramSlotBean> getCurrentWeekProgramSlots();
     public List<String>getAllExistingYear();
     public String deleteProgramSlotById(String id);
     public boolean insertProgramSlot(ProgramSlotBean programSlotBean,String assignedBy);
     public ProgramSlotBean getProgramSlotById(String id);
     public boolean copySchedule(int yearFrom,int weekFrom,int yearTo,int weekTo,String assignedBy)throws IllegalArgumentException;

     
}
