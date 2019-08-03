package dds.frba.utn.quemepongo.Utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.HashSet;
import java.util.Set;

public class ActivityLifeCycleCallbackImpl implements  Application.ActivityLifecycleCallbacks {

    private Set<Activity> set = new HashSet<>();


    @Override
    public void onActivityStarted(Activity activity) {
        set.add(activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        set.remove(activity);

    }

    public Activity getCurrentActivity() {
        if (set.isEmpty()) {
            return null;
        }
        return set.iterator().next();
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}