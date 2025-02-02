#!/bin/bash
# mkdir release
# cp release-notes.md release/
# cp app/build/libs/app-all.jar release/
# cd release
cp app/build/libs/app-all.jar app/build/libs/Coulomb.jar 
gh release create "v0.6" app/build/libs/Coulomb.jar --draft