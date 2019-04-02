package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.TypeMismatchException;

public class TypeConverterTest {
	@Test
	public void testConvertStringToInt() {
		TypeConverter converter = new SimpleTypeConverter();
		Integer i = converter.convertIfNecessary("3",Integer.class);
		assertEquals(3, i.intValue());
		
		try {
			converter.convertIfNecessary("3.1",Integer.class);
		} catch (TypeMismatchException e) {
			return;
		}
		fail();
	}
	
	@Test
	public void testConvertStringToBoolean() {
		TypeConverter converter = new SimpleTypeConverter();
		Boolean b = converter.convertIfNecessary("true",Boolean.class);
		assertEquals(true, b.booleanValue());
		
		try {
			converter.convertIfNecessary("aabbcc",Boolean.class);
		} catch (TypeMismatchException e) {
			return;
		}
		fail();
	}
}