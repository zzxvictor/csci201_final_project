import java.io.*;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;

public class EmailReminder {
public static void sendReminder(int courseID, DBInterface db) {
		
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(courseID);
		String coursename = "";
		int currentLectureNumber = 0;
		int graceDayPolicy = 0;
		ResultSet RS = db.makeQuery("select * from Course where courseID=?", params);
		
		try {
			if (RS.next()) {
				
				try {
					coursename = RS.getString("courseName");
					currentLectureNumber = RS.getInt("currentLectureNumber");
					graceDayPolicy = RS.getInt("numGraceDays");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String courseName = coursename;
		
		ResultSet rs = db.makeQuery("select sum(prescence) as totalCount, studentID, email, name from "
										+ "(select * from Attendance inner join User on Attendance.studentID = User.userID) as dt "
										+ "where courseID=? group by studentID", params);
		
		try {
			
			while (rs.next()) {
			
				int totalPresenceCount = rs.getInt("totalCount");
				int studentID = rs.getInt("studentID");
				String studentName = rs.getString("name");
				String email = rs.getString("email");
				
				int graceDayLeft = Math.max (0, graceDayPolicy - (currentLectureNumber - totalPresenceCount));
				
				
				try {
					Properties props = new Properties();
					props.setProperty("mail.transport.protocol", "SMTP");
					props.setProperty("mail.smtp.host", "smtp.gmail.com");
					props.setProperty("mail.smtp.port", "587");
					props.setProperty("mail.smtp.auth", "true");
					props.setProperty("mail.smtp.starttls.enable", "true");
					
					
					Authenticator auth = new Authenticator() {
						
						public PasswordAuthentication getPasswordAuthentication() {
							
							return new PasswordAuthentication("vitkorzixuan@gmail.com", "mwzrahxallprqtel");
						} 
						
					};
					
					
					Session session = Session.getInstance(props, auth);
					session.setDebug(true);
					MimeMessage msg = new MimeMessage(session);
					
					msg.setFrom(new InternetAddress("viktorzixuan@gmail.com"));
					msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(email));
					System.out.println("Recipient: " + email);
					msg.setSubject("Attendance Reminder");
					
					String content = "";
					
					content += "Hi " + studentName + ",</br>";
					
					content += "<p> You are receiving this email because you are enrolled in " + courseName + ".</p>";
					content += "<p> Keep in mind that you only have <b>" + graceDayLeft + "</b> grace day left. Once you "
							+ "have used up the grace days, anymore unexcused absence will result in <b>grade deductions.</b> </p>";
					content += "<p> <b>FightOn!</b> </p>";
					content += "<p> " + courseName + " team </p>";
					
					
					msg.setContent(content, "text/html;charset=utf-8");
					
					Transport.send(msg);
					
					
					System.out.println("Email has been sent");
					}
					catch (Exception e) {
						
						e.printStackTrace();
					}
			
			}
			
		}
		catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

public static void sendReminder(String email, String studentName, String courseName) {
	
	try {
		Properties props = new Properties();
		props.setProperty("mail.transport.protocol", "SMTP");
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		
		
		Authenticator auth = new Authenticator() {
			
			public PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication("viktorzixuan@gmail.com", "mwzrahxallprqtel");
			} 
			
		};
		
		
		Session session = Session.getInstance(props, auth);
		session.setDebug(true);
		MimeMessage msg = new MimeMessage(session);
		
		msg.setFrom(new InternetAddress("viktorzixuan@gmail.com"));
		msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(email));
		
		msg.setSubject("Attendance Reminder");
		
		String content = "";
		
		content += "Hi " + studentName + ",</br>";
		
		content += "<p> You are receiving this email because you are enrolled in " + courseName + ".</p>";
		content += "<p> Keep in mind that you only have <b>one more</b> grace day left. Once you "
				+ "have used up the grace days, anymore unexcused absence will result in <b>grade deductions.</b> </p>";
		content += "<p> <b>FightOn!</b> </p>";
		content += "<p> " + courseName + " team </p>";
		
		
		msg.setContent(content, "text/html;charset=utf-8");
		
		Transport.send(msg);
		
		
		}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
	
	
}

}
