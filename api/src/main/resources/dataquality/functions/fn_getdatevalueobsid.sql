DROP FUNCTION IF EXISTS getdatevalueobsid;
/0xd
CREATE FUNCTION `getdatevalueobsid`(`obsid` int) RETURNS date
BEGIN

    DECLARE val DATE;

    SELECT  obs.value_datetime into val from obs WHERE  obs.obs_id=obsid;

	RETURN val;

END /0xd

