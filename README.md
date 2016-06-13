Metaheurísticas
=============================================

Prácticas de Metaheurísticas(MH) sobre selección de características

Implementación
---------------------------------------------

- **ArffReader**, que contiene el código de la función que lee archivos *.arff* a partir de los que se generan la instancias.  
- **CSVFileWriter**, que contiene el código de la función que escribe los archivos *.csv* de salida.  
- **SC**, que contiene la función main y el *5x2-CrossValidation*.  
- La clase abstracta **Heuristic**, que implementa las funciones comunes que no dependen del azar dando cabida a la herencia por parte de cualquier tipo de heurística.  
- La clase abstracta **RandomHeuristic**, que implementa las funciones comunes que dependen del azar dando cabida a la herencia por parte las heurísticas con componente aleatoria.  
- La clase **SFS**, que implementa el comportamiento de la heurística de *Sequential Forward Search*.  
- La clase **LocalSearch**, que implementa el comportamiento de la heurística de *Búsqueda Local del Primero Mejor* con solución inicial aleatoria.  
- La clase **SimulatedAnnealing**, que implementa el comportamiento de la heurística de *Enfriamiento Simulado* con el esquema de enfriamiento de Cauchy modificado.  
- La clase **TabooSearch**, que implementa el comportamiento de la heurística de *Búsqueda Tabú* sin multiarranque con solución inicial aleatoria.  
- La clase **BasicMultiBootingSearch**, que implementa el comportamiento de la heurística de *Búsqueda Multiarranque Básica* con solución inicial aleatoria y optimizando con *Búsqueda Local*.  
- La clase **GRASP**, que implementa el comportamiento de la heurística de *Greedy Randomized Adaptive Search Procedure* con solución inicial greedy probabilista y optimizando con *Búsqueda Local*.  
- La clase **IteratedLocalSearch**, que implementa el comportamiento de la heurística de *Búsqueda Local Reiterada* con solución inicial aleatoria y posterior refinamiento mediante *Búsqueda Local* y mutación de la mejor solución encontrada.  
- La clase abstracta **GeneticAlgorithm**, que implementa las funciones comunes de los algoritmos genéticos permitiendo de este modo estructurar el esqueleto básico dando cabida a la herencia por parte de heurística con inspiración biológica.  
- La clase **GenerationalGA**, que implementa el comportamiento de la heurística del *Algoritmo Genético Generacional con Elitismo* con población inicial aleatoria.  
- La clase **SteadyStateGA**, que implementa el comportamiento de la heurística del *Algoritmo Genético Estacionario* con dos descendientes por generación con población inicial aleatoria.  

Licencias
---------------------------------------------
El proyecto se distribuye bajo la licencia [GPL](https://github.com/oxcar103/Metaheuristicas/blob/master/LICENSE)

La licencia **NO** se aplicará para el material aportado por el profesor.  
La licencia **SÍ** se aplicará sobre **CSVFileWriter** aunque está tomado de [Lothar Soto](https://github.com/Lothar94).  

Off-Topic
---------------------------------------------

Certificado de Calidad: [3179597](http://github.com/oxcar103/Metaheuristicas/commit/3179597a514cecae327c73c6edd3000d9be9cfa2)
