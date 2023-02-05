@rem
@rem Copyright 2023 the original author or authors.
@rem
@echo off
@rem ##########################################################################
@rem
@rem  Test Runner Of Automation
@rem
@rem ##########################################################################

SET PROJECT_DIR=%CD%
SET DRIVER=chrome
SET EXPLICIT_WAIT=30
SET IMPLICIT_WAIT=10
SET TEST_ENV=QA
SET HEADLESS=true
SET SEND_MAIL=false

CD %PROJECT_DIR%

echo Running Test...
echo mvn clean verify -DBROWSER=%DRIVER% -DTEST_ENV=%TEST_ENV% -DHEADLESS=%HEADLESS% -DEXPLICIT_WAIT=%EXPLICIT_WAIT% -DIMPLICIT_WAIT=%IMPLICIT_WAIT% -DSEND_MAIL=%SEND_MAIL%
CALL mvn clean verify -DBROWSER=%DRIVER% -DTEST_ENV=%TEST_ENV% -DHEADLESS=%HEADLESS% -DEXPLICIT_WAIT=%EXPLICIT_WAIT% -DIMPLICIT_WAIT=%IMPLICIT_WAIT% -DSEND_MAIL=%SEND_MAIL%
