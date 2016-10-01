/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import net.sf.json.JSONArray;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import sg.edu.nus.iss.phoenix.schedule.entity.ProgramSlotBean;
import sg.edu.nus.iss.phoenix.schedule.service.ScheduleService;

/**
 *
 * @author Guo Qi
 */
public class ScheduleServiceTest {
    
    ScheduleService scheduleService;
    List<ProgramSlotBean> programSlot;
    ProgramSlotBean slotBean;
    
    public ScheduleServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        scheduleService = new ScheduleService();
        programSlot = new JSONArray();
        slotBean = new ProgramSlotBean();
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void GetCurrentWeekProgramSlotTest(){
        try{
            programSlot = scheduleService.getProgramSlotOfCurrentWeek();
            Assert.assertTrue(programSlot.size() != 0);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void GetAllExistingYearTest(){
        try{
            Assert.assertTrue(scheduleService.getAllExistingYear().size() != 0);
            System.out.println("Existing years: " + scheduleService.getAllExistingYear().size());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void GetProgramSlotByYearAndWeekTest(){
        try{
            programSlot = scheduleService.getProgramSlotByYearAndWeek("2016", "39");
            Assert.assertTrue(programSlot.size() != 0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Test
    public void InsertDeleteProgramSlotTest(){
        try {
            slotBean.setId("123");
            slotBean.setWeeklyScheduleId("123");
            slotBean.setDateOfProgram("2016-10-01");
            slotBean.setDuration("2");
            slotBean.setPresenter("guoqi");
            slotBean.setProducer("qiguo");
            slotBean.setProgramName("news");
            slotBean.setStartTime("12");
            scheduleService.insertProgramSlot(slotBean, "pointyhead");
            Assert.assertTrue(scheduleService.getProgramSlotById("123").getPresenter().equals("guoqi"));
            System.out.println("Insert program slot: " + scheduleService.getProgramSlotById("123").getPresenter());
            scheduleService.deleteProgramSlotById("123");
            System.out.println("Program slot deleted");
        } catch (Exception e) {
        }
    }
    
    
    
}
