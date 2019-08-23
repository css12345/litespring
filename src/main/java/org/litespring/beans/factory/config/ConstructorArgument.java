package org.litespring.beans.factory.config;

import java.util.ArrayList;
import java.util.List;

public class ConstructorArgument {

	private List<ValueHolder> argumentValues = new ArrayList<>();

	public static class ValueHolder {

		private String type;

		private String name;

		private Object value;
		
		private boolean isResolved = false;
		
		private Object resolvedValue;
		
		public ValueHolder(Object value) {
			this.value = value;
		}

		public Object getValue() {
			return value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setValue(Object value) {
			this.value = value;
		}
		
		public boolean isResolved() {
			return isResolved;
		}

		public Object getResolvedValue() {
			return resolvedValue;
		}

		public void setResolvedValue(Object resolvedValue) {
			this.isResolved = true;
			this.resolvedValue = resolvedValue;
		}

	}

	public List<ValueHolder> getArgumentValues() {
		return argumentValues;
	}

	public void addArgumentValue(ValueHolder valueHolder) {
		argumentValues.add(valueHolder);
	}

	public int getArgumentCount() {	
		return argumentValues.size();
	}

	public ValueHolder getArgumentValue(int paramIndex) {
		if (0 <= paramIndex && paramIndex < argumentValues.size())
			return argumentValues.get(paramIndex);
		return null;
	}
}
