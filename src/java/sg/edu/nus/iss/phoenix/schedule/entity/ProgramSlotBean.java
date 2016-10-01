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
public class ProgramSlotBean implements Serializable{
    private String id;
    private String weeklyScheduleId;
    private String presenter;
    private String producer;
    private String duration;
    private String dateOfProgram;
    private String startTime;
    private String programName;

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
     * @return the weeklyScheduleId
     */
    public String getWeeklyScheduleId() {
        return weeklyScheduleId;
    }

    /**
     * @param weeklyScheduleId the weeklyScheduleId to set
     */
    public void setWeeklyScheduleId(String weeklyScheduleId) {
        this.weeklyScheduleId = weeklyScheduleId;
    }

    /**
     * @return the presenter
     */
    public String getPresenter() {
        return presenter;
    }

    /**
     * @param presenter the presenter to set
     */
    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    /**
     * @return the producer
     */
    public String getProducer() {
        return producer;
    }

    /**
     * @param producer the producer to set
     */
    public void setProducer(String producer) {
        this.producer = producer;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the dateOfProgram
     */
    public String getDateOfProgram() {
        return dateOfProgram;
    }

    /**
     * @param dateOfProgram the dateOfProgram to set
     */
    public void setDateOfProgram(String dateOfProgram) {
        this.dateOfProgram = dateOfProgram;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the programName
     */
    public String getProgramName() {
        return programName;
    }

    /**
     * @param programName the programName to set
     */
    public void setProgramName(String programName) {
        this.programName = programName;
    }
    
    
}
