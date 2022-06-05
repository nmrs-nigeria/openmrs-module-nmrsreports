DROP FUNCTION IF EXISTS getoutcomewithpillbalanceandfrequency;
/0xd

CREATE FUNCTION `getoutcomewithpillbalanceandfrequency`(`lastpickupdate` DATE,`daysofarvrefill` NUMERIC, `ltfudays` NUMERIC, `pillbalance` NUMERIC, `obsid` int, `enddate` DATE) RETURNS TEXT CHARSET UTF8
    BEGIN
        DECLARE  ltfudate DATE;
        DECLARE  ltfunumber NUMERIC;
        DECLARE  daysdiff NUMERIC;
        DECLARE outcome TEXT;
        DECLARE frequencycode NUMERIC;
        DECLARE evaluatedpillbalance INT;
	
		SELECT obs.value_coded INTO frequencycode FROM obs WHERE obs.obs_id=obsid;
        
    	SELECT CASE  
		WHEN frequencycode=160870 THEN FLOOR(pillbalance/4)
		WHEN frequencycode=166057 THEN FLOOR(pillbalance/8)
		WHEN frequencycode=166056 THEN FLOOR(pillbalance/6)
		WHEN frequencycode=165721 THEN FLOOR(pillbalance/4)
        WHEN frequencycode=160858 THEN FLOOR(pillbalance/2)
        ELSE pillbalance
		END INTO evaluatedpillbalance;
		
        SET ltfunumber=daysofarvrefill+ltfudays+evaluatedpillbalance;
        SELECT DATE_ADD(lastpickupdate, INTERVAL ltfunumber DAY) INTO ltfudate;
        SELECT DATEDIFF(ltfudate,enddate) INTO daysdiff;
        SELECT IF(lastpickupdate IS NULL,"",IF(daysdiff >=0,"Active","InActive")) INTO outcome;
        RETURN outcome;

END /0xd