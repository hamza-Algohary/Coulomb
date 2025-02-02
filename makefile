builder:
	flatpak-builder --user --install --disable-cache --repo=org.flathub app/build/flatpak flathub/io.github.hamza_algohary.Coulomb.yaml --force-clean
debug:
	flatpak --log-session-bus run io.github.hamza_algohary.Coulomb
run:
	flatpak run io.github.hamza_algohary.Coulomb
clean:
	rm -rf org.flathub
	rm -rf .flatpak-builder
lint:
	flatpak run --command=flatpak-builder-lint org.flatpak.Builder --exceptions appstream app/src/main/resources/io.github.hamza_algohary.Coulomb.metainfo.xml
preview:
	gnome-software --show-metainfo app/src/main/resources/io.github.hamza_algohary.Coulomb.metainfo.xml