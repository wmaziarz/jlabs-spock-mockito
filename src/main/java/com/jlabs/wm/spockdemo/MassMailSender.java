package com.jlabs.wm.spockdemo;


import java.util.List;

public interface MassMailSender {

	List<String> sendEmailsToUsers(String subject, String content, String userNameLike);
}
