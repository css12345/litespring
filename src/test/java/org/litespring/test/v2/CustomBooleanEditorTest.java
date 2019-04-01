package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomBooleanEditor;

public class CustomBooleanEditorTest {

	@Test
	public void testStringConvertToBoolean() {
		//true代表允许空值
		CustomBooleanEditor editor = new CustomBooleanEditor(true);
		editor.setAsText("true");
		Object value = editor.getValue();
		assertEquals(true, ((Boolean)value).booleanValue());
		
		editor.setAsText("false");
		value = editor.getValue();
		assertEquals(false, ((Boolean)value).booleanValue());
		
		
		editor.setAsText("on");
		value = editor.getValue();
		assertEquals(true, ((Boolean)value).booleanValue());
		
		editor.setAsText("off");
		value = editor.getValue();
		assertEquals(false, ((Boolean)value).booleanValue());
		
		
		editor.setAsText("yes");
		value = editor.getValue();
		assertEquals(true, ((Boolean)value).booleanValue());
		
		editor.setAsText("no");
		value = editor.getValue();
		assertEquals(false, ((Boolean)value).booleanValue());
		
		
		try {
			editor.setAsText("aabbcc");
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
}
