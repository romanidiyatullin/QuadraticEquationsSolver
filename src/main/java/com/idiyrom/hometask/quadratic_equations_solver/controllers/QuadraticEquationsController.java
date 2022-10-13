package com.idiyrom.hometask.quadratic_equations_solver.controllers;

import com.idiyrom.hometask.quadratic_equations_solver.component.QuadraticEquationsException;
import com.idiyrom.hometask.quadratic_equations_solver.component.QuadraticEquationsSolver;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

/*
    REST Controller with endpoint to receive quadratic equation as String via HTTP GET method
*/

@RestController
@Slf4j
@RequestMapping("${url.prefix}")
public class QuadraticEquationsController {

    @Autowired
    QuadraticEquationsSolver quadraticEquationsSolver;

    // Endpoint for HTTP GET method - get input params as string & return Solution object as JSON:
    @GetMapping("${url.get}/{equationString}")
    private QuadraticEquationsSolver.Solution resolveFromGet(@PathVariable String equationString){
        try {
                return quadraticEquationsSolver.initializeParamsFromString(equationString).solveQuadraticEquation();
            }
        catch (QuadraticEquationsException exception){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    // Endpoint for HTTP POST method - get input parameters in JSON format & return Solution object as JSON:
    @PostMapping("${url.post}")
    private QuadraticEquationsSolver.Solution resolveFromPost(@RequestBody @NotNull QuadraticEquationsSolver quadraticEquationsSolverFromPost){
        try {
            return quadraticEquationsSolverFromPost.solveQuadraticEquation();
        }
        catch(QuadraticEquationsException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }


    // Endpoint for HTTP POST method - get input parameters from file based on provided path to file:
    @PostMapping("${url.post.file}")
    private QuadraticEquationsSolver.Solution resolveFromFile(@RequestBody @NotNull Map<String, String> pathToFile){
        try {
            return quadraticEquationsSolver.initializeParamsFromFile(pathToFile.get("path")).solveQuadraticEquation();
        }
        catch (QuadraticEquationsException exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
