//package src;

import java.util.Random;




public class PentominoGA {
    
	static final int TARGET = 165;
	public static int pieceAmount = 1000;
	public static int generation = 500;
	static int mutationRate = 1;
	public static int populationSize = 1000;
	public static int tournamentSize=10;
	
    //beginning of the main methat that will have to work with my calculations(see comments at that section)
	public static void main(String[] args) {
	 

		
				
		Random generator = new Random(System.currentTimeMillis());
		Boxes[] boxPopulation = new Boxes[populationSize];
		int[] boxOrientation = new int[pieceAmount];
		int[] boxRotation = new int[pieceAmount];
		int[] BoxPieces = new int[pieceAmount];
		
		//initialize random boxes, rotation and orientation
		
		for (int i = 0; i < populationSize; i++) {
			for (int k = 0; k < pieceAmount; k++) {
				BoxPieces[k] = generator.nextInt(12);
				boxRotation[k] = generator.nextInt(4);
				boxOrientation[k] = generator.nextInt(3);
			} 
			boxPopulation[i] = new Boxes(BoxPieces, boxRotation, boxOrientation);
		}
		
		

		GeneticAlgorithm(boxPopulation, generation);
	}
	

	//sorting based on score
	public static void sortBoxes(Boxes[] population){
        //Boxes[] answer=new Boxes[population.length];
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

	public static void GeneticAlgorithm(Boxes [] Population, int generations){
		//put the method here in setboxes

		
		Boxes[] newPopulation = new Boxes[Population.length];
		
		//method 
		Boxes[] tempBoxes = new Boxes[tournamentSize];
		Boxes parent1;
		Boxes parent2;
		//System.out.println(AIJudgeParcels.judgeVolumes(tempBoxes[k].getAllBoxes(), tempBoxes[k].getRotation(), tempBoxes[k].getOrientation()));
		
		
		Random rand = new Random();
		for (int j = 0; j < generations; j++) {
			AIJudgepentominoes.scoring(Population);
			GenerationSelector.setPopulation(Population);
			
		
			for (int i = 0; i < Population.length/2; i++) {
				for(int k=0;k<tournamentSize;k++){
					tempBoxes[k] = GenerationSelector.nextBox();

				}
				
				sortBoxes(tempBoxes);
				parent1 = tempBoxes[tournamentSize-1];
				tempBoxes = new Boxes[tournamentSize];

				for(int k=0;k<tournamentSize;k++){
					tempBoxes[k] = GenerationSelector.nextBox();
				}
				
				sortBoxes(tempBoxes);
				parent2 = tempBoxes[tournamentSize-1];
				tempBoxes = new Boxes[tournamentSize];
				
				Boxes [] Boxchildren = crossoverBoxes(parent1, parent2, rand.nextInt(parent1.getAllBoxes().length));
				newPopulation[i+Population.length/2] = Boxchildren[0];
				newPopulation[i] = Boxchildren[1];
			}
			mutation(newPopulation);
			Population = newPopulation;
		}

		
		sortBoxes(Population);
		System.out.println("Generation:" + generation + "\nScore:" + Population[populationSize-1].getScore()+ "\n\n\n");
		

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
			int[] orientation=boxpopulation[i].getOrientation();

			
			
			//for every chromosome 
			for(int j=0;j<chromosomes.length;j++){
				//roll a 100 sided die and if its lower then five mutate that piece into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					chromosomes[j]=rand.nextInt(12);
					
				}
			
				//roll a 100 sided die and if its lower then five mutate that rotation into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					rotations[j]=rand.nextInt(4);
				}
			//!!!!!!! be careful !!!!!! wss een out of index error

				//roll a 100 sided die and if its lower then five mutate that rotation into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					orientation[j]=rand.nextInt(3);
				}
			}

		}

	 }

	
}
