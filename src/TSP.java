/**
* TSP
* La clase TSP es la encargada de tener todos los métodos que hacen funcionar al problema TSP mediante el algoritmo de Colonia
* de Hormigas. Contiene un Scanner para leer los datos del usuario; una lista de ciudades enumeradas ascendentemente; una lista
* de hormigas que serán las responsables de ejecutar los pasos del algoritmo; un número de ciudades especificado por el usuario;
* un número de hormigas especificado por el usuario; una ciudad que representa el punto de partida para todas las hormigas,
* especificado por el usuario; un camino óptimo correspondiente al camino más corto entre las ciudades y que las hormigas van
* descubriendo a medida que avanza el algoritmo; una matriz de conexiones que representa la distancia que hay entre las ciudades;
* una matriz de feromonas que representa la cantidad de feromonas que hay entre cada ciudad; un factor de evaporación que 
* representa la cantidad de evaporación que sufrira cada rastro de feromona luego de hacer un recorrido completo; una cantidad
* de feromona que cada hormiga depositará luego de un recorrido completo; una cantidad de feromona inicial para inicializar la
* matriz de feromonas; un regulador de feromona para que las feromonas en los caminos tengan un peso y un regulador de
* visibilidad para que el inverso de la distancia entre ciudades tengan un peso.
* 
* @author Maldonado, Carlos.
*/
import java.util.ArrayList;
import java.util.Scanner;

public class TSP 
{
    Scanner sc = new Scanner(System.in);
    private ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();
    private ArrayList<Hormiga> cantHormigas = new ArrayList<Hormiga>();
    private int numCiudades = 0;
    private int hormigas = 0;
    private int puntoPartida = 0;
    private double caminoOptimo = 0;
    private double conexiones[][];
    private double feromonas[][];
    private double evaporacion = 0.01;
    private double cantidadFeromona = 0.1;
    private double feromonaInicial = 0.1;
    private double reguladorFeromona = 1; 
    private double reguladorVisibilidad = 1;
    
    /**
    * bienvenida
    * Este método es el encargado de realizar toda la lógica de la aplicación del algoritmo de Colonia de Hormigas a un
    * problema TSP.
    */
    public void bienvenida()
    {
        System.out.println("===================TSP por algoritmo de colonia de hormigas===================\n\n");
        System.out.print("Por favor introduzca el número de ciudades: ");
        numCiudades = sc.nextInt();
        int cont = 1;
        while(cont <= numCiudades)
        {
            Ciudad ciudadNueva = new Ciudad(cont);
            ciudadNueva.setVisitada(false);
            this.ciudades.add(ciudadNueva);
            cont++;
        }
        this.conexiones = new double[this.ciudades.size()][this.ciudades.size()];
        this.feromonas = new double[this.ciudades.size()][this.ciudades.size()];
        this.conectarCiudades();
        System.out.print("Por favor introduzca el número de hormigas: ");
        hormigas = sc.nextInt();
        this.inicializarFeromonas();
        this.inicializarHormigas();
        this.imprimirCiudades();
        System.out.print("\nPor favor introduzca el número de la ciudad de partida: ");
        puntoPartida = sc.nextInt();
        for(int i=0;i<this.cantHormigas.size();i++)
        {
            this.cantHormigas.get(i).setCiudadActual(this.ciudades.get(puntoPartida-1));
            this.ciudades.get(puntoPartida-1).setVisitada(true);
            this.cantHormigas.get(i).getCiudadesVisitadas().add(this.ciudades.get(puntoPartida-1));
        }
        System.out.println("========================================================================CONDICIONES INICIALES========================================================================");
        this.imprimirMatrizAdyacencia(); 
        this.imprimirFeromonas();
        System.out.println("\n========================================================================INICIO DEL ALGORITMO========================================================================");
        this.coloniaHormigas2();
        this.caminoOptimo();
        System.out.println("\n\n\nEl camino óptimo es: "+this.caminoOptimo);
    }
    
    /**
    * conectarCiudades
    * Este método es el encargado de asignarle una distancia a todas las conexiones existentes a cada una de las ciudades.
    */
    public void conectarCiudades()
    {
        for(int i=0;i<this.ciudades.size();i++)
        {
            for(int j=0;j<this.ciudades.size();j++)
            {
                if(i == j)
                {
                    
                }
                else if(this.conexiones[j][i] != 0.0)
                {
                    this.conexiones[i][j] = this.conexiones[j][i];
                }
                else
                {
                    System.out.print("Por favor introduzca la distancia entre la ciudad "+(i+1)+" y la ciudad "+(j+1)+": ");
                    this.conexiones[i][j] = sc.nextDouble();
                }
            }
        }
    }
    
    /**
    * coloniaHormigas2
    * Este método es el encargado de contener la lógica del algoritmo Colonia de Hormigas como tal que ejecutará cada una de 
    * las hormigas.
    */
    public void coloniaHormigas2()
    {
        for(int i=0;i<this.cantHormigas.size();i++)
        {
            System.out.println("\n================HORMIGA "+i+"================");
            this.cantHormigas.get(i).calcularHayFeromona(this.cantHormigas.get(i).getCiudadActual().getIdentificador()-1);
            this.cantHormigas.get(i).calcularProbabilidades2(this.cantHormigas.get(i).getCiudadActual().getIdentificador()-1, this.conexiones, this.feromonas, this.reguladorFeromona, this.reguladorVisibilidad);
            this.cantHormigas.get(i).imprimirHayFeromonas();            
            this.cantHormigas.get(i).imprimirProbabilidades();
            while(this.cantHormigas.get(i).getCiudadesVisitadas().size() != this.ciudades.size())
            {
                double haciaDonde = Math.random();
                System.out.println("\nSoy la hormiguita "+i+" y tengo una probabilidad de "+haciaDonde);
                for(int j=0;j<this.cantHormigas.get(i).getProbabilidades().length;j++)
                {
                    if(this.cantHormigas.get(i).getProbabilidades()[this.cantHormigas.get(i).getCiudadActual().getIdentificador()-1][j] != 0.0)
                    {
                        if(haciaDonde < this.cantHormigas.get(i).getProbabilidades()[this.cantHormigas.get(i).getCiudadActual().getIdentificador()-1][j])
                        {
                            System.out.println("\nSoy la hormiguita "+i+" y voy a la ciudad "+(j+1));
                            this.cantHormigas.get(i).setRecorridoTotal(this.cantHormigas.get(i).getRecorridoTotal() + this.conexiones[this.cantHormigas.get(i).getCiudadActual().getIdentificador()-1][j]);
                            this.cantHormigas.get(i).setCiudadActual(this.ciudades.get(j));
                            this.cantHormigas.get(i).getCiudadesVisitadas().add(this.ciudades.get(j));
                            this.ciudades.get(j).setVisitada(true);
                            this.cantHormigas.get(i).calcularHayFeromona(this.cantHormigas.get(i).getCiudadActual().getIdentificador()-1);
                            this.cantHormigas.get(i).calcularProbabilidades2(this.cantHormigas.get(i).getCiudadActual().getIdentificador()-1, this.conexiones, this.feromonas, this.reguladorFeromona, this.reguladorVisibilidad);
                            this.cantHormigas.get(i).imprimirProbabilidades();
                            this.cantHormigas.get(i).imprimirHayFeromonas();
                            break;
                        }
                    }
                }
            }
            this.sinVisitar();
            this.resultadoFinal(this.cantHormigas.get(i),i);
            this.calcularFeromona(this.cantHormigas.get(i));
            System.out.println("\nLa siguiente hormiga "+(i+1)+" comenzara con esta configuración");
            this.imprimirFeromonas();
        }
    }
    
    /**
    * calcularFeromona
    * Este método es el encargado de calcular las feromonas que deposita la hormiga en cada camino que ella ha recorrido.
    * 
    * @param hormiga es la hormiga que ya ha realizado con recorrido completo.
    */
    public void calcularFeromona(Hormiga hormiga)
    {
        this.evaporarFeromona();
        for(int i=0;i<hormiga.getCiudadesVisitadas().size()-1;i++)
        {
            this.feromonas[hormiga.getCiudadesVisitadas().get(i).getIdentificador()-1][hormiga.getCiudadesVisitadas().get(i+1).getIdentificador()-1] += (this.cantidadFeromona/hormiga.getRecorridoTotal());
            this.feromonas[hormiga.getCiudadesVisitadas().get(i+1).getIdentificador()-1][hormiga.getCiudadesVisitadas().get(i).getIdentificador()-1] += (this.cantidadFeromona/hormiga.getRecorridoTotal());
        }
    }
    
    /**
    * evaporarFeromona
    * Este método es el encargado de evaporar las feromonas existentes en cada uno de los caminos entre las ciudades luego de
    * que una hormiga ha realizado un recorrido completo.
    */
    public void evaporarFeromona()
    {
        for(int i=0;i<this.feromonas.length;i++)
        {
            for(int j=0;j<this.feromonas.length;j++)
            {
                if(i == j)
                {
                    
                }
                else
                {
                    this.feromonas[i][j] = (1-this.evaporacion)*this.feromonas[i][j];
                }
            }
        }
    }
    
    /**
    * inicializarFeromonas
    * inicializarHormigas
    * Estos métodos son los encargados de inicializar la lista de hormigas y la matriz de feromonas iniciales. 
    */
    public void inicializarHormigas()
    {
        for(int i=0;i<this.hormigas;i++)
        {
            Hormiga hormiguita = new Hormiga(this.ciudades.size());
            this.cantHormigas.add(hormiguita);
        }
    }
    
    public void inicializarFeromonas()
    {
        for(int i=0;i<this.feromonas.length;i++)
        {
            for(int j=0;j<this.feromonas.length;j++)
            {
                if(i == j)
                {
                    
                }
                else
                {
                    this.feromonas[i][j] = this.feromonaInicial;
                }
            }
        }
    }
    
    /**
    * imprimirCiudades
    * imprimirMatrizAdyacencia
    * imprimirFeromonas
    * Estos métodos son los encargados de imprimir por consola las ciudades en la aplicación, las distancias entre las ciudades
    * y las feromonas entre cada ciudad.
    */
    public void imprimirCiudades()
    {
        System.out.println("====Ciudades en el TSP====");
        for(int i=0;i<this.ciudades.size();i++)
        {
            System.out.print("["+this.ciudades.get(i).getIdentificador()+"]");
        }
    }
    
    public void imprimirMatrizAdyacencia()
    {
        System.out.println("\n====Ciudades conectadas (distancia)====");
        for(int i=0;i<this.ciudades.size();i++)
        {
            for(int j=0;j<this.ciudades.size();j++)
            {
                System.out.print("["+this.conexiones[i][j]+"]");
            }
            System.out.println("");
        }
    }
    
    public void imprimirFeromonas()
    {
        System.out.println("\n====Feromonas en cada ciudad====");
        for(int i=0;i<this.feromonas.length;i++)
        {
            for(int j=0;j<this.feromonas.length;j++)
            {
                System.out.print("["+this.feromonas[i][j]+"]");
            }
            System.out.println("");
        }
    }
    
    /**
    * resultadoFinal
    * Este método es el encargado de imprimir por consola la secuencia de ciudades que recorrió una hormiga y la distancia 
    * acumulada luego de recorrerlo.
    * 
    * @param hormiga es la hormiga que acaba de finalizar el recorrido.
    * @param num es el identificador de la hormiga.
    */
    public void resultadoFinal(Hormiga hormiga, int num)
    {
        System.out.println("\n=======CAMINO ESCOGIDO POR LA HORMIGA "+num+"=======");
        for(int i=0;i<hormiga.getCiudadesVisitadas().size();i++)
        {
            System.out.print(hormiga.getCiudadesVisitadas().get(i).getIdentificador()+"-");
        }
        System.out.println("\nCon una distancia de: "+hormiga.getRecorridoTotal());
        System.out.println("===============================================\n");
    }
    
    /**
    * caminoOptimo
    * Este método es el encargado de calcular el camino más corto de todas las hormigas que finalizaron un recorrido completo.
    */
    public void caminoOptimo()
    {
        this.caminoOptimo = this.cantHormigas.get(0).getRecorridoTotal();
        for(int i=0;i<this.cantHormigas.size();i++)
        {
            if(this.cantHormigas.get(i).getRecorridoTotal() < this.caminoOptimo)
            {
                this.caminoOptimo = this.cantHormigas.get(i).getRecorridoTotal();
            }
        }
    }
    
    /**
    * sinVisitar
    * Este método es el encargado de colocar todas las ciudades en un estado de no visitadas luego de que una hormiga ha 
    * finalizado su recorrido.
    */
    public void sinVisitar()
    {
        for(int i=0;i<this.ciudades.size();i++)
        {
            this.ciudades.get(i).setVisitada(false);
        }
        this.ciudades.get(puntoPartida-1).setVisitada(true);
    }
}