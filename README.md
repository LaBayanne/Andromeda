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
### How to play:
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

---------------
 Works with `openjdk 1.8` and `openjfx 1.8`.
 Test with `Junit5`.

