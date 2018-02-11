#!/usr/bin/env bash
mvn clean package
my_dir="$(dirname "$0")"
source ${my_dir}/deploy.sh