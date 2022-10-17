# QuadraticEquationsSolver

Interview (home) task - Small App to solve quadratic equations.


Quadratic equations are equations in following format: 

    AX*X + BX + C = 0,   
        where A, B, C - some constants, i.e.:
        X*X -3*X -4 = 0, 
        here A=1, B=-3, C=-4.
        This particular equation has following 2 roots: 4 and -1
        
        Quadratic equations may have 2 roots (solutions), just 1 root or no roots. 
        
    Formula to solveQuadraticEquation quadratic equations:
        ROOT1 =  (-B + SQRT(BB - 4AC) ) / 2A
        ROOT2 =  (-B - SQRT(BB - 4AC) ) / 2A
        
    NOTE: "no roots" case is applicable when expression for square root (BB-4AC) is negative.


Interaction interfaces:
1. "REST" (really it is RPC, since not all REST rules are followed..) API via GET & POST methods.
2. Direct (classic) invocation API.


Usage Examples:
1. Sending equation via GET: 

          http://localhost:8082/api/get/A=-4&B=12&C=-9&P=3
          here A = -4, B = 12, C = -9, 
          desired precision = 3, delitmitter symbol is "&", so equation is:
          -4X*X + 12X -9 = 0
          
          Solution will be provided back in JSON (machine-parsible) format.

2. Sending equation via POST in JSON format:

          http://localhost:8082/api/post 
          {
            "a": -4,
            "b": 12,
            "c":-9,
            "precision": 4
          }

          so equation is:
          -4X*X + 12X -9 = 0, desired precision = 4.
          
          Solution will be provided back in JSON (machine-parsable) format.

3. Providing path to Server's file which should containt quadratic equation via POST in JSON format:

          http://localhost:8082/api/file
          {
            "path": "///Users/user1/equation_file.txt"
          }  
          
          Solution will be provided back in JSON (machine-parsable) format.
    
        
 Self-documenting REST API (once App started) available here:
 http://localhost:8082/swagger.html 
 
