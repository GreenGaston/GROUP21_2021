import java.util.Random;

public class GenerationSelector {
    public static Boxes[] population;
    public static int[] notUsedIndexes;
    public static Random random=new Random();

    public static void setPopulation(Boxes[] _population){
        population=clonePopulation(_population);
        notUsedIndexes=new int[_population.length];
        for (int i = 0; i < notUsedIndexes.length; i++) {
            notUsedIndexes[i]=i;
        }
        
        //System.out.println(notUsedIndexes.length);
    }

    public static Boxes[] clonePopulation(Boxes[] population){
        Boxes[] clonePop=new Boxes[population.length];
        for (int i = 0; i < population.length; i++) {
            clonePop[i]=population[i].clone();
            
        }
        return clonePop;
    }

    
    public static Boxes nextBox(){
        //System.out.println(notUsedIndexes.length);
        
        int temp;
        if(notUsedIndexes.length==0){
            notUsedIndexes=new int[population.length];
            for (int i = 0; i < notUsedIndexes.length; i++) {
                notUsedIndexes[i]=i;
            }
        }
        temp=random.nextInt(notUsedIndexes.length);
        
        Boxes answer= population[notUsedIndexes[temp]];
        notUsedIndexes=removeIndex(notUsedIndexes, temp);
        return answer;

        }
    
    public static int[] removeIndex(int[] Indexes, int index) {

        int[] answerList = new int[Indexes.length - 1];

        for (int i = 0, k = 0; i < Indexes.length; i++) {

            if (i != index) {

                answerList[k] = (Indexes[i]);
                k++;
            }
        }
        return answerList;
    }

    
}
