# COMP-305 PROJECT (Tribal Shaman King Selection)
Emre , Barış , Berke , Eren  

Table of Contents
1-Development Logs
2-Technical Information and Introduction
3-Our Approach
4-Time and Space Complexity
5-Run Times and Test Results
6-Extreme Cases That are Not Handled

Completed steps: 
1-) Meeting
2-) Discussing the problem
3-) Coming up with some brief ideas
4-) Completion of the major parts(finding the min delegates needed, finding
 the min_population_votes_needed, etc.)
5-)Creating a base for the algorithm
6-)Improving and fixing the edge cases of the algorithm
7-)Creating the presentation with time&space complexity, our final thoughts,
 and conclusion.

*Technical Information and Introduction

This program is written solely with java standard libraries and does not 
require any special treatment to compile and run. Please stick to the 
standard procedure when trying to compile and execute the program. 

This program is written as a part of the COMP305-Algorithms Complexity 
course offered at Koc University. We were tasked to come up with a creative
 solution for the following problem:  

The country of Triberia is a forgotten Shamanic civilization that lived 
approximately around 20,000 BC. They were a very progressive society in the
sense that they were selecting their shaman king. However, The Shaman King 
of the Triberia is not elected by a popular vote, but by a majority vote 
in the Electoral Hut. Each tribe in Triberia, gets some number of 
king-selectors in the Electoral Hut, and whoever they vote in becomes the 
next Shaman King. Under these circumstances, what is the fewest number of
votes that can result in the election of the shaman king?

*Our Approach 

We decided to make a greedy approach to deal with this problem. For each
tribe, we defined a value called electorValue that is found by diving the 
tribe's population with the tribe's elector count. This value tells us how 
valuable the votes of each tribe member are when compared with other 
tribes. After finding this value, we put every tribe into a priority queue 
with electorValue's representing keys such that the tribe with the smallest
elector value (thus the highest number of electors per member) is at the 
top of the list. Then we start poping from this list and sum up the 
populations and elector values of the popped tribes separately until we 
reach or surpass the delegate value that is required to win the election.

This aproach has an edge case.There is a chance of surpassing the minimum
elector values needed when counting in the last tribe. For example, if 10 
electors are sufficient to win the election, with this algorithm we can 
pick tribes with 5, 4, and 4 electors if they are the most efficient ones. 
Even though this tribe selection is the most efficient way of picking 13 
electors, the last 3 electors can be overkill.

To overcome this edge case, we implemented a checking mechanism. If the 
last tribe we picked surpasses the electors that are needed to win the 
election, we don't Immidiatelly consider that. Instead, we check our second
priority queue where tribes are listed from the least populous to the most
populous. We pop the tribes one by one and add their elector values to 
see if we can reach the winning elector votes required this way. If we 
can find a tribe with 1 elector and a population less than the last 
efficient tribe we got, we choose that tribe instead. This is because 
even if its population vote is not as valuable as the one with 4 electors
since it is least populous is it cheaper to get its 1 elector and it is 
also sufficient to win the election.

*Time and Space Complexity

The time complexity of our algorithm: O(N)
The space complexity of our algorithm: O(N)

*Run Times and Test Results

Run times of the test cases:

For the test case-1: 0.0032966 seconds

For the test case-2: 0.0040365 seconds

*Extreme Cases That are not Handled 

If a single tribe is sufficient and necessary to win the election (holds more than half the available electors), this approach cannot detect that. 
Example:

Tribe 1: 
Electors = 1
Population = 2

Tribe 2:
Electors = 1
Population = 3

Tribe 3:
Electors = 150
Population = 9000

We decided not to handle this case because this edge case occurring is close to impossible when the sample size is large enough and such distribution will likely never occur in real-life scenarios. Although it is trivial to check this edge case, and it will not increase the complexity of the algorithm, we didn't want to increase the run time and left it as is.






<<<<<<< HEAD
=======
To Do List: 
1-) Meet 
2-) Discuss the problem
3-) Come up with some ideas
4-) Try to find best idea to solve the problem.
5-) Prepare a presentation about the time&space complexity of the algorithm, about the ideas that we proposed, and also about our final idea to solve the tribal shaman king selection problem.




How to compile our program: 


To compile our program, we need to write javac *.java to the terminal.


How to run our program: 

To run our program, we need to type java Main to the terminal.


*Further Improvements

It is still possible that there are cases where our algorithm does not yield the correct result. Even though it is very efficient compared to the alternatives such as subset-sum, we haven't formally proven its correctness. Further analysis on its correctness and handling of the edge cases can be done to further improve the algortihm.









>>>>>>> origin/main






