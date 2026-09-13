param([string]$Dir)
$Dir = (Resolve-Path $Dir).Path
$cp = Get-Content -Raw (Join-Path $Dir "cp.txt")
$cp = $cp.Trim()
# java's @argfile parser treats backslash as a shell-style escape character,
# which corrupts Windows paths - use forward slashes throughout (the JVM
# accepts them fine on Windows) instead of the native backslash separator.
$classesDir = (Join-Path $Dir "target\classes") -replace '\\', '/'
$cp = $cp -replace '\\', '/'
$line = '-cp "' + $classesDir + ';' + $cp + '"'
Set-Content -Path (Join-Path $Dir "cp.args") -Value $line -NoNewline -Encoding ascii
