# -*- coding: utf-8 -*-
"""
Created on Sat Apr 20 23:39:43 2019

@author: Syed Waleed Hyder
"""

def time_work_together(timeA, timeB):
    rateA = 1/timeA
    rateB = 1/timeB
    rate_total = rateA + rateB
    time_total = 1/rate_total
    
    return time_total

timeA = int(input("Enter hours for Person A completes the task: "))
timeB = int(input("Enter hours for Person B completes the task: "))

print("Person A completes the task in {} hours".format(timeA))
print("Person B completes the task in {} hours".format(timeB))
total_time =round(time_work_together(timeA, timeB), 3)
print("Person A and B complete the task in {} hours".format(total_time))