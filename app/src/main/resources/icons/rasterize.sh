#!/bin/bash

rm -rf raster
mkdir raster
mkdir raster/scale_1
mkdir raster/scale_2
mkdir raster/scale_1/light
mkdir raster/scale_1/dark
mkdir raster/scale_2/light
mkdir raster/scale_2/dark

inkscape --export-type=png vector/light/*.svg -w 32 -h 32
inkscape --export-type=png vector/dark/*.svg -w 32 -h 32

# Special Size for the logo
inkscape --export-type=png vector/light/coulomb.svg -w 256 -h 256
inkscape --export-type=png vector/dark/coulomb.svg -w 256 -h 256

mv vector/light/*.png raster/scale_1/light/
mv vector/dark/*.png raster/scale_1/dark/

inkscape --export-type=png vector/light/*.svg -w 64 -h 64
inkscape --export-type=png vector/dark/*.svg -w 64 -h 64

# Special Size for the logo
inkscape --export-type=png vector/light/coulomb.svg -w 512 -h 512
inkscape --export-type=png vector/dark/coulomb.svg -w 512 -h 512

mv vector/light/*.png raster/scale_2/light/
mv vector/dark/*.png  raster/scale_2/dark/


