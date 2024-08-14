package desafio.api;

import desafio.model.InsumosMedicos;
import jakarta.json.*;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Path("/insumos")
public class InsumosApi {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInsumos() {
        try {
            // Leer el archivo JSON
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("insumos.json");
            JsonReader jsonReader = Json.createReader(inputStream);
            JsonArray jsonArray = jsonReader.readArray();

            // Convertir el JSON a una lista de objetos Insumo
            List<InsumosMedicos> insumos = new ArrayList<>();
            for (JsonValue jsonValue : jsonArray) {
                JsonObject jsonObject = jsonValue.asJsonObject();
                InsumosMedicos insumo = new InsumosMedicos(jsonObject.getString("nombre"), jsonObject.getString("imagen"),jsonObject.getString("descripcion"), jsonObject.getJsonNumber("precio").doubleValue());
                insumos.add(insumo);
            }

            // Convertir la lista de insumos a JSON
            JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
            for (InsumosMedicos insumo : insumos) {
                JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
                jsonObjectBuilder.add("nombre", insumo.getNombre());
                jsonObjectBuilder.add("imagen", insumo.getImagen());
                jsonObjectBuilder.add("descripcion", insumo.getDescripcion());
                jsonObjectBuilder.add("precio", insumo.getPrecio());
                jsonArrayBuilder.add(jsonObjectBuilder.build());
            }

            return Response.ok(jsonArrayBuilder.build()).build();
        } catch (Exception e) {
            // Manejar la excepción
            return Response.serverError().build();
        }
    }
}
