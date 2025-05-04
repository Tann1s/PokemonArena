#!/usr/bin/env bash

SCENARIO=$1
DIRECTORY="./docker_results"
COMMAND="commands_${SCENARIO}.txt"
RESULTS="pokemon_${SCENARIO}_results.txt"
INITIAL="pokemon_initial_${SCENARIO}_results.txt"
DIFFERENCE="diff_results_${SCENARIO}.txt"
TIMEFORMAT="%R"

mkdir -p ${DIRECTORY}

function dockerstuff(){
  docker exec pokemon sh -c "\
    java -jar pokemon.jar < ${COMMAND} > ${RESULTS} && \
    diff -s ${RESULTS} ${INITIAL} > ${DIFFERENCE}"
  docker cp pokemon:/usr/src/cs6310/${RESULTS} ${DIRECTORY}
  docker cp pokemon:/usr/src/cs6310/${DIFFERENCE} ${DIRECTORY}
}

if [[ -f "./test_scenarios/${COMMAND}" ]]; then
  docker run -d --name pokemon gatech/pokemon sh -c "mkdir docker_results && sleep 100"  > /dev/null
  RUNTIME=`time (dockerstuff) 2>&1`
  docker rm -f pokemon > /dev/null
  FILE_CONTENTS="${DIRECTORY}/${DIFFERENCE}"
  echo "Elapsed time: ${RUNTIME}s" >> ${FILE_CONTENTS}
  echo "$(cat ${FILE_CONTENTS})"
else
    echo "File ${COMMAND} does not exist."
fi