package com.example.Application;

public class ColNode extends MemberNode { // subclass of Node, for every column of the linked list, there is a
    // columnNode that contains identifying info about that specific column as well
    // as the size of the column ( number of Nodes in it)

    // Compare to hungergames: ColNode is Joanna. She is a team member, but she is
    // closer to Finnick than to Katniss

    // Each Node points to four other nodes and its columnNode
    int limitation = -1; 
    int num = -1;
    int position = -1;

    int size = 0;

}