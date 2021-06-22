DROP FUNCTION IF EXISTS getoutcome;
/0xd

CREATE FUNCTION `getoutcome`(`lastpickupdate` DATE,`daysofarvrefill` NUMERIC,`ltfudays` NUMERIC, `enddate` DATE) RETURNS TEXT CHARSET UTF8
    BEGIN
        DECLARE  ltfudate DATE;
        DECLARE  ltfunumber NUMERIC;
        DECLARE  daysdiff NUMERIC;
        DECLARE outcome TEXT;

        SET ltfunumber=daysofarvrefill+ltfudays;
        SELECT DATE_ADD(lastpickupdate, INTERVAL ltfunumber DAY) INTO ltfudate;
        SELECT DATEDIFF(ltfudate,enddate) INTO daysdiff;
        SELECT IF(daysdiff >=0,"Active","LTFU") INTO outcome;
        RETURN outcome;

END /0xd