# -*- coding: utf-8 -*-
"""
Created on Sun Apr 21 00:22:30 2019

@author: Syed Waleed Hyder
"""

import math
def simple_interest(P,r,t):
    """
    P = Principal Amount
    I = Interest Amount
    r = Rate of Interest per year in decimal
    """
    I = P*r*t
    A = P + I
    return A

def compound_interest(P, r, n, t):
    """
    A = the future value of the investment/loan, including interest
    P = the principal investment amount (the initial deposit or loan amount)
    r = the annual interest rate (decimal)
    n = the number of times that interest is compounded per unit t
    t = the time the money is invested or borrowed for
    """
    if(n == "NaN"):
        A = P * math.exp(r*t)
    else:
        A = P * ((1 + r/n)**(n*t))
    return A

principal = int(input("Enter the principal investment: "))
rate = float(input("Enter the annual interest rate: "))
time = int(input("Enter the time the money is invested for: "))

print("Simple interest gives {} dollars".format(simple_interest(principal, rate, time)))
print("12 compounds gives {} dollars".format(compound_interest(principal, rate, 12, time)))
print("365 compounds gives {} dollars".format(compound_interest(principal, rate, 365, time)))
print("Continual compoubding gives {} dollars".format(compound_interest(principal, rate, "NaN", time)))
