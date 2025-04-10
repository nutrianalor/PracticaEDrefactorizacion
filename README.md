# Practica refactorizacion

### Nuria Alba Ortega

# Errores

## 1.- Replace this use of System.out by a logger.

En el desarrollo de software, los registros (logs) sirven como un historial de eventos dentro de una aplicación, proporcionando información crucial para la depuración. 

>Es esencial asegurarse de que los logs sean:
>
>* Fácilmente accesibles. 
>
>* Formateados para facilitar su lectura
>
>* Correctamente almacenados
>
>* Registrados de forma segura 

Estos requisitos no se cumplen si un programa escribe directamente en las salidas estándar (por ejemplo, System.out). Por eso, se recomienda utilizar logger.

Lo que debemos hacer en este caso es sustituir los `System.out.println("...")` por lo siguiente:

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

BigDecimal es una clase que se usa para representar números decimales con signo que no cambian (inmutables) y que pueden tener una precisión muy alta, sin límite fijo. Esto lo hace ideal para cálculos donde se necesita mucha exactitud, como en operaciones financieras.

Por ejemplo, al escribir new BigDecimal(0.1), no se obtiene exactamente 0.1, sino algo como 0.10000000000000000555... Esto pasa porque el número 0.1 no puede representarse de forma exacta en binario con una cantidad finita de bits.

```
BigDecimal presupuestoEstimado = new BigDecimal("123456.78");
```
Hay que poner el número entre comillas!!

## 3.- This class overrides "equals()" and should therefore also override "hashCode()".

Según la especificación del lenguaje Java, existe un contrato entre equals(Object)y hashCode():

Si dos objetos son iguales según el equals(Object), entonces llamar al hashCode en cada uno de los dos objetos debe producir el mismo resultado entero.

Sin embargo, el programador debe tener en cuenta que producir resultados enteros distintos para objetos desiguales puede mejorar el rendimiento de las tablas hash.

>Para cumplir con este contrato, dichos métodos deben ser heredados o anulados.

```
    @Override
    public int hashCode() {
    	return Objects.hash(equipoNombre);
    }
```

## 4.- Simply return -1

El método `Comparable.compareTo` devuelve un entero negativo, cero o un entero positivo para indicar si el objeto es menor, igual o mayor que el parámetro. Lo que importa es el signo del valor de retorno, o si es cero, no su magnitud.

Devolver un valor constante, positivo o negativo, distinto de los básicos (-1, 0 o 1) no proporciona información adicional al emisor.

```
return 1;
```

## 5.- Make equipoNombre a static final constant or non-public and provide accessors if needed.

Los campos públicos pueden ser modificados por cualquier parte del código y esto puede generar cambios inesperados y errores difíciles de encontrar.

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

La convención que menciona el error está expresada por la siguiente expresión regular: `^[a-z][a-zA-Z0-9]*$`. Esto significa:

Comienza con una letra minúscula `(^[a-z])`.

Después de la primera letra, puede contener letras mayúsculas, minúsculas o números `([a-zA-Z0-9]*)`.

```
private static String nombre_real_madrid = "Real Madrid club de Fútbol";
```
```
private static String nombre_atletico_madrid = "Atlético de Madrid";
```


## 7.- Remove this unused "resultadosArray" local variable.

Las variables locales no utilizadas pueden hacer que el código sea menos legible, más confuso y más difícil de mantener, además de generar errores o un uso ineficiente de la memoria. Por lo tanto, es recomendable eliminarlas.

## 8.- Use the built-in formatting to construct this argument.

Algunas llamadas a métodos pueden no hacer nada según la configuración, pero aun así sus argumentos se evalúan. Esto puede causar pérdida de rendimiento si esos argumentos son difíciles de calcular.

Por ejemplo, al registrar mensajes con cadenas concatenadas, se hace el trabajo de evaluación o concatenación incluso si el mensaje nunca se usa. Para evitar esto, es mejor pasar valores ya calculados o usar formatos de cadena en lugar de concatenación. Si el mensaje requiere lógica compleja, es preferible omitir Preconditions y lanzar manualmente una excepción cuando sea necesario.

```
throw new IllegalStateException("mensaje " + mensaje2);
```

## 9.- Refactor this method to reduce its Cognitive Complexity from 18 to the 15 allowed.

Esta regla plantea un problema cuando la complejidad cognitiva del código de una función supera un determinado umbral.

La complejidad cognitiva mide la dificultad de comprender el flujo de control de una unidad de código. El código con alta complejidad cognitiva es difícil de leer, comprender, probar y modificar.

Como regla general, una alta complejidad cognitiva es una señal de que el código debe refactorizarse en partes más pequeñas y fáciles de administrar.

## 10.- Remove this "clone" implementation; use a copy constructor or copy factory instead.

Usar Object.clone() y la interfaz Cloneable en Java no es recomendable porque presentan varios problemas. Cloneable no tiene métodos, pero impone reglas que el compilador no puede verificar, lo cual es una mala práctica. También hay problemas al sobrescribir clone(), como errores en conversiones de tipo y manejo complicado de excepciones como CloneNotSupportedException.

```
public GestorFutbol copy() {
        GestorFutbol copia = new GestorFutbol(this.equipoNombre);
        copia.puntos = this.puntos;
        return copia;
    }
```