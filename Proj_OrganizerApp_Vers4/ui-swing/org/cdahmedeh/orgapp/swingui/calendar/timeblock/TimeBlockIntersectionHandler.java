package org.cdahmedeh.orgapp.swingui.calendar.timeblock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.cdahmedeh.orgapp.swingui.calendar.BRectangle;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.experimental.alg.color.ImpvGreedyColoring;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class TimeBlockIntersectionHandler {
	public static void processIntersections(ArrayList<BRectangle> renderedTimeBlocks) {
		SimpleGraph<BRectangle, DefaultEdge> g = new SimpleGraph<>(DefaultEdge.class);
		
		for (BRectangle rtbFirst: renderedTimeBlocks) {
			g.addVertex(rtbFirst);
		}
		
		for (BRectangle rtbFirst: renderedTimeBlocks) {
			for (BRectangle rtbSecond: renderedTimeBlocks) {
				if (rtbFirst.intersects(rtbSecond.x+1, rtbSecond.y+1, rtbSecond.width-2, rtbSecond.height-2)) {
					if (rtbFirst != rtbSecond) {
						g.addEdge(rtbFirst, rtbSecond);
					}
				}
			}	
		}
		
		ConnectivityInspector<BRectangle, DefaultEdge> ci = new ConnectivityInspector<>(g);
		List<Set<BRectangle>> connectedSets = ci.connectedSets();

		ImpvGreedyColoring<BRectangle, DefaultEdge> gc = new ImpvGreedyColoring<>(g);
		int[] colors = gc.colors();
		
		for (Set<BRectangle> set: connectedSets) {
			if (set.size() <= 1) continue;
			
			int color = 0;
			for (BRectangle rtb: set) {
				int i = renderedTimeBlocks.indexOf(rtb);
				color = Math.max(color, colors[i]);
			}
				
			for (BRectangle rtb: set) {
				int i = renderedTimeBlocks.indexOf(rtb);
				rtb.width = rtb.width/color;
				rtb.x = rtb.x + rtb.width*(colors[i]-1);
			}
		}
	}
}
