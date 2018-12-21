```
 _______           _                            _       
(_______)         | |                          | |      
 _______ ____   __| | ____ ___  ____  _____  __| |_____ 
|  ___  |  _ \ / _  |/ ___) _ \|    \| ___ |/ _  (____ |
| |   | | | | ( (_| | |  | |_| | | | | ____( (_| / ___ |
|_|   |_|_| |_|\____|_|   \___/|_|_|_|_____)\____\_____|
                                                        
                                                                              
```
----------------------

<a href="https://github.com/LaBayanne/Andromeda.git"> Lien du repo github ! (privé)</a>
</br>
<a href="https://docs.google.com/document/d/1InqnvMFz5X4-hOMiPz7J0wUCnQpDNI-ywEv3xuSAwLo/edit?usp=sharing">Lien du "cahier des charge" du projet</a>

-------------------------------
#### src_basic

Execute Game.class to launch the game.

In the first version of the game, you begin with the green planet. Your enemy is in red.
All the others planets are neutral. The number on the planet is the number of starships that you can generate.
Select one of your planet with left click. You can add multiple planet to you selection by pressing `Ctrl` when you click.
You can select all you planets with a double click.
By default, you send 100% of your starship stock. You can change this value with the wheel.

You can select the destination of you starships with right click.
 * If the destination is an ally, you will augment the stock of the selected planet.
 * If the destination is an enemy, or a neutral planet, you will decrease its stock.
	 	If it's stock decrease to 0, you became the owner of this planet.

 You can redirect your squad by clicking on one of the starship composing the squad, and selecting a new target.

 You can quick save the game when you want, by clicking `Ctrl + s`. You can restore when you want with `Ctrl + l`.
 	
 The number of starship on planet owned by players augment each frame, depending of the generation speed of the planet.
 If you are the last player controlling planets, you win the game.
 If it'a an other player who controle the game, you lose :)


### src_advanced

Execute Game.class to launch the game.

I think you should test this version before reading this.

This version include a main menu, similarly to a windows desktop. Click on Start or press escape to open a submenu.
In this menu, you can launch a new game (and select the number of players), load an existing game (there is only one saved game on the same moment) and quit the game.

On the main game, similarly to the first version of the game, you control a file and must take all the file of the screen. Like on the main menu you can click on start or press escape top open a submenu, where you can go back to the main menu, quit the game, save the game or load a game. This menu stop the game too.

The game includes three different "planets" and three different "starships".

* Planets :

	- File : 			
			- production speed : 		average
			- type of starships : 	Finger
	- Directory : 		
			- production speed : 		fast
			- type of starships : 	Arrow
	- Application : 	
			- production speed : 		slow
			- type of starships : 	MoveCursor
						
* Starships :

	- Finger :			
			- damage :					2
			- speed : 					average
			- cost :					1
	- Arrow : 			
			- damage : 				1
			- speed : 					fast
			- cost : 					1
	- MoveCursor : 	
			- damage : 				4
			- speed :					slow
			- cost :					3
			

After a moment, Avast will appear on the game and destroy planets and starships it encounter. Its number of virus increase with the time like other planets, and create a massive attack on random planets when the number 300 is reached. Nobody can attack Avast.


We found a bug in the pathfinding : starships can sometimes go through planets.

At level of improvements, we could add more musics and effects, offer three or more save files, add defeat and victory menus, more types of planets and starships...

---------------
 Works with `openjdk 1.8` and `openjfx 1.8`.
 Test with `Junit5`.

