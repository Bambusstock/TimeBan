TimeBan - Temporary bans
========================

There are many reasons why you might want to temporary ban players. Maybe they griefed something or insulted other players. However, Timeban helps you manage those temporary bans. You may define a specific duration along with a custom reason or just use the default options. Furthermore you will never have to unban players manuallay again. TimeBan is easy and flexible to use!

Features
--------
- Temporary ban and unban a player or n-players at once.
- Supports a custom reason and duration.
- Displays the reason when player is kicked and attempts to login.
- Don't worry about unabnning players. TimeBan will handle this automatically!
- List and search all temporary bans.
- Remove a temporary ban, but keep the ban itself.
- All commands work ingame and via console.
- Supports permissions.
- Configure standard reason and duration, scheduler delay ...
- Includes language files and ingame help pages.

Quick introduction
------------------

### Get help ingame

To get help when needed you might want to use the ingame help via:

	/timeban help

### Ban a player

	/timeban ban userA 1w "Griefing!"
	/timeban ban userA 21.04.2015-12:00 "Griefing!"

For more detailed information on the parameters see below!

### Unban a player

If you wan't to manually unban a player use the unban command:

	/timeban unban userA

The "simple format"
-------------------

Telling TimeBan how long to ban a player is simple. Let's say you want to ban a player for one day. The "string" would look like this: `1d`. Ban a player for two days and twelve hours: `2d12h`.

- `y` - Year
- `m` - Month
- `w` - Week
- `d` - Day
- `h` - Hours
- `i` - Minutes
- `s` - Seconds

Two more examples:

	1y5m20w -> 1 year 5 months 20 weeks
	2h30i -> 2 hours 30 minutes

__Please keep in mind that you have to maintain the order of the shortcuts from year to seconds!__

__If you would like to use a more "human readable" date format like `21.02.2015-12:00` you can enable this in your config!__

Command Syntax
--------------

Some commands use parameters, some not. Some parameters are optional, some are required.

- Parameters in angle brackets ("<", ">") are required.
- Parameters in square brackets ("[", "]") are optional.

A combination of angle and square brackets means, that just one of the parameters is required. A short example:

`<[a] [b]>` You  need to specify either the "a" parameter or the "b" parameter.

Commands
--------

### /timeban ban <user,user2> [duration] "[reason]"

Ban one or more players.

#### Parameters:

- [duration] specifies the ban duration. standard format is the "simple format". Default is the standard ban duration defined in your configuration.

- [reason] specifies the reason why the player was banned. Default is the default reason defined in your configuration.

#### Examples:

	/timeban ban userA,userB
	/timeban ban userA 2w "Griefing!"
	/timeban ban userA,userB 2w
	/timeban ban userB "Griefing!"

### /timeban unban <[user,user2] [-a]>

Remove the temporary ban of a user.

#### Parameters:

You need to specify either usernames or use the "-a" parameter. If you use usernames, the players will be unbanned. "-a" will unban all players.

If you just like to remove a __temporary ban__ and keep the player banned use the `rm` command (see below).

#### Examples:

	/timeban unban userA,userB
	/timeban unabn -a

### /timeban info

This command will display some information: the amount of temporary bans and the date of the next unban.

### /timeban list [search] [-r|s]

Use this command to display a list of all bans. The list is default sorted ascending by unban date (newest first).

#### Parameters:

* [search] is a pattern to match a user (this is following the conventions of [Java's Regular Expressions](http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html#sum "JavaDoc")). If you want to get all bans for players starting with "a" you would use `/timeban list a.*`.
* -r get the list ordered descending.
* -s gets you a shortened output.

#### Examples:

	/timeban list u.* -> would search for userA, userB, underdog etc.
	/timeban list -r -> list all bans with latest unban date first
	/timeban list -rs -> descending order and short output
	/timeban list u.* -rs -> list only players starting with u, sort descending, use short output
	/timeban list

### /timeban rm <[user,user2] [-a]>

With this command you can remove the temporary ban for a player. This means that TimeBan no longer check if the user should be unbanned. Also the reason and unban date get lost! __However, the player will still be banned!__ If you wan't to unban a player banned with TimeBan use the `unban` command!

#### Parameters:

Specify the usernames or use "-a" to remove all temporary bans.

#### Examples:

	/timeban rm -a
	/timeban rm userA,userB

### /timeban run

This will run the scheduler manually. The scheduler will check for due unbans.

Automatically unban
-------------------

TimeBan checks by default every minute if there are some users due to unban. TimeBan also verifies if a player is due to unban if he attempts to login.

Configuration
-------------

`defaultDuration`

Define a default duration. Understands only the "simple format" discussed above. The value will be used if you leave out a [duration] parameter.

`defaultReason`

Define a default reason. Used if you leave out a [reason] parameter.

`runDelay`

Define the amount of minutes between one run of the scheduler and the next.

`runSilent`

Define if the scheduler should produce output (server log). `false` enables output.

`locale`

Define the language files to use. Keep in mind that the languages files need to be present in `/server/plugins/TimeBan/lang`!

`banUntilDate`

Set to true and you will be able to ban using a "human readable" date like `21.02.2015-12:00`. The simple format will no longer work.

`dateFormat`

Specify the date format used to parse the date string provided by you for the ban command. Check out the JavaDoc for more information about [the pattern](http://docs.oracle.com/javase/6/docs/api/java/text/SimpleDateFormat.html).

Language files
--------------

Both the man pages and the normal language file are located at `/server/plugins/TimeBan/lang/...`. To customize the files I recommend you to copy the `en` folder an create your own e.g. `en_custom`. Modify the messages and change the plugin config setting `locale` to the name of your own folder(e.g. `locale: en_custom`).

Permissions
-----------

* `timeban.all` - The TimeBan command itself.
* `timeban.ban` - Define who can ban a player using TimeBan.
* `timeban.unban` - Define who can unban a player using Timeban.
* `timeban.info` - Define who is able to view information like amount of bans.
* `timeban.help` - Define who is able to see help information.
* `timeban.list` - Define who can list all bans.
* `timeban.rm` - Define who can remove a Timeban.
* `timeban.run` - Define who can run the Timeban task.