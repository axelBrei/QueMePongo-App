package dds.frba.utn.quemepongo.View.Fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import dds.frba.utn.quemepongo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends DialogFragment {

    private Boolean shouldDismiss = false;
    private Boolean allowStateLoss = false;

    public ProgressFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        //check if we should dismiss the dialog after rotation
        if (shouldDismiss) {
            if (allowStateLoss)
                dismissAllowingStateLoss();
            else
                dismiss();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void dismiss() {
        if (getActivity() != null) { // it's "safer" to dismiss
            shouldDismiss = false;
            super.dismiss();
        } else {
            shouldDismiss = true;
            allowStateLoss = false;
        }
    }

    @Override
    public void dismissAllowingStateLoss() {
        if (getActivity() != null) { // it's "safer" to dismiss
            shouldDismiss = false;
            super.dismissAllowingStateLoss();
        } else
            allowStateLoss = shouldDismiss = true;
    }


    //keeping dialog after rotation
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }
}
