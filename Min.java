import java.io.*;
import java.util.Random;

public class Min {
    public static void main(String[] args) throws Exception{
        try(InputStreamReader ir = new InputStreamReader(System.in); BufferedReader in = new BufferedReader(ir)){
            int arrayDim = 10;
            int[] numbers = new int[arrayDim];
            Random rand = new Random();
            int threadNumber = 2;
            int minValue = Integer.MAX_VALUE;

            try{
                threadNumber = Integer.parseInt(args[0]);
            }
            catch(ArrayIndexOutOfBoundsException | NumberFormatException exc){
                System.out.println("Verranno usati il numero di thread di default:" + threadNumber);
            }

            // inizializzazione array
            System.out.print("Array: ");
            for(int i = 0; i < arrayDim; i++){
                numbers[i] = rand.nextInt(10) + 1;
                System.out.print(numbers[i] + " ");
            }
            System.out.println("");

            int step = arrayDim / threadNumber;

            if(arrayDim % threadNumber == 0){
                // caso 1: la dimensione dell'array è divisibile per il numero di thread
                // creazione e inizializzazione
                MinThread[] threads = new MinThread[threadNumber];
                for(int i = 0; i < threadNumber; i++){
                    threads[i] = new MinThread(numbers, step*i, step*(i+1));
                    threads[i].start();
                }

                // join e confronto
                for(int i = 0; i < threadNumber; i++){
                    threads[i].join();
                    if(minValue > threads[i].getPartialMin()){
                        minValue = threads[i].getPartialMin();
                    }
                }
                System.out.println("Min:" + minValue);
            }else{
                // caso 2: la dimensione dell'array non è divisibile per il numero di thread
                // creazione e inizializzazione
                MinThread[] threads = new MinThread[threadNumber + 1];
                for(int i = 0; i < threadNumber; i++){
                    threads[i] = new MinThread(numbers, step*i, step*(i+1));
                    threads[i].start();
                }
                threads[threadNumber] = new MinThread(numbers, numbers.length-(numbers.length % threadNumber), numbers.length);
                threads[threadNumber].start();

                // join e confronto
                for(int i = 0; i < threadNumber; i++){
                    threads[i].join();
                    if(minValue > threads[i].getPartialMin()){
                        minValue = threads[i].getPartialMin();
                    }
                }
                threads[threadNumber].join();
                if(minValue > threads[threadNumber].getPartialMin()){
                    minValue = threads[threadNumber].getPartialMin();
                }
                System.out.println("Min:" + minValue);
            }
        }
    }
}

class MinThread extends Thread{
    private int[] numbers;
    private int startPos, endPos;
    private int partialMin;

    public MinThread(int[] numbers, int startPos, int endPos){
        this.numbers = numbers;
        this.startPos = startPos;
        this.endPos = endPos;
        this.partialMin = Integer.MAX_VALUE;
    }

    public int getPartialMin(){
        return this.partialMin;
    }

    public void run(){
        for(int i = startPos; i < endPos; i++){
            if(partialMin > numbers[i]){
                partialMin = numbers[i];
            }
        }
        return;
    }
}