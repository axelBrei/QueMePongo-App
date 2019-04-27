package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

import dds.frba.utn.quemepongo.DAO.JsonDao;


public class JsonController {
    private Context context;
    private Gson gson;
    private JsonDao jsonDao;

    private class ListContainer {
        @SerializedName(value = "colores", alternate = "telas")
        private List list;

        public ListContainer() {
        }

        public List getList() {
            return list;
        }

        public void setList(List list) {
            this.list = list;
        }
    }

    public JsonController(Context context) {
        this.context = context;
        gson = new Gson();
        jsonDao = new JsonDao(context);
    }

    public List getColors(){
        try {
            String jsonString = jsonDao.getColorsBuffer();
            return gson.fromJson(jsonString, ListContainer.class).getList();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<String> getTiposDeTela(){
        try{
            String jsonString = jsonDao.getTipoDeTelaBuffer();
            return gson.fromJson(jsonString, ListContainer.class).getList();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
