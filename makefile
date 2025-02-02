builder:
	flatpak-builder --user --install --disable-cache --repo=org.flathub app/build/flatpak io.github.hamza_algohary.Coulomb.yaml --force-clean
debug:
	flatpak run io.github.hamza_algohary.Coulomb
run:
	flatpak run --log-session-bus io.github.hamza_algohary.Coulomb
clean:
	rm -rf org.flathub
	rm -rf .flatpak-builder
