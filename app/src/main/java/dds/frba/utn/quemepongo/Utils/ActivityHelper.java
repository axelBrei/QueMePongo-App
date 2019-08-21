package dds.frba.utn.quemepongo.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import androidx.annotation.Nullable;

import dds.frba.utn.quemepongo.QueMePongo;
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

}
