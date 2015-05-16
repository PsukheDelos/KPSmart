package kps.distribution.pathFinder;

import java.util.Comparator;

public class PathCondition implements Comparator<PathFinderNode>{
	public static final float NO_TIME_LIMIT = Float.POSITIVE_INFINITY;
	public static final float NO_COST_LIMIT = Float.POSITIVE_INFINITY;

	public final float maxCost;
	public final float maxTime;
	public final Optimisation optimisation;

	public PathCondition(float maxCost, float maxTime, Optimisation optimisation){
		this.maxCost = maxCost;
		this.maxTime = maxTime;
		this.optimisation = optimisation;
	}

	public boolean accepts(float cost, float time){
		return cost <= maxCost && time <= maxTime;
	}

	public int compare(PathFinderNode o1, PathFinderNode o2) {
		if (o1 == null) return -1;
		if (o2 == null) return  1;
		switch (optimisation){
			case LOWEST_COST:
				if (o1.getCost() < o2.getCost()) return -1;
				if (o1.getCost() > o2.getCost()) return 1;
				return 0;
			case SHORTEST_TIME:
				if (o1.getTime() < o2.getTime()) return -1;
				if (o1.getTime() > o2.getTime()) return 1;
				return 0;
			default:
				return 0;
		}
	}
}
