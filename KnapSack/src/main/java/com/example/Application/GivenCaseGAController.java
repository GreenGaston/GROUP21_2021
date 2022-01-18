package com.example.Application;

import javafx.scene.control.*;
import javafx.fxml.FXML;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

//Controller used for the given case menu fxml file
public class GivenCaseGAController
{
    public static boolean boxType = false;
    @FXML
    private TextField generationField;

    @FXML
    private TextField mutationRateField;

    @FXML
    private TextField populationSizeField;

    @FXML
    private TextField selectionMethodField;

    @FXML
    private TextField pieceL;

    @FXML
    private TextField pieceP;

    @FXML
    private TextField pieceT;

    @FXML
    private TextField tournamentSizeField;

    public static int[][][] GAMatrix;

    //This method creates an array of parcel id's to be used in the genetic algorithm
    public int[] createPieceArrayParcel(int L, int P, int T){
        Random random = new Random();
        ArrayList<Integer> piecesList = new ArrayList<>();

        for(int i = 0; i < L; i++){ //adding the pieceID: 1,  L times
            piecesList.add(random.nextInt(2));
        }
        for(int j = 0; j < P; j++){//adding the pieceID: 9, P times
            piecesList.add(random.nextInt(5) + 3);
        }
        for(int k = 0; k < T; k++){ //adding the pieceID: 3, T times
            piecesList.add(9);
        }

        int[] pieces = new int[piecesList.size()];
        for(int i = 0; i < piecesList.size(); i++){
            pieces[i]= piecesList.get(i);
        }
        return pieces;
    }


    public void onButtonClick(){ // If the submit button has been pressed this method is called
        try{
            int[] pieces1;

                System.out.print(boxType);
                int box1 = Integer.valueOf(pieceL.getText());
                int box2 = Integer.valueOf(pieceP.getText());
                int box3 = Integer.valueOf(pieceT.getText());
                System.out.println("Bug 1");
                int[] pieces = createPieceArrayParcel(box1, box2, box3);

                pieces1 = pieces;
                boxType = true;


            //gets the inputs from the text-fields and stores them into temporary variables
            int generation = Integer.valueOf(generationField.getText());
            int mutationRate = Integer.valueOf(mutationRateField.getText());
            int pieceAmount = pieces1.length;
            int populationSize = Integer.valueOf(populationSizeField.getText());
            int selectionMethod = Integer.valueOf(selectionMethodField.getText());
            int tournamentSize = Integer.valueOf(tournamentSizeField.getText());

            GAMatrix = Given_Case_GA.GAmethod(pieces1, generation, mutationRate, populationSize, tournamentSize, selectionMethod, true); //uses the variables to call the GA method that uses a given case

            HelloController.testMatrix = GAMatrix; //stores the returned matrix
        }catch (NumberFormatException e){ //if input is incorrect throw exception
            System.out.println("Make sure every input is an Integer!");
        }
    }
}
