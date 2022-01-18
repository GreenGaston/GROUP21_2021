package com.example.Application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GivenCaseGAControllerPLT {
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

    //This method creates an array of piece id's to be used in the genetic algorithm
    public int[] createPieceArray(int L, int P, int T){
        ArrayList<Integer> piecesList = new ArrayList<>();

        for(int i = 0; i < L; i++){ //adding the pieceID: 1,  L times
            piecesList.add(1);
        }
        for(int j = 0; j < P; j++){//adding the pieceID: 9, P times
            piecesList.add(9);
        }
        for(int k = 0; k < T; k++){ //adding the pieceID: 3, T times
            piecesList.add(3);
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
                int L = Integer.valueOf(pieceL.getText());
                int P = Integer.valueOf(pieceP.getText());
                int T = Integer.valueOf(pieceT.getText());
                int[] pieces = createPieceArray(L, P, T);
                pieces1 = pieces;
                System.out.println(Arrays.toString(pieces1));
                boxType = false;

            //gets the inputs from the text-fields and stores them into temporary variables
            int generation = Integer.valueOf(generationField.getText());
            int mutationRate = Integer.valueOf(mutationRateField.getText());
            int pieceAmount = pieces1.length;
            int populationSize = Integer.valueOf(populationSizeField.getText());
            int selectionMethod = Integer.valueOf(selectionMethodField.getText());
            int tournamentSize = Integer.valueOf(tournamentSizeField.getText());

            System.out.println(boxType);
            GAMatrix = Given_Case_GA.GAmethod(pieces1, generation, mutationRate, populationSize, tournamentSize, selectionMethod, false); //uses the variables to call the GA method that uses a given case
            HelloController.testMatrix = GAMatrix; //stores the returned matrix
        }catch (NumberFormatException e){ //if input is incorrect throw exception
            System.out.println("Make sure every input is an Integer!");
        }
    }
}
