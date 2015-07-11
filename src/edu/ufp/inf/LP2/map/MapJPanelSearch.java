/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.ufp.inf.lp2.map;

import edu.princeton.cs.introcs.In;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author AF
 */
public class MapJPanelSearch extends javax.swing.JPanel {

    /**
     * Creates new form MapJPanelSearch
     */
    public MapJPanelSearch() {
	initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        if(evt.getButton()==MouseEvent.BUTTON1){
	    double width = this.getWidth();
	    double height = this.getHeight();
	    double x=(double)evt.getX()*width;///this.map.getMaxX();
	    double y=(double)evt.getY()*height;///this.map.getMaxY();
	    double lat=this.map.maxLat-(this.map.maxLat-this.map.minLat)*y/this.map.getMaxY();
	    double lon=this.map.maxLon-(this.map.maxLon-this.map.minLon)*x/this.map.getMaxX();
	    JOptionPane.showMessageDialog(this, "Coordenadas do mapa \nlat min "+this.map.minLat+" lat max " + this.map.maxLat+"\nlon min "+this.map.minLon+" lon max "+this.map.maxLon+"\nCoord ponto \nlat "+lat+"\nlon "+lon);
	}
    }//GEN-LAST:event_formMouseClicked

protected void paintComponent(Graphics g) {
	super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
	int width = this.getWidth();
	int height = this.getHeight();
	double scale = width / this.map.getMaxX() < height / this.map.getMaxY() ? width / this.map.getMaxX() : height / this.map.getMaxY();
	Image offscreenImg = this.createImage(width, height);
	Graphics offscreenGraphics = offscreenImg.getGraphics();
	offscreenGraphics.setColor(this.getBackground());
	offscreenGraphics.fillRect(0, 0, width, height);
	offscreenGraphics.drawImage(img, 0, 0, null);
	this.map.drawNode(offscreenGraphics,scale); //desenha todos os nós do mapa

	for(MapEdge me:this.edges)
	{
	    me.draw(offscreenGraphics,scale); //desenha todas as arestas do mapa
	}
	for(POI p:this.path)
	{
	    p.draw(offscreenGraphics, scale,Color.RED);
	}
	g.drawImage(offscreenImg, 0, 0, null);
	this.repaint();
    }
    MapGraph map =null;
    //MapGraph map=new MapGraph(new In(MapGraph.osm_nodes_inputfile_name), new In(MapGraph.osm_highway_inputfile_name), new In(MapGraph.osm_hotels_inputfile_name), new In(MapGraph.osm_restaurant_inputfile_name));
    ArrayList<MapNode> mapnodes = new ArrayList<>();
    ArrayList<POI> pois = new ArrayList<>();
    ArrayList<POI> pois2 = new ArrayList<>();
    ArrayList<POI> path=new ArrayList<>();
    ArrayList<MapEdge> edges = new ArrayList<>();
    private Image img;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
