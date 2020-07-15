package run1;

public class KmPic implements KmObj{
	
	double x,y;
	//double r,g,b;
	double color;
	
	public KmPic(double x, double y) {
		this.x = x;
		this.y = y;
		//this.r = 0; this.g = 0; this.b = 0;
		this.color = 0;
	}
	
	public KmPic(double x, double y, double color) {
		this.x = x;
		this.y = y;
		//this.r = r; this.g = g; this.b = b;
		this.color = color;
	}

	@Override
	public double getDistance(KmObj other) {
		
		int c = (int)color;
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = c & 0xff;
		//other
		KmPic kmn = (KmPic)other;
		int oc = (int)kmn.color;
		int or = (oc >> 16) & 0xff;
		int og = (oc >> 8) & 0xff;
		int ob = oc & 0xff;
		return Math.sqrt(//double
				(r-or)*(r-or)*1.0 +
				(g-og)*(g-og) +
				(b-ob)*(g-ob)
				);
		// TODO Auto-generated method stub
	}

	@Override
	public int compareTo(KmObj other) {
		int c = (int)color;
		int r = (c >> 16) & 0xff;
		int g = (c >> 8) & 0xff;
		int b = c & 0xff;
		//other
		KmPic kmn = (KmPic)other;
		int oc = (int)kmn.color;
		int or = (oc >> 16) & 0xff;
		int og = (oc >> 8) & 0xff;
		int ob = oc & 0xff;
		if(r==or && g==og && b==ob) return 0;
		
		if(r>or) return 1;
		else if(r<or) return -1;
		
		
		if(g>og) return 1;
		else if(g<og) return -1;
		
		if(b>ob) return 1;
		else return -1;
		// TODO Auto-generated method stub
	}

	@Override
	public KmObj getCenter(KmObj[] objs, int[] serials) {
		int n = serials.length;
		double sx=0, sy=0, sr = 0, sg = 0, sb = 0;
		for(int i=0;i<n;i++) {
			KmPic kmn = (KmPic)objs[serials[i]];
			int c = (int)kmn.color;
			int r = (c >> 16) & 0xff;
			int g = (c >> 8) & 0xff;
			int b = c & 0xff;
			sx += kmn.x;
			sy += kmn.y;
			sr += r;
			sg += g;
			sb += b;
			//System.out.println("KAKA:x = "+kmn.x+", y = "+kmn.y);
		}
		//System.out.println("KAKA:sx="+sx+", sy = "+sy+"n="+n);
		sx /= n; sy /= n;
		sr /= n; sg /= n; sb /=n;
		int r=(int)sr, g = (int)sg, b = (int)sb;
		int ans = ((0xFF << 24)|(r << 16)|(g << 8)|b);
		KmPic ret = new KmPic(sx,sy,ans);
		return (KmObj)ret;
	}

}
