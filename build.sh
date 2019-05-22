#!/usr/bin/env bash
asciidoctor -a stylesheet=../../base/css.css -o ./asciidoc/out/index.html ./asciidoc/cn/6.8.0/index.asciidoc
rm -rf ./asciidoc/out/images
mkdir ./asciidoc/out/images
cp -r ./asciidoc/base/images ./asciidoc/out