#
# Makefile
#

CLASS=bin/udp/server/Server.class bin/udp/client/Client.class

build: $(CLASS)
all: clean build
clean:
	rm -rf bin/*

run:
	java -cp bin udp.client.Client

server:
	java -cp bin udp.server.Server

bin/udp/server/Server.class: src/udp/server/Server.java
	javac -d bin $<

bin/udp/client/Client.class: src/udp/client/Client.java
	javac -d bin $<


