package com.example.Application;

import javafx.scene.control.*;
import javafx.fxml.FXML;

import java.util.Arrays;

//Controller used for the GAV2 menu
public class GAMenuController {
    @FXML
    private TextField generationField;

    @FXML
    private TextField mutationRateField;

    @FXML
    private TextField pieceAmountField;

    @FXML
    private TextField populationSizeField;

    @FXML
    private Button submitButton;

    @FXML
    private TextField tournamentSizeField;

    public static int[][][] GAMatrix;

    public static boolean PLT;

    public static void setTrue(){
        PLT = true;
    } //method is called when the parcels are boxes

    public static void setFalse(){
        PLT = false;
    } //method is called when the parcels are PLT pieces

    public void onButtonClick(){
        try{
            //gets the inputs from the text-fields and stores them into temporary variables
            int generation = Integer.valueOf(generationField.getText());
            int mutationRate = Integer.valueOf(mutationRateField.getText());
            int pieceAmount = Integer.valueOf(pieceAmountField.getText());
            int populationSize = Integer.valueOf(populationSizeField.getText());
            int tournamentSize = Integer.valueOf(tournamentSizeField.getText());


            if(PLT){

                GAMatrix = PentominoGAV2.GAmethod(pieceAmount, generation, mutationRate, populationSize, tournamentSize); // uses the variables to call the GA pentomino method
                HelloController.testMatrix = GAMatrix; //sets the matrix that is used for the drawing of the group, to the matrix the genetic algorithm outputted

            }else{
                GAMatrix = GAV2.GAmethod(pieceAmount, generation, mutationRate, populationSize, tournamentSize); //uses the variables to call the GA method
                HelloController.testMatrix = GAMatrix; //sets the matrix that is used for the drawing of the group, to the matrix the genetic algorithm outputted
            }
            HelloController.testMatrix = GAMatrix; //sets the matrix that is used for the drawing of the group, to the matrix the genetic algorithm outputted

            System.out.println(generation);
        }catch (NumberFormatException e){
            System.out.println("Make sure every input is an Integer!");
        }
    }
}
