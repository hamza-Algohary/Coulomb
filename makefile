.PHONY: build
build:
	-mkdir build
	javac -cp ".:./lib/ejml.jar:lib/jchart2d.jar:lib/jna-5.13.0(1).jar:lib/java-gtk-javadoc.jar:lib/java-gtk-sources.jar:lib/java-gtk.jar" src/algebra/*.java src/circuitsimulator/*.java src/circuitsimulator/devices/*.java src/graphics/*.java src/gui/*.java src/gui/components/*.java src/gui/oscilliscope/*.java -d build/
	# -mkdir bin
	# jar -cf bin/circuit-simluator.jar build/* lib/*
