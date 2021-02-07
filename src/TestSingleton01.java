import java.util.ArrayList;
import java.util.List;
class Singleton01{//������ڶ��̻߳����д��ڲ���ȫ
	private Singleton01() {}
	private static Singleton01 instance;
	public static Singleton01 getInstance() {
		if (instance == null) {
			instance = new Singleton01();
		}
		return instance;
	}
}
class Singleton02{//����JVM�Ż������ڷ���ͬ���ʹ����ͬ������û�кܴ����
	//private Singleton02() {}
	private static Singleton02 instance;
	public synchronized static Singleton02 getInstance() {
			if (instance == null) {
				instance = new Singleton02();
			}
			return instance;	
	}
}
class Singleton03{
	private Singleton03() {}
	private static Singleton03 instance;
	//�����������ϡ���ã�Ƶ�����ʻᵼ�´���������Ӱ������
	public static Singleton03 getInstance() {
		synchronized (Singleton03.class) {
			if (instance == null) {
				instance = new Singleton03();
			}//���󴴽����ӳٴ�����������أ��ӳټ���(�������ڼ������ʱ��ʹ����˶��󣬶���ʹ�øö����ʱ��ᴴ����)
			return instance;
		}	
	}
	public void display() {}
	public static void show() {}
}
class Singleton04{
	private Singleton04() {}
	/**
	 * volatile:JMM(��Ҫ�˽�JMM Java�ڴ�ģ��)
	 * 
	 * 1)��֤�����ɼ��ԣ�һ���߳��޸Ľ�������������ɼ���
	 * 2)��ָֹ��������
	 * 		��������ʾ���������������4����������Щ������ͨ��ָ��ִ�еģ�JVM��ʱ���Ż�ָ�ʹִ�е�ָ��˳�����仯��ʹ��volatitl���ֹ
	 * 3)���ǲ��ܱ�֤ԭ���ԣ�����һ���������α�����synchronized�ܹ���֤ԭ����
	 * 
	 * instance = new Singleton();
	 * a)���ڴ濪�ٿռ�
	 * b)�������Գ�ʼ��
	 * c)ִ�й��췽��
	 * d)Ϊ����instance��ֵ
	 */
	private static  volatile Singleton04 instance;
	/*
	   * ����Singleton03�����Ż�
	 *1)���������������߳�
	 *2)������С����Ӧ�÷�Χ(���Ĵ����) 
	 */
	public static Singleton04 getInstance() {
		if (instance == null) {//˫����֤
			synchronized (Singleton04.class) {
				if (instance == null) {
					instance = new Singleton04();
				}
			}	
		}	
		return instance;
	}
}
public class TestSingleton01 {

	public static void main(String[] args) {
		List<Thread> list = new ArrayList<Thread>();
		for (int i = 0; i < 10; i++) {
			list.add(new Thread() {
				public void run() {
					System.out.println(Singleton01.getInstance());
				}
			});
		}
		for(Thread t:list) {
			t.start();
		}
	}
}
