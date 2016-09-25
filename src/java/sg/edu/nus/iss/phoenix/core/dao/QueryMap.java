package sg.edu.nus.iss.phoenix.core.dao;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * QueryMap
 * indicate the query conditions while querying the database\
 * Author:CaiYicheng
 * SE24 FT09
 * */
public class QueryMap {

	List<QueryElement> queryList;

	
	public QueryMap() {
		super();
		queryList=new LinkedList<QueryElement>();
	}
	
	public QueryMap(Map<String,String>map) {
		super();
		queryList=new LinkedList<QueryElement>();
		this.addAllFromMap(map);
	}

	//get the value by key
	public String getValue(String key){
		for(QueryElement qElement:queryList){
			if(qElement.getKey().equals(key)){
				return qElement.getValue();
			}
		}
		return null;
	}
	
	//check if the map contains the key
	public boolean containsKey(String key){
		String value=this.getValue(key);
		if(value!=null){
			return true;
		}else{
			return false;
		}
	}
	
	//remove the element by key(if existed)
	public void remove(String key){
		for(QueryElement qElement:queryList){
			if(qElement.getKey().equals(key)){
				queryList.remove(qElement);
			}
		}
	}
	
	
	//get QueryElement by key
	public QueryElement getQueryElement(String key){
		for(QueryElement qElement:queryList){
			if(qElement.getKey().equals(key)){
				return qElement;
			}
		}
		return null;
	}
	
	//put new key-value into the key map
	public void put(String key,String value){
		this.remove(key);
		QueryElement queryElement=new QueryElement(key,value);
		queryList.add(queryElement);
	}
	
	//put the key-value, logic conditions, and value relations into the querymap
	public void put(String key,String value,String logic,String relation){
		this.remove(key);
		QueryElement queryElement =new QueryElement(key,value,logic,relation);
		queryList.add(queryElement);
	}
	
	//get a cloned querylist
	public List<QueryElement> getClonedList(){
		List<QueryElement> newList=new ArrayList<QueryElement>();
		for(QueryElement qElement:queryList){
			try {
				newList.add(qElement.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newList;
	}
	
	//put all the elements into this querymap
	public void addAll(QueryMap queryMap){
		List<QueryElement> list2Add=queryMap.getList();
		for(QueryElement qElement:list2Add){
			this.put(qElement.getKey(), qElement.getValue(), qElement.getLogic(), qElement.getLogic());
		}
	}
	
	//get the list
	public List<QueryElement>getList(){
		return this.queryList;
	}
	
	//get the iterator of the list
	public Iterator<QueryElement>getIterator(){
		return queryList.iterator();
	}
	
	//put every key-value set from Map to the QueryMap
	public void addAllFromMap(Map<String,String>map){
		for(String key:map.keySet()){
			this.put(key, map.get(key));
		}
	}
	
	//check if the querymap is empty
	public boolean isEmpty(){
		return this.queryList.size()==0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((queryList == null) ? 0 : queryList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryMap other = (QueryMap) obj;
		if (queryList == null) {
			if (other.queryList != null)
				return false;
		} else if (!queryList.equals(other.queryList))
			return false;
		return true;
	}

	//to SQL Query conditions
	@Override
	public String toString() {
		String queryString="";
		for(QueryElement qElement:queryList){
			queryString+=qElement.toString();
		}
		if(queryString.startsWith("AND")){
			return queryString.substring(4, queryString.length());
		}
		
		if(queryString.startsWith("OR")){
			return queryString.substring(3, queryString.length());
		}
		
		return queryString;
	}
	
	
}
