# inference.py
# ----
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).

import busters
from util import manhattanDistance
import game


class DiscreteDistribution(dict):
    """
    A DiscreteDistribution models belief distributions and weight distributions
    over a finite set of discrete keys.
    """

    def __getitem__(self, key):
        self.setdefault(key, 0)
        return dict.__getitem__(self, key)

    def copy(self):
        """
        Return a copy of the distribution.
        """
        return DiscreteDistribution(dict.copy(self))

    def argMax(self):
        """
        Return the key with the highest value.
        """
        if len(self.keys()) == 0:
            return None
        all = list(self.items())
        values = [x[1] for x in all]
        maxIndex = values.index(max(values))
        return all[maxIndex][0]

    def total(self):
        """
        Return the sum of values for all keys.
        """
        return float(sum(self.values()))

    def normalize(self):
        """
        Normalize the distribution such that the total value of all keys sums
        to 1. The ratio of values for all keys will remain the same. In the case
        where the total value of the distribution is 0, do nothing.

        >>> dist = DiscreteDistribution()
        >>> dist['a'] = 1
        >>> dist['b'] = 2
        >>> dist['c'] = 2
        >>> dist['d'] = 0
        >>> dist.normalize()
        >>> list(sorted(dist.items()))
        [('a', 0.2), ('b', 0.4), ('c', 0.4), ('d', 0.0)]
        >>> dist['e'] = 4
        >>> list(sorted(dist.items()))
        [('a', 0.2), ('b', 0.4), ('c', 0.4), ('d', 0.0), ('e', 4)]
        >>> empty = DiscreteDistribution()
        >>> empty.normalize()
        >>> empty
        {}
        """
        "*** YOUR CODE HERE ***"
        all_possible_keys = self.keys()  # extract all keys
        all_possible_values = self.values()  # obtain all values
        summation_of_values = 0
        for my_val in all_possible_values:  # obtain summation of all values
            summation_of_values = summation_of_values + my_val

        summation_of_all_values = summation_of_values
        is_summation_zero = (summation_of_all_values == 0)
        is_summation_non_zero = (summation_of_all_values < 0 or summation_of_all_values > 0)

        for single_key in all_possible_keys:
            if is_summation_zero:  # if the summation of all values is zero
                self[single_key] = float(0)  # to handle with divide by zero exception
            elif is_summation_non_zero:
                val_of_wgh = (1 / summation_of_all_values)
                self[single_key] = val_of_wgh * self[single_key]  # normalization of each value
            else:
                print("An invalid situation happened about summation of values !")

    def sample(self):
        """
        Draw a random sample from the distribution and return the key, weighted
        by the values associated with each key.

        >>> dist = DiscreteDistribution()
        >>> dist['a'] = 1
        >>> dist['b'] = 2
        >>> dist['c'] = 2
        >>> dist['d'] = 0
        >>> N = 100000.0
        >>> samples = [dist.sample() for _ in range(int(N))]
        >>> round(samples.count('a') * 1.0/N, 1)  # proportion of 'a'
        0.2
        >>> round(samples.count('b') * 1.0/N, 1)
        0.4
        >>> round(samples.count('c') * 1.0/N, 1)
        0.4
        >>> round(samples.count('d') * 1.0/N, 1)
        0.0
        """
        "*** YOUR CODE HERE ***"
        from random import choices
        first_index_value = 0
        all_possible_keys = self.keys()  # obtain all keys of distribution
        all_possible_values = self.values()  # extract all possible values of distribution
        list_of_all_possible_keys = list(all_possible_keys)  # convert keys to a list
        list_of_all_possible_values = list(all_possible_values)  # convert values to a list
        randomization_outcome = choices(
            list_of_all_possible_keys,
            list_of_all_possible_values
        )  # draw random sample from the distribution
        outcome_sample_value = randomization_outcome[first_index_value]  # extract the key
        return outcome_sample_value  # return the extracted key


class InferenceModule:
    """
    An inference module tracks a belief distribution over a ghost's location.
    """

    ############################################
    # Useful methods for all inference modules #
    ############################################

    def __init__(self, ghostAgent):
        """
        Set the ghost agent for later access.
        """
        self.ghostAgent = ghostAgent
        self.index = ghostAgent.index
        self.obs = []  # most recent observation position

    def getJailPosition(self):
        return (2 * self.ghostAgent.index - 1, 1)

    def getPositionDistributionHelper(self, gameState, pos, index, agent):
        try:
            jail = self.getJailPosition()
            gameState = self.setGhostPosition(gameState, pos, index + 1)
        except TypeError:
            jail = self.getJailPosition(index)
            gameState = self.setGhostPositions(gameState, pos)
        pacmanPosition = gameState.getPacmanPosition()
        ghostPosition = gameState.getGhostPosition(index + 1)  # The position you set
        dist = DiscreteDistribution()
        if pacmanPosition == ghostPosition:  # The ghost has been caught!
            dist[jail] = 1.0
            return dist
        pacmanSuccessorStates = game.Actions.getLegalNeighbors(pacmanPosition, \
                                                               gameState.getWalls())  # Positions Pacman can move to
        if ghostPosition in pacmanSuccessorStates:  # Ghost could get caught
            mult = 1.0 / float(len(pacmanSuccessorStates))
            dist[jail] = mult
        else:
            mult = 0.0
        actionDist = agent.getDistribution(gameState)
        for action, prob in actionDist.items():
            successorPosition = game.Actions.getSuccessor(ghostPosition, action)
            if successorPosition in pacmanSuccessorStates:  # Ghost could get caught
                denom = float(len(actionDist))
                dist[jail] += prob * (1.0 / denom) * (1.0 - mult)
                dist[successorPosition] = prob * ((denom - 1.0) / denom) * (1.0 - mult)
            else:
                dist[successorPosition] = prob * (1.0 - mult)
        return dist

    def getPositionDistribution(self, gameState, pos, index=None, agent=None):
        """
        Return a distribution over successor positions of the ghost from the
        given gameState. You must first place the ghost in the gameState, using
        setGhostPosition below.
        """
        if index == None:
            index = self.index - 1
        if agent == None:
            agent = self.ghostAgent
        return self.getPositionDistributionHelper(gameState, pos, index, agent)

    def getObservationProb(self, noisyDistance, pacmanPosition, ghostPosition, jailPosition):
        """
        Return the probability P(noisyDistance | pacmanPosition, ghostPosition).
        """
        "*** YOUR CODE HERE ***"
        if pacmanPosition is None:
            print("Pacman position parameter is None !")
            raise Exception("Pacman position parameter is None !")
        if jailPosition is None:
            print("Jail position parameter is None !")
            raise Exception("Jail position parameter is None !")
        if ghostPosition is None:
            print("Ghost position parameter is None !")
            raise Exception("Ghost position parameter is None !")
        manh_distance_heuristic_val = manhattanDistance(pacmanPosition, ghostPosition)
        check_ghost_pos_jail_pos_equality = (ghostPosition == jailPosition)
        check_ghost_pos_jail_pos_inequality = (ghostPosition != jailPosition)
        check_noisy_dist_nullity = (noisyDistance is None)
        if check_ghost_pos_jail_pos_equality:
            if check_noisy_dist_nullity:  # in case the ghost is reached and eaten
                return 1
            else:  # in case the ghost is not reached and eaten
                return 0
        elif check_ghost_pos_jail_pos_inequality:
            if check_noisy_dist_nullity:  # in case the ghost is reached and eaten
                return 0
            else:  # in case the ghost is not reached and eaten
                observ_prob_outcome = busters.getObservationProbability(
                    noisyDistance,
                    manh_distance_heuristic_val
                )
                return observ_prob_outcome

    def setGhostPosition(self, gameState, ghostPosition, index):
        """
        Set the position of the ghost for this inference module to the specified
        position in the supplied gameState.

        Note that calling setGhostPosition does not change the position of the
        ghost in the GameState object used for tracking the true progression of
        the game.  The code in inference.py only ever receives a deep copy of
        the GameState object which is responsible for maintaining game state,
        not a reference to the original object.  Note also that the ghost
        distance observations are stored at the time the GameState object is
        created, so changing the position of the ghost will not affect the
        functioning of observe.
        """
        conf = game.Configuration(ghostPosition, game.Directions.STOP)
        gameState.data.agentStates[index] = game.AgentState(conf, False)
        return gameState

    def setGhostPositions(self, gameState, ghostPositions):
        """
        Sets the position of all ghosts to the values in ghostPositions.
        """
        for index, pos in enumerate(ghostPositions):
            conf = game.Configuration(pos, game.Directions.STOP)
            gameState.data.agentStates[index + 1] = game.AgentState(conf, False)
        return gameState

    def observe(self, gameState):
        """
        Collect the relevant noisy distance observation and pass it along.
        """
        distances = gameState.getNoisyGhostDistances()
        if len(distances) >= self.index:  # Check for missing observations
            obs = distances[self.index - 1]
            self.obs = obs
            self.observeUpdate(obs, gameState)

    def initialize(self, gameState):
        """
        Initialize beliefs to a uniform distribution over all legal positions.
        """
        self.legalPositions = [p for p in gameState.getWalls().asList(False) if p[1] > 1]
        self.allPositions = self.legalPositions + [self.getJailPosition()]
        self.initializeUniformly(gameState)

    ######################################
    # Methods that need to be overridden #
    ######################################

    def initializeUniformly(self, gameState):
        """
        Set the belief state to a uniform prior belief over all positions.
        """
        raise NotImplementedError

    def observeUpdate(self, observation, gameState):
        """
        Update beliefs based on the given distance observation and gameState.
        """
        raise NotImplementedError

    def elapseTime(self, gameState):
        """
        Predict beliefs for the next time step from a gameState.
        """
        raise NotImplementedError

    def getBeliefDistribution(self):
        """
        Return the agent's current belief state, a distribution over ghost
        locations conditioned on all evidence so far.
        """
        raise NotImplementedError


class ExactInference(InferenceModule):
    """
    The exact dynamic inference module should use forward algorithm updates to
    compute the exact belief function at each time step.
    """

    def initializeUniformly(self, gameState):
        """
        Begin with a uniform distribution over legal ghost positions (i.e., not
        including the jail position).
        """
        self.beliefs = DiscreteDistribution()
        for p in self.legalPositions:
            self.beliefs[p] = 1.0
        self.beliefs.normalize()

    def observeUpdate(self, observation, gameState):
        """
        Update beliefs based on the distance observation and Pacman's position.

        The observation is the noisy Manhattan distance to the ghost you are
        tracking.

        self.allPositions is a list of the possible ghost positions, including
        the jail position. You should only consider positions that are in
        self.allPositions.

        The update model is not entirely stationary: it may depend on Pacman's
        current position. However, this is not a problem, as Pacman's current
        position is known.
        """
        "*** YOUR CODE HERE ***"
        self.beliefs.normalize()  # normalize all current possible beliefs before sampling
        all_possible_locations_of_ghosts = self.allPositions  # extract all possible locations
        for possible_loc in all_possible_locations_of_ghosts:  # go through all possible locations
            location_of_the_jail = self.getJailPosition()  # obtain the jail position
            pacman_agent_location = gameState.getPacmanPosition()  # obtain the pacman agent position
            probability_observation = self.getObservationProb(
                observation,
                pacman_agent_location,
                possible_loc,
                location_of_the_jail
            )  # obtain the observation probability
            self.beliefs[possible_loc] = probability_observation * self.beliefs[
                possible_loc]  # update the current value of the belief

        self.beliefs.normalize()  # finally, normalize new beliefs again

    def elapseTime(self, gameState):
        """
        Predict beliefs in response to a time step passing from the current
        state.

        The transition model is not entirely stationary: it may depend on
        Pacman's current position. However, this is not a problem, as Pacman's
        current position is known.
        """
        "*** YOUR CODE HERE ***"
        discrete_distribution_of_probabilities = DiscreteDistribution()
        all_possible_locations = self.allPositions
        for valid_location in all_possible_locations:
            updated_distribution_of_position = self.getPositionDistribution(
                gameState,
                valid_location
            )
            distribution_of_belief_values = self.getBeliefDistribution()
            single_belief = distribution_of_belief_values[valid_location]
            for updated_location in updated_distribution_of_position:
                weighted_position = single_belief * updated_distribution_of_position[updated_location]
                discrete_distribution_of_probabilities[updated_location] = discrete_distribution_of_probabilities[
                                                                               updated_location] + weighted_position
        self.beliefs = discrete_distribution_of_probabilities

    def getBeliefDistribution(self):
        return self.beliefs


class ParticleFilter(InferenceModule):
    """
    A particle filter for approximately tracking a single ghost.
    """

    def __init__(self, ghostAgent, numParticles=300):
        InferenceModule.__init__(self, ghostAgent)
        self.setNumParticles(numParticles)

    def setNumParticles(self, numParticles):
        self.numParticles = numParticles

    def initializeUniformly(self, gameState):
        """
        Initialize a list of particles. Use self.numParticles for the number of
        particles. Use self.legalPositions for the legal board positions where
        a particle could be located. Particles should be evenly (not randomly)
        distributed across positions in order to ensure a uniform prior. Use
        self.particles for the list of particles.
        """
        self.particles = []
        "*** YOUR CODE HERE ***"
        if gameState is None:
            print("Game state parameter is None !")
            raise Exception("Game state parameter is None !")

        all_valid_locations = self.legalPositions
        length_of_valid_loc_list = len(self.legalPositions)
        total_particle_number = self.numParticles

        for valid_loc in all_valid_locations:
            valid_loc_list = []
            valid_loc_list += [valid_loc]
            particle_num_per_loc = total_particle_number / length_of_valid_loc_list
            result_of_integer_div = int(particle_num_per_loc)
            weighted_lst = valid_loc_list * result_of_integer_div
            self.particles.extend(weighted_lst)

    def observeUpdate(self, observation, gameState):
        """
        Update beliefs based on the distance observation and Pacman's position.

        The observation is the noisy Manhattan distance to the ghost you are
        tracking.

        There is one special case that a correct implementation must handle.
        When all particles receive zero weight, the list of particles should
        be reinitialized by calling initializeUniformly. The total method of
        the DiscreteDistribution may be useful.
        """
        "*** YOUR CODE HERE ***"

        distribution_of_discrete_prob = DiscreteDistribution()
        agent_pacman_loc = gameState.getPacmanPosition()
        all_particles = self.particles
        location_of_jail = self.getJailPosition()
        number_of_all_particles = self.numParticles

        for sng_particle in all_particles:
            observation_probability = self.getObservationProb(
                observation,
                agent_pacman_loc,
                sng_particle,
                location_of_jail
            )
            distribution_of_discrete_prob[sng_particle] = observation_probability + distribution_of_discrete_prob[
                sng_particle]

        all_values_of_dict = distribution_of_discrete_prob.values()
        summation_of_distribution_values = sum(all_values_of_dict)
        check_zero_sum = (summation_of_distribution_values == 0)
        check_non_zero_sum = (
                summation_of_distribution_values < 0 or
                summation_of_distribution_values > 0
        )

        if check_non_zero_sum:
            lst_of_all_sampled_values = list()
            custom_particle_number_range = range(0, number_of_all_particles)
            lst_of_all_sampled_values = [
                distribution_of_discrete_prob.sample()
                for single_particle in custom_particle_number_range
            ]
            self.particles = list(lst_of_all_sampled_values)
        elif check_zero_sum:
            self.initializeUniformly(gameState)

    def elapseTime(self, gameState):
        """
        Sample each particle's next state based on its current state and the
        gameState.
        """
        "*** YOUR CODE HERE ***"
        all_particles_updated = list()
        all_possible_particles = self.particles
        for prt_single in all_possible_particles:
            distribution_of_locations = self.getPositionDistribution(
                gameState,
                prt_single
            )
            sampled_from_distribution = distribution_of_locations.sample()
            all_particles_updated.append(sampled_from_distribution)
        self.particles = list(all_particles_updated)

    def getBeliefDistribution(self):
        """
        Return the agent's current belief state, a distribution over ghost
        locations conditioned on all evidence and time passage. This method
        essentially converts a list of particles into a belief distribution.
        
        This function should return a normalized distribution.
        """
        "*** YOUR CODE HERE ***"
        discrete_distribution_of_probabilities = DiscreteDistribution()
        all_possible_particles = self.particles
        for single_particle in all_possible_particles:
            particle_in_distribution = (single_particle in discrete_distribution_of_probabilities)
            particle_not_inside_distribution = (single_particle not in discrete_distribution_of_probabilities)
            if particle_in_distribution:
                discrete_distribution_of_probabilities[single_particle] = 1 + discrete_distribution_of_probabilities[
                    single_particle]
            elif particle_not_inside_distribution:
                discrete_distribution_of_probabilities[single_particle] = 1
        discrete_distribution_of_probabilities.normalize()
        return discrete_distribution_of_probabilities


class JointParticleFilter(ParticleFilter):
    """
    JointParticleFilter tracks a joint distribution over tuples of all ghost
    positions.
    """

    def __init__(self, numParticles=600):
        self.setNumParticles(numParticles)

    def initialize(self, gameState, legalPositions):
        """
        Store information about the game, then initialize particles.
        """
        self.numGhosts = gameState.getNumAgents() - 1
        self.ghostAgents = []
        self.legalPositions = legalPositions
        self.initializeUniformly(gameState)

    def initializeUniformly(self, gameState):
        """
        Initialize particles to be consistent with a uniform prior. Particles
        should be evenly distributed across positions in order to ensure a
        uniform prior.
        """
        self.particles = []
        "*** YOUR CODE HERE ***"
        if gameState is None:
            print("The game state parameter in initialization function is None !")
            raise Exception("The game state parameter in initialization function is None !")

        from itertools import product  # for cartesian product
        from random import choices  # for specific number of random choices from cartesian product list
        total_number_of_ghosts = self.numGhosts  # obtaining total number of ghosts
        total_number_of_particles = self.numParticles  # obtaining total number of particles
        all_valid_locations = self.legalPositions  # obtaining all legal positions
        particle_number_range = range(0, total_number_of_particles)

        # obtaining the cartesian product of ghosts and locations to reach all location pairs made of ghost loc and valid position loc.
        # the position of ghost will be picked from the result of the cartesian product
        cartesian_product_result = product(
            all_valid_locations,
            repeat=total_number_of_ghosts
        )  # obtaining cartesian product for all ghosts
        cartesian_product_result_lst = list(
            cartesian_product_result)  # converting the cartesian product result to a list
        cartesian_lst_len = len(cartesian_product_result_lst)
        randomized_selections = choices(
            cartesian_product_result_lst,
            k=cartesian_lst_len
        )  # selecting k random choices from the cartesian product result list.
        length_of_randomized = len(randomized_selections)
        for particle_index in particle_number_range:  # go along all particles
            particle_group_ind = particle_index % length_of_randomized
            particle_structure = cartesian_product_result_lst[particle_group_ind]  # assign evenly
            self.particles.append(particle_structure)

    def addGhostAgent(self, agent):
        """
        Each ghost agent is registered separately and stored (in case they are
        different).
        """
        self.ghostAgents.append(agent)

    def getJailPosition(self, i):
        return (2 * i + 1, 1)

    def observe(self, gameState):
        """
        Resample the set of particles using the likelihood of the noisy
        observations.
        """
        observation = gameState.getNoisyGhostDistances()
        self.observeUpdate(observation, gameState)

    def observeUpdate(self, observation, gameState):
        """
        Update beliefs based on the distance observation and Pacman's position.

        The observation is the noisy Manhattan distances to all ghosts you
        are tracking.

        There is one special case that a correct implementation must handle.
        When all particles receive zero weight, the list of particles should
        be reinitialized by calling initializeUniformly. The total method of
        the DiscreteDistribution may be useful.
        """
        "*** YOUR CODE HERE ***"
        if (observation is None) or (gameState is None):
            print("One of the parameters from observation and gameState is None for the observeUpdate function")
            raise Exception(
                "One of the parameters from observation and gameState is None for the observeUpdate function")

        all_possible_particles = self.particles  # obtaining all particles
        total_number_of_particles = self.numParticles  # obtaining the total number of the particles
        total_number_of_ghosts = self.numGhosts  # obtaining the total number of the ghosts
        agent_pacman_location = gameState.getPacmanPosition()  # obtaining the pacman position

        custom_range_of_ghost_number = range(0, total_number_of_ghosts)

        instance_of_discrete_distribution = DiscreteDistribution()
        for particle_index in all_possible_particles:
            first_val_of_evidence_probability = 1
            for ghost_ind in custom_range_of_ghost_number:
                single_instance_of_ghost_observation = observation[ghost_ind]
                single_instance_of_particle = particle_index[ghost_ind]
                location_of_jail = self.getJailPosition(ghost_ind)
                probability_of_observation = self.getObservationProb(
                    single_instance_of_ghost_observation,
                    agent_pacman_location,
                    single_instance_of_particle,
                    location_of_jail
                )  # obtaining observation probability
                first_val_of_evidence_probability = first_val_of_evidence_probability * probability_of_observation
            instance_of_discrete_distribution[particle_index] = instance_of_discrete_distribution[
                                                                    particle_index] + first_val_of_evidence_probability

        keys_of_discrete_distribution = instance_of_discrete_distribution.values()
        summation_of_distribution = sum(keys_of_discrete_distribution)
        summation_of_distribution = float(summation_of_distribution)
        check_summation_non_zero = (summation_of_distribution > 0 or summation_of_distribution < 0)
        check_summation_zero = (summation_of_distribution == 0)
        if check_summation_non_zero:
            lst_of_all_smp = list()
            my_particle_number_range = range(0, total_number_of_particles)
            for particle_index_val in my_particle_number_range:
                lst_of_all_smp.append(instance_of_discrete_distribution.sample())
            self.particles = list(lst_of_all_smp)
        elif check_summation_zero:
            self.initializeUniformly(gameState)

    def elapseTime(self, gameState):
        """
        Sample each particle's next state based on its current state and the
        gameState.
        """
        newParticles = []
        for oldParticle in self.particles:
            newParticle = list(oldParticle)  # A list of ghost positions
            # now loop through and update each entry in newParticle...
            "*** YOUR CODE HERE ***"
            length_of_updated_particle = len(newParticle)
            my_custom_particle_range = range(0, length_of_updated_particle)
            for particle_index in my_custom_particle_range:
                agent_ghost_adversary = self.ghostAgents[
                    particle_index
                ]
                position_distribution_instance = self.getPositionDistribution(
                    gameState,
                    newParticle,
                    particle_index,
                    agent_ghost_adversary
                )
                sampled_pos_from_position_distribution = position_distribution_instance.sample()
                newParticle[particle_index] = sampled_pos_from_position_distribution
            """*** END YOUR CODE HERE ***"""
            newParticles.append(tuple(newParticle))
        self.particles = newParticles


# One JointInference module is shared globally across instances of MarginalInference
jointInference = JointParticleFilter()


class MarginalInference(InferenceModule):
    """
    A wrapper around the JointInference module that returns marginal beliefs
    about ghosts.
    """

    def initializeUniformly(self, gameState):
        """
        Set the belief state to an initial, prior value.
        """
        if self.index == 1:
            jointInference.initialize(gameState, self.legalPositions)
        jointInference.addGhostAgent(self.ghostAgent)

    def observe(self, gameState):
        """
        Update beliefs based on the given distance observation and gameState.
        """
        if self.index == 1:
            jointInference.observe(gameState)

    def elapseTime(self, gameState):
        """
        Predict beliefs for a time step elapsing from a gameState.
        """
        if self.index == 1:
            jointInference.elapseTime(gameState)

    def getBeliefDistribution(self):
        """
        Return the marginal belief over a particular ghost by summing out the
        others.
        """
        jointDistribution = jointInference.getBeliefDistribution()
        dist = DiscreteDistribution()
        for t, prob in jointDistribution.items():
            dist[t[self.index - 1]] += prob
        return dist
