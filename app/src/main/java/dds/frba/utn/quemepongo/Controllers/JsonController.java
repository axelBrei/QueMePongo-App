package dds.frba.utn.quemepongo.Controllers;

import android.content.Context;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import dds.frba.utn.quemepongo.DAO.JsonDao;
import dds.frba.utn.quemepongo.Utils.ListContainer;


public class JsonController {
    private Context context;
    private ObjectMapper objectMapper;
    private JsonDao jsonDao;


    public JsonController(Context context) {
        this.context = context;
        objectMapper = new ObjectMapper();
        jsonDao = new JsonDao(context);
    }

    public List getColors(){
        try {
            String jsonString = jsonDao.getColorsBuffer();
            return objectMapper.readValue(jsonString, ListContainer.class).getList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    public List<String> getTiposDeTela(){
        try{
            String jsonString = jsonDao.getTipoDeTelaBuffer();
            return objectMapper.readValue(jsonString, ListContainer.class).getList();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
