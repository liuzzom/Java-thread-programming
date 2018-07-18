public class Test{
    public static void main(String[] args) throws Exception{
        for(int i = 0; i < 10; i++){
            System.out.print("\rMessage number " + (i+1));
            Thread.sleep(1000);
        }
        System.out.print("\n");
    }
}