Assignment Repo for CS6310
# To Install Docker go to:
```
https://docs.docker.com/get-docker/
```
# Note please run all scripts from the project root directory
### To build:
```
docker build -t gatech/pokemon -f Dockerfile ./
```
### To run in interactive mode
###### If you are not debugging use 1.a & 2.a
###### If you want to attach a debug session to your container use 1.b and 2.b
#### Step 1.a from the host
```
docker run --rm -ti gatech/pokemon sh
```
#### Step 1.b from the host with debug
##### More details at the end
```
docker run --rm -p 5005:5005 -ti gatech/pokemon sh
```
#### Step 2.a from the container
```
java -jar pokemon.jar
```
#### Step 2.b from the container with debug
##### More details at the end
```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar pokemon.jar
```
#### Step 3 from the jar inside the container
* From there you can run any of the commands listed from the assignment:
```
setseed,<Integer>
removeseed
battle,<Pokemon 1>,<Pokemon 2>
tournament,<Pokemon 1>,<Pokemon 2>,....,<Pokemon N>
displayinfo,<Pokemon 1>
stop
```
### To run & test in interactive mode from inside the container
```
java -jar pokemon.jar < commands_00.txt > pokemon_00_results.txt
diff -s pokemon_00_results.txt pokemon_initial_00_results.txt > results_00.txt
cat results_00.txt
```
### To run a specific scenario of your jar from your host in docker and output to your host
The "mkdir docker_results ; " would not be needed after the 1st run, but just in case you have not made the directory yet with another command.
You can substitute 00 with a different command file.
```
mkdir docker_results ; docker run gatech/pokemon sh -c 'java -jar pokemon.jar < commands_00.txt'  > docker_results/pokemon_00_results.txt
```
### Remote Docker Debug with IntelliJ
#### Using Run / Debug IntelliJ configurations
```
Start the Docker Container
```
![run docker configuration](./img/run-docker.png)
```
Create a Terminal, 3 different examples
```
1. Right click on the container from the intellij window \
![terminal option 0](./img/create-terminal-0.png) \
![terminal option 1](./img/create-terminal-1.png)

2. Select terminal from the dashboard view \
![terminal option 2](./img/create-terminal-2.png)
3. Select terminal from log view \
![terminal option 3](./img/create-terminal-3.png)
```
Run java application on port 5005
```
![start jar](./img/start-jar.png)
```
Start Debug
```
![debug docker](./img/debug-docker.png)
### From here, you can go back to your docker terminal, and enter a command. If you have a breakpoint set, you will hit your breakpoint. 
```
To Stop Debug & Docker
```
![stop debug](./img/stop-debug.png)
![stop container](./img/stop-container.png)
![delete container](./img/delete-container.png)
#### Using CMD / Powershell / Terminal to start & IntelliJ to Debug
##### For the Debug steps, see above
```
Start the Docker Container
```
![cmd terminal inputs](./img/cmd-terminal.png)

### Example IntelliJ Configurations There should be no red or errors.
#### You may need to select the right docker service, and or update the java version to the version you have installed.
![edit configuration](./img/edit-configurations.png)
![docker configuration](./img/docker.png)
![docker debug configuration](./img/docker-debug.png)
![local java configuration](./img/local-java.png)

### If you get stuck in an infinite loop in your container
Simply stop and remove the running container from your host
```
docker ps
docker rm -f <container_id>
```
### To test a specific scenario
#### Mac / Linux from the host
```
./scripts/test.sh <scenario>
```
#### Windows from the host
```
.\scripts\test.sh <scenario>
```

### To batch run the test scenarios
#### Mac / Linux from the host
```
./scripts/batch.sh
```
#### Windows from the host
```
.\scripts\batch.sh
```
### If the scripts for test.sh & batch.sh are successful, they will create a docker_results folder under the root of your project on your host. The resulting files will be in this folder.
### To test with a clean image & container
After running the below command you will need to run the build command again
#### Windows
```
docker ps -aq | % { docker stop $_ } | % { docker rm -f $_ } | docker images -f "dangling=true" -aq | % { docker rmi -f $_ } | docker images gatech/* -aq | % { docker rmi -f $_ }
```
#### Mac
```
docker ps -aq | xargs docker stop | xargs docker rm -f && docker images -f "dangling=true" -aq | xargs docker rmi -f && docker images "gatech/*" -aq | xargs docker rmi -f
```
### To zip your code
You should validate your zip file has everything needed. Your src folder & everything under your src folder. Nothing else should be in your zip file. < filename > should be replaced with a format:`YYYY-##-A#`
1. YYYY is the current year: Ex 2024 
2. \#\# is the Semester, 01 is Spring, 02 is Summer, 03 is Fall
3. A# is A & the assignment number.
4. Ex: 2024-01-A4 for 2024 Spring Assignment 4
#### Mac / Linux
```
./scripts/zip.sh <filename>
```
#### Windows
```
.\scripts\zip.ps1 <filename>
```
### Goals for completion
```
1) All your test files that exist should come back with they are identical when doing the diff
2) EX: 'Files pokemon_00_results.txt and pokemon_initial_00_results.txt are identical'
3) The files that are hidden and not proviced yet (The missing ones out of the 10 total) will come back like the below when running the batch script
4) EX: 'File commands_10.txt does not exist.'
5) You should not need to or change the diff commands or docker files or any file outside your src directory
6) All Pokemon should exist inside the cs6310.Pokemon package. cs6310 is a top level package, Pokemon is a nested package under it. 
6) Double check your line endings in your testing / result files if needed, everything should have just LF not CLRF 
```

### Assignment Q&A Post
Please post any questions about docker or otherwise to the post linked below:  
[Link To Ed Discussion](https://edstem.org/)