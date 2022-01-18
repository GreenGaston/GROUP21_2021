package com.example.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AGXController {
    @FXML
    private ChoiceBox<String> Score;

    private boolean once = false;

    public void onStart(){
        if(!once) {
            //Score.setValue("Score");//set initial value to Score
            //fills the choicebox so that you can select the grid that matches with the score
            ObservableList<String> algorithmMenu = FXCollections.observableArrayList(); //Creating a list to be added to the Score combobox
            //adding all the scores to the list
            algorithmMenu.add("x: 5 y: 8 z: 3");
            algorithmMenu.add("x: 5 y: 8 z: 1");
            algorithmMenu.add("x: 5 y: 4 z: 3");
            algorithmMenu.add("x: 5 y: 4 z: 1");
            algorithmMenu.add("x: 5 y: 2 z: 3");
            algorithmMenu.add("x: 5 y: 1  z:3");
            Score.setItems(algorithmMenu);//adding list to the choiceBox
            once = true;
        }
    }
    public void onClick(){
        //Loading the correct matrix
        String item = Score.getValue();

        if(item == "x: 5 y: 8 z: 3"){
            HelloController.testMatrix = AGXDB.solution3D583;
        }else if(item == "x: 5 y: 8 z: 1"){
            HelloController.testMatrix = AGXDB.solution3D581;
        }else if(item == "x: 5 y: 4 z: 3"){
            HelloController.testMatrix = AGXDB.solution3D543;
        }else if(item == "x: 5 y: 4 z: 1"){
            HelloController.testMatrix = AGXDB.solution3D541;
        }else if(item == "x: 5 y: 2 z: 3"){
            HelloController.testMatrix = AGXDB.solution3D523;
        }else if(item == "x: 5 y: 1  z:3"){
            HelloController.testMatrix = AGXDB.solution3D513;
        }else{

        }
    }
}
