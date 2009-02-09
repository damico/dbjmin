#!/bin/bash
if [ -z "$JAVA_HOME" ]; then
    echo ""
    echo "No JAVA_HOME variable found in your environment!"
    echo ""
    exit 1
fi
JAVALOCAL=$JAVA_HOME/bin
DBJMINLOCAL=
if [ -z "$DBJMINLOCAL" ]; then
    echo ""
    echo "No DBJMINLOCAL variable found in your environment!" 
    echo "DBJMINLOCAL is the directory of DBJMIN installation"
    echo "Example: /opt/sws/DB2-JMIN-SWING"
    echo ""
fi
cd $DBJMINLOCAL
$JAVALOCAL/java -cp $DBJMINLOCAL/:$DBJMINLOCAL/lib/:$DBJMINLOCAL/lib/db2jcc.jar:$DBJMINLOCAL/lib/db2jcc_license_cu.jar:$DBJMINLOCAL/lib/mysql-connector-java-5.0.3-bin.jar:$DBJMINLOCAL/lib/ojdbc14.jar:$DBJMINLOCAL/lib/derby.jar:$DBJMINLOCAL/lib/derbyclient.jar:$DBJMINLOCAL/lib/postgresql-8.1-407.jdbc3.jar:$DBJMINLOCAL/lib/jaybird-full-2.1.0.jar:$DBJMINLOCAL/app/db2jmin.pojo.jar  db2jmin.pojo.swing.Launch
