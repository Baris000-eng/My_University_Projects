FLAGS    = -z execstack 
FLAGS_32 = -static -m32
TARGET   = server 
 
N = 4

all: $(TARGET)

server: server.c
	gcc -DDUMMY_SIZE=$(N) $(FLAGS) $(FLAGS_32) -o server server.c

clean:
	rm -f $(TARGET)
