# AI-robot

##Project for the ID1214 Artificial Intelligence course at KTH

The program creates a map and randomly plots obstacles into it. A robot is spawned with the goal to visit 
all the reachable cells in the map in an efficient way. When it is done with all of them it will know and will stop running.

From the Map.java class one can easily customize the appearance of the map by editing the following global variables:

__Customizations__
* width: Number of columns in the map
* length: Number of rows in the map
* CELL_WIDTH: Width of a cell in pixels.
* OBSTACLE_PERCENTAGE: [0-100] percentage of blocks which will be presented as obstacles
* SEGREGATION: [0-100] level of segregation of these obstacles, i.e how far are from each other. 0 will create big clusters.
100 will create small obstacles everywhere. Its better to try it out with a low percentage of obstacles to appreciate the difference.
* MAZE_MODE_ACTIVE: boolean which plots the obstacles in the form of a maze, instead of randomly. (under development)

__Example pictures__

####Example 1
![alt text][AI-robot1]

![alt tag][AI-robot2]

####Example 2
![alt text][AI-robot3]

![alt text][AI-robot4]

####Maze
![alt text][AI-robot5]

![alt text][AI-robot6]



[AI-robot1]: https://raw.githubusercontent.com/juanluisrto/AI-robot/master/bin/images/AI-robot1.PNG 
[AI-robot2]: https://raw.githubusercontent.com/juanluisrto/AI-robot/master/bin/images/AI-robot2.PNG
[AI-robot3]: https://raw.githubusercontent.com/juanluisrto/AI-robot/master/bin/images/AI-robot3.PNG
[AI-robot4]: https://raw.githubusercontent.com/juanluisrto/AI-robot/master/bin/images/AI-robot4.PNG
[AI-robot5]: https://raw.githubusercontent.com/juanluisrto/AI-robot/master/bin/images/AI-robot5.PNG
[AI-robot6]: https://raw.githubusercontent.com/juanluisrto/AI-robot/master/bin/images/AI-robot6.PNG
