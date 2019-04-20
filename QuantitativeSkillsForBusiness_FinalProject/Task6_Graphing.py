# -*- coding: utf-8 -*-
"""
Created on Sat Apr 20 22:21:01 2019

@author: Syed Waleed Hyder
"""

import matplotlib.pyplot as plt 
import numpy as np 


def calculateY(function, X):
    
    # function = 1 : y = mx + b
    # function = 2 : y = A*(B^x)
    # function = 3 : y = P*[e^(r*t)]
    # function = 4 : y = a*x^2 +b*x +c
    # function = 5 : y = a/(x-h) + k
    
    if(function == 1):
        m = 2
        b = 1
        Y = m*X + b 
    if(function == 2):
        A = 1.2
        B = 1.5
        Y = A*(B**X)
    if(function == 3):
        P = 0.9
        r = 0.5
        t = X
        Y = P*(np.exp(r*t)) 
    if(function == 4):
        a = 1
        b = 2
        c = 1
        Y = a*(X**2) + b*X + c
    if(function ==5):
        a = 1
        h = 2
        k = 1
        Y = a/(X-h) + k
    
    return Y

    

# setting the x - coordinates 
x = np.arange(0, 10, 0.1) 
# setting the corresponding y - coordinates 
y = calculateY(1, x)
#y = np.sin(x)

# potting the points 
plt.plot(x, y, label="y=mx+b")
# setting the corresponding y - coordinates 
y = calculateY(2, x)
# potting the points 
plt.plot(x, y, label="y=A*(B^x)")
# setting the corresponding y - coordinates 
y = calculateY(3, x)
# potting the points 
plt.plot(x, y, label="y=P*[e^(r*t)]")
# setting the corresponding y - coordinates 
y = calculateY(4, x)
# potting the points 
plt.plot(x, y, label="y=a*x^2 +b*x +c")
# setting the corresponding y - coordinates 
y = calculateY(5, x)
# potting the points 
plt.plot(x, y, label="y=a/(x-h)+k") 
  
  
# naming the x axis 
plt.xlabel('x - axis') 
# naming the y axis 
plt.ylabel('y - axis') 
# giving a title to my graph 
#plt.title('Two lines on same graph!') 
  
# show a legend on the plot 
plt.legend()
# function to show the plot 
plt.show()
