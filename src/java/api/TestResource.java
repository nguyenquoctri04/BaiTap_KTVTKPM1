package api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import java.util.HashMap;
import java.util.Map;

@Path("/test")
public class TestResource {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response testConnection() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Kết nối thành công!");
        response.put("timestamp", System.currentTimeMillis());
        
        return Response.ok(response).build();
    }
}