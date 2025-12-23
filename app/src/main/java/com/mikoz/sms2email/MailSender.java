package com.mikoz.sms2email;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.core.app.NotificationCompat;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
  public void send(Context context, String subject, String content) {
    final SharedPreferences pref = context.getSharedPreferences("", Context.MODE_PRIVATE);
    final Properties prop = new Properties();
    prop.put("mail.smtp.host", pref.getString("smtp.host", "smtp.gmail.com"));
    prop.put("mail.smtp.port", pref.getInt("smtp.port", 587));
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true");
    Session session =
        Session.getInstance(
            prop,
            new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    pref.getString("smtp.user", ""), pref.getString("smtp.password", ""));
              }
            });

    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(pref.getString("from", "")));
      message.setRecipients(
          Message.RecipientType.TO, InternetAddress.parse(pref.getString("to", "")));
      message.setSubject(subject);
      message.setText(content);
      Transport.send(message);
    } catch (MessagingException e) {
      new NotificationCompat.Builder(context.getApplicationContext())
          .setContentTitle("Failed to send email")
          .setContentText(e.toString())
          .setPriority(NotificationCompat.PRIORITY_DEFAULT)
          .notify();
    }
  }
}
