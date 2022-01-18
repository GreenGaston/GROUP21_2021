package com.example.Application;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        ArrayList<Integer> temp=new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> lijst=new ArrayList<ArrayList<Integer>>();
        int[][] matrix={{1,0,0,1,0,0,1},{1,0,0,1,0,0,0},{0,0,0,1,1,0,1},{0,0,1,0,1,1,0},{0,1,1,0,0,1,1},{0,1,0,0,0,0,1}};
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){

                temp.add(matrix[i][j]);
                }
            lijst.add(temp);
            temp=new ArrayList<Integer>();
            }
            
         }
    
}
