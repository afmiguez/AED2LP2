/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ufp.inf.aed2.project.inverted_index;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.introcs.In;
import java.util.ArrayList;

/**
 *
 * @author jtorres
 */
public class ClenteRestaurantesST {

    static In osm_restaurante_infile = new In("data\\meusPOIRestaurante.txt"); // exemplo de tipo de POI
    static RedBlackBST<String, Queue<OSMNodeElementPOI>> restaurantesST_Name = new <String, Queue<OSMNodeElementPOI>> RedBlackBST();
    static RedBlackBST<Double, Queue<OSMNodeElementPOI>> restaurantesST_Lat = new <Double, Queue<OSMNodeElementPOI>> RedBlackBST();
    static RedBlackBST<Double, Queue<OSMNodeElementPOI>> restaurantesST_Lon = new <Double, Queue<OSMNodeElementPOI>> RedBlackBST();

    public static void main(String argv[]) {
        while (osm_restaurante_infile.hasNextLine()) {
            String line = osm_restaurante_infile.readLine(); 
            //System.out.println(line);
            String[] restaurante = line.split(",");

            OSMNodeElementPOI restaurantValue = new OSMNodeElementPOI(
                    restaurante[0], //id
                    Double.parseDouble(restaurante[1]), //lat (key for ST restaurantesST_Lat)
                    Double.parseDouble(restaurante[2])); // lon            
            
	    for (int i = 3; i < restaurante.length; i++) {
                int len = restaurante[i].length();
                int keyval_separator = restaurante[i].indexOf("=");
                String key = restaurante[i].substring(1, keyval_separator);
                String val = restaurante[i].substring(keyval_separator + 1, len - 1);
                restaurantValue.putKeyVal(key, val);
            }

            String restaurantName = (String) restaurantValue.getVal("name");
            System.out.println("Nome do restaurante: " + restaurantName);
	    if(restaurantName!=null)
	    {
            if (!restaurantesST_Name.contains(restaurantName)) {
                restaurantesST_Name.put(restaurantName, new Queue<OSMNodeElementPOI>());
            }
            if (!restaurantesST_Lat.contains(restaurantValue.getLat())) { //se não houver valor na Latitude, cria a fila
                restaurantesST_Lat.put(restaurantValue.getLat(), new Queue<OSMNodeElementPOI>());
            }
	    if(!restaurantesST_Lon.contains(restaurantValue.getLon())){
		restaurantesST_Lon.put(restaurantValue.getLon(), new Queue<OSMNodeElementPOI>());
	    }
            restaurantesST_Name.get(restaurantName).enqueue(restaurantValue);
            restaurantesST_Lat.get(restaurantValue.getLat()).enqueue(restaurantValue); //insere na fila seja o primeiro valor ou outros
	    restaurantesST_Lon.get(restaurantValue.getLon()).enqueue(restaurantValue);
	    }
        }

        /**
         * Exemplo de query para imprimir restaurantes localizados entre as
         * latitudes: lat_min e lat_max *
         */
        double lat_min = 41.1452000, lat_max = 41.1511000,lon_min=-8.63,lon_max=-8.61;
        ArrayList<OSMNodeElementPOI> poi=new ArrayList<>();
	System.out.println("\n\nRestaurantes com lat entre " + lat_min + " e " + lat_max + " e lon entre "+lon_min+" e " + lon_max+ ":");
        for (Double rl : restaurantesST_Lat.keys(lat_min, lat_max)) { //itera todas as chaves entre lat_min e lat_max
            for (OSMNodeElementPOI r : restaurantesST_Lat.get(rl)) { //itera todos os POIS que contenham latitude entre os limites acima
                poi.add(r); // lista de POIS dentro da latitude
            }
        }
	for(Double r1:restaurantesST_Lon.keys(lon_min, lon_max))
	for(OSMNodeElementPOI p:restaurantesST_Lon.get(r1)){
	    if(poi.contains(p))
	    System.out.println(p);
	}
    }
    
}
