package run1;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import run1.Monitor;
import run1.MyFrame;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*System.out.println("hello coolgo");
		KmNode nd1 = new KmNode(0,0);
		KmNode nd2 = new KmNode(3,4);
		System.out.println(nd1.getDistance(nd2));*/
		MyFrame f = new MyFrame("Test");
	}
	
	

}


class MyFrame extends JFrame
{
	ArrayList<Point> points=null;
	ArrayList<Point> center=null;
	final static int width = 1200;
	final static int height = 900;
	//ArrayList<KmNode> nds=null;
	MyFrame(String s)
	{
		super(s);
		points=new ArrayList<Point>();
		center=new ArrayList<Point>();
		//nds = new ArrayList<KmNode>();
		this.setLayout(new FlowLayout());
		this.setSize(width, height);
		this.setBackground(Color.blue);
		this.setVisible(true);
		//�ر�ʱ��ֹ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		JLabel label = new JLabel("���������յľ�����Ŀ:");
		panel.add(label);
		// ����һ���ı�����
        final JTextArea textArea = new JTextArea(1, 10);
        // �����Զ�����
        textArea.setLineWrap(true);
        textArea.setBackground(Color.white);
        textArea.setText("1");
        // ��ӵ��������
        JButton button = new JButton("����");  
        JButton button2 = new JButton("����");
        panel.add(textArea);
        panel.add(button);
        panel.add(button2);
        
        this.setContentPane(panel);
        this.getContentPane().setBackground(Color.lightGray);
        this.getContentPane().setVisible(true);
        
		this.addMouseListener(new Monitor());
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Iterator<Point> i=points.iterator();
				KmNode[] kmn = new KmNode[points.size()];
				int num=0;
				while(i.hasNext())
				{
					Point p=(Point)i.next();
					kmn[num] = new KmNode(p.x,p.y);	
					//if(i.hasNext()==false)
					System.out.println("x="+kmn[num].x+", y="+kmn[num].y);
					num++;
				}
				int[][] map = kmean(kmn, Integer.parseInt(textArea.getText()));
				//System.out.println("k = "+Integer.parseInt(textArea.getText()));
				//��ʾ������
				//button.setBounds(0, 0, 100, 50);
				//points.clear();
				center.clear();
				//addCenter(new Point(100,100));
				//nds.clear();
				repaint();
				for(int x=0;x<width;x++) {
					for(int y=0;y<height;y++) {
						if(map[x][y]==1) {
							addCenter(new Point(x,y));
						}
					}
				}
				/*if(textArea.getText().equals("")) {
					System.out.println("???");
				}*/
				//System.out.println("cin = "+textArea.getText());
			}
		});
		
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				points.clear();
				center.clear();
				repaint();
				/*if(textArea.getText().equals("")) {
					System.out.println("???");
				}*/
				//System.out.println("cin = "+textArea.getText());
			}
		});
		
	}
	
	public static int[][] kmean(KmNode[] objs, int k){
		//System.out.println("lllen = "+objs.length);
		Arrays.sort(objs);
		//System.out.println("xxx="+objs[0].x+",yyy="+objs[0].y+"!!!");
		//System.out.print("here111111");
		int ret[][] = new int[width][height];
		for(int i=0;i<ret.length;i++) {
			for(int j=0;j<ret[i].length;j++) {
				ret[i][j]=0;
			}
		}
		if(k==0)return ret;
		int len = objs.length;
		int cs0 = len/k;
		
		KmNode[] last = new KmNode[k];
		KmNode[] save = new KmNode[k];
		//��last����ֵ
		for(int i=0;i<k;i++) {
			last[i] = objs[i*cs0];
		}
		//����ÿ���㣬�ҳ��������������ѡ���������ģ������Ӧ�������ļ��ϣ���ȷ��k���µľ������ġ�����ȼ��ѡ����
		int a[][] = new int[k][];//һ������ĵ���������
		//��¼ÿһ�и���
		int num[] = new int[k];
		//��ʼ��
		for(int i=0;i<k;i++) {
			num[i] = 0;
		}
		//�������Σ���һ�����ÿ������ĳ�
		for(int i=0; i<len; i++) {
			KmNode c = objs[i];
			int cnt=0;double dst=100000;
			for(int j=0;j<k;j++) {
				if(last[j].getDistance(c) < dst) {
					cnt = j;
					dst = last[j].getDistance(c);
				}
			}
			num[cnt]++;
			//System.out.println("first cnt = "+cnt);
		}
		//�ڶ��γ�ʼ��
		for(int i=0;i<k;i++) {
			a[i] = new int[num[i]];
			num[i] = 0;
		}
		
		//System.out.println("k = "+k);
		for(int i=0; i<len; i++) {
			KmNode c = objs[i];
			int cnt=0;double dst=100000;
			for(int j=0;j<k;j++) {
				//System.out.println("c.x="+c.x+" , c.y="+c.y);
				//System.out.println("last[j].x="+last[j].x + ", y="+last[j].y);
				//System.out.println("distance = "+c.getDistance(c)+"here!!");
				if(last[j].getDistance(c) < dst) {
					cnt = j;
					dst = last[j].getDistance(c);
				}
				//System.out.println("distance = "+last[j].getDistance(c));
				//System.out.println("������ i="+i+" and j="+j + " and dst="+dst);
			}
			a[cnt][num[cnt]] = i;
			num[cnt]++;
			//System.out.println("first cnt = "+cnt);
		}
		//����ÿ���������ģ������µľ������ġ�
		for(int i=0;i<k;i++) {
			int serials[] = new int[num[i]];
			for(int j=0;j<num[i];j++) {
				int ss = a[i][j];
				serials[j] = ss;
			}
			save[i] = (KmNode) objs[i*cs0].getCenter(objs, serials);
			//System.out.println("@@@x = "+save[i].x+", y = "+save[i].y);
		}
		
		//��һ���ж�
		Arrays.sort(last);
		Arrays.sort(save);
		//Ĭ����ȷ
		int ok = 1;
		for(int i=0;i<k;i++) {
			if(last[i].x!=save[i].x || last[i].y!=save[i].y) {
				ok=0;
				break;
			}
		}
		//���������ķ����ı䣬���������㷨
		int jsq=0;//��������
		while(ok==0 && jsq<1000) {
			System.out.println("���������У���"+(jsq++)+"��");
			
			//����ÿ���㣬�ҳ��������������ѡ���������ģ������Ӧ�������ļ��ϣ���ȷ��k���µľ������ġ�
			//ArrayList<Integer> a[] = new ArrayList[k];
			//��һ����վ�������
			for(int i=0;i<k;i++) {
				num[i]=0;
			}
			for(int i=0; i<len; i++) {
				KmNode c = objs[i];
				int cnt=0;double dst=100000;
				for(int j=0;j<k;j++) {
					if(last[j].getDistance(c) < dst) {
						cnt = j;
						dst = last[j].getDistance(c);
					}
				}
				num[cnt]++;
				//System.out.println("cnt = "+cnt);
			}
			//�ڶ�����վ�������
			for(int i=0;i<k;i++) {
				a[i] = new int[num[i]];
				num[i]=0;
			}
			for(int i=0; i<len; i++) {
				KmNode c = objs[i];
				int cnt=0;double dst=100000;
				for(int j=0;j<k;j++) {
					if(last[j].getDistance(c) < dst) {
						cnt = j;
						dst = last[j].getDistance(c);
					}
				}
				a[cnt][num[cnt]] = i;
				num[cnt]++;
				//System.out.println("cnt = "+cnt);
			}
			//����ÿ���������ģ������µľ������ġ�
			for(int i=0;i<k;i++) {
				int serials[] = new int[num[i]];
				for(int j=0;j<num[i];j++) {
					int ss = a[i][j];
					serials[j] = ss;
				}
				save[i] = (KmNode) objs[i*cs0].getCenter(objs, serials);
				//System.out.println("x = "+save[i].x+", y = "+save[i].y);
			}
			
			//������
			Arrays.sort(last);
			Arrays.sort(save);
			//Ĭ����ȷ
			ok=1;
			for(int i=0;i<k;i++) {
				if(last[i].x!=save[i].x || last[i].y!=save[i].y) {
					ok=0;
					break;
				}
			}
			//�������ı����last
			for(int i=0;i<k;i++) {
				last[i] = save[i];
			}
		}
		System.out.println("��ӡ��������λ��");
		for(int i=0;i<k;i++) {
			System.out.println("��"+(i+1)+"����������Ϊ��x = "+save[i].x+", y = "+save[i].y);
			ret[(int)save[i].x][(int)save[i].y]=1;
		}
		//���ڶ�ά������
		
		
		//������ʹ�ú���
		return ret;
	}
	
	public void paint(Graphics g)//��дpaint()����
	{
		super.paint(g);
		Iterator<Point> i=points.iterator();
		int num=0;
		while(i.hasNext())
		{
			Point p=(Point)i.next();
			g.setColor(Color.darkGray);    //������ɫ
			g.fillOval(p.x,p.y,6,6); //��ʵ��Բ
			//if(i.hasNext()==false)
			//System.out.println("x="+p.x+", y="+p.y);
			num++;
		}
		
		i=center.iterator();
		while(i.hasNext())
		{
			Point p=(Point)i.next();
			g.setColor(Color.red);    //������ɫ
			g.fillOval(p.x,p.y,6,6); //��ʵ��Բ
			//if(i.hasNext()==false)
			//System.out.println("x="+p.x+", y="+p.y);
		}
		//System.out.println(num);
		
	}
	public void addPoint(Point p)
	{
		points.add(p);  //������ӵ�������
		//KmNode n = new KmNode(p.getX(),p.getY());
		//System.out.println("x="+n.x);
	}
	public void addCenter(Point p)
	{
		center.add(p);  //������ӵ�������
		//KmNode n = new KmNode(p.getX(),p.getY());
		//System.out.println("x="+n.x);
	}
}
 
class Monitor extends MouseAdapter
{
	public void mousePressed(MouseEvent e)
	{
		
		MyFrame f=(MyFrame)e.getSource();
		f.addPoint(new Point(e.getX(),e.getY()));
		f.repaint();
		
	}
}