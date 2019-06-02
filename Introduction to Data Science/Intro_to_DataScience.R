library(ISLR)

linearMod <- lm(mpg~horsepower, data=Auto)
print(linearMod)
print(summary(linearMod))

predict(linearMod,data.frame(horsepower=c(98)),interval="prediction")

plot(Auto$horsepower,Auto$mpg)
abline(linearMod,lwd=5,col="blue")

par(mfrow = c(2,2))
plot(linearMod)

#TASK 2
rm(list=ls())
attach(Auto)
mpg01 <- ifelse( mpg > median(mpg), yes = 1, no = 0)
Auto <- data.frame(Auto, mpg01)
cor(Auto[,-9])
pairs(Auto)
#Data Normalization
Auto <- data.frame(mpg01, apply(cbind(cylinders, weight, displacement, horsepower, acceleration), 
                                2, scale), year)
#train and test datasets
train <-  (year %% 2 == 0) # if the year is even (%%)
test <-  !train
Auto.train <-  Auto[train,]
Auto.test <-  Auto[test,]
mpg01.test <-  mpg01[test]

logModel <- glm(mpg01~cylinders+displacement+horsepower+weight, data=Auto, subset=train)
logModel.predict <-  predict(logModel, Auto.test)
predict01 <- ifelse( logModel.predict > 0.5, yes = 1, no = 0)
mean(predict01 != mpg01.test)
print(summary(logModel))

#TASK3
rm(list=ls())
attach(iris)
print(summary(iris))

set.seed(1)

my_iris <- data.frame(Sepal.Length, Sepal.Width)
species <- iris$Species

head(my_iris)

kc <- kmeans(my_iris, 3)

table(species, kc$cluster)
table(kc$centers)

plot(Petal.Length ~ Petal.Width, data = my_iris, col = kc$cluster)