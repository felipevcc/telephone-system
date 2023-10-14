/*******************************************************************************
Description: Table that stores updates of the time required for released numbers to make them available again
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.MINIMUM_TIME_SETTING
(
    TIME_ID NUMBER(10),
    TIME_VALUE NUMBER(4) NOT NULL DEFAULT 30,
    CREATED_AT DATE NOT NULL DEFAULT CURRENT_DATE
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.MINIMUM_TIME_SETTING IS 'Table that stores updates of the time required for released numbers to make them available again'


COMMENT ON COLUMN APP_ASIG_NUM_TEL.MINIMUM_TIME_SETTING.TIME_ID IS 'Unique identifier for the minimum time setting';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.MINIMUM_TIME_SETTING.TIME_VALUE  IS 'Represents the amount in days of the time required';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.MINIMUM_TIME_SETTING.CREATED_AT  IS 'Time creation date';
