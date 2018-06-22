# Parallel Temperature*
Calculates the final temperature for each region of a metal alloy of size Nx2N

Consider a rectangular piece of metal alloy, two times as wide as high,
consisting of three different metals, each with different thermal
characteristics.  For each region of the alloy, there is a given
amount (expressed in terms of a percentage) of each of the three base
metals, that varies up to 25 percent due to random noise.
The top left corner (at the mesh element at index [0,0]) is
heated at *S* degrees Celsius and the bottom right corner (index [width - 1,height - 1]) is heated at *T* degrees Celsius. The temperature at these points may also randomly vary over time.


This program calculates the final temperature for each region on the piece of alloy. 
The new temperature for a given region of the alloy is calculated using the formula 


where *m* represents each of the three base metals, *C_m* is the thermal constant for metal *m*, *N* is the set representing the neighbouring regions, *temp_n* is the temperature of the neighbouring region, *p^{m}_{n}* is the percentage of metal *m* in neighbour *n*, and *\vert N\vert* is the number of neighbouring regions. This computation is repeated until the temperatures converge to a final value or a reasonable maximum number of iterations is reached.
The values for *S*, *T*, *C_1*, *C_2*, *C_3*, the dimensions of the mesh, and the threshold should be parameters to the program. Note however, that combinations of these parameters do not do not converge well. Try values of (0.75, 1.0, 1.25) C1, C2, C3 for your test/demo.


Assume that the edges are maximally insulated from their surroundings.



The program graphically display the results by drawing a grid of points with intensities or colors indicating temperature. 

Problem elaborated by Doug Lea (CSC375) - SUNY
