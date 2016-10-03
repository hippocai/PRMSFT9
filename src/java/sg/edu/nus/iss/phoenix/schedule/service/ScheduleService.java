/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.util.List;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactory;
import sg.edu.nus.iss.phoenix.core.dao.DAOFactoryImpl;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDao;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDaoImpl;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;

/**
 *The service for schedule
 * @author CaiYicheng
 */
public class ScheduleService {
    DAOFactory df=new DAOFactoryImpl();
    /**
     * The Constructor of the schedule service
     */
    public ScheduleService(){
        super();
    }
    
    /**
     * Get the program slots of current week
     * @return 
     */
    public JSONArray getProgramSlotOfCurrentWeek(){
        ScheduleDao scheduleDao=df.getScheduleDao();
        List<ProgramSlotBean>resultSlotBeans=scheduleDao.getCurrentWeekProgramSlots();
        return JsonUtil.getJson4JavaList(resultSlotBeans);
    }
    
    /**
     * Get all existing year from database
     * @return 
     */
    public JSONArray getAllExistingYear(){
        ScheduleDao scheduleDao=df.getScheduleDao();
        return JsonUtil.getJson4JavaList(scheduleDao.getAllExistingYear());
    }
    
    /**
     * Get program slot by year and week
     * @param yearString
     * @param weekString
     * @return 
     */
    public JSONArray getProgramSlotByYearAndWeek(String yearString,String weekString){
        ScheduleDao scheduleDao=df.getScheduleDao();
        int year=Integer.parseInt(yearString);
        int week=Integer.parseInt(weekString);
        List<ProgramSlotBean> resultList=scheduleDao.getProgramSlotByYearAndWeek(year, week);
        if(resultList==null||resultList.isEmpty()){
            return null;
        }
        return JsonUtil.getJson4JavaList(resultList);
    }
    
    /**
     * Get program slot by the program slot ID
     * @param id
     * @return 
     */
    public ProgramSlotBean getProgramSlotById(String id){
        ScheduleDao scheduleDao=df.getScheduleDao();
        return scheduleDao.getProgramSlotById(id);
    }
    
    /**
     * Delete program slot by program slot ID
     * @param id
     * @return 
     */
    public String deleteProgramSlotById(String id){
        ScheduleDao scheduleDao=df.getScheduleDao();
        return scheduleDao.deleteProgramSlotById(id);
    }
    
    /**
     * Insert the program slot
     * @param programSlotBean
     * @param assignedBy
     * @return 
     */
    public boolean insertProgramSlot(ProgramSlotBean programSlotBean,String assignedBy){
        ScheduleDao scheduleDao=df.getScheduleDao();
        return scheduleDao.insertProgramSlot(programSlotBean, assignedBy);
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
         ScheduleDao scheduleDao=df.getScheduleDao();
         return scheduleDao.copySchedule(yearFrom, weekFrom, yearTo, weekTo, assignedBy);
     }
     
     /**
      * Get program slot by date
      * @param date
      * @return 
      */
     public List<ProgramSlotBean>getProgramSlotByDate(String date){
         ScheduleDao scheduleDao=df.getScheduleDao();
         return scheduleDao.getProgramSlotByDate(date);
     }
}
