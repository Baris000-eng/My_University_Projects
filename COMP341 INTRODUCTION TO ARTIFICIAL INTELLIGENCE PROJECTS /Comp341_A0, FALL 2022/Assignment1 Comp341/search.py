# search.py
# ---------
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
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]


def basedOnBrdthOrDpth(sNm, prob):
    upperVersion = sNm.upper()
    lowerVersion = sNm.lower()
    lowerBfsStr = "bfs"
    upperBfsStr = "BFS"
    lowerDfsStr = "dfs"
    upperDfsStr = "DFS"

    # If BFS algo
    if ((upperVersion.__eq__(upperBfsStr)) is True) or \
            ((lowerVersion.__eq__(lowerBfsStr)) is True):
        struct_of_search_algo = util.Queue()  # construct a queue by using util.py.
    else:
        # If DFS algo
        if ((sNm.casefold().__eq__(lowerDfsStr)) is not False) or \
                ((sNm.casefold().__eq__(upperDfsStr)) is not False):
            from util import Stack
            struct_of_search_algo = Stack()  # construct a stack by using util.py.

    visNod = list()
    init_state = prob.getStartState()  # extraction of the initial problem state
    all_acts = list()
    pshed = (init_state, all_acts)
    struct_of_search_algo.push(pshed)
    emp = struct_of_search_algo.isEmpty()
    if emp is True:  # checks whether the created data structure is empty.
        print("- !!! Empty structure !!! -")
        raise Exception("The structure instance is empty ")
    else:
        while emp is not True: # while the stack structure or queue structure is not empty
            stack_elm = struct_of_search_algo.pop() # pop from data structure
            locOfAg, actOfAg = stack_elm # extract move and location
            if prob.isGoalState(locOfAg) is not True:
                ddd = 2
                zzz = 2
            else:  # if we have not reached the aimed state yet!
                return actOfAg  # return the agent actions
            if locOfAg in visNod:
                fff = 3
            else:
                visNod.append(locOfAg) # append to visited node list
                sucList = prob.getSuccessors(locOfAg) #obtain the successors
                for ndd in sucList: #for all elements in successor list
                    import itertools  # for using itertools library
                    moveToLst = list()
                    secElm = ndd[1] #action of one successor
                    moveToLst.append(secElm)
                    concatenation = itertools.chain(actOfAg, moveToLst)  # for chaining agent actions with move list.
                    renewed_move_lst = list(concatenation)
                    iniElm = ndd[0] #location of one successor
                    pushed_elem = (iniElm, renewed_move_lst)
                    struct_of_search_algo.push(pushed_elem) 


def depthFirstSearch(problem):
    depth_text = "Depth based"
    print(depth_text)
    type_of_search = "DFS"
    print(type_of_search)
    dep_res = basedOnBrdthOrDpth(type_of_search, problem)  # calling basedOnBrdthOrDpth() function with the DFS name.
    return dep_res


def breadthFirstSearch(problem):
    breadth_text = "Breadth based"
    print(breadth_text)
    name_of_searching_al = "BFS"
    print(name_of_searching_al)
    bre_res = basedOnBrdthOrDpth(name_of_searching_al,
                                 problem)  # calling basedOnBrdthOrDpth function with the BFS name.
    return bre_res


def uniformCostSearch(problem):
    # print("*****Start of the uniform cost searching *****")
    from util import PriorityQueue
    struct_of_prio_que = PriorityQueue()
    if struct_of_prio_que is None:  # controls if the priority queue object is null
        print("Null priority queue error has been thrown !!")  # related printed message
        raise Exception("********* The priority queue instance is null !!!!!!!!! *******")  # related exception
    else:
        all_moves_of_the_agent = list()
        ini_st = problem.getStartState()  # obtaining the initial state of the problem
        trav = list()
        prio_num = 0
        fir = (ini_st, all_moves_of_the_agent)
        sec = prio_num
        struct_of_prio_que.push(fir, sec)
        isPrioEmp = struct_of_prio_que.isEmpty()  # defining a boolean variable named isPrioEmp to check whether priority queue is empty.
        if isPrioEmp is True:  # check whether the priority queue instance is empty.
            print("***** !!!! Empty priority queue object !!!! **** ")
        else:
            while isPrioEmp is False:  # whenever the priority queue is not empty.
                sit, moves_of_agents = struct_of_prio_que.pop()  # pop moves and states from that queue
                if sit not in trav:
                    trav.append(sit)
                    objectiveStateReached = problem.isGoalState(
                        sit)  # defining a boolean variable which keeps whether we reached the goal state
                    if objectiveStateReached is not True:
                        aab = 2
                    else:  # if we have already reached the state which is the objective.
                        return moves_of_agents  # return the agent moves
                    subseq_lst = problem.getSuccessors(sit)  # extract successor elements
                    for subseq, mov, cst_of_mov in subseq_lst:
                        if subseq in trav:
                            my_small_lst = [subseq, trav]
                        else:
                            mov_lst = list()  # initialize a list called mov_lst
                            mov_lst.append(mov)  # append moves to the move list
                            from itertools import chain  # use the chain library
                            tot_cst = problem.getCostOfActions(moves_of_agents)  # get the cost corresponding to the
                            # actions
                            merged = chain(moves_of_agents, mov_lst)  # merge moves_of_agents with move list
                            upd_mov = list(merged)  # convert to a list
                            upd_cst = cst_of_mov + tot_cst  # alter the cost
                            init_elem = (subseq, upd_mov)
                            sec_elem = upd_cst
                            struct_of_prio_que.push(init_elem,
                                                    sec_elem)  # push the successor , moves, and cost to priority queue
                else:
                    trv_lst = [sit, trav]


def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def aStarSearch(problem, heuristic=nullHeuristic):
    from util import PriorityQueue  # for using the structure tool under util.py
    prio_que_str = PriorityQueue()  # creating an instance of priority queue
    if prio_que_str is None:  # if the priority queue object is none
        print("Null priority queue error has been thrown !!")  # print a related message
        raise Exception("********* The priority queue instance is null !!!!!!!!! *******")  # throw a related exception
    else:
        all_agent_moves = list()
        first_sta = problem.getStartState()  # get the first state of problem
        if first_sta is None:  # control if that state is none
            print("The initial state of the problem is null !")
            raise Exception("!!! *** Null initial state is found !!! ****")
        trav = list()
        prio_value = 0
        fe = (first_sta, all_agent_moves)
        se = prio_value
        prio_que_str.push(fe, se)
        empty = prio_que_str.isEmpty()  # defining a boolean variable named empty to keep whether priority queue is empty.
        if empty is True:
            print("***** !!!! Empty priority queue object !!!! **** ")
        else:  # if the priority queue is not empty
            while empty is False:  # while the priority queue is not empty
                situ, moves_of_agents = prio_que_str.pop()  # pop states and agent moves from that priority queue
                if situ not in trav:
                    trav.append(situ)  # create a list of the nodes which have not been traversed yet.
                    aimStateReached = problem.isGoalState(
                        situ)  # defining a boolean variable (named aimStateReached) which keeps whether the goal situation is reached.
                    if aimStateReached is not True:
                        gg = 4
                        # print("aim not reached yet !")
                    else:
                        # print("aim is reached now !")
                        return moves_of_agents
                    subseq_lst = problem.getSuccessors(situ)  # extract successor elements
                    for lst_elem in subseq_lst:
                        subseq, mov, cst_of_mov = lst_elem
                        he = heuristic(subseq, problem)
                        if subseq in trav:
                            my_tiny_lst = []
                            if my_tiny_lst is None:
                                print("Null list error !")
                                raise Exception("List is null !")
                            my_tiny_lst.append(subseq)
                            my_tiny_lst.append(trav)
                            # print(my_tiny_lst)
                        else:
                            from itertools import chain  # for utilizing chain under itertools library
                            mov_lst = list()  # creation of move list named mov_lst
                            mov_lst.append(mov)  # appending moves to mov_lst
                            tot_cst = problem.getCostOfActions(moves_of_agents)  # extract the total cost of actions
                            combined = chain(moves_of_agents, mov_lst)  # chain moves of agents to move list
                            altered_mov = list(combined)
                            altered_cst = cst_of_mov + tot_cst
                            elem_zero = (subseq, altered_mov)
                            sec_elem = altered_cst
                            lis_with_heur = [he, sec_elem]
                            val_with_heur = sum(lis_with_heur)  # extract the summation of the heuristic and cost
                            prio_que_str.push(elem_zero, val_with_heur)

                else:
                    trav_lst = []
                    if trav_lst is None:
                        print("List is null !")
                        raise Exception("Null list is found !")
                    trav_lst.append(situ)
                    trav_lst.append(trav)
                    # print(trav_lst)


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
