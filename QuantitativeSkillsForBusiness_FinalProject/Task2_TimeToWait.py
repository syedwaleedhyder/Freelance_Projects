# -*- coding: utf-8 -*-
"""
Created on Sun Apr 21 01:08:31 2019

@author: Syed Waleed Hyder
"""
def time_to_wait(principal, rate, accrued):
    time = (accrued - principal)/(principal*rate)
    return time

principal = int(input("Enter the principal investment: "))
rate = float(input("Enter the annual interest rate: "))
accrued = int(input("Enter the amount you want: "))

time = time_to_wait(principal, rate, accrued)
print("Number of years to get the {} is {}".format(accrued, round(time)))