/*******************************************************************************
Description: Job scheduled to run the telephone number tracking procedure every day at midnight
Author: Andres Felipe Villamizar Collazos
Date 16-10-2023
@copyright: TechCamp
*******************************************************************************/

BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
        job_name        => 'MI_JOB_DIARIO',
        job_type        => 'PLSQL_BLOCK',
        job_action      => 'BEGIN PCK_TELEPHONE_NUMBER.Proc_TrackingNumbers; END;',
        start_date      => TRUNC(SYSTIMESTAMP) + INTERVAL '1' DAY,
        repeat_interval => 'FREQ=DAILY; BYHOUR=0; BYMINUTE=0; BYSECOND=0',
        enabled         => TRUE
    );
END;
