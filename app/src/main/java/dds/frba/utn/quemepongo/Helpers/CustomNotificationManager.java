package dds.frba.utn.quemepongo.Helpers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import dds.frba.utn.quemepongo.R;

public class CustomNotificationManager {
    private static final String CHANNEL_ID = "My channel";

    // pongo el constructor en privado para respetar el partron builder
    private CustomNotificationManager(){}

    public static class Builder {
        private NotificationCompat.Builder builder;
        private Context context;

        public NotificationCompat.Builder builder(Context context){
            builder = new NotificationCompat.Builder(context, CHANNEL_ID);
            builder.setChannelId(CHANNEL_ID)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            return builder;
        }
        public NotificationCompat.Builder setTitle(String title){
            return builder.setContentTitle(title);
        }

        public NotificationCompat.Builder setDescription(String description){
            return builder.setContentText(description);
        }

        public void show(){
            NotificationManagerCompat mCompatManager = NotificationManagerCompat.from(context);
            mCompatManager.notify(0,builder.build());
        }
    }

    public static void showNotification(Context context){
        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_full_open_on_phone)
                .setContentTitle("Notificacion!")
                .setContentText("Hola como estas?")
                .setChannelId(CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mCompatManager = NotificationManagerCompat.from(context);
        mCompatManager.notify(0,builder.build());
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.app_name);
            String description = "Canal para mandar eventos de nuevos atuendos";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
