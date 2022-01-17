package com.example.Application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

public class HelloController {
    private static boxGroup boxes;
    private static boolean start = false;

    public static int[][][] testMatrix = new int[15][4][4];
    private static double mouseStartX, mouseStartY;
    private static double currentX = 0;
    private static double currentY = 0;

    private Camera camera;

    private final DoubleProperty angleAxisX = new SimpleDoubleProperty(0);
    private final DoubleProperty angleAxisY = new SimpleDoubleProperty(0);

    private final static int HEIGHT = 400;
    private final static int WIDTH = 400;

    private static int CUBEWIDTH = 10;
    private static int CUBEHEIGHT = 10;
    private static int CUBEDEPTH = 10;

    private final static int startX = (WIDTH/2) - (testMatrix[0][0].length/2)*CUBEWIDTH + CUBEWIDTH;
    private final static int startY = (HEIGHT/2) - (testMatrix[0].length/2)*CUBEHEIGHT;

    private static PhongMaterial boxColor;

    //This method returns a box that has the correct dimensions and color according to it's id
    private static Box buildBox(int colorID) {
        Color fill = GetColorOfID(colorID); //retrieves the color that matches with the id
        boxColor = new PhongMaterial(fill);//Sets the color according to its id
        Box cube = new Box(); //creates a new box
        cube.setMaterial(boxColor); //Colors the box

        //setting the dimensions of the box
        cube.setHeight(CUBEHEIGHT);
        cube.setWidth(CUBEWIDTH);
        cube.setDepth(CUBEDEPTH);

        return cube;
    }

    //this class is used to build the grid lines
    class gridLine extends Line{
         gridLine(boolean back){ //if the boolean is true, that means the line needs to be translated to the back of boxes group
            this.setStartX(0);
            this.setStartY(0);
            if(back){
                this.getTransforms().add(new Translate(0, 0, testMatrix.length*CUBEDEPTH));
            }
            this.setFill(Color.RED);
        }
        void setCoordinatesX(){
            this.setStartX(-CUBEWIDTH/2);
            this.setEndX(testMatrix[0][0].length * CUBEWIDTH -CUBEWIDTH/2);
            this.setStartY(-CUBEHEIGHT/2);
            this.setEndY(-CUBEHEIGHT/2);
            this.translateZProperty().set(-CUBEHEIGHT/2);
        }
        void setCoordinatesY(){
            this.setStartX(-CUBEWIDTH/2);
            this.setEndX(-CUBEWIDTH/2);
            this.setStartY(-CUBEHEIGHT/2);
            this.setEndY(testMatrix[0].length * CUBEHEIGHT - CUBEHEIGHT/2);
            this.translateZProperty().set(-CUBEHEIGHT/2);
        }

        void setCoordinatesXEnd(){
            this.setStartX(-CUBEWIDTH/2);
            this.setEndX(testMatrix[0][0].length * CUBEWIDTH -CUBEWIDTH/2);
            this.setStartY(-CUBEHEIGHT/2 + CUBEHEIGHT*testMatrix[0].length);
            this.setEndY(-CUBEHEIGHT/2 + CUBEHEIGHT*testMatrix[0].length);
            this.translateZProperty().set(-CUBEHEIGHT/2);
        }
        void setCoordinatesYEnd(){
            this.setStartX(-CUBEWIDTH/2 + CUBEWIDTH*testMatrix[0][0].length);
            this.setEndX(-CUBEWIDTH/2 + CUBEWIDTH*testMatrix[0][0].length);
            this.setStartY(-CUBEHEIGHT/2);
            this.setEndY(testMatrix[0].length * CUBEHEIGHT - CUBEHEIGHT/2);
            this.translateZProperty().set(-CUBEHEIGHT/2);
        }
    }

    class boxGroup extends Group{
        Rotate r;
        Transform t = new Rotate();

        //when this method is called the whole group is rotated 'angle' degrees on the x axis
        void rotateByX(int angle){
            r = new Rotate(angle, Rotate.X_AXIS);
            t.createConcatenation(r);
            this.getTransforms().add(r);

        }
        //when this method is called the whole group is rotated 'angle' degrees on the y axis
        void rotateByY(int angle){
            r = new Rotate(angle, Rotate.Y_AXIS);
            t.createConcatenation(r);
            this.getTransforms().add(r);
        }
    }

    //this method fills the grid with -1
    public void emptyGrid(int[][][] testMatrix){
        for(int k = 0; k < testMatrix.length; k++){ //loops through the z axis
            for(int j = 0; j < testMatrix[k].length; j++){ //loops through the y axis
                for(int i = 0;i < testMatrix[k][j].length; i++){//loops through the x axis
                    if(testMatrix[k][j][i] == -1){
                    }else{
                       testMatrix[k][j][i] = -1;

                    }
                }
            }
        }
    }

    //this method is used to draw lines around the boxes group to create one big transparent cube
    public void drawGrid(){

        //the following 4 lines are the square that is drawn in the front
        //if the boolean value is set to true, the lines translate backwards so that only the connecting lines in between the rectangles need to be drawn
        gridLine line1 = new gridLine(false);
        line1.setCoordinatesX();

        gridLine line2 = new gridLine(false);
        line2.setCoordinatesY();

        gridLine line3 = new gridLine(false);
        line3.setCoordinatesXEnd();

        gridLine line4 = new gridLine(false);
        line4.setCoordinatesYEnd();

        //adding the lines to boxes group
        boxes.getChildren().add(line1);
        boxes.getChildren().add(line2);
        boxes.getChildren().add(line3);
        boxes.getChildren().add(line4);

        //These are the lines that are drawn at the rear of the cube
         line1 = new gridLine(true);
        line1.setCoordinatesX();

         line2 = new gridLine(true);
        line2.setCoordinatesY();

         line3 = new gridLine(true);
        line3.setCoordinatesXEnd();

         line4 = new gridLine(true);
        line4.setCoordinatesYEnd();


        //the following 4 connection lines connect the square in the front to the back

        //the connection line for the top left
        Line connection1 = new Line();
        connection1.setStartX(-CUBEWIDTH/2);
        connection1.translateXProperty().set(-CUBEWIDTH/2);
        connection1.setEndX(testMatrix.length * CUBEDEPTH - CUBEDEPTH/2);
        connection1.setStartY(-CUBEHEIGHT/2);
        connection1.setEndY(-CUBEHEIGHT/2);
        connection1.getTransforms().add(new Rotate(-90, Rotate.Y_AXIS));

        //the connection line for the top right
        int offset = CUBEWIDTH*testMatrix[0][0].length;
        Line connection2 = new Line();
        connection2.setStartX(-CUBEWIDTH/2 );
        connection2.translateXProperty().set(-CUBEWIDTH/2 + offset);
        connection2.setEndX(testMatrix.length * CUBEDEPTH - CUBEDEPTH/2);
        connection2.setStartY(-CUBEHEIGHT/2);
        connection2.setEndY(-CUBEHEIGHT/2);
        connection2.getTransforms().add(new Rotate(-90, Rotate.Y_AXIS));

        //the connection line for the bottom left
        int offsety = CUBEHEIGHT*testMatrix[0].length;
        Line connection3 = new Line();
        connection3.setStartX(-CUBEWIDTH/2);
        connection3.translateXProperty().set(-CUBEWIDTH/2);
        connection3.setEndX(testMatrix.length * CUBEDEPTH - CUBEDEPTH/2);
        connection3.setStartY(-CUBEHEIGHT/2 + offsety);
        connection3.setEndY(-CUBEHEIGHT/2 + offsety);
        connection3.getTransforms().add(new Rotate(-90, Rotate.Y_AXIS));

        //the connection line for the bottom right
        Line connection4 = new Line();
        connection4.setStartX(-CUBEWIDTH/2 );
        connection4.translateXProperty().set(-CUBEWIDTH/2 + offset);
        connection4.setEndX(testMatrix.length * CUBEDEPTH - CUBEDEPTH/2);
        connection4.setStartY(-CUBEHEIGHT/2 + offsety);
        connection4.setEndY(-CUBEHEIGHT/2 + offsety);
        connection4.getTransforms().add(new Rotate(-90, Rotate.Y_AXIS));

        //adding the connection lines and rectangle to the boxes group
        boxes.getChildren().add(line1);
        boxes.getChildren().add(line2);
        boxes.getChildren().add(line3);
        boxes.getChildren().add(line4);
        boxes.getChildren().add(connection1);
        boxes.getChildren().add(connection2);
        boxes.getChildren().add(connection3);
        boxes.getChildren().add(connection4);
    }

    //This method builds a group filled with boxes based on a 3D matrix filled with colorID's
    public void drawBox(int[][][] testMatrix){
        boxes = new boxGroup();//Creating a group where the individual cubes are added to

        Box cube;
        for(int k = 0; k < testMatrix.length; k++){ //loops through the z axis
            for(int j = 0; j < testMatrix[k].length; j++){ //loops through the y axis
                for(int i = 0;i < testMatrix[k][j].length; i++){//loops through the x axis
                    if(testMatrix[k][j][i] == -1){
                    }else{


                        cube = buildBox(testMatrix[k][j][i]); //building a cube with correct dimensions and color
                        cube.setLayoutX(CUBEWIDTH*i); //setting the x coordinate of the cube
                        cube.setLayoutY(j*CUBEHEIGHT); //setting the y coordinate of the cube
                        cube.setTranslateZ(k * CUBEDEPTH); //setting the z coordinate of the cube
                        boxes.getChildren().add(cube); //adding the cube to the group

                    }
                }
            }
        }
        AmbientLight light = new AmbientLight(Color.WHITE);
        boxes.getChildren().add(light);
        boxes.setLayoutX(startX); //setting the x coordinate for the group
        boxes.setLayoutY(startY); //setting the y coordinate for the group
        drawGrid(); //method that draws the grid and adds it to the group
    }

    //the method that gives a returns a color for a certain ID
    public static boolean colorBlind = false;
    public static Color GetColorOfID(int i) { // create the colorblind colors
        if (!colorBlind) {
            if (i == 0) {
                return Color.MAGENTA.brighter();
            } else if (i == 1) {
                return Color.BLUE.darker();
            } else if (i == 2) {
                return Color.CYAN;
            } else if (i == 3) {
                return Color.GREEN;
            } else if (i == 4) {
                return Color.RED.darker();
            } else if (i == 5) {
                return Color.PINK;
            } else if (i == 6) {
                return Color.RED;
            } else if (i == 7) {
                return Color.YELLOW;
            } else if (i == 8) {
                return Color.rgb(123, 40, 49);
            } else if (i == 9) {
                return  Color.rgb(0, 0, 100);
            } else if (i == 10) {
                return Color.rgb(100, 0, 0);
            } else if (i == 11) {
                return  Color.rgb(0, 100, 0);
            } else if (i == 12) {
                return Color.rgb(128, 128, 128);
            } else {
                return Color.BLACK;
            }
        } else { // create the colors for the normal mode
            if (i == 0) {
                return Color.web("#ff0000");
            } else if (i == 1) {
                return Color.web("#00ff00");
            } else if (i == 2) {
                return Color.web("#1bbf1b");
            } else if (i == 3) {
                return Color.web("#ff00ff");
            } else if (i == 4) {
                return Color.web("#ffff00");
            } else if (i == 5) {
                return Color.web("#ffffff");
            } else if (i == 6) {
                return Color.web("#ff8000");
            } else if (i == 7) {
                return Color.web("#e3e3ff");
            } else if (i == 8) {
                return Color.web("#adadad");
            } else if (i == 9) {
                return Color.web("#ff009d");
            } else if (i == 10) {
                return Color.web("#ff7373");
            } else if (i == 11) {
                return Color.web("#fc99ff");
            } else if (i == 12) {
                return  Color.rgb(128, 128, 128);
            } else {
                return Color.BLACK;
            }
        }
    }

    private void mouseController(boxGroup group, SubScene scene) {
        Rotate rotationX; //the rotation for the x-axis
        Rotate rotationY; //the rotation for the y-axis

        group.getTransforms().addAll(
                rotationX = new Rotate(0, new Point3D(0, (testMatrix[0].length*CUBEHEIGHT), 0)), //sets the rotation axis on the x-axis
                rotationY = new Rotate(0, new Point3D(testMatrix[0][0].length*CUBEWIDTH, 0, 0)) //sets the rotation axis on the y-axis
        );

        rotationX.setPivotX(testMatrix[0][0].length*CUBEWIDTH/2 + CUBEWIDTH/2); //setting the pivot point for the x-axis
        rotationY.setPivotY(testMatrix[0].length*CUBEHEIGHT/2 + CUBEHEIGHT/2);  //setting the pivot point for the y-axis
        rotationX.setPivotZ(testMatrix.length * CUBEDEPTH/2 + CUBEDEPTH/2); //setting the pivot point for the z-axis
        rotationY.setPivotZ(testMatrix.length * CUBEDEPTH/2 + CUBEDEPTH/2); //setting the pivot point for the z-axis

        rotationX.angleProperty().bind(angleAxisX); //the x-axis angle on which the group is rotated
        rotationY.angleProperty().bind(angleAxisY); //the y-axis angle on which the group is rotated

        scene.setOnMousePressed(mouseEvent -> {
            mouseStartX = mouseEvent.getSceneX(); //stores the starting x point
            mouseStartY = mouseEvent.getSceneY(); //stores the starting y point

            currentX = angleAxisX.get(); //stores the end x point
            currentY = angleAxisY.get(); //stores the end y point

            scene.requestFocus();
        });

        scene.setOnMouseDragged(mouseEvent -> {
            angleAxisX.set(currentX + (mouseStartX - mouseEvent.getSceneX())); //updates the angle for the x-axis based on the difference between the start x and start x
            angleAxisY.set(currentY + mouseStartY - mouseEvent.getSceneY()); //updates the angle for the y-axis based on the difference between the start y and start y
        });

        //the event handler that makes zooming in and out possible
        scene.addEventHandler(KeyEvent.ANY, event -> {//adding event handler to the scene
            switch (event.getCode()){
                case UP:
                    group.translateZProperty().set(group.getTranslateZ()-10); //moves the group filled with boxes away from the camera
                    break;
                case DOWN:
                    group.translateZProperty().set(group.getTranslateZ() + 10); //moves the group filled with boxes to the camera
                    break;
                case SPACE:
                    angleAxisX.set(0); //sets the x rotation to 0
                    angleAxisY.set(0); //sets the y rotation to 0
                    break;
            }
        });
    }
    @FXML
    private SubScene ViewerSubScene; //sub scene used for showing the cube group

    @FXML
    private ComboBox<String> AlgorithmSelection; //combo box used for choosing the algorithm

    public static boolean once;

    @FXML
    //method that to adds all the items to the combo boxes
    public void onStart(){
        if(once){

        }
        else{
            if(!start){

                emptyGrid(testMatrix);
                start = true;
            }
            ViewerSubScene.setFill(Color.DARKGRAY);
            ObservableList<String> algorithmMenu = FXCollections.observableArrayList("Algorithm X");
            algorithmMenu.add("Tile solver");
            algorithmMenu.add("Genetic algorithm");
            algorithmMenu.add("Genetic algorithm V2");
            algorithmMenu.add("Genetic algorithm given case");
            AlgorithmSelection.setValue("Pick algorithm");
            AlgorithmSelection.setItems(algorithmMenu);
            once = true;
        }

    }

    @FXML
    private Menu ColorMenu;

    @FXML
    private MenuItem CBRegular;

    @FXML
    private MenuItem CBTrue;

    @FXML
    private CheckBox PLTSelector;

    public void isColorBlind(){
        colorBlind = true; //if the colorBlind boolean is set to true,  use the colorblind colors
        launch3DView(); //repaint the grid
    }

    public void isNotColorBlind(){
        colorBlind = false; //if the colorBlind boolean is set to false, don't use the colorblind colors
        launch3DView(); //repaint the grid
    }

    public void onPLT(){
        if(PLTSelector.isSelected()){ //checks if the PLT package box is selected and loads a plt field or box field dependent on isSelected boolean
            GAController.setTrue();

        }else{
            GAController.setFalse();

        }
        pickAG();
    }

    //method that checks which algorithm is selected through the algorithm selection Combobox
    public void pickAG(){
        String item = AlgorithmSelection.getValue();
        if(item == "Genetic algorithm"){ //if the selected item equals Genetic algorithm then load the genetic algorithm
            if(PLTSelector.isSelected()){ //checks if the PLT package box is selected and loads a plt field or box field dependent on isSelected boolean
                GAController.setTrue();
            }else{
                GAController.setFalse();
            }
            loadGA();
        }
        else if(item == "Algorithm X"){ //if the selected item equals Algorithm X then load Algorithm X
            loadAgX();
            testMatrix = AGXDB.solution3D583;
        }
        else if(item == "Genetic algorithm given case"){//if the selected item equals Genetic algorithm that uses a given case then load that algorithm
            if(PLTSelector.isSelected()){ //checks if the PLT package box is selected and loads a plt field or box field dependent on isSelected boolean
                //GivenCaseGAController.setFalse();
                loadGAGC();
            }else{
                //GivenCaseGAController.setTrue();
                loadGAGCB();
            }
        }
        else if(item == "Genetic algorithm V2"){
            if(PLTSelector.isSelected()){ //checks if the PLT package box is selected and loads a plt field or box field dependent on isSelected boolean
                GAMenuController.setTrue();
            }else{
                GAMenuController.setFalse();
            }
            loadGAV2();

        }
        else if(item == "Tile solver"){//if the selected item equals Tile solver then load the tile solver
            loadTileSolver();
        }
        else{
        }
    }

    @FXML
    private BorderPane selectionPane; //the selection pane is used to display the menus ie fxml files for the different algorithms

    // this method uses the PentominoSolver to fill a 3D int array with the id's of the pieces that filled the grid
    public void loadTileSolver(){
        testMatrix = PentominoSolver.solve3dint(5, 8, 33); //the testMatrix which is used in the drawBox method and drawGrid method is set equal to the result of the Pentominosolver
        launch3DView(); //after the testMatrix is updated the launch3D method is called and the ViewerSubScene is updated
    }

    //this method loads the pentomino genetic algorithm version 2
    public void loadPGAV2(){

    }

    //this method loads the pentomino genetic algorithm
    public void loadPGA(){

    }

    // this method loads the algorithm X menu (fxml file) into the selection pane
    public void loadAgX(){
        selectionPane.setCenter(null);
        selectionLoader AGX = new selectionLoader();
        Pane set = AGX.setSelectionPaneAGX();
        selectionPane.setCenter(set);
        System.out.println("Ag x");
    }


    //This method loads the genetic algorithm menu into the selection pane
    public void loadGA(){
        selectionPane.setCenter(null);
        selectionLoader GA = new selectionLoader();
        Pane set = GA.setSelectionPaneGA();
        selectionPane.setCenter(set);
        launch3DView();
    }

    //this method loads the genetec algorithm version 2 menu in the selection pane
    public void loadGAV2(){
        selectionPane.setCenter(null);
        selectionLoader GA = new selectionLoader();
        Pane set = GA.setSelectionPaneGAV2();
        selectionPane.setCenter(set);
        launch3DView();
    }

    //this method loads the Genetic algorithm menu that uses a given amount of boxes
    public void loadGAGCB(){
        selectionPane.setCenter(null);
        selectionLoader GA = new selectionLoader();
        Pane set = GA.setSelectionPaneGAGCBOX();
        selectionPane.setCenter(set);
       launch3DView();
    }

    //this method loads the Genetic algorithm menu that uses a given amount of PLT parcels
    public void loadGAGC(){
        selectionPane.setCenter(null);
        selectionLoader GA = new selectionLoader();
        Pane set = GA.setSelectionPaneGAGC();
        selectionPane.setCenter(set);
        launch3DView();
    }

    @FXML
    private VBox SceneVBOX; //The Vbox that contains the subscene

    public void launch3DView(){

        camera = new PerspectiveCamera(); //creates a new camera

        camera.setLayoutX(testMatrix[0][0].length*CUBEWIDTH/2 - CUBEWIDTH/2); //sets the x location of the camera to the center of the sub scene
        camera.setLayoutY(testMatrix[0].length*CUBEHEIGHT/2 + CUBEHEIGHT/2); //sets the y location of the camera to the center of the sub scene


        drawBox(testMatrix); //draws the matrix that needs to be shown
        drawGrid();//draws the grid around the group
        ViewerSubScene.setRoot(boxes); //adds the group of boxes to the sub scene
        ViewerSubScene.setFill(Color.DARKGRAY); //sets the background of the subscene  to grey

        camera.translateZProperty().set(-100); //moves the camera back

        ViewerSubScene.setCamera(camera); //adds the camera to the sus scene
        mouseController(boxes, ViewerSubScene); //adds the mouse controller to the scene, so that you can rotate the boxes group
    }
}


//This class is used to load fxml files into the selectionPane, the fxml files are the menus for the different algorithm's
class selectionLoader{
    private Pane selectionPane;

    public Pane setSelectionPaneGA(){
        try{
            selectionPane = new Pane();
            selectionPane = FXMLLoader.load(getClass().getResource("GA.fxml"));

        } catch (Exception e){
            System.out.println("Incorrect file path!");
        }
        return selectionPane;
    }
    public Pane setSelectionPaneGAV2(){
        try{
            selectionPane = new Pane();
            selectionPane = FXMLLoader.load(getClass().getResource("GAV2.fxml"));

        } catch (Exception e){
            System.out.println("Incorrect file path!");
        }
        return selectionPane;
    }
    public Pane setSelectionPaneAGX(){
        try{
            selectionPane = new Pane();
            selectionPane = FXMLLoader.load(getClass().getResource("AGXFXML.fxml"));
        } catch (Exception e){
            System.out.println("Incorrect file path!");
        }
        return selectionPane;
    }

    public Pane setSelectionPaneGAGC(){
        try{
            selectionPane = new Pane();
            selectionPane = FXMLLoader.load(getClass().getResource("Given_Case_GAPLT.fxml"));
        } catch (Exception e){
            System.out.println("Incorrect file path!");
        }
        return selectionPane;
    }

    public Pane setSelectionPaneGAGCBOX(){
        try{
            selectionPane = new Pane();
            selectionPane = FXMLLoader.load(getClass().getResource("Given_Case_GAB.fxml"));
        } catch (Exception e){
            System.out.println("Incorrect file path!");
        }
        return selectionPane;
    }

    public Pane setSelectionPaneP3D(){
        try{
            selectionPane = new Pane();
            selectionPane = FXMLLoader.load(getClass().getResource("P3DFXML.fxml"));
        } catch (Exception e){
            System.out.println("Incorrect file path!");
        }
        return selectionPane;
    }
}