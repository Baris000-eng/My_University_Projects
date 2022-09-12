CC = gcc

all: clean install run

install: agecalculator

clean:
  rm -rf age.o io.o main.o agecalculator

run: agecalculator
  ./agecalculator

agecalculator: age.o io.o main.o
  $(CC) -o agecalculator age.o io.o main.o

age.o: age.c
  $(CC) -c age.c

io.o: io.c
  $(CC) -c io.c

main.o: main.c
  $(CC) -c main.c

  