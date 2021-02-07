import java.util.ArrayList;
import java.util.List;
class Singleton01{//此设计在多线程环境中存在不安全
	private Singleton01() {}
	private static Singleton01 instance;
	public static Singleton01 getInstance() {
		if (instance == null) {
			instance = new Singleton01();
		}
		return instance;
	}
}
class Singleton02{//由于JVM优化，现在方法同步和代码块同步性能没有很大差异
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
	//场景：大对象、稀少用，频繁访问会导致大量阻塞，影响性能
	public static Singleton03 getInstance() {
		synchronized (Singleton03.class) {
			if (instance == null) {
				instance = new Singleton03();
			}//对象创建：延迟创建，按需加载，延迟加载(并不是在加载类的时候就创建了对象，而是使用该对象的时候会创建它)
			return instance;
		}	
	}
	public void display() {}
	public static void show() {}
}
class Singleton04{
	private Singleton04() {}
	/**
	 * volatile:JMM(需要了解JMM Java内存模型)
	 * 
	 * 1)保证操作可见性（一个线程修改结束，其他程序可见）
	 * 2)禁止指令重排序
	 * 		如下面所示创建对象会有如下4个操作，这些操作是通过指令执行的，JVM有时会优化指令，使执行的指令顺序发生变化，使用volatitl会禁止
	 * 3)但是不能保证原子性，所以一般用于修饰变量，synchronized能够保证原子性
	 * 
	 * instance = new Singleton();
	 * a)堆内存开辟空间
	 * b)对象属性初始化
	 * c)执行构造方法
	 * d)为变量instance赋值
	 */
	private static  volatile Singleton04 instance;
	/*
	   * 基于Singleton03进行优化
	 *1)尽量减少阻塞的线程
	 *2)尽量缩小锁的应用范围(锁的代码块) 
	 */
	public static Singleton04 getInstance() {
		if (instance == null) {//双重验证
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
