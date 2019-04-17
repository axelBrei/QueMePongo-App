package dds.frba.utn.quemepongo.Utils;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.dd.processbutton.ProcessButton;

import java.net.ContentHandlerFactory;
import java.util.Random;

public class ProgressGenerator {

    private Random random;
    private Integer progress;

    public ProgressGenerator() {
        random = new Random();

    }

    private int generateDelay() {
        return random.nextInt(1000);
    }

    public void start(final ProcessButton button,@NonNull final OnCompleteListenner mListener){
        final Handler mHandler = new Handler();
        progress = 0;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                progress += 10;
                button.setProgress(progress);
                if (progress < 100) {
                    mHandler.postDelayed(this, generateDelay());
                } else {
                    mListener.onComplete(null);
                }
            }
        });
    }

    public void start(final ProcessButton button, Runnable runnable, final OnCompleteListenner mlistener){
        final Handler mHandler = new Handler();
        progress = 0;
        Thread t = new Thread(){
            @Override
            public void run() {
                super.run();
                progress += 10;
                button.setProgress(progress);
                if (progress < 100) {
                    mHandler.postDelayed(this, generateDelay());
                }
            }
        };
        t.run();
        runnable.run();
        mlistener.onComplete(null);
        t.interrupt();
    }

    public void start(final ProcessButton button){
        final Handler mHandler = new Handler();
        progress = 0;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress += 5;
                button.setProgress(progress);
                mHandler.postDelayed(this, 500);
            }
        }, 0);
    }

    public void stop(final ProcessButton button){
        button.setProgress(100);
    }

    public void stopWithError(final ProcessButton button){
        button.setProgress(-1);
    }
}
