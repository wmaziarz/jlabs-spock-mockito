package com.jlabs.wm.spockdemo;

import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.jlabs.wm.spockdemo.dto.User;
import static org.mockito.Mockito.*;


public class DefaultMassMailSenderMockitoTest {

	private UserRepository userRepository;
	private EmailSender emailSender;

	private DefaultMassMailSender underTest;

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		emailSender = mock(EmailSender.class);

		underTest = new DefaultMassMailSender();
		underTest.setUserRepository(userRepository);
		underTest.setEmailSender(emailSender);
	}

	/**
	 * should send emails to matching users and return a list of email addresses that the email has been sent to
	 */
	@Test
	public void testSendingEmailsToMatchingUsers() {
		//given:
		User user1 = new User();
		user1.setId(1L);
		user1.setUserName("test user one");
		user1.setEmail("test@user.one");

		User user2 = new User();
		user2.setId(2L);
		user2.setUserName("IGNORED test user two");
		user2.setEmail("test@user.two");

		User user3 = new User();
		user3.setId(3L);
		user3.setUserName("THIRD test user");
		user3.setEmail("test@user.three");

		User user4 = new User();
		user4.setId(4L);
		user4.setUserName("testing user four");
		user4.setEmail("test@user.four");

		String subject = "some test email subject";
		String content = "some content of the email";

		when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3, user4));

		//when:
		List<String> result = underTest.sendEmailsToUsers(subject, content, "test");

		//then:
		Assert.assertEquals(Arrays.asList("test@user.one", "test@user.four"), result);

		verify(userRepository).findAll();
	}

}