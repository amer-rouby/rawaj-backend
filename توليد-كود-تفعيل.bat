@echo off
setlocal
cd /d "%~dp0"

echo ========================================
echo   Generate Activation Code
echo ========================================
echo.

if not exist cp.txt (
  echo First-time setup, please wait...
  call "%~dp0mvnw.cmd" -q dependency:build-classpath -Dmdep.outputFile=cp.txt
  if errorlevel 1 (
    echo Setup failed. Make sure this file is in the backend project folder.
    pause
    exit /b 1
  )
)

if not exist target\classes (
  echo Compiling for the first time, please wait...
  call "%~dp0mvnw.cmd" -q compile
)

set /p LICENSE_KEY="Paste the activation key the technician sent you: "

if "%LICENSE_KEY%"=="" (
  echo You must enter the key first.
  pause
  exit /b 1
)

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0build-cp-arg.ps1" -Dir "%~dp0."

echo.
echo Generating code...
echo.

java @cp.args com.zakisupermarket.tools.LicenseCodeGenerator "E:\Backend\Projects\zaki-license-keys\license-private.pem" "%LICENSE_KEY%" 1200

echo.
echo ========================================
echo Copy the code above (after "Activation/renewal code")
echo and send it to the technician to paste into the customer's screen.
echo ========================================
pause
