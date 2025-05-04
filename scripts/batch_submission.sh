#!/usr/bin/env bash

# SCENARIO=$1
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
regex="commands_([0-9]+).txt"

mkdir -p ./docker_results
# number of test cases * 10 + 10 extra sec
docker run -d --name pokemon gatech/pokemon sh -c "mkdir docker_results && sleep 110"

for test in $DIR/../test_scenarios/*;
do
  if [[ $test =~ $regex ]]
    then
        SCENARIO="${BASH_REMATCH[1]}"
        docker exec pokemon sh -c "java -jar pokemon.jar < commands_${SCENARIO}.txt > pokemon_${SCENARIO}_results.txt && \
        tr '[:upper:]' '[:lower:]' < pokemon_${SCENARIO}_results.txt > temp_${SCENARIO}.txt && mv temp_${SCENARIO}.txt pokemon_${SCENARIO}_results.txt && \
        tr -d '[:blank:]' < pokemon_${SCENARIO}_results.txt > temp_${SCENARIO}.txt && mv temp_${SCENARIO}.txt pokemon_${SCENARIO}_results.txt && \
        tr '[:upper:]' '[:lower:]' < pokemon_initial_${SCENARIO}_results.txt > temp_${SCENARIO}.txt && mv temp_${SCENARIO}.txt pokemon_initial_${SCENARIO}_results.txt && \
        tr -d '[:blank:]' < pokemon_initial_${SCENARIO}_results.txt > temp_${SCENARIO}.txt && mv temp_${SCENARIO}.txt pokemon_initial_${SCENARIO}_results.txt && \
        diff -a -b -E -s -N -u pokemon_${SCENARIO}_results.txt pokemon_initial_${SCENARIO}_results.txt > docker_results/diff_results_${SCENARIO}.txt" &
    fi
done
wait
# /usr/src/cs6310/
docker cp pokemon:/usr/src/cs6310/docker_results/ ./
docker rm -f pokemon &> /dev/null
i=0
for test in $DIR/../test_scenarios/*;
do
  if [[ $test =~ $regex ]]
    then
        if [[ $i -lt 10 ]]
          then
            SCENARIO="${BASH_REMATCH[1]}"
            echo " ------ Test Case: ${SCENARIO} ----"
            DIRECTORY="./docker_results"
            DIFFERENCE="diff_results_${SCENARIO}.txt"
            FILE_CONTENTS="${DIRECTORY}/${DIFFERENCE}"
            echo "$(cat ${FILE_CONTENTS})"
        fi
        ((i++))
    fi
done