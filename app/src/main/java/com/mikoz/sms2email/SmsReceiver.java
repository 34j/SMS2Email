package com.mikoz.sms2email;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import java.util.Objects;

public class SmsReceiver extends BroadcastReceiver {
  protected MailSender mailSender = new MailSender();

  @Override
  public void onReceive(Context context, Intent intent) {
    if (Objects.equals(intent.getAction(), Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
      SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
      String sender = messages[0].getOriginatingAddress();
      StringBuilder bodyText = new StringBuilder();
      for (SmsMessage message : messages) {
        bodyText.append(message.getMessageBody());
      }
      String message = bodyText.toString();
      Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

      NotificationHelper.createNotificationChannel(context);

      NotificationManager notificationManager =
          (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

      NotificationCompat.Builder builder =
          new NotificationCompat.Builder(context, NotificationHelper.CHANNEL_ID)
              .setContentTitle(sender)
              .setContentText(message)
              .setSmallIcon(android.R.drawable.ic_dialog_email);

      notificationManager.notify(2, builder.build());

      mailSender.send(context, sender, message);
    }
  }
}
