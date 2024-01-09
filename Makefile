JAVAC=/usr/bin/javac
.SUFFIXES: .java .class
SRCDIR=src
BINDIR=bin
FILESDIR=files
PYTHON=/usr/bin/python3

$(BINDIR)/%.class:$(SRCDIR)/%.java
		$(JAVAC) -d $(BINDIR)/ src/*.java


CLASSES= Score.class	WordDictionary.class	WordPanel.class	WordApp.class	WordRecord.class

CLASS_FILES=$(CLASSES:%.class=$(BINDIR)/%.class)

default: $(CLASS_FILES)

all = 25
display = 5
file = example_dict.txt

run: $(CLASS_FILES)
		java -cp bin skeletonCodeAssgnmt2/WordApp $(all) $(display) $(file)

docs:
		javadoc -d docs/ src/*.java

clean:
		rm -f bin/*
		rm -f Tests/*
