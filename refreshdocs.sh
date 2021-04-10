#!/usr/bin/env sh

# Pastes all files from ./build/docs to ./docs
cp -f -r -u ./build/docs/javadoc/* ./docs
git add ./docs
