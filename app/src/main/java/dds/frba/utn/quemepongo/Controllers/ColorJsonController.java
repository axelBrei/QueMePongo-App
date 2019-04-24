package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import dds.frba.utn.quemepongo.DAO.ColoresJsonDao;


public class ColorJsonController {

    public List<String> getColors(Context context){
        Gson gson = new Gson();
        try {
            String jsonString = new ColoresJsonDao().getColorsBuffer(context);
            return gson.fromJson(jsonString, ColoresContainer.class).getColores();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class ColoresContainer{
        private List<String> colores;

        public ColoresContainer() {
        }

        public List<String> getColores() {
            return colores;
        }

        public void setColores(List<String> colores) {
            this.colores = colores;
        }
    }
}
