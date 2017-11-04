package api;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Clase Vuelo: esta clase caracteriza un objeto Vuelo
 */
public class Vuelo {

	private String nombre_;
	private Aeropuerto aPartida_;
	private Aeropuerto aArribo_;
	private int hsPartida_;
	private char partidaAPm_;
	private int hsArribo_;
	private char arriboAPm_;
	private int duracion_;

	public Vuelo() {
		
	}

	public Vuelo(String nombre, Aeropuerto aPartida, Aeropuerto aArribo, int hsPartida,char partidaAPm, int hsArribo, char arriboAPm){
		nombre_ = nombre;
		aPartida_=aPartida;
		aArribo_=aArribo;
		hsPartida_=hsPartida;
		hsArribo_= hsArribo;
		partidaAPm_=partidaAPm;
		arriboAPm_= arriboAPm;
		
	}

	public String getNombre() {
		return nombre_;
	}

	public void setNombre(String nombre_) {
		this.nombre_ = nombre_;
	}

	public Aeropuerto getaPartida() {
		return aPartida_;
	}

	public void setaPartida(Aeropuerto aPartida_) {
		this.aPartida_ = aPartida_;
	}

	public Aeropuerto getaArribo() {
		return aArribo_;
	}

	public void setaArribo(Aeropuerto aArribo_) {
		this.aArribo_ = aArribo_;
	}

	public int getHsPartida() {
		return hsPartida_;
	}

	public void setHsPartida(int hsPartida_) {
		this.hsPartida_ = hsPartida_;
	}

	public int getHsArribo() {
		return hsArribo_;
	}

	public void setHsArribo(int hsArribo_) {
		this.hsArribo_ = hsArribo_;
	}

	public char getPartidaAPm() {
		return partidaAPm_;
	}

	public void setPartidaAPm(char partidaAPm_) {
		this.partidaAPm_ = partidaAPm_;
	}

	public char getArriboApm() {
		return arriboAPm_;
	}

	public void setArriboAPm(char arriboAPm_) {
		this.arriboAPm_ = arriboAPm_;
	}

    public int getDuracion() {
        return duracion_;
    }

    public void setDuracion(int duracion_) {
        this.duracion_ = duracion_;
    }

    /**
     * Este método ha sido modificado para mostrar información más clara.
     */
    public String toString() {
	    StringBuilder sb = new StringBuilder();

	    int hours = duracion_ / 60;
	    int minutes = duracion_% 60;

	    String tmp = String.format("%04d", hsPartida_);
	    String departure = tmp.substring(0, 2) + ":" + tmp.substring(2, 4);
	    tmp = String.format("%04d", hsArribo_);
	    String arrival = tmp.substring(0, 2) + ":" + tmp.substring(2, 4);

	    sb.append("Vuelo ");
	    sb.append(nombre_);
	    sb.append(" desde ");
	    sb.append(aPartida_.getCodigo());
	    sb.append(" hasta ");
	    sb.append(aArribo_.getCodigo());
	    sb.append(". Sale a las ");
	    sb.append(departure);
	    sb.append(partidaAPm_ == 'A' ? "AM" : "PM");
	    sb.append(" (GMT-");
	    sb.append(Math.abs(aPartida_.getAjusteHorario() / 100));
	    sb.append(")");
	    sb.append(" y llega a las ");
	    sb.append(arrival);
	    sb.append(arriboAPm_ == 'A' ? "AM" : "PM");
        sb.append(" (GMT-");
        sb.append(Math.abs(aArribo_.getAjusteHorario() / 100));
        sb.append(")");
	    sb.append(" (");

	    if (hours > 0) {
            sb.append(hours);
            sb.append(hours == 1 ? " hora" : " horas");

            if (minutes > 0)
                sb.append(" ");
        }

	    if (minutes > 0) {
            sb.append(minutes);
            sb.append(" minutos");
        }

        sb.append(")");
	    return sb.toString();
	}

}


