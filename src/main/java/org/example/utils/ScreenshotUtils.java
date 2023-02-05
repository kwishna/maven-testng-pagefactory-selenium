package org.example.utils;

import org.example.browserManager.DriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;

public final class ScreenshotUtils {

	private ScreenshotUtils() {
	}

	public static String getBase64Image() {
		return ((TakesScreenshot) DriverManager.driver()).getScreenshotAs(OutputType.BASE64);
	}

	public static File getFileImage() {
		return ((TakesScreenshot) DriverManager.driver()).getScreenshotAs(OutputType.FILE);
	}

	public static byte[] getBytesImage() {
		return ((TakesScreenshot) DriverManager.driver()).getScreenshotAs(OutputType.BYTES);
	}

}
