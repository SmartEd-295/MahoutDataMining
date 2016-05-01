@echo off
setLocal EnableDelayedExpansion

set limit=600000
set file=SmartCampusStudentData_03292016.csv
set lineCounter=1
set filenameCounter=1

set name=
set extension=
for %%a in (%file%) do (
    set "name=%%~na"
    set "extension=%%~xa"
)

for /f "tokens=*" %%a in (%file%) do (
    set splitFile=!name!-part!filenameCounter!!extension!
    if !lineCounter! gtr !limit! (
        set /a filenameCounter=!filenameCounter! + 1
        set lineCounter=1
        echo Created !splitFile!.
    )
    echo %%a>> !splitFile!

    set /a lineCounter=!lineCounter! + 1
)