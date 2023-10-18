package com.miller.service.framework.depend.method;

import com.miller.service.framework.BasicTestCase;
import com.miller.service.framework.depend.DependsOnMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DependsOnMethodAnnotationTests extends BasicTestCase {
	@Test
	void independentTest() {
		assertEquals(0, 0);
	}

	@Test
	void gamma() {
		assertEquals(1, 1);
	}

	@Test
	@DependsOnMethod("gamma")
	void delta() {
		assertEquals(2, 2);
	}

	@Test
	@DependsOnMethod("delta")
	void beta() {
		assertEquals(3, 3);
	}

	@Test
	@DependsOnMethod("beta")
	void alpha() {
		assertEquals(4, 4);
	}
}
