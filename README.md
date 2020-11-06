[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![Discord][discord-shield]][discord-url]

<br>
<p align="center">
  <a href="https://github.com/Koupah/Among-Us-Editor">
    <img src="images/AUEditor.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">AUEditor</h3>

  <p align="center">
    Edit the Among Us playerPrefs file (and now gameHostOptions!), allows for the changing of skins, pets, hats and even a secret/glitched color! 
    Join the discord to share your profiles, make suggestions and many other things!  
    <br>
    <a href="https://github.com/Koupah/Among-Us-Editor/releases"><strong>Downloads »</strong></a>
    <br>
    <br>
    <a href="https://youtu.be/OS1px12PtGQ">View Demo</a>
    ·
    <a href="https://github.com/Koupah/Among-Us-Editor/issues">Report Bug</a>
    ·
    <a href="https://github.com/Koupah/Among-Us-Editor/issues">Request Feature</a>
    ·
    <a href="https://discord.com/invite/HTm3mwK">Discord Server</a>
  </p>
</p>

<!-- TABLE OF CONTENTS -->
## Table of Contents

* [About the Project](#about-the-project)
  * [Video Showcase](#video-showcase)
* [Usage](#usage)
* [Glitched Color](#secretglitched-color)
* [Contributing](#contributing)
* [License](#license)
* [Contact](#contact)
* [Acknowledgements](#acknowledgements)
* [Among Us Config Files INFO](#information-about-among-us-config-files)

<!-- ABOUT THE PROJECT -->
## About The Project

[![AUEditor Demo][product-screenshot]]()

AUEditor is an Among Us Config Editor that allows you to easily modify the config that is used by Among Us for your Outfits and Game Host Settings.

If you upload a video showing or using this please give credit or atleast link this GitHub page!

### Video Showcase

* 1.51 (Latest) by Koupah: https://youtu.be/OS1px12PtGQ  
* 1.51 by Discord Member: https://youtu.be/7KABvBbP1hM  
* 1.45 by Koupah: https://youtu.be/rlv_1qFbZFM  
* 1.0 by Koupah: https://youtu.be/2wRJmS7DKpc 
 
 
<!-- USAGE EXAMPLES -->
## Usage

Custom names that include "\\", ")" or "\[" will result in a "Banned from Room" message and you also cannot include "," in your name as it breaks the config file.  
Names longer than 10 characters will also not work in online games, local play lets you make a lobby but I'm not sure if others can join.

Video Tutorial: https://youtu.be/OS1px12PtGQ

1) Download the latest .jar from [here.](https://github.com/Koupah/Among-Us-Editor/releases)
2) Make sure to download the correct Java (JRE) for your operating system from [here.](https://www.java.com/en/download/)
3) Run the .jar you downloaded in Step 1
4) Modify your settings to what you please!

=======
# Among-Us-Editor
### DISCORD: https://koupah.club/aueditor ###  
#### ( https://discord.com/invite/HTm3mwK ) ####    
Join the discord to share your profiles, make suggestions and many other things!  
  
## Download Instructions ##  
### Video Tutorial: https://youtu.be/OS1px12PtGQ ###  
### Requires Java 7 or above https://www.java.com/en/download/ ###  
Download Link: https://github.com/Koupah/Among-Us-Editor/releases  
Download the .jar file by clicking on it, then run it once it's finished downloading  
From there, it's as simple as choosing your settings then applying & running the game  
  
NOTE: You need to relaunch/have your game closed when using this! 
The game only seems to read settings on launch, so use this then launch your game!  

  
## Secret/Glitched Color ##
The secret color is a lighter version of the dark green, in lobbies chat normally won't work, you can't customize your character. People also see your name as "???".  
In game, your dead body will be red, in meetings you'll be red and in chat you'll be red.
Therefore there can be 3 greens and 2 reds, and I believe if multiple people have a different color value above 12, there can be multiple glitched greens (Untested).  
![](images/differences.png)

#### This glitched color is referenced in this wiki page https://among-us.fandom.com/wiki/Fortegreen ####
![](images/fortegreen.png)  
![](images/fortegreen1.png)  
  

<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

Discord: Koupah#5129
  
Among Us Editor Discord Server: [https://discord.com/invite/HTm3mwK](https://discord.com/invite/HTm3mwK)

Project Link: [https://github.com/Koupah/Among-Us-Editor](https://github.com/Koupah/Among-Us-Editor)



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements

* Slymeball - Helped me bring support to Mac/Linux via his issues.
* camas - Created the playerPrefs index table 

If you'd like to help with something or recieve credit for something you've done, [contact me!](#contact)

## Information about Among Us config files ##  
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

Indexes of gameHostOptions file
|Byte offset/range|Name of setting displayed in Among Us|Type|Value from Recommended Settings|Value shown in Among Us|Comments|
|--|--|--|--|--|--|
|0x0||||||
|0x1|Max Players|8 bit value|`0A` or 10|10||
|0x2-0x3|Language|16 bit value?|`00 01`|English|`00 01` for English, `02 00` for Spanish, `04 00` for Korean, `08 00` for Russian, `10 00` for Portuguese, `20 00` for Arabic, `40 00` for Filipino/Tagalog, `80 00` for Polish, `01 00` for Other|
|0x4-0x5||Unknown|`00 00`|||
|0x6|Map|8 bit value|0|The Skeld/Mira HQ/Polus|0 for The Skeld, 1 for Mira HQ, 2 for Polus|
|0x7-0xA or 7-10|Player Speed|Float 32 bit|`00 00 80 3F` or 1.0|1.0x|All values are in Little Endian.|
|0xB-0xE or 11-14|Crewmate Vision|Float 32 bit|`00 00 80 3F` or 1.0|1.0x||
|0xF-0x12 or 15-18|Impostor Vision|Float 32 bit|`00 00 C0 3F` or 1.5|1.5x||
|0x13-0x16 or 19-22|Kill Cooldown|Float 32 bit|`00 00 34 42` or 45.0|45.0s||
|0x17 or 23|# Common Tasks|8 bit value|`01` or 1|1||
|0x18 or 24|# Long Tasks|8 bit value|`01` or 1|1||
|0x19 or 25|# Short Tasks|8 bit value|`02` or 2|2||
|0x1A or 26|# Emergency Meetings|8 bit value|01 or 1|1||
|0x1B-0x1D or 27-29||Unknown|`00 00 00`|||
|0x1E or 30|Impostors|8 bit value|`02` or 2|2||
|0x1F or 31|Kill Distance|8 bit value|`01` or 1|Medium|0 for short, 1 for medium, and 2 for long.|
|0x20 or 32|Discussion Time|8 bit value|`0F` or 15|15s||
|0x21-0x23 or 33-35||Unknown|`00 00 00`|||
|0x24-0x25 or 36-37|Voting Time|16 bit value|`78 00` or 120|120s||
|0x26-0x27 or 38-39||Unknown|`00 00`||Maybe Voting Time is a 32 bit value.|
|0x28 or 40|Recommended Settings|8 bit value|`01` or 1|A checked checkbox|01 for true, 00 for false|
|0x29 or 41|Emergency Cooldown|8 bit value|`0F` or 15|15s||
|0x2A or 42|Confirm Ejects|8 bit value|`01` or 1|A checked checkbox|01 for true, 00 for false|
|0x2B or 43|Visual Tasks|8 bit value|`01` or 1|A checked checkbox|01 for true, 00 for false|
|0x2C or 44|Anonymous Votes|8 bit value|`00` or 0|A checked checkbox|01 for true, 00 for false|
|0x2D or 45|Task Bar Updates|8 bit value|`01` or 1|A checked checkbox|0 for always, 1 for meetings, 2 for never|


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/Koupah/Among-Us-Editor.svg?style=flat-square
[contributors-url]: https://github.com/Koupah/Among-Us-Editor/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/Koupah/Among-Us-Editor.svg?style=flat-square
[forks-url]: https://github.com/Koupah/Among-Us-Editor/network/members
[stars-shield]: https://img.shields.io/github/stars/Koupah/Among-Us-Editor.svg?style=flat-square
[stars-url]: https://github.com/Koupah/Among-Us-Editor/stargazers
[issues-shield]: https://img.shields.io/github/issues/Koupah/Among-Us-Editor.svg?style=flat-square
[issues-url]: https://github.com/Koupah/Among-Us-Editor/issues
[license-shield]: https://img.shields.io/github/license/Koupah/Among-Us-Editor.svg?style=flat-square
[license-url]: https://github.com/Koupah/Among-Us-Editor/blob/master/LICENSE.txt
[discord-shield]:https://img.shields.io/badge/discord-join-HTm3mwK.svg?logo=discord&longCache=true&style=flat-square
[discord-url]:https://discord.com/invite/HTm3mwK
[product-screenshot]: images/AUEditorShowcase.PNG
