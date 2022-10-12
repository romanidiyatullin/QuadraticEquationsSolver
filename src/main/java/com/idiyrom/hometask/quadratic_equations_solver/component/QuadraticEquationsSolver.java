package com.idiyrom.hometask.quadratic_equations_solver.component;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/*
Class to solve quadratic equations.

    Quadratic equations usually have 2 roots (solutions).
    Quadratic equations are equations in following format: AX*X + BX + C = 0,
        where A, B, C - some constants, i.e.:
        X*X -3*X -4 = 0, here A=1, B=-3, C=-4.
        This particular equation has following 2 roots: 4 and -1

    Formula to solveQuadraticEquation quadratic equations:
        ROOT1 =  (-B + SQRT(BB - 4AC) ) / 2A
        ROOT2 =  (-B - SQRT(BB - 4AC) ) / 2A

    Weak points to be covered with Exception mechanism, i.e.:
        a. (A == null || A == 0) -> Equation is no longer quadratic && cannot divide by 0.
        b. ((B*B - 4AC) < 0)     -> Expression must be non-negative since it's used in square root calculation.
*/

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuadraticEquationsSolver {

    /*
       Using BigDecimal instead of doubles due to precision limitations / issues with double primitive datatype.
       In real life WOULD CLARIFY required precision from FAs to avoid "overthinking"
       Assignments not required here but added for better readability
     */
    private BigDecimal a = null;
    private BigDecimal b = null;
    private BigDecimal c = null;

    private int precision = 0;
    private final int DEFAULT_MATH_CONTEXT_PRECISION = 10;      // Required if precision not provided

    private BigDecimal root1 = null;
    private BigDecimal root2 = null;

    private final int MIN_EQUATION_STRING_SIZE=3;               // Min possible input expression length, example: A=1
    private final String SEPARATOR_FOR_EQUATION_STRING = "&";
    private String keyToGetConstantA = "A";
    private String keyToGetConstantB = "B";
    private String keyToGetConstantC = "C";
    private String keyToGetPrecision = "P";


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Solution {
        private BigDecimal root1 = null;
        private BigDecimal root2 = null;
    }

    /*
       Method to solve quadratic equation based on provided data
    */
    public Solution solveQuadraticEquation() throws QuadraticEquationsException {

        validateBeforeSolving();

        precision = precision > 0 ? precision: DEFAULT_MATH_CONTEXT_PRECISION;

        /*
        Formula to solveQuadraticEquation quadratic equations:
        ROOT1 =  (-B + SQRT(BB - 4AC) ) / 2A
        ROOT2 =  (-B - SQRT(BB - 4AC) ) / 2A
        */
        BigDecimal sqrt = b.multiply(b).subtract(a.multiply(c.multiply(new BigDecimal(4)))).sqrt(new MathContext(precision));
        BigDecimal divider = a.multiply(new BigDecimal(2));
        root1 = b.negate().add(sqrt).divide(divider, RoundingMode.DOWN);
        root2 = b.negate().subtract(sqrt).divide(divider, RoundingMode.DOWN);

        return new Solution(root1, root2);
    }

    /*
        Method to parse provided String and populate required object fields
        Example of input string:
        A=1&B=-3&C=-4&P=7 -> X*X - 3*X - 4 = 0, precision = 7
    */
    public void initializeParamsFromString(String source) throws QuadraticEquationsException {

        if (source == null || source.length() < MIN_EQUATION_STRING_SIZE)
            throw new QuadraticEquationsException("Incorrect quadratic equation string size");

        Map<String, String> parsedStringMap = new HashMap<String, String>();

        for (String keyValuePair : source.split(SEPARATOR_FOR_EQUATION_STRING)) {
            String[] keyValuePairArray = keyValuePair.split(" *= *", 2);
            parsedStringMap.put(keyValuePairArray[0], keyValuePairArray[1]);
        }


        a = new BigDecimal(parsedStringMap.getOrDefault(keyToGetConstantA,"0"));
        b = new BigDecimal(parsedStringMap.getOrDefault(keyToGetConstantB, "0"));
        c = new BigDecimal(parsedStringMap.getOrDefault(keyToGetConstantC, "0" ));
        precision = Integer.parseInt(parsedStringMap.getOrDefault(keyToGetPrecision, String.valueOf(DEFAULT_MATH_CONTEXT_PRECISION)));
    }


    /*
        Method to get equation String from file
    */
    public void initializeParamsFromFile(String path) throws QuadraticEquationsException {
        String result = null;
        try {
            Path file = Paths.get(path);
            result = Files.readString(file);
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        initializeParamsFromString(result);
    }

    /*
        Method to provide basic validations
    */
    public void validateBeforeSolving() throws QuadraticEquationsException {

        // Constant A cannot be 0:
        if(a.compareTo(BigDecimal.ZERO)==0)
            throw new QuadraticEquationsException("Constant A is 0.");

        // Check if B or C are nulls - assign them 0 in this case
        if(b==null) b = BigDecimal.ZERO;
        if(c==null) c = BigDecimal.ZERO;


        // SQRT cannot be invoked on negative number:
        if(b.multiply(b).compareTo(a.multiply(c).multiply(new BigDecimal(4))) <0)
            throw new QuadraticEquationsException("Incorrect equation - expression in SQRT cannot be negative");
    }
}