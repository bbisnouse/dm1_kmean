package run1;

import run1.KmObj;

public interface KmObj extends Comparable<KmObj>{
	
	//double x=0,y=0;
	
	
	//public KmObj(double x, double y) { this.x = x; this.y = y; }

	public abstract double getDistance(KmObj other);
	//�������ľ���
	
	public abstract int compareTo(KmObj other);
	//������Ϊ�˺˶Ծ��������Ƿ��غ�
	
	public abstract KmObj getCenter(KmObj[] objs, int[] serials);
	// ͨ��serialsѡ����ţ������ǵ�����
}