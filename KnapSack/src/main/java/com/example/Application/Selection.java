package com.example.Application;
import java.util.Random;

public class Selection {
    //public static Boxes[] newPopulation = new Boxes[Population.length];
		
		//method 
    public static Boxes[] tempBoxes;
	public static Boxes parent1;
	public static Boxes parent2;
    public static int tournamentSize;
    public static Random rand=new Random();
    public static void setTournamentSize(int size){
        tempBoxes=new Boxes[size];
        tournamentSize=size;

    }
    //this file is used to make the selection a variable you can change based on an index
    //as seen below it will use a different selection on the population based on what int you gave it
    public static Boxes[] selectionMethod(Boxes[] population,int selectionmethod){
        if(selectionmethod==1){
            return Tournament(population);
        }
        if(selectionmethod==2){
            return Roulette(population);
        }
        else{
            return elitism(population);

        }
    }



    public static Boxes[] elitism(Boxes[] Population){
        return Population;
    }


    //tournament selection

    public static Boxes[] Tournament(Boxes[] Population){
        Boxes[]newPopulation = new Boxes[Population.length];
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
        return newPopulation;
    }


    //roulette selection

    public static Boxes[] Roulette(Boxes[] population){
        Boxes[] newPopulation=new Boxes[population.length];
        int totalScore=0;
        for (int i = 0; i < newPopulation.length; i++) {
            totalScore+=newPopulation[i].getScore();
            
        }
        int tempscore=0;
        int roulletteRoll=0;
        for (int i = 0; i < newPopulation.length; i++) {
            roulletteRoll=rand.nextInt(totalScore)-1;
            for (int j = 0; j < newPopulation.length; j++) {
                tempscore+=newPopulation[i].getScore();
                if(roulletteRoll<tempscore){
                    newPopulation[i]=population[j];
                    break;
                }
                
            }
            
        }
        return newPopulation;
    }

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

}
