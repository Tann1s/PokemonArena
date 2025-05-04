#!/usr/bin/env bash

exec &>> regrade-results.txt

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# run batch for submission
timeout 130 $DIR/grader.sh