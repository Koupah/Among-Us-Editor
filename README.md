# Among-Us-Editor
### DISCORD: https://koupah.club/aueditor ###  
#### ( https://discord.com/invite/HTm3mwK ) ####    
Join the discord to share your profiles, make suggestions and many other things!  
### Partnered with https://github.com/AmongUsPlus/among-plus-installer, their discord: https://discord.gg/jAyHbqn ###  
  
## Download Instructions ##  
### Video Tutorial: https://youtu.be/OS1px12PtGQ ###  
### Requires Java 7 or above https://www.java.com/en/download/ ###  
Download Link: https://github.com/Koupah/Among-Us-Editor/releases  
Download the .jar file by clicking on it, then run it once it's finished downloading  
From there, it's as simple as choosing your settings then applying & running the game  
  
NOTE: You need to relaunch/have your game closed when using this! 
The game only seems to read settings on launch, so use this then launch your game!  

## Videos ##  
1.51 (Latest) by Koupah: https://youtu.be/OS1px12PtGQ  
1.51 by Discord Member: https://youtu.be/7KABvBbP1hM  
1.45 by Koupah: https://youtu.be/rlv_1qFbZFM  
1.0 by Koupah: https://youtu.be/2wRJmS7DKpc  
  
## Secret/Glitched Color (Fortegreen) ##
The secret color is a lighter version of the dark green, in lobbies chat normally won't work, you can't customize your character. People also see your name as "???".  
In game, your dead body will be red, in meetings you'll be red and in chat you'll be red.
Therefore there can be 3 greens and 2 reds, and I believe if multiple people have a different color value above 12, there can be multiple glitched greens (Untested).  
![](images/differences.png)

#### This glitched color is referenced in this wiki page https://among-us.fandom.com/wiki/Fortegreen ~~https://among-us.fandom.com/wiki/Player~~ ####
![](images/fortegreen.png)  
![](images/fortegreen1.png)  
  


If you upload a video showing or using this please give credit or atleast link this GitHub page!

My Discord: Koupah#5129

## Credits ##  
All Contributors in the discord!
Slymeball: Has helped me bring support to mac/linux via his issues. Thank you.
camas: Created the playerPrefs index table  

If you'd like to help w/ something or receive credit, you can contact me on my discord listed above 

## Information for those interested: ##  

There were originally a bunch more commits (50+ lost) but I messed up and forced my whole workspace and it messed everything up  

Custom names that include "\\", ")" or "\[" will result in a "Banned from Room" message and you also cannot include "," in your name as it breaks the file.  
Names longer than 10 characters will also not work in online games, local play lets you make a lobby but I'm not sure if others can join.

Indexes of playerPrefs file
|Index|Name used by Among Us|Type|Comment|
|--|--|--|--|
|0|lastPlayerName|String||
|1|touchConfig|int|Controls 0 Mouse, 1 Mouse and Keyboard|
|2|colorConfig|uint|0-11, 12+ for Fortegreen ~~glitched~~ color|
|3|ignored unknown||Default `1`|
|4|ignored sendName|bool|Default `False`|
|5|ignored sendTelemetry|bool|Default `False`|
|6|ignored sendDataScreen|bool|Default `False`|
|7|showAdsScreen|bit flags|Personalized `0x00`, NonPersonalized `0x01`, Accepted `0x80`, Purchased `0x82`|
|8|showMinPlayerWarning|bool||
|9|showOnlineHelp|bool||
|10|lastHat|uint|0 to like 93|
|11|sfxVolume|byte|0-255|
|12|musicVolume|byte|0-255|
|13|joyStickSize|float|0.5, 1, 1.5|
|14|lastGameStart|long|Ticks since last game started. Used to check if left game too early|
|15|lastSkin|uint|0-15|
|16|lastPet|uint|0-10|
|17|censorChat|bool||
|18|lastLanguage|uint|0-4, 0: English, 1: Spanish, 2: Portuguese, 3: Korean, 4: Russian|
|19|vsync|bool||
