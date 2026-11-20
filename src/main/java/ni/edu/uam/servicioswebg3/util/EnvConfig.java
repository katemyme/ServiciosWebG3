package ni.edu.uam.servicioswebg3.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Lee la configuración desde variables de entorno.
 *
 * Orden de búsqueda:
 *   1. Variable de entorno del sistema (System.getenv).
 *   2. Archivo .env en la raíz del proyecto (útil para desarrollo en IntelliJ).
 *
 * El archivo .env NO se sube a git (ver .gitignore); usa .env.example como plantilla.
 */
public final class EnvConfig {

    private static final Path ARCHIVO_ENV = Path.of(".env");
    private static Map<String, String> valoresArchivo;

    private EnvConfig() {
    }

    /** Devuelve el valor de la variable o null si no está definida. */
    public static String get(String clave) {
        String valor = System.getenv(clave);
        if (valor == null || valor.isBlank()) {
            valor = leerArchivoEnv().get(clave);
        }
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }


    public static String getRequired(String clave) {
        String valor = get(clave);
        if (valor == null) {
            throw new IllegalStateException(
                    "Falta la variable de entorno " + clave
                            + " (defínala en el sistema o en el archivo .env)");
        }
        return valor;
    }

    private static synchronized Map<String, String> leerArchivoEnv() {
        if (valoresArchivo != null) {
            return valoresArchivo;
        }
        valoresArchivo = new HashMap<>();
        if (!Files.exists(ARCHIVO_ENV)) {
            return valoresArchivo;
        }
        try {
            List<String> lineas = Files.readAllLines(ARCHIVO_ENV, StandardCharsets.UTF_8);
            for (String linea : lineas) {
                String texto = linea.trim();
                if (texto.isEmpty() || texto.startsWith("#")) {
                    continue;
                }
                int igual = texto.indexOf('=');
                if (igual <= 0) {
                    continue;
                }
                String clave = texto.substring(0, igual).trim();
                String valor = texto.substring(igual + 1).trim();
                if (valor.length() >= 2
                        && ((valor.startsWith("\"") && valor.endsWith("\""))
                        || (valor.startsWith("'") && valor.endsWith("'")))) {
                    valor = valor.substring(1, valor.length() - 1);
                }
                valoresArchivo.put(clave, valor);
            }
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer el archivo .env: " + e.getMessage(), e);
        }
        return valoresArchivo;
    }
}
