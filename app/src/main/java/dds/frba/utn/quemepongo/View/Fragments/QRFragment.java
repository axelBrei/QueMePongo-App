package dds.frba.utn.quemepongo.View.Fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.QRCode;

import dds.frba.utn.quemepongo.Helpers.ErrorHelper;
import dds.frba.utn.quemepongo.R;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import me.ydcool.lib.qrmodule.encoding.QrGenerator;

@FieldDefaults(level =  AccessLevel.PRIVATE)
public class QRFragment extends Fragment {
    int idGuardarropa;

    public QRFragment() {
        // Required empty public constructor
    }

    public static QRFragment createInstance(int idGuardarropa) {
        QRFragment fragment = new QRFragment();
        fragment.idGuardarropa = idGuardarropa;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_qr, container, false);

        view.findViewById(R.id.QrFragmentBackButton).setOnClickListener( (v) -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        ImageView qrImage = view.findViewById(R.id.FragmentQRImage);
        String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        try{
            qrImage.setImageBitmap(
                    generateQRWithUserId(firebaseUid)
            );
        }catch (WriterException e){
            e.printStackTrace();
            ErrorHelper.showSimpleError(getActivity(), "Ha ocurrido un error al generar su QR");
        }

        return view;
    }

    public Bitmap generateQRWithUserId(String userId) throws WriterException{
        return new QrGenerator.Builder()
                .qrSize(500)
                .content(userId)
                .margin(2)
                .ecc(ErrorCorrectionLevel.H)
                .encode();
    }
}
