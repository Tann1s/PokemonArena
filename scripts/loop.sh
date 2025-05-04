#!/usr/bin/env bash

exec &>> results.txt

now=$(date +"%m-%d-%y %r")
echo "Start time : $now"

oldIFS=${IFS}
IFS=$'\n'

# printf "IFS was: ${oldIFS}"

find ./submissions/ -iname '* *' | while read LINE
do
  newName="${LINE// /_}"
  printf "removing space from ${LINE} new name ${newName}\r\n"
  mv "${LINE}" "${newName}"
done

find ./submissions/ -type f -name '* *' | while read LINE
do
  newName="${LINE// /_}"
  printf "removing space from ${LINE} new name ${newName}\r\n"
  mv "${LINE}" "${newName}"
done

IFS=${oldIFS}

for i in $(find ./submissions/ -type f -iname "*.pdf")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.pdf")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.jpg")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.jpg")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.jpeg")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.jpeg")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.png")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.png")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.txt")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.txt")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.docx")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.docx")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.svg")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.svg")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.drawio")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.drawio")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.jar")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.jar")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.pptx")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.pptx")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.rar")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.rar")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.7z")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname "*.7z")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

# find ./submissions/ -type f -iname "*.jar" -execdir sh -c 'printf "%s\n" "${0%.*}"' {} ';' | xargs rm -r -f

mkdir ./submissions/jars

for i in $(find ./submissions/ -type f -iname "*.jar")
do
  name=${i/.\//}
  name=${name/submissions\//}
  name=${name/.jar/}
  printf "making jar folder ${name}\r\n"
  mkdir "./submissions/jars/${name}"
done

for i in $(find ./submissions/ -type f -iname "*.jar" -execdir sh -c 'printf "%s\n" "${0%.*}"' {} ';')
do
  name=${i/.\//}
  name=${name/submissions\//}
  name=${name/.jar/}
  printf "moving jar ${name}\r\n"
  mv "./submissions/${name}.jar" "./submissions/jars/${name}/"
done

for i in $(find ./submissions/ -type f -iname "*.zip.zip" -execdir sh -c 'printf "%s\n" "${0%.*}"' {} ';')
do
  name=${i/.\//}
  name=${name/submissions\//}
  printf "renaming double .zip.zip ${name}.zip\r\n"
  mv "./submissions/${name}.zip" "./submissions/${name}"
done

for i in $(find ./submissions/ -type f -iname "*_.zip-1.zip" -execdir sh -c 'printf "%s\n" "${0%.*}"' {} ';')
do
  name=${i/.\//}
  name=${name/"_.zip-1"/""}
  name=${name/submissions\//}
  printf "${name}\r\n"
  printf "renaming double _.zip-1.zip ${name}.zip\r\n"
  mv "./submissions/${name}_.zip-1.zip" "./submissions/${name}.zip"
done

for i in $(find ./submissions/ -type f -iname "*.zip" -execdir sh -c 'printf "%s\n" "${0%.*}"' {} ';')
do
  name=${i/.\//}
  name=${name/submissions\//}
  name=${name/.zip/}
  folder=${name//src/}
  folder=${folder//SRC/}
  printf "removing old folder ${folder}\r\n"
  rm -r -f ${folder}
  printf "unzipping ${name}\r\n"
  unzip -d "./submissions/${folder}/" "./submissions/${name}.zip"
  printf "moving zip ${name}\r\n"
  mv "./submissions/${name}.zip" "./submissions/${folder}/"
done

# Remove spaces again
oldIFS=${IFS}
IFS=$'\n'

# printf "IFS was: ${oldIFS}"

find ./submissions/ -iname '* *' | while read LINE
do
  newName="${LINE// /_}"
  printf "removing space from ${LINE} new name ${newName}\r\n"
  mv "${LINE}" "${newName}"
done

# file type removed for spaces in directory structure.
find ./submissions/ -name '* *' | while read LINE
do
  newName="${LINE// /_}"
  printf "removing space from ${LINE} new name ${newName}\r\n"
  mv "${LINE}" "${newName}"
done

IFS=${oldIFS}

# End remove spaces again

# remove any macos folders or files.
for i in $(find ./submissions/ -type d -iname "__MACOSX")
do
  printf "removing ${i}\r\n"
  rm -rf "${i}"
done

for i in $(find ./submissions/ -type d -iname "__MACOSX")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

for i in $(find ./submissions/ -type f -iname ".DS_Store")
do
  printf "removing ${i}\r\n"
  rm -f "${i}"
done

for i in $(find ./submissions/ -type f -iname ".DS_Store")
do
  printf "deleting ${i}\r\n"
  delete "${i}"
done

j=0

for i in $(find ./submissions/ -iname "src");
do
  # remove any existing containers
  docker ps -a -q | xargs docker rm -f &> /dev/null
  docker container prune -f &> /dev/null
  # remove any existing images
  docker rmi -f gatech/pokemon
  # setup next student
  folder=${i//src/}
  folder=${folder//SRC/}
  rmFolder=${folder//.\//}
  semesterFolder="2024-01-A2"
  printf "${folder}\r\n"
  # printf "src folder: ${folder}\r\n"
  # printf "src folder: ${rmFolder}\r\n"
  rm -rf "${rmFolder}test_scenarios/"*
  rm -rf "${rmFolder}test_results/"*
  rm -rf "${rmFolder}docker_results/"*
  rm -rf "${rmFolder}submission/"*
  rm -rf "${rmFolder}bin/"*
  rm -rf "${rmFolder}out/"*
  rm -rf "${rmFolder}out/"
  rm -rf "${rmFolder}.idea/"*
  rm -rf "${rmFolder}.idea/"
  rm -rf "${rmFolder}scripts/"*
  rm -rf "${rmFolder}Dockerfile"
  rm -rf "${rmFolder}.classpath"
  rm -rf "${rmFolder}.gitignore"
  rm -rf "${rmFolder}README.md"
  rm -rf "${rmFolder}.project"
  cp "./${semesterFolder}/Dockerfile" "${folder}"
  # cp "./${semesterFolder}/DockerfileV2" "${folder}"
  cp -R "./${semesterFolder}/scripts/." "${folder}scripts/"
  cp -R "./${semesterFolder}/test_scenarios/." "${folder}test_scenarios/"
  cp -R "./${semesterFolder}/test_results/." "${folder}test_results/"
  cp -R "./${semesterFolder}/src/." "${folder}src/"

  if test -f "${folder}Dockerfile"; then
    cDir=${PWD}
    cd "$folder"
    #printf "${PWD}\r\n"
    #printf "dockerfile at: ${folder}Dockerfile\r\n"
    oldFS=${IFS}
    IFS='/'
    student=''
    read -r -a elements <<< "${folder}Dockerfile"
    for element in "${elements[@]}";
    do
      #printf "element: ${element}\r\n"
      if [[ "${element}" == *"_"* ]]; then
        elementIFS=${IFS}
        IFS='_'
        read -r -a studentSubmission <<< "${element}"
        #printf "student is: ${studentSubmission[0]}\r\n"
        student=${studentSubmission[0]}
        IFS=${elementIFS}
      fi
    done
    IFS=${oldFS}
    # change default print to file
    exec &>> "${student}_results_${j}.txt"
    # build & run new student
    docker build -t gatech/pokemon -f Dockerfile ./ --no-cache --rm
    # rename old jar file
    # mv submission/streaming_wars.jar submission/old.jar
    # scripts/copy.sh
    # slightly higher than the grader by 10 sec
    timeout 130 scripts/grader.sh
    cd "$cDir"
    exec &>> results.txt
  fi

  j=$((j+1))
done

exec &>> results.txt

now=$(date +"%m-%d-%y %r")
echo "Finish time : $now"