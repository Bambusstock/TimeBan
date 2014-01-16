TimeBan - Temporary bans for Craftbukkit
========================================

TimeBan provides you with the abillity to temporary ban players. You may also set a custom ban reason displayed when kicking the player and when the player tries to join your server.

Features
--------
- Temporary ban a player with a custom reason.
- Banned players will see the reason when they are kicked an also if they attempt to login.
- Ban multiple users with the same duration and same custom reason in one command.
- TimeBan automatically checks for unbans. On login and while the server is running. (You can define a interval in minutes.)
- Unban multiple users with one command. Or even all with "-a". (More information below.)
- List all temporary bans.
- Search through the list of temporary bans.
- Remove a temporary ban, but keep the ban itself.
- Get quick information about the amount of temporary bans and the date of the next unban.
- Run the TimeBan scheduler manually.
- All commands work ingame and via console.
- Of course: permissions.
- Configuration: Standard reason, Standard ban duration, scheduler delay.

Quick introduction
------------------

### Ban a player

You wan't to ban a player? No problem:

	/timeban ban <username,username2> <duration> <reason>

Let's have a look a the parameters. (For more detailed information see below!)

- `<username,username2>` Enter the username(s) of the player(s) you wan't to ban.
- `<duration>` There is a [simple format] to tell TimeBan how long you wan't to ban a bad guy. Leave out and TimeBan uses the standard ban duration.
- `<reason>` Enter a reason embeded in a ". Example: `/timeban ban testuser 1d "No griefing!"`

### Unban a player

Banned a player by mistake or wan't to unban him early? Use the unban command:

	/timeban unban <username,username2>

The "simple format"
-------------------

How do you tell TimeBan, how long a player should be banned? There is a simple syntax. Let's say you wan't to ban a player for one day. The "string" would look like this: `1d`. Ban a player for two days and twelve hours: `2d12h`. A overview of "shortcuts":

- `y` - Year
- `m` - Month
- `w` - Week
- `d` - Day
- `h` - Hours
- `i` - Minutes
- `s` - Seconds

You can combine this however you wan't (theoretically, there would be so many test cases ;)). Two more examples:

	1y5m20w, 2h30i

__Please keep in mind that you have to maintain the order of the shortcuts from year to seconds!__

Command Syntax
--------------

Some commands use parameters, some not. Some parameters are voluntary, some are required.

- Parameters in angle brackets ("<", ">") are required.
- Parameters in square brackets ("[", "]") are voluntary.

A combination of angle and square brackets means, that just one of the parameters are required. I think it's logically if you look at the command but a short example:

`<[a] [b]>` You  need to specify either the "a" parameter or the "b" parameter.

Commands
--------

### /timeban ban <username,username2> [duration] "[reason]"

Ban one or more players.

[duration] will follow the "simple format" and specifies the ban duration. If no duration is specified TimeBan uses a standard ban duration defined in the configuration.

[reason] specifies the reason why the player was banned. If you don't specify the reason TimeBan will use the default reason defined in the configuration.

### /timeban unban <[username,username2] [-a]>

You need to specify either usernames or use the "-a" parameter. If you use the usernames, the players will be unbanned. "-a" will unban all players. If you just like to remove a __temporary ban__ and keep the player banned use the "rm" command (see below).


### /timeban info

This command will display some information: the amount of temporary bans and the date of the next unban.

### /timeban list [search] [-r|s]

Use this command to display a list of all bans. The list is default sorted ascending by unban date (newest first). With [search] you can define a pattern to match a user (this is following the conventions of [Java's Regular Expressions](http://docs.oracle.com/javase/6/docs/api/java/util/regex/Pattern.html#sum "JavaDoc")). For example if you want to get all bans for players starting with "a" you could use `/timeban list a.*`. If you 'd like to get the list ordered descending use the "-r" parameter. Use the "-s" parameter to get a shortened output.

### /timeban rm <[username,username2] [-a]>

With this command you can remove the temporary ban for a player. This means that TimeBan no longer check if the user should be unbanned. Also the reason and unban date get lost! __However, the player will still be banned!__ If you wan't to unban a player banned with TimeBan use the TimeBan unban command!

### /timeban run

This manually run the scheduler and is checking for unbans.

Configuration
-------------

### General

#### `defaultDuration` (Default: 1h (1 hour))

Use this to define a default duration. Understands the "simple format" from above. If you leave out the <duration> pattern calling a command, this is automatically used.

#### `defaultReason` (Default: "Default reason.")

Define a default reason.

#### `runDelay` (Default: 1)

Define the amount of minutes between one run of the scheduler and the next.

### `runSilent` (Default: true)

Define if the UnbanBot should produce output (server log).

Permissions
-----------
### timeban.all (Default: op)

The TimeBan command itself.

### timeban.ban (Default: op)

Define who can ban a player using TimeBan.

### timeban.unban (Default: op)

Define who can unban a player using Timeban.

### timeban.info (Default: op)

Define who is able to view information like amount of bans.

### timeban.help (Default: op)

Define who is able to see help information.

### timeban.list (Default: op)

Define who can list all bans.

### timeban.rm (Default: op)

Define who can remove a Timeban.

### timeban.run (Default: op)

Define who can run the Timeban task.

Upcoming features:
------------------
- use the ChatPaginator for timeban list output
- list upcoming bans

Possible features:
------------------
- ban until date instead of untilstring
- reload timbean configuration
- undo command
- import and export the list of bans