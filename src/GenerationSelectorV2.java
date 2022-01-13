import java.util.Random;

//this method is used to ensure that all individuals participate in a tournament
public class GenerationSelectorV2 {
    public static BoxesV2[] population;
    public static int[] notUsedIndexes;
    public static Random random=new Random();

    public static void setPopulation(BoxesV2[] _population){
        population=clonePopulation(_population);
        notUsedIndexes=new int[_population.length];
        for (int i = 0; i < notUsedIndexes.length; i++) {
            notUsedIndexes[i]=i;
        }
        
        //System.out.println(notUsedIndexes.length);
    }

    public static BoxesV2[] clonePopulation(BoxesV2[] population){
        BoxesV2[] clonePop=new BoxesV2[population.length];
        for (int i = 0; i < population.length; i++) {
            clonePop[i]=population[i].clone();
            
        }
        return clonePop;
    }

    //this method only pick non used indexes if they exist 
    public static BoxesV2 nextBox(){
        //System.out.println(notUsedIndexes.length);
        
        int temp;
        if(notUsedIndexes.length==0){
            notUsedIndexes=new int[population.length];
            for (int i = 0; i < notUsedIndexes.length; i++) {
                notUsedIndexes[i]=i;
            }
        }
        temp=random.nextInt(notUsedIndexes.length);
        
        BoxesV2 answer= population[notUsedIndexes[temp]];
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
