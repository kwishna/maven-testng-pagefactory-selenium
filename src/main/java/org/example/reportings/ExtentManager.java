package org.example.reportings;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {

	private ExtentManager() {
	}

	private static final ThreadLocal<ExtentTest> EXTENT_TEST = ThreadLocal.withInitial(() -> null);

	public static ExtentTest getExtentTest() {
		return EXTENT_TEST.get();
	}

	static void setExtentTest(ExtentTest test) {
		EXTENT_TEST.set(test);
	}

	static void unload() {
		EXTENT_TEST.remove();
	}

}
