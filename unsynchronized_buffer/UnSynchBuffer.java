public class UnSynchBuffer implements Buffer{
	private int value = 0;

	public void set(int value){
		System.out.println(Thread.currentThread().getName() + " writes " + value);
		this.value = value;
	}

	public int get(){
		System.out.println(Thread.currentThread().getName() + " read " + this.value);
		return this.value;
	}
}