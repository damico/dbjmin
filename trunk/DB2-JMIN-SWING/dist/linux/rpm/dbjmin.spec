Name:           dbjmin
Version:        0.5
Release:        1%{?dist}
Summary:        Graphical Client for databases

Group:          System Environment/Base
License:        gpl
BuildRoot:      %{_tmppath}/%{name}-%{version}
BuildArch:      noarch
Requires:       filesystem
Requires:	java >= 1.5

%description
Graphical Client for databases.
 This is an easy-to-use client for DB2, Oracle, Derby, MySQL, PostgreSQL and Firebird.
 For more information see http://dbjmin.googlecode.com/

%prep


%install


%clean
rm -rf $RPM_BUILD_ROOT


%files
/usr/bin/dbjmin
/usr/lib/dbjmin/*
/usr/share/pixmaps/dbjmin.xpm
/usr/share/applications/dbjmin.desktop

%changelog
0.5
-Better arrangement of memory for web module
-Headers improvement for web module. now it shows more information of
tables/schemas
-Improved deb/rpm definition packages
-Refactored SystemOper to make correct calls for Browsers into Windows environment
-Fixed bug that was failing to recognize Windows OS
-Added specialized actions for WEB support
-Changed JettyController to start jetty in a separated thread
-Added package and class to encrypt and decrypt properties file
-Added in sqltext a event for closes the open parenthesis.
-Added feature with suggests hints to complete the sqlcommands.

0.3
-Finished implementation of Command's history
-Added right path for Command's history
-Removed "count" variable from main scope inside singleton
-Added more tests units for preServers and RWhistory
-Fixed bug with preServers (it was saving and getting from a wrong path)

0.2
- Fixed the return of error message when try to connect an invalid or unreacheable database
- Added new messages and logic to start_dbjmin.sh
- Added new error/warning messages about table selection per schema and sample data of selected table
- Added colors to help the reading of app messages
- Added word-wrapping for text areas
- Significant improvement for SQLParser class performance
- Changed the approach for parse multiple executeUpdate SQLs from StringTokenizer st = new StringTokenizer(preSql, ";") to a new class SQLParser
- Fixed bug that was avoiding the correct display of SELECT queries

0.1.9
- Added support to save new db credentials using F3 key in password field (Thanks Mir0)
- Fixed bug that was not recognizing DROP command 

0.1.8.1
- Fixed bug that was crashing any user input SQLs 

0.1.8
- Rearrange more code from Launch class to SwingUtils
- Added support to process multiple executeUpdate statements at once,
	statements must be separated by semi-colon.
- Added new method to parse executeUpdate statements into InputUserValidation class
- Added a new verbose feature to Logger class

0.1.7
- Added more documentation into classes
- Added singleton to SystemOper class
- Improved Linux LookAndFeel settings

0.1.6
- Added lib/ folder with dependency jars
- Added SwingUtils class to handle Desktop UI settings
- Fixed a small bug that was avoid the appearance of null/empty fields from table's columns


0.1.5
- Added support to Store database credentials as per request posted into sf.net (1820605)
DBJMIN is a new name to the old project DB2-JMIN.

0.1.4
- Added support to drop tables

0.1.3
- Increased size of log text box 
- Increased size of sql text box
- Added support to expand and collapse screen size
- Added GPL license to two new classes
- Added more constants to Constants class
- Changed color of logtext 

0.1.1
- Added support to different UI sizes depending of OS (SystemOper class).
- Added .exe launcher to windows version (thanks launch4j http://launch4j.sourceforge.net/)
- Fixed start_dbjmin.bat
- Try to 'start to improve' the quality of code: Added Constants class

0.1.0
- Added full support to derby, client/server and embedded
- Corrected lincense details on all source code
- Added version/build info on main screen 
- Fixed the support to update/delete/insert/create queries
- Icon change to dbjmin
- Application name changed to db-jmin-swing
- Added new method on InputDataValidation class to handle derby support
- Several small changes on DBconnector class

0.0.9

- Fixed the bug that was deleting the log history of sql queries done by user during the software execution.
- Fixed the problem with the endless log. Now each time the program runs, the old log is deleted and a new one is created.

Several changes made from last commit:
- Corrected bug on View sample data to postgre db
- Corrected bug on View structure of table on postgre db
- Added support to Derby db
- Correct bug to no /usr/passwd on derby db
- Added support to Firebird db
- Added support to Oracle db (Special Thanks to Juliano Martins :-) )
- Added some null pointer exception treatment when null data comes in array lists
- Added support to INSERT, DELETE, UPDADE and CREATE commands
- Corrected bug that was summing new schemas and tables with the previous ones from last use
- Added a new way of JFrame construction. This was a try to avoid the bug of too many calls of sw startup.... I don't know if will work :(

ENTER Key event added to passwd field, to avoid the use of mouse

Radical change: MySQL/Postgre database support added
Finally, really fixed the no table selected exception when Data button was pushed without no table selected


