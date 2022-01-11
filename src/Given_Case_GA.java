
    //package src;

import java.util.Random;




public class Given_Case_GA {
    
	static final int TARGET = 165;
	public static int pieceAmount = 400;
	public static int generation = 300;
	static int mutationRate = 5;
	public static int populationSize = 500;
	public static int tournamentSize=5;
	public static int[][][] answerGrid;
	public static int selectionType=1;
    public static Boolean ParcelUse=true;
	//selection type can be:
	//1 for tournament
	//2 for roulette
	//3 for elitism
	
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
				BoxPieces[k] = generator.nextInt(3);
				boxRotation[k] = generator.nextInt(4);
				boxOrientation[k] = generator.nextInt(3);
			} 
			boxPopulation[i] = new Boxes(BoxPieces, boxRotation, boxOrientation);
		}
		
		

		GeneticAlgorithm(boxPopulation, generation,selectionType);
	}

	public static int[][][] GAmethod(int[] Pieces,int _generations, int _mutationrate,int _populationsSize,int _TournamentSize,int selectionMethod,Boolean parcel){
		pieceAmount = Pieces.length;
		generation = _generations;
		mutationRate = _mutationrate;
		populationSize = _populationsSize;
		tournamentSize=_TournamentSize;
        selectionType=selectionMethod;
        ParcelUse=parcel;


		Random generator = new Random(System.currentTimeMillis());
		Boxes[] boxPopulation = new Boxes[populationSize];
		int[] boxOrientation = new int[pieceAmount];
		int[] boxRotation = new int[pieceAmount];
		int[] BoxPieces = Pieces;
		
		
		//initialize random boxes, rotation and orientation
		
		for (int i = 0; i < populationSize; i++) {
			for (int k = 0; k < pieceAmount; k++) {
				
				boxRotation[k] = generator.nextInt(4);
				boxOrientation[k] = generator.nextInt(3);
				

			} 
			boxPopulation[i] = new Boxes(BoxPieces, boxRotation, boxOrientation);
		}
		

		
		if(ParcelUse){

            GeneticAlgorithm(boxPopulation, generation,selectionMethod);
            return answerGrid;
        }
        else{
            GeneticAlgorithmPentominoes(boxPopulation, generation, selectionMethod);
            return answerGrid;
        }
        
			
	}
	
	

	//sorting based on score
	public static void sortBoxes(Boxes[] population){
        
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

	public static void GeneticAlgorithm(Boxes [] Population, int generations,int selectionMethod){
		//put the method here in setboxes

		
		
		Boxes[] newPopulation = new Boxes[Population.length];
	
		Selection.setTournamentSize(tournamentSize);
		
		for (int j = 0; j < generations; j++) {
			AIJudgeParcels.scoring(Population);
			GenerationSelector.setPopulation(Population);
			
			newPopulation=Selection.selectionMethod(Population,selectionMethod);


			mutation(newPopulation);
			Population = newPopulation;
		}

		
		sortBoxes(Population);
		System.out.println("Generation:" + generation + "\nScore:" + Population[populationSize-1].getScore()+ "\n\n\n");
		answerGrid=AIJudgeParcels.getMatrix(Population[populationSize-1]);

	

	}
    public static void GeneticAlgorithmPentominoes(Boxes [] Population, int generations,int selectionMethod){

		Boxes[] newPopulation = new Boxes[Population.length];
	
		Selection.setTournamentSize(tournamentSize);
		
		for (int j = 0; j < generations; j++) {
			AIJudgepentominoes.scoring(Population);
			GenerationSelector.setPopulation(Population);
			
			newPopulation=Selection.selectionMethod(Population,selectionMethod);


			mutation(newPopulation);
			Population = newPopulation;
		}

		
		sortBoxes(Population);
		System.out.println("Generation:" + generation + "\nScore:" + Population[populationSize-1].getScore()+ "\n\n\n");
		answerGrid=AIJudgeParcels.getMatrix(Population[populationSize-1]);

	

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

    }

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
			}
            roll=rand.nextInt(100);
            if(roll<mutationRate){
                int Index=rand.nextInt(chromosomes.length);
                int Index2=rand.nextInt(chromosomes.length);
                int temp=chromosomes[Index];
                chromosomes[Index]=chromosomes[Index2];
                chromosomes[Index2]=temp;
            }



		}

	 }

	
}

    

