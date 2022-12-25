# searchAgents.py
# ---------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
This file contains all of the agents that can be selected to control Pacman.  To
select an agent, use the '-p' option when running pacman.py.  Arguments can be
passed to your agent using '-a'.  For example, to load a SearchAgent that uses
depth first search (dfs), run the following command:

> python pacman.py -p SearchAgent -a fn=depthFirstSearch

Commands to invoke other search strategies can be found in the project
description.

Please only change the parts of the file you are asked to.  Look for the lines
that say

"*** YOUR CODE HERE ***"

The parts you fill in start about 3/4 of the way down.  Follow the project
description for details.

Good luck and happy searching!
"""

from game import Directions
from game import Agent
from game import Actions
import util
import time
import search


class GoWestAgent(Agent):
    "An agent that goes West until it can't."

    def getAction(self, state):
        "The agent receives a GameState (defined in pacman.py)."
        if Directions.WEST in state.getLegalPacmanActions():
            return Directions.WEST
        else:
            return Directions.STOP


#######################################################
# This portion is written for you, but will only work #
#       after you fill in parts of search.py          #
#######################################################

class SearchAgent(Agent):
    """
    This very general search agent finds a path using a supplied search
    algorithm for a supplied search problem, then returns actions to follow that
    path.

    As a default, this agent runs DFS on a PositionSearchProblem to find
    location (1,1)

    Options for fn include:
      depthFirstSearch or dfs
      breadthFirstSearch or bfs


    Note: You should NOT change any code in SearchAgent
    """

    def __init__(self, fn='depthFirstSearch', prob='PositionSearchProblem', heuristic='nullHeuristic'):
        # Warning: some advanced Python magic is employed below to find the right functions and problems

        # Get the search function from the name and heuristic
        if fn not in dir(search):
            raise AttributeError(fn + ' is not a search function in search.py.')
        func = getattr(search, fn)
        if 'heuristic' not in func.__code__.co_varnames:
            print('[SearchAgent] using function ' + fn)
            self.searchFunction = func
        else:
            if heuristic in globals().keys():
                heur = globals()[heuristic]
            elif heuristic in dir(search):
                heur = getattr(search, heuristic)
            else:
                raise AttributeError(heuristic + ' is not a function in searchAgents.py or search.py.')
            print('[SearchAgent] using function %s and heuristic %s' % (fn, heuristic))
            # Note: this bit of Python trickery combines the search algorithm and the heuristic
            self.searchFunction = lambda x: func(x, heuristic=heur)

        # Get the search problem type from the name
        if prob not in globals().keys() or not prob.endswith('Problem'):
            raise AttributeError(prob + ' is not a search problem type in SearchAgents.py.')
        self.searchType = globals()[prob]
        print('[SearchAgent] using problem type ' + prob)

    def registerInitialState(self, state):
        """
        This is the first time that the agent sees the layout of the game
        board. Here, we choose a path to the goal. In this phase, the agent
        should compute the path to the goal and store it in a local variable.
        All of the work is done in this method!

        state: a GameState object (pacman.py)
        """
        if self.searchFunction == None: raise Exception("No search function provided for SearchAgent")
        starttime = time.time()
        problem = self.searchType(state)  # Makes a new search problem
        self.actions = self.searchFunction(problem)  # Find a path
        totalCost = problem.getCostOfActions(self.actions)
        print('Path found with total cost of %d in %.1f seconds' % (totalCost, time.time() - starttime))
        if '_expanded' in dir(problem): print('Search nodes expanded: %d' % problem._expanded)

    def getAction(self, state):
        """
        Returns the next action in the path chosen earlier (in
        registerInitialState).  Return Directions.STOP if there is no further
        action to take.

        state: a GameState object (pacman.py)
        """
        if 'actionIndex' not in dir(self): self.actionIndex = 0
        i = self.actionIndex
        self.actionIndex += 1
        if i < len(self.actions):
            return self.actions[i]
        else:
            return Directions.STOP


class PositionSearchProblem(search.SearchProblem):
    """
    A search problem defines the state space, start state, goal test, successor
    function and cost function.  This search problem can be used to find paths
    to a particular point on the pacman board.

    The state space consists of (x,y) positions in a pacman game.

    Note: this search problem is fully specified; you should NOT change it.
    """

    def __init__(self, gameState, costFn=lambda x: 1, goal=(1, 1), start=None, warn=True, visualize=True):
        """
        Stores the start and goal.

        gameState: A GameState object (pacman.py)
        costFn: A function from a search state (tuple) to a non-negative number
        goal: A position in the gameState
        """
        self.walls = gameState.getWalls()
        self.startState = gameState.getPacmanPosition()
        if start != None: self.startState = start
        self.goal = goal
        self.costFn = costFn
        self.visualize = visualize
        if warn and (gameState.getNumFood() != 1 or not gameState.hasFood(*goal)):
            print('Warning: this does not look like a regular search maze')

        # For display purposes
        self._visited, self._visitedlist, self._expanded = {}, [], 0  # DO NOT CHANGE

    def getStartState(self):
        return self.startState

    def isGoalState(self, state):
        isGoal = state == self.goal

        # For display purposes only
        if isGoal and self.visualize:
            self._visitedlist.append(state)
            import __main__
            if '_display' in dir(__main__):
                if 'drawExpandedCells' in dir(__main__._display):  # @UndefinedVariable
                    __main__._display.drawExpandedCells(self._visitedlist)  # @UndefinedVariable

        return isGoal

    def getSuccessors(self, state):
        """
        Returns successor states, the actions they require, and a cost of 1.

         As noted in search.py:
             For a given state, this should return a list of triples,
         (successor, action, stepCost), where 'successor' is a
         successor to the current state, 'action' is the action
         required to get there, and 'stepCost' is the incremental
         cost of expanding to that successor
        """

        successors = []
        for action in [Directions.NORTH, Directions.SOUTH, Directions.EAST, Directions.WEST]:
            x, y = state
            dx, dy = Actions.directionToVector(action)
            nextx, nexty = int(x + dx), int(y + dy)
            if not self.walls[nextx][nexty]:
                nextState = (nextx, nexty)
                cost = self.costFn(nextState)
                successors.append((nextState, action, cost))

        # Bookkeeping for display purposes
        self._expanded += 1  # DO NOT CHANGE
        if state not in self._visited:
            self._visited[state] = True
            self._visitedlist.append(state)

        return successors

    def getCostOfActions(self, actions):
        """
        Returns the cost of a particular sequence of actions. If those actions
        include an illegal move, return 999999.
        """
        if actions == None: return 999999
        x, y = self.getStartState()
        cost = 0
        for action in actions:
            # Check figure out the next state and see whether its' legal
            dx, dy = Actions.directionToVector(action)
            x, y = int(x + dx), int(y + dy)
            if self.walls[x][y]: return 999999
            cost += self.costFn((x, y))
        return cost


class StayEastSearchAgent(SearchAgent):
    """
    An agent for position search with a cost function that penalizes being in
    positions on the West side of the board.

    The cost function for stepping into a position (x,y) is 1/2^x.
    """

    def __init__(self):
        self.searchFunction = search.uniformCostSearch
        costFn = lambda pos: .5 ** pos[0]
        self.searchType = lambda state: PositionSearchProblem(state, costFn, (1, 1), None, False)


class StayWestSearchAgent(SearchAgent):
    """
    An agent for position search with a cost function that penalizes being in
    positions on the East side of the board.

    The cost function for stepping into a position (x,y) is 2^x.
    """

    def __init__(self):
        self.searchFunction = search.uniformCostSearch
        costFn = lambda pos: 2 ** pos[0]
        self.searchType = lambda state: PositionSearchProblem(state, costFn)


def manhattanHeuristic(position, problem, info={}):
    "The Manhattan distance heuristic for a PositionSearchProblem"
    xy1 = position
    xy2 = problem.goal
    return abs(xy1[0] - xy2[0]) + abs(xy1[1] - xy2[1])


def euclideanHeuristic(position, problem, info={}):
    "The Euclidean distance heuristic for a PositionSearchProblem"
    xy1 = position
    xy2 = problem.goal
    return ((xy1[0] - xy2[0]) ** 2 + (xy1[1] - xy2[1]) ** 2) ** 0.5


#####################################################
# This portion is incomplete.  Time to write code!  #
#####################################################

class CornersProblem(search.SearchProblem):
    """
    This search problem finds paths through all four corners of a layout.

    You must select a suitable state space and successor function
    """

    def __init__(self, startingGameState):
        """
        Stores the walls, pacman's starting position and corners.
        """
        self.walls = startingGameState.getWalls()
        self.startingPosition = startingGameState.getPacmanPosition()
        top, right = self.walls.height - 2, self.walls.width - 2
        self.corners = ((1, 1), (1, top), (right, 1), (right, top))
        for corner in self.corners:
            if not startingGameState.hasFood(*corner):
                print('Warning: no food in corner ' + str(corner))
        self._expanded = 0  # DO NOT CHANGE; Number of search nodes expanded
        # Please add any code here which you would like to use
        # in initializing the problem
        "*** YOUR CODE HERE ***"
        # I do all my initializations and assignments in the get start state function.

    def getStartState(self):
        """
        Returns the start state (in your state space, not the full Pacman state
        space)
        """
        "*** YOUR CODE HERE ***"
        ini_crd = 1
        wal = self.walls
        h = wal.height
        w = wal.width
        a = -2 + h
        r = -2 + w
        fir = (ini_crd, ini_crd)
        sec = (ini_crd, a)
        thi = (r, ini_crd)
        lst = (r, a)
        crnrs = (fir, sec, thi, lst)
        zeroth = self.startingPosition
        first = crnrs
        return zeroth, first #returning the tuple made of starting position and the corners

    def isGoalState(self, state):
        glRch = False  # defining a boolean variable named glRch which will keep whether the aimed state is reached.
        if state is None:  # for checking whether the state is null
            print("***** !!!! Null agent state is encountered !!! *****")
            raise Exception("**!!Null Agent State !!**")
        if len(state) <= 0:  # for checking whether the state is empty
            print("Invalid or empty agent state is encountered !!! ********")
            raise Exception("!!!! Invalid or empty agent state !!!!")
        all_possible_corners = state[1] # obtaining the untraveled corners
        length = len(all_possible_corners)  # length of untraveled ones
        if length == 0:  # checks if the length of the list containing unvisited nodes is equal to 0
            glRch = True  # if that is the case, I have assigned the value of the boolean var called glRch to true
        return glRch  # I have finally returned the value of the boolean variable called glRch

    def getSuccessors(self, state):
        """
        Returns successor states, the actions they require, and a cost of 1.

         As noted in search.py:
            For a given state, this should return a list of triples, (successor,
            action, stepCost), where 'successor' is a successor to the current
            state, 'action' is the action required to get there, and 'stepCost'
            is the incremental cost of expanding to that successor
        """

        successors = []
        for action in [Directions.NORTH, Directions.SOUTH, Directions.EAST, Directions.WEST]:
            # Add a successor state to the successor list if the action is legal
            # Here's a code snippet for figuring out whether a new position hits a wall:
            #   x,y = currentPosition
            #   dx, dy = Actions.directionToVector(action)
            #   nextx, nexty = int(x + dx), int(y + dy)
            #   hitsWall = self.walls[nextx][nexty]

            "*** YOUR CODE HERE ***"
            ln = len(state) #obtaining length of the state
            if state is None:  # checking whether the agent state object is none
                print("Null State of the Agent !!!!") #print a related message
                raise Exception("Null Agent State !!!")#display a related exception
            elif ln <= 0:  # checking whether the agent state object is empty
                print("Invalid or empty agent state")#print a related message
                raise Exception("Empty or invalid state of the  agent !!!")#display a related exception
            all_possib_corns = state[1] # corners
            coordinateValOfX, coordinateValOfY = state[0] #x coordinate and y coordinate
            vectoralXAlteration, vectoralYAlteration = Actions.directionToVector(
                action)  # obtain the vector-based x change and vector-based y change by calling directionToVector function
            upd_x_val = coordinateValOfX + vectoralXAlteration  # change x by vectoral x change
            upd_y_val = coordinateValOfY + vectoralYAlteration  # alter y by vectoral y change
            upd_x_val = int(upd_x_val)  # convert x to int
            upd_y_val = int(upd_y_val)  # change y to int
            hitOccurs = self.walls[upd_x_val][
                upd_y_val]  # defining a boolean variable named hitOccurs which keeps whether we hit the wall.In other words, it checks if wall contains updated coordinate tuple.
            if hitOccurs is True: #if hit is already occurred
                ffff = 99 #do nothing, may also be used 'pass' here
            else:  # if the hit does not occur
                subseq_nd_vl = upd_x_val, upd_y_val #creating a tuple from the updated_x_value and updated_y_value which as a whole indicating the next node
                lst = list() #list for keeping the untraversed nodes
                for crn in all_possib_corns: #going along all corners
                    if crn != subseq_nd_vl:  # when the current agent location is not equal to the subsequent node location
                        lst.append(crn) #append the location of agent to the list which keeps the untraversed corners

                subseq_sit = subseq_nd_vl, lst #defining the next state as the next node in its first element and the unvisited corners as its second element.
                cst = 1  # defining the cost to be 1
                tup = subseq_sit, action, cst  # extracting cost, actions, successor state.
                successors.append(
                    tup)  # appending the extracted cost, actions, and successor state to the list named successors

        self._expanded += 1  # DO NOT CHANGE
        return successors

    def getCostOfActions(self, actions):
        """
        Returns the cost of a particular sequence of actions.  If those actions
        include an illegal move, return 999999.  This is implemented for you.
        """
        if actions == None: return 999999
        x, y = self.startingPosition
        for action in actions:
            dx, dy = Actions.directionToVector(action)
            x, y = int(x + dx), int(y + dy)
            if self.walls[x][y]: return 999999
        return len(actions)


def methValAbso(p):
    if p <= 0:
        p = -p
    return p


def heuManDis(q, w):
    firXVal = q[0]  # obtaining x coordinate value of the first point
    firYVal = q[1]  # obtaining y coordinate value of the first point

    secXVal = w[0]  # obtaining x coordinate value of the second point
    secYVal = w[1]  # obtaining y coordinate value of the second point

    xDf = secXVal - firXVal  # finding difference between x values.
    yDf = secYVal - firYVal  # finding difference between y values.

    posXDf = methValAbso(xDf)  # obtaining the absolute value of the difference between x values.
    posYDf = methValAbso(yDf)  # extracting the absolute value of the difference between y values.
    dst_val = posXDf + posYDf  # adding the absolute-value differences

    return dst_val  # return that addition


def cornersHeuristic(state, problem):
    
    #Null checks done for dealing with all types of checks ########
    if problem is None:  # checks if the problem object is null
        print("Null problem instance !!")
        raise Exception("There is no search problem !!")
   #Null checks done for dealing with all types of checks ########

    stLen = len(state)  # obtaining length of state

    
    #Null checks done for dealing with all types of checks , and test cases ########
    if state is None:
        print("Null agent state !!!")
        raise Exception("** Null Agent State **")
    elif stLen <= 0:  # checks if state is empty.
        print("*** !!! Empty or not valid agent state is encountered !!! ***")
        raise Exception("*** Empty or Not Valid State of the  Agent !!! *****")
   #Null checks done for dealing with all types of checks , and test cases ########

    noCost = 0
    noTrv = state[1] #unvisited corners
    
    ##################Dealing with null cases andd empty cases for covering all possible test cases where null arguments are provided.
    if len(noTrv) <= 0:
        print("!!!!!!!!! **** No Element inside the non traversed path **** !!!!!!!!")
        return noCost
    elif noTrv is None:
        print("!! Null instance of the non traversed path is encountered !! *******")
        raise Exception("Null instance of the non-traversed path !!")
    ##################Dealing with null cases andd empty cases for covering all possible test cases where null arguments are provided.

    lst = list()
    agentLoc = state[0] #position
    for c in noTrv: # going along all of the unvisited corners in a for loop
        dst = heuManDis(c,
                        agentLoc)  # calculating the manhattan distance from agent location to every single non-travsersed node.
        lst.append(dst)  # appending the calculated manhattan distance to a list.

        
   ##################Dealing with null cases andd empty cases for covering all possible test cases where null or empty arguments are provided.
    if len(lst) <= 0:
        print("No element in the distance list !!")
        return 0
    elif lst is None:
        print("Null instance of the distance list !! **")
        raise Exception("Null distance list instance !!")
  ##################Dealing with null cases andd empty cases for covering all possible test cases where null or empty arguments are provided.

   ####################Find the max ##############################################################
    mx = lst[0]  # assign the maximum element to the first element of the list called lst.
    length = len(lst)  # define the length of the list called lst
    for z in range(0, length):  # for going all along the length of the list called lst
        df = lst[z] - mx
        if df >= 0:  # if any element of lst is bigger than or equal to mx
            mx = lst[z]  # alter mx
      ####################Find the max ##############################################################
    return mx  # return the value of mx


class AStarCornersAgent(SearchAgent):
    "A SearchAgent for FoodSearchProblem using A* and your foodHeuristic"

    def __init__(self):
        self.searchFunction = lambda prob: search.aStarSearch(prob, cornersHeuristic)
        self.searchType = CornersProblem


class FoodSearchProblem:
    def __init__(self, startingGameState):
        self.start = (startingGameState.getPacmanPosition(), startingGameState.getFood())
        self.walls = startingGameState.getWalls()
        self.startingGameState = startingGameState
        self._expanded = 0  # DO NOT CHANGE
        self.heuristicInfo = {}  # A dictionary for the heuristic to store information

    def getStartState(self):
        return self.start

    def isGoalState(self, state):
        return state[1].count() == 0

    def getSuccessors(self, state):
        "Returns successor states, the actions they require, and a cost of 1."
        successors = []
        self._expanded += 1  # DO NOT CHANGE
        for direction in [Directions.NORTH, Directions.SOUTH, Directions.EAST, Directions.WEST]:
            x, y = state[0]
            dx, dy = Actions.directionToVector(direction)
            nextx, nexty = int(x + dx), int(y + dy)
            if not self.walls[nextx][nexty]:
                nextFood = state[1].copy()
                nextFood[nextx][nexty] = False
                successors.append((((nextx, nexty), nextFood), direction, 1))
        return successors

    def getCostOfActions(self, actions):
        """Returns the cost of a particular sequence of actions.  If those actions
        include an illegal move, return 999999"""
        x, y = self.getStartState()[0]
        cost = 0
        for action in actions:
            # figure out the next state and see whether it's legal
            dx, dy = Actions.directionToVector(action)
            x, y = int(x + dx), int(y + dy)
            if self.walls[x][y]:
                return 999999
            cost += 1
        return cost


class AStarFoodSearchAgent(SearchAgent):
    "A SearchAgent for FoodSearchProblem using A* and your foodHeuristic"

    def __init__(self):
        self.searchFunction = lambda prob: search.aStarSearch(prob, foodHeuristic)
        self.searchType = FoodSearchProblem


def obt_lrg(ls):
    if ls is None:  # if the ls object is null
        print("Null list instance is encountered !! ***")  # print a related message
        raise Exception("** ! This list is null ! ** ")  # throw a related exception
    if len(ls) == 0:  # if there is no element in the list called ls
        print("**!!There is no element in the list called ls !!**")  # print a related message
        raise Exception("No element found in the list called ls ! **")  # throw a related exception
    z = ls[0]  # assign z to the first element of lst
    lng = len(ls)  # define length of ls
    for t in range(0, lng):  # go along ls
        if ls[t] >= z:  # if any element of ls is bigger than or equal to  z
            z = ls[t]  # update z to that element
    return z  # return the value of z


def heu_manh(position, problem):
    import math #importing math library
    aim_sit = problem.goal #defining the aimed situation by calling problem.goal.
    if aim_sit is None:  # checks whether the aim situation object is null
        print("** Aim State is null !! **")
        raise Exception("*** Null goal state ***")
    if len(aim_sit) == 0:  # checks whether the aim situation is empty
        print("Empty goal state is encountered !! ")
        raise Exception("*** Empty goal state ****")
    my_loc_val = position #### the agent position
    if my_loc_val is None:  # checks if the location of agent is null
        print("*** Position object is null ***") #print a related message
        raise Exception("Null position !") #throw a related exception
    if len(my_loc_val) == 0:  # checks if the location of agent is empty
        print("*** !!! Empty position is encountered !!! ***") #print a related message
        raise Exception("**** !! Empty position is encountered !! ****") #throw a related exception
    df_sec = my_loc_val[1] - aim_sit[1]  # for calculating the difference between the y values
    df_fir = my_loc_val[0] - aim_sit[0]  # for calculating the difference between the x values
    if min(my_loc_val[1], aim_sit[1]) == my_loc_val[1]:  # if the minimum of y coordinate of the current location and y
        # coordinate of the aim situation is y# coordinate of the current location
        df_sec = int(math.prod([df_sec, -1]))  # altering the sign of the df_sec.
    if min(my_loc_val[0], aim_sit[0]) == my_loc_val[0]:  # if the minimum of x coordinate of the current location and
        # x coordinate of the aim situation is x coordinate of the current location
        df_fir = int(math.prod([-1, df_fir]))  # altering the sign of the df_fir.
    rslt_df = df_fir + df_sec  # add the first difference and second difference
    return rslt_df  # return the found total difference


####### to be used in the food heuristic function #######
def pth_lng_mn(ini, ult, problem):
    psgs = problem.startingGameState  # extracting the initial state of the problem
    if psgs is None:  # if the first state of  the problem is none.
        print("Null initial state of game encountered !! ")  # print a related message
        raise Exception("First state of game is null !! ")  # throw a related exception
    visPerm = False  # define a variable called visPerm to disable visualization.
    warPerm = False  # define a variable called visPerm to disable warnings.

    individ_cst = 1  # defining a variable called individ_cst, and assigning it to 1. This variable will keep the step costs.

    # creating an instance of the position search problem with necessary arguments in it.
    psp = PositionSearchProblem(
        problem.startingGameState, lambda vv: individ_cst,
        start=ini, goal=ult, warn=warPerm, visualize=visPerm
    )
    # creating an instance of the position search problem with necessary arguments in it.

    # handling the null cases for all the objects used in this question.
    # this is for covering all possible test cases where null argument or parameter may also be provided ###
    if ult is None:
        print("Null last state !!!!")
        raise Exception("Null last state encountered !!")
    if ini is None:
        print("Null problem state encountered !! **")
        raise Exception("Null problem state *** ")
    if problem is None:
        print("Null problem instance encountered !! ****")
        raise Exception("**** Null problem instance ****")
    if psp is None:
        print("this problem instance is null !!")
        raise Exception("null problem")
     # handling the null cases for all the objects used in this question.
    
    astrSrch = search.aStarSearch(psp,
                                  heu_manh)  # using an A* search by utilizing manhattan distance heuristic as the second argument.
    if astrSrch is None:
        print("** !! Null a star search instance is encountered !! ** ")
        raise Exception("Null instance of the a star search !! ** ")
    # this is for covering all possible test cases where null argument or parameter may also be provided ###
    
    return len(astrSrch) #returning length of astar search list returned by search.aStarSearch().
####### to be used in the food heuristic function #######



def foodHeuristic(state, problem):
    # My extra code additions before the below section.
    ################################Null checks done for dealing with all types of test cases########
    if state is None:
        print("The state of the problem is null")
        raise Exception("None problem state object !!! ")
    if problem is None:
        print("The problem is null")
        raise Exception("Null problem type!!! ")
    ################################Null checks done for dealing with all types of test cases########

    position, foodGrid = state #

    "*** YOUR CODE HERE ***"
    if position is None:  # checking if position of agent is null
        print("Null position of the agent !! ****")
        raise Exception("Null position")
    if foodGrid is None:  # checking whether the food grid is null
        print("Null object of food grid !")
        raise Exception("!!! Null grid encountered !!!")
    crdnts_of_fds = foodGrid.asList()  # converting the food grid to a list.
    if crdnts_of_fds is None:
        print("*** !! Null grid of foods is encountered now !!! *")
        raise Exception("Null Grid of Foods")

    lngth_of_fds = len(crdnts_of_fds)  # obtain the length of the list of food coordinates

    error_value = 0  # defining a variable named error_value and assigning its value to 0
    if lngth_of_fds <= 0:  # if the length of the food grid is invalid
        print("Empty or invalid list of food coordinates is encountered now !!")
        return error_value  # return the value of the variable called error_value
    else:
        lng_lis = list()  # initialize a list of lengths by utilizing list method
        for b in crdnts_of_fds:  # iterate over the entire set of food coordinates
            dis_of_pth = pth_lng_mn(position, b, problem)  # obtaining the distance to a non-traversed food
            lng_lis.append(dis_of_pth)  # appending the distance to a non-traversed node to a list
        if lng_lis is None:
            print("Null list of distances to foods is encountered")
            raise Exception("Null distance list !")
        elif len(lng_lis) <= 0:
            print("It is an empty distance list!")
            raise Exception("** Empty distance list **")

    elm_mx = lng_lis[
        0]  # create a variable named elm_mx and make it equal to the first element of the list called lng_lis.
    ls_ln = len(lng_lis)  # extract the length of the list
    for v in range(0, ls_ln):  # traverse the list
        if lng_lis[v] >= elm_mx:  # if any list element is bigger than or equal to the element named elm_mx.
            elm_mx = lng_lis[v]  # update value of elm_mx to that greater element.
    return elm_mx  # return the value of the elm_mx


class ClosestDotSearchAgent(SearchAgent):
    "Search for all food using a sequence of searches"

    def registerInitialState(self, state):
        self.actions = []
        currentState = state
        while (currentState.getFood().count() > 0):
            nextPathSegment = self.findPathToClosestDot(currentState)  # The missing piece
            self.actions += nextPathSegment
            for action in nextPathSegment:
                legal = currentState.getLegalActions()
                if action not in legal:
                    t = (str(action), str(currentState))
                    raise Exception('findPathToClosestDot returned an illegal move: %s!\n%s' % t)
                currentState = currentState.generateSuccessor(0, action)
        self.actionIndex = 0
        print('Path found with cost %d.' % len(self.actions))

    def findPathToClosestDot(self, gameState):
        """
        Returns a path (a list of actions) to the closest dot, starting from
        gameState.
        """
        # Here are some useful elements of the startState
        startPosition = gameState.getPacmanPosition()
        food = gameState.getFood()
        walls = gameState.getWalls()
        problem = AnyFoodSearchProblem(gameState)

        "*** YOUR CODE HERE ***"
        my_srch = search.bfs(problem)  # calling bfs from search problems to find the path to the nearest dot.
        
        #Null check the null return case of search.bfs
        if my_srch is None:
            print("!!! ** Null object of the bfs search problem is found !!! ** ")
            raise Exception("Null problem")
        return my_srch #return search.bfs() 's result.


class AnyFoodSearchProblem(PositionSearchProblem):
    def __init__(self, gameState):
        "Stores information from the gameState.  You don't need to change this."
        # Store the food for later reference
        self.food = gameState.getFood()

        # Store info for the PositionSearchProblem (no need to change this)
        self.walls = gameState.getWalls()
        self.startState = gameState.getPacmanPosition()
        self.costFn = lambda x: 1
        self._visited, self._visitedlist, self._expanded = {}, [], 0  # DO NOT CHANGE

    def isGoalState(self, state):
        reachOccurs = True
        """
        The state is Pacman's position. Fill this in with a goal test that will
        complete the problem definition.
        """
        # the extra code I wrote before the comment named your code here
        if state is None:  # checking if the state is null or not
            print("!!! *** Null state instance is found !!! ***")  # when the state is null, print a message which is related
            raise Exception("*!*! Null instance of state *!*!")
        # the extra code I wrote before the comment named your code here

        x, y = state #extracting the x and y coordinates in the state

        "*** YOUR CODE HERE ***"

        if self.food is None:  # checking if food is null or not
            raise Exception("Null grid of food")
        fdObj = self.food  # extracting the food
        my_ls = fdObj.asList()  # converting the food grid to list by using asList function
        if my_ls is None:
            raise Exception("** Null list of food found !!! **")
        fls = fdObj.asList()  # converting food grid to a list.
        lf = len(fls)
        if x is None:  # checking whether x is None
            print("Null x value is found !!! *** ")
            raise Exception("*** Null X Value ***")
        if y is None:  # checking whether y is None
            print("Null y value is found !!! ***")
            raise Exception("*** Null Y Value ***")

        if (fdObj is None) or (fls is None):  # check if the food object or food grid list is null.
            print("Null food object or food list is found!")
        elif lf < 0 or lf == 0:
            print("Empty food object found!")
        elif self.food[state[0]][state[1]] == True:  # checks if the goal is reached. In other words, checks whether the aimed state is in the food gridd.
            reachOccurs = True  #then assign the reachOccurs boolean to true, since we reached the goal
        else:
            reachOccurs = False #otherwise, then assign the reachOccurs boolean to false, since we could not reach the goal

        return reachOccurs


def mazeDistance(point1, point2, gameState):
    x1, y1 = point1
    x2, y2 = point2
    walls = gameState.getWalls()
    assert not walls[x1][y1], 'point1 is a wall: ' + str(point1)
    assert not walls[x2][y2], 'point2 is a wall: ' + str(point2)
    prob = PositionSearchProblem(gameState, start=point1, goal=point2, warn=False, visualize=False)
    return len(search.bfs(prob))
