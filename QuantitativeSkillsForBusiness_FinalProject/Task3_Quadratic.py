# -*- coding: utf-8 -*-
"""
Created on Sat Apr 20 23:51:39 2019

@author: Syed Waleed Hyder
"""
import math
def computeRoot(a,b,det):
    aux_first = -b + math.sqrt(det)
    aux_second = -b - math.sqrt(det)
    return aux_first/(2*a), aux_second/(2*a)

a = int(input("Please input value of a: "))
b = int(input("Please input value of b: "))
c = int(input("Please input value of c: "))
det = (b**2)-(4*a*c)
if det < 0:
    print("There are 0 x-intercepts.")
else:
    first_root, second_root = computeRoot(a,b,det)
    if(det != 0):
        print("This fucntion has 2 x-intercepts.")
        print('They are at ({}, 0) and ({}, 0)'.format(round(first_root, 3), round(second_root, 3)))
    else:
        print("This fucntion has 1 x-intercept.")
        print('It is at ({}, 0)'.format(first_root))