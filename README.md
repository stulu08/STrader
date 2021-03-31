# STrader
STrader ~ A fully customizable entity trader plugin

This is a plugin where you can create entity traders that you can spawn inside you world or make them spawn on server start with just a single command:
/traderspawn <tradername> <default(true/false)>
and if you want to, you can disable deafult trader spawning in the config.yml file and spawn the default traders with:
/spawnDefaultTraders

You can create you traders with the default_traders config file:
Write the name of the trader and then the path to the config file to the trader where you can customize the display name with color, slot size and the entity type(List can be found here)

and you can link an inventory file where you can customize the items, just write:
<slot>: <Item name>|<buy price>|<sell price>|<stack size>



And after you setup everything what normaly dont takes over 10 minutes you can  sell and buy items from the traders with just a single click

This plugin needs vault and an econemy plugin like essentialsx to handle the money[/B]

Some aditional Features:
- Entitys can't get hurt and can't damage other entitys
- I found while testing no dupe glitch
- Any entity
- Works with any econemy plugin that supports vault
- Set's money automaticly up using vault api
- Traders can only spawn with command or on server start
- Full support
- The plugin automaticly removes every trader when server gets shutdown to prevent glitches
- You can create as much traders as you want!

commands:
  traderspawn:
    permission: strader.spawn
    description: Command to spawn an trader

  spawnDefaultTraders:
    permission: strader.spawn
    description: Command to spawn all default traders



permissions:
  strader.spawn:
    default: op

  strader.acces:
    default: true
description: allowes you to open the trades inventory

  strader.buy:
    default: true

  strader.sell:
    default: true

  strader.default:
description: all the permissions a normal player need'S
    children:
      strader.acces: true
      strader.buy: true
      strader.sell: true
    default: true

  strader.*:
description: all the permissions an admin need's
    children:
      strader.spawn: true
      strader.op: true
      strader.acces: true
      strader.buy: true
      strader.sell: true
    default: op

If you have any questions just ask me
If you want a custome prefix just ask
