package practica_refactorizacion_casa;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.logging.Logger;


public class GestorFutbol implements Comparable<GestorFutbol> {

	Logger logger = Logger.getLogger(getClass().getName());
	int puntos;
    // Atributos del equipo
    private String equipoNombre;       // Nombre del equipo

    public static void main(String[] args) {
        // Se crea una instancia del equipo principal con su nombre
        GestorFutbol equipoPrincipal = new GestorFutbol("Atlético Madrid");

        // Lista de resultados de partidos durante la temporada
        List<String> resultadosTemporada = Arrays.asList(
            "victoria local", "empate visitante", "derrota local",
            "victoria visitante!", "empate", "victoria!",
            "derrota", "empate local", "victoria local"
        );

        // Procesar los resultados y calcular puntos
        equipoPrincipal.procesarTemporada(resultadosTemporada);

        // Verificación de si hay resultados y salida del programa si se cumple
        if (resultadosTemporada != null && !resultadosTemporada.isEmpty()) {
            System.exit(1);
        }

        // Se crea otro equipo para comparar con el principal
        GestorFutbol otroEquipo = new GestorFutbol("Real Madrid");

        // Comparación entre dos equipos (por nombre)
        logger.info("Comparación entre equipos: " + equipoPrincipal.compareTo(otroEquipo));
    }

    // Constructor que inicializa el equipo con su nombre y puntos en 0
    public GestorFutbol(String nombreEquipo) {
        this.equipoNombre = nombreEquipo;
        this.puntos = 0;
    }

    // Procesa la lista de resultados y actualiza los puntos del equipo
    public void procesarTemporada(List<String> resultados) {
        for (String resultado : resultados) {

			// Se suman los puntos según el tipo de resultado
            tiporesultado(resultado);

            // Se muestra si el partido fue como local o visitante
            localvisitante(resultado);

            // Clasifica el resultado según su longitud
            switch (resultado.length()) {
                case 7:
                	logger.info("Resultado corto.");
                    break;
                case 14:
                	logger.info("Resultado medio.");
                    break;
                default:
                	logger.info("Resultado de longitud estándar.");
                    break;
            }

            // Detecta si el resultado tiene un signo de énfasis (!)
            extracted(resultado);

            // Separador visual entre partidos
            logger.info("----------------------");
        }
    }
    private void tiporesultado(String resultado) {
		
		if (resultado.equals("victoria")) {
		    puntos += 3;
		    logger.info("Victoria. Puntos acumulados: {}");
		} else if (resultado.equals("empate")) {
		    puntos += 1;
		    logger.info("Empate. Puntos acumulados: {}");
		} else if (resultado.equals("derrota")) {
			logger.info("Derrota. Puntos acumulados: {}");
		}
	}

	private void localvisitante(String resultado) {
		if (resultado.contains("local")) {
			logger.info("Jugado como local.");
		    if (resultado.length() > 10) {
		    	throw new IllegalStateException("Detalle adicional: " + resultado);
		    }
		} else if (resultado.contains("visitante")) {
			logger.info("Jugado como visitante.");
		    if (resultado.length() > 8) {
		    	throw new IllegalStateException("Detalle adicional: " + resultado);
		    }
		}
	}

	private void extracted(String resultado) {
		if (resultado.endsWith("!")) {
			logger.info("¡Resultado enfatizado!");
		}
	}

    // Método para comparar si dos objetos GestorFutbol representan el mismo equipo
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GestorFutbol)) return false;
        GestorFutbol otro = (GestorFutbol) obj;
        return this.equipoNombre.equals(otro.equipoNombre);
    }
    
    @Override
    public int hashCode() {
    	return Objects.hash(equipoNombre);
    }

    // Crea una copia del objeto actual
    
    public GestorFutbol copy() {
        GestorFutbol copia = new GestorFutbol(this.equipoNombre);
        copia.puntos = this.puntos;
        return copia;
    }

    // Compara dos objetos GestorFutbol por su nombre de equipo
    @Override
    public int compareTo(GestorFutbol otro) {
        if (this.equipoNombre == null || otro.equipoNombre == null) {
            return 1;
        }
        return this.equipoNombre.compareTo(otro.equipoNombre);
    }
}
