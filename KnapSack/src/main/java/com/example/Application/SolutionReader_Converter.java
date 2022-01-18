package com.example.Application;

import java.io.*;
import java.util.*;

public class SolutionReader_Converter {
    public static void main(String[] args) {
        // The int[] currentSizes contains the indexes of the sizes of the current solution
        int[] currentSizes = new int[3];
        currentSizes[0] = 5;
        currentSizes[1] = 8;
        currentSizes[2] = 3;

        // The int[] wantedSizes contains the indexes of the sizes of the wanted solution
        int[] wantedSizes = new int[3];
        wantedSizes[0] = 5;
        wantedSizes[1] = 8;
        wantedSizes[2] = 33;


        // Read the file containing the solution
        // Return solution as an int[]
        int[] solution1D = read(currentSizes);
        // Convert the int[] into an int[][][]
        // The sizes of the eventual solution can be changed inside of convert
        int[][][] solution3D = convert(solution1D, currentSizes, wantedSizes);
        // Save the 3D version of the solution
        // Write it into a txt file
        save(solution3D, currentSizes);
    }

    private static int[] read(int[] sizes){
        // Read the file with the saved solution in it
        // and store each row of the solution as string in a string arraylist
        ArrayList<String> stringSolutionRow = new ArrayList<>();
        try {
            File solution = new File("Algorithm_X_LPT_"+sizes[0]+sizes[1]+sizes[2]+".txt");
            Scanner myReader = new Scanner(solution);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                stringSolutionRow.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
        }

        // Convert the string arraylist into a string[][]
        String[][] stringSolution2D = new String[stringSolutionRow.size()][];
        for (int i = 0; i < stringSolutionRow.size(); i++) {
            stringSolution2D[i] = stringSolutionRow.get(i).split("\\s+");
        }

        // Convert the string[][] into an int[][]
        int[][] intSolution2D = new int[stringSolution2D.length][stringSolution2D[0].length];
        for (int i = 0; i < intSolution2D.length; i++) {
            for (int j = 0; j < intSolution2D[0].length; j++) {
                intSolution2D[i][j] = Integer.parseInt(stringSolution2D[i][j]);
            }
        }

        // Convert the int[][] into int[]
            // So this means, get rid of all the zero's
            // this only leaves a solution with all the pieces on the right place
        int[] intSolution1D = new int[intSolution2D[0].length];
        for (int j = 0; j < intSolution2D[0].length; j++) {
            for (int i = 0; i < intSolution2D.length; i++) {
                if (intSolution2D[i][j] > 0){
                    intSolution1D[j] = intSolution2D[i][j]; 
                }
            }
        }
        return intSolution1D;
    }

    private static int[][][] convert(int[] list, int[] currentSizes, int[] wantedSizes){
        // These are the sizes you want to end up with
        int columnAmount = wantedSizes[0];
        int rowAmount = wantedSizes[1];
        int layerAmount = wantedSizes[2];

        // These are the sizes you currently have
        // Current amount of columns
        int listSizeX = currentSizes[0];
        // Current amount of rows
        int listSizeY = currentSizes[1];
        // Current amount of layers
        int listSizeZ = currentSizes[2];
        
        print1DIntArray(list, "solutionpart");

        int[] partialSolution1D = new int[columnAmount*rowAmount*listSizeZ];
        // Go through it as many times as amount of layers the current solution contains
        for (int i = 0; i < listSizeZ; i++) { 
            // Go through the current solution as many times as the listSizeY fits in rowAmount
            for (int j = 0; j < rowAmount/listSizeY; j++) { 
                // Go through the amount of digits the current solution currently has on each layer
                for (int k = 0; k < columnAmount*listSizeY; k++) {
                    System.out.println(j*columnAmount+k);
                    partialSolution1D[i*columnAmount*rowAmount+j*columnAmount*listSizeY+k] = list[i*columnAmount+k];  
                }
                print1DIntArray(partialSolution1D, "extended solution part");
            }
        }

        print1DIntArray(partialSolution1D, "bigger solutionpart");

        int [] solution1D = new int[columnAmount*rowAmount*layerAmount];
        // Go through the solution as many times as listSizeZ fits in sizeZ
        for (int i = 0; i < layerAmount/listSizeZ; i++) {
            // Go through the solution
            for (int j = 0; j < partialSolution1D.length; j++) {
                solution1D[i*partialSolution1D.length+j] = partialSolution1D[j];
            }
        }

        print1DIntArray(solution1D, "solution");

        // Convert the int[] into an int[][][]
        int[][][] solution3D = new int[layerAmount][rowAmount][columnAmount];
        
        // Go through all layers
        for (int layer = 0; layer < solution3D.length; layer++) {
            // Go through all rows
            for (int row = 0; row < solution3D[0].length; row++) {
                // Go through all columns
                for (int col = 0; col < solution3D[0][0].length; col++) {
                    solution3D[layer][row][col] = solution1D[layer*rowAmount*columnAmount+row*columnAmount+col];
                    // System.out.print("index "+layer+","+row+","+col+" becomes: ");
                    // System.out.println(solution3D[layer][row][col]);
                }
            }
        }

        print3DIntArray(solution3D, "3D solution");
        return solution3D;
    }

    private static void save(int[][][] list, int[] sizes){
        try{
            FileWriter myWriter = new FileWriter("3D_int_"+sizes[0]+sizes[1]+sizes[2]+".txt");
            // This writes the complete 3D int version of the solution in a txt file

            myWriter.write("int[][][] solution3D = \n");

            for (int layer = 0; layer < list.length; layer++) {
                myWriter.write("    {\n");
                for (int row = 0; row < list[0].length; row++) {
                    myWriter.write("        {");
                    for (int col = 0; col < list[0][0].length; col++) {
                        myWriter.write(list[layer][row][col]+"");
                        if (col != list[0][0].length-1){
                            myWriter.write(",");
                        }
                    }
                    if (row != list[0].length-1){
                        myWriter.write("},\n");
                    }else{
                        myWriter.write("}\n");
                    }
                }
                if (layer != list.length-1){
                    myWriter.write("    },\n");
                }else{
                    myWriter.write("    };\n");
                }
            }
        
            myWriter.close();
            
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void print1DIntArray(int[] list, String titel) {
        System.out.println(titel);

        for (int i = 0; i < list.length; i++) {
            System.out.print(list[i]+" ");
        }
        System.out.println();
    }

    private static void print3DIntArray(int[][][] list, String titel){
        System.out.println(titel);

        System.out.println("int[][][] solution3D = ");
        for (int layer = 0; layer < list.length; layer++) {
            System.out.println("    {");
            for (int row = 0; row < list[0].length; row++) {
                System.out.print("        {");
                for (int col = 0; col < list[0][0].length; col++) {
                    System.out.print(list[layer][row][col]);
                    if (col != list[0][0].length-1){
                        System.out.print(",");
                    }
                }
                if (row != list[0].length-1){
                    System.out.println("},");
                }else{
                    System.out.println("}");
                }
            }
            if (layer != list.length-1){
                System.out.println("    },");
            }else{
                System.out.println("    };");
            }
        }
    }
}
