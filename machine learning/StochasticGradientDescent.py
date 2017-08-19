import numpy as np
import random
import math
from GradientDescent import GradientDescent
class StochasticGradientDescent(GradientDescent):

    def __init__(self, learningRate, yList, xLists):
        GradientDescent.__init__(self, learningRate, yList, xLists)
        self.setAdjRate(1)
        self.tolerance = 10 ** -20

    def innerCost(self, trainingSetNum):
        return ((self.hypothesis(trainingSetNum) - self.yValues[trainingSetNum]) ** 2) /2

    def delta(self, featureNum, trainingSetNum):
        diff = (self.hypothesis(trainingSetNum) - self.yValues[trainingSetNum]) * self.trainingSets[featureNum, trainingSetNum]
        return self.learningRate * diff

    def shuffleTrainingSets(self):
        numSets = self.numTrainingSets()
        currIndex = 0
        while(currIndex < numSets):
            randIndex = random.randint(0, self.numTrainingSets() - 1)
            randTS = np.asarray(list(self.trainingSets[:, randIndex]))
            self.trainingSets[:, randIndex] = np.asarray(self.trainingSets[:, currIndex])
            self.trainingSets[:, currIndex] = randTS
            # print self.trainingSets
            currIndex += 1

    def train(self):
        self.shuffleTrainingSets()
        tsNum = 0
        tempCoefficients = np.zeros((len(self.coefficients)))
        currCost = self.innerCost(tsNum)
        currCostTotal = 0
        prevCostTotal1 = 0
        prevCostTotal2 = 0
        while (True):
            currCoeff = 0
            while (currCoeff < len(tempCoefficients)):
                tempCoefficients[currCoeff] = self.coefficients[currCoeff] - self.delta(currCoeff, tsNum)
                currCoeff += 1

            tsNum = (tsNum + 1) % self.numTrainingSets()
            if(tsNum == 0) :
                prevCostTotal2 = prevCostTotal1
                prevCostTotal1 = currCostTotal
                currCostTotal = 0

            self.coefficients = list(tempCoefficients)

            currCostTotal += currCost
            currCost = self.innerCost(tsNum)

            #print currCost

            if(currCost > 10 ** 50):
                self.coefficients = np.zeros(len(self.coefficients))
                self.learningRate /= 1000.0
                #print self.learningRate
                self.train()
                break

            if(prevCostTotal1 != 0 and prevCostTotal2 != 0):
                percentChange = (prevCostTotal1 - prevCostTotal2) * 100 / prevCostTotal1
                if( abs(percentChange) <= 0.001):
                    break
                elif(percentChange <= -0.5):
                    self.learningRate /= self.adjRate
                elif(percentChange >= 0.1):
                    self.learningRate *= self.adjRate
            #elif(currCost >= currCostTotal/2):
            #    self.learningRate /= self.adjRate


    def addTrainingSets(self, yList, xLists):
        yValuesAdded = np.asarray(list(yList))
        trainingSetsAdded = np.ones((len(xLists[0]) + 1, len(xLists)))

        r = 0
        while (r < len(xLists)):
            c = 0
            while (c < len(xLists[r])):
                trainingSetsAdded[c + 1, r] = xLists[r][c]
                c += 1
            r += 1

        self.yValues = np.concatenate([self.yValues, yValuesAdded])
        self.trainingSets = np.concatenate((self.originalTrainingSets, trainingSetsAdded), axis=1)
        self.originalTrainingSets = np.copy(self.trainingSets)
        self.scale()

if __name__ == '__main__':
    # print "Hello"
    xLists = [[1, 2, 3, 4], [6, 7, 8, 9], [10, 11, 12, 13], [14, 15, 16, 17]]
    yList = [5, 6, 7, 8]
    sgd = StochasticGradientDescent(0.001, yList, xLists)
    sgd.shuffleTrainingSets()
    sgd.train()
    c1 = list(sgd.coefficients)
    p1 = sgd.predict(xLists[2])
    xListsToAdd = [[18, 19, 20, 21], [22, 23, 24, 25], [26, 27, 28, 29]]
    yListToAdd = [9, 10, 11]
    sgd.train()
    c2 = list(sgd.coefficients)
    p2 = sgd.predict(xLists[2])
    print
    print c1
    print p1
    print c2
    print p2

