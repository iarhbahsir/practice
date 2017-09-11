# get training data, validation data, test data, layer neuron numbers, learning rate, mini-batch size, number of training epochs
# function for mini-batch stochastic gradient descent
# function for updating each mini batch
# function for backpropagation
# function for sigmoid computation
# function for sigmoid derivative computation
# function for output for a given input
# function for accuracy

# function for neuron cost calculation
# function for activation

import numpy as np
import random
import mnist_loader

class ANN(object):

    def __init__(self, layer_sizes, init_biases=None, init_weights=None):
        # create layers of nn
        self.layer_sizes = np.asarray(list(layer_sizes))

        if(init_biases != None and init_weights != None):
            self.biases = init_biases
            self.weights = init_weights
        else:
            # create biases for all neurons except for input neurons
            self.biases = [np.random.randn(neurons, 1) for neurons in layer_sizes[1:]]
            # create weight matrices for neurons accessible by [neuron in layer x, neuron in layer x-1] (for ease in calculations)
            self.weights = [np.random.randn(dest_neuron, source_neuron) for dest_neuron, source_neuron in zip(layer_sizes[1:], layer_sizes[:-1])]

    def train(self, learning_rate, mini_batch_size, epochs_to_train, training_data, validation_data=None, test_data=None, periodically_evaluate=False, epochs_between_evaluation=1):
        # uses mini-batch stochastic gradient descent
        print "Started training neural network..."
        # outer for loop to keep track of epochs trained
        #nn_info = open("nn_info", 'a')
        #nn_info_alt = open("nn_info_alt", 'w')
        for curr_epoch in xrange(epochs_to_train):
            # shuffle data for better performance
            shuffled_training_data = np.array(training_data)
            random.shuffle(shuffled_training_data)

            # inner loop for training all mini_batches in randomized data
            mb = 0
            for curr_mini_batch in [shuffled_training_data[x: x + mini_batch_size] for x in xrange(0, len(shuffled_training_data), mini_batch_size)]:
                print "Processing epoch " + str(curr_epoch) + ", mini-batch: " + str(mb) + "/" + str(len(shuffled_training_data)/mini_batch_size) + "\r"
                self.train_mini_batch(learning_rate, curr_mini_batch)
                if(mb % 100 == 0):
                    #nn_info_alt.writelines("Mini-batch" + str(mb) + "\n\n Biases:\n" + str(self.biases) + "\n\nWeights:\n" + str(self.weights))
                    np.save("nn_biases.npy", self.biases)
                    np.save("nn_weights.npy", self.weights)
                mb = mb + 1

            print "Finished epoch " + str(curr_epoch) + "/" + str(epochs_to_train)

            #nn_info.writelines("Epoch " + str(curr_epoch) + ":\n\n Biases:\n" + str(self.weights) + "\n\nWeights:\n" + str(self.biases))

            # periodically evaluate accuracy of nn if desired using test data if given
            if(test_data != None and periodically_evaluate):
                if(curr_epoch % epochs_between_evaluation == 0):
                    print "Calculating current accuracy..."
                    percent_accurate = self.evaluate_accuracy(test_data)
                    print "Current accuracy is " + str(percent_accurate) + "% (" + str(percent_accurate/100.0 * len(training_data)) + "/" + str(len(training_data)) + ")"

        #nn_info.close()

    def train_mini_batch(self, learning_rate, mini_batch):
        # create matrix to store sum of bias and weight cost gradient components
        del_bias_sum = np.zeros(np.shape(self.biases))
        del_weight_sum = np.zeros(np.shape(self.weights))

        for set in mini_batch:
            # use backpropagation to get matrices of weight and bias cost gradient components for the neurons
            del_bias, del_weight = self.backpropagate(set[0], set[1])

            # add to total gradients for bias and weight
            del_bias_sum = np.add(del_bias_sum, del_bias)
            del_weight_sum = np.add(del_weight_sum, del_weight)

            # update the weights and the biases simultaneously
            self.biases = np.subtract(self.biases, np.multiply(del_bias_sum, learning_rate / len(mini_batch)))

    def backpropagate(self, x_in, y_in):
        # calculate activation matrix
        weighted_inputs, activations = self.feedforward(x_in)

        # compute output layer error
        errors = np.array(self.biases)
        for x in xrange(len(errors)):
            errors[x] = np.zeros(np.shape(errors[x]))
        errors[-1] = np.multiply(self.del_activation_cost(activations[-1], y_in), self.sigmoid_derivative(weighted_inputs[-1]))

        # backpropagate error, finding error for all layers
        for layer_index in xrange(len(activations) - 2, 0, -1):
            errors[layer_index - 1] = np.multiply(np.dot(np.transpose(self.weights[layer_index]), errors[layer_index]), self.sigmoid_derivative(weighted_inputs[layer_index]))

        # compute cost gradient matrices using activation and error matrices
        del_weight = np.array(self.weights)
        """
        for layer_index in xrange(len(del_weight)):
            for neuron in xrange(len(del_weight[layer_index])):
                del_weight[layer_index][neuron] = np.zeros(np.shape(del_weight[layer_index][neuron]))
        """
        """
        for layer_index in xrange(len(self.weights)):
            for dest_neuron in xrange(len(self.weights[layer_index])):
                for source_neuron in xrange(len(self.weights[layer_index][dest_neuron])):
                    del_weight[layer_index][dest_neuron][source_neuron] = activations[layer_index][source_neuron] * errors[layer_index][dest_neuron]
        """
        for layer_index in xrange(len(self.weights)):
            del_weight[layer_index] = np.dot(activations[layer_index + 1], np.transpose(errors[layer_index]))
        return errors, del_weight

    def feedforward(self, inputs):
        # set input layer
        activations = [np.zeros((neurons, 1)) for neurons in self.layer_sizes]
        weighted_inputs = np.array((activations))
        weighted_inputs[0] = np.array(inputs)
        activations[0] = np.array(inputs)

        # compute activations
        for layer_index in xrange(1, len(weighted_inputs)):
            weighted_inputs[layer_index] = np.dot(self.weights[layer_index - 1], activations[layer_index - 1]) + self.biases[layer_index - 1]
            activations[layer_index] = self.sigmoid(weighted_inputs[layer_index])
        """
        for layer_index in xrange(1, len(weighted_inputs)):
            for dest_neuron in xrange(len(weighted_inputs[layer_index])):
                for source_neuron in xrange(len(weighted_inputs[layer_index - 1][dest_neuron])):
                    weighted_inputs[layer_index][dest_neuron] += self.weights[layer_index - 1][dest_neuron][source_neuron] * weighted_inputs[layer_index - 1][source_neuron]
                    weighted_inputs[layer_index][dest_neuron] += self.biases[layer_index - 1][dest_neuron]
            activations[layer_index] = self.sigmoid(weighted_inputs[layer_index])
        """
        return weighted_inputs, activations

    def sigmoid(self, to_sigmoid):
        sigmoided = np.array(np.shape(to_sigmoid))
        #for neuron in xrange(len(to_sigmoid)):
        #    sigmoided[neuron] = 1 / (1 + np.exp(-1 * to_sigmoid[neuron]))

        sigmoided = 1 / (1 + np.exp(-1 * to_sigmoid))

        return sigmoided

    def del_activation_cost(self, activations, y):
        return activations - y

    def sigmoid_derivative(self, to_sigmoid):
        return self.sigmoid(to_sigmoid) * (1 - self.sigmoid(to_sigmoid))

    def evaluate_accuracy(self, test_data):
        num_correct = 0.0
        for set in test_data:
            activation = self.feedforward(set[0])[1][-1]
            print "Predicted " + str(np.argmax(activation))
            print "Actual " + str(set[1])
            print
            if(np.argmax(activation) == set[1]):
                num_correct += 1

        return (100.0 * num_correct) / (len(test_data))

if __name__ == '__main__':
    np.set_printoptions(threshold=np.inf)
    training, validation, test = mnist_loader.load_data_wrapper()
    layer_sizes = ([784, 30, 10])
    """
    try:
        init_biases = np.load("nn_biases.npy")
        init_weights = np.load("nn_weights.npy")
        nn = ANN(layer_sizes, init_biases, init_weights)
        print "Loaded predetermined weights and biases."
    except IOError:
        print "Could not load predetermined weights and biases."
        nn = ANN(layer_sizes)
    """
    nn = ANN(layer_sizes)
    #nn.evaluate_accuracy(test)
    nn.train(3.0, 10, 30, training, validation, test, True, 1)

