/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.phoenix.schedule.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *Time Control Util
 * @author CaiYicheng
 */

public class TimeUtil {

	public TimeUtil(){
	}
	
        /**
         * Get Current Year
         * @return int
         */
	public static int getCurrentYear(){
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
        /**
         * Get the year number by date
         * @param date
         * @return 
         */
        public static int getYear(Date date){
            SimpleDateFormat format = new SimpleDateFormat("yyyy");  
            return Integer.parseInt(format.format(date));
        }
        
        /**
         * Get the week of the year by date
         * @param date
         * @return 
         */
	  public static int getWeekOfYear(Date date) {
	        Calendar c = new GregorianCalendar();
	        c.setFirstDayOfWeek(Calendar.MONDAY);
	        c.setMinimalDaysInFirstWeek(7);
	        c.setTime(date);
	 
	        return c.get(Calendar.WEEK_OF_YEAR);
	    }
	 
	    /**
             * Get the maxium week of the year
             * @param year
             * @return 
             */
	    public static int getMaxWeekNumOfYear(int year) {
	        Calendar c = new GregorianCalendar();
	        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
	        return getWeekOfYear(c.getTime());
	    }
	 
	    /**
             * Get the first day of the week by year and week
             * @param year
             * @param week
             * @return 
             */
	    public static Date getFirstDayOfWeek(int year, int week) {
	        Calendar c = new GregorianCalendar();
	        c.set(Calendar.YEAR, year);
	        c.set(Calendar.MONTH, Calendar.JANUARY);
	        c.set(Calendar.DATE, 1);
	 
	        Calendar cal = (GregorianCalendar) c.clone();
	        cal.add(Calendar.DATE, week * 7);
	 
	        return getFirstDayOfWeek(cal.getTime());
	    }
	    
	    /**
             * Get the first day of the week by date
             * @param date
             * @return 
             */
	    public static Date getFirstDayOfWeek(Date date) {
	        Calendar c = new GregorianCalendar();
	        c.setFirstDayOfWeek(Calendar.MONDAY);
	        c.setTime(date);
	        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
	        return c.getTime();
	    }
	    
            /**
             * Parse the formatted string to date
             * @param dateString
             * @return 
             */
	    public static Date parseStringToDate(String dateString){
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	    	Date date = null;  
	    	try {  
	    	    date = format.parse(dateString); 
	    	    return date;
	    	} catch (ParseException e) {  
	    	    // TODO Auto-generated catch block  
	    	    e.printStackTrace();  
	    	    return null;
	    	}
	    }
	    
            /**
             * Parse the date to formated date string
             * @param date
             * @return 
             */
	    public static String parseDate2String(Date date){
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	    	
	    	return format.format(date);
	    }
            
            /**
             * Get the formated String of current date
             * @return 
             */
            public static String getCurrentDateString(){
                return parseDate2String(new Date());
            }
            
            /**
             * Add days to date
             * @param date
             * @param day
             * @return 
             */
            public static Date addDay(Date date,int day){
               Calendar Cal=java.util.Calendar.getInstance(); 
               Cal.setTime(date);
               Cal.add(Calendar.DATE, day);
               return Cal.getTime();
            }
            
            /**
             * Add hours to date
             * @param date
             * @param hour
             * @return 
             */
            public static Date addHour(Date date,int hour){
                Calendar Cal=java.util.Calendar.getInstance(); 
               Cal.setTime(date);
               Cal.add(Calendar.HOUR, hour);
               return Cal.getTime();
            }
            
            /**
             * Add Minutes to date
             * @param date
             * @param minute
             * @return 
             */
            public static Date addMinute(Date date, int minute){
                Calendar Cal=java.util.Calendar.getInstance(); 
               Cal.setTime(date);
               Cal.add(Calendar.MINUTE, minute);
               return Cal.getTime();
            }
	
        /**
         * Get the discrepants days between 2 dates
         * @param dateStart
         * @param dateEnd
         * @return 
         */    
        public static int getDiscrepantDays(Date dateStart, Date dateEnd) {  
            return (int) ((dateEnd.getTime() - dateStart.getTime()) / 1000 / 60 / 60 / 24);  
        }  
        
        /**
         * Change the date to another week
         * @param dateString
         * @param year
         * @param week
         * @return 
         */
        public static String changeDateWeek(String dateString,int year,int week){
            	Date theFirstDayOfWeekChangeTo=getFirstDayOfWeek(year, week);
            	Date date2Change=parseStringToDate(dateString);
            	//if(!theFirstDayOfWeekChangeTo.after(currentDate)){
            		
            	//}
            	//throw new IllegalArgumentException("Can not copy to previous schedule");
            	Date theFirstDayOfWeekChangeFrom=getFirstDayOfWeek(date2Change);
            	if(!theFirstDayOfWeekChangeTo.after(getFirstDayOfWeek(new Date()))){
            		throw new IllegalArgumentException("Can not copy to previous schedule");
            	}
            	int days=getDiscrepantDays(theFirstDayOfWeekChangeFrom, theFirstDayOfWeekChangeTo);
            	System.out.println(days);
            	Date newDate=addDay(date2Change,days);
            	return parseDate2String(newDate);
            	
         }
	
}
