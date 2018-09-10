default: contest

export:
	export "LD_LIBRARY_PATH=~/Current_Semester/EC/assignmentfiles_2017/"

build:
	javac -cp contest.jar player53.java individual.java

create_submission: build
	jar cmf MainClass.txt submission.jar player53.class individual.class

dummy_test: create_submission
	java -jar testrun.jar -submission=player53 -evaluation=SphereEvaluation -seed=1

contest: create_submission export
	java -jar testrun.jar -submission=player53 -evaluation=BentCigarFunction -seed=1
