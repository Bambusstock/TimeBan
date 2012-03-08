TimeBan - Banning plugin for Craftbukkit using the Bukkit-Api
=============================================================

Did you ever wanted to ban a player e.g for a hour or two and after this time automatically unban him? TimeBan provide the abillity to do this.

Usage
-----

### Ban a player

If you like to ban a player simply type this:

	/timeban ban <username> <seconds>

If you leave out the <seconds> pattern TimeBan would use a default duration defined in the config.yml.

Configuration
-------------

### General

#### `defaultDuration` (Default: 3600)

Use this to define a default duration. If you leave out the <seconds> pattern, this is automatically used.

Permissions
-----------

Comming soon.

Known Bugs
----------

TODO
----

- Add features:
	- comfortable use of <seconds> pattern, e.g. use __3h__ for a ban duration of 3 hours. Some more capabilities: y (year), w (week), d (days), s (seconds), m (minutes). Provide a extra class...
	- `/timeban clear` to clear the ban list
	- `/timeban show` to show a list of all banned user including duration in human readable format
	- provide permissions
	- provide configuration
	
