# multiAgents.py
# --------------
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


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent
from pacman import GameState
import math
from math import inf

my_custom_array = list()


class ReflexAgent(Agent):
    """
    A reflex agent chooses an action at each choice point by examining
    its alternatives via a state evaluation function.

    The code below is provided as a guide.  You are welcome to change
    it in any way you see fit, so long as you don't touch our method
    headers.
    """

    def getAction(self, gameState: GameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {NORTH, SOUTH, WEST, EAST, STOP}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices)  # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState: GameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        #########null checks for ensuring the code work in all cases of the calls of this function##############################
        if (successorGameState is None) or \
                (newPos is None) or \
                (newFood is None) or \
                (newGhostStates is None) or \
                (newScaredTimes is None) or \
                (currentGameState is None) or \
                (action is None):
            print("invalid game state, pacman position, food, ghost state, scared times , or action !") #print a related message stating one of the parameters is none
            raise Exception("invalid game state, pacman position, food, ghost state, scared times, or action !") #raising an exception stating that. one of the parameters is invalid!
        if len(newScaredTimes) < 0 or \
                len(newScaredTimes) == 0:
            print("invalid length value for new scared times list or empty new scared times list !!!")
            #########null checks for ensuring the code work in all cases of the calls of this function##############################

        coordinateDifferencesKeeper = list() # initializing the list which will keep the distances to foods (initialization done by list() call 
        listVersionOfNewFood = newFood.asList() # using the. asList function to get the list of foods
        fdAmount = len(listVersionOfNewFood) #obtaining the length of the list of foods with len() function and assigning this to fdAmount
        for lisElem in listVersionOfNewFood:
            my_custom_heuristic_value = manhattanDistance(newPos, lisElem) #calculating the distance to food by using the manhattan distance heuristic, thus the. manhattan distance function
            coordinateDifferencesKeeper.append(my_custom_heuristic_value) #appending the found heuristic to the list which is supposed to keep the distances to the foods

            
       ############### if the internal python implementation of len is changed somehow by the python's writer or if the parameters are changed#####
        if len(coordinateDifferencesKeeper) < 0 or \
                (coordinateDifferencesKeeper is None):
            print("invalid distance keeper !")
            ############### if the internal python implementation of len is changed somehow by the python's writer or if the parameters are changed#####

            
            
        closness_to_grid = 0
        lengthOfDiffKeeper = len(coordinateDifferencesKeeper)
        firstCondForAtLeastTwo = (lengthOfDiffKeeper > 2)
        secondCondForAtLeastTwo = (lengthOfDiffKeeper == 2)
        resultingCondForAtLeastTwo = (firstCondForAtLeastTwo or secondCondForAtLeastTwo) #specifying whether there is more than 1 element in the list of distances to foods

        atLeastTwo = (resultingCondForAtLeastTwo)
        lessThanZero = (lengthOfDiffKeeper < 0)
        equalToOne = (lengthOfDiffKeeper == 1) # specifying whether there is 1 element in the list of distances to foods
        equalToZero = (lengthOfDiffKeeper == 0)
        initial_index_value = 0

        if coordinateDifferencesKeeper is None:
            print("null list encountered right now !!!!!!!!!!")
            raise Exception("null list encountered right now !!!!!!!!!!")

        if atLeastTwo: #if there is more than 1 food near
            closness_to_grid = coordinateDifferencesKeeper[initial_index_value] #assigning the variable which will keep the closeness to food metric to the first element of the list which keeps the distances to foods
            my_custom_range = range(0, lengthOfDiffKeeper)
            for w in my_custom_range: #going all along the list which keeps the distances to foods
                cond1 = (coordinateDifferencesKeeper[w] < closness_to_grid) 
                cond2 = (coordinateDifferencesKeeper[w] == closness_to_grid)
                cndt = cond1 or cond2
                if cndt:#if there is a smaller or equal distance (compared to the closeness_grid variable) in the list of distances to foods 
                    closness_to_grid = coordinateDifferencesKeeper[w] #update the closeness grid
        elif lessThanZero: #for covering all possible cases if the python implementer changes the implementation somehow
            print("invalid length value encountered currently !")
            raise Exception("invalid length value encountered currently !")
        elif equalToZero: #for covering empty list case
            print("empty coordinate difference list found currently!")
        elif equalToOne: #if there is only 1 element in the food distances list
            closness_to_grid = coordinateDifferencesKeeper[initial_index_value] #assign that element to the closeness_grid variable

        adversary_array = list() #initializing the list which will keep the distances to adversaries
        for adversary_state in newGhostStates: # going along all of the ghost states
            adversaryPos = adversary_state.getPosition() #obtaining the position of the adversary by using getPosition() function
            heur = manhattanDistance(newPos, adversaryPos) #calculating the heuristic by using the manhattan distance heuristic, thus the manhattanDistance() function
            adversary_array.append(heur) #appending the calculated heuristic to the list which keeps all the distances to ghosts

        lengthOfAdv = len(adversary_array)
        if adversary_array is None: #null check for adversary array
            print("null list of adversaries !!!!!!!!!!")
            raise Exception("null list of adversaries !!!!!!!!!!")
        elif lengthOfAdv < 0: 
            print("invalid length value found for adversary list !")
            raise Exception("invalid length value found for adversary list !")
        elif lengthOfAdv == 0: #empty check for adversary length
            print("empty list of adversary detected !")
        else: #if the list of distances to ghosts is not empty or is not none
            adversary_closeness_metric = adversary_array[initial_index_value] #get the first element of the list of the distances to ghosts
            my_range = range(0, lengthOfAdv)
            for y in my_range: #going all along the list of distances to ghosts
                first_condition = (adversary_array[y] < adversary_closeness_metric) 
                second_condition = (adversary_array[y] == adversary_closeness_metric)
                resulting_condition = (first_condition or second_condition)
                if resulting_condition:#if there is a distance smaller than or equal (within the list of distances to ghosts) 
                    # to the adversary_closeness_metric variable(closeness to ghost).
                    adversary_closeness_metric = adversary_array[y] #update the closeness to ghost

                    
                    
                    
        ######################I have created this part by considering the main agent goals, which are being away from the ghosts, being near to the foods,
        #######consuming the capsules to eat the ghosts, and increasing the game score#######################
        #######Since the distance to ghost is should be directly proportional to the evaluation function value, and distance to food is inversely proportional to 
        #######the evaluation function value, I have used the distance to food in the denominator part of a fraction. Moreover, I have used the distance to 
        ######ghost metric in a way which increase the evaluation function value.#######################################
        
        
        
##########################I have decided the coefficients by trail and error approach ##############################
        subseq_value_of_score = successorGameState.getScore() #obtaining the score of the successor game state
        first_denominator = (adversary_closeness_metric + 11)
        second_denominator = (14 + closness_to_grid)

        initial_bool = (first_denominator > 0)
        second_bool = (second_denominator > 0)
        denominatorsNotZero = (initial_bool and second_bool)
        ##########################I have decided the coefficients by trail and error approach ##############################

        if denominatorsNotZero:
            return 7 + 3 * (
                    float(-2.86) / first_denominator + float(4.15) / second_denominator +
                    35 * subseq_value_of_score - len(currentGameState.getCapsules())
            )

        return -777 
        ##############################


def scoreEvaluationFunction(currentGameState: GameState):
    """
    This default evaluation function just returns the score of the state.
    The score is the same one displayed in the Pacman GUI.

    This evaluation function is meant for use with adversarial search agents
    (not reflex agents).
    """
    return currentGameState.getScore()


class MultiAgentSearchAgent(Agent):
    """
    This class provides some common elements to all of your
    multi-agent searchers.  Any methods defined here will be available
    to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

    You *do not* need to make any changes here, but you can if you want to
    add functionality to all your adversarial search agents.  Please do not
    remove anything, however.

    Note: this is an abstract class: one that should not be instantiated.  It's
    only partially specified, and designed to be extended.  Agent (game.py)
    is another abstract class.
    """

    def __init__(self, evalFn='scoreEvaluationFunction', depth='2'):
        self.index = 0  # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)


class MinimaxAgent(MultiAgentSearchAgent):
    """
    Your minimax agent (question 2)
    """

    def __init__(self, evalFn='scoreEvaluationFunction', depth='2'):
        super().__init__(evalFn, depth)

    def getAction(self, gameState: GameState):
        """
        Returns the minimax action from the current gameState using self.depth
        and self.evaluationFunction.

        Here are some method calls that might be useful when implementing minimax.

        gameState.getLegalActions(agentIndex):
        Returns a list of legal actions for an agent
        agentIndex=0 means Pacman, ghosts are >= 1

        gameState.generateSuccessor(agentIndex, action):
        Returns the successor game state after an agent takes an action

        gameState.getNumAgents():
        Returns the total number of agents in the game

        gameState.isWin():
        Returns whether or not the game state is a winning state

        gameState.isLose():
        Returns whether or not the game state is a losing state
        """
        "*** YOUR CODE HERE ***"
        ##################null handling for all possible test cases########
        pacman_posit = 0
        initial_value_of_depth = 0
        if gameState is None:
            print("null state of the game is found! ")
            print("Invalid Game State !!!")
            raise Exception("null state of the game is found! ")

        global my_custom_array
        my_custom_array = list()
        lrg = self.valLargest(pacman_posit, initial_value_of_depth, gameState)
        my_custom = my_custom_array
        if my_custom is None:
            print("The list which keeps move types and results is detected as null !!!!")
            raise Exception("The list which keeps move types and results is detected as null !!!!")
        elif len(my_custom) < 0:
            print("invalid length of the list which keeps move types and results !")
            raise Exception("invalid length of the list which keeps move types and results !")
        elif len(my_custom) == 0:
            print("Empty list detected !")
        ##################null handling for all possible test cases########

        for t in my_custom: #going along the list keeping the actions and values
            mv, res = t #extracting action and value for each tuple in the list keeping the actions and values as tuples.
            diff_value = (lrg - res)
            less = diff_value < 0 #keeping the cases where the minimax is less than value
            more = diff_value > 0 #keeping the cases where the minimax is more than value
            resulting = less or more #keeping the cases where the minimax is less than value or the cases where the minimax is more than value
            if resulting: #if minimax value is not equal to the value in a tuple
                pass #do nothing
            else: #if minimax value is equal to the value in a tuple
                return mv #then, return the moves

    def valLargest(self, ag, dp, st):
        ###########################################Null handling for covering all possible test cases by even considderiing the inner implementation changes by Python implementer.
        if (ag is None) or \
                (dp is None) or \
                (st is None):
            print("at least one of the function parameters is null !!!")
            raise Exception("at least one of the function parameters is null !!!")
        if self.index is None:
            print("index is found as null !")
            raise Exception("index is found as null !")
        if self.depth is None:
            print("Depth is found as null !")
            raise Exception("Depth is found as null !")
        ###########################################Null handling for covering all possible test cases by even considderiing the inner implementation changes by Python implementer.

        renewed_depth = dp + 1
        from math import inf
        set_of_all_vertices = inf * -1 #initializing the first value to the minus infinity
        all_moves = st.getLegalActions(ag) #obtaining all legal actions by calling getLegalActions()
        ###########################################Null handling for covering all possible test cases by even considderiing the inner implementation changes by Python implementer.
        if all_moves is None:
            print("all valid moves list is none !")
            raise Exception("all valid moves list is none !")
        elif len(all_moves) < 0:
            print("invalid length for the all moves list!")
        elif len(all_moves) == 0:
            print("all moves list is detected as empty !!")
        ###########################################Null handling for covering all possible test cases by even considderiing the inner implementation changes by Python implementer.
        total_amount_of_pacman = st.getNumAgents() #obtaining the total number of pacmans 
        index_value_of_pacman = renewed_depth % total_amount_of_pacman #obtaining the location of the pacmans 
        for move in all_moves: #going along all of the legal moves
            renewed_situation = st.generateSuccessor(ag, move) #generate successor
            smallValueOrBigValue = self.algo_sb(index_value_of_pacman, renewed_depth, renewed_situation) #call minimax function
            lessThanOrMoreThanZero = (dp < 0 or dp > 0)
            if lessThanOrMoreThanZero: 
                pass
            else: #if agent location
                my_tuple_structure = (move, smallValueOrBigValue) #creating the tuple keeping the action and minimax value
                global my_custom_array
                my_custom_array.append(my_tuple_structure) #appending the created tuple to the global list keeping actions and values

            bigger = (smallValueOrBigValue > set_of_all_vertices)
            equal = (smallValueOrBigValue == set_of_all_vertices)
            res_bool = (bigger or equal)
            if res_bool: #if the minimax value is bigger than or equal to the initial value v
                set_of_all_vertices = smallValueOrBigValue #then assign the initial value to the v
                
        return set_of_all_vertices #return the initial value

    def valSmallest(self, pacman, distance, situ):
        renewed_value_of_distance = distance + 1 #for covering all possible cases for the pacman location (from 0 to depth) 
        ##################null handling for covering all test cases #####
        if (pacman is None) or \
                (distance is None) or \
                (situ is None):
            print("At least one of the parameters of smallest value function is null !!!!")
            raise Exception("At least one of the parameters of smallest value function is null !!!!")
        if self.index is None:
            print("index is null !")
            raise Exception("index is null !")
        if self.depth is None:
            print("depth is detected as null !")
            raise Exception("depth is found as null !")
        ##################null handling for covering all test cases #####
        
        from math import inf
        from math import prod
        array_of_inf = [] #creating a list which keeps the initial value
        array_of_inf.append(+1) #append 1 to the list
        array_of_inf.append(+inf) #append inf to the list 
        all_vertices = prod(array_of_inf) #assigning the initial value to the multiplication of elements in array_of_inf, namely plus infinity.
        valid_move_set = situ.getLegalActions(pacman) #obtaining the valid (legal) move set (actions)
         ##################null handling for covering all test cases #####
        if valid_move_set is None:
            print("the set of valid moves is none !")
            raise Exception("the set of valid moves is none !")
        elif len(valid_move_set) < 0:
            print("invalid length found for the set of valid move set !")
            raise Exception("invalid length found for the set of valid move set !")
         ##################null handling for covering all test cases #####
        for particular_move in valid_move_set: #for each move in all legal actions
            renewedSituation = situ.generateSuccessor(pacman, particular_move)
            pacmanNum = situ.getNumAgents() #get the total number of pacmans
            pacman_loc = renewed_value_of_distance % pacmanNum #obtain the location of pacman as index
            bigValOrSmallVal = self.algo_sb(pacman_loc, renewed_value_of_distance, renewedSituation)
            lessCondition = (bigValOrSmallVal < all_vertices) #boolean covering the cases where minimax is smaller than initial value
            equalCondition = (bigValOrSmallVal == all_vertices) #boolean covering the cases where minimax is equal to the initial value
            verticesBigger = (lessCondition or equalCondition) #boolean covering the cases where minimax is smaller than or equal to the initial value
            if verticesBigger: #if minimax is smaller than or equal to the initial value
                all_vertices = self.algo_sb(pacman_loc, renewed_value_of_distance, renewedSituation) #assign the initial value to the value minimax value
            else: #if minimax is bigger than the initial value
                pass #do nothing

        return all_vertices #return the initial value

    def algo_sb(self, a, d, s):
        ##################null handling for covering all test cases #####
        if (a is None) or \
                (d is None) or \
                (s is None):
            print("at least one of the parameters of pacman, situation and deepness is found as null !!")
            raise Exception("at least one of the parameters of pacman, situation and deepness is found as null !!")
        if (self.index is None) or \
                (self.depth is None):
            print("index or depth is null !!")
            raise Exception("index or depth is null !!")
        ##################null handling for covering all test cases #####

        total_num_of_pacmans = s.getNumAgents() #obtaiining total number of pacmans 
        
        ##################null handling for covering all test cases #####
        if total_num_of_pacmans < 0 or \
                total_num_of_pacmans == 0:
            print("invalid number of pacmans found !")
       ##################null handling for covering all test cases #####
    
            
       ###############Trial and error in depth limit imp##########################
        dpth = self.depth
        depth_of_all = total_num_of_pacmans * dpth 
        win = s.isWin()
        lose = s.isLose()
        diff_value = depth_of_all - d
        isPacmanLoc = (a == 0)
        diff_zero = (diff_value == 0)
        diff_less_than_zero = (diff_value < 0)
        ###############Trial and error in depth limit imp##########################

        diff_at_most_zero = (diff_less_than_zero or diff_zero)
        depth_is_zero = (d == 0)
        if win: #since the winning is the terminal state
            return self.evaluationFunction(s) #evaluate the utility by calling the evaluationFunction()
        elif diff_at_most_zero:  # for dealing with the maximum recursion depth #
            return self.evaluationFunction(s) #evaluate the utility by calling the evaluationFunction()
        elif depth_is_zero: #if the depth is more than the sum of all possible depths, namely the total number of pacmans times the depth.
            return self.evaluationFunction(s) #evaluate the utility by calling the evaluationFunction()
        elif lose: #one of the terminal states
            return self.evaluationFunction(s)#evaluate the utility by calling the evaluationFunction()
        elif a > 0: # min agent
            return self.valSmallest(a, d, s) #min value
        elif a < 0: #min agent
            return self.valSmallest(a, d, s) #min value
        elif isPacmanLoc: #max agent, namely pacman loc
            return self.valLargest(a, d, s) #max value function call


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
    Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState: GameState):
        """
        Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        
        ############All null checks for the parameters #################################
        if (gameState is None) \
                or \
                (self.index is None) \
                or \
                (self.depth is None):
            print("the game state, depth, or index is detected as none !!!")
            raise Exception("the game state, depth, or index is detected as none !!!")
        ############All null checks for the parameters #################################
        
        
        value_of_deepness_metric = 0 
        global my_custom_array
        my_custom_array = list()
        from math import inf
        location_of_agent = 0 #initializing the location of the pacman agent.

        # self, val_of_alpha, val_of_beta, param_of_dpth, param_of_sit, param_of_ag #

        value_of_beta_arg = +1 * inf     #initializing the beta to the positive infinity
        value_of_alpha_arg = -1 * inf    #initializing the alpha to the negative infinity

        largerOrSmallerNumber = self.bigger_val(
            val_of_alpha=value_of_alpha_arg,
            val_of_beta=value_of_beta_arg,
            param_of_dpth=value_of_deepness_metric,
            param_of_sit=gameState,
            param_of_ag=location_of_agent
        ) #since we are trying to obtain most optimal action, call the function obtaining max value , namely bigger_val.
        my_lst = my_custom_array

        for pairsOfMovesAndResults in my_lst: #going along all action and value tuples
            move, result = pairsOfMovesAndResults #dividing each of the tuples into the action and value
            diff_value = largerOrSmallerNumber - result #get the difference between minimax value and the value in one of these tuples
            neg = diff_value < 0 #specifies the boolean where minimax value is smaller than the value in one of tuple
            pos = diff_value > 0 #specifies the boolean where minimax value is greater than the value in one of tuple
            negativeOrPositive = (pos or neg)
            if negativeOrPositive: #if the value part in a tuple is not equal to the minimax value
                pass #continue
            else:#if the value part in a tuple is equal to the minimax value
                return move #return the corresponding move

    def smaller_val(self, value_of_alpha, value_of_beta, parameter_of_dpth, parameter_of_sit, parameter_of_ag):
        ##############All null checks for the parameters of this function to make the code work in all cases####################
        if (self.index is None) \
                or \
                (self.depth is None) \
                or \
                (value_of_beta is None) \
                or \
                (value_of_beta is None) \
                or \
                (parameter_of_dpth is None) \
                or \
                (parameter_of_sit is None) \
                or \
                (parameter_of_ag is None):
            print("one of the parameters is detected as null !!!!")
            print("an invalid parameter of the function called smaller val iis detected")
            raise Exception("one of the parameters is detected as null !!!!")
        ##############All null checks for the parameters of this function to make the code work in all cases####################
        
        
        all_of_vertices = (+1) * (+ math.inf) #assigning the initial value to the plus infinity as in the lec nt.
        updated_depth_param = parameter_of_dpth + 1 #for covering all locations from 0 to depth for the pacman agent.
        all_feasible_moves = parameter_of_sit.getLegalActions(parameter_of_ag) #obtaining all possible legal actions
        
        ##################Null checks, empty checks, and edge case checks for dealing with all possible cases including also the internal python implementation changes ###
        if all_feasible_moves is None:
            print("the list of all feasible moves is null !!!")
            raise Exception("the list of all feasible moves is null !!!")
        elif len(all_feasible_moves) < 0:
            print("the length of the list of all feasible moves is invalid !!!!")
            raise Exception("the length of the list of all feasible moves is invalid !!!!")
        ##################Null checks, empty checks, and edge case checks for dealing with all possible cases including also the internal python implementation changes ###
        
        
        for feasible_move in all_feasible_moves: #going along all possible legal actions
            updated_sit = parameter_of_sit.generateSuccessor(parameter_of_ag, feasible_move) #generating the successor
            total_number_of_pacmans = parameter_of_sit.getNumAgents() # obtaining the number of agents
            
            ###############################Null checks, imaginary checks, and empty checks for dealing with all possible cases including also the internal python implementation changes ###
            if total_number_of_pacmans < 0 or \
                    total_number_of_pacmans == 0:
                print("invalid number of pacmans found !")
                raise Exception("invalid amount of pacmans !")
            index_value_of_agent = updated_depth_param % total_number_of_pacmans#####obtaining the location of the pacman as index.
            if index_value_of_agent < 0 \
                    or \
                    index_value_of_agent > total_number_of_pacmans \
                    or \
                    index_value_of_agent == total_number_of_pacmans:
                print("the index value of the agent is invalid !!!")
                raise Exception("the index value of the agent is invalid !!!")
           ###############################Null checks, imaginary checks, and empty checks for dealing with all possible cases including also the internal python implementation changes ###
        
        

            # self, val_of_alpha, val_of_beta, param_of_dpth, param_of_sit, param_of_ag
            mnmx_alg = self.lrgSmall(
                val_of_alp=value_of_alpha,
                val_of_bet=value_of_beta,
                par_of_dpt=updated_depth_param,
                par_of_sit=updated_sit,
                par_of_ag=index_value_of_agent
            ) #obtaining minimax value

            value_of_diff = (mnmx_alg - all_of_vertices) #obtaining the difference between minimax value and value initialized as minus infinity.
            if value_of_diff > 0:#do nothing if minimax value is bigger than or equal to value, since they should be pruned (as in lecture)
                pass
            else:
                all_of_vertices = mnmx_alg #assigning the v value to the minimax value if minimax value is smaller (as in lecture)

            vertexMinusAlpha = (all_of_vertices - value_of_alpha)
            alphaDiffZeroOrMore = (vertexMinusAlpha > 0 or vertexMinusAlpha == 0)
            if alphaDiffZeroOrMore:
                pass
            else:  #if value smaller than alpha
                return all_of_vertices #then return the value

            beta_diff = all_of_vertices - value_of_beta
            beta_diff_positive = (beta_diff > 0)
            beta_diff_zero = (beta_diff == 0)
            if beta_diff_positive or beta_diff_zero:#if beta value is bigger than or equal to the v value, then do nothing. In other words, pass.
                pass
            else:
                value_of_beta = all_of_vertices #if beta value is smaller than v value, then assign beta to the v value

        return all_of_vertices #return the value 

    def lrgSmall(self, val_of_alp, val_of_bet, par_of_dpt, par_of_sit, par_of_ag):
        #######covering all possible null cases for dealing with the cases where the python implementer changes the internal implementation somehow or the function is called with edge case arguments
        if (self.index is None) \
                or \
                (self.depth is None) \
                or \
                (val_of_bet is None) \
                or \
                (val_of_alp is None) \
                or \
                (par_of_sit is None) \
                or \
                (par_of_ag is None) \
                or \
                (par_of_dpt is None):
            print("one of the parameters of the function called lrg small is found as null !")
            print("An invalid parameter of the function called lrg small is detected !!")
            raise Exception("one of the parameters of the function called lrg small is found as null !")
            
         #######covering all possible null cases for dealing with the cases where the python implementer changes the internal implementation somehow or the function is called with edge case arguments
        
        
        total_number_of_pacmans = par_of_sit.getNumAgents() #obtaining the total number of agents by using the getNumAgents() function
        wins = par_of_sit.isWin() #defining the winning case boolean variable
        value_of_deepness = self.depth #assigning self.depth to value_of_deepness
        loses = par_of_sit.isLose() #defining the losing case boolean variable
        
        
        deepness_array = list() #creating an empty list which will keep total number of pacmans and self.depth
        deepness_array.append(total_number_of_pacmans) #appending the total number of pacmans to that list
        deepness_array.append(value_of_deepness)#appending the self.depth to that list
        sum_of_deepness_values = math.prod(deepness_array) #multiplying the list elements with math.prod()
        difference_value = par_of_dpt - sum_of_deepness_values #obtaining difference between depth parameter and the total depth value for all possible agents.
        if loses: #since the loosing is a terminal state
            return self.evaluationFunction(par_of_sit) #return the utility of the state
        elif difference_value > 0:#if depth is bigger than the total depth value covering all agents
            return self.evaluationFunction(par_of_sit)#return the utility of that situation
        elif difference_value == 0: #if depth is equal to the total depth value covering all agents
            return self.evaluationFunction(par_of_sit)#return the utility of that situation
        elif wins:#since the winning is a terminal state
            return self.evaluationFunction(par_of_sit)#return the utility of that state
        
        agentLocPositive = (par_of_ag > 0)
        agentLocNegative = (par_of_ag < 0)
        agentNotZero = (agentLocPositive or agentLocNegative) #boolean variable defining whether the agent is min or max.
        if agentNotZero == True: #when the agent is min
            return self.smaller_val(val_of_alp, val_of_bet, par_of_dpt, par_of_sit, par_of_ag)#return the value of min value function
        else: # max agent case, pacman location
            return self.bigger_val(val_of_alp, val_of_bet, par_of_dpt, par_of_sit, par_of_ag) #return the value of max value function

    def bigger_val(self, val_of_alpha, val_of_beta, param_of_dpth, param_of_sit, param_of_ag):
        updated_dpth = (1 + param_of_dpth)
        
        #####################################Null checks done for ensuring that the code works at any possible case ################################
        if (val_of_beta is None) \
                or \
                (val_of_alpha is None) \
                or \
                (param_of_sit is None) \
                or \
                (param_of_ag is None) \
                or \
                (param_of_dpth is None) \
                or \
                (self.index is None) \
                or \
                (self.depth is None):
            print("one of the parameters of the bigger val function is found as null !")
            raise Exception("one of the parameters of the bigger val function is found as null !")
        #####################################Null checks done for ensuring that the code works at any possible case ################################
        
        
        from math import inf
        all_possible_vertices = inf * -1 #initializing the value to negative infinity as we did in lec.
        all_feasible_mvs = param_of_sit.getLegalActions(param_of_ag) #obtaining all possible legal actions by using getLegalActions() function#
        moveLen = len(all_feasible_mvs)#defining the length of the all possible legal actions #############
        
        
        #####################################Null and empty checks done for ensuring that the code works at any possible case ################################
        if all_feasible_mvs is None:
            print("the list of all feasible moves is null !")
            raise Exception("the list of all feasible moves is null !")
        elif moveLen < 0:
            print("invalid length for the list of all feasible moves !")
            raise Exception("invalid length for the list of all feasible moves !")
        elif moveLen == 0:
            print("the list of all possible moves is detected as empty !!")
       #####################################Null and empty checks done for ensuring that the code works at any possible case ################################

    
        
        for mvs in all_feasible_mvs: #going all along the legal moves
            renewed_situation = param_of_sit.generateSuccessor(param_of_ag, mvs) #generate successor
            total_amount_of_agents = param_of_sit.getNumAgents() #obtain the agent number
            pacman_location = updated_dpth % total_amount_of_agents #finding the location of the pacman as index.
            # self, val_of_alpha, val_of_beta, param_of_dpth, param_of_sit, param_of_ag
            largerOrSmaller = self.lrgSmall(
                val_of_alp=val_of_alpha,
                val_of_bet=val_of_beta,
                par_of_dpt=updated_dpth,
                par_of_sit=renewed_situation,
                par_of_ag=pacman_location
            ) #obtaining the minimax value 
            if param_of_dpth > 0:
                pass
            elif param_of_dpth < 0:
                pass
            else: #if the parameter of depth is initiialized to zero
                my_tuple_val = (mvs, largerOrSmaller) 
                global my_custom_array
                my_custom_array.append(my_tuple_val)

            if largerOrSmaller == all_possible_vertices \
                    or \
                    largerOrSmaller > all_possible_vertices: if the value is smaller than or equal to the minimax value
                all_possible_vertices = largerOrSmaller #update value to minimax value

            elif largerOrSmaller < all_possible_vertices: #if minimax value is less than the value.
                pass #do nothing

            beta_difference_value = (all_possible_vertices - val_of_beta) #which keeps the relation between the alpha value and value
            beta_diff_negative = (beta_difference_value < 0)
            beta_diff_zero = (beta_difference_value == 0)
            if beta_diff_negative or beta_diff_zero: #if value is less than or equal to the value of beta
                pass #do nothing
            else:#if the beta value is smaller than value
                return all_possible_vertices #return value

            alpha_difference_value = (all_possible_vertices - val_of_alpha)
            alpha_diff_zero = (alpha_difference_value == 0) #which will be checking whether alpha value is equal to value
            alpha_diff_negative = (alpha_difference_value < 0) #which will be checking whether alpha value is smaller than value
            if alpha_diff_zero or alpha_diff_negative: #if the value is smaller than or equal to alpha value
                pass #do nothing, in other words, pass.
            else: #if the value is greater than the alpha value
                val_of_alpha = all_possible_vertices #assign the alpha value to value

        return all_possible_vertices #return the value


class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def __init__(self, evalFn='scoreEvaluationFunction', depth='2'):
        super().__init__(evalFn, depth)

    def getAction(self, gameState: GameState):
        """
        Returns the expectimax action using self.depth and self.evaluationFunction

        All ghosts should be modeled as choosing uniformly at random from their
        legal moves.
        """
        "*** YOUR CODE HERE ***"
        
        ####doing null checks for the parameters ###############
        if (gameState is None) \
                or \
                (self.depth is None) \
                or \
                (self.index is None):
            print("Game state, depth, or index is None !!")
            raise Exception("Game state, depth, or index is None !!")
        ####doing null checks for the parameters ###############

        
        
        my_custom_stop_string = "Stop" #defining the stop move
        global my_custom_array
        my_custom_array = list()
        larger_val_or_smaller_val = self.value_lrg(0, gameState, 0) #calling the function which finds the max value.
        my_arr = my_custom_array

        
        
        ######################## Null checks and empty checks for the my_arr ##################
        if my_arr is None:
            print("The list which keeps the move and outcome tuples is found as null !!")
            raise Exception("The list which keeps the move and outcome tuples is found as null !!")
        elif len(my_arr) < 0:
            print("The length of the list keeping move outcome tuples is invalid !!")
            raise Exception("The length of the list keeping move outcome tuples is invalid !!")
        elif len(my_arr) == 0:
            print("The length of the list keeping move result tuples is zero !")
            print("The list keeping move result tuples is empty !")
        ######################## Null checks and empty checks for the my_arr ##################
        
        
        
        for move_result_tuples in my_arr: #going all along the action,value tuples 
            move, consequence = move_result_tuples #dividing a tuple in the array into action and value where the action is first and value is second
            diff = larger_val_or_smaller_val - consequence #it will be used for checking the relation between the value and the minimax value
            if diff < 0: #if the minimax value is smaller than value
                pass #do nothing, in other words, pass
            elif diff > 0:#if the minimax value is larger than value
                pass #do nothing, in other words, pass
            else: #if the minimax value is equal to the value
                string_equality_condition = ( #defining a boolean variable which will keep whether the move is equal to the 'Stop' regardless of the case of the 'Stop'.
                    move.lower().__eq__(
                        my_custom_stop_string.lower()
                    )
                )
                if string_equality_condition == True: #if the move is equal to the 'stop' action regardless of its case
                    pass #do not do anything and pass that case
                return move #if the move is not equal to stop, than return that move

    @staticmethod
    def add_weighted_values(num1, num2):
        num1 = num1 + num2
        return num1

    def summation_over_expected_probabilities(self, d, a, s):
        ##############Null checks done for making sure that the algorithm works at any possible case made with or without any faulties############
        if (d is None) \
                or \
                (a is None) \
                or \
                (s is None) \
                or \
                (self.depth is None) \
                or \
                (self.index is None):
            print("one of the parameters of the function called expected value sum is none !")
            raise Exception("one of the parameters of the function called expected value sum is none !")
       ##############Null checks done for making sure that the algorithm works at any possible case made with or without any faulties############
    
    
        updated_depth_val = 1 + d #for covering all possible values from 0 to depth for the pacman index
        all_possible_vertices = 0 #initialization of the expected value over all possible vertices
        all_feasible_moves = s.getLegalActions(a) #obtaining the legal actions
        total_number_of_pacmans = s.getNumAgents() #obtaining the number of agents
        index_value_of_pacman = updated_depth_val % total_number_of_pacmans #obtaining the index of pacman

        
        
        ###########################null checks and empty checks for all_feasible_moves##################################################
        if all_feasible_moves is None:
            print("The list of all feasible moves is null !!!")
            raise Exception("The list of all feasible moves is null !!!")
        feasible_move_list_length = len(all_feasible_moves)
        if feasible_move_list_length < 0:
            print("invalid length of the list of all feasible moves !")
            raise Exception("invalid length of the list of all feasible moves !")
        elif feasible_move_list_length == 0:
            print("Empty list of all feasible moves !")
        ###########################null checks and empty checks for all_feasible_moves##################################################

        for feasible_move in all_feasible_moves: #going all along the legal actions
            newState = s.generateSuccessor(a, feasible_move) #generating the successor
            probability_of_unit_move = float(1 / feasible_move_list_length) #probability to be multiplied with minimax value
            
            #####################################obtaining the minimax value #####################################
            large_value_or_small_value = self.largeOrSmall( 
                dp=updated_depth_val,
                st=newState,
                ag=index_value_of_pacman,
            ) #minimax value
            #####################################obtaining the minimax value #####################################
            
            weighted_value = probability_of_unit_move * large_value_or_small_value #multiplying the minimax value with the probability and obtaining the weighted version.
            all_possible_vertices = self.add_weighted_values(all_possible_vertices, weighted_value) #adding the weighted value to total expected value

        return all_possible_vertices

    def value_lrg(self, dp, st, ag):
        if (self.depth is None) \
                or \
                (self.index is None) \
                or \
                (dp is None) \
                or \
                (st is None) \
                or \
                (ag is None):
            print("One of the parameters of the function called value_lrg is null !!!!")
            raise Exception("One of the parameters of the function called value_lrg is null !!!!")
        from math import inf
        updated_value_of_depth = (1 + dp)
        all_possible_vertices = (+ inf) * (-1) #assigning the initial value to minus infinity.
        total_pacman_number = st.getNumAgents() #obtaining the total number of agents by calling getNumAgents() function.
        feasible_moves = st.getLegalActions(ag) #obtaining the total number of agents by calling getLegalActions() function.
        ######################Null handling and empty case handling for covering all possible test cases ####
        if (feasible_moves is None) \ 
                or \
                (len(feasible_moves) < 0):
            print("Invalid list of feasible moves !!!")
            raise Exception("Invalid list of feasible moves !!!")

        if len(feasible_moves) == 0:
            print("Empty list of feasible moves is found !")
            raise Exception("Empty list of feasible moves is found !")
         ######################Null handling and empty case handling for covering all possible test cases ####

        pacman_ind = updated_value_of_depth % total_pacman_number #obtaining the location of pacman as index.
        
         ######################Null handling and empty case handling for covering all possible test cases ####
        if pacman_ind < 0 \
                or \
                pacman_ind > total_pacman_number \
                or \
                pacman_ind == total_pacman_number:
            print("Invalid Pacman Index !")
            raise Exception("Invalid Pacman Index !")
        if feasible_moves is None:
            print("the list of all feasible moves is Null !")
            raise Exception("the list of all feasible moves is Null !")

        length_of_list_of_feasible_moves = len(feasible_moves)
        if length_of_list_of_feasible_moves < 0:
            print("Invalid length of the list of feasible moves !")
            raise Exception("Invalid length of the list of feasible moves !")
        elif length_of_list_of_feasible_moves == 0:
            print("The list of all feasible moves is empty !")
        ######################Null handling and empty case handling for covering all possible test cases ####

        for feasible_move in feasible_moves: #going along all legal moves
            updated_situation = st.generateSuccessor(
                ag,
                feasible_move
            ) #generating the successor
            value_large_or_value_small = self.largeOrSmall(
                dp=updated_value_of_depth,
                st=updated_situation,
                ag=pacman_ind,
            ) #obtaining the minimax value

            if dp < 0:
                pass
            elif dp > 0:
                pass
            else:
                my_tup = (
                    feasible_move,
                    value_large_or_value_small
                ) #create the tuple made of the action and minimax value.
                global my_custom_array
                my_custom_array.append(
                    my_tup
                ) #appending to the global list keeping the actions and values

            diff_vertex = (all_possible_vertices - value_large_or_value_small) #obtaining the difference between the initial value and minimax value.
            if diff_vertex > 0: #if the initial value bigger than minimax value
                pass # do nothing
            elif diff_vertex == 0: #if the initial value is equal to the minimax value
                pass # do nothing
            else: #if the initial value is smaller than the minimax value
                all_possible_vertices = value_large_or_value_small #assign the initial value to the minimax value

        return all_possible_vertices #return the initial value

    def largeOrSmall(self, dp, st, ag):
        ##########Null checks done for covering all possible test cases #############
        if (self.depth is None) \
                or \
                (self.index is None) \
                or \
                (dp is None) \
                or \
                (st is None) \
                or \
                (ag is None):
            print("One of the parameters of the function called value_lrg is null !!!!")
            raise Exception("One of the parameters of the function called value_lrg is null !!!!")
         ##########Null checks done for covering all possible test cases #############

        #########Trial and error approach and the help of Berkeley website instructions and the pdf description are used for depth limit #
        value_of_depth = self.depth
        if value_of_depth < 0:
            print("Invalid depth !")
            raise Exception("Invalid depth !")
        pacman_wins = st.isWin()
        total_number_of_pacmans = st.getNumAgents()
        pacman_fails = st.isLose()
        is_pacman_index = (ag == 0)
        summmation_of_depths = total_number_of_pacmans * value_of_depth
        is_not_pacman_index = (
                ag > 0 or ag < 0
        )
         #########Trial and error approach and the help of Berkeley website instructions and the pdf description are used for depth limit #

        difference_dpth = dp - summmation_of_depths
        if pacman_fails == True:  # if pacman loses the game
            return self.evaluationFunction(st)  # call evaluation function
        elif pacman_wins == True:  # if pacman wins the game
            return self.evaluationFunction(st)  # call evaluation function
        elif difference_dpth > 0:  # for handling maximum depth of the recursion
            return self.evaluationFunction(st)
        elif difference_dpth == 0:  # for dealing with the maximum depth of the recursion
            return self.evaluationFunction(st)
        elif dp == 0:
            return self.evaluationFunction(st)
        elif is_not_pacman_index == True: #is not pacman location
            return self.summation_over_expected_probabilities(
                d=dp,
                a=ag,
                s=st
            ) #calling expected value function
        elif is_pacman_index == True: #pacman location
            return self.value_lrg(
                dp=dp,
                st=st,
                ag=ag
            ) #calling max value function


def betterEvaluationFunction(currentGameState: GameState):
    """
    Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
    evaluation function (question 5).

    DESCRIPTION: <write something here so we know what you did>
    """
    "*** YOUR CODE HERE ***"
    
    
    #############I have used almost same logic with the normal evaluation function I have used in question1. The things I changed are only the weight of the 
    ############closeness to food. I have increased the value of the numerator of the reciprocal including the distance to food in its denominator. I do 
    ######this increase so that the evaulation function have better value in case the distance of agent to food decreases.
    #########I have also kept in attention that the pacman is in the aim of eating foods, being away from ghosts and eating capsules. I have used these
    ######features or negatives of them or reciprocals of them by considering their relation with the total value of evaluation function. 
    if currentGameState is None:
        print("the current game state is detected as null !!!")
        raise Exception("the current game state is detected as null !!!")
    updatedPosition = currentGameState.getPacmanPosition()
    newFoodGridElement = currentGameState.getFood()
    updatedGhostSituations = currentGameState.getGhostStates()
    scrTim = list()
    for element in updatedGhostSituations:
        scrTim.append(element.scaredTimer)

    if (currentGameState is None) or \
            (updatedPosition is None) or \
            (newFoodGridElement is None) or \
            (updatedGhostSituations is None) or \
            (scrTim is None):
        print("invalid game state, pacman position, food, ghost state or scared times !")
        raise Exception("invalid game state, pacman position, food, ghost state or scared times !")
    if len(scrTim) < 0 or \
            len(scrTim) == 0:
        print("invalid length value for new scared times list or empty new scared times list !!!")

    coordinateDifferencesKeeper = list()
    listVersionOfNewFood = newFoodGridElement.asList()
    for lisElem in listVersionOfNewFood:
        my_custom_heuristic_value = manhattanDistance(updatedPosition, lisElem)
        coordinateDifferencesKeeper.append(my_custom_heuristic_value)

    closness_to_grid = 0
    value_of_error_situation = -1003
    lengthOfDiffKeeper = len(coordinateDifferencesKeeper)
    atLeastTwo = (lengthOfDiffKeeper > 2 or lengthOfDiffKeeper == 2)
    lessThanZero = (lengthOfDiffKeeper < 0)
    equalToOne = (lengthOfDiffKeeper == 1)
    equalToZero = (lengthOfDiffKeeper == 0)
    initial_index_value = 0

    if coordinateDifferencesKeeper is None:
        print("null list encountered right now !!!!!!!!!!")
        raise Exception("null list encountered right now !!!!!!!!!!")

    if atLeastTwo:
        closness_to_grid = coordinateDifferencesKeeper[initial_index_value]
        my_custom_range = range(0, lengthOfDiffKeeper)
        for w in my_custom_range:
            cond1 = (coordinateDifferencesKeeper[w] < closness_to_grid)
            cond2 = (coordinateDifferencesKeeper[w] == closness_to_grid)
            cndt = cond1 or cond2
            if cndt:
                closness_to_grid = coordinateDifferencesKeeper[w]
    elif lessThanZero:
        print("invalid length value encountered currently !")
        raise Exception("invalid length value encountered currently !")
    elif equalToZero:
        print("empty coordinate difference list found currently!")
    elif equalToOne:
        closness_to_grid = coordinateDifferencesKeeper[initial_index_value]

    adversary_array = list()
    for adversary_state in updatedGhostSituations:
        adversaryPos = adversary_state.getPosition()
        heur = manhattanDistance(updatedPosition, adversaryPos)
        adversary_array.append(heur)

    lengthOfAdv = len(adversary_array)
    if adversary_array is None:
        print("null list of adversaries !!!!!!!!!!")
        raise Exception("null list of adversaries !!!!!!!!!!")
    elif lengthOfAdv < 0:
        print("invalid length value found for adversary list !")
        raise Exception("invalid length value found for adversary list !")
    elif lengthOfAdv == 0:
        print("empty list of adversary detected !")
    else:
        adversary_closeness_metric = adversary_array[initial_index_value]
        my_range = range(0, lengthOfAdv)
        for y in my_range:
            second_condition_bool = (adversary_array == adversary_closeness_metric)
            first_condition_bool = (adversary_array[y] < adversary_closeness_metric)
            resulting_condition = (first_condition_bool or second_condition_bool)
            if not resulting_condition:
                pass
            else:
                adversary_closeness_metric = adversary_array[y]

    subseq_value_of_score = currentGameState.getScore()
    denominator1 = (adversary_closeness_metric + 15)
    denominator2 = (8 + closness_to_grid)

    first_denominator_condition = (denominator1 != 0)
    second_denominator_condition = (denominator2 != 0)
    denominatorsNotEqualToZero = first_denominator_condition and second_denominator_condition
    if denominatorsNotEqualToZero:
        firstPart = (float(-2.97) / denominator1)
        secondPart = (float(5.07) / denominator2)
        thirdPart = 43 * subseq_value_of_score
        my_evaluation_variable_with_weights = (firstPart + secondPart + thirdPart) - len(currentGameState.getCapsules())
        return 17 + 5 * my_evaluation_variable_with_weights

    return value_of_error_situation


# Abbreviation
better = betterEvaluationFunction
