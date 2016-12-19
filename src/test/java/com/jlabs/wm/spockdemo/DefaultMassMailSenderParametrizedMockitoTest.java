package com.jlabs.wm.spockdemo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import com.jlabs.wm.spockdemo.dto.User;
import static org.mockito.Mockito.*;

@RunWith(Parameterized.class)
public class DefaultMassMailSenderParametrizedMockitoTest {

	private UserRepository userRepository;
	private EmailSender emailSender;

	private DefaultMassMailSender underTest;

	private String userName;
	private List<String> expectedEmails;

	public DefaultMassMailSenderParametrizedMockitoTest(String userName, List<String> expectedEmails) {
		this.userName = userName;
		this.expectedEmails = expectedEmails;
	}

	@Before
	public void setUp() {
		userRepository = mock(UserRepository.class);
		emailSender = mock(EmailSender.class);

		underTest = new DefaultMassMailSender();
		underTest.setUserRepository(userRepository);
		underTest.setEmailSender(emailSender);
	}

	@Parameterized.Parameters
	public static List<Object[]> expectedUserEmails() {
		return Arrays.asList(new Object[][] {
				{"test", Arrays.asList("test@user.one", "test@user.four")},
				{"testing", Collections.singletonList("test@user.four")},
				{"z", Collections.emptyList()},
				{"third", Collections.singletonList("test@user.three")}
		});
	}

	/**
	 * should send emails to matching users and return a list of email addresses that the email has been sent to - parametrized test
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
		List<String> result = underTest.sendEmailsToUsers(subject, content, userName);

		//then:
		Assert.assertEquals(expectedEmails, result);

		verify(userRepository).findAll();
	}
}