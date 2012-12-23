#!/bin/bash
rm ../../testgame-android/assets/maps/*
java -classpath ../../../libgdx/gdx.jar:../../../libgdx/extensions/gdx-tools.jar:../../../libgdx/extensions/gdx-tiled-preprocessor.jar com.badlogic.gdx.tiledmappacker.TiledMapPacker maps ../../testgame-android/assets/maps 
