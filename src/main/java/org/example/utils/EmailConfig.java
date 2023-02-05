package org.example.utils;

import java.util.Objects;

public interface EmailConfig {
	String SERVER = "smtp.gmail.com";
	String PORT = "587";
	String FROM = System.getProperty("EMAIL_FROM");
	String PASSWORD = System.getProperty("EMAIL_FROM_PASSWORD");
	String[] TO = System.getProperty("EMAIL_TO").split(";");
	String EMAIL_SUBJECT = Objects.equals(System.getProperty("EMAIL_SUBJECT", ""), "") ? "EMAIL_SUBJECT" : System.getProperty("EMAIL_SUBJECT");
}