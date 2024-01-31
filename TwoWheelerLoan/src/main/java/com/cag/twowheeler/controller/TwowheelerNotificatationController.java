package com.cag.twowheeler.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.cag.twowheeler.dto.MailNotificatationData;
import com.cag.twowheeler.entity.MainDealer;
import com.cag.twowheeler.entity.SubDealer;
import com.cag.twowheeler.repository.MainDealerRepository;
import com.cag.twowheeler.repository.SubDealerRepository;
import com.cag.twowheeler.responce.responce;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author Ankeet
 * Note:=Due to Internet Issue in Server We are Not able To Send Mail
 *
 */
@Component
@Slf4j
public class TwowheelerNotificatationController {

	@Autowired
	MainDealerRepository mainDealerRepository;

	@Autowired
	SubDealerRepository subDealerRepository;
	long checker = 0;
	long updaterchecker = 0;

	
	
	/**
	 * @API USE TO GET  EMAILS TO SEND NOTIFICATATION...!
	 * @API WILL RUN AUTOMATICLY EVERY DAY 10hr:37min:14sec AM 
	 */
//	@Scheduled(cron = "45 37 10 * * *")
	public ResponseEntity<responce> getNotificatation() {
		updaterchecker = checker;
		LocalDate date = LocalDate.now().minusMonths(1);
		List<MainDealer> mainDealerData = mainDealerRepository.getData(date + "");
		mainDealerData.stream().forEach(e -> {
			checker++;
			System.out.println(checker);
			MailNotificatationData dealerdto = MailNotificatationData.builder().DealaerName(e.getDealerName())
					.emailid(e.getMailID()).build();
			SendMail(dealerdto);
		});

		List<SubDealer> subDealerData = subDealerRepository.getData(date + "");
		subDealerData.stream().forEach(e -> {
			checker++;
			System.out.println(checker);

			MailNotificatationData dealerdto = MailNotificatationData.builder().DealaerName(e.getDealerName())
					.emailid(e.getMailID()).build();
			SendMail(dealerdto);
		});
//		System.out.println("============last" + checker);
//		System.out.println("============last" + updaterchecker);

		if (checker != updaterchecker) {
			log.info("mail send...!");
			return ResponseEntity.status(HttpStatus.OK)
					.body(responce.builder().message("MAIL SENDED").error("FALSE").build());
		} else {
			log.info("mail not send...!");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(responce.builder().message("MAIL NOT SENDED").error("TRUE").build());
		}
	}

	/**
	 * LOGIC FOR SEND EMAIL.....!
	 */
	private void SendMail(MailNotificatationData dealerdto) {

		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", 465);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("ankeetgalpalliwar@gmail.com", "-------NA------");//put your Temp Password
			}
		});
		session.setDebug(true);
		// compose the massage
		MimeMessage obj = new MimeMessage(session);
		try {
			obj.setFrom(new InternetAddress("ankeetgalpalliwar@gmail.com"));
			obj.addRecipient(Message.RecipientType.TO, new InternetAddress(dealerdto.getEmailid()));
			obj.setSubject("DEALER SERVICE NOTIFICATION");
//            obj.setText(url + "  \n" + "From Ankit....! " );
			MimeMultipart mimeMultipart = new MimeMultipart();
			MimeBodyPart textpart = new MimeBodyPart();
//			MimeBodyPart filepart = new MimeBodyPart();

			textpart.setText("DEAR, " + dealerdto.getDealaerName() + " YOUR DEALER SERVICE IS EXPIRE IN ONE MONTH");
//			try {
//				filepart.attachFile(new File("C:\\Users\\Ankeet\\Desktop\\jasper report\\address.txt"));
//			} catch (IOException e) {// "C:\Users\Ankeet\Desktop\jasper report\address.txt"
//				e.printStackTrace();
//			}

			mimeMultipart.addBodyPart(textpart);
//			mimeMultipart.addBodyPart(filepart);
			obj.setContent(mimeMultipart);
			// send massage to Transport class..
			Transport.send(obj);
			log.info("Mail send sucessfuly.....!");
		} catch (MessagingException e) {
			e.printStackTrace();
			log.error("Exceptation print.........!");
		}
	}

}
