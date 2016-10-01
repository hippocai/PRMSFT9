package sg.edu.nus.iss.phoenix.core.dao;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import sg.edu.nus.iss.phoenix.schedule.service.JsonUtil;

public class BaseDao {
	private final DBOperator operator=DBOperator.getOperator();
	private String tableName="";
	private String idField="id";
	private Map<String,String>bean2DBNameMap=new HashMap<String,String>();
	private Map<String,String>db2BeanNameMap=new HashMap<String,String>();
	private static final Logger log=Logger.getLogger(BaseDao.class);
	private void transMap2Bean(Map<String, String> map, Object obj) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
			
				if (map.containsKey(key)) {
					Object value = map.get(key);
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return;

	}
	
	private Map<String, String> transBean2Map(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (!key.equals("class") && !key.contains("callback")) {
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if(value==null){
						continue;
					}
					map.put(key, value.toString() + "");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	private Class resultClass=null;
	
	private String beanName2DBName(String beanFieldName){
		return bean2DBNameMap.get(beanFieldName);
	}
	
	private String dbName2BeanName(String dbColumnName){
		return db2BeanNameMap.get(dbColumnName);
	}
	
	private Map<String,String> transferBeanMap2DBMap(Map<String,String>beanMap){
		Map<String,String>dbMap=new HashMap<String,String>();
		if(beanMap==null){
			return dbMap;
		}
		for(String field:beanMap.keySet()){
			String column=this.beanName2DBName(field);
			if(column==null){
				log.warn("WARN!!!The field:"+field+" is not Existed Or Setted!");
				continue;
			}
			dbMap.put(column, beanMap.get(field));
		}
		return dbMap;
	}
	
	private Map<String,String>transferDBMap2BeanMap(Map<String,String>dbMap){
		Map<String,String>beanMap=new HashMap<String,String>();
		for(String column:dbMap.keySet()){
			String field=this.dbName2BeanName(column);
			if(field==null){
				log.warn("WARN!!!The column:"+column+" is not Existed Or Setted!");
				continue;
			}
			beanMap.put(field, dbMap.get(column));
		}
		return beanMap;
	}
	public void setTableName(String _tableName){
		this.tableName="`"+_tableName+"`";
	}
	
	public void nameMap(String BeanFieldName,String DBColumnName){
		bean2DBNameMap.put(BeanFieldName, DBColumnName);
		db2BeanNameMap.put(DBColumnName, BeanFieldName);
	}
	
	public void setIdField(String idField){
		this.idField=idField;
	}
	
	public void setResultClass(Class cls){
		this.resultClass=cls;
	}
	
	public BaseDao(){
		
	}
	
	public boolean insert(Object bean){
		Map<String,String>beanMap=this.transBean2Map(bean);
		Map<String,String>map2Insert=this.transferBeanMap2DBMap(beanMap);
		map2Insert.remove(this.idField);
		try {
			operator.Insert(this.tableName, map2Insert);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error Occured While Inserting",e);
		} 
		return false;
	}
	

	public boolean delete(Map<String,String>query){
		QueryMap queryMap=new QueryMap();
		queryMap.addAllFromMap(this.transferBeanMap2DBMap(query));
		try {
			operator.Delete(this.tableName, queryMap);
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error Occured While Deleteting",e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error Occured While Deleteting",e);
		}
		return false;
	}
	
	public boolean update(Map<String,String>newValue,Map<String,String>query){
		QueryMap queryMap=new QueryMap();
		queryMap.addAllFromMap(this.transferBeanMap2DBMap(query));
		Map<String,String>map2Update=this.transferBeanMap2DBMap(newValue);
		try {
			operator.Update(this.tableName, map2Update, queryMap);
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error Occured While Updating",e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error Occured While Updating",e);
		}
		
		return false;
	}
	@SuppressWarnings("unchecked")
	private <T>T transferObj2Bean(Object obj) throws IllegalAccessException{
		Object testObj=(T)new Object();
		if(testObj.getClass().getName().equals(obj.getClass().getName())){
			throw new IllegalAccessException("Type Not Same:1."+testObj.getClass().getName()+"  2."+obj.getClass().getName());
		}
		return (T)obj;
	}
	public <T>List<T>select(Map<String,String>query){
		QueryMap queryMap=new QueryMap();
		queryMap.addAllFromMap(this.transferBeanMap2DBMap(query));
		List<T> resultBeanList=new ArrayList<T>();
		try {
			List<LinkedHashMap<String,String>>result;
			result=operator.Select(this.tableName, queryMap);
			for(LinkedHashMap<String,String>dbMap:result){
				Map<String,String>beanMap=this.transferDBMap2BeanMap(dbMap);
				Object resultObj=this.resultClass.newInstance();
				this.transMap2Bean(beanMap, resultObj);
				resultBeanList.add(this.transferObj2Bean(resultObj));
			}
                       // System.out.println("From BaseDao:"+JsonUtil.getJson4JavaList(result).toString());
                       // System.out.println("From BaseDao:"+JsonUtil.getJson4JavaList(resultBeanList).toString());
			return resultBeanList;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error Occured While Querying",e);
		} 
		return resultBeanList;
	}
	
	public DBOperator getOperator(){
		return this.operator;
	}
	
	
}
