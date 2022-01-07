import java.util.Random;


public class GA {
    public static int generation = 0;
	static final int TARGET = 165;
	static int mutationRate = 5;
	static private int[] Weight = {16,24,27};
	// static private int[] scores = {6,8,10};
	// static private int unchanged_box = 1;
	static private int[] orientations = {1,2,3};
	static private int[] rotations = {1,2,3};
	
    //beginning of the main methat that will have to work with my calculations(see comments at that section)
	public static void main(double[] args) {
	 

		int Max_Value = 165;
		for (int i = 16; i <= 27; i++) {
			Weight[i-16] = i;			
		}
		Weight[4] = ' ';
		orientations[4] = ' ';
		rotations[4] = ' ';

		Random generator = new Random(System.currentTimeMillis());
		Boxes[] boxPopulation = new Boxes[Max_Value];

		//initialize random boxes
		for (int i = 0; i < Max_Value; i++) {
			int[] TemporaryBox = new int[TARGET];
			for (int k = 0; k < TARGET; k++) {
				TemporaryBox[k] =Weight[generator.nextInt(Weight.length)];
			} 
			boxPopulation[i] = new Boxes(TemporaryBox, orientations, rotations);
		}
		
		GeneticAlgorithm(boxPopulation, Max_Value);

	}

		public static void GeneticAlgorithm(Boxes [] Max_value, int BoxSize){
		//put the method here in setboxes

		
		//elitest selection (choosing the top 40%)
		AIJudgeParcels.scoring(Max_value);
		//TODO: tournament

		// while (nextPopulation too small) {
		// 	Members tournament = randomly choose x members from currentPopulation
		
		// 	if(crossover){
		// 		Member parents = select best two members from tournament
		// 		Member children = crossover(parents)
		// 		nextPopulation.add(children);
		// 	} else {
		// 		Member parent = select best one member from tournament
		// 		Member child = mutate(parent)
		// 		nextPopulation.add(child);
		// 	}
		// }
		
		Boxes [] newBoxes = new Boxes [BoxSize +((BoxSize/100)*40)];
		for (int i = 0; i < Max_value.length; i++) {	
			newBoxes[i]=Max_value[i];
		}
		
		generation++;

		//crossover the top25% of the boxes
		
		int crossing = BoxSize;
		for (int i = 0; i < (Max_value.length/100)*25; i++) {
			int cross = 0;

			Random random = new Random();
			int crossoverLocation = random.nextInt(3);

			while(cross <2){
				newBoxes[crossing]= crossoverBoxes(newBoxes[i], newBoxes[i+1], crossoverLocation)[cross];
				cross++;
				crossing ++;
			}

		}
		//TODO: mutating the boxes

		//print out score and generations
		System.out.println("Generation:" + generation + "\nScore:" + Max_value[0].getScore()+ "\n\n\n");
	
		//recursion
		while (Max_value[0].score != TARGET){
			GeneticAlgorithm(Max_value, BoxSize);
		}

		//Phenotype of the boxes
		if(Max_value[0].score == TARGET){
			for (int i = 0; i < Max_value.length; i++) {
				System.out.println(Max_value[i].Description() + "Score: " + Max_value[i].score);
			}
		}
		
		
		

	}

	//something with the parcels and rotations
	public static int getMaxRotation(int parcelID){
		if(parcelID==0){
			return 4;
		}
		if(parcelID==1){
			return 6;
		}
		else{
			return 1;
		}
	}

    //this is my crossover method. If you see any optimalisations possible, lmk. I'd be interested in learning other ways.
	public static Boxes[] crossoverBoxes(Boxes box1, Boxes box2, int crossoverLocations){
		Boxes [] newBoxes = new Boxes [4];
		Boxes temporaryBox = box1.clone();
		Boxes CrossoverBox1 = box1.clone();
		Boxes CrossoverBox2 = box2.clone();

		newBoxes[0]=CrossoverBox1;
		newBoxes[1]=CrossoverBox2;
		int crossoverLocation = crossoverLocations;

		for (int i = 0; i < crossoverLocation; i++) {
			box1.getAllBoxes()[i] = box2.getAllBoxes()[i];
		}

		for (int i = 0; i < crossoverLocation; i++) {
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
	// 		int[] chromosomes=boxpopulation[i].getAllBoxes();
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

	//this is a method i think we are going to have to use
	public static void mutation2(Boxes[] boxpopulation){
		int roll;
		Random rand=new Random();
		//for every individual in the population
		for(int i=0;i<boxpopulation.length;i++){
			//get its chromosomes
			int[] chromosomes=boxpopulation[i].getAllBoxes();
			//for every chromosome 
			for(int j=0;j<chromosomes.length;j++){
				//roll a 100 sided die and if its lower then five mutate that piece into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					chromosomes[j]=rand.nextInt(3);
				}
			}
			int[] rotations=boxpopulation[i].getRotation();
			for(int j=0;j<rotations.length;j++){
				//roll a 100 sided die and if its lower then five mutate that rotation into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					chromosomes[j]=rand.nextInt(getMaxRotation(boxpopulation[i].getAllBoxes()[j]));
				}
			}
			//!!!!!!! be careful !!!!!! wss een out of index error

			int[] orientation=boxpopulation[i].getOrientation();
			for(int j=0;j<orientation.length;j++){
				//roll a 100 sided die and if its lower then five mutate that rotation into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					chromosomes[j]=rand.nextInt(3);
				}
			}

		}

	 }

	
}
