#!/bin/sh
#rm /usr/local/bin/keyshed
#rm -rf ~/.keyshed
mvn clean package assembly:single
cd target
unzip KeyShed-bin.zip