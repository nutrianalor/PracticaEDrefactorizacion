# Practica refactorizacion

### Nuria Alba Ortega

# Errores

## 1.- Replace this use of System.out by a logger.
```
System.out.println("...")
```
En el desarrollo de software, los registros (logs) sirven como un historial de eventos dentro de una aplicación, proporcionando información crucial para la depuración. 

Al registrar eventos, es esencial asegurarse de que los logs sean:

fácilmente accesibles

uniformemente formateados para facilitar su lectura

correctamente almacenados

registrados de forma segura cuando se trata de datos sensibles

Estos requisitos no se cumplen si un programa escribe directamente en las salidas estándar (por ejemplo, System.out, System.err). Por eso, se recomienda encarecidamente definir y utilizar un registrador (logger) dedicado.

Lo que debemos hacer en este caso es sustituir los System.out.println("...") por lo siguiente:

```
logger.info("...")
```
También debemos importar fuera de la clase:

```
import java.util.logging.Logger;
```

Y además lo siguiente adentro de la clase:
```
Logger logger = Logger.getLogger(getClass().getName());
```

## 2.- Use "BigDecimal.valueOf" instead.

Se BigDecimal utiliza para representar números decimales con signo inmutables y de precisión arbitraria.

A diferencia de BigDecimal, el doubletipo primitivo y el Doubletipo tienen una precisión limitada debido al uso de punto flotante IEEE 754 de 64 bits de doble precisión. Debido a la imprecisión del punto flotante, el constructor puede ser algo impredecible.BigDecimal(double)

Por ejemplo, escribir no crea un BigDecimal que sea exactamente igual a 0,1, sino que es igual a 0,1000000000000000055511151231257827021181583404541015625. Esto se debe a que 0,1 no se puede representar exactamente como un doble (o, en ese sentido, como una fracción binaria de cualquier longitud finita).new BigDecimal(0.1)

```
BigDecimal presupuestoEstimado = new BigDecimal("123456.78");
```
Hay que ponerlo entre comillas.

## 3.- This class overrides "equals()" and should therefore also override "hashCode()".

Según la especificación del lenguaje Java, existe un contrato entre equals(Object)y hashCode():

Si dos objetos son iguales según el equals(Object)método, entonces llamar al hashCodemétodo en cada uno de los dos objetos debe producir el mismo resultado entero.

No es necesario que si dos objetos son desiguales según el equals(java.lang.Object)método, entonces llamar al hashCodemétodo en cada uno de los dos objetos deba producir resultados enteros distintos.

Sin embargo, el programador debe tener en cuenta que producir resultados enteros distintos para objetos desiguales puede mejorar el rendimiento de las tablas hash.

Para cumplir con este contrato, dichos métodos deben ser heredados o ambos anulados.

```
    @Override
    public int hashCode() {
    	return Objects.hash(equipoNombre);
    }
```

## 4.- Simply return -1

El método `Comparable.compareTo` devuelve un entero negativo, cero o un entero positivo para indicar si el objeto es menor, igual o mayor que el parámetro. Lo que importa es el signo del valor de retorno, o si es cero, no su magnitud.

Devolver un valor constante, positivo o negativo, distinto de los básicos (-1, 0 o 1) no proporciona información adicional al emisor. Además, podría confundir a los lectores de código que intentan comprender su propósito.

```
return 1;
```

## 5.- Make equipoNombre a static final constant or non-public and provide accessors if needed.

Los campos públicos pueden ser modificados por cualquier parte del código y esto puede generar cambios inesperados y errores difíciles de rastrear.

Los campos públicos no ocultan los detalles de implementación. Por lo tanto, ya no es posible cambiar el almacenamiento interno de los datos sin afectar el código cliente de la clase.

El código es más difícil de mantener.

```
private String equipoNombre;
```

```
private int puntos; 
```

```
private static int partidosTotales = 0;
```

## 6.- Rename this field "NOMBRE_REAL_MADRID" to match the regular expression '^[a-z][a-zA-Z0-9]*$'.

No sigue la convención de nomenclatura recomendada para las variables en Java.

La convención que menciona el error está expresada por la siguiente expresión regular: `'^[a-z][a-zA-Z0-9]*$'`. Esto significa:

Comienza con una letra minúscula `(^[a-z])`.

Después de la primera letra, puede contener letras mayúsculas, minúsculas o números `([a-zA-Z0-9]*)`.

```
private static String nOMBRE_REAL_MADRID = "Real Madrid club de Fútbol";
```
```
private static String nOMBRE_ATLETICO_MADRID = "Atlético de Madrid";
```


## 7.- Remove this unused "resultadosArray" local variable.

Una variable local no utilizada es una variable que se ha declarado, pero no se utiliza en ninguna parte del bloque de código donde está definida. Es código inactivo, lo que contribuye a una complejidad innecesaria y genera confusión al leer el código. Por lo tanto, debe eliminarse del código para mantener la claridad y la eficiencia.

¿Cuál es el impacto potencial?
Tener variables locales sin utilizar en su código puede generar varios problemas:

Disminución de la legibilidad : Las variables no utilizadas pueden dificultar la lectura del código. Añaden líneas adicionales y complejidad, lo que puede distraer la atención de la lógica principal del código.
Malentendido : Cuando otros desarrolladores lean tu código, podrían preguntarse por qué una variable se declara pero no se usa. Esto puede generar confusión y una mala interpretación de la intención del código.
Posibles errores : Si una variable se declara pero no se utiliza, podría indicar un error o código incompleto. Por ejemplo, si declaraste una variable con la intención de usarla en un cálculo, pero luego olvidaste hacerlo, tu programa podría no funcionar como se esperaba.
Problemas de mantenimiento : Las variables sin usar pueden dificultar el mantenimiento del código. Si un programador ve una variable sin usar, podría pensar que es un error e intentar corregir el código, lo que podría generar nuevos errores.
Uso de memoria : Aunque los compiladores modernos son lo suficientemente inteligentes como para ignorar las variables no utilizadas, no todos lo hacen. En tales casos, las variables no utilizadas ocupan espacio de memoria, lo que resulta en un uso ineficiente de los recursos.
En resumen, las variables locales no utilizadas pueden hacer que el código sea menos legible, más confuso y más difícil de mantener, además de generar errores o un uso ineficiente de la memoria. Por lo tanto, es recomendable eliminarlas.

## 8.- Use the built-in formatting to construct this argument.

Algunas llamadas a métodos pueden ser "sin operaciones", lo que significa que el método invocado no realiza ninguna acción según la configuración de la aplicación (p. ej., registros de depuración en producción). Sin embargo, incluso si el método no realiza ninguna acción, es posible que sus argumentos deban evaluarse antes de llamarlo.

Pasar argumentos de mensaje que requieren una evaluación adicional a una com.google.common.base.Preconditionscomprobación de Guava puede resultar en una pérdida de rendimiento. Esto se debe a que, independientemente de si son necesarios o no, cada argumento debe resolverse antes de llamar al método.

De manera similar, pasar cadenas concatenadas a un método de registro también puede generar una pérdida de rendimiento innecesaria porque la concatenación se realizará cada vez que se llame al método, independientemente de que el nivel de registro sea lo suficientemente bajo para mostrar el mensaje o no.

En lugar de ello, debe estructurar su código para pasar valores estáticos o precalculados a Preconditionslas llamadas de verificación de condiciones y registro.

En concreto, se debe utilizar el formato de cadena integrado en lugar de la concatenación de cadenas, y si el mensaje es el resultado de una llamada a un método, entonces Preconditionsse debe omitir por completo y se debe lanzar condicionalmente la excepción relevante en su lugar.

```
throw new IllegalStateException("mensaje " + mensaje2);
```

## 9.- Refactor this method to reduce its Cognitive Complexity from 18 to the 15 allowed.

Esta regla plantea un problema cuando la complejidad cognitiva del código de una función supera un determinado umbral.

La complejidad cognitiva mide la dificultad de comprender el flujo de control de una unidad de código. El código con alta complejidad cognitiva es difícil de leer, comprender, probar y modificar.

Como regla general, una alta complejidad cognitiva es una señal de que el código debe refactorizarse en partes más pequeñas y fáciles de administrar.

¿Qué sintaxis del código afecta la puntuación de complejidad cognitiva?
Estos son los conceptos básicos:

La complejidad cognitiva aumenta cada vez que el código rompe el flujo de lectura lineal normal.
Esto afecta, por ejemplo, a las estructuras de bucle, los condicionales, las capturas, los interruptores, los saltos a etiquetas y las condiciones que combinan múltiples operadores.
Cada nivel de anidamiento aumenta la complejidad.
Durante la lectura de código, cuanto más se profundiza en las capas anidadas, más difícil resulta recordar el contexto.
Las llamadas a métodos son gratuitas.
Un nombre de método bien seleccionado es un resumen de varias líneas de código. El lector puede explorar primero una vista general de lo que realiza el código y luego profundizar en el contenido de las funciones llamadas.
Nota: Esto no aplica a las llamadas recursivas, ya que estas incrementarán la puntuación cognitiva.
El método de cálculo está completamente detallado en el PDF vinculado en los recursos.



## 10.- Define a constant instead of duplicating this literal "Victoria. Puntos acumulados: {}" 3 times.

Los strings duplicados hacen que el proceso de refactorización sea complejo y propenso a errores, ya que cualquier cambio debería propagarse en todas las ocurrencias.

## 11.- Remove this "clone" implementation; use a copy constructor or copy factory instead.

Esta regla plantea un problema cuando una clase anula el Object.clonemétodo en lugar de recurrir a un constructor de copias u otros mecanismos de copia.

El mecanismo Object.clone/ java.lang.Cloneableen Java debe considerarse roto por las siguientes razones y, en consecuencia, no debe utilizarse:

CloneableEs una interfaz de marcador sin API, pero con un contrato sobre el comportamiento de la clase que el compilador no puede imponer. Esta es una mala práctica.
Las clases se instancian sin llamar a su constructor, por lo que no se pueden aplicar posibles condiciones previas.
Hay fallas de implementación por diseño cuando se anulan Object.clone, como las conversiones de tipos o el manejo de CloneNotSupportedExceptionexcepciones.

```
public GestorFutbol copy() {
        GestorFutbol copia = new GestorFutbol(this.equipoNombre);
        copia.puntos = this.puntos;
        return copia;
    }
```