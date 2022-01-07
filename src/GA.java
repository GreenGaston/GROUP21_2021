//package src;

import java.util.Random;


public class GA {
    public static int generation = 0;
	static final int TARGET = 165;
	static int mutationRate = 5;
	static private int Weight[] = {16,24,27};
	static private int scores[] = {6,8,10};
	static private int unchanged_box = 1;
	
    //beginning of the main methat that will have to work with my calculations(see comments at that section)
	public static void main(double[] args) {
	 

		int Max_Value = 165;
		Random generator = new Random(System.currentTimeMillis());
		Boxes[] boxPopulation = new Boxes[Max_Value];

		//initialize random boxes
		for (int i = 0; i < Max_Value; i++) {
			int[] TemporaryBox = new int[TARGET];
			for (int k = 0; k < TemporaryBox.length; k++) {
				TemporaryBox[k] =Weight[generator.nextInt(Weight.length)];
			} 
			boxPopulation[i] = new Boxes(TemporaryBox);
		}
		Calculations(boxPopulation, Max_Value);

	}

	public static void Calculations(Boxes [] population, int popSize){

		/*TO-DO: Calculations based on sorting mechanism.
        to do so, we first need a sorting method like the heapsort file in my CCN genetic algorithm
        if you could make the sorting file, it would help me a lot as then the hardest part is basically done
        for the GA. In this calculations method, I will have to make the GA work with the sorting method.
        */

	}
    //this is my crossover method. If you see any optimalisations possible, lmk. I'd be interested in learning other ways.
	public static Boxes[] crossoverBoxes(Boxes box1, Boxes box2, int crossoverLocation){
		Boxes [] newBoxes = new Boxes [4];
		Boxes temporaryBox = box1.clone();
		Boxes CrossoverBox1 = box1.clone();
		Boxes CrossoverBox2 = box2.clone();

		newBoxes[0]=CrossoverBox1;
		newBoxes[1]=CrossoverBox2;

		for (int i = 0; i < newBoxes.length; i++) {
			box1.getAllBoxes()[i] = box2.getAllBoxes()[i];
		}

		for (int i = 0; i < newBoxes.length; i++) {
			box2.getAllBoxes()[i] = temporaryBox.getAllBoxes()[i];
		}

		newBoxes[2]= box1;
		newBoxes[3]= box2;

		return newBoxes;

        /*
        How this method works: I take 2 chromosomes. I pick a crossover location.
        the location is the point where the chromosomes will be broken. 
        Then I move one part of parent 1 to the new part of parent 2. Then I move 
        the part of parent 2 back to parent 1. I have made a copy(temporary box) of parent 1(box 1) so 
        the code will remember what is was before the switch.
        */

	}

	// //this is a mutation method for integers representing the pentomino pieces
	// public static void mutation(Boxes[] boxpopulation){
	// 	int roll;
	// 	Random rand=new Random();
	// 	//for every individual in the population
	// 	for(int i=0;i<boxpopulation.length;i++){
	// 		//get its chromosomes
	// 		int[] chromosomes=boxpopulation[i].getAllBoxes()
	// 		//for every chromosome 
	// 		for(int j=0;j<chromosomes.length;j++){
	// 			//roll a 100 sided die and if its lower then five mutate that piece into another one
	// 			roll=rand.nextInt(100);
	// 			if(roll<mutationRate){
	// 				chromosomes[j]=rand.nextInt(12);
	// 			}
	// 		}
	// 	}
	// }

	// //this is a method i think we are going to have to use
	// public static void mutation2(Boxes[] boxpopulation){
	// 	int roll;
	// 	Random rand=new Random();
	// 	//for every individual in the population
	// 	for(int i=0;i<boxpopulation.length;i++){
	// 		//get its chromosomes
	// 		int[] chromosomes=boxpopulation[i].getAllBoxes()
	// 		//for every chromosome 
	// 		for(int j=0;j<chromosomes.length;j++){
	// 			//roll a 100 sided die and if its lower then five mutate that piece into another one
	// 			roll=rand.nextInt(100);
	// 			if(roll<mutationRate){
	// 				chromosomes[j]=rand.nextInt(12);
	// 			}
	// 		}
	// 		int[] rotations=boxpopulation.getRotations();
	// 		for(int j=0;j<rotations.length;j++){
	// 			//roll a 100 sided die and if its lower then five mutate that rotation into another one
	// 			roll=rand.nextInt(100);
	// 			if(roll<mutationRate){
	// 				chromosomes[j]=rand.nextInt(3);
	// 			}
	// 		}
	// 		int[] orientation=boxpopulation.getOrientation();
	// 		for(int j=0;j<orientation.length;j++){
	// 			//roll a 100 sided die and if its lower then five mutate that rotation into another one
	// 			roll=rand.nextInt(100);
	// 			if(roll<mutationRate){
	// 				chromosomes[j]=rand.nextInt(2);
	// 			}
	// 		}

	// 	}

	// }

	//TO-DO: MUTATION METHOD
}
