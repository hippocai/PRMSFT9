/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.controller;

import at.nocturne.api.Action;
import at.nocturne.api.Perform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sg.edu.nus.iss.phoenix.authenticate.entity.User;
import sg.edu.nus.iss.phoenix.radioprogram.delegate.ReviewSelectProgramDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.schedule.dao.TimeUtil;
import sg.edu.nus.iss.phoenix.schedule.delegate.ScheduleDelegate;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;
import sg.edu.nus.iss.phoenix.schedule.service.JsonUtil;
import sg.edu.nus.iss.phoenix.user.delegate.ReviewSelectPresenterProducerDelegate;

/**
 *
 * @author hippo
 */
@Action("addEditSchedule")
public class AddEditScheduleCmd implements Perform{

    ReviewSelectPresenterProducerDelegate rsppd=new ReviewSelectPresenterProducerDelegate();
    ReviewSelectProgramDelegate rspd=new ReviewSelectProgramDelegate();
    ScheduleDelegate sd=new ScheduleDelegate();
    @Override
    public String perform(String string, HttpServletRequest hsr, HttpServletResponse hsr1) throws IOException, ServletException {
        String programSlotId=hsr.getParameter("psId");
        if(programSlotId==null||programSlotId.equals("")){
            hsr.setAttribute("insert", "true");
            hsr.setAttribute("originalData", "null");
        }else{
            hsr.setAttribute("insert", "false");
            ProgramSlotBean programSlotBean=sd.getProgramSlotById(programSlotId);
            hsr.setAttribute("originalData", JsonUtil.getJsonString4JavaPOJO(programSlotBean));
            //setOriginalProgramSlot(hsr,programSlotString);
        }
        List<User>presenterList=rsppd.getAllPresenter();
        List<User>producerList=rsppd.getAllProducer();
        List<RadioProgram>allPrograms=rspd.reviewSelectRadioProgram();
        List<String>allProgramsNameList=new ArrayList<>();
        for(RadioProgram radioProgram:allPrograms){
            allProgramsNameList.add(radioProgram.getName());
        }
        hsr.setAttribute("allPresenters", JsonUtil.getJson4JavaList(presenterList).toString());
        hsr.setAttribute("allProducers", JsonUtil.getJson4JavaList(producerList).toString());
        hsr.setAttribute("allPrograms", JsonUtil.getJson4JavaList(allProgramsNameList).toString());
        hsr.setAttribute("currentDate", TimeUtil.getCurrentDateString());
        return "/pages/setupsch.jsp";
    }
    
}
