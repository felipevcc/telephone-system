/*******************************************************************************
Description: Table that stores the data of the telephone centers
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE TABLE APP_ASIG_NUM_TEL.CENTER
(
    CENTER_ID NUMBER,
    NAME VARCHAR2(20) NOT NULL,
    ADDRESS VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(50) NOT NULL,
    PHONE_NUMBER VARCHAR2(20) NOT NULL,
    INITIAL_NUMBER NUMBER(8) NOT NULL,
    FINAL_NUMBER NUMBER(8) NOT NULL,
    CREATED_AT DATE DEFAULT SYSDATE NOT NULL
) TABLESPACE TS_APP_ASIG_NUM_TEL;


COMMENT ON TABLE APP_ASIG_NUM_TEL.CENTER IS 'Table that stores the data of the telephone centers';


COMMENT ON COLUMN APP_ASIG_NUM_TEL.CENTER.CENTER_ID IS 'Unique identifier for the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CENTER.NAME  IS 'Represents the name of the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CENTER.ADDRESS  IS 'Represents the address of the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CENTER.EMAIL  IS 'Represents the email of the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CENTER.PHONE_NUMBER  IS 'Represents the phone number of the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CENTER.INITIAL_NUMBER  IS 'Initial number of the range of telephones available in the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CENTER.FINAL_NUMBER  IS 'Final number of the range of telephones available in the center';

COMMENT ON COLUMN APP_ASIG_NUM_TEL.CENTER.CREATED_AT  IS 'Center creation date';
