package api;

import java.util.*;
import java.io.*;

/**
 * Clase ApiVuelos: esta clase, a través de sus métodos, permite cargar dos archivos de texto plano
 * conteniendo la representacion de Aeropuertos y Vuelos; y transformar el contenido de los mismos
 * en listas de objetos de las clases Aeropuerto y Vuelo respectivamente.
 */
public class ApiVuelos {
	
	private Aeropuerto aeropuertos [];
	private Vuelo vuelos [];
	private HashMap<String, Aeropuerto> mapAeropuerto;
	private File archivoAeropuertos = null;
	private File archivoVuelos = null;
	
	/**
	  * cargarAeropuertos(nombreArchivo): convierte el contenido de un archivo de texto,
	  *  con un formato particular, a una lista de Aeropuertos.
	  *
	  * @param nombreArchivoAeropuertos es un archivo existente (no un directorio).	  
	  *
	  * @exception FileNotFoundException si nombreArchivoAeropuertos no existe.
	  * @exception IOException si falla la conexión con nombreArchivoAeropuertos.
	  *
	  * @exception IllegalArgumentException si nombreArchivoAeropuertos es un directorio. 
	  * @exception SecurityException si nombreArchivoAeropuertos no puede ser leido.
	  */
	public void cargarAeropuertos(String nombreArchivoAeropuertos) throws FileNotFoundException, IOException {
		archivoAeropuertos = new File(nombreArchivoAeropuertos);
		
		if (archivoAeropuertos == null)
			throw new NullPointerException("Error: El archivo "+nombreArchivoAeropuertos+" no puede ser vacío.");
		
              
        if (!archivoAeropuertos.exists())
			throw new IOException("Error: El archivo "+nombreArchivoAeropuertos+" no pudo hallarse.");

        if (!archivoAeropuertos.canRead())
        	throw new IOException("Error: El archivo "+nombreArchivoAeropuertos+" no tiene permisos de lectura.");
        
        try{
        	mapAeropuerto = new HashMap<String,Aeropuerto>();
        	ArrayList<Aeropuerto> listAp = new ArrayList<Aeropuerto>();
        	BufferedReader br = new BufferedReader(new FileReader(archivoAeropuertos));
        	StreamTokenizer st = new StreamTokenizer(br);

        	while(st.nextToken() != StreamTokenizer.TT_EOF){   
        		Aeropuerto ap = new Aeropuerto();
        		if(st.ttype == StreamTokenizer.TT_WORD) {        			
        	        ap.setCodigo(st.sval);
        	        st.nextToken();
        	        if(st.ttype == StreamTokenizer.TT_NUMBER) {            	        
            	        if (!ap.getCodigo().equals("")){              	        	           	        	
                			ap.setAjusteHorario((int)st.nval);
                			mapAeropuerto.put(ap.getCodigo(), ap);
                			listAp.add(ap);
            	        }	
            	    }
        		}    
        	}

        	aeropuertos = new Aeropuerto [listAp.size()];
        	aeropuertos = listAp.toArray(aeropuertos);
        	br.close();
		} catch (java.io.IOException e) {
			throw new IOException("Error: de entrada/salida");	
		}		
	}
	
	
	public Aeropuerto [] getAeropuertos(){
		return aeropuertos;
	}
	
	public String verAeropuertos(){
		return Arrays.toString(aeropuertos);
	}
	
	
	/**
	  * cargarVuelos(nombreArchivo): convierte el contenido de un archivo de texto,
	  *  con un formato particular, a una lista de Vuelos.
	  *
	  * @param nombreArchivoVuelos es un archivo existente (no un directorio).	  
	  *
	  * @exception FileNotFoundException si nombreArchivoVuelos no existe.
	  * @exception IOException si falla la conexión con nombreArchivoVuelos.
	  *
	  * @exception IllegalArgumentException si nombreArchivoVuelos es un directorio. 
	  * @exception SecurityException si nombreArchivoVuelos no puede ser leido.
	  */
	public void cargarVuelos(String nombreArchivoVuelos) throws FileNotFoundException, IOException {
		archivoVuelos = new File(nombreArchivoVuelos);

		if (!archivoVuelos.exists())
			throw new IOException("Error: El archivo "+nombreArchivoVuelos+" no pudo hallarse.");
		if (archivoVuelos == null)
			throw new NullPointerException("Error: El archivo "+nombreArchivoVuelos+" no puede ser vacío.");
        if (!archivoVuelos.canRead())
        	throw new IOException("Error: El archivo "+nombreArchivoVuelos+" no tiene permisos de lectura.");        	
        try {
        	ArrayList<Vuelo> listV = new ArrayList<Vuelo>();
        	BufferedReader br = new BufferedReader(new FileReader(archivoVuelos));
        	StreamTokenizer st = new StreamTokenizer(br);

        	while (st.nextToken() != StreamTokenizer.TT_EOF) {
        		Vuelo v = new Vuelo(); 
        		if (trataLineaVuelo(st, v)) {
        			listV.add(v);
        		}
        	}

        	vuelos = new Vuelo [listV.size()];
        	vuelos = listV.toArray(vuelos);
        	br.close();
        	
        } catch (java.io.IOException e) {
        	throw new IOException("Error: de entrada/salida");	      
		}	
	}
	
	public Vuelo [] getVuelos(){
		return vuelos;
	}
	
	public String verVuelos(){
		return Arrays.toString(vuelos);
	}
	
	
	/** 
	 * trataLineaVuelo(st, v): parsea el StreamTokenizer
	 * asignando los valores al objeto v. 
	 * Retorna falso si no parsea un vuelo.
	 * En caso que se produzca un error en el stream,
	 * Retorna una excepción de Entrada/salida.
	 * 
	 */
	private boolean trataLineaVuelo(StreamTokenizer st, Vuelo v) throws IOException {
		String codV="";
		int ti,tf; 
		String ampm;
		int col = 0;
		boolean salirAnormal = false;

		try {
			while ((col< 6) && !salirAnormal) {
				
				switch (col){
				case 0: {
					if (st.ttype == StreamTokenizer.TT_WORD) {
						codV = (st.sval);
						col = 1;
					} else salirAnormal = true;

				}
				case 1:{
					st.nextToken();
					if (st.ttype == StreamTokenizer.TT_NUMBER) {
						codV += "-"+String.valueOf(st.nval);
						v.setNombre(codV);
						col=2;
					} else salirAnormal = true;
				}
				case 2:{
					st.nextToken();
					if (st.ttype == StreamTokenizer.TT_WORD) {
						v.setaPartida(mapAeropuerto.get(st.sval));
						col=3;		
					} else salirAnormal = true;
				}
				case 3:{
					st.nextToken();
					if (st.ttype == StreamTokenizer.TT_NUMBER) {
						ti = (int)st.nval;
						st.nextToken();
						if(st.ttype == StreamTokenizer.TT_WORD) {
							ampm= st.sval;
							v.setHsPartida(ti);
							v.setPartidaAPm(ampm.charAt(0));
						}
						col=4;
					} else salirAnormal = true;
					
				}
				case 4:{
					st.nextToken();
					if (st.ttype == StreamTokenizer.TT_WORD) {
						v.setaArribo(mapAeropuerto.get(st.sval));
						col=5;		
					} else salirAnormal = true;
				}
				case 5:{
					st.nextToken();
					if (st.ttype == StreamTokenizer.TT_NUMBER) {
						tf = (int)st.nval;
						st.nextToken();
						if(st.ttype == StreamTokenizer.TT_WORD) {
							ampm= st.sval;
							v.setHsArribo(tf);
							v.setArriboAPm(ampm.charAt(0));
						}
						col=6;
					} else salirAnormal = true;
				}
				}
			}
		} catch (java.io.IOException e) {
			throw new IOException("Error: de entrada/salida");		      
		}

		return !salirAnormal;
	}	
	
}
