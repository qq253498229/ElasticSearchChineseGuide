#!/usr/bin/env bash
asciidoctor -a stylesheet=../../base/css.css -o ./index.html ./asciidoc/cn/6.8.0/index.asciidoc
rm -rf ./images
mkdir ./images
cp -r asciidoc/base/images .