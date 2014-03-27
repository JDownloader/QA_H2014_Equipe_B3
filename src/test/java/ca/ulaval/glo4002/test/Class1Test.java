package ca.ulaval.glo4002.test;

import org.junit.runner.RunWith;
import org.junit.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class Class1Test {
	
	@Mock private Class2 class2;
	@InjectMocks private Class1 class1 = new Class1() ;
	
	@Test
	public void Test() {
		when(class2.getAttrib()).thenReturn(4);
		assertEquals(class1.getAttrib(), 4);
	}
}
