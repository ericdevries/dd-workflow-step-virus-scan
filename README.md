dd-workflow-step-virus-scan
===========
![Build Status](https://github.com/DANS-KNAW/dd-workflow-step-virus-scan/actions/workflows/build.yml/badge.svg)
![Site Status](https://github.com/DANS-KNAW/dd-workflow-step-virus-scan/actions/workflows/docs.yml/badge.svg)

For documentation see: https://dans-knaw.github.io/dd-workflow-step-virus-scan


docker run -p 3310:3310 -v ./clamd.conf:/etc/clamav/clamd.conf mkodockx/docker-clamav:alpine
