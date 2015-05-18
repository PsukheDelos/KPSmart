package kps.distribution.pathFinder;

import java.security.InvalidParameterException;
import java.util.Comparator;

import kps.distribution.network.PathType;
import kps.distribution.network.Route;
import kps.distribution.network.TransportType;

public class PathCondition implements Comparator<PathFinderNode>{
	public final PathType pathType;
	public final Optimisation optimisation;

	public PathCondition(PathType pathType, Optimisation optimisation){
		this.pathType = pathType;
		this.optimisation = optimisation;
	}

	public boolean accepts(Route route) {
		switch(pathType){
		case AIR_ONLY:
			return route.getType() == TransportType.AIR;
		case AIR_SEA_LAND:
			return true;
		default:
			throw new InvalidParameterException("Unexpected path type");
		}
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
