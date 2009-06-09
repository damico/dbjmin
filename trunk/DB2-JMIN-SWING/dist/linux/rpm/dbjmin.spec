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
chmod  +x /usr/bin/dbjmin
chmod  +x /usr/bin/dbjmind

%clean
rm -rf $RPM_BUILD_ROOT


%files
/usr/bin/dbjmin
/usr/lib/dbjmin/*
/usr/share/pixmaps/dbjmin.xpm
/usr/share/applications/dbjmin.desktop
