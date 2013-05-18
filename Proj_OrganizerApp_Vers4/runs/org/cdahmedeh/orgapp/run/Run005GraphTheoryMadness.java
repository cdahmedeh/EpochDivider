package org.cdahmedeh.orgapp.run;

import org.jgrapht.DirectedGraph;
import org.jgrapht.experimental.alg.color.GreedyColoring;
import org.jgrapht.experimental.alg.color.ImpvGreedyColoring;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Run005GraphTheoryMadness {
	public static void main(String[] args) {
        SimpleGraph<String, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
        
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";
        String f = "f";
        
		g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addVertex(e);
        g.addVertex(f);
        
        g.addEdge(a, b);
        g.addEdge(a, c);
        g.addEdge(a, d);
        g.addEdge(b, c);
        g.addEdge(c, d);
        g.addEdge(e, f);
        
        ImpvGreedyColoring<String, DefaultEdge> greedyColoring = new ImpvGreedyColoring<>(g);
        int[] colors = greedyColoring.colors();
        for (int co: colors){
        	System.out.println(co);
        }
        
	}
}

 

