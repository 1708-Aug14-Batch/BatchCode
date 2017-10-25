package java4s;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public class GMailServer extends Authenticator {

	private String mailhost = "smtp.gmail.com";
	private String user;
	private String password;
	private Session session;
	
	public GMailServer(String user, String password) {
		this.user = user;
		this.password = password;
		
		Properties prop = new Properties();
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.host", mailhost);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.debug", "true");
		prop.put("mail.smtp.socketFactory.fallback", "false");
		prop.put("mail.smtp.quitwait", "false");
		
		session = Session.getDefaultInstance(prop,this);
	}
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(user,password);
	}
	
	public void sendMail (String subject, String body, String sender, String recipients) throws Exception {
		MimeMessage message = new MimeMessage(session);
		
		DataHandler handler = new DataHandler(new ByteArrayDataSource(body.getBytes(), "text/plain"));
		message.setSender(new InternetAddress(sender));
		message.setSubject(subject);
		message.setDataHandler(handler);
		if(recipients.indexOf(",") > 0) {
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipients));
		}
		else
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipients));
		Transport.send(message);
		
	}
	public class ByteArrayDataSource implements DataSource{

		private byte[] data;
		private String type;
		
		public ByteArrayDataSource(byte[] data, String type) {
			super();
			this.data = data;
			this.type = type;
		}

		
		public ByteArrayDataSource(byte[] data) {
			super();
			this.data = data;
		}
		
		public void setType(String type) {
			this.type = type;
		}
			
		
		public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(data);
		}

		public String getName() {
			 return "ByteArrayDataSource";
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("Not Supported");
		}
		
	}
	
}
