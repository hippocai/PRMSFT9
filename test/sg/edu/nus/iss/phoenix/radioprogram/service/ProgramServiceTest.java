/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.radioprogram.service;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 *
 * @author Guo Qi
 */
public class ProgramServiceTest {
    
    public ProgramServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of searchPrograms method, of class ProgramService.
     */
    @Test
    public void testSearchPrograms() {
        try {
            System.out.println("searchPrograms");
            ArrayList<RadioProgram> rp = new ArrayList<RadioProgram>();
            RadioProgram rpso = new RadioProgram();
            rpso.setName("noose");
            rpso.setDescription("black comedy show");
            rpso.setTypicalDuration(Time.valueOf("00:30:00"));
            ProgramService instance = new ProgramService();
            ProgramDAO programDao = mock(ProgramDAO.class);
            when(programDao.searchMatching(rpso)).thenReturn(rp);
            String programName = instance.searchPrograms(rpso).get(0).getName();
            assertEquals(programName, "noose");
          
        } catch (SQLException ex) {
            Logger.getLogger(ProgramServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of findRPByCriteria method, of class ProgramService.
     */
    @Test
    public void testFindRPByCriteria() {
        try {
            System.out.println("findRPByCriteria");
            ArrayList<RadioProgram> rpsList = new ArrayList<RadioProgram>();
            RadioProgram rp = new RadioProgram();
            rp.setName("noose");
            rp.setDescription("black comedy show");
            rp.setTypicalDuration(Time.valueOf("00:30:00"));
            ProgramService instance = new ProgramService();
            ProgramDAO programDao = mock(ProgramDAO.class);
            when(programDao.searchMatching(rp)).thenReturn(rpsList);
            String programName = instance.findRPByCriteria(rp).get(0).getName();
            assertEquals(programName, "noose");
        } catch (SQLException ex) {
            Logger.getLogger(ProgramServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of findRP method, of class ProgramService.
     */
    @Test
    public void testFindRP() {
        try {
            System.out.println("findRP");
            ArrayList<RadioProgram> rpsList = new ArrayList<RadioProgram>();
            RadioProgram rp = new RadioProgram();
            rp.setName("noose");
            rp.setDescription("black comedy show");
            rp.setTypicalDuration(Time.valueOf("00:30:00"));
            String rpName = "noose";
            String programDesc = "black comedy show";
            ProgramDAO programDao = mock(ProgramDAO.class);
            when(programDao.searchMatching(rp)).thenReturn(rpsList);
            ProgramService instance = new ProgramService();
            String instanceDesc = instance.findRP(rpName).getDescription();
            assertEquals(programDesc, instanceDesc);
            
        } catch (SQLException ex) {
            Logger.getLogger(ProgramServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of findAllRP method, of class ProgramService.
     */
    @Test
    public void testFindAllRP() {
        try {
            System.out.println("findAllRP");
            ArrayList<RadioProgram> rpsList = new ArrayList<RadioProgram>();
            ProgramDAO programDao = mock(ProgramDAO.class);
            when(programDao.loadAll()).thenReturn(rpsList);
            ProgramService instance = new ProgramService();
            ArrayList<RadioProgram> result = instance.findAllRP();
            assertTrue(!result.isEmpty());
        } catch (SQLException ex) {
            Logger.getLogger(ProgramServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of processCreate method, of class ProgramService.
     */
    @Test
    public void testProcessCreateAndModify() {
        System.out.println("processCreate");
        RadioProgram rp = new RadioProgram();
        RadioProgram rpUpdate = new RadioProgram();
        rpUpdate.setAll("sports", "NBA", Time.valueOf("00:30:00"));
        rp.setName("sports");
        rp.setDescription("sports news");
        rp.setTypicalDuration(Time.valueOf("00:30:00"));
        ProgramService instance = new ProgramService();
        instance.processCreate(rp);
        String programName = instance.findRPByCriteria(rp).get(0).getName();
        assertEquals(programName, "sports");
    }


    /**
     * Test of processDelete method, of class ProgramService.
     */
    @Test
    public void testProcessDelete() {
        System.out.println("processDelete");
        String name = "sports";
        ProgramService instance = new ProgramService();
        instance.processDelete(name);
        assertTrue(instance.findAllRP().size() == 8);

    }
    
}
