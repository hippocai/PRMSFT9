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
public class WeeklyScheduleBean implements Serializable{
    private String id;
    private String annualId;
    private String startDate;
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
     * @return the annualId
     */
    public String getAnnualId() {
        return annualId;
    }

    /**
     * @param annualId the annualId to set
     */
    public void setAnnualId(String annualId) {
        this.annualId = annualId;
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
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
