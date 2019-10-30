package dds.frba.utn.quemepongo.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import dds.frba.utn.quemepongo.QueMePongo;
import dds.frba.utn.quemepongo.R;
import dds.frba.utn.quemepongo.View.QueMePongoActivity;

public class ActivityHelper {

    public static void startActivityWithBacbButtonBlocked(Activity originActivity, Class destination, @Nullable Bundle originalBundle) {
        Intent intent = new Intent(originActivity, destination);
        Bundle bundle = originalBundle;
        if(bundle == null){
            bundle = new Bundle();
        }
        bundle.putBoolean(QueMePongoActivity.ENABLE_BACK_BUTTON, false);
        intent.putExtras(bundle);
        originActivity.startActivity(intent);
    }

    public static void startActivity(Activity origin, Class destination, Bundle bundle) {
        Intent intent = new Intent(origin,destination);
        intent.putExtras(bundle);
        origin.startActivity(intent);
    }

    public static void startActivity(Activity origin, Class destination) {
        Intent intent = new Intent(origin,destination);
        origin.startActivity(intent);
    }

    public static void startActivityForResult(Activity origin, Class destination, int requestCode) {
        origin.startActivityForResult(
                new Intent(origin,destination),
                requestCode
        );
    }

    public static void showFragment(AppCompatActivity origin, Fragment fragment, @Nullable Integer where) {
        FragmentTransaction transaction = origin.getSupportFragmentManager().beginTransaction();
        if(where != null){
            transaction.add(where, fragment, null);
        }else{
            transaction.add(android.R.id.content, fragment, null);
        }
        transaction.commit();
    }

    public static void showFragment(AppCompatActivity origin, Fragment fragment, @Nullable Integer where, String tag) {
        FragmentTransaction transaction = origin.getSupportFragmentManager().beginTransaction();
        if(where != null){
            transaction.add(where, fragment, tag);
        }else{
            transaction.add(android.R.id.content, fragment, tag);
        }
        transaction.commit();
    }

    public static void showFragmentWithSlideIn(AppCompatActivity origin, Fragment fragment, @Nullable Integer where, String tag) {
        FragmentTransaction transaction = origin.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.detail_event_enter, R.anim.detail_event_exit);
        if(where != null){
            transaction.add(where, fragment, tag);
        }else{
            transaction.add(android.R.id.content, fragment, tag);
        }
        transaction.commit();
    }

    public static void removeFragmentWithAnimation(AppCompatActivity activity, Fragment fragment){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.detail_event_enter, R.anim.detail_event_exit);
        transaction
                .remove(fragment)
                .commit();
    }

}
