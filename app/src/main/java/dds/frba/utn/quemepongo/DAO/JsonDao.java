package dds.frba.utn.quemepongo.DAO;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.util.List;

public class JsonDao {
    private Context context;

    public JsonDao(Context context) {
        this.context = context;
    }

    public String getColorsBuffer() throws IOException {
        // METODO Q DEVUELVE EL JSON COMO UN STRING
        InputStream stream = context.getAssets().open("colors.json");

        int size = stream.available();
        byte[] buffer = new byte[size];
        stream.read(buffer);

        return new String(buffer);
    }

    public String getTipoDeTelaBuffer() throws IOException {
        InputStream stream = context.getAssets().open("tiposDeTela.json");

        int size = stream.available();
        byte[] buffer = new byte[size];
        stream.read(buffer);

        return new String(buffer);
    }
}
