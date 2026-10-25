@echo off
if not exist out mkdir out
powershell -NoProfile -Command "$sources = Get-ChildItem -Path 'src' -Filter '*.java' -Recurse | ForEach-Object { $_.FullName }; javac -encoding UTF-8 -d 'out' $sources; exit $LASTEXITCODE"
if errorlevel 1 exit /b 1
java -cp out footballd3.Main
