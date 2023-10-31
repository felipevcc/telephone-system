/*******************************************************************************
Description: Check constraint on the IS_ACTIVE column of the TELEPHONE_NUMBER_AUDIT table
Author: Andres Felipe Villamizar Collazos
Date 30-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER_AUDIT
ADD CONSTRAINT CK_TELEPHONE_AUDIT_ACTIVE CHECK (IS_ACTIVE IN (1, 0));
