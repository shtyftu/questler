#!/usr/bin/env bash
scp -i ~/.ssh/shty2.pem target/questler-0.0.1-SNAPSHOT.war ubuntu@ec2-54-234-71-83.compute-1.amazonaws.com:~/questler/
if [ $? -eq 1 ]; then
    echo Unable to deploy war file
    exit 1
fi
ssh -i "~/.ssh/shty2.pem" ubuntu@ec2-54-234-71-83.compute-1.amazonaws.com "sudo service questler-app restart"
if [ $? -eq 1 ]; then
    echo Unable to restart app
    exit 1
fi
