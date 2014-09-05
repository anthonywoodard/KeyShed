#!/bin/sh
#BASEDIR should be where all KeyShed application files are located
BASEDIR=$(dirname $0)
f1=/usr/local/bin
f2=~/.keyshed

if [ -d "$f2" ]; then    
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
        echo "\033[0;34mUpdating KeyShed...\033[0m"
        cd $BASEDIR
        cd ..          
        #update KeyShed.jar, install.sh, update.sh, startup.sh, lib only
        if [ -f "KeyShed/Keyshed.jar" ]; then 
          cp -rf KeyShed/Keyshed.jar $f2
        fi
        if [ -f "KeyShed/install.sh" ]; then 
          cp -rf KeyShed/install.sh $f2
        fi
        if [ -d "KeyShed/startup" ]; then 
          cp -rf KeyShed/startup $f2
        fi
        if [ -d "KeyShed/lib" ]; then 
          cp -rf KeyShed/lib $f2
        fi
        cd $f2
        mv startup/startup.sh startup/keyshed
        chmod +x startup/keyshed
        cp -rf startup/keyshed $f1        
        rm -rf startup      
        break
      fi
    fi
  done
  #change internal field separator back to original value
  IFS=$OIFS

  echo "\033[0;32mKeyShed has been updated and/or is at the current version. Type keyshed to get started.\033[0m"
else
  echo "KeyShed does not exist.  You must install it before updating it."
fi
