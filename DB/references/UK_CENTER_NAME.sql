/*******************************************************************************
Description: Unique constraint on the NAME column of the CENTER table
Author: Andres Felipe Villamizar Collazos
Date 13-10-2023
@copyright: TechCamp
*******************************************************************************/

ALTER TABLE APP_ASIG_NUM_TEL.CENTER
ADD CONSTRAINT UK_CENTER_NAME UNIQUE (NAME);
