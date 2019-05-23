#!/usr/bin/env bash
rm -rf ./docs/images
mkdir ./docs/images
cp -r ./asciidoc/base/images ./docs

#asciidoctor -a stylesheet=../../base/css.css -o ./docs/6.8.0/index.html ./asciidoc/cn/6.8.0/index.asciidoc
asciidoctor -a stylesheet=../../base/css.css -o ./docs/7.1.0/index.html ./asciidoc/cn/7.1.0/index.asciidoc
