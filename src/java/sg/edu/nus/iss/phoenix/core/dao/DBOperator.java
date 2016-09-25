package sg.edu.nus.iss.phoenix.core.dao;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/*
 * DBOperator
 * Basic functionality related to database
 * Author:CaiYicheng
 * SE24 FT09
 * */
public class DBOperator { 
	//OrderByAsc
	public static final int ORDER_ASC=0x01;
	//OrderByDesc
	public static final int ORDER_DESC=0x02;
	//Not Order
	private static final int ORDER_NONE=0;
	
	private static final Logger log=Logger.getLogger(DBOperator.class);
	
	private static DBOperator dbOperator=null;
	//Create DBConnector
	private static Connection getConnector() throws ClassNotFoundException, SQLException {
	    String driver = DBCommon.DB_DRIVER;
	    String url = DBCommon.DB_URL;
	    String username = DBCommon.DB_USER;
	    String password = DBCommon.DB_PASSWORD;
	    Connection conn = null;
	    Class.forName(driver); 
	    conn = (Connection) DriverManager.getConnection(url, username, password);
	    return conn;
	}
	public static DBOperator getOperator(){
		if(dbOperator==null){
			dbOperator=new DBOperator();
		}
		return dbOperator;
	}
	//Replace the symbol that may cause sql injection
	private static String TransactSQLInjection(String str){
	   return str.replaceAll(".*([';]+|(--)+).*"," ");
	}
	
	private DBOperator(){
		
	}
	//check if table existed
	private boolean isTableExisted(String tableName,Connection connector) throws SQLException{
		if(tableName==null||("").equals(tableName)){
			log.error("ERROR!!!!The TableName Is Null");
			return false;
		}
		DatabaseMetaData meta;
		meta = connector.getMetaData();
		ResultSet rsTables = meta.getTables(null , null, tableName, null);
		if(rsTables.next()){
			  return true;
		}else{
			  return false;
		}
		
	
	}
	//generate the statement like "(?,?,?)"
	private String generateQMark(Map<String,String>values){
		String str="";
		for(int i=0;i<values.size();++i){
			str+="?,";
		}
		return str.substring(0, str.length()-1);
	}
	
	//generate the Table field string
	private String generateTableField(Map<String,String>values){
		String str="";
		for(String key:values.keySet()){
			str+=key+",";
		}
		return str.substring(0, str.length()-1);
	}
	
	//generate the 'key=value' string
	private String generateStringValueSetByMap(Map<String,String>map,String separater,String connector){
		String str="";
		for(String key:map.keySet()){
			if(!str.equals("")){
				str+=separater+" ";
			}
			String value=map.get(key);
			//Replace the symbol that may cause sql injection
			value=TransactSQLInjection(value);
			str=str+key+" "+connector+" '"+value+"'";
		}
		
		return str;
	}
	
	//Insert Exec
	public synchronized int Insert(String table,Map<String,String>values) throws ClassNotFoundException, SQLException {
	    Connection conn = getConnector();
	    if(!isTableExisted(table, conn)){
	    	throw new IllegalArgumentException("the Table:"+table+" is not existed");
	    }
	    String sql="insert into "+table+" ("+generateTableField(values)+" ) values("+generateQMark(values)+")";
	    int affectedRows=0;
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        int valueNum=0;
	        for(String key:values.keySet()){
	        	++valueNum;
	        	pstmt.setString(valueNum, values.get(key));
	        }
	        log.info("Execute SQL Comment:"+sql+"\nValues:"+generateTableField(values));
	        affectedRows = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	    	log.error("Error occured while inserting", e);
	        e.printStackTrace();
	    }
	    log.info("Success,Affected "+affectedRows+" Rows");
	    return affectedRows;
	}
	
	//update Exec
	public synchronized int Update(String table, Map<String,String>updateValue,QueryMap queryMap) throws ClassNotFoundException, SQLException{
		Connection conn=getConnector();
	    if(!isTableExisted(table, conn)){
	    	throw new IllegalArgumentException("the Table:"+table+" is not existed");
	    }
		int affectedRows=0;
		if(updateValue==null||updateValue.isEmpty()){
			
		}
		String sql="update "+table+" set "+generateStringValueSetByMap(updateValue,",","=");
		if(queryMap!=null&&!queryMap.isEmpty()){
			sql+=" where "+queryMap.toString();
		}
		PreparedStatement pstmt;
		  try {
		        pstmt = (PreparedStatement) conn.prepareStatement(sql);
		        log.info("Execute SQL Comment:"+sql);
		        affectedRows = pstmt.executeUpdate();
		        pstmt.close();
		        conn.close();
		    } catch (SQLException e) {
		    	log.error("Error occured while updating", e);
		        e.printStackTrace();
		    }
		
		log.info("Success,Affected "+affectedRows+" Rows");
		return affectedRows;
	}
	
	//Delete Exec
	public synchronized int Delete(String table,QueryMap queryMap) throws ClassNotFoundException, SQLException{
		Connection conn=getConnector();
	    if(!isTableExisted(table, conn)){
	    	throw new IllegalArgumentException("the Table:"+table+" is not existed");
	    }
		int affectedRows=0;
		String sql="delete from "+table;
		if(queryMap!=null&&!queryMap.isEmpty()){
			sql+=" where "+queryMap.toString();
		}
		PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        log.info("Execute SQL Comment:"+sql);
	        affectedRows = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	    	log.error("Error occured while deleting", e);
	        e.printStackTrace();
	    }
	    log.info("Success,Affected "+affectedRows+" Rows");
		return affectedRows;
	}
	
	//Select Exec
	public synchronized List<LinkedHashMap<String,String>>SelectByOrder(String table,QueryMap queryMap,String sortBy,int sortType) throws ClassNotFoundException, SQLException{
		List<LinkedHashMap<String,String>> resultList=new ArrayList<LinkedHashMap<String,String>>();
		 Connection conn = getConnector();
		 if(!isTableExisted(table, conn)){
		    	throw new IllegalArgumentException("the Table:"+table+" is not existed");
		  }
		  String sql = "select * from "+table;
			if(queryMap!=null&&!queryMap.isEmpty()){
				sql+=" where "+queryMap.toString();
			}
		  if(sortType!=DBOperator.ORDER_NONE){
			  if(sortBy==null||sortBy.equals("")){
				  throw new IllegalArgumentException("The Sort Column is null");
			  }
			  sql+="ORDER BY "+sortBy+" ";
			  switch(sortType){
			  case ORDER_ASC:sql+="ASC";break;
			  case ORDER_DESC:sql+="DESC";break;
			  default:sql+="ASC";break;
			  }
		  }
		  PreparedStatement pstmt;
		  try {
				LinkedHashMap<String,String>result=new LinkedHashMap<String,String>();
		        pstmt = (PreparedStatement)conn.prepareStatement(sql);
		        log.info("Execute SQL Comment:"+sql);
		        ResultSet rs = pstmt.executeQuery();
		        ResultSetMetaData metaData=rs.getMetaData();
		        int col = metaData.getColumnCount();
		        while (rs.next()) {
		            for (int i = 1; i <= col; i++) {
		            	result.put(metaData.getColumnName(i), rs.getString(i));
		             }
		            resultList.add(result);
		        }
		    } catch (SQLException e) {
		    	log.error("Error occured while querying", e);
		        e.printStackTrace();
		    }
		return resultList;
	}
	
	public List<LinkedHashMap<String,String>>Select(String table,QueryMap queryMap) throws ClassNotFoundException, SQLException{
		return this.SelectByOrder(table, queryMap, null, DBOperator.ORDER_NONE);
	}
	
	
}
