/**
* Ciudad
* La clase Ciudad es la encargada de representar cada ciudad en el problema TSP en donde cada una contiene un identificador y
* un atributo que visualiza si la ciudad ha sido visitada o no.
* 
* @author Maldonado, Carlos.
*/
import java.util.ArrayList;

public class Ciudad
{
    private int identificador;
    private boolean visitada = false;
    
    /**
    * @constructor Ciudad
    * 
    * @param identificador es un número que identifica a cada una de las ciudades.
    */
    public Ciudad(int identificador)
    {
        this.identificador = identificador;
    }
    
    public Ciudad()
    {
        
    }

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public boolean isVisitada() {
        return visitada;
    }

    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }
}
