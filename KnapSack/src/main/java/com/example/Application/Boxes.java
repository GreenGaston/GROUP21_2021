package com.example.Application;

public class Boxes {
    int[] All_Boxes;
	int score;
	int[] rotations;
	int[] orientations;

	//this file represents an instance of an individual in the population we use in the genetic algorithm
	
	/*I think the names of these methods are pretty self-explanatory.
	They come in handy in my GA file. */


	public Boxes(int[] All_Boxes, int[] rotations, int[] orientations) {
		this.All_Boxes = All_Boxes;
		this.rotations= rotations;
		this.orientations= orientations;
		this.score = 0;
		
	}

	public int[] getAllBoxes() {
		return All_Boxes;
	}

	public int[] getBoxes(Boxes box) {
		return All_Boxes;
	}

	public void setBoxes(int[] All_Boxes) {
		this.All_Boxes = All_Boxes;
	}

	public int[] getRotation(){
		return rotations;
	}

	public void setRotation(int[] rotations){
		this.rotations = rotations;
	}

	public int[] getOrientation(){
		return orientations;
	}

	public void setOrientation(int[] orientations){
		this.orientations = orientations;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
		
	}
		
	public String Description() {
		StringBuilder builder = new StringBuilder();
		builder.append(All_Boxes);
		return builder.toString();
	}
	
	public Boxes clone() {
		int[] BoxClone = new int[All_Boxes.length];
		int [] orientations = new int[All_Boxes.length];
		int [] rotations = new int[All_Boxes.length];

		for(int i = 0; i < BoxClone.length; i++) {
			BoxClone[i] = All_Boxes[i];
			orientations[i] = this.orientations[i];
			rotations[i] = this.rotations[i];
		}
		Boxes temp =new Boxes(BoxClone, rotations,orientations);
		temp.setScore(score);
		return temp;
	}
	
	// public  void  calculateScore() {
	// 	int current_score = 0;

	// 	int  Highest_score = 165;
		
	// 	for(int i = 0; i<Highest_score; i++){
	// 		if(Highest_score == All_Boxes[i]){
	// 			current_score++;
	// 		}
	// 	}
		
	// 	score = current_score;	
	// }

    

}
