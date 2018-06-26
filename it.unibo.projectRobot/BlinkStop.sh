#!/bin/bash

kill -12 `ps -e|grep Blink | awk {'print $1'}`
