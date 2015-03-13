# Connecting Oracle #


  1. **Type:** oracle
  1. **Srv:** your\_server
  1. **Port:** 1521
  1. **db:** your\_database
  1. **User:** username
  1. **Passwd:** password

Example

  1. Type: 

&lt;oracle&gt;


  1. Srv: <192.168.190.1>
  1. Port: 

&lt;1521&gt;


  1. db: 

&lt;mydatabase&gt;


  1. User: 

&lt;username&gt;


  1. Passwd: 

&lt;password&gt;



**'[' > ']'** (Click in the button for connect)

## Machine Setup ##

Windows XP Professional

java version "1.6.0\_13"
Java(TM) SE Runtime Environment (build 1.6.0\_13-b03)
Java HotSpot(TM) Client VM (build 11.3-b02, mixed mode, sharing)

Oracle 10

## Testing ##

  * CREATE

```
   create table TBL_TEST 
   (
     ID INTEGER NOT NULL,
     DESCRIPTION VARCHAR(25),
     PRIMARY KEY (ID) 
   )
```

  * INSERT

> insert into TBL\_TEST(ID, DESCRIPTION) VALUES(1, 'Oracle Test' )

  * SELECT

> Select `*` from TBL\_TEST

  * BATCH UPADE

  * View Data

> Button **[DATA](DATA.md)** provides an easier way to select information, return for you all information within your table.

  * View Structure

> Button **[Structure](Structure.md)** provides an easier way to returns the table structure from Oracle.

  * Example

  1. Column Name: 

&lt;Description&gt;


  1. Type Name: 

&lt;long&gt;


  1. Length: 

&lt;25&gt;



**_For more Information about commands for Oracle:_** http://books.google.com/books?id=kl5l3_MisOAC&printsec=frontcover&dq=oracle+guide

# Connecting Firebird #

  1. **Type:** firebird
  1. **Srv:** your\_server
  1. **Port:** 3050
  1. **db:** your\_database.fdb
  1. **User:** database\_user
  1. **Passwd:** password

**_Example_**

  1. Type: 

&lt;firebird&gt;


  1. Srv: 

&lt;localhost&gt;


  1. Port: 

&lt;3050&gt;


  1. db: <mydatabase.fdb>
  1. User: 

&lt;sysdba&gt;


  1. Passwd: 

&lt;masterkey&gt;



**`[` > `]`** (Click in the button for connect)

## Machine Setup ##

Linux darkstar 2.6.24.5-smp #2 SMP Wed Apr 30 13:41:38 CDT 2008 i686 AMD Turion(tm) 64 Mobile Technology MK-36 AuthenticAMD GNU/Linux

java version "1.6.0\_06"
Java(TM) SE Runtime Environment (build 1.6.0\_06-b02)
Java HotSpot(TM) Client VM (build 10.0-b22, mixed mode, sharing)

## Testing ##

  * CREATE

```
  create table TBL_TEST 
   (
     ID INTEGER NOT NULL,
     DESCRIPTION CHAR(25),
     PRIMARY KEY (ID) 
   )
```


  * INSERT

> ` insert into TBL_TEST(ID, DESCRIPTION) VALUES(1, 'Firebird Test' ) `


  * SELECT

> `` Select `*` from TBL_TEST ``

  * BATCH UPADE

  * View Data

> Button [DATA](DATA.md) provides an easier way to select information, return for you all information within your table.

  * View Structure

> Button [Structure](Structure.md) provides an easier way to returns the table structure from Firebird.

  * Example**1. Column Name:**

&lt;ID&gt;


  1. Type Name: 

&lt;LONG&gt;


  1. Length: 

&lt;4&gt;



**_For more Information about commands for firebird:_** http://wiki.firebirdsql.org/

# Connecting MySQL #

## Machine Setup ##

## Testing ##
  * CREATE
  * INSERT
  * SELECT
  * BATCH UPADE
  * View Data
  * View Structure


# Connecting PostgreSQL #

## Machine Setup ##

## Testing ##
  * CREATE
  * INSERT
  * SELECT
  * BATCH UPADE
  * View Data
  * View Structure

# Connecting Derby Embedded #

## Machine Setup ##

## Testing ##
  * CREATE
  * INSERT
  * SELECT
  * BATCH UPADE
  * View Data
  * View Structure

# Connecting Derby Server #

## Machine Setup ##

## Testing ##
  * CREATE
  * INSERT
  * SELECT
  * BATCH UPADE
  * View Data
  * View Structure

# Connecting DB2 #

## Machine Setup ##

## Testing ##
  * CREATE
  * INSERT
  * SELECT
  * BATCH UPADE
  * View Data
  * View Structure

# Common Features Test #
  * Save credentials
  * Invalid data
  * Tooltips
  * SQL History
  * SQL Syntax autocomplete