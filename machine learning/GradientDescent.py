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

import numpy

# Create feature vectors: n+1 by m matrix of training sets
# Create parameter vector: n+1 by 1 matrix of coefficients, with x0 initialized to 1
# Create m by 1 vector of y values
# Write function to find hypothesis using current coefficients and values of a training set
# Write function to find delta value for a feature
# Write function to simultaneously update parameters until convergence
# Write function to adjust learning rate

class GradientDescent:

    #for n independent variables and m training sets, n arrays of m values are passed in, with first array being y values
    def __init__(self, yList, *xList):
        self.x = xList
        self.y = yList
        for value in self.x:
            for v in value:
                print v
        for value in self.y:
            print value

    def hypothesis(self, trainingSet):

    def


if __name__ == '__main__':
    print "Hello"
    xList = [1, 2, 3, 4]
    yList = [5, 6, 7, 8]
    gd = GradientDescent(yList, xList)