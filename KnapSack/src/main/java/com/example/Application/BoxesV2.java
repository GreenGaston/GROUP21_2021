package com.example.Application;

public class BoxesV2 {
    public int[] Pieces;
	public int score;
	public int[] rotations;
	public int[] orientations;
	public int[] x;
	public int[] y;	
	public int[] z;


	//this file represents an instance of an individual in the population we use in the genetic algorithm
	//differnce with Boxes is this file contains coordinates for every piece
	
	/*I think the names of these methods are pretty self-explanatory.
	They come in handy in my GA file. */
	public BoxesV2(int[] All_Boxes, int[] rotations, int[] orientations,int[] x, int[] y, int[] z) {
		this.Pieces = All_Boxes;
		this.rotations= rotations;
		this.orientations= orientations;
		this.x=x;
		this.y=y;
		this.z=z;
		this.score = 0;
		
	}

	public int[] getAllBoxes() {
		return Pieces;
	}

	public int[] getBoxes(Boxes box) {
		return Pieces;
	}

	public void setBoxes(int[] All_Boxes) {
		this.Pieces = All_Boxes;
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
		builder.append(Pieces);
		return builder.toString();
	}
	
	public BoxesV2 clone() {
		int[] BoxClone = new int[Pieces.length];
		int [] orientations = new int[Pieces.length];
		int [] rotations = new int[Pieces.length];
		int[] x= new int[Pieces.length];
		int[] y= new int[Pieces.length];
		int[] z= new int[Pieces.length];

		for(int i = 0; i < BoxClone.length; i++) {
			BoxClone[i] = Pieces[i];
			orientations[i] = this.orientations[i];
			rotations[i] = this.rotations[i];
			x[i]=this.x[i];
			y[i]=this.y[i];
			z[i]=this.z[i];
		}
		BoxesV2 temp =new BoxesV2(BoxClone, rotations,orientations,x,y,z);
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
