/**
* Hormiga
* La clase Hormiga es la encargada de contener todos los métodos que realiza cada hormiga para trasladarse entre cada una de las
* ciudades hasta finalizar un recorrido completo. Contiene una lista de ciudades visitadas por cada hormiga que realiza un
* recorrido completo; una ciudad que representa la ciudad actual en la cual está situada la hormiga que actualmente recorre las
* ciudades; un recorrido total que representa la distancia acumulada de la hormiga en el recorrido; una matriz de probabilidades
* que representa la preferencia que cada hormiga para trasladarse a una ciudad u otra y una matriz que representa las feromonas
* que puede detectar la hormiga una vez que esta situada en una ciudad.
* 
* @author Maldonado, Carlos.
*/
import java.util.ArrayList;

public class Hormiga
{
    private ArrayList<Ciudad> ciudadesVisitadas = new ArrayList<Ciudad>();
    private Ciudad ciudadActual = new Ciudad();
    private double recorridoTotal;
    private double probabilidades[][];
    private int hayFeromona[][];
    
    public Hormiga()
    {
        
    }
    
    /**
    * @constructor Hormiga 
    * 
    * @param tam representa el número de ciudades que tendrá el problema de TSP.
    */
    public Hormiga(int tam)
    {
        this.probabilidades = new double[tam][tam];
        this.hayFeromona = new int[tam][tam];
        this.inicializarProbabilidades();
        this.inicializarHayFeromona();
    }
    
    /**
    * transicion2
    * Este método es el encargado de calcular el valor probabilístico de preferencia sobre una ciudad u otra.
    * 
    * @param ciudadActual representa el número de la ciudad en donde está situada actualmente la hormiga.
    * @param conexiones representa la matriz de distancias entre cada una de las ciudades.
    * @param feromonas representa la matriz de feromonas entre cada una de las ciudades.
    * @param reguladorFeromona es el peso que tiene cada feromona entre una ciudad y otra.
    * @param reguladorVisibilidad es el peso que tiene cada inverso de la distancia entre una ciudad y otra.
    * @param feromonaEnCamino es la cantidad de feromona actual que hay en el camino que ella escogió a moverse.
    * @param distanciaAMoverse es la distancia que recorrerá la hormiga en el camino que ella escogió a moverse.
    * 
    * @return double es el valor probabilístico de preferencia sobre una ciudad u otra.
    */
    public double transicion2(int ciudadActual, double conexiones[][], double feromonas[][], double reguladorFeromona, double reguladorVisibilidad, double feromonaEnCamino, double distanciaAMoverse)
    {
        double denominador = 0.0;
        for(int i=0;i<conexiones.length;i++)
        {
            if(this.hayFeromona[ciudadActual][i] == 0.0)
            {
                
            }
            else
            {
                denominador += (Math.pow(feromonas[ciudadActual][i], reguladorFeromona))*(Math.pow((1/conexiones[ciudadActual][i]), reguladorVisibilidad));
            }
        }
        return (Math.pow(feromonaEnCamino, reguladorFeromona))*(Math.pow((1/distanciaAMoverse), reguladorVisibilidad))/denominador;
    }
    
    /**
    * calcularProbabilidades2
    * Este método es el encargado de calcular cada valor en la matriz de probabilidades que tiene cada hormiga.
    * 
    * @param ciudadActual representa el número de la ciudad en donde está situada actualmente la hormiga.
    * @param conexiones representa la matriz de distancias entre cada una de las ciudades.
    * @param feromonas representa la matriz de feromonas entre cada una de las ciudades.
    * @param reguladorFeromona es el peso que tiene cada feromona entre una ciudad y otra.
    * @param reguladorVisibilidad es el peso que tiene cada inverso de la distancia entre una ciudad y otra.
    */
    public void calcularProbabilidades2(int ciudadActual, double conexiones[][], double feromonas[][], double reguladorFeromona, double reguladorVisibilidad)
    {
        double ultimoValor = 0.0;
        for(int i=0;i<this.probabilidades.length;i++)
        {
            for(int j=0;j<this.probabilidades.length;j++)
            {
                if(i == j)
                {
                    this.probabilidades[i][j] = 0.0;
                }
                else
                {
                    if(j == ciudadActual)
                    {
                        this.probabilidades[i][j] = 0.0;
                    }
                    else
                    {
                        if(this.probabilidades[i][j] == 0.0)
                        {
                            
                        }
                        else
                        {
                            this.probabilidades[i][j] = ultimoValor + this.transicion2(i, conexiones, feromonas, reguladorFeromona, reguladorVisibilidad, feromonas[i][j], conexiones[i][j]);
                            ultimoValor = this.probabilidades[i][j];
                        }
                    }
                }
            }
            ultimoValor = 0;
        }
    }
    
    /**
    * calcularHayFeromona
    * Este método es el encargado de calcular si la hormiga es capaz de detectar una feromona en algún camino, siendo 1 si lo 
    * detecta y 0 en caso contrario; asignándolo a la matriz de hayFeromona.
    * 
    * @param ciudadActual representa el número de la ciudad en donde está situada actualmente la hormiga.
    */
    public void calcularHayFeromona(int ciudadActual)
    {
        for(int i=0;i<this.hayFeromona.length;i++)
        {
            for(int j=0;j<this.hayFeromona.length;j++)
            {
                if(i == j)
                {
                    
                }
                else
                {
                    if(j == ciudadActual)
                    {
                        this.hayFeromona[i][j] = 0;
                    }
                    else
                    {
                        if(this.hayFeromona[i][j] == 0)
                        {
                            
                        }
                        else
                        {
                            this.hayFeromona[i][j] = 1;
                        }
                    }
                }
            }
        }
    }
    
    /**
    * inicializarProbabilidades
    * inicializarHayFeromona
    * Estos métodos son los encargados de inicializar las matrices de probabilidades y de ver si hay feromonas respectivamente.
    */
    public void inicializarProbabilidades()
    {
        for(int i=0;i<this.probabilidades.length;i++)
        {
            for(int j=0;j<this.probabilidades.length;j++)
            {
                this.probabilidades[i][j] = 0.1;
            }
        }
    }
    
    public void inicializarHayFeromona()
    {
        for(int i=0;i<this.hayFeromona.length;i++)
        {
            for(int j=0;j<this.hayFeromona.length;j++)
            {
                if(i == j)
                {
                    this.hayFeromona[i][j] = 0;
                }
                else
                {
                    this.hayFeromona[i][j] = 1;
                }
            }
        }
    }
    
    /**
    * imprimirProbabilidades
    * imprimirHayFeromonas
    * Estos métodos se encargan de imprimir en consola la matriz de probabilidades y la matriz de las feromonas que es capaz de
    * ver una hormiga respectivamente.
    */
    public void imprimirProbabilidades()
    {
        System.out.println("\n====Probabilidades de una ciudad a otra====");
        for(int i=0;i<this.probabilidades.length;i++)
        {
            for(int j=0;j<this.probabilidades.length;j++)
            {
                System.out.print("["+this.probabilidades[i][j]+"]");
            }
            System.out.println("");
        }
    }
    
    public void imprimirHayFeromonas()
    {
        System.out.println("\n====Feromonas de la hormiga====");
        for(int i=0;i<this.hayFeromona.length;i++)
        {
            for(int j=0;j<this.hayFeromona.length;j++)
            {
                System.out.print("["+this.hayFeromona[i][j]+"]");
            }
            System.out.println("");
        }
    }

    public ArrayList<Ciudad> getCiudadesVisitadas() {
        return ciudadesVisitadas;
    }

    public void setCiudadesVisitadas(ArrayList<Ciudad> ciudadesVisitadas) {
        this.ciudadesVisitadas = ciudadesVisitadas;
    }

    public double getRecorridoTotal() {
        return recorridoTotal;
    }

    public void setRecorridoTotal(double recorridoTotal) {
        this.recorridoTotal = recorridoTotal;
    }

    public Ciudad getCiudadActual() {
        return ciudadActual;
    }

    public void setCiudadActual(Ciudad ciudadActual) {
        this.ciudadActual = ciudadActual;
    }

    public double[][] getProbabilidades() {
        return probabilidades;
    }

    public void setProbabilidades(double[][] probabilidades) {
        this.probabilidades = probabilidades;
    }
}
