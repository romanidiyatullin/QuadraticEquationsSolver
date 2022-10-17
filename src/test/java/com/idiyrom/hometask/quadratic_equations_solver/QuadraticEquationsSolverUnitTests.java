package com.idiyrom.hometask.quadratic_equations_solver;

import com.idiyrom.hometask.quadratic_equations_solver.component.QuadraticEquationsException;
import com.idiyrom.hometask.quadratic_equations_solver.component.QuadraticEquationsSolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
class QuadraticEquationsSolverUnitTests {


	static QuadraticEquationsSolver quadraticEquationsSolver;

	@BeforeEach
	void init() {
		quadraticEquationsSolver = new QuadraticEquationsSolver();
	}

	/*	1. Unit tests for biz method:
		   public QuadraticEquationsSolver initializeParamsFromString(String source) throws QuadraticEquationsException

		   Group 1. "Positive" cases - returns QuadraticEquationsSolver object:
		     Test1: [Source String - const A Only]             -> expect: No exception - returns QuadraticEquationsSolver object
		     Test2: [Source String - min size]           	   -> expect: No exception - returns QuadraticEquationsSolver object
		     Test3: [Source String - const A, B]               -> expect: No exception - returns QuadraticEquationsSolver object
		     Test4: [Source String - const A, B, C]            -> expect: No exception - returns QuadraticEquationsSolver object
		     Test5: [Source String - const A, B, C, precision] -> expect: No exception - returns QuadraticEquationsSolver object
	*/
	@DisplayName("Testing initializeParamsFromString(..) with cases that expect return of QuadraticEquationsSolver object")
	@ParameterizedTest
	@ValueSource(strings = {"A=-5", "A=1", "A=2&B=3", "A=3&B=-2&C=-4", "A=1&B=2&C=-3&P=8"})
	void testInitializeParamsFromStringExpectToGetQuadraticEquationsSolverObject(String source) throws QuadraticEquationsException {
		assertNotNull(quadraticEquationsSolver.initializeParamsFromString(source));
	}

	/*
			Group 2. "Negative" cases - throws QuadraticEquationsException:
			  Test1: [Source String is NULL]                     -> expect: throws QuadraticEquationsException
			  Test2: [Source String is empty]                    -> expect: throws QuadraticEquationsException
			  Test3: [Source String < MIN_SIZE]					 -> expect: throws QuadraticEquationsException
			  Test4: [Source String has no assignment operator]  -> expect: throws QuadraticEquationsException
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
		  	  Test1: [File exists, String is valid]   -> expect: No exception - returns QuadraticEquationsSolver object
	*/
	@Test
	@DisplayName("Testing initializeParamsFromFile(..) with cases that expect return of QuadraticEquationsSolver object")
	void testInitializeParamsFromFileExpectToGetQuadraticEquationsSolverObject(@Value("${unittests.filepath}") String source) throws QuadraticEquationsException {
		System.out.println(source);
		assertNotNull(quadraticEquationsSolver.initializeParamsFromFile(source));
	}


	/*
		   Group 2. "Negative" cases - returns QuadraticEquationsException:
			  Test1: [No such file] -> expect: throws QuadraticEquationsException
	*/
	@DisplayName("Testing initializeParamsFromFile(..) with cases that expect QuadraticEquationsException")
	@ParameterizedTest
	@ValueSource(strings = {"///Users/user1/fake_file_that_does_not_exist.txt"})
	void testInitializeParamsFromFileExpectToThrowException(String source) throws QuadraticEquationsException {
		assertThrows(QuadraticEquationsException.class, () -> quadraticEquationsSolver.initializeParamsFromFile(source));
	}


	/*	3. Unit tests for biz method:
		   public void validateQuadraticEquationsSolver() throws QuadraticEquationsException

		   Group 1. "Positive" cases - validateQuadraticEquationsSolver() does not throw QuadraticEquationsException:
			  Test1: [Const A only]                 -> expect: Solution object
			  Test2: [Const A,B only ]              -> expect: Solution object
			  Test4: [Const A,C only]               -> expect: Solution object
			  Test5: [Const A,B,C only ]            -> expect: Solution object
			  Test6: [Const A,B,C,P - all exist ]   -> expect: Solution object
			  Test7: [Const A,B,C,P - all exist, precision received as negative, replaced by default]-> expect: Solution object

	 */
	@DisplayName("Testing validateQuadraticEquationsSolver() with cases that expect valid Solutions object")
	@ParameterizedTest
	@CsvSource({"2,,,0","1,2,,0", "3,,-2,0","1,2,-3,0","2,3,-4,5","1,3,-4,-99"})
	void testValidateQuadraticEquationsSolverPositive(BigDecimal a, BigDecimal b, BigDecimal c, int p) throws QuadraticEquationsException {
		quadraticEquationsSolver.setA(a);
		quadraticEquationsSolver.setB(b);
		quadraticEquationsSolver.setC(c);
		quadraticEquationsSolver.setPrecision(p);
		assertNotNull(quadraticEquationsSolver.solveQuadraticEquation());
	}

	 /*	   Group 2. "Negative" cases - validateQuadraticEquationsSolver() throws QuadraticEquationsException:
			  Test1: [Const A is 0]                                -> expect: throws QuadraticEquationsException
			  Test2: [Const A is NULL]               			   -> expect: throws QuadraticEquationsException
			  Test3: [Expression in SQRT is negative - NO ROOTS!]  -> expect: throws QuadraticEquationsException
			  Test4: [All are NULLS]                   			   -> expect: throws QuadraticEquationsException

	 */
	@DisplayName("Testing validateQuadraticEquationsSolver() with cases that expect QuadraticEquationsException")
	@ParameterizedTest
	@CsvSource({"0,1,2,3",",1,2,3", "2,1,6,7",",,,0"})
	void testValidateQuadraticEquationsSolverToThrowException(BigDecimal a, BigDecimal b, BigDecimal c, int p) throws QuadraticEquationsException {
		quadraticEquationsSolver.setA(a);
		quadraticEquationsSolver.setB(b);
		quadraticEquationsSolver.setC(c);
		quadraticEquationsSolver.setPrecision(p);
		assertThrows(QuadraticEquationsException.class, () -> quadraticEquationsSolver.validateQuadraticEquationsSolver());
	}


	/*	4. Unit tests for biz method:
       public Solution solveQuadraticEquation() throws QuadraticEquationsException

       Group 1. Verify if QuadraticEquationSolver provides correct calculations with given precision
          Test1: [2 Roots, Precision specified]      -> expect: correct Solution object
          Test2: [1 Root only, Precision specified ] -> expect: correct Solution object
          Test3: [2 Roots, default Precision ] -> expect: correct Solution object

          NOTE: [No roots] case tested and caught by validateQuadraticEquationsSolver() method so not tested here!!
 */
	@DisplayName("Testing solveQuadraticEquation() with cases that expect specific valid roots")
	@ParameterizedTest
	@CsvSource({"2,3,-4,4,0.8508,-2.351","-4,12,-9,6,1.5,","1,3,-4,0,1,-4"})
	void testSolveQuadraticEquation(BigDecimal a, BigDecimal b, BigDecimal c, int p,
									BigDecimal expectedRoot1, BigDecimal expectedRoot2)
									throws QuadraticEquationsException {
		quadraticEquationsSolver.setA(a);
		quadraticEquationsSolver.setB(b);
		quadraticEquationsSolver.setC(c);
		quadraticEquationsSolver.setPrecision(p);

		assertEquals(expectedRoot1,quadraticEquationsSolver.solveQuadraticEquation().getRoot1());
		assertEquals(expectedRoot2,quadraticEquationsSolver.solveQuadraticEquation().getRoot2());
	}
}