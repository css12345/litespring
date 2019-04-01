package org.litespring.beans;

public class PropertyValue {
	private final String name;
	private final Object value;
	private Object convertedValue;
	
	private boolean converted = false;

	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public Object getConvertedValue() {
		return convertedValue;
	}

	public void setConvertedValue(Object convertedValue) {
		this.converted = true;
		this.convertedValue = convertedValue;
	}

	public boolean isConverted() {
		return converted;
	}
	
}
