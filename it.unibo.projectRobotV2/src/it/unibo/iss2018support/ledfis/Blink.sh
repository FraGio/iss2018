#!/bin/bash

 function quit {
 echo "0" > /sys/class/gpio/gpio4/value
 #echo "SPENGO" >> out.txt
 exit
 }


trap 'quit' SIGUSR2
#echo $1>> out.txt
#echo $2>> out.txt

echo "4" > /sys/class/gpio/export
echo "out" > /sys/class/gpio/gpio4/direction

if [[ $1 == 'on' ]]; then

#echo "ACCENDO">> out.txt

echo "1" > /sys/class/gpio/gpio4/value
while [[ true ]] ; do
sleep 1
done
fi

if [[ $1 == 'blink' ]]; then
while [[ true ]] ; do


echo "1" > /sys/class/gpio/gpio4/value
sleep 1
echo "0" > /sys/class/gpio/gpio4/value
sleep 1

#echo "BLINK_ON">> out.txt
#sleep 1
#echo "BLINK_OFF">> out.txt
#sleep 1

done

fi

