package run1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImageFrame f = new ImageFrame("Test");
	}

}
class ImageFrame extends JFrame
{
	final static int width = 1200;
	final static int height = 900;
	String path1, path2;
	static BufferedImage img1;//img1һֱ��ԭͼƬ,img2��ʼ��ΪԭͼƬ
	static BufferedImage img2;
    ImageFrame(String s)
	{
		super(s);
		this.setLayout(new FlowLayout());
		this.setSize(width, height);
		this.setBackground(Color.blue);
		this.setVisible(true);
		//�ر�ʱ��ֹ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Ŀ¼
		JMenu menu, submenu;
	    JMenuItem i1, i2, i3, i4;
		JMenuBar mb = new JMenuBar();
		menu = new JMenu("�ļ�");
        i1 = new JMenuItem("ѡ��ͼƬ");//ѡ��ͼƬ��ʾ�ڴ��ڣ�����·��������path1
        //ͼƬ��ΪBufferedImage img1��img2��ʼ������img1
        i2 = new JMenuItem("���ػ�");
        //1. �������ת��KmPic���飬kmean�㷨�����ת����ΪBufferImage img2
        //2. ��ͼƬ��ʾ�ڴ���
        i3 = new JMenuItem("��ԭ");//��ԭͼƬ
        i4 = new JMenuItem("����");//��·��������path2����ͼƬimg2������path2
        menu.add(i1);
        menu.addSeparator();
        menu.add(i2);
        menu.addSeparator();
        menu.add(i3);
        menu.addSeparator();
        menu.add(i4);
        //���Ŀ¼
        mb.add(menu);
        this.setJMenuBar(mb);
        this.setLocationRelativeTo(null);
        JLabel label=new JLabel();
        //����Ŀ¼�¼�
        i1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				File file = null;
				String path = null;
				JFileChooser fileChooser = new JFileChooser();
				FileSystemView fsv = FileSystemView.getFileSystemView();  //ע���ˣ�������Ҫ��һ��
				System.out.println(fsv.getHomeDirectory());                //�õ�����·��
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("��ѡ��Ҫ�򿪵�ͼƬ...");
				fileChooser.setApproveButtonText("ȷ��");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				result = fileChooser.showOpenDialog(null);
				if (JFileChooser.APPROVE_OPTION == result) {
					path=fileChooser.getSelectedFile().getPath();
					System.out.println("path: "+path);
					path1 = path;
					repaint();
					Image img = Toolkit.getDefaultToolkit().getImage(path);
			        label.setSize(1200,900);
			        label.setIcon( new ImageIcon(img));
			        add(label);
			        file = new File(path);
			        try {
						img1=(BufferedImage)ImageIO.read(file);
						img2 = img1;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
        i2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//1. �������ת��KmPic���飬kmean�㷨�����ת����ΪBufferImage img2
				if(img1==null)return;
				int width = img1.getWidth();
				int height = img1.getHeight();
				int[][] ans = new int[width][height];//kmean����ֵ
				JFrame f = new JFrame();
		        String k = JOptionPane.showInputDialog(f,"����������ֵ");
				System.out.println("��������Ϊ"+height*width+"����");
				//��������10��500��֮��
				if(height*width > 5000000) {
					System.out.println("��Ƭ̫�󣬴���ʧ�ܣ�");return;
				}
				KmPic pic[] = new KmPic[height*width];
				int cnt=0;//pic������
				
				for(int i=0;i<width;i++) {
					for(int j=0;j<height;j++) {
						int color = img1.getRGB(i, j);
						pic[cnt++] = new KmPic(i,j,color);
					}
				}
				
				System.out.println("w = "+width+", h="+height);
				System.out.println("k = "+k);
				ans = kmean(pic,Integer.parseInt(k));
				
				//2. ��ͼƬ��ʾ�ڴ���
				img2 = new BufferedImage(width, height, img1.getType());
				for(int i=0;i<width;i++){
					for(int j=0;j<height;j++) {
						img2.setRGB(i, j,ans[i][j]);
					}
				}
				repaint();
				label.setSize(1200,900);
				label.setIcon(new ImageIcon(img2));
				add(label);
				 
			}
		});
        i3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				label.removeAll();
				repaint();
				label.setIcon(new ImageIcon(img1));
				add(label);
			}
		});
        i4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				File file = null;
				String path = null;
				JFileChooser fileChooser = new JFileChooser();
				FileSystemView fsv = FileSystemView.getFileSystemView();  //ע���ˣ�������Ҫ��һ��
				System.out.println(fsv.getHomeDirectory());                //�õ�����·��
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("��ѡ��Ҫ�����λ��...");
				fileChooser.setApproveButtonText("ȷ��");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				result = fileChooser.showOpenDialog(null);
				if (JFileChooser.APPROVE_OPTION == result) {
					path=fileChooser.getSelectedFile().getPath();
					System.out.println("path: "+path);
					path2 = path;
					try {
						File outputfile1 = new File(path);
				        ImageIO.write(img2, "jpg", outputfile1);
				        System.out.println("�ɹ���");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
        //���Ĭ��ͼƬ
        //Image image = Toolkit.getDefaultToolkit().getImage("E:\\save\\trump.jpg");
        //JLabel label=new JLabel();
        //label.removeAll();
        //label.setSize(1200,900);
        //label.setIcon( new ImageIcon(image));
        //this.add(label);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
		
	}
    //rgbתint
    public int converRgbToArgb(int r,int g,int b){
    	int color = ((0xFF << 24)|(r << 16)|(g << 8)|b);
    	return color;
    }
    
    public static int[][] kmean(KmPic[] objs, int k){
    	Arrays.sort(objs);
    	int width = img1.getWidth();
		int height = img1.getHeight();
		int[][] ret = new int[width][height];//kmean����ֵ
		if(k==0)return ret;//k=0���ؿ�����
		int len = objs.length;
		int cs0 = len/k;
		
		KmPic[] last = new KmPic[k];
		KmPic[] save = new KmPic[k];
		//��last����ֵ��ѡ������ļ�����ɫ
		for(int i=0;i<k;i++) {
			last[i] = objs[i*cs0];
		}
		//����ÿ����ɫ���ҳ����������������ɫѡ���������ģ������Ӧ�������ļ��ϣ���ȷ��k���µľ������ġ�����ȼ��ѡ����
		int a[][] = new int[k][];//һ���������500����㣬�ǳ�ռ���ڴ棬������
		//��¼ÿһ�и���
		int num[] = new int[k];
		//��ʼ��
		for(int i=0;i<k;i++) {
			num[i] = 0;
		}
		//�������Σ���һ�����ÿ������ĳ�
		for(int i=0; i<len; i++) {
			KmPic c = objs[i];
			int cnt=0;double dst=100000;
			for(int j=0;j<k;j++) {
				if(last[j].getDistance(c) < dst) {
					cnt = j;
					dst = last[j].getDistance(c);
				}
			}
			num[cnt]++;
		}
		//�ڶ��γ�ʼ��
		for(int i=0;i<k;i++) {
			a[i] = new int[num[i]];
			num[i] = 0;
		}
		//��ɾ���
		for(int i=0; i<len; i++) {
			KmPic c = objs[i];
			int cnt=0;double dst=100000;
			for(int j=0;j<k;j++) {
				if(last[j].getDistance(c) < dst) {
					cnt = j;
					dst = last[j].getDistance(c);
				}
			}
			a[cnt][num[cnt]] = i;
			num[cnt]++;
			//System.out.println("first cnt = "+cnt);
		}
		
		//����ÿ���������ģ������µľ�������,��������ɫ
		for(int i=0;i<k;i++) {
			int serials[] = new int[num[i]];
			for(int j=0;j<num[i];j++) {
				int ss = a[i][j];
				serials[j] = ss;
			}
			save[i] = (KmPic) objs[i*cs0].getCenter(objs, serials);
		}
		
		//��һ���ж�
		Arrays.sort(last);
		Arrays.sort(save);
		//Ĭ����ȷ
		int ok = 1;
		for(int i=0;i<k;i++) {
			if(last[i].color!=save[i].color) {
				ok=0;
				break;
			}
		}
		//���������ķ����ı䣬���������㷨
		int jsq=0;//��������
		while(ok==0 && jsq<100) {
			System.out.println("���������У���"+(jsq++)+"��");
			
			//����ÿ���㣬�ҳ��������������ѡ���������ģ������Ӧ�������ļ��ϣ���ȷ��k���µľ������ġ�
			//��һ����վ�������
			for(int i=0;i<k;i++) {
				num[i]=0;
			}
			for(int i=0; i<len; i++) {
				KmPic c = objs[i];
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
				KmPic c = objs[i];
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
				save[i] = (KmPic) objs[i*cs0].getCenter(objs, serials);
				//System.out.println("x = "+save[i].x+", y = "+save[i].y);
			}
			
			//������
			Arrays.sort(last);
			Arrays.sort(save);
			//Ĭ����ȷ
			ok=1;
			for(int i=0;i<k;i++) {
				if(last[i].color!=save[i].color) {
					ok=0;
					break;
				}
			}
			//�������ı����last
			for(int i=0;i<k;i++) {
				last[i] = save[i];
			}
		}
		//�޸���Щ����λ�õ���ɫΪ��������
		//����ÿ���������ģ������µľ�������,��������ɫ
		for(int i=0;i<k;i++) {
			for(int j=0;j<num[i];j++) {
				int ss = a[i][j];//�����е���objs�е�λ��
				ret[(int)objs[ss].x][(int)objs[ss].y] = (int)save[i].color;
			}
		}
		System.out.println("��������Ϊ"+jsq+"��");
    	return ret;
    }
    
	
	public void paint(Graphics g)//��дpaint()����
	{
		super.paint(g);
		//System.out.println(num);
		
	}
}
 