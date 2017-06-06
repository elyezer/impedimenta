# The `lib` directory must either contain or link to `jopt-simple-5.0.3.jar`.
# (The full dotted path is "net.sf.jopt-simple".)
CLASSPATH:="lib/*:$$CLASSPATH"

help:
	@echo "Please use \`make <target>' where <target> is one of:"
	@echo "  help"
	@echo "    to show this message"
	@echo "  compile"
	@echo "    to compile the application"
	@echo "  run-no-opts"
	@echo "    to run the application with no options"
	@echo "  run-help"
	@echo "    to run the application with the --help option"
	@echo "  run-classes"
	@echo "    to run the application with several classes"
	@echo "  run-issue-classes"
	@echo "    to run the application with the --issue option and several classes"

compile: edu/vtc/cis3720/jxa03200/Main.class

run-no-opts: compile
	CLASSPATH=$(CLASSPATH) java edu.vtc.cis3720.jxa03200.Main

run-help: compile
	CLASSPATH=$(CLASSPATH) java edu.vtc.cis3720.jxa03200.Main --help

run-classes: compile
	CLASSPATH=$(CLASSPATH) java edu.vtc.cis3720.jxa03200.Main foo bar

run-issue-classes: compile
	CLASSPATH=$(CLASSPATH) java edu.vtc.cis3720.jxa03200.Main --issue REQUIREMENTS foo bar

edu/vtc/cis3720/jxa03200/Issue.class: edu/vtc/cis3720/jxa03200/Issue.java

edu/vtc/cis3720/jxa03200/Main.class: edu/vtc/cis3720/jxa03200/Main.java \
		edu/vtc/cis3720/jxa03200/Issue.class \
		edu/vtc/cis3720/jxa03200/Review.class

edu/vtc/cis3720/jxa03200/Review.class: edu/vtc/cis3720/jxa03200/Review.java \
		edu/vtc/cis3720/jxa03200/Reviews.class

edu/vtc/cis3720/jxa03200/Reviews.class: edu/vtc/cis3720/jxa03200/Reviews.java

.PHONY: help compile run-no-opts run-help run-classes run-issue-classes

.SUFFIXES: .java .class
.java.class:
	CLASSPATH=$(CLASSPATH) javac -Xlint $(@:.class=.java)
