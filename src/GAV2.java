//package src;

import java.util.Random;




public class GAV2 {
    
	static final int TARGET = 165;
	public static int pieceAmount = 1000;
	public static int generation = 1000;
	static int mutationRate = 1;
	public static int populationSize = 1000;
	public static int tournamentSize=10;
	public static int length=33;
	public static int height=8;
	public static int width=5;
	public static int[][][] answerGrid;
	
    //beginning of the main methat that will have to work with my calculations(see comments at that section)
	public static void main(String[] args) {
	 

		
				
		Random generator = new Random(System.currentTimeMillis());
		BoxesV2[] boxPopulation = new BoxesV2[populationSize];
		int[] boxOrientation = new int[pieceAmount];
		int[] boxRotation = new int[pieceAmount];
		int[] BoxPieces = new int[pieceAmount];
		int[] boxX = new int[pieceAmount];
		int[] boxY = new int[pieceAmount];
		int[] boxZ = new int[pieceAmount];
		
		//initialize random boxes, rotation and orientation
		
		for (int i = 0; i < populationSize; i++) {
			for (int k = 0; k < pieceAmount; k++) {
				BoxPieces[k] = generator.nextInt(3);
				boxRotation[k] = generator.nextInt(4);
				boxOrientation[k] = generator.nextInt(3);
				boxX[k]=generator.nextInt(width);
				boxY[k]=generator.nextInt(height);
				boxZ[k]=generator.nextInt(length);
			} 
			boxPopulation[i] = new BoxesV2(BoxPieces, boxRotation, boxOrientation,boxX,boxY,boxZ);
		}
		
		

		GeneticAlgorithm(boxPopulation, generation);
	}

	public static int[][][] GAmethod(int _pieceamount,int _generations, int _mutationrate,int _populationsSize,int _TournamentSize){
		pieceAmount = _pieceamount;
		generation = _generations;
		mutationRate = _mutationrate;
		populationSize = _populationsSize;
		tournamentSize=_TournamentSize;


		Random generator = new Random(System.currentTimeMillis());
		BoxesV2[] boxPopulation = new BoxesV2[populationSize];
		int[] boxOrientation = new int[pieceAmount];
		int[] boxRotation = new int[pieceAmount];
		int[] BoxPieces = new int[pieceAmount];
		int[] boxX = new int[pieceAmount];
		int[] boxY = new int[pieceAmount];
		int[] boxZ = new int[pieceAmount];
		
		//initialize random boxes, rotation and orientation
		
		for (int i = 0; i < populationSize; i++) {
			for (int k = 0; k < pieceAmount; k++) {
				BoxPieces[k] = generator.nextInt(3);
				boxRotation[k] = generator.nextInt(4);
				boxOrientation[k] = generator.nextInt(3);
				boxX[k]=generator.nextInt(width);
				boxY[k]=generator.nextInt(height);
				boxZ[k]=generator.nextInt(length);

			} 
			boxPopulation[i] = new BoxesV2(BoxPieces, boxRotation, boxOrientation,boxX,boxY,boxZ);
		}
		

		
		

		GeneticAlgorithm(boxPopulation, generation);
		return answerGrid;
			
	}
	

	//sorting based on score
	public static void sortBoxes(BoxesV2[] population){
       
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

                    BoxesV2 temp1= population[j];
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

	public static void GeneticAlgorithm(BoxesV2 [] Population, int generations){
		//put the method here in setboxes

		BoxesV2[] newPopulation = new BoxesV2[Population.length];
		
		//method 
		BoxesV2[] tempBoxes = new BoxesV2[tournamentSize];
		BoxesV2 parent1;
		BoxesV2 parent2;
		//System.out.println(AIJudgeParcels.judgeVolumes(tempBoxes[k].getAllBoxes(), tempBoxes[k].getRotation(), tempBoxes[k].getOrientation()));
		
		
		Random rand = new Random();
		for (int j = 0; j < generations; j++) {
			AIJudgeParcelsV2.scoring(Population);
			GenerationSelectorV2.setPopulation(Population);
			
		
			for (int i = 0; i < Population.length/2; i++) {
				for(int k=0;k<tournamentSize;k++){
					tempBoxes[k] = GenerationSelectorV2.nextBox();

				}
				
				sortBoxes(tempBoxes);
				parent1 = tempBoxes[tournamentSize-1];
				tempBoxes = new BoxesV2[tournamentSize];

				for(int k=0;k<tournamentSize;k++){
					tempBoxes[k] = GenerationSelectorV2.nextBox();
				}
				
				sortBoxes(tempBoxes);
				parent2 = tempBoxes[tournamentSize-1];
				tempBoxes = new BoxesV2[tournamentSize];
				
				BoxesV2 [] Boxchildren = crossoverBoxes(parent1, parent2, rand.nextInt(parent1.getAllBoxes().length));
				newPopulation[i+Population.length/2] = Boxchildren[0];
				newPopulation[i] = Boxchildren[1];
			}
			mutation(newPopulation);
			Population = newPopulation;
		}

		AIJudgeParcelsV2.scoring(Population);
		sortBoxes(Population);
		System.out.println("Generation:" + generation + "\nScore:" + Population[populationSize-1].getScore()+ "\n\n\n");
		answerGrid=AIJudgeParcelsV2.getMatrix(Population[populationSize-1]);
		

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
	public static BoxesV2[] crossoverBoxes(BoxesV2 box1, BoxesV2 box2, int crossoverLocations){
		BoxesV2 [] newBoxes = new BoxesV2 [2];
		BoxesV2 temporaryBox = box1.clone();
		BoxesV2 CrossoverBox1 = box1.clone();
		BoxesV2 CrossoverBox2 = box2.clone();

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



	//this is a method i think we are going to have to use
	public static void mutation(BoxesV2[] boxpopulation){
		int roll;
		Random rand=new Random();
		//for every individual in the population
		for(int i=0;i<boxpopulation.length;i++){
			//get its chromosomes
			int[] Pieces=boxpopulation[i].getAllBoxes();
			int[] rotations=boxpopulation[i].getRotation();
			int[] orientation=boxpopulation[i].getOrientation();
			int[] x=boxpopulation[i].x;
			int[] y=boxpopulation[i].y;
			int[] z=boxpopulation[i].z;
		
			
			//for every chromosome 
			for(int j=0;j<Pieces.length;j++){
				//roll a 100 sided die and if its lower then five mutate that piece into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					Pieces[j]=rand.nextInt(3);
					rotations[j]=rand.nextInt(getMaxRotation(Pieces[j]));
				}
			
				//roll a 100 sided die and if its lower then five mutate that rotation into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					rotations[j]=rand.nextInt(getMaxRotation(boxpopulation[i].getAllBoxes()[j]));
				}
			
				//!!!!!!! be careful !!!!!! wss een out of index error
				//roll a 100 sided die and if its lower then five mutate that rotation into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					orientation[j]=rand.nextInt(3);
				}
			
				//roll a 100 sided die and if its lower then five mutate that piece into another one
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					x[j]=rand.nextInt(width);
				}
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					y[j]=rand.nextInt(height);
				}
				roll=rand.nextInt(100);
				if(roll<mutationRate){
					z[j]=rand.nextInt(length);
				}
			}

		}

	 }

	
}
