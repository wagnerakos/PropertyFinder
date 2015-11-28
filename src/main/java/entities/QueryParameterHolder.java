package entities;

import lombok.Getter;
import lombok.Setter;

public class QueryParameterHolder {

	@Getter
	@Setter
	private String value;
	
	@Getter
	@Setter
	private String operator;
	
	@Getter
	@Setter
	private String fieldName;
	
	@Getter
	@Setter
	private String label;
	
	@Getter
	@Setter
	private String parameterName;
	
	@Getter
	@Setter
	private Class<? extends Object> valueType;
	
	public QueryParameterHolder(String fieldName, String label, String operator, String value, Class<? extends Object> valueType, String paramName) {
		this.label = label;
		this.fieldName = fieldName;
		this.operator = operator;
		this.value = value;
		this.valueType = valueType;
		this.parameterName = paramName;
	}
	
	public QueryParameterHolder(String fieldName, String label, String operator, String value) {
		this(fieldName, label, operator, value, String.class, fieldName);
	}
}
