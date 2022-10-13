package com.idiyrom.hometask.quadratic_equations_solver.component;

/*
    Create our own Checked Exception type (draft) - for future, to make Exception mechanism more agile and helpful
 */
public class QuadraticEquationsException extends Exception{

    QuadraticEquationsException(String message){
        super(message);
    }
}
