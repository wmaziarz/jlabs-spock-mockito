package com.jlabs.wm.spockdemo;


public interface EmailSender {

	void sendEmail(String subject, String content, String emailAddr);
}
