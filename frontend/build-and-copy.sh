#!/bin/bash

FRONTEND_DIR="."
BUILD_DIR="$FRONTEND_DIR/dist/repo-analyzer"
STATIC_DIR="../src/main/resources/static"

echo "📂 Checking frontend directory..."
if [ ! -d "$FRONTEND_DIR" ]; then
    echo "❌ Error: Frontend directory $FRONTEND_DIR does not exist."
    exit 1
fi

cd "$FRONTEND_DIR" || exit

echo "📦 Installing npm dependencies..."
npm install || { echo "❌ npm install failed"; exit 1; }

echo "⚙️ Building Angular app..."
npm run build || { echo "❌ Angular build failed"; exit 1; }

echo "📂 Checking build output directory..."
if [ ! -d "$BUILD_DIR" ]; then
    echo "❌ Error: Angular build output directory not found at $BUILD_DIR."
    exit 1
fi

echo "🧹 Cleaning old static files..."
rm -rf "$STATIC_DIR"
mkdir -p "$STATIC_DIR"

echo "📂 Copying built files to $STATIC_DIR..."
cp -r "$BUILD_DIR"/* "$STATIC_DIR/"

echo "✅ Build and copy completed successfully!"
