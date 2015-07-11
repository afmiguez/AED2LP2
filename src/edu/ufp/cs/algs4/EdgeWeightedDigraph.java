package edu.ufp.cs.algs4;

//import edu.ufp.cs.introcs.*;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;
import edu.ufp.inf.lp2.map.MapEdge;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


/** 
 *    The <tt>EdgeWeightedDigraph</tt> class represents a edge-weighted
 *    digraph of vertices named 0 through <em>V</em> - 1, where each
 *    directed edge is of type {@link DirectedEdge} and has a real-valued weight.
 *    It supports the following two primary operations: add a directed edge
 *    to the digraph and iterate over all of edges incident from a given vertex.
 *    It also provides
 *    methods for returning the number of vertices <em>V</em> and the number
 *    of edges <em>E</em>. Parallel edges and self-loops are permitted.
 *    <p>
 *    This implementation uses an adjacency-lists representation, which 
 *    is a vertex-indexed array of @link{Bag} objects.
 *    All operations take constant time (in the worst case) except
 *    iterating over the edges incident from a given vertex, which takes
 *    time proportional to the number of such edges.
 *    <p>
 *    For additional documentation,
 *    see <a href="http://algs4.cs.princeton.edu/44sp">Section 4.4</a> of
 *    <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class EdgeWeightedDigraph implements Serializable {
  /* {author=Kevin Wayne
 }*/


  protected int V;

  protected int E;


  public ArrayList<MapEdge>[] adj;

  /** 
   *   Initializes an empty edge-weighted digraph with <tt>V</tt> vertices and 0 edges.
   *   param V the number of vertices
   *  @throws java.lang.IllegalArgumentException if <tt>V</tt> < 0
   */
  
  public EdgeWeightedDigraph(int V) {
  /* {throws=java.lang.IllegalArgumentException if <tt>V</tt> < 0
 }*/

        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (ArrayList<MapEdge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++)
            adj[v] = new ArrayList<MapEdge>();
  }

  /** 
   *   Initializes a random edge-weighted digraph with <tt>V</tt> vertices and <em>E</em> edges.
   *   param V the number of vertices
   *   param E the number of edges
   *  @throws java.lang.IllegalArgumentException if <tt>V</tt> < 0
   *  @throws java.lang.IllegalArgumentException if <tt>E</tt> < 0
   */
  public EdgeWeightedDigraph(int V, int E) {
  /* {throws=java.lang.IllegalArgumentException if <tt>E</tt> < 0
 }*/

        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges in a Digraph must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            double weight = Math.round(100 * Math.random()) / 100.0;
            MapEdge e = new MapEdge(v, w, weight);
            addEdge(e);
        }
  }

  /** 
   *    
   *   Initializes an edge-weighted digraph from an input stream.
   *   The format is the number of vertices <em>V</em>,
   *   followed by the number of edges <em>E</em>,
   *   followed by <em>E</em> pairs of vertices and edge weights,
   *   with each entry separated by whitespace.
   *  @param in the input stream
   *  @throws java.lang.IndexOutOfBoundsException if the endpoints of any edge are not in prescribed range
   *  @throws java.lang.IllegalArgumentException if the number of vertices or edges is negative
   */
  public EdgeWeightedDigraph(In in) {
  /* {param=in the input stream
, throws=java.lang.IllegalArgumentException if the number of vertices or edges is negative
 }*/

        this(in.readInt());
        int E = in.readInt();
        if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
            if (w < 0 || w >= V) throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (V-1));
            double weight = in.readDouble();
            addEdge(new MapEdge(v, w, weight));
        }
  }

  /** 
   *   Initializes a new edge-weighted digraph that is a deep copy of <tt>G</tt>.
   *  @param G the edge-weighted graph to copy
   */
  public EdgeWeightedDigraph(EdgeWeightedDigraph G) {
  /* {param=G the edge-weighted graph to copy
 }*/

        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<MapEdge> reverse = new Stack<MapEdge>();
            for (MapEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (MapEdge e : reverse) {
                adj[v].add(e);
            }
        }
  }

  public EdgeWeightedDigraph(EdgeWeightedDigraph G, int n) {
        this(G.V()+n);
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<MapEdge> reverse = new Stack<MapEdge>();
            for (MapEdge e : G.adj[v]) {
                reverse.push(e);
            }
            for (MapEdge e : reverse) {
                adj[v].add(e);
            }
        }
  }
  public EdgeWeightedDigraph(){
      this.V=0;
  }

  /** 
   *   Returns the number of vertices in the edge-weighted digraph.
   *  @return the number of vertices in the edge-weighted digraph
   */
  public int V() {
  /* {return=the number of vertices in the edge-weighted digraph
 }*/

        return V;
  }

  /** 
   *   Returns the number of edges in the edge-weighted digraph.
   *  @return the number of edges in the edge-weighted digraph
   */
  public int E() {
  /* {return=the number of edges in the edge-weighted digraph
 }*/

        return E;
  }

  /** 
   *   Adds the directed edge <tt>e</tt> to the edge-weighted digraph.
   *  @param e the edge
   */
  public void addEdge(MapEdge e) {
  /* {param=e the edge
 }*/

        int v = e.from();
        adj[v].add(e);
        E++;
  }

  /** 
   *   Returns the directed edges incident from vertex <tt>v</tt>.
   *  @return the directed edges incident from vertex <tt>v</tt> as an Iterable
   *  @param v the vertex
   *  @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
   */
  public Iterable adj(int v) {
  /* {return=the directed edges incident from vertex <tt>v</tt> as an Iterable
, param=v the vertex
, throws=java.lang.IndexOutOfBoundsException unless 0 <= v < V
 }*/

        if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
        return adj[v];
  }

  /** 
   *   Returns all directed edges in the edge-weighted digraph.
   *   To iterate over the edges in the edge-weighted graph, use foreach notation:
   *   <tt>for (DirectedEdge e : G.edges())</tt>.
   *  @return all edges in the edge-weighted graph as an Iterable.
   */
  public Iterable edges() {
  /* {return=all edges in the edge-weighted graph as an Iterable.
 }*/

        ArrayList<DirectedEdge> list = new ArrayList<DirectedEdge>();
        for (int v = 0; v < V; v++) {
	    for (Iterator it = adj(v).iterator(); it.hasNext();) {
		DirectedEdge e = (DirectedEdge) it.next();
		list.add(e);
	    }
        }
        return list;
  }

  /** 
   *   Returns the number of directed edges incident from vertex <tt>v</tt>.
   *   This is known as the <em>outdegree</em> of vertex <tt>v</tt>.
   *  @return the number of directed edges incident from vertex <tt>v</tt>
   *  @param v the vertex
   *  @throws java.lang.IndexOutOfBoundsException unless 0 <= v < V
   */
  public int outdegree(int v) {
  /* {return=the number of directed edges incident from vertex <tt>v</tt>
, param=v the vertex
, throws=java.lang.IndexOutOfBoundsException unless 0 <= v < V
 }*/

        if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
        return adj[v].size();
  }

  /** 
   *   Returns a string representation of the edge-weighted digraph.
   *   This method takes time proportional to <em>E</em> + <em>V</em>.
   *  @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
   *     followed by the <em>V</em> adjacency lists of edges
   */
  public String toString() {
  /* {return=the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
    followed by the <em>V</em> adjacency lists of edges
 }*/

        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (DirectedEdge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
  }

  /** 
   *   Unit tests the <tt>EdgeWeightedDigraph</tt> data type.
   */

}