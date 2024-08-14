package desafio.bean;

import desafio.model.InsumosMedicos;
import jakarta.annotation.PostConstruct;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ManagedBean
@SessionScoped
public class InsumosBean {
    private List<InsumosMedicos> insumos;

    @PostConstruct
    public void loadInsumos() {
        try {
            // Consumir la API
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target("http://localhost:8080/desafio-1.0-SNAPSHOT/api/insumos");
            Response response = target.request(MediaType.APPLICATION_JSON).get();

            JsonArray jsonArray = response.readEntity(JsonArray.class);
            insumos = new ArrayList<>();
            for (JsonValue jsonValue : jsonArray) {
                JsonObject jsonObject = jsonValue.asJsonObject();
                InsumosMedicos insumo = new InsumosMedicos(jsonObject.getString("nombre"), jsonObject.getString("imagen"), jsonObject.getString("descripcion"), jsonObject.getJsonNumber("precio").doubleValue());
                insumos.add(insumo);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<InsumosMedicos> getInsumos() {
        return insumos;
    }
}
