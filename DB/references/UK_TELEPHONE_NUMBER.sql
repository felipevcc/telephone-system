/*******************************************************************************
Description: Unique constraint on the PHONE_NUMBER column of the TELEPHONE_NUMBER table
Author: Andres Felipe Villamizar Collazos
Date 12-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.TELEPHONE_NUMBER
ADD CONSTRAINT UK_TELEPHONE_NUMBER UNIQUE (PHONE_NUMBER);
