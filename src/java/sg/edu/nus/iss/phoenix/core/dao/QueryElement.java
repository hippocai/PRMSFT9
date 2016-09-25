package sg.edu.nus.iss.phoenix.core.dao;
public class QueryElement {
	public static final String AND="AND";
	public static final String OR="OR";
	public static final String EQUAL="=";
	public static final String LIKE="LIKE";
	
	private String Key="";
	private String Value="";
	private String logic=QueryElement.AND;
	private String relation=QueryElement.EQUAL;
	
	private static String TransactSQLInjection(String str){
		   return str.replaceAll(".*([';]+|(--)+).*"," ");
	}
	
	@Override
	public QueryElement clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new QueryElement(Key,Value,logic,relation);
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	
	public void set(String key,String value){
		this.setKey(key);
		this.setValue(value);
	}

	
	public void set(String key,String value,String _logic,String _relation){
		this.set(key, value);
		this.setLogic(_logic);
		this.setRelation(_relation);
	}
	
	
	public QueryElement(String key, String value, String logic, String relation) {
		super();
		Key = key;
		Value = value;
		this.logic = logic;
		this.relation = relation;
	}
	
	public QueryElement(String key, String value) {
		super();
		Key = key;
		Value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Key == null) ? 0 : Key.hashCode());
		result = prime * result + ((Value == null) ? 0 : Value.hashCode());
		result = prime * result + ((logic == null) ? 0 : logic.hashCode());
		result = prime * result + ((relation == null) ? 0 : relation.hashCode());
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
		QueryElement other = (QueryElement) obj;
		if (Key == null) {
			if (other.Key != null)
				return false;
		} else if (!Key.equals(other.Key))
			return false;
		if (Value == null) {
			if (other.Value != null)
				return false;
		} else if (!Value.equals(other.Value))
			return false;
		if (logic == null) {
			if (other.logic != null)
				return false;
		} else if (!logic.equals(other.logic))
			return false;
		if (relation == null) {
			if (other.relation != null)
				return false;
		} else if (!relation.equals(other.relation))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return this.logic+" "+this.Key+this.relation+"'"+TransactSQLInjection(this.Value)+"'"+" ";
	}
	
	
}
