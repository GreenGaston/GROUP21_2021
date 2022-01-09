import java.util.Random;

import javax.swing.Box;


public class GA {
    public static int generation = 1000;
	static final int TARGET = 165;
	public static int pieceAmount = 100;
	static int mutationRate = 5;
	public static int Max_Value = 0;
	// static private int[] Weight;
	// static private int[] scores = {6,8,10};
	// static private int unchanged_box = 1;
	// static private int[] orientations;
	// static private int[] rotations;
	
    //beginning of the main methat that will have to work with my calculations(see comments at that section)
	public static void main(String[] args) {
	 

		Max_Value = 1000;
				
		Random generator = new Random(System.currentTimeMillis());
		Boxes[] boxPopulation = new Boxes[Max_Value];
		int[] boxOrientation = new int[pieceAmount];
		int[] boxRotation = new int[pieceAmount];
		int[] TemporaryBox = new int[pieceAmount];
		
		//initialize random boxes, rotation and orientation
		
		for (int i = 0; i < Max_Value; i++) {
			for (int k = 0; k < pieceAmount; k++) {
				TemporaryBox[k] = generator.nextInt(3);
				boxRotation[k] = generator.nextInt(4);
				boxOrientation[k] = generator.nextInt(3);
			} 
			boxPopulation[i] = new Boxes(TemporaryBox, boxRotation, boxOrientation);
		}
		
		

		GeneticAlgorithm(boxPopulation, generation);
	}
	

	//sorting based on score
	public static void sortBoxes(Boxes[] population){
        Boxes[] answer=new Boxes[population.length];
        int[] scores=new int[population.length];
        for(int i=0;i<population.length;i++){
            scores[i]=population[i].getScore();
        }

        //bubble sort
        int listlength = scores.length;
        for (int i = 0; i < listlength-1; i++){
            for (int j = 0; j < listlength-i-1; j++){
                if (scores[j] > scores[j+1])
                {

                    int temp = scores[j];
                    scores[j] = scores[j+1];
                    scores[1+j] = temp;

                    Boxes temp1= population[j];
                    population[j] =population[j+1];
                    population[j+1] =temp1;
                }
            }
        }
    }
	public static void print3dint(int[][][] answerGrid){
        for(int i=0;i<answerGrid.length;i++){
            for(int j=0;j<answerGrid[0].length;j++){
                for(int k=0;k<answerGrid[0][0].length;k++){
                    System.out.print(answerGrid[i][j][k]+" ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

	public static void GeneticAlgorithm(Boxes [] Max_value, int generations){
		//put the method here in setboxes

		
		AIJudgeParcels.scoring(Max_value);
		Boxes[] newPopulation = new Boxes[Max_value.length];
		
		//method 
		Boxes[] tempBoxes = new Boxes[5];
		Boxes parent1;
		Boxes parent2;
		
		Random rand = new Random();
		for (int j = 0; j < generations; j++) {
			
		
			for (int i = 0; i < Max_value.length/2; i++) {
				tempBoxes[0] = Max_value[rand.nextInt(Max_value.length)];
				tempBoxes[1] = Max_value[rand.nextInt(Max_value.length)];
				tempBoxes[2] = Max_value[rand.nextInt(Max_value.length)];
				tempBoxes[3] = Max_value[rand.nextInt(Max_value.length)];
				tempBoxes[4] = Max_value[rand.nextInt(Max_value.length)];

				
				sortBoxes(tempBoxes);
				parent1 = tempBoxes[4];
				tempBoxes = new Boxes[5];

				tempBoxes[0] = Max_value[rand.nextInt(Max_value.length)];
				tempBoxes[1] = Max_value[rand.nextInt(Max_value.length)];
				tempBoxes[2] = Max_value[rand.nextInt(Max_value.length)];
				tempBoxes[3] = Max_value[rand.nextInt(Max_value.length)];
				tempBoxes[4] = Max_value[rand.nextInt(Max_value.length)];

				
				sortBoxes(tempBoxes);
				parent2 = tempBoxes[4];
				tempBoxes = new Boxes[5];
				
				Boxes [] Boxchildren = crossoverBoxes(parent1, parent2, rand.nextInt(parent1.getAllBoxes().length));
				newPopulation[i+Max_value.length/2] = Boxchildren[0];
				newPopulation[i] = Boxchildren[1];
			}
			mutation(newPopulation);
			Max_value = newPopulation;
		}



				
		// Boxes [] newBoxes = new Boxes [BoxSize];
		// for (int i = 0; i < Max_value.length; i++) {	
		// 	newBoxes[i]=Max_value[i];
		// }
		
		generation++;
		//crossover the top25% of the boxes
		
		// int crossoverBoxes = BoxSize;
		// for (int i = 0; i < (Max_value.length/100)*25; i++) {
		// 	int cross = 0;
			
		// 	Random random = new Random();
		// 	int crossoverLocation = random.nextInt(3);
			
		// 	while(cross <2){
		// 		newBoxes[crossoverBoxes]= crossoverBoxes(newBoxes[i], newBoxes[i+1], crossoverLocation)[cross];
		// 		cross++;
		// 		crossoverBoxes ++;
		// 	}
			
		// }

		//psuedocode of my idea:
		//1. pick 5 random boxes from the boxpopulation(Max_value)
		//2. cross over the 2 best scoring boxes from the 5 I just picked
		//3. add the new chromosome  to the next population
		//3. repeat this untill best score achieved

		//for(random generation)
		// pick 5
		//crossover 2 best scoring --> child
		// add child to population

		//sorting method


		//if(crossoverBoxes>0){
			
		// 	Boxes [] tournament = new Boxes [crossoverBoxes+((crossoverBoxes/100)*2)];
		// 	for (int i = 0; i < Max_value.length; i++) {	
		// 	tournament[i]=Max_value[i];
		// 	}
			
		// 	Random random = new Random();
		// 	int crossoverLocation = random.nextInt(3);
			
		// 	Boxes [] tournamentChildren = crossoverBoxes(tournament, tournament, crossoverLocation);
		// 			Boxes newpopulation = Max_value.Boxes(tournamentChildren)
		// }

		// else{

		// 	Boxes [] tournament = new Boxes [crossoverBoxes+((crossoverBoxes/100)*1)];
		// 		for (int i = 0; i < Max_value.length; i++) {	
		// 			tournament[i]=Max_value[i];
		// 			}
					
		// 			Random random = new Random();
		// 			int crossoverLocation = random.nextInt(3);
					
		// 			Boxes [] tournamentChild2 = crossoverBoxes(tournament, tournament, crossoverLocation);
		// 			Boxes newpopulation = Max_value.Boxes(tournamentChild2)

		// }
		
		// Boxes[] newPopulation = new Boxes[BoxSize ];
		// for (int i = 0; i < Max_value.length; i++) {
		// 	newPopulation[i]=Max_value[i];
			
		// }
		

		//TODO: mutating the boxes

		//print out score and generations
		sortBoxes(Max_value);
		System.out.println("Generation:" + generation + "\nScore:" + Max_value[Max_Value-1].getScore()+ "\n\n\n");

		//print3dint(AIJudgeParcels.getGrid(Max_value[Max_Value-1].getAllBoxes(), Max_value[Max_Value-1].getRotation(), Max_value[Max_Value-1].getOrientation()));
	
		//recursion
		// while (Max_value[0].score != TARGET){
		// 	GeneticAlgorithm(Max_value, generations);
		// }

		//Phenotype of the boxes
		// if(Max_value[0].score == TARGET){
		// 	for (int i = 0; i < Max_value.length; i++) {
		// 		System.out.println(Max_value[i].Description() + "Score: " + Max_value[i].score);
		// 	}
		// }
		
		
		
	

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

		int crossoverLocation = crossoverLocations;

		for (int i = 0; i < crossoverLocation; i++) {
			CrossoverBox1.getAllBoxes()[i] = CrossoverBox2.getAllBoxes()[i];
			CrossoverBox1.getOrientation()[i] = CrossoverBox2.getOrientation()[i];
			CrossoverBox1.getRotation()[i] = CrossoverBox2.getRotation()[i];
		}

		

		for (int i = 0; i < crossoverLocation; i++) {
			CrossoverBox2.getAllBoxes()[i] = temporaryBox.getAllBoxes()[i];
			CrossoverBox2.getOrientation()[i] = temporaryBox.getOrientation()[i];
			CrossoverBox2.getRotation()[i] = temporaryBox.getRotation()[i];
		}

		newBoxes[0]= box1;
		newBoxes[1]= box2;

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
	public static void mutation(Boxes[] boxpopulation){
		int roll;
		Random rand=new Random();
		//for every individual in the population
		for(int i=0;i<boxpopulation.length;i++){
			//get its chromosomes
			int[] chromosomes=boxpopulation[i].getAllBoxes();
			int[] rotations=boxpopulation[i].getRotation();
			for(int j=0;j<rotations.length;j++){
				//roll a 100 sided die and if its lower then five mutate that rotation into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					chromosomes[j]=rand.nextInt(getMaxRotation(boxpopulation[i].getAllBoxes()[j]));
				}
			}
			//!!!!!!! be careful !!!!!! wss een out of index error
			
			//for every chromosome 
			for(int j=0;j<chromosomes.length;j++){
				//roll a 100 sided die and if its lower then five mutate that piece into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					chromosomes[j]=rand.nextInt(3);
					chromosomes[j]=rand.nextInt(getMaxRotation(boxpopulation[i].getAllBoxes()[j]));
				}
			}

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
