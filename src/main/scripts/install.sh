#!/bin/sh
#BASEDIR should be where all KeyShed application files are located
BASEDIR=$(dirname $0)
f1=/usr/local/bin
f2=~/.keyshed

if [ -d "$f2" ]; then
  echo "\033[0;33mYou already have KeyShed installed. Do you want to uninstall?\033[0m"  
  echo "\033[0;33mType Y or N: \033[0m\c"
  read line
  if [ "$line" = Y ] || [ "$line" = y ]; then
    echo "\033[0;33mPlease note uninstall will remove all files including the database.\033[0m"
    echo "\033[0;33mDo you want to continue?\033[0m\c"
    read line2
    if [ "$line2" = Y ] || [ "$line2" = y ]; then
      rm -rf $f1/keyshed
      rm -rf $f2
      echo "Uninstall complete... Continuing with installation..."
    else
      exit
    fi
  else
    exit
  fi  
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