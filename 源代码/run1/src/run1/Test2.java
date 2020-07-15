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
	static BufferedImage img1;//img1一直是原图片,img2初始化为原图片
	static BufferedImage img2;
    ImageFrame(String s)
	{
		super(s);
		this.setLayout(new FlowLayout());
		this.setSize(width, height);
		this.setBackground(Color.blue);
		this.setVisible(true);
		//关闭时终止
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//目录
		JMenu menu, submenu;
	    JMenuItem i1, i2, i3, i4;
		JMenuBar mb = new JMenuBar();
		menu = new JMenu("文件");
        i1 = new JMenuItem("选择图片");//选择图片显示在窗口，并将路径保存在path1
        //图片存为BufferedImage img1，img2初始化等于img1
        i2 = new JMenuItem("像素化");
        //1. 逐个像素转存KmPic数组，kmean算法处理后，转出存为BufferImage img2
        //2. 将图片显示在窗口
        i3 = new JMenuItem("还原");//还原图片
        i4 = new JMenuItem("保存");//将路径保存在path2，将图片img2保存在path2
        menu.add(i1);
        menu.addSeparator();
        menu.add(i2);
        menu.addSeparator();
        menu.add(i3);
        menu.addSeparator();
        menu.add(i4);
        //添加目录
        mb.add(menu);
        this.setJMenuBar(mb);
        this.setLocationRelativeTo(null);
        JLabel label=new JLabel();
        //设置目录事件
        i1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = 0;
				File file = null;
				String path = null;
				JFileChooser fileChooser = new JFileChooser();
				FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
				System.out.println(fsv.getHomeDirectory());                //得到桌面路径
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("请选择要打开的图片...");
				fileChooser.setApproveButtonText("确定");
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
				//1. 逐个像素转存KmPic数组，kmean算法处理后，转出存为BufferImage img2
				if(img1==null)return;
				int width = img1.getWidth();
				int height = img1.getHeight();
				int[][] ans = new int[width][height];//kmean返回值
				JFrame f = new JFrame();
		        String k = JOptionPane.showInputDialog(f,"请输入像素值");
				System.out.println("共计像素为"+height*width+"个！");
				//像素量在10万到500万之间
				if(height*width > 5000000) {
					System.out.println("照片太大，处理失败！");return;
				}
				KmPic pic[] = new KmPic[height*width];
				int cnt=0;//pic计数器
				
				for(int i=0;i<width;i++) {
					for(int j=0;j<height;j++) {
						int color = img1.getRGB(i, j);
						pic[cnt++] = new KmPic(i,j,color);
					}
				}
				
				System.out.println("w = "+width+", h="+height);
				System.out.println("k = "+k);
				ans = kmean(pic,Integer.parseInt(k));
				
				//2. 将图片显示在窗口
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
				FileSystemView fsv = FileSystemView.getFileSystemView();  //注意了，这里重要的一句
				System.out.println(fsv.getHomeDirectory());                //得到桌面路径
				fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
				fileChooser.setDialogTitle("请选择要保存的位置...");
				fileChooser.setApproveButtonText("确定");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				result = fileChooser.showOpenDialog(null);
				if (JFileChooser.APPROVE_OPTION == result) {
					path=fileChooser.getSelectedFile().getPath();
					System.out.println("path: "+path);
					path2 = path;
					try {
						File outputfile1 = new File(path);
				        ImageIO.write(img2, "jpg", outputfile1);
				        System.out.println("成功！");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
        //添加默认图片
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
    //rgb转int
    public int converRgbToArgb(int r,int g,int b){
    	int color = ((0xFF << 24)|(r << 16)|(g << 8)|b);
    	return color;
    }
    
    public static int[][] kmean(KmPic[] objs, int k){
    	Arrays.sort(objs);
    	int width = img1.getWidth();
		int height = img1.getHeight();
		int[][] ret = new int[width][height];//kmean返回值
		if(k==0)return ret;//k=0返回空数组
		int len = objs.length;
		int cs0 = len/k;
		
		KmPic[] last = new KmPic[k];
		KmPic[] save = new KmPic[k];
		//给last赋初值，选出最初的几种颜色
		for(int i=0;i<k;i++) {
			last[i] = objs[i*cs0];
		}
		//对于每个颜色，找出所有离他最近的颜色选定聚类中心，加入对应聚类中心集合，并确定k个新的聚类中心。最初等间隔选中心
		int a[][] = new int[k][];//一个聚类最多500万个点，非常占用内存，不定长
		//记录每一行个数
		int num[] = new int[k];
		//初始化
		for(int i=0;i<k;i++) {
			num[i] = 0;
		}
		//运算两次，第一次求出每个聚类的长
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
		//第二次初始化
		for(int i=0;i<k;i++) {
			a[i] = new int[num[i]];
			num[i] = 0;
		}
		//完成聚类
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
		
		//对于每个聚类中心，求它新的聚类中心,即聚类颜色
		for(int i=0;i<k;i++) {
			int serials[] = new int[num[i]];
			for(int j=0;j<num[i];j++) {
				int ss = a[i][j];
				serials[j] = ss;
			}
			save[i] = (KmPic) objs[i*cs0].getCenter(objs, serials);
		}
		
		//第一次判断
		Arrays.sort(last);
		Arrays.sort(save);
		//默认正确
		int ok = 1;
		for(int i=0;i<k;i++) {
			if(last[i].color!=save[i].color) {
				ok=0;
				break;
			}
		}
		//当聚类中心发生改变，迭代进行算法
		int jsq=0;//计数器！
		while(ok==0 && jsq<100) {
			System.out.println("迭代进行中，第"+(jsq++)+"次");
			
			//对于每个点，找出所有离他最近的选定聚类中心，加入对应聚类中心集合，并确定k个新的聚类中心。
			//第一次清空聚类数组
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
			//第二次清空聚类数组
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
			//对于每个聚类中心，求它新的聚类中心。
			for(int i=0;i<k;i++) {
				int serials[] = new int[num[i]];
				for(int j=0;j<num[i];j++) {
					int ss = a[i][j];
					serials[j] = ss;
				}
				save[i] = (KmPic) objs[i*cs0].getCenter(objs, serials);
				//System.out.println("x = "+save[i].x+", y = "+save[i].y);
			}
			
			//先排序
			Arrays.sort(last);
			Arrays.sort(save);
			//默认正确
			ok=1;
			for(int i=0;i<k;i++) {
				if(last[i].color!=save[i].color) {
					ok=0;
					break;
				}
			}
			//聚类中心保存给last
			for(int i=0;i<k;i++) {
				last[i] = save[i];
			}
		}
		//修改这些聚类位置的颜色为聚类中心
		//对于每个聚类中心，求它新的聚类中心,即聚类颜色
		for(int i=0;i<k;i++) {
			for(int j=0;j<num[i];j++) {
				int ss = a[i][j];//聚类中点在objs中的位置
				ret[(int)objs[ss].x][(int)objs[ss].y] = (int)save[i].color;
			}
		}
		System.out.println("迭代次数为"+jsq+"次");
    	return ret;
    }
    
	
	public void paint(Graphics g)//重写paint()方法
	{
		super.paint(g);
		//System.out.println(num);
		
	}
}
 