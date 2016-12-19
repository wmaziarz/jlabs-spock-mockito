package com.jlabs.wm.spockdemo;


import java.util.List;
import java.util.stream.Collectors;
import com.jlabs.wm.spockdemo.dto.User;

public class DefaultMassMailSender implements MassMailSender {

	private UserRepository userRepository;
	private EmailSender emailSender;

	@Override
	public List<String> sendEmailsToUsers(String subject, String content, String userNameLike) {
		return userRepository.findAll().stream()
				.filter(u -> isUserMatching(u, userNameLike))
				.map(User::getEmail)
				.map(emailAddr -> sendEmail(subject, content, emailAddr))
				.collect(Collectors.toList());
	}

	private boolean isUserMatching(User user, String userNameLike) {
		return user.getUserName().toLowerCase().startsWith(userNameLike.toLowerCase());
	}

	private String sendEmail(String subject, String content, String emailAddr) {
		emailSender.sendEmail(subject, content, emailAddr);
		return emailAddr;
	}


	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setEmailSender(EmailSender emailSender) {
		this.emailSender = emailSender;
	}


}
