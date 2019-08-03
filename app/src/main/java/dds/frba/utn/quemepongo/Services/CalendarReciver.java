package dds.frba.utn.quemepongo.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.CalendarContract;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.widget.Toast;

import java.io.Console;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

import dds.frba.utn.quemepongo.Controllers.EventosController;
import dds.frba.utn.quemepongo.Model.Evento;
import dds.frba.utn.quemepongo.Model.Ubicacion;

public class CalendarReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.getAction().equalsIgnoreCase(CalendarContract.ACTION_EVENT_REMINDER)){
            Uri uri = intent.getData();
            String alertTime = uri.getLastPathSegment();
            String selection = CalendarContract.CalendarAlerts.ALARM_TIME + "=?";
            Cursor cursor =
                    context.getContentResolver()
                            .query(
                                    CalendarContract.CalendarAlerts.CONTENT_URI_BY_INSTANCE,
                                    new String[]{CalendarContract.CalendarAlerts.EVENT_ID},
                                    selection,
                                    new String[]{alertTime},
                                    null);
            int eventId = 0;
            if( cursor.moveToNext() ) eventId = cursor.getInt(0);
            System.out.println("Event id = " + eventId);
            try {
                getEventDetails(context, eventId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendEventToBack(Context context, Evento evento){
        EventosController eventosController = new EventosController(context);
//        eventosController.sendEvento(evento );
    }

    private void getEventDetails(Context context,Integer eventID) throws IOException {
        if (eventID != 0) {
                Uri event = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventID);
                Cursor cursor;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    cursor = context.getContentResolver().query(event, null, null, null);
                    if (cursor.moveToFirst()) {
                        Evento evento = new Evento();
                        do {
                            if ((cursor.getInt(cursor.getColumnIndex(CalendarContract.Events.DELETED)) == 1)) {
                                //The added event was deleted and is not visible in calendar but exists in calendar DB
                                break;
                            }
                            if(!cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE)).isEmpty()){
                                evento.setNombre(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE)));
                            }
                            if(!cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART)).isEmpty()){
                                Date date = Date.from(Instant.ofEpochMilli(Long.parseLong(cursor.getString(cursor.getColumnIndex(CalendarContract.Events.DTSTART)))));
                                evento.setFecha(date);
                            }
                            if(!cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION)).isEmpty()){
                                String ubicacion = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.EVENT_LOCATION));
                                evento.setUbicacion(context, ubicacion);
                            }
                        } while (cursor.moveToNext());

                        sendEventToBack(context, evento);
                    }
                }
        }
    }
}
