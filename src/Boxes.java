package src;

public class Boxes {
    int[] All_Boxes;
	int score;
	
	/*I think the names of these methods are pretty self-explanatory.
	They come in handy in my GA file. */
	public Boxes(int[] All_Boxes) {
		this.All_Boxes = All_Boxes;
		this.score = 0;
		calculateScore();
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
		for(int i = 0; i < BoxClone.length; i++) {
			BoxClone[i] = All_Boxes[i];
		}
		return new Boxes(BoxClone);
	}
	
	public  void  calculateScore() {
		int current_score = 0;

		int [] Highest_score = {1,0,1};
		
		for(int i = 0; i<Highest_score.length; i++){
			if(Highest_score[i] == All_Boxes[i]){
				current_score++;
			}
		}

		score = current_score;	
	}

    

}
