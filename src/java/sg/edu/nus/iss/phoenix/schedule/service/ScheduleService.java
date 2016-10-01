/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.service;

import java.util.List;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDao;
import sg.edu.nus.iss.phoenix.schedule.dao.ScheduleDaoImpl;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;

/**
 *
 * @author hippo
 */
public class ScheduleService {
    public ScheduleService(){
        super();
    }
    
    public JSONArray getProgramSlotOfCurrentWeek(){
        ScheduleDao scheduleDao=new ScheduleDaoImpl();
        List<ProgramSlotBean>resultSlotBeans=scheduleDao.getCurrentWeekProgramSlots();
        return JsonUtil.getJson4JavaList(resultSlotBeans);
    }
    
    public JSONArray getAllExistingYear(){
        ScheduleDao scheduleDao=new ScheduleDaoImpl();
        return JsonUtil.getJson4JavaList(scheduleDao.getAllExistingYear());
    }
    
    public JSONArray getProgramSlotByYearAndWeek(String yearString,String weekString){
        ScheduleDao scheduleDao=new ScheduleDaoImpl();
        int year=Integer.parseInt(yearString);
        int week=Integer.parseInt(weekString);
        List<ProgramSlotBean> resultList=scheduleDao.getProgramSlotByYearAndWeek(year, week);
        if(resultList==null||resultList.isEmpty()){
            return null;
        }
        return JsonUtil.getJson4JavaList(resultList);
    }
    
    public ProgramSlotBean getProgramSlotById(String id){
        ScheduleDao scheduleDao=new ScheduleDaoImpl();
        return scheduleDao.getProgramSlotById(id);
    }
    
    
    public String deleteProgramSlotById(String id){
        ScheduleDao scheduleDao=new ScheduleDaoImpl();
        return scheduleDao.deleteProgramSlotById(id);
    }
    
    public boolean insertProgramSlot(ProgramSlotBean programSlotBean,String assignedBy){
        ScheduleDao scheduleDao=new ScheduleDaoImpl();
        return scheduleDao.insertProgramSlot(programSlotBean, assignedBy);
    }
}
