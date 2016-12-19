package com.jlabs.wm.spockdemo


import com.jlabs.wm.spockdemo.dto.User
import spock.lang.Specification


class DefaultMassMailSenderTest extends Specification {

    def userRepository = Mock(UserRepository)
    def emailSender = Mock(EmailSender)
    def underTest = new DefaultMassMailSender(userRepository: userRepository, emailSender: emailSender)

    def "should send emails to matching users and return a list of email addresses that the email has been sent to"() {
        given:
        def user1 = new User(id: 1L, userName: 'test user one', email: 'test@user.one')
        def user2 = new User(id: 2L, userName: 'IGNORED test user two', email: 'test@user.two')
        def user3 = new User(id: 3L, userName: 'THIRD test user', email: 'test@user.three')
        def user4 = new User(id: 4L, userName: 'testing user four', email: 'test@user.four')

        def subject = 'some test email subject'
        def content = 'some content of the email'

        when:
        def result = underTest.sendEmailsToUsers(subject, content, 'test')

        then: "get users from repository and send email to each matching user"
        1 * userRepository.findAll() >> [user1, user2, user3, user4]

        1 * emailSender.sendEmail(subject, content, 'test@user.one')
        1 * emailSender.sendEmail(subject, content, 'test@user.four')

        result == ['test@user.one', 'test@user.four']
    }

    def "should send emails to matching users and return a list of email addresses that the email has been sent to - test with data table"() {
        given:
        def user1 = new User(id: 1L, userName: 'test user one', email: 'test@user.one')
        def user2 = new User(id: 2L, userName: 'IGNORED test user two', email: 'test@user.two')
        def user3 = new User(id: 3L, userName: 'THIRD test user', email: 'test@user.three')
        def user4 = new User(id: 4L, userName: 'testing user four', email: 'test@user.four')

        def subject = 'some test email subject'
        def content = 'some content of the email'

        when:
        def result = underTest.sendEmailsToUsers(subject, content, userNameLike)

        then:
        1 * userRepository.findAll() >> [user1, user2, user3, user4]

        _ * emailSender.sendEmail(subject, content, _ as String)

        result == expectedEmailList

        where:
        userNameLike | expectedEmailList
        'test'       | ['test@user.one', 'test@user.four']
        'testing'    | ['test@user.four']
        'z'          | []
        'third'      | ['test@user.three']
    }
}
