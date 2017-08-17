# Gradient descent for mutilinear regression
# Take in arrays of training sets as input
# Return an array of function coefficients

# Pass in arrays as constructor
# Set coefficients to desired values manually (as initial guess)
# Set learning rate
# Calculate delta value of given independent variable
# Public train function to produce coefficients
# Feature scaling with mean normalization (do last, probably as separate class)
# Add training sets (leave out, add in stochastic version)
# Pass in convergence tolerance?

import numpy as np

# Create feature vectors: n+1 by m matrix of training sets, with x0 initialized to 1
# Create parameter vector: n+1 by 1 matrix of coefficients
# Create m by 1 vector of y values
# Write function to find hypothesis using current coefficients and values of a training set
# Write function to find delta value for a feature
# Write function to simultaneously update parameters until convergence
# Write function to adjust learning rate

class GradientDescent:

    #for n independent variables and m training sets, n arrays of m values are passed in, with first array being y values
    def __init__(self, learningRate, yList, xLists):
        # Define learning rate
        self.learningRate = learningRate

        # Create m by 1 vector of y values
        self.yValues = np.asarray(list(yList))

        # Create parameter vector: n+1 by 1 matrix of coefficients initialized to 0
        self.coefficients = np.zeros((len(xLists) + 1))

        # Create feature vectors: n+1 by m matrix of training sets, with x0 initialized to 1
        self.trainingSets = np.ones((len(xLists[0]) + 1, len(xLists)))

        r = 0
        while(r < len(xLists)) :
            c = 0
            while(c < len(xLists[r])) :
                self.trainingSets[c + 1, r] = xLists[r][c]
                c += 1
            r += 1

        print self.yValues
        print self.trainingSets
        print self.coefficients

    # Write function to find hypothesis using current coefficients and values of a training set
    def hypothesis(self, trainingSetNum):
        return np.matmul(self.coefficients, self.trainingSets[:, trainingSetNum])

    # Write function to find delta value for a feature
    def delta(self, featureNum):
        tsNum = 0
        sum = 0
        while(tsNum < len(self.trainingSets[0])) :
            sum += (self.hypothesis(tsNum) - self.yValues[tsNum]) * self.trainingSets[featureNum, tsNum]
            tsNum += 1
        return self.learningRate * sum / self.trainingSets.shape[1]

    # Write function to find delta value for a feature
    def costFunction(self):
        cost = 0
        ts = 0
        while(ts < self.trainingSets.shape[1]) :
            cost += (self.hypothesis(ts) - self.yValues[ts]) ** 2
            ts += 1
        return cost / (2 * self.trainingSets.shape[1])

    # Write function to simultaneously update parameters until convergence
    def train(self):
        currCost = self.costFunction()
        tempCoefficients = np.zeros((len(self.coefficients)))
        currCoeff = 0;
        prevCosts = [0]*10
        prevCostIndex = 0
        while(True):
            prevCosts[prevCostIndex] = currCost
            prevCostIndex = (prevCostIndex + 1) % len(prevCosts)
            while(currCoeff < len(tempCoefficients)):
                tempCoefficients[currCoeff] = self.coefficients[currCoeff] - self.delta(currCoeff)
                currCoeff += 1
            currCoeff = 0
            print tempCoefficients
            print currCost
            self.coefficients = list(tempCoefficients)
            currCost = self.costFunction()
            if(prevCosts[0] == prevCosts[5] and prevCosts[5] == prevCosts[9]):
                break

    def predict(self, xList):
        xValues = np.asarray([1] + xList)
        xValues = xValues.transpose()
        return np.matmul(self.coefficients, xValues)

if __name__ == '__main__':
    print "Hello"
    xLists = [[1, 2, 3, 4], [6, 7, 8, 9], [10, 11, 12, 13], [14, 15, 16, 17]]
    yList = [5, 6, 7, 8]
    gd = GradientDescent(0.001, yList, xLists)
    gd.train()
    p = gd.predict(xLists[0])
    print p
    print gd.coefficients