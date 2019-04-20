# -*- coding: utf-8 -*-
"""
Created on Sat Apr 20 23:23:32 2019

@author: Syed Waleed Hyder
"""
def average_cost_calculator(A, B, C, D, E):
    total_A = A + C*D
    total_B = B + C*E
    average_cost = (total_A + total_B)/2
    return average_cost


A = int(input("Enter cost of product A: "))
B = int(input("Enter cost of product B: "))
C = int(input("Enter annual maintenance cost: "))
D = int(input("Enter years product A lasted: "))
E = int(input("Enter years product B lasted: "))

average_cost = average_cost_calculator(A, B, C, D, E)
print("Average cost of each product is: ", average_cost)
    
