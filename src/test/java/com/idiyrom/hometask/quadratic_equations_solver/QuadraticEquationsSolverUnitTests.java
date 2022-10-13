package com.idiyrom.hometask.quadratic_equations_solver;

import com.idiyrom.hometask.quadratic_equations_solver.component.QuadraticEquationsException;
import com.idiyrom.hometask.quadratic_equations_solver.component.QuadraticEquationsSolver;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class QuadraticEquationsSolverUnitTests {

	@Autowired
	static QuadraticEquationsSolver quadraticEquationsSolver;

	@BeforeAll
	static void init() {
		quadraticEquationsSolver = new QuadraticEquationsSolver();
	}

	/*	1. Unit tests for biz method:
		   public QuadraticEquationsSolver initializeParamsFromString(String source) throws QuadraticEquationsException

		   Group 1. "Positive" cases - returns QuadraticEquationsSolver object:
		     Test1: [Valid Const A]                 -> expect: No exception - returns QuadraticEquationsSolver object
		     Test2: [Valid Const A - min size]      -> expect: No exception - returns QuadraticEquationsSolver object
		     Test3: [Valid Const A & B]             -> expect: No exception - returns QuadraticEquationsSolver object
		     Test4: [Valid Const A & B & C]         -> expect: No exception - returns QuadraticEquationsSolver object
		     Test5: [Valid Const A & B & C & P]     -> expect: No exception - returns QuadraticEquationsSolver object
	*/
	@DisplayName("Testing initializeParamsFromString(..) with cases that expect return of QuadraticEquationsSolver object")
	@ParameterizedTest
	@ValueSource(strings = {"A=-1", "A=-1", "A=2&B=3", "A=3&B=-2&C=-4", "A=1&B=2&C=-3&P=8"})
	void testInitializeParamsFromStringExpectToGetQuadraticEquationsSolverObject(String source) throws QuadraticEquationsException {
		assertNotNull(quadraticEquationsSolver.initializeParamsFromString(source));
	}

	/*
			Group 2. "Negative" cases - throws QuadraticEquationsException:
			  Test1: [String is NULL]                     -> expect: throws QuadraticEquationsException
			  Test2: [String is empty]                    -> expect: throws QuadraticEquationsException
			  Test3: [String < MIN_SIZE]       			  -> expect: throws QuadraticEquationsException
			  Test4: [String has no assignment operator]  -> expect: throws QuadraticEquationsException
	*/
	@DisplayName("Testing initializeParamsFromString(..) with cases that expect to throw QuadraticEquationsException")
	@ParameterizedTest
	@NullSource
	@ValueSource(strings = {" ", "A=", "ABC&DEF"})
	void testInitializeParamsFromStringExpectToThrowException(String source) throws QuadraticEquationsException {
		assertThrows(QuadraticEquationsException.class, () -> quadraticEquationsSolver.initializeParamsFromString(source));
	}


	/*	2. Unit tests for biz method:
		   public QuadraticEquationsSolver initializeParamsFromFile(String path) throws QuadraticEquationsException

		   Group 1. "Positive" cases - returns QuadraticEquationsSolver object:
		  	  Test1: [File exists]   -> expect: No exception - returns QuadraticEquationsSolver object
	*/
	@Test
	@DisplayName("Testing initializeParamsFromFile(..) with cases that expect return of QuadraticEquationsSolver object")
	void testInitializeParamsFromFileExpectToGetQuadraticEquationsSolverObject(@Value("${unittests.filepath}") String source) throws QuadraticEquationsException {
		System.out.println(source);
		assertNotNull(quadraticEquationsSolver.initializeParamsFromFile(source));
	}


	/*
		   Group 2. "Negative" cases - returns QuadraticEquationsException:
			  Test1: [File is absent] -> expect: throws QuadraticEquationsException
	*/
	@DisplayName("Testing initializeParamsFromFile(..) with cases that expect QuadraticEquationsException")
	@ParameterizedTest
	@ValueSource(strings = {"///Users/user1/test999.txt"})
	void testInitializeParamsFromFileExpectToThrowException(String source) throws QuadraticEquationsException {
		assertThrows(QuadraticEquationsException.class, () -> quadraticEquationsSolver.initializeParamsFromFile(source));
	}

	/*	3. Unit tests for biz method:
		   public void validateQuadraticEquationsSolver() throws QuadraticEquationsException
		   // TODO: Complete unit tests package

	// tests ideas:
	// All ok
	// A is 0
	// A is absent
	// Expression for SQRT is negative
	// B is absent
	// C is absent
	// Both B & C are absent
	// Too Small string
	// Empty string
	// Corrupted string ( no delimitter)
	// Corrupted string ( inadequate expression)
	// No file
	// Empty file
	// No permissions to access (read) file
	// Root is just 1
*/
}