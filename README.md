TimeBan - Banning plugin for Craftbukkit using the Bukkit-Api
=============================================================

Did you ever wanted to ban a player e.g for a hour or two and after this time automatically unban him? TimeBan provide the abillity to do this.

Usage
-----

### Ban a player

If you like to ban a player simply type this:

	/timeban ban <username,username2> <seconds> <reason>

If you leave out the <seconds> pattern TimeBan would use a default duration defined in the config.yml. If you leave out the <reason> pattern TimeBan use the default reason defined in the config.yml.

Other commands
--------------

### /timeban list [search] [-r|s]

Use this command to display a list of all bans. The list is default sorted by player name ascending. With [search] you can define a pattern to match user. For example if you want to get all bans for players starting with "a" you could use `/timeban list *a`. If you 'd like to get the list ordered descending use the "-r" parameter. Use the "-s" parameter to get the short output.

### /timeban unban [username,username2] [-a]

Use this to unban a user or more. If you add the flag "-a" all players banned with TimeBan are unbanned.  

### /timeban rm [username,username2] [-a]

Use this command to remove all bans from the ban list. The players keep banned. Use unban for unbanning a player.

### /timeban run

Manually run scheduler 

Configuration
-------------

### General

#### `defaultDuration` (Default: 3600)

Use this to define a default duration. If you leave out the <seconds> pattern, this is automatically used.

#### `defaultReason` (Default: Default reason.)

Define your default reason.

#### `runDelay` (Default: 600)

Define the amount of seconds between one run of the scheduler and the next.

Permissions
-----------

Comming soon.

Known Bugs
----------

TODO
----

- Add features:
	- provide permissions
	- provide configuration
	
Possible features:
------------------
- Undo
- Import and Export of ban list 
	
