/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.radioprogram.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import sg.edu.nus.iss.phoenix.radioprogram.dao.ProgramDAO;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

/**
 *
 * @author Guo Qi
 */
public class ReviewSelectProgramServiceTest {
    
    public ReviewSelectProgramServiceTest() {
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
     * Test of reviewSelectRadioProgram method, of class ReviewSelectProgramService.
     */
    @Test
    public void testReviewSelectRadioProgram() {
        try {
            System.out.println("reviewSelectRadioProgram");
            ArrayList<RadioProgram> rpsList = new ArrayList<>();
            ProgramDAO programDAO = mock(ProgramDAO.class);
            when(programDAO.loadAll()).thenReturn(rpsList);
            
            ReviewSelectProgramService instance = new ReviewSelectProgramService();
            List<RadioProgram> result = instance.reviewSelectRadioProgram();
            assertTrue(!result.isEmpty());

        } catch (SQLException ex) {
            Logger.getLogger(ReviewSelectProgramServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
