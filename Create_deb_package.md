
```
mkdir dbjmin-0.5
```

  * copy the content of **dist/deb/dbjmin** to this folder the result structure should be:

```
$ ls -la
total 16
drwxr-xr-x  4 jdamico jdamico 4096 2009-06-15 17:46 .
drwxrwxrwx 15 jdamico jdamico 4096 2009-06-11 11:20 ..
drwxr-xr-x  2 jdamico jdamico 4096 2009-06-09 16:43 DEBIAN
drwxr-xr-x  5 jdamico jdamico 4096 2009-06-09 16:42 usr
```

  * copy to **dbjmin-0.5/usr/lib/dbjmin** all required libraries of projec
  * copy to **dbjmin-0.5/usr/lib/dbjmin** db2jmin.pojo.jar
  * the result structure of **dbjmin-0.5/usr/lib/dbjmin** should be:

```
$ ls -la
total 8988
drwxr-xr-x 2 jdamico jdamico    4096 2009-06-15 17:00 .
drwxr-xr-x 3 jdamico jdamico    4096 2009-06-09 16:42 ..
-rwxr-xr-x 1 jdamico jdamico 1973658 2009-05-29 15:59 db2jcc.jar
-rwxr-xr-x 1 jdamico jdamico    1015 2009-05-29 15:59 db2jcc_license_cu.jar
-rw-r--r-- 1 jdamico jdamico  115500 2009-06-15 17:49 db2jmin.pojo.jar
-rwxr-xr-x 1 jdamico jdamico    2191 2009-05-29 15:59 dbjmin.png
-rwxr-xr-x 1 jdamico jdamico  296047 2009-05-29 15:59 derbyclient.jar
-rwxr-xr-x 1 jdamico jdamico 2274660 2009-05-29 15:59 derby.jar
-rwxrwxrwx 1 jdamico jdamico    5190 2009-05-29 08:10 deserialize.js
-rwxr-xr-x 1 jdamico jdamico  646218 2009-05-29 15:59 jaybird-full-2.1.0.jar
-rwxr-xr-x 1 jdamico jdamico  460721 2009-05-29 15:59 jetty-6.1.0.jar
-rwxr-xr-x 1 jdamico jdamico   21151 2009-05-29 15:59 jetty-embedded-6.1.5.jar
-rwxr-xr-x 1 jdamico jdamico  114597 2009-05-29 15:59 jetty-util-6.1.0.jar
-rwxr-xr-x 1 jdamico jdamico  493105 2009-05-29 15:59 mysql-connector-java-5.0.3-bin.jar
-rwxr-xr-x 1 jdamico jdamico 1536979 2009-05-29 15:59 ojdbc14.jar
-rwxr-xr-x 1 jdamico jdamico  402906 2009-05-29 15:59 postgresql-8.1-407.jdbc3.jar
-rwxr--r-- 1 jdamico jdamico    1219 2009-06-15 16:59 preServers.xml
-rwxrwxrwx 1 jdamico jdamico  124142 2009-05-29 08:10 prototype.js
-rw-r--r-- 1 jdamico jdamico  527075 2009-06-01 17:57 rsyntaxtextarea.jar
-rwxr-xr-x 1 jdamico jdamico  105112 2009-05-29 15:59 servlet-api-2.5.jar

```

```
chmod 0555 dbjmin-0.5/DEBIAN/postinst
chmod 0555 dbjmin-0.5/DEBIAN/prerm
dpkg -b dbjmin-0.5
```