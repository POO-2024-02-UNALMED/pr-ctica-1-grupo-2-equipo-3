/* Autores: Samuel Botero Rivera, Santiago Sanchez Ruiz, Samuel Gutierrez Betancur, Samuel Garcia Rojas */

package gestorAplicacion.personas;

import java.io.Serializable;

//Clase destinada para que hereden doctor y persona
public class Persona implements Serializable {
    //Atributos
	private int cedula;
    private String nombre;
    private String tipoEps;

    //Constructor
    public Persona(int cedula, String nombre, String tipoEps) {
    	this.cedula = cedula;
    	this.nombre = nombre;
    	this.tipoEps = tipoEps;
    }

    //Getters y Setters
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTipoEps() {
        return tipoEps;
    }

    public void setTipoEps(String tipoEps) {
        this.tipoEps = tipoEps;
    }

    public String bienvenida(){
        return "Bienvenido Usuario "+nombre;
    }
}
