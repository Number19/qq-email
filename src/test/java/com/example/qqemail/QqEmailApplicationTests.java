package com.example.qqemail;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QqEmailApplicationTests {

	/**
	 * 注:由于Spring Boot的starter模块提供了自动化配置，
	 * 所以在引入了spring-boot-starter-mail依赖之后，
	 * 会根据配置文件中的内容去创建JavaMailSender实例，
	 * 因此我们可以直接在需要使用的地方直接@Autowired来引入邮件发送对象。
	 */
	@Autowired
	private JavaMailSender mailSender;


	/**
	 * 单元测试来实现一封简单邮件的发送
	 * @throws Exception
	 */
	@Test
	public void sendSimpleMail() throws Exception {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("844261278@qq.com");
		message.setTo("844261278@qq.com");
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");
		mailSender.send(message);
	}

	/**
	 * 发送附件
	 * @throws Exception
	 */
	@Test
	public void sendAttachmentsMail() throws Exception {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("844261278@qq.com");
		helper.setTo("844261278@qq.com");
		helper.setSubject("主题：有附件");
		helper.setText("有附件的邮件");

		FileSystemResource file = new FileSystemResource(new File("D:\\IDEA_WORKPLACE\\PersonalKnowledgePractice\\qq-email\\src\\main\\resources\\static\\weixin.jpg"));
		helper.addAttachment("附件-1.jpg", file);
		helper.addAttachment("附件-2.jpg", file);

		mailSender.send(mimeMessage);
	}

	/**
	 * 嵌入静态资源
	 * @throws Exception
	 */
	@Test
	public void sendInlineMail() throws Exception {

		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("844261278@qq.com");
		helper.setTo("844261278@qq.com");
		helper.setSubject("主题：嵌入静态资源");
		helper.setText("<html><body><img src=\"cid:weixin\" ></body></html>", true);

		FileSystemResource file = new FileSystemResource(new File("D:\\IDEA_WORKPLACE\\PersonalKnowledgePractice\\qq-email\\src\\main\\resources\\static\\weixin.jpg"));
		helper.addInline("weixin", file);

		mailSender.send(mimeMessage);
	}


}

