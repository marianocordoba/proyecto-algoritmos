package api;

/**
 * Clase Aeropuerto: esta clase caracteriza un objeto Aeropuerto
 */
public class Aeropuerto {

	private String codigo;		/* Código de 3 letras*/
	private int ajusteHorario; /* Diferencia huso horario en base a GTM.*/
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getAjusteHorario() {
		return ajusteHorario;
	}

	public void setAjusteHorario(int ajusteHorario) {
		this.ajusteHorario = ajusteHorario;
	}
	
	public String toString(){
		return (codigo + " " + ajusteHorario );
	}
	
}
