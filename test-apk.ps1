# Quick APK Test - The Bookmark App
# Run this script to quickly install and test the APK

Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  The Bookmark App - APK Testing Script" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""

# APK Location
$apkPath = "d:\Semicolon\The Bookmark\BookmarkApp\app\build\outputs\apk\debug\app-debug.apk"

# Check if APK exists
if (Test-Path $apkPath) {
    Write-Host "✅ APK found!" -ForegroundColor Green
    $apkInfo = Get-Item $apkPath
    Write-Host "   Location: $apkPath" -ForegroundColor Gray
    Write-Host "   Size: $([math]::Round($apkInfo.Length / 1MB, 2)) MB" -ForegroundColor Gray
    Write-Host "   Created: $($apkInfo.LastWriteTime)" -ForegroundColor Gray
    Write-Host ""
} else {
    Write-Host "❌ APK not found at expected location!" -ForegroundColor Red
    Write-Host "   Expected: $apkPath" -ForegroundColor Gray
    exit 1
}

# Check for ADB
Write-Host "Checking for Android Debug Bridge (ADB)..." -ForegroundColor Yellow

$adbPaths = @(
    "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe",
    "$env:USERPROFILE\AppData\Local\Android\Sdk\platform-tools\adb.exe",
    "C:\Android\Sdk\platform-tools\adb.exe"
)

$adbPath = $null
foreach ($path in $adbPaths) {
    if (Test-Path $path) {
        $adbPath = $path
        break
    }
}

if ($adbPath) {
    Write-Host "✅ ADB found at: $adbPath" -ForegroundColor Green
    Write-Host ""
    
    # Check for devices
    Write-Host "Checking for connected devices..." -ForegroundColor Yellow
    $devices = & $adbPath devices
    Write-Host $devices -ForegroundColor Gray
    Write-Host ""
    
    # Count devices (excluding header)
    $deviceCount = ($devices | Select-String "device$").Count
    
    if ($deviceCount -gt 0) {
        Write-Host "✅ Found $deviceCount device(s)!" -ForegroundColor Green
        Write-Host ""
        
        # Ask to install
        $install = Read-Host "Do you want to install the APK now? (Y/N)"
        
        if ($install -eq "Y" -or $install -eq "y") {
            Write-Host ""
            Write-Host "Installing APK..." -ForegroundColor Yellow
            & $adbPath install -r $apkPath
            
            Write-Host ""
            Write-Host "✅ Installation command executed!" -ForegroundColor Green
            Write-Host ""
            Write-Host "📱 You can now open 'The Bookmark' app on your device!" -ForegroundColor Cyan
            Write-Host ""
        }
    } else {
        Write-Host "⚠️  No devices connected!" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "To install the APK:" -ForegroundColor Cyan
        Write-Host "1. Start an Android emulator from Android Studio" -ForegroundColor White
        Write-Host "2. Or connect your Android phone via USB with USB debugging enabled" -ForegroundColor White
        Write-Host "3. Run this script again" -ForegroundColor White
        Write-Host ""
        Write-Host "Alternative: Drag and drop the APK onto a running emulator" -ForegroundColor Cyan
    }
} else {
    Write-Host "⚠️  ADB not found in common locations" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Alternative Installation Methods:" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Method 1: Android Studio" -ForegroundColor White
    Write-Host "  1. Open Android Studio" -ForegroundColor Gray
    Write-Host "  2. Start an emulator" -ForegroundColor Gray
    Write-Host "  3. Drag the APK file onto the emulator window" -ForegroundColor Gray
    Write-Host ""
    Write-Host "Method 2: Physical Device" -ForegroundColor White
    Write-Host "  1. Copy APK to your phone:" -ForegroundColor Gray
    Write-Host "     $apkPath" -ForegroundColor DarkGray
    Write-Host "  2. Enable 'Install from Unknown Sources' in Settings" -ForegroundColor Gray
    Write-Host "  3. Tap the APK file to install" -ForegroundColor Gray
    Write-Host ""
}

Write-Host "==================================================" -ForegroundColor Cyan
Write-Host "  Testing Checklist" -ForegroundColor Cyan
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "After installation, test these features:" -ForegroundColor Yellow
Write-Host ""
Write-Host "  ☐ Sign Up with new account" -ForegroundColor White
Write-Host "  ☐ Login with credentials" -ForegroundColor White
Write-Host "  ☐ Navigate between tabs" -ForegroundColor White
Write-Host "  ☐ Add a note" -ForegroundColor White
Write-Host "  ☐ View account info" -ForegroundColor White
Write-Host "  ☐ Logout and login again" -ForegroundColor White
Write-Host "  ☐ Verify data persists" -ForegroundColor White
Write-Host ""
Write-Host "For detailed testing guide, see: APK_TESTING_GUIDE.md" -ForegroundColor Cyan
Write-Host ""
Write-Host "==================================================" -ForegroundColor Cyan
Write-Host ""

# Keep window open
Write-Host "Press any key to exit..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
