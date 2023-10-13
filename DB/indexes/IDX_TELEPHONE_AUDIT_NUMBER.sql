/*******************************************************************************
Description: Index in the telephone number column to consult the history
Author: Andres Felipe Villamizar Collazos
Date 13-10-2023
@copyright: TechCamp
*******************************************************************************/

CREATE INDEX IDX_TELEPHONE_AUDIT_NUMBER ON APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT (PHONE_NUMBER);
