#!/usr/bin/env bash
asciidoctor -a stylesheet=../../base/css.css -o ./docs/index.html ./asciidoc/cn/6.8.0/index.asciidoc
rm -rf ./docs/images
mkdir ./docs/images
cp -r ./asciidoc/base/images ./docs