# Script to install Java 17
Write-Host "Downloading Java 17..." -ForegroundColor Green

$java17Url = "https://api.adoptium.net/v3/binary/latest/17/ga/windows/x64/jdk/hotspot/normal/eclipse"
$downloadPath = "$env:TEMP\jdk-17.zip"
$installPath = "$env:USERPROFILE\Java\jdk-17"

try {
    # Download Java 17
    Write-Host "Downloading from: $java17Url" -ForegroundColor Yellow
    Invoke-WebRequest -Uri $java17Url -OutFile $downloadPath -UseBasicParsing
    
    Write-Host "Extracting Java 17..." -ForegroundColor Green
    
    # Extract
    if (Test-Path $installPath) {
        Remove-Item $installPath -Recurse -Force
    }
    New-Item -ItemType Directory -Path $installPath -Force | Out-Null
    
    Expand-Archive -Path $downloadPath -DestinationPath "$env:TEMP\jdk-17-temp" -Force
    
    # Find JDK folder
    $jdkFolder = Get-ChildItem "$env:TEMP\jdk-17-temp" -Directory | Select-Object -First 1
    Copy-Item "$($jdkFolder.FullName)\*" -Destination $installPath -Recurse -Force
    
    # Cleanup
    Remove-Item "$env:TEMP\jdk-17-temp" -Recurse -Force
    Remove-Item $downloadPath -Force
    
    Write-Host "Java 17 installed to: $installPath" -ForegroundColor Green
    Write-Host ""
    Write-Host "Setting JAVA_HOME environment variable..." -ForegroundColor Yellow
    [System.Environment]::SetEnvironmentVariable('JAVA_HOME', $installPath, 'User')
    Write-Host "JAVA_HOME set to: $installPath" -ForegroundColor Green
    Write-Host ""
    Write-Host "Please restart your terminal and run:" -ForegroundColor Yellow
    Write-Host "  .\gradlew.bat bootRun" -ForegroundColor Cyan
    
} catch {
    Write-Host "Error: $_" -ForegroundColor Red
    Write-Host ""
    Write-Host "Alternative method:" -ForegroundColor Yellow
    Write-Host "1. Download Java 17 manually: https://adoptium.net/temurin/releases/?version=17" -ForegroundColor Cyan
    Write-Host "2. Install it" -ForegroundColor Cyan
    Write-Host "3. Set JAVA_HOME to Java 17 path" -ForegroundColor Cyan
}
