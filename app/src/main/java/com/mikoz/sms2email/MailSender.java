package com.mikoz.sms2email;

import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
  private static final String TAG = "MailSender";

  public void send(Context context, String subject, String content) {
    final SharedPreferences pref = context.getSharedPreferences("", Context.MODE_PRIVATE);
    final Properties prop = new Properties();
    prop.put("mail.smtp.host", pref.getString("smtp.host", "smtp.gmail.com"));
    prop.put("mail.smtp.port", pref.getInt("smtp.port", 587));
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true");

    try {
      Session session =
          Session.getInstance(
              prop,
              new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(
                      pref.getString("smtp.user", ""), pref.getString("smtp.password", ""));
                }
              });
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(pref.getString("from", "")));
      message.setRecipients(
          Message.RecipientType.TO, InternetAddress.parse(pref.getString("to", "")));
      message.setSubject(subject);
      message.setText(content);
      Transport.send(message);
    } catch (Exception e) {
      // Log the full stack trace
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
      String stackTrace = sw.toString();
      Log.e(TAG, "Failed to send email", e);

      NotificationHelper.createNotificationChannel(context);

      NotificationManager notificationManager =
          (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

      NotificationCompat.Builder builder =
          new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
              .setContentTitle("Failed to send email")
              .setContentText(e.getMessage())
              .setStyle(
                  new NotificationCompat.BigTextStyle()
                      .bigText(e.getMessage() + "\n\n" + stackTrace))
              .setSmallIcon(android.R.drawable.ic_dialog_email)
              .setPriority(NotificationCompat.PRIORITY_DEFAULT);

      notificationManager.notify(1, builder.build());
    }
  }
}
