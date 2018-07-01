#!/bin/bash
echo '0' > /sys/class/gpio/gpio15/value
echo '15' > /sys/class/gpio/unexport
