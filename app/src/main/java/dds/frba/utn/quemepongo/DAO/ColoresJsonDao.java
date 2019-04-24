package dds.frba.utn.quemepongo.DAO;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.List;

public class ColoresJsonDao {

    public String getColorsBuffer(Context context) throws IOException {
        // METODO Q DEVUELVE EL JSON COMO UN STRING
        InputStream stream = context.getAssets().open("colors.json");

        int size = stream.available();
        byte[] buffer = new byte[size];
        stream.read(buffer);

        return new String(buffer);

    }
}
