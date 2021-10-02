# Name: pc code.py
# Author: Varun Pereira
# Date: 13/10/2020

# "A purely serial solution is also possible.That is,
# the micro:bit runs in a loop and delivers its
# data continuously to the PC which is constantly
# watching and saving what it gets to file." - Ron

# Because my microbit does exactly what Ron said was appropriate,
# as quoted above,there was no need for a buffer size nor clearing
# data, because of the continuous and constant transmission of data
# via direct serial connection.

# Note: microbit code was done on MakeCode, so it will
# need to be imported, if needed. I have set sample size
# to 10 for simplicity.

import serial
import time
import csv
import datetime

# establishing the settings of the serial connection,
# this includes which port is connected, speed of data
# transfer etc.
ser = serial.Serial(
port = 'COM3',
baudrate = 115200,
parity = serial.PARITY_NONE,
stopbits = serial.STOPBITS_ONE,
bytesize = serial.EIGHTBITS
)
ser.flushInput()


# creates 2 csv files, 1 for each variable
# (light and temperature)
# then, write column names.
with open('temp.csv', 'a', newline='') as f:
    writer = csv.writer(f,delimiter = ',')
    writer.writerow(['Device Number','Date Time','Temp'])
with open('light.csv', 'a', newline='') as f:
    writer = csv.writer(f,delimiter = ',')
    writer.writerow(['Device Number','Date Time','Light'])

# microbit is continuously transmitting data, use
# a and b to keep track of how many samples are collected
# and the boolean temp, to determine which variable as
# the data stream alternates between light and temperature.
temp = True
a = 1
b = 1


# microbit sets how many samples eg 10 or 20 etc
# each piece of data is decoded and when it is, it
# is time stamped via pc.
# Then the 2 csv files are appended with these 3 pieces
# of information in a row.
# If pc downloads data correctly
# then pc sends a confirmation message back to microbit,
# if microbit receives this, then it will display tick.
while True:
    if temp == True:
        temp = False
        ser_bytes = ser.readline()
        decoded_bytes = ser_bytes.decode('utf-8')
        print(decoded_bytes)
        stamp = str(datetime.datetime.now())
        with open('temp.csv', 'a', newline='') as f:
            writer = csv.writer(f,delimiter = ',')
            writer.writerow([a,stamp,decoded_bytes])
        ser.write(str.encode(''))
        time.sleep(0.5)
        a += 1

    elif temp == False:
        temp = True
        ser_bytes = ser.readline()
        decoded_bytes = ser_bytes.decode('utf-8')
        print(decoded_bytes)
        stamp = str(datetime.datetime.now())
        with open('light.csv', 'a', newline='') as f:
            writer = csv.writer(f,delimiter = ',')
            writer.writerow([b,stamp,decoded_bytes])
        ser.write(str.encode(''))
        time.sleep(0.5)
        b += 1

# in microbit, where there was a loop set to 10 samples for simplicity sake,
# 2 special functions were created for tick and cross images
# the microbit collects data when user press the A button