/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sg.edu.nus.iss.phoenix.core.dao.BaseDao;
import sg.edu.nus.iss.phoenix.schedule.entity.AnnualScheduleBean;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;
import sg.edu.nus.iss.phoenix.schedule.entity.WeeklyScheduleBean;
import sg.edu.nus.iss.phoenix.schedule.service.JsonUtil;

/**
 *The implementation of the schedule dao
 * @author EasonChua
 */
public class ScheduleDaoImpl implements ScheduleDao{

    private BaseDao annualScheDao=new BaseDao();
    private BaseDao weeklyScheDao=new BaseDao();
    private BaseDao programSlotDao=new BaseDao();
/**
 * The constructor of the Schedule Dao
 */    
    public ScheduleDaoImpl(){
       initAnnualScheduleDao();
       initWeeklyScheduleDao();
       initProgramSlotDao();
    }
    
    /**
     * Init the annual schedule dao
     */
    private void initAnnualScheduleDao(){
       annualScheDao.setIdField("id");
       annualScheDao.setResultClass(AnnualScheduleBean.class);
       annualScheDao.setTableName("annual-schedule");
       annualScheDao.nameMap("id", "id");
       annualScheDao.nameMap("year", "year");
       annualScheDao.nameMap("assignedBy", "assingedBy");
    }
    
    /**
     * init the weekly schedule dao
     */
    private void initWeeklyScheduleDao(){
        weeklyScheDao.setIdField("id");
        weeklyScheDao.setResultClass(WeeklyScheduleBean.class);
        weeklyScheDao.setTableName("weekly-schedule");
        weeklyScheDao.nameMap("id","id");
        weeklyScheDao.nameMap("annualId", "annual_id");
        weeklyScheDao.nameMap("startDate", "startDate");
        weeklyScheDao.nameMap("assignedBy", "assignedBy");
    }
    /**
     * Init the program slot dao
     */
    private void initProgramSlotDao(){
        programSlotDao.setTableName("program-slot");
        programSlotDao.setResultClass(ProgramSlotBean.class);
        programSlotDao.setIdField("id");
        programSlotDao.nameMap("id", "id");
        programSlotDao.nameMap("weeklyScheduleId", "weekly_schedule_id");
        programSlotDao.nameMap("presenter", "presenter");
        programSlotDao.nameMap("producer", "producer");
        programSlotDao.nameMap("duration", "duration");
        programSlotDao.nameMap("dateOfProgram", "dateOfProgram");
        programSlotDao.nameMap("startTime", "startTime");
        programSlotDao.nameMap("programName", "program-name");
    }
    
    /**
     * Get program slot by year and week
     * @param year
     * @param week
     * @return 
     */
     @Override
    public List<ProgramSlotBean> getProgramSlotByYearAndWeek(int year,int week){
        Map<String,String> annualQueryMap=new HashMap<>();
        annualQueryMap.put("year", year+"");
        List<AnnualScheduleBean> annualScheList=annualScheDao.select(annualQueryMap);
        if(annualScheList==null||annualScheList.size()==0){
            return null;
        }
        AnnualScheduleBean annualBean=annualScheList.get(0);
        String annualScheduleId=annualBean.getId();
        Map<String,String>weeklyScheQueryMap=new HashMap<>();
        weeklyScheQueryMap.put("annualId", annualScheduleId);
        if(week!=-1){
            weeklyScheQueryMap.put("startDate", TimeUtil.parseDate2String(TimeUtil.getFirstDayOfWeek(year, week)));
        }
        List<WeeklyScheduleBean> weeklyScheList=weeklyScheDao.select(weeklyScheQueryMap);
        if(weeklyScheList==null||weeklyScheList.size()==0){
            return null;
        }
        
        List<ProgramSlotBean> programSlots=new ArrayList<>();
        for(WeeklyScheduleBean weeklyScheBean:weeklyScheList){
            Map<String,String>programSlotMap=new HashMap<>();
            programSlotMap.put("weeklyScheduleId", weeklyScheBean.getId());
            List<ProgramSlotBean>searchResultBeans=programSlotDao.select(programSlotMap);
            if(searchResultBeans!=null){
                programSlots.addAll(searchResultBeans);
            }
        }
         System.out.println(JsonUtil.getJson4JavaList(programSlots).toString());
        return programSlots;
    }
    
    /**
     * Get the programslots of current week
     * @return 
     */
    @Override
     public List<ProgramSlotBean> getCurrentWeekProgramSlots(){
         int year=TimeUtil.getCurrentYear();
         int week=TimeUtil.getWeekOfYear(new Date());
         return this.getProgramSlotByYearAndWeek(year, week);
         
     }

     /**
      * Get all the existing year in the database
      * @return 
      */
    @Override
    public List<String> getAllExistingYear() {
       List<AnnualScheduleBean>allAnnualSchedule=annualScheDao.select(null);
       List<String>allYears=new ArrayList<>();
       for(AnnualScheduleBean annBean:allAnnualSchedule){
           allYears.add(annBean.getYear());
       }
       return allYears;
       
    }

    /**
     * Delete the programslot by id
     * @param id
     * @return 
     */
    @Override
    public String deleteProgramSlotById(String id) {
       
            Map<String,String>map=new HashMap<>();
            map.put("id", id);
            if(programSlotDao.delete(map)){
                return "Success";
            }else{
                 return "Error";
            }
           
   
    }

    /**
     * Insert the program slot
     * @param programSlotBean
     * @param assignedBy
     * @return 
     */
    @Override
    public boolean insertProgramSlot(ProgramSlotBean programSlotBean, String assignedBy) {
        String dateString=programSlotBean.getDateOfProgram();
        Date date=TimeUtil.parseStringToDate(dateString);
        int year=TimeUtil.getYear(date);
        int week=TimeUtil.getWeekOfYear(date);
        String firstDayOfWeekString=TimeUtil.parseDate2String(TimeUtil.getFirstDayOfWeek(date));
        String annualScheduleIdString=this.getAnnualScheduleIdByYear(year);
        if(isStringEmpty(annualScheduleIdString)){
            annualScheduleIdString=this.createAnnualScheduleAndGetId(year, assignedBy);
        }
        String weeklyScheduleIdString=this.getWeeklyScheduleIdByWeek(firstDayOfWeekString, annualScheduleIdString);
        if(isStringEmpty(weeklyScheduleIdString)){
            weeklyScheduleIdString=this.createWeeklyScheduleAndGetId(firstDayOfWeekString, assignedBy, annualScheduleIdString);
        }
        programSlotBean.setWeeklyScheduleId(weeklyScheduleIdString);
        return programSlotDao.insert(programSlotBean);
    }
    
    /**
     * Get the annual schedule id by Year
     * @param year
     * @return 
     */
     public String getAnnualScheduleIdByYear(int year){
         Map<String,String>map=new HashMap<>();
         map.put("year", year+"");
         List<AnnualScheduleBean> annualScheduleBeans=annualScheDao.select(map);
         if(annualScheduleBeans!=null&&annualScheduleBeans.size()>0){
             return annualScheduleBeans.get(0).getId();
         }
         return null;
     }
     
     /**
      * Create annual schedule
      * @param year
      * @param assignedBy
      * @return ID String of the annual schedule which is created just now
      */
     public String createAnnualScheduleAndGetId(int year,String assignedBy){
         AnnualScheduleBean annualScheduleBean=new AnnualScheduleBean();
         annualScheduleBean.setYear(year+"");
         annualScheduleBean.setAssignedBy(assignedBy);
         annualScheDao.insert(annualScheduleBean);
         return this.getAnnualScheduleIdByYear(year);
     }
     
     /**
      * Get the weekly schedule by the Date
      * @param startTimeString
      * @param annualScheduleIdString
      * @return 
      */
     public String getWeeklyScheduleIdByWeek(String startTimeString,String annualScheduleIdString){
         Map<String,String>map=new HashMap<>();
         map.put("startDate", startTimeString);
         map.put("annualId", annualScheduleIdString);
         List<WeeklyScheduleBean> weeklyScheduleBeans=weeklyScheDao.select(map);
         if(weeklyScheduleBeans==null||weeklyScheduleBeans.isEmpty()){
             return null;
         }
         return weeklyScheduleBeans.get(0).getId();
     }
     
     /**
      * Create the weekly schedule
      * @param startTime
      * @param assignedBy
      * @param annualScheduleIdString
      * @return The IDString of the weekly schedule which is created just now
      */
     public String createWeeklyScheduleAndGetId(String startTime,String assignedBy,String annualScheduleIdString){
         WeeklyScheduleBean weeklyScheduleBean=new WeeklyScheduleBean();
         weeklyScheduleBean.setAnnualId(annualScheduleIdString);
         weeklyScheduleBean.setAssignedBy(assignedBy);
         weeklyScheduleBean.setStartDate(startTime);
         weeklyScheDao.insert(weeklyScheduleBean);
         return this.getWeeklyScheduleIdByWeek(startTime, annualScheduleIdString);
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
     * Get the program slot by ID
     * @param id
     * @return 
     */
    @Override
    public ProgramSlotBean getProgramSlotById(String id) {
       Map<String,String> map=new HashMap<>();
       map.put("id", id);
       List<ProgramSlotBean> programSlotBeans=programSlotDao.select(map);
       if(programSlotBeans!=null&&!programSlotBeans.isEmpty()){
           return programSlotBeans.get(0);
       }else{
           return null;
       }
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
    @Override
    public boolean copySchedule(int yearFrom, int weekFrom, int yearTo, int weekTo, String assignedBy)throws IllegalArgumentException {
       List<ProgramSlotBean> programSlotBeanTo=this.getProgramSlotByYearAndWeek(yearTo, weekTo);
       if(programSlotBeanTo!=null&&programSlotBeanTo.size()>0){
           throw new IllegalArgumentException("The week is not empty");
       }
       List<ProgramSlotBean>programSlotBeans=this.getProgramSlotByYearAndWeek(yearFrom, weekFrom);
       for(ProgramSlotBean programSlotBean:programSlotBeans){
           programSlotBean.setDateOfProgram(TimeUtil.changeDateWeek(programSlotBean.getDateOfProgram(), yearTo, weekTo));
       }
       for(ProgramSlotBean programSlotBean:programSlotBeans){
           this.insertProgramSlot(programSlotBean, assignedBy);
       }
       return true;
    }

    /**
     * Get the program slots by date
     * @param date
     * @return 
     */
    @Override
    public List<ProgramSlotBean> getProgramSlotByDate(String date) {
        Map<String,String> map=new HashMap<>();
       map.put("dateOfProgram",date);
       List<ProgramSlotBean> programSlotBeans=programSlotDao.select(map);

      return programSlotBeans;
    }
}
