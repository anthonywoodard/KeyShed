#!/bin/sh
cd ~/.keyshed
cp -rf ks.sqlite recover.sqlite
if [ "$1" = "backup" ]; then
  #determine if directory exists
	if [ -d "$2" ]; then 
    cp -rf ks.sqlite keyshed.sys
    cp -rf keyshed.sys $2
    echo "Backup complete."
  fi
elif [ "$1" = "restore" ]; then
  #determine if file exists
  if [ -f "$2" ]; then 
    mv $2 ~/.keyshed/ks.sqlite
    echo "Restore complete."
  fi
elif [ "$1" = "recover" ]; then
  cp -rf recover.sqlite ks.sqlite
  echo "Recovery complete."
elif [ "$1" = "timestamp" ]; then
  ATIME=$(stat -f "%m%t%Sm" ks.sqlite)
  echo $ATIME
elif [ "$1" = "uninstall" ]; then
  echo "Would you like to uninstall KeyShed?"
  echo "Type Y to uninstall KeyShed: \c"
  read line
  if [ "$line" = Y ] || [ "$line" = y ]; then
    rm /usr/local/bin/keyshed
    rm -rf ~/.keyshed
    echo "Uninstall complete."
  fi
elif [ "$1" = "help" ]; then
  echo "KeyShed is a command line tool for storing access credentials."
  echo "Start KeyShed by typing keyshed at the prompt or type keyshed with the following options:"
  echo "backup [directory] - backup the KeyShed database to a directory"
  echo "help"
  echo "recover - recover the KeyShed database from an internal copy"
  echo "restore [file]  - restore the KeyShed database from a file"
  echo "timestamp - display the last time the KeyShed database was moved"
  echo "uninstall"
else
  java -jar KeyShed.jar
fi