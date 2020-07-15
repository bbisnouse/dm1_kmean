package run1;

import run1.KmNode;
import run1.KmObj;

public class KmNode implements KmObj{
	double x,y;
	
	public KmNode(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public double getDistance(KmObj other) {
		KmNode kmn = (KmNode)other;
		return Math.sqrt(
				(x-kmn.x)*(x-kmn.x) +
				(y-kmn.y)*(y-kmn.y)
				);
	} 
	//�������ľ���
	@Override
	public int compareTo(KmObj other) {
		KmNode kmn = (KmNode)other;
		if(x==kmn.x) {
			if(y == kmn.y) return 0;
			else if(y > kmn.y)return 1;
			else return -1;
		}
		else {
			if(x > kmn.x)return 1;
			else return -1;
			//return (x - other.x > 0) ? 1 : -1;
		}
	} 
	//������Ϊ�˺˶Ծ��������Ƿ��غ�
	
	@Override
	public KmObj getCenter(KmObj[] objs, int[] serials) {
		int n = serials.length;
		double sx = 0, sy = 0;
		for(int i=0;i<n;i++) {
			KmNode kmn = (KmNode)objs[serials[i]];
			sx += kmn.x;
			sy += kmn.y;
			//System.out.println("KAKA:x = "+kmn.x+", y = "+kmn.y);
		}
		//System.out.println("KAKA:sx="+sx+", sy = "+sy+"n="+n);
		sx /= n; sy /= n;
		KmNode ret = new KmNode(sx,sy);
		return (KmObj)ret;
	} 
	// ͨ��serialsѡ����ţ������ǵ�����
}
