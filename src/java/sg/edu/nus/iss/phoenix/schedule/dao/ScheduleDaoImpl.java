/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao;

import sg.edu.nus.iss.phoenix.core.dao.BaseDao;

/**
 *
 * @author EasonChua
 */
public class ScheduleDaoImpl extends BaseDao{
    private BaseDao annualScheDao=new BaseDao();
    private BaseDao weeklyScheDao=new BaseDao();
    private BaseDao programSlotDao=new BaseDao();
    
    public ScheduleDaoImpl(){
       initAnnualScheduleDao();
       initWeeklyScheduleDao();
       initProgramSlot();
    }
    
    private void initAnnualScheduleDao(){
       annualScheDao.setIdField("id");
       annualScheDao.setResultClass(null);
       annualScheDao.setTableName("");
       annualScheDao.nameMap("", "");
    }
    
    private void initWeeklyScheduleDao(){
        weeklyScheDao.setIdField("");
        weeklyScheDao.setResultClass(null);
        weeklyScheDao.setTableName("");
        weeklyScheDao.nameMap("","");
    }
    
    private void initProgramSlot(){
        
    }
    
    private void initProgramSlotDao(){
        
    }
    
}
