package ni.edu.uam.servicioswebg3.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ni.edu.uam.servicioswebg3.model.Cliente;
import ni.edu.uam.servicioswebg3.util.EnvConfig;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    // Nombres de las variables de entorno (valores en el sistema o en el archivo .env)
    private static final String VAR_URL = "CLIENTES_API_URL";
    private static final String VAR_API_KEY = "CLIENTES_API_KEY"; // opcional

    private final HttpClient cliente = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Cliente> obtenerClientes() throws IOException, InterruptedException {
        String url = EnvConfig.getRequired(VAR_URL);
        String apiKey = EnvConfig.get(VAR_API_KEY);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(15))
                .header("Accept", "application/json")
                .GET();

        // Si el servicio requiere llave (p. ej. Supabase), se envía en los headers
        if (apiKey != null) {
            builder.header("apikey", apiKey);
            builder.header("Authorization", "Bearer " + apiKey);
        }

        HttpResponse<String> response = cliente.send(
                builder.build(), HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            throw new IOException("Error al obtener los clientes (HTTP " + response.statusCode() + ")");
        }

        JsonNode datos = mapper.readTree(response.body());

        if (!datos.isArray()) {
            throw new IOException("La respuesta no es un arreglo JSON");
        }

        List<Cliente> clientes = new ArrayList<>();
        for (JsonNode nodo : datos) {
            Cliente c = new Cliente();
            c.setId(nodo.path("id").asInt());
            c.setNombre(nodo.path("first_name").asText());
            c.setApellido(nodo.path("last_name").asText());
            c.setEmail(nodo.path("email").asText());
            c.setTelefono(nodo.path("phone").asText());
            c.setFechaNacimiento(leerFecha(nodo.path("date_of_birth")));
            c.setPais(nodo.path("country").asText());
            c.setCiudad(nodo.path("city").asText());
            c.setDireccion(nodo.path("address").asText());
            c.setIngresoAnual(nodo.path("annual_income").asDouble());
            c.setPuntajeCredito(nodo.path("credit_score").asInt());
            c.setTieneDeuda(nodo.path("has_unpaid_debt").asBoolean());
            c.setMontoDeuda(nodo.path("unpaid_debt_amount").asDouble());
            c.setEstado(nodo.path("status").asText());
            c.setCreadoEn(leerFechaHora(nodo.path("created_at")));
            c.setActualizadoEn(leerFechaHora(nodo.path("updated_at")));
            clientes.add(c);
        }
        return clientes;
    }

    // "1988-03-14" -> LocalDate (null si viene vacío)
    private static LocalDate leerFecha(JsonNode nodo) {
        String texto = nodo.asText(null);
        return (texto == null || texto.isBlank()) ? null : LocalDate.parse(texto);
    }

    // "2026-11-17T16:14:20.965102+00:00" -> OffsetDateTime (null si viene vacío)
    private static OffsetDateTime leerFechaHora(JsonNode nodo) {
        String texto = nodo.asText(null);
        return (texto == null || texto.isBlank()) ? null : OffsetDateTime.parse(texto);
    }
}
