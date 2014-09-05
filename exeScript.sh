#!/bin/sh
#rm /usr/local/bin/keyshed
#rm -rf ~/.keyshed
mvn clean package assembly:single
cd target
unzip KeyShed-bin.zip
cd KeyShed

echo "Do you want to install or update?"
echo "Type i for install or u for update: \c"
read line
if [ "$line" = I ] || [ "$line" = i ]; then
  sh install.sh
elif [ "$line" = U ] || [ "$line" = u ]; then 
  sh update.sh
fi
