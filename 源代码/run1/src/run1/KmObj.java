package run1;

import run1.KmObj;

public interface KmObj extends Comparable<KmObj>{
	
	//double x=0,y=0;
	
	
	//public KmObj(double x, double y) { this.x = x; this.y = y; }

	public abstract double getDistance(KmObj other);
	//求两点间的距离
	
	public abstract int compareTo(KmObj other);
	//点排序，为了核对聚类中心是否重合
	
	public abstract KmObj getCenter(KmObj[] objs, int[] serials);
	// 通过serials选定标号，求它们的中心
}