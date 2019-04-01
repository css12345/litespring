package org.litespring.test.v2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.litespring.beans.propertyeditors.CustomNumberEditor;

public class CustomNumberEditorTest {
	@Test
	public void testConvertString() {
		//第二个参数是看是否允许传入空值
		CustomNumberEditor editor = new CustomNumberEditor(Integer.class,true);
		editor.setAsText("3");
		Object value = editor.getValue();
		assertTrue(value instanceof Integer);
		assertEquals(3, ((Integer)value).intValue());
		
		editor.setAsText("");
		assertTrue(editor.getValue() == null);
		
		try {
			editor.setAsText("3.1");
		} catch (IllegalArgumentException e) {
			return;
		}
		fail();
	}
}
