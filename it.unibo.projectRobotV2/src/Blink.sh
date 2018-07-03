#!/bin/bash
echo "15" > /sys/class/gpio/export
sleep  0.1
echo "out" > /sys/class/gpio/gpio15/direction
sleep 0.1
echo "1" > /sys/class/gpio/gpio15/value
