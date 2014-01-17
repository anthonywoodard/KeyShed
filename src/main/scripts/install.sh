#!/bin/sh
#BASEDIR should be where all KeyShed application files are located
BASEDIR=$(dirname $0)
f1=/usr/local/bin
f2=~/.keyshed

if [ -d "$f2" ]; then
  echo "\033[0;33mYou already have KeyShed installed.\033[0m You'll need to remove ~/.keyshed if you want to install."
  exit
fi

#use internal field separator to loop $PATH
OIFS=$IFS
#change internal field separator to : 
IFS=:
echo "\033[0;34mLooking for $f1 directory...\033[0m"
for i in $PATH
do
  #determine if current PATH value is = $f1
	if echo "$i" | egrep -q "$f1" ; then
    #determine if directory exists
		if [ -d "$f1" ]; then       
      echo "\033[0;34mInstalling KeyShed...\033[0m"
      cd $BASEDIR
      cd ..          
	  	mkdir $f2      
      cp -rf KeyShed/. $f2
      cd $f2
      mv startup/startup.sh startup/keyshed
      chmod +x startup/keyshed
      mv startup/keyshed $f1
      #determine if keyshed has been added to /usr/local/bin
      if [ ! -f "$f1/keyshed" ]; then
        rm -rf $f2
        echo "\033[0;33mKeyShed install failed.\033[0m"
        #change internal field separator back to original value
        IFS=$OIFS
        exit
      fi
      rm -rf startup      
      break
	  fi
	fi
done
#change internal field separator back to original value
IFS=$OIFS

if [ ! -d "$f2" ]; then
  echo "\033[0;33mKeyShed install failed.\033[0m"
else
  echo "\033[0;32mKeyShed install succeeded. Type keyshed to get started.\033[0m"
fi