/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.entity;

import java.io.Serializable;

/**
 *
 * @author hippo
 */
public class AnnualScheduleBean implements  Serializable{
    private String id;
    private String year;
    private String assignedBy;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the assignedBy
     */
    public String getAssignedBy() {
        return assignedBy;
    }

    /**
     * @param assignedBy the assignedBy to set
     */
    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }
}
