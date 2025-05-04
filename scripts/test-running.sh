#!/usr/bin/env bash

SCENARIO=$1
DIRECTORY="./docker_results"
COMMAND="commands_${SCENARIO}.txt"
RESULTS="pokemon_${SCENARIO}_results.txt"
INITIAL="pokemon_initial_${SCENARIO}_results.txt"
DIFFERENCE="diff_results_${SCENARIO}.txt"

mkdir -p ${DIRECTORY}

if [[  -f "./test_scenarios/${COMMAND}" ]]; then
  docker exec pokemon sh -c "\
      java -jar pokemon.jar < ${COMMAND} > ${RESULTS} && \
      diff -s ${RESULTS} ${INITIAL} > ${DIFFERENCE}" # && \
      # cat ${DIFFERENCE}"
  docker cp pokemon:/usr/src/cs6310/${RESULTS} ${DIRECTORY}
  docker cp pokemon:/usr/src/cs6310/${DIFFERENCE} ${DIRECTORY}

  FILE_CONTENTS="${DIRECTORY}/${DIFFERENCE}"
  echo "$(cat ${FILE_CONTENTS})"
else
    echo "File ${COMMAND} does not exist."
fi