/*******************************************************************************
Description: Insert default minimum required time into MINIMUM_TIME_SETTING table
Author: Andres Felipe Villamizar Collazos
Date 14-10-2023
@copyright: TechCamp
*******************************************************************************/

INSERT INTO APP_ASIG_NUM_TEL.MINIMUM_TIME_SETTING (TIME_ID, TIME_VALUE) VALUES (SEQ_MINIMUM_TIME_SETTING.NEXTVAL, 30);
COMMIT;
